package com.webuilding.rsa;

import com.webuilding.common.AjaxResult;
import com.webuilding.config.GlobalConfig;
import com.webuilding.util.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Random;

@ControllerAdvice(basePackages = {"com.webuilding.controller"})
@Component
public class MyResponseBodyAdvice implements ResponseBodyAdvice<AjaxResult> {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 算法
     */
    private static final String ALGORITHM_NAME = "RSA";
    /**
     * MD5_RSA
     */
    private static final String MD5_RSA = "MD5withRSA";
    @Autowired
    private GlobalConfig config;

    @Value("${project.public.kay}")
    public String publicKay;
    /**
     *
     * @param returnType
     * @param converterType
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        RsaRequest serializedField = returnType.getMethodAnnotation(RsaRequest.class);
        if (StringUtils.isNull(serializedField)){
            return false;
        }
        return serializedField.result();
    }

    /**
     * 对于结果进行加密
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     */
    @SneakyThrows
    @Override
    public AjaxResult beforeBodyWrite(AjaxResult body, MethodParameter returnType, org.springframework.http.MediaType selectedContentType,
                                      Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Object data = body.get("msg");
        String code = (String)body.get("code");
        if (StringUtils.isNull(data) /*|| "1"!=code*/){
            return body;
        }
        // 生成一个解数据的秘钥
        String aseKey = getRandomString(16);
        //加密密钥
        String encrypt = RSAUtils.encrypt(aseKey,RSAUtils.getPublicKey( config.getPublicKey()));
        String encrypt1 = AesEncryptUtils.encrypt(data.toString(), aseKey);


        String encrypt55 = RSAUtils.decrypt(encrypt,RSAUtils.getPrivateKey( config.getPrivateKey()));
        String encrypt66 = AesEncryptUtils.decrypt(encrypt1, encrypt55);


        body.put("encrypted",encrypt);
        body.put(AjaxResult.DATA_TAG,encrypt1);
        return body;
    }
    /**
     * 创建指定位数的随机字符串
     * @param length 表示生成字符串的长度
     * @return 字符串
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }



}

