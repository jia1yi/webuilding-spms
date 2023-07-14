package com.webuilding.entity;

import java.io.Serializable;

/**
 * 操作日志表
 */
public class OperationLog implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer operationLogId;
    /**
     * 日志描述
     */
    private String logDescription;
    /**
     * 方法参数
     */
    private String actionArgs;
    /**
     * 用户主键
     */
    private String userNo;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;

    private String ip;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 是否成功 1:成功 2异常
     */
    private Integer succeed;
    /**
     * 异常堆栈信息
     */
    private String message;
    /**
     * 模块名称
     */
    private String modelName;
    /**
     * 操作
     */
    private String action;

    public Integer getOperationLogId() {
        return operationLogId;
    }

    public void setOperationLogId(Integer operationLogId) {
        this.operationLogId = operationLogId;
    }

    public String getLogDescription() {
        return logDescription;
    }

    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription;
    }

    public String getActionArgs() {
        return actionArgs;
    }

    public void setActionArgs(String actionArgs) {
        this.actionArgs = actionArgs;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getSucceed() {
        return succeed;
    }

    public void setSucceed(Integer succeed) {
        this.succeed = succeed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
                "logDescription='" + logDescription + '\'' +
                ", actionArgs='" + actionArgs + '\'' +
                ", userNo='" + userNo + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                ", succeed=" + succeed +
                ", message='" + message + '\'' +
                ", modelName='" + modelName + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
