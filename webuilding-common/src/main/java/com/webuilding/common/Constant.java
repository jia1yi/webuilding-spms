package com.webuilding.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 常量
 */
public class Constant {
    public static Set<String>  METHOD_URL_SET = Sets.newConcurrentHashSet();

    /***令令查询用户接口**/
    public static String LING_URL_QUERY_USER = "/developerCommonUserApi/queryUser/";
    /***令令查询组织机构接口**/
    public static String LING_URL_QUERY_DEPART = "/developerDepartApi/queryDepart/";
    /***令令编辑用户接口**/
    public static String LING_URL_EDIT_USER = "/developerCommonUserApi/editUser/";
    /***令令访客预约接口**/
    public static String LING_URL_VISITOR_BOOK = "/developerVisitorApi/visitorBook/";
    /***令令业主审批访客接口**/
    public static String LING_URL_VISIT_EXAMINE = "/developerVisitorApi/visitExamine/";
    /***令令访客记录 id 查询详情接口**/
    public static String LING_URL_QUERY_RECORD_ID = "/developerVisitorApi/queryVisitorRecordById/";
    /***令令获取用户二维码接口**/
    public static String LING_URL_GET_USER_QRCODE = "/developerCommonUserApi/getUserQrcode/";
    /***令令远程开门接口**/
    public static String LING_URL_REMOTEDOOR = "/developerDeviceApi/remoteDoor/";
    /***令令上传图片接口**/
    public static String LING_URL_UPLOAD_IMG = "/developerVisitorApi/uploadImg/";

    /***根据手机号查询验证码**/
    public static String WEIXIN_URL_VERIFICATION_CODE = "/infinova_visitor/sign/visitor/getVerificationCodeByPhone";
    /***开锁短信**/
    public static String WEIXIN_URL_SEND_PHONE_MESSAGE= "/infinova_visitor/sign/visitor/sendPhoneMessage";
    /***发送预约审批的短信**/
    public static String WEIXIN_URL_SEND_PHONE_APPROVAL= "/infinova_visitor/sign/visitor/sendPhoneMessageAppointmentApproval";
    /***发送预约审批的消息**/
    public static String WEIXIN_URL_SEND_WECHAT_APPROVAL= "/infinova_visitor/sign/visitor/sendWechatMessageAppointmentApproval";
    /***微信推送消息接口(post)审批通过提醒**/
    public static String WEIXIN_URL_SEND_WECHAT_APPROVAL_PASS= "/infinova_visitor/sign/visitor/sendWechatMessageApprovalPass";
    /***微信推送消息接口(post)预约拒绝通知**/
    public static String WEIXIN_URL_SEND_WECHAT_APPROVAL_REFUSE= "/infinova_visitor/sign/visitor/sendWechatMessageApprovalRefuse";
}
