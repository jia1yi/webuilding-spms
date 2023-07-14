package com.webuilding.service;

import com.alibaba.fastjson2.JSONObject;
import com.webuilding.entity.VisitorBook;
import com.webuilding.exceprion.ApiHttpException;

/**
 * 调用第三方api服务
 */
public interface IApiService
{
    /**
     * 令令查询用户信息
     *
     * @param dataType 数据类型（1 用户 id，2 用户手机号码，3 用户工号）
     * @param dataValue 数据
     * @return 返回参数
     */
    public JSONObject queryUser(String dataType, String dataValue) throws ApiHttpException;
    public JSONObject queryUser(JSONObject json) throws ApiHttpException;

    /**
     * 令令查 远程开门
     *
     * @param dataType 数据类型（1 设备 id，2设备串码）
     * @param dataValue 数据
     * @return 返回参数
     */
    public JSONObject remoteDoor(String dataType, String dataValue) throws ApiHttpException;
    public JSONObject remoteDoor(JSONObject json) throws ApiHttpException;

    /**
     * 令令 查询组织机构
     *
     * @param dataType 据类型：1 代表 id，2 代表 code
     * @param data 数据
     * @param isChild 否 是否查询子级组织机构，默认不查询（0 否，1 是）
     *
     * @return 返回参数
     */
    public JSONObject queryDepart(String dataType, String data,String isChild) throws ApiHttpException;
    public JSONObject queryDepart(JSONObject json) throws ApiHttpException;

    /**
     * 令令 编辑用户
     *
     * @param userJson 令令请求的用户参数数据
     *
     * @return 返回参数
     */
    public JSONObject editUser(JSONObject userJson) throws ApiHttpException;

    /**
     * 令令 上传图片
     *
     * @param uploadJson 令令请求的上传图片参数
     *
     * @return 返回参数
     */
    public JSONObject uploadImg(JSONObject uploadJson) throws ApiHttpException;

    /**
     * 令令 访客预约登记
     *
     * @param booksJson 令令请求的用户参数数据
     *
     * @return 返回参数
     */
    public JSONObject visitorBook(JSONObject booksJson) throws ApiHttpException;

    /**
     * 令令 业主预约审核
     *
     * @param visitorRecordId 访客记录id
     * @param status 审批状态 （0 未审批、1 拒绝、2 通过、3 不审批)）
     * @param remark 备注
     *
     * @return 返回参数
     */
    public JSONObject visitExamine(String visitorRecordId,String status,String remark) throws ApiHttpException;
    public JSONObject visitExamine(JSONObject json) throws ApiHttpException;

    /**
     * 令令 查询预约记录
     *
     * @param id 访客记录id
     *
     * @return 返回参数
     */
    public JSONObject queryVisitorRecordById(String id) throws ApiHttpException;
    public JSONObject queryVisitorRecordById(JSONObject json) throws ApiHttpException;

    /**
     * 令令 获取用户二维码
     *
     * @param dataType 访客记录id
     * @param dataValue 访客记录id
     * @param startTime 访客记录id
     * @param endTime 访客记录id
     *
     * @return 返回参数
     */
    public JSONObject getUserQrcode(String dataType,String dataValue,long startTime,long endTime) throws ApiHttpException;
    public JSONObject getUserQrcode(JSONObject json) throws ApiHttpException;

    /**
     * 根据手机号 获取验证码信息
     *
     * @param phone 数据类型（1 用户 id，2 用户手机号码，3 用户工号）
     * @return 返回参数
     */
    public String getVerificationCode(String phone) throws ApiHttpException;

    /***
     * 预约/邀约 完成
     * **/
    public boolean reserveSuccessSendMessage(VisitorBook bookInfo)throws ApiHttpException;

    /***
     * 审核完成
     * **/
    public boolean approveSuccessSendMessage(VisitorBook bookInfo)throws ApiHttpException;

}
