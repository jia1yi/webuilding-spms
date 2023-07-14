package com.webuilding.rsa;

import com.webuilding.config.GlobalConfig;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;


@ControllerAdvice(basePackages = {"com.webuilding.controller"})
@Component
public class RsaDecodeRequestBodyAdvice implements RequestBodyAdvice {
    private static final Logger log = LoggerFactory.getLogger(RsaDecodeRequestBodyAdvice.class);
    @Autowired
    private GlobalConfig config;
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            log.info("sdafdsfsadfasdf");
            HttpHeaders headers = inputMessage.getHeaders();
            List<String> authorization = headers.get("Authorization");
            boolean param = false;
            if (methodParameter.getMethod().isAnnotationPresent(RequestAuth.class)) {
                //获取注解配置的包含和去除字段
                RequestAuth serializedField = methodParameter.getMethodAnnotation(RequestAuth.class);
                //入参是否需要解密
                param = serializedField.param();
            }
            if (param) {
                log.info("对参数进行进行解密");
                return new ResquestInputMessage(inputMessage);
            }else{
                return inputMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("对参数进行进行解密");
            return inputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    class ResquestInputMessage implements HttpInputMessage {
        private HttpHeaders headers;
        private InputStream body;
        public ResquestInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            String content = IOUtils.toString(inputMessage.getBody(),"utf-8");
            this.body = IOUtils.toInputStream(XRsaUtil.getData(content,config.getPrivateKey()));
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }
        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

    }


}
