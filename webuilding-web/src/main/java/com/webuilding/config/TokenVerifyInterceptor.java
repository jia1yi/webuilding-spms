package com.webuilding.config;

import com.webuilding.annotation.TokenVerify;
import com.webuilding.common.CommonConfig;
import com.webuilding.entity.VisitorBook;
import com.webuilding.service.IVisitorBookService;
import com.webuilding.util.JWTUtil;
import com.webuilding.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

@Component
public class TokenVerifyInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AllControllerAdvice.class);
    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private IVisitorBookService visitorBookService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(TokenVerify.class)) {
                String token = request.getHeader("Authorization");

                String method1 = request.getRequestURI();
                //logger.info("方法名称："+method1);
                /*if(method1.endsWith("queryQrCode")){
                    String visitorId = request.getParameter("visitorId");

                    if(!StringUtils.isEmpty(visitorId)){
                        verifyVisitorId(token,visitorId);
                    }else {
                        return false;
                    }
                }*/

                if(method1.endsWith("reserveList")||method1.endsWith("approveList")){
                    String telephone = request.getParameter("telephone");
                    if(!StringUtils.isEmpty(telephone)){
                        if (!verify(token, telephone)) {
                            throw new RuntimeException("token和手机号不匹配");
                        }
                    }else {
                        return false;
                    }
                }
                if(method1.endsWith("recentRecord")){
                    String visitorTel = request.getParameter("visitorTel");
                    if(!StringUtils.isEmpty(visitorTel)){
                        if (!verify(token, visitorTel)) {
                            throw new RuntimeException("token和手机号不匹配");
                        }
                    }else {
                        return false;
                    }
                }

            }
        }
        return true;
    }
    private boolean verifyVisitorId(String token, String visitorId) {

        VisitorBook bookInfo = visitorBookService.selectVisitorBookByVisitorID(visitorId);

        return verify(token, bookInfo.getVisitorTelephone());
    }
    private boolean verify(String token, String tel) {
        //JWTUtil.tokenMaps.put("123","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTm8iOiIxNTE5MTQ3MTEwOSIsImV4cCI6MTY4OTE3MTM5NH0.ii4sgEn0GA2jiVQf59Mk969Ycg7HdhOxcpepLb4uPx8");
        // 验证逻辑
       // logger.info("校验token和手机号---tel："+tel+",token"+token);
        if(globalConfig.getTokenMaps().get(tel).getToken().equals(token)){
            return true;
        }
        return false;
    }
}