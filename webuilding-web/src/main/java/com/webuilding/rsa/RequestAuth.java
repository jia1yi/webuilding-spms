package com.webuilding.rsa;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestAuth {

    /**
     * 入参是否解密，默认解密
     */
    boolean param() default true;


}

