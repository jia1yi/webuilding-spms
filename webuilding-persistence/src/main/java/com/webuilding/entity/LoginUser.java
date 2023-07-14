package com.webuilding.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录用户
 */
public class LoginUser implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     *  登录手机号
     */
    private String telephone;

    /**
     * 微信id
     */
    private String wechatId;

    /**
     * 用户最后登录时间
     */
    private Date last_login_time;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public Date getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }
}
