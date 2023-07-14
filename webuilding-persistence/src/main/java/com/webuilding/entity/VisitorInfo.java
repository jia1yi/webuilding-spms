package com.webuilding.entity;

import java.io.Serializable;

/**
 * 访客信息
 */
public class VisitorInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     *  主键
     */
    private int id;
    /**
     * 预约记录id
     */
    private String visitorId;
    /**
     * 访客证件类型，默认身份证（1身份证）
     */
    private String IDType;
    /**
     * 访客姓名
     */
    private String name;
    /***
     *访客性别（1 男，2 女）
     */
    private String gender;
    /***
     *访客民族
     */
    private String race;
    /***
     *访客生日
     */
    private String birthday;
    /***
     *访客地址
     */
    private String census_addr;
    /***
     *访客证件号码
     */
    private String iden_number;
    /***
     *访客发证机关
     */
    private String police;
    /***
     *访客证件有效起始时间
     */
    private String active_from;
    /***
     *访客证件有效结束时间
     */
    private String active_to;
    /***
     *访客证件卡号
     */
    private String card_id;
    /***
     *访客证件头像
     */
    private String head_img_name;
    /***
     *访客电话号码
     */
    private String visitorTelephone;
    /***
     *访客微信id
     */
    private String visitorWxId;
    /***
     *访客车牌号
     */
    private String carNO;

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

    public String getIDType() {
        return IDType;
    }

    public void setIDType(String IDType) {
        this.IDType = IDType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCensus_addr() {
        return census_addr;
    }

    public void setCensus_addr(String census_addr) {
        this.census_addr = census_addr;
    }

    public String getIden_number() {
        return iden_number;
    }

    public void setIden_number(String iden_number) {
        this.iden_number = iden_number;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public String getActive_from() {
        return active_from;
    }

    public void setActive_from(String active_from) {
        this.active_from = active_from;
    }

    public String getActive_to() {
        return active_to;
    }

    public void setActive_to(String active_to) {
        this.active_to = active_to;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getHead_img_name() {
        return head_img_name;
    }

    public void setHead_img_name(String head_img_name) {
        this.head_img_name = head_img_name;
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

    public String getCarNO() {
        return carNO;
    }

    public void setCarNO(String carNO) {
        this.carNO = carNO;
    }
}
