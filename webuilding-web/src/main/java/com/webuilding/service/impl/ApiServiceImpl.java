package com.webuilding.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.webuilding.common.CodeEnum;
import com.webuilding.common.CommonConfig;
import com.webuilding.common.Constant;
import com.webuilding.entity.VisitorBook;
import com.webuilding.exceprion.ApiHttpException;
import com.webuilding.util.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.webuilding.service.IApiService;
import com.webuilding.util.HttpClientUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 调用第三方api服务
 * 
 */
@Service
public class ApiServiceImpl implements IApiService{
    private final Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);
    @Autowired
    protected CommonConfig commonConfig;
    /**
     * 令令查询用户信息
     *queryVisitorRecordById
     * @param dataType 数据类型（1 用户 id，2 用户手机号码，3 用户工号）
     * @param dataValue 数据
     * @return 返回参数
     */
    public JSONObject queryUser(String dataType, String dataValue) throws ApiHttpException{
        //请求参数
        JSONObject params = new JSONObject();
        params.put("dataType",dataType);
        JSONArray data = new JSONArray();
        data.add(dataValue);
        params.put("dataValue",data);
        return queryUser(params);
    }
    public JSONObject queryUser(JSONObject params) throws ApiHttpException{
        //构造令令参数结构
        List<NameValuePair> lingParams = getLingHeader(params);
        String url = commonConfig.getLingUrl() + Constant.LING_URL_QUERY_USER + commonConfig.getLingOpenToken();//请求地址
        try{
            JSONObject result = HttpClientUtils.sendPost(url,lingParams);
            //记录日志
            httpResultLogInfo("查询用户信息",url,params.toJSONString(),result.toJSONString());
            return result;
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("调用查询用户信息接口失败",url,params.toJSONString(),e.getMessage());
            throw new ApiHttpException(CodeEnum.LINGLING_ERR.getCode(),CodeEnum.LINGLING_ERR.getMsg());
        }
    }
    /**
     * 令令查 远程开门
     *
     * @param dataType 数据类型（1 设备 id，2设备串码）
     * @param dataValue 数据
     * @return 返回参数
     */
    public JSONObject remoteDoor(String dataType, String dataValue) throws ApiHttpException{
        //请求参数
        JSONObject params = new JSONObject();
        params.put("dataType",dataType);
        JSONArray data = new JSONArray();
        data.add(dataValue);
        params.put("dataValue",data);
        return remoteDoor(params);
    }
    public JSONObject remoteDoor(JSONObject params) throws ApiHttpException{
        //构造令令参数结构
        List<NameValuePair> lingParams = getLingHeader(params);
        String url = commonConfig.getLingUrl() + Constant.LING_URL_REMOTEDOOR + commonConfig.getLingOpenToken();//请求地址
        try{
            JSONObject result = HttpClientUtils.sendPost(url,lingParams);
            //记录日志
            httpResultLogInfo("远程开门",url,params.toJSONString(),result.toJSONString());
            return result;
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("调用远程开门接口失败",url,params.toJSONString(),e.getMessage());
            throw new ApiHttpException(CodeEnum.LINGLING_ERR.getCode(),CodeEnum.LINGLING_ERR.getMsg());
        }
    }
    /**
     * 令令 查询组织机构
     *
     * @param dataType 据类型：1 代表 id，2 代表 code
     * @param data 数据
     * @param isChild 否 是否查询子级组织机构，默认不查询（0 否，1 是）
     *
     * @return 返回参数
     */
    public JSONObject queryDepart(String dataType, String data,String isChild) throws ApiHttpException{
        //请求参数
        JSONObject params = new JSONObject();
        params.put("dataType",dataType);
        params.put("data",data);
        params.put("isChild",isChild);
        //构造令令参数结构
        return queryDepart(params);
    }
    public JSONObject queryDepart(JSONObject params) throws ApiHttpException{
        //构造令令参数结构
        List<NameValuePair> lingParams = getLingHeader(params);
        String url = commonConfig.getLingUrl() + Constant.LING_URL_QUERY_DEPART + commonConfig.getLingOpenToken();//请求地址
        try{
            JSONObject result = HttpClientUtils.sendPost(url,lingParams);
            //记录日志
            httpResultLogInfo("查询组织机构信息",url,params.toJSONString(),result.toJSONString());

            return result;
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("调用查询组织机构信息失败",url,params.toJSONString(),e.getMessage());
            throw new ApiHttpException(CodeEnum.LINGLING_ERR.getCode(),CodeEnum.LINGLING_ERR.getMsg());
        }
    }
    /**
     * 令令 编辑用户
     *
     * @param userJson 令令请求的用户参数数据
     *
     * @return 返回参数
     */
    public JSONObject editUser(JSONObject userJson) throws ApiHttpException{
        //构造令令参数结构
        List<NameValuePair> lingParams = getLingHeader(userJson);
        String url = commonConfig.getLingUrl() + Constant.LING_URL_EDIT_USER + commonConfig.getLingOpenToken();//请求地址
        try{
            JSONObject result = HttpClientUtils.sendPost(url,lingParams);
            //记录日志
            httpResultLogInfo("编辑用户信息",url,userJson.toJSONString(),result.toJSONString());

            return result;
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("编辑用户失败",url,userJson.toJSONString(),e.getMessage());
            throw new ApiHttpException(CodeEnum.LINGLING_ERR.getCode(),CodeEnum.LINGLING_ERR.getMsg());
        }
    }
    /**
     * 令令 上传图片
     *
     * @param uploadJson 令令请求的上传图片参数
     *
     * @return 返回参数
     */
    public JSONObject uploadImg(JSONObject uploadJson) throws ApiHttpException{
        //构造令令参数结构
        List<NameValuePair> lingParams = getLingHeader(uploadJson);
        String url = commonConfig.getLingUrl() + Constant.LING_URL_UPLOAD_IMG + commonConfig.getLingOpenToken();//请求地址
        try{
            JSONObject result = HttpClientUtils.sendPost(url,lingParams);
            //记录日志
            httpResultLogInfo("上传图片",url,uploadJson.toJSONString(),result.toJSONString());
            return result;
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("上传图片失败",url,uploadJson.toJSONString(),e.getMessage());
            throw new ApiHttpException(CodeEnum.LINGLING_ERR.getCode(),CodeEnum.LINGLING_ERR.getMsg());
        }
    }
    /**
     * 令令 访客预约登记
     *
     * @param booksJson 令令请求的用户参数数据
     *
     * @return 返回参数
     */
    public JSONObject visitorBook(JSONObject booksJson) throws ApiHttpException{
        //构造令令参数结构
        List<NameValuePair> lingParams = getLingHeader(booksJson);
        String url = commonConfig.getLingUrl() + Constant.LING_URL_VISITOR_BOOK + commonConfig.getLingOpenToken();//请求地址
        try{
            JSONObject result = HttpClientUtils.sendPost(url,lingParams);
            //记录日志
            httpResultLogInfo("访客预约",url,booksJson.toJSONString(),result.toJSONString());
            return result;
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("访客预约失败",url,booksJson.toJSONString(),e.getMessage());
            throw new ApiHttpException(CodeEnum.LINGLING_ERR.getCode(),CodeEnum.LINGLING_ERR.getMsg());
        }
    }
    /**
     * 令令 业主预约审核
     *
     * @param visitorRecordId 访客记录id
     * @param status 审批状态 （0 未审批、1 拒绝、2 通过、3 不审批)）
     * @param remark 备注
     *
     * @return 返回参数
     */
    public JSONObject visitExamine(String visitorRecordId,String status,String remark) throws ApiHttpException{
        //请求参数
        JSONObject params = new JSONObject();
        params.put("visitorRecordId",visitorRecordId);
        params.put("status",status);
        params.put("remark",remark);
        return visitExamine(params);
    }
    public JSONObject visitExamine(JSONObject params) throws ApiHttpException{
        //构造令令参数结构
        List<NameValuePair> lingParams = getLingHeader(params);
        String url = commonConfig.getLingUrl() + Constant.LING_URL_VISIT_EXAMINE + commonConfig.getLingOpenToken();//请求地址
        try{
            JSONObject result = HttpClientUtils.sendPost(url,lingParams);
            //记录日志
            httpResultLogInfo("业主预约审核",url,params.toJSONString(),result.toJSONString());
            return result;
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("业主预约审核失败",url,params.toJSONString(),e.getMessage());
            throw new ApiHttpException(CodeEnum.LINGLING_ERR.getCode(),CodeEnum.LINGLING_ERR.getMsg());
        }
    }
    /**
     * 令令 查询预约记录
     *
     * @param id 访客记录id
     *
     * @return 返回参数
     */
    public JSONObject queryVisitorRecordById(String id) throws ApiHttpException{
        //请求参数
        JSONObject params = new JSONObject();
        params.put("id",id);
        return queryVisitorRecordById(params);
    }
    public JSONObject queryVisitorRecordById(JSONObject params) throws ApiHttpException{
        //构造令令参数结构
        List<NameValuePair> lingParams = getLingHeader(params);
        String url = commonConfig.getLingUrl() + Constant.LING_URL_QUERY_RECORD_ID + commonConfig.getLingOpenToken();//请求地址
        try{
            JSONObject result = HttpClientUtils.sendPost(url,lingParams);
            //记录日志
            httpResultLogInfo("查询预约记录",url,params.toJSONString(),result.toJSONString());
            return result;
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("查询预约记录失败",url,params.toJSONString(),e.getMessage());
            throw new ApiHttpException(CodeEnum.LINGLING_ERR.getCode(),CodeEnum.LINGLING_ERR.getMsg());
        }
    }
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
    public JSONObject getUserQrcode(String dataType,String dataValue,long startTime,long endTime) throws ApiHttpException{
        //请求参数
        JSONObject params = new JSONObject();
        params.put("dataType",dataType);
        params.put("dataValue",dataValue);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        return getUserQrcode(params);
    }
    public JSONObject getUserQrcode(JSONObject params) throws ApiHttpException{
        //构造令令参数结构
        List<NameValuePair> lingParams = getLingHeader(params);
        String url = commonConfig.getLingUrl() + Constant.LING_URL_GET_USER_QRCODE + commonConfig.getLingOpenToken();//请求地址
        try{
            JSONObject result = HttpClientUtils.sendPost(url,lingParams);
            //记录日志
            httpResultLogInfo("获取用户二维码",url,params.toJSONString(),result.toJSONString());
            return result;
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("获取用户二维码失败",url,params.toJSONString(),e.getMessage());
            throw new ApiHttpException(CodeEnum.LINGLING_ERR.getCode(),CodeEnum.LINGLING_ERR.getMsg());
        }
    }
    /**
     * 返回令令请求数据格式
     * ***/
    private List<NameValuePair> getLingHeader(JSONObject params){
        List<NameValuePair> nameValuePairList = new ArrayList<>();

        JSONObject header = new JSONObject();
        header.put("signature",commonConfig.getLingSignature());
        header.put("token",commonConfig.getLingToken());

        JSONObject message = new JSONObject();
        message.put("requestParam",params);
        message.put("header",header);

        nameValuePairList.add(new BasicNameValuePair("MESSAGE",message.toJSONString()));
        return nameValuePairList;
    }
    /**
     * 根据手机号 获取验证码信息
     *
     * @param phone 数据类型（1 用户 id，2 用户手机号码，3 用户工号）
     * @return 返回参数
     */
    public String getVerificationCode(String phone) throws ApiHttpException{

        String url = commonConfig.getWeixinUrl() + Constant.WEIXIN_URL_VERIFICATION_CODE + "?phone="+phone;//请求地址
        try{
            JSONObject result = HttpClientUtils.sendGet(url);
            //记录日志
            httpResultLogInfo("查询验证码信息",url,"",result.toJSONString());

            if(result.getIntValue("code") == 0){
                return result.getString("value");
            }
            return null;
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("验证码查询失败",url,"",e.getMessage());
            throw new ApiHttpException(CodeEnum.WEIXIN_ERR.getCode(),CodeEnum.WEIXIN_ERR.getMsg());
        }
    }
    /***
     * 预约/邀约 完成
     * **/
    public boolean reserveSuccessSendMessage(VisitorBook bookInfo)throws ApiHttpException{
        if(bookInfo.getVisitType() == 1){//邀约
            //发送短信(开锁二维码)
            return sendPhoneMessage(bookInfo);
        }else{
            if(!sendWechatMessageAppointmentApproval(bookInfo)){//推送消息失败
                return sendPhoneMessageAppointmentApproval(bookInfo);
            }
        }
        return true;
    }
    /**
     * 发送开锁二维码
     * ***/
    private boolean sendPhoneMessage(VisitorBook bookInfo){
        //访客手机号
        String phones = bookInfo.getVisitorTelephone();
        //预约id
        String id = bookInfo.getVisitorId();
        //公司名称
        String companyName = bookInfo.getVisitDepartName();
        if(StringUtils.isEmpty(phones) || StringUtils.isEmpty(id)){//手机号或者id为空
            logger.error("发送开锁二维码失败，手机号：{},id：{}",phones,id);
            return false;
        }
        String url = commonConfig.getWeixinUrl() + Constant.WEIXIN_URL_SEND_PHONE_MESSAGE + "?phones="+phones+"&id="+ new String(Base64.encodeBase64(id.getBytes())) +"&companyName=";//请求地址
        logger.info("发送开锁短信，{}",url);
        try{
            url  = url + URLEncoder.encode(companyName, "utf-8");//请求地址
            JSONObject result = HttpClientUtils.sendGet(url);
            //记录日志
            httpResultLogInfo("发送开锁二维码成功",url,"",result.toJSONString());
            if(result.getIntValue("code") == 0){
                return true;
            }
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("发送开锁二维码失败",url,"",e.getMessage());
        }
        return false;
    }
    /**
     * 推送审批通知
     * ***/
    private boolean sendWechatMessageAppointmentApproval(VisitorBook bookInfo){
        if(StringUtils.isEmpty(bookInfo.getVisitorUserWxId())){//被访问者微信id为空
            return false;
        }
        //构造审批消息体
        JSONObject params = new JSONObject();
        params.put("companyName",bookInfo.getVisitDepartName());//公司名称
        params.put("appointmentTime",bookInfo.getCreateTime());//预约时间
        params.put("owner",bookInfo.getVisitUserName());//业主
        params.put("reason",bookInfo.getVisitPurpose());//到访原因
        params.put("visitorName",bookInfo.getVisitorName());//访问者
        params.put("visitorPhone",bookInfo.getVisitorTelephone());//访问者电话
        params.put("appointId",bookInfo.getVisitorId());//预约id
        params.put("openUrl","");//开锁链接
        params.put("wechatId",bookInfo.getVisitorUserWxId());//微信id

        String url = commonConfig.getWeixinUrl() + Constant.WEIXIN_URL_SEND_WECHAT_APPROVAL;
        try{
            JSONObject result = HttpClientUtils.sendPost(url,params.toJSONString());
            //记录日志
            httpResultLogInfo("推送预约审批通知",url,params.toJSONString(),result.toJSONString());

            if(result.getIntValue("code") == 0){
                return true;
            }
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("推送预约审批通知失败",url,params.toJSONString(),e.getMessage());
        }
        return false;
    }
    /**
     * 发送审批短信
     * ***/
    private boolean sendPhoneMessageAppointmentApproval(VisitorBook bookInfo){
        //访客手机号
        String phone = bookInfo.getVisitUserTelephone();
        //预约id
        String id = bookInfo.getVisitorId();
        //公司名称
        String companyName = bookInfo.getVisitDepartName();
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(id)){//手机号或者id为空
            logger.error("发送审批短信失败，手机号：{},id：{}",phone,id);
            return false;
        }
        String url = commonConfig.getWeixinUrl() + Constant.WEIXIN_URL_SEND_PHONE_APPROVAL + "?phone="+phone+"&id="+new String(Base64.encodeBase64(id.getBytes()))+"&companyName=";//请求地址
        try{
            url = url + URLEncoder.encode(companyName, "utf-8");//请求地址
            JSONObject result = HttpClientUtils.sendGet(url);
            //记录日志
            httpResultLogInfo("发送审批短信成功",url,"",result.toJSONString());

            if(result.getIntValue("code") == 0){
                return true;
            }
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("发送审批短信失败",url,"",e.getMessage());
        }
        return false;
    }
    /***
     * 审核完成approveSuccessSendMessage
     * **/
    public boolean approveSuccessSendMessage(VisitorBook bookInfo)throws ApiHttpException{
        logger.info("审核回调：visitorId:{};approveStatus:{}",bookInfo.getVisitorId(),bookInfo.getApproveStatus());

        if(StringUtils.isNotEmpty(bookInfo.getApproveStatus()) && bookInfo.getApproveStatus().equals("2")){//通过
            sendPhoneMessage(bookInfo);
            return sendWechatMessageApprovalPass(bookInfo);
        }else if(StringUtils.isNotEmpty(bookInfo.getApproveStatus()) && bookInfo.getApproveStatus().equals("3")){//邀约
            sendPhoneMessage(bookInfo);
            return sendWechatMessageApprovalPass(bookInfo);
        }else if(StringUtils.isNotEmpty(bookInfo.getApproveStatus()) && bookInfo.getApproveStatus().equals("1")){//拒绝
            return sendWechatMessageApprovalRefuse(bookInfo);
        }

        return false;
    }
    /**
     * 推送审批通过消息
     * ***/
    private boolean sendWechatMessageApprovalPass(VisitorBook bookInfo){
        if(StringUtils.isEmpty(bookInfo.getVisitorWxId())){//访问者微信id为空
            return false;
        }
        //构造审批消息体
        JSONObject params = new JSONObject();
        params.put("companyName",bookInfo.getVisitDepartName());//公司名称
        params.put("appointmentTime",bookInfo.getCreateTime());//预约时间
        params.put("owner",bookInfo.getVisitUserName());//业主
        params.put("reason",bookInfo.getVisitPurpose());//到访原因
        if(StringUtils.isEmpty(bookInfo.getVisitFloor())){
            params.put("floor",bookInfo.getBaseFloor());//楼层
        }else{
            params.put("floor",bookInfo.getVisitFloor());//楼层
        }
        params.put("appointId",bookInfo.getVisitorId());//预约id
        params.put("openUrl","");//开锁链接
        params.put("wechatId",bookInfo.getVisitorWxId());//微信id

        String url = commonConfig.getWeixinUrl() + Constant.WEIXIN_URL_SEND_WECHAT_APPROVAL_PASS;
        try{
            JSONObject result = HttpClientUtils.sendPost(url,params.toJSONString());
            //记录日志
            httpResultLogInfo("推送审批通过消息",url,params.toJSONString(),result.toJSONString());

            if(result.getIntValue("code") == 0){
                return true;
            }
        }catch (Exception e){
            //记录错误信息
            httpResultLogError("推送审批通过消息失败",url,"",e.getMessage());
        }
        return false;
    }
    /**
     * 推送审批拒绝消息
     * ***/
    private boolean sendWechatMessageApprovalRefuse(VisitorBook bookInfo){
        if(StringUtils.isEmpty(bookInfo.getVisitorWxId())){//访问者微信id为空
            return false;
        }
        //构造审批消息体
        JSONObject params = new JSONObject();
        params.put("companyName",bookInfo.getVisitDepartName());//公司名称
        params.put("appointmentTime",bookInfo.getCreateTime());//预约时间
        params.put("owner",bookInfo.getVisitUserName());//业主
        params.put("appointId",bookInfo.getVisitorId());//预约id
        params.put("openUrl","");//开锁链接
        params.put("wechatId",bookInfo.getVisitorWxId());//微信id
        params.put("refuseStr",bookInfo.getApproveRemark());//拒绝原因

        String url = commonConfig.getWeixinUrl() + Constant.WEIXIN_URL_SEND_WECHAT_APPROVAL_REFUSE;
        try{
            JSONObject result = HttpClientUtils.sendPost(url,params.toJSONString());
            //记录日志
            httpResultLogInfo("推送审批拒绝消息",url,params.toJSONString(),result.toJSONString());
            if(result.getIntValue("code") == 0){
                return true;
            }
        }catch (Exception e){
            //记录错误信息approveSuccessSendMessage
            httpResultLogError("推送审批拒绝消息失败",url,"",e.getMessage());
        }
        return false;
    }
    /***
     * 记录请求返回值
     * ****/
    private void httpResultLogInfo(String title,String url,String params,String result){
        if(commonConfig.isApiDebug()){//是否开启日志
            logger.info(title);
            logger.info("url:{}",url);
            logger.info("参数:{}",params);
            logger.info("返回信息：{}",result);
        }
    }
    /***
     *记录请求错误信息
     *  **/
    private void httpResultLogError(String title,String url,String params,String errMessage){
        logger.error(title);
        logger.error("url:{}",url);
        logger.error("参数:{}",params);
        logger.error("错误信息：{}",errMessage);
    }
}
