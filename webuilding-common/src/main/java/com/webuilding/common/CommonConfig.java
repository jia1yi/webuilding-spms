package com.webuilding.common;

import java.util.UUID;

public class CommonConfig {
    //第三方接口调试设置
    private boolean apiDebug;
    //令令接口配置
    private String lingUrl;
    private String lingSignature;
    private String lingToken;
    private String lingOpenToken;

    //微信后台接口配置
    private String weixinUrl;

    //api token
    private String tokenSecret;//加密密钥
    private String tokenKey;//请求token参数名
    private long tokenExpireTime;//token过期时间

    private int maxPagesize;

    public int getMaxPagesize() {
        return maxPagesize;
    }

    public void setMaxPagesize(int maxPagesize) {
        this.maxPagesize = maxPagesize;
    }

    public boolean isApiDebug() {
        return apiDebug;
    }

    public void setApiDebug(boolean apiDebug) {
        this.apiDebug = apiDebug;
    }

    public String getLingUrl() {
        return lingUrl;
    }

    public void setLingUrl(String lingUrl) {
        this.lingUrl = lingUrl;
    }

    public String getLingSignature() {
        return lingSignature;
    }

    public void setLingSignature(String lingSignature) {
        this.lingSignature = lingSignature;
    }

    public String getLingToken() {
        return lingToken;
    }

    public void setLingToken(String lingToken) {
        this.lingToken = lingToken;
    }

    public String getLingOpenToken() {
        return lingOpenToken;
    }

    public void setLingOpenToken(String lingOpenToken) {
        this.lingOpenToken = lingOpenToken;
    }

    public String getWeixinUrl() {
        return weixinUrl;
    }

    public void setWeixinUrl(String weixinUrl) {
        this.weixinUrl = weixinUrl;
    }

    public String getTokenSecret() {
        tokenSecret= UUID.randomUUID().toString();
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public long getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(long tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }
}
