package com.webuilding.rsa;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RsaRequest {

    /**
     * 入参是否解密，默认解密
     */
    boolean param() default true;

    /**
     * 出参是否加密，默认加密
     */
    boolean result() default true;

}

