package com.webuilding.shiro;

import com.alibaba.fastjson2.JSONObject;
import com.webuilding.common.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author grm
 *
 * 代码的执行流程preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    @Autowired
    CommonConfig commonConfig;

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        if (commonConfig == null) {
            commonConfig = SpringContextBeanService.getBean("commonConfig");
        }
        String authorization = req.getHeader(commonConfig == null?"Authorization":commonConfig.getTokenKey());
        return authorization != null;
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (commonConfig == null) {
            commonConfig = SpringContextBeanService.getBean("commonConfig");
        }
        String authorization = httpServletRequest.getHeader(commonConfig == null?"Authorization":commonConfig.getTokenKey());

        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        setUserBean(request, response, token);
        return true;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if(Constant.METHOD_URL_SET.contains(requestURI)){
            return true;
        }
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
                return true;
            } catch (AuthenticationException e) {
                log.info(e.getMessage());
                responseError(request, response);
            }
        }
        return false;
    }

    private void setUserBean(ServletRequest request, ServletResponse response, JWTToken token) {
        /*Object principal = SecurityUtils.getSubject().getPrincipal();
        if(principal instanceof User){
            User userBean =(User)principal;
            request.setAttribute("currentUser", userBean);
        }*/
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 非法url返回身份错误信息
     */
     private void responseError(ServletRequest request, ServletResponse response) {
        Writer out= null;
        OutputStreamWriter outputStreamWriter =null;
        try {
            outputStreamWriter = new OutputStreamWriter(response.getOutputStream(), "utf-8");
            response.setContentType("application/json; charset=utf-8");
            out = new BufferedWriter(outputStreamWriter);
            out.write(JSONObject.toJSONString(AjaxResult.error(CodeEnum.IDENTIFICATION_ERROR.getCode(),CodeEnum.IDENTIFICATION_ERROR.getMsg(),"")));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null !=  outputStreamWriter){
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

   
}
