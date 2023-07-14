package com.webuilding.exceprion;

import com.webuilding.common.CodeEnum;

/**
 * http请求错误
 */
public class ApiHttpException extends Exception{
    /**
     */
    private static final long serialVersionUID = 3455708526465670030L;

    private String code;
    private String message;

    public ApiHttpException(String msg){
        super(msg);
        this.code = CodeEnum.DATA_ERROR.getCode();
        this.message = msg;
    }

    public ApiHttpException(String msg, String code){
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}