package com.webuilding.common;

/**
 * 请求错误码
 */
public enum CodeEnum {
    SUCCESS("1","操作成功"),
    ERROR("0","操作失败"),

    IDENTIFICATION_ERROR("20001","身份异常"),
    VERIFICATION_CODE_ERROR("20003","验证码不正确"),
    LINGLING_ERR("30001","接口调用失败"),//令令接口调用失败
    WEIXIN_ERR("30002","接口调用失败"),//微信后台接口调用失败
    DASHI_ERR("30003","接口调用失败"),//达实接口调用失败

    DATA_ERROR("50000","业务错误"),
    PARAM_ERROR("60000","参数错误"),

    PAGESIZE_ERROR("70000","分页参数错误");

    private String respCode;
    private String respMsg;


    CodeEnum(String respCode, String respMsg){
        this.respCode = respCode;
        this.respMsg=respMsg;
    }

    public String getCode() {
        return respCode;
    }
    public String getMsg() {
        return respMsg;
    }
}
