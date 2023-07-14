package com.webuilding.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 预约记录
 */
public class VisitorBook implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     *  主键
     */
    private int id;
    /**
     * 预约记录id
     */
    private String visitorId;
    /***
     *拜访组织机构 code
     */
    private String visitDepartCode;
    /***
     *拜访组织机构名称
     */
    private String visitDepartName;
    /***
     *访问事由
     */
    private String visitPurpose;
    /***
     *最低楼层
     */
    private int baseFloor;
    /***
     *访问楼层
     */
    private String visitFloor;
    /***
     *访问开始时间
     */
    private long visitStartTime;
    /***
     *访问结束时间
     */
    private long visitEndTime;
    /***
     *访问有效次数
     */
    private int effecNumber;
    /***
     *拜访来源(1 有人值守访客机、 2自助访客机、3 微信公众号、4第三方平台、5APP、6 微信小程序)
     */
    private int fromType;
    /***
     *访问类型（1 邀请、2 预约）
     */
    private int visitType;
    /***
     *被访人用户手机号码
     */
    private String visitUserTelephone;
    /***
     *被访人微信id
     */
    private String visitorUserWxId;
    /***
     *被访人用户名称
     */
    private String visitUserName;
    /***
     *备注
     */
    private String remark;
    /***
     *是否强制取消审批，默认不取消（1 取消，0 不取消）
     */
    private String cancelApproval;
    /***
     *是否强制取消现场认证，默认不取消（1 取消，0 不取消）
     */
    private String cancelCertificate;
    /***
     *访客车牌号
     */
    private String carNO;
    /***
     *审批类型:1访客 2 车位
     */
    private String approveType;
    /***
     * 审批状态:0 未审批、1 审批 2 拒绝、3 不审批
     */
    private String approveStatus;
    /***
     *审批备注
     */
    private String approveRemark;
    /***
     *黑白名单原因
     */
    private String reason;
    /***
     *状态（1 正常，2 需要审批，3 现场认证）
     */
    private String visitStatus;
    /***
     *黑白名单类型（0 黑，1 白）
     */
    private String visitorType;
    /***
     *访客二维码数据
     */
    private String qrcodeKey;
    /***
     *访客二维码 id
     */
    private String codeId;
    /***
     *访客码
     */
    private String visitorCode;
    /***
     * 访客姓名
     */
    private String visitorName;
    /***
     *访客电话号码
     */
    private String visitorTelephone;
    /***
     *访客微信id
     */
    private String visitorWxId;

    /***
     *领码状态（1 未领码、2 已领码）
     */
    private String printStatus;

    /***
     *审批时间
     */
    private long approveTime;

    /***
     *发码时间
     */
    private long printTime;

    /***
     *审批状态（0，待审批 1 预约失败，2 预约拒绝，3 预约成功）
     */
    private String parkingStatus;

    /**
     * 生成时间
     * */
    private long createTime;
    /**
     * 查询已经审批的
     * */
    private String visitorApprove;

    /****
     * 访客记录
     */
    private List<VisitorInfo> visitorMsg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitDepartCode() {
        return visitDepartCode;
    }

    public void setVisitDepartCode(String visitDepartCode) {
        this.visitDepartCode = visitDepartCode;
    }

    public String getVisitDepartName() {
        return visitDepartName;
    }

    public void setVisitDepartName(String visitDepartName) {
        this.visitDepartName = visitDepartName;
    }

    public String getVisitPurpose() {
        return visitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }

    public int getBaseFloor() {
        return baseFloor;
    }

    public void setBaseFloor(int baseFloor) {
        this.baseFloor = baseFloor;
    }

    public String getVisitFloor() {
        return visitFloor;
    }

    public void setVisitFloor(String visitFloor) {
        this.visitFloor = visitFloor;
    }

    public long getVisitStartTime() {
        return visitStartTime;
    }

    public void setVisitStartTime(long visitStartTime) {
        this.visitStartTime = visitStartTime;
    }

    public long getVisitEndTime() {
        return visitEndTime;
    }

    public void setVisitEndTime(long visitEndTime) {
        this.visitEndTime = visitEndTime;
    }

    public int getEffecNumber() {
        return effecNumber;
    }

    public void setEffecNumber(int effecNumber) {
        this.effecNumber = effecNumber;
    }

    public int getFromType() {
        return fromType;
    }

    public void setFromType(int fromType) {
        this.fromType = fromType;
    }

    public int getVisitType() {
        return visitType;
    }

    public void setVisitType(int visitType) {
        this.visitType = visitType;
    }

    public String getVisitUserTelephone() {
        return visitUserTelephone;
    }

    public void setVisitUserTelephone(String visitUserTelephone) {
        this.visitUserTelephone = visitUserTelephone;
    }

    public String getVisitorUserWxId() {
        return visitorUserWxId;
    }

    public void setVisitorUserWxId(String visitorUserWxId) {
        this.visitorUserWxId = visitorUserWxId;
    }

    public String getVisitUserName() {
        return visitUserName;
    }

    public void setVisitUserName(String visitUserName) {
        this.visitUserName = visitUserName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCancelApproval() {
        return cancelApproval;
    }

    public void setCancelApproval(String cancelApproval) {
        this.cancelApproval = cancelApproval;
    }

    public String getCancelCertificate() {
        return cancelCertificate;
    }

    public void setCancelCertificate(String cancelCertificate) {
        this.cancelCertificate = cancelCertificate;
    }

    public String getCarNO() {
        return carNO;
    }

    public void setCarNO(String carNO) {
        this.carNO = carNO;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(String visitStatus) {
        this.visitStatus = visitStatus;
    }

    public String getVisitorType() {
        return visitorType;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }

    public String getQrcodeKey() {
        return qrcodeKey;
    }

    public void setQrcodeKey(String qrcodeKey) {
        this.qrcodeKey = qrcodeKey;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getVisitorCode() {
        return visitorCode;
    }

    public void setVisitorCode(String visitorCode) {
        this.visitorCode = visitorCode;
    }

    public List<VisitorInfo> getVisitorMsg() {
        return visitorMsg;
    }

    public void setVisitorMsg(List<VisitorInfo> visitorMsg) {
        this.visitorMsg = visitorMsg;
    }

    public String getVisitorTelephone() {
        return visitorTelephone;
    }

    public void setVisitorTelephone(String visitorTelephone) {
        this.visitorTelephone = visitorTelephone;
    }

    public String getVisitorWxId() {
        return visitorWxId;
    }

    public void setVisitorWxId(String visitorWxId) {
        this.visitorWxId = visitorWxId;
    }

    public String getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(String printStatus) {
        this.printStatus = printStatus;
    }

    public long getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(long approveTime) {
        this.approveTime = approveTime;
    }

    public long getPrintTime() {
        return printTime;
    }

    public void setPrintTime(long printTime) {
        this.printTime = printTime;
    }

    public String getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(String parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getVisitorApprove() {
        return visitorApprove;
    }

    public void setVisitorApprove(String visitorApprove) {
        this.visitorApprove = visitorApprove;
    }
}
