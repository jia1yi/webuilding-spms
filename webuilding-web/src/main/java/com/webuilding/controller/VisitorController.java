package com.webuilding.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.webuilding.annotation.Log;
import com.webuilding.annotation.Pass;
import com.webuilding.common.AjaxResult;
import com.webuilding.common.CodeEnum;
import com.webuilding.common.CommonConfig;
import com.webuilding.entity.LoginUser;
import com.webuilding.entity.VisitorBook;
import com.webuilding.exceprion.ParamJsonException;
import com.webuilding.rsa.RsaRequest;
import com.webuilding.annotation.TokenVerify;
import com.webuilding.service.IApiService;
import com.webuilding.service.ILoginUserService;
import com.webuilding.service.IVisitorBookService;
import com.webuilding.util.LingApiUtils;
import com.webuilding.util.PageUtil;
import com.webuilding.util.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  登录接口
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController extends BaseController{

    @Autowired
    IApiService apiService;
    @Autowired
    protected CommonConfig commonConfig;
    @Autowired
    IVisitorBookService visitorBookService;
    @Autowired
    protected ILoginUserService loginUserService;

    @GetMapping("/querySimpleUser")
    @Log(action="querySimpleUser",modelName= "querySimpleUser",description="根据手机号判断用户是否为业主")
    @RsaRequest(result=true,param =false)
    /*@TokenVerify*/
    public AjaxResult querySimpleUser(@RequestParam(name = "visitorUserTel", required = true) String visitorUserTel) throws Exception{
        String userName = "";//用户姓名
        String departCode = "";//用户组织机构编码
        String departName = "";//用户组织机构
        //查询令令用户基本信息
        JSONObject result = new JSONObject();
        JSONObject userResult = apiService.queryUser("2",visitorUserTel);
        //接口返回成功
        if(LingApiUtils.checkResultCode(userResult)){//请求成功
            //取用户
            JSONObject user = LingApiUtils.getUserObject(userResult);
            if(user != null){
                userName = user.getString("userName");
                departCode = user.getString("orgCode");
            }

            //获取组织名称
            JSONObject departResult = null;
            if(StringUtils.isNotEmpty(departCode)){
                departResult = apiService.queryDepart("2",departCode,null);
            }
            //取用户
            JSONObject depart = LingApiUtils.getDepartObject(departResult);
            if(depart != null){
                departName = depart.getString("departName");
            }
            //设置返回数据
            result.put("userName",userName);
            result.put("departName",departName);
        }else {
            return AjaxResult.error(CodeEnum.IDENTIFICATION_ERROR.getCode(),CodeEnum.IDENTIFICATION_ERROR.getMsg(),"");
        }
        return AjaxResult.success("查询成功",result);
    }

    @GetMapping("/queryUser")
    @Log(action="queryUser",modelName= "queryUser",description="根据手机号获取用户信息")
    @RsaRequest(result=true,param =false)
    /*@TokenVerify*/
    public AjaxResult queryUser(@RequestParam(name = "visitorUserTel", required = true) String visitorUserTel) throws Exception{
        //查询令令用户基本信息
       JSONObject userResult = apiService.queryUser("2",visitorUserTel);
        if(LingApiUtils.checkResultCode(userResult)){//请求成功
            if(userResult.getJSONObject("responseResult") != null){
                JSONArray user = userResult.getJSONObject("responseResult").getJSONArray("user");
                JSONObject result = new JSONObject();
                result.put("user",user);
                return AjaxResult.success("查询成功",result);
            }
        }

        return AjaxResult.error("查询失败",userResult);
    }

    @PostMapping("/editUser")
    @Log(action="editUser",modelName= "editUser",description="编辑用户信息")
    @RsaRequest(result=true,param =false)
    public AjaxResult editUser(@RequestBody(required = true) JSONObject json) throws Exception{
        //令令编辑用户接口
        JSONObject result = apiService.editUser(json);
        if(LingApiUtils.checkResultCode(result)){//请求成功
            if(result.getJSONObject("responseResult") != null){
                return AjaxResult.success("修改成功",result.getJSONObject("responseResult"));
            }
        }
        return AjaxResult.error("修改失败",result);
    }

    @PostMapping("/uploadImg")
    //@Log(action="uploadImg",modelName= "uploadImg",description="上传图片")
    @RsaRequest(result=true,param =false)
    public AjaxResult uploadImg(@RequestBody(required = true) JSONObject json) throws Exception{
        //令令上传图片接口
        JSONObject result = apiService.uploadImg(json);
        if(LingApiUtils.checkResultCode(result)){//请求成功
            return AjaxResult.success("上传成功",result.getString("responseResult"));
        }
        return AjaxResult.error("上传失败",result);
    }

    @PostMapping("/remoteDoor")
    @Log(action="remoteDoor",modelName= "remoteDoor",description="远程开门")
    @RsaRequest(result=true,param =false)
    public AjaxResult remoteDoor(@RequestBody(required = true) JSONObject json) throws Exception{
        //令令远程开门
        JSONObject result = apiService.remoteDoor(json);
        if(LingApiUtils.checkResultCode(result)){//请求成功
            if(result.getJSONObject("responseResult") != null){
                return AjaxResult.success("远程开门成功",result.getJSONObject("responseResult"));
            }
        }
        return AjaxResult.error("远程开门失败",result);
    }

    @PostMapping("/reserve")
    @Log(action="reserve",modelName= "reserve",description="访客预约")
    @RsaRequest(result=true,param =false)
    public AjaxResult reserve(@RequestBody(required = true) JSONObject json) throws Exception{
        //获取预约信息
        VisitorBook bookInfo = LingApiUtils.getVisitorBookInfo(json);
        //调用令令 进行访客预约
        JSONObject result = apiService.visitorBook(json);
        if(LingApiUtils.checkResultCode(result)){//请求成功
            if(result.getJSONObject("responseResult") != null){
                //保存预约记录
                List<VisitorBook> bookList = LingApiUtils.getUpdateBookResult(bookInfo,result.getJSONObject("responseResult"));
                for(VisitorBook b : bookList){
                    //同步预约记录状态
                    b = synchBookState(false,b,apiService.queryVisitorRecordById(b.getVisitorId()));
                    visitorBookService.insertVisitorBook(b);

                    //预约成功发送消息
                    //apiService.reserveSuccessSendMessage(b);
                }

                return AjaxResult.success("预约成功",result.getJSONObject("responseResult"));
            }
        }
        return AjaxResult.error("预约失败",result);
    }

    @PostMapping("/approve")
    @Log(action="approve",modelName= "approve",description="访客审批")
    @Pass
    @RsaRequest(result=true,param =false)
    public AjaxResult approve(@RequestBody(required = true) JSONObject json) throws Exception{
        String visitorId = json.getString("visitorId");//访客记录id
        String approveType = json.getString("approveType");//审批类型： 1访客 2 车位
        String approveStatus = json.getString("approveStatus");//审批状态 1 审批 2 拒绝
        String remark = json.getString("remark");//审批备注
        if(StringUtils.isEmpty(visitorId) || StringUtils.isEmpty(approveType) || StringUtils.isEmpty(approveStatus)){
            throw new ParamJsonException();
        }
        //访客审批
        JSONObject result = apiService.visitExamine(visitorId,approveStatus,remark);
        if(LingApiUtils.checkResultCode(result)){//请求成功
            // TODO: 2020/11/6  这里回头做车位审批
            //更新数据库审批状态
            VisitorBook bookInfo = visitorBookService.selectVisitorBookByVisitorID(visitorId);
            if(bookInfo == null){
                LingApiUtils.getLinglingBookInfo(apiService.queryVisitorRecordById(visitorId));
            }else{
                bookInfo.setApproveType(approveType);
                bookInfo.setApproveStatus(approveStatus);
                bookInfo.setApproveRemark(remark);
                visitorBookService.updateVisitorBook(bookInfo);
                //这里在同步令令预约状态
                synchBookState(bookInfo,apiService.queryVisitorRecordById(visitorId));
            }

            //预约成功发送消息
            //apiService.approveSuccessSendMessage(bookInfo);
            return AjaxResult.success("审核成功",result.getString("responseResult"));
        }
        return AjaxResult.error("审核失败",result);
    }

    @GetMapping("/approveList")
    @Log(action="approveList",modelName= "approveList",description="根据手机号获取审批列表")
    @RsaRequest(result=true,param =false)
    @TokenVerify
    public AjaxResult approveList(@RequestParam(name = "currentPage", required = true) Integer currentPage,
                                  @RequestParam(name = "pageSize", required = true) Integer pageSize,
                                  @RequestParam(name = "telephone", required = true) String telephone,
                                  @RequestParam(name = "fromType", required = false,defaultValue = "0") Integer fromType,
                                  @RequestParam(name = "visitType", required = false,defaultValue = "0") Integer visitType,
                                  @RequestParam(name = "approveType", required = false) String approveType,
                                  @RequestParam(name = "approveStatus", required = false) String approveStatus,
                                  @RequestParam(name = "visitStatus", required = false) String visitStatus) throws Exception{
        //查询条件

        if(pageSize>commonConfig.getMaxPagesize()){
            return AjaxResult.error(CodeEnum.PAGESIZE_ERROR.getCode(),CodeEnum.PAGESIZE_ERROR.getMsg(),"");
        }
        if(StringUtils.isEmpty(telephone)){
            return AjaxResult.error(CodeEnum.PARAM_ERROR.getCode(),CodeEnum.PARAM_ERROR.getMsg(),"");
        }
        VisitorBook bookInfo = new VisitorBook();
        bookInfo.setVisitUserTelephone(telephone);
        bookInfo.setFromType(fromType);//拜访来源(1 有人值守访客机、 2自助访客机、3 微信公众号、4第三方平台、5APP、6 微信小程序)
        bookInfo.setVisitType(visitType);//访问类型（1 邀请、2 预约）
        bookInfo.setApproveType(approveType);//审批类型:1访客 2 车位
        if(StringUtils.isNotEmpty(approveStatus) && approveStatus.equals("9")){//查询已经审批的
            bookInfo.setVisitorApprove(approveStatus);//
        }else{
            bookInfo.setApproveStatus(approveStatus);//审批状态:0 未审批、1 审批 2 拒绝、3 不审批
        }
        bookInfo.setVisitStatus(visitStatus);//-状态（1 正常，2 需要审批，3 现场认证）-
        //总记录数
        int count = visitorBookService.selectCountVisitorBook(bookInfo);
        //构造分页信息
        PageUtil pageInfo = new PageUtil(currentPage, pageSize);
        pageInfo.setTotalCount(count);

        List<VisitorBook> list = visitorBookService.selectVisitorBookPage(bookInfo,pageInfo.getStart(),pageInfo.getPageSize());

        JSONObject result = new JSONObject();
        result.put("currentPage",pageInfo.getPageCurrent());//当前页码
        result.put("pageSize",pageInfo.getPageSize());//页面大小
        result.put("totalPage",pageInfo.getTotalPage());//总页数
        result.put("recordsTotal",pageInfo.getTotalCount());//总记录数
        result.put("data",LingApiUtils.changeBookInfoList(list));//数据

        return AjaxResult.success("查询成功",result);
    }

    @GetMapping("/reserveList")
    @Log(action="reserveList",modelName= "reserveList",description="根据手机号获取预约列表")
    @RsaRequest(result=true,param =false)
    @TokenVerify
    public AjaxResult reserveList(@RequestParam(name = "currentPage", required = true) Integer currentPage,
                                  @RequestParam(name = "pageSize", required = true) Integer pageSize,
                                  @RequestParam(name = "telephone", required = true) String telephone,
                                  @RequestParam(name = "fromType", required = false,defaultValue = "0") Integer fromType,
                                  @RequestParam(name = "visitType", required = false,defaultValue = "0") Integer visitType,
                                  @RequestParam(name = "approveType", required = false) String approveType,
                                  @RequestParam(name = "approveStatus", required = false) String approveStatus,
                                  @RequestParam(name = "visitStatus", required = false) String visitStatus) throws Exception{
        //查询条件

        if(pageSize>commonConfig.getMaxPagesize()){
           return AjaxResult.error(CodeEnum.PAGESIZE_ERROR.getCode(),CodeEnum.PAGESIZE_ERROR.getMsg(),"");
        }
        if(StringUtils.isEmpty(telephone)){
            return AjaxResult.error(CodeEnum.PARAM_ERROR.getCode(),CodeEnum.PARAM_ERROR.getMsg(),"");
        }
        VisitorBook bookInfo = new VisitorBook();
        bookInfo.setVisitorTelephone(telephone);
        bookInfo.setFromType(fromType);//拜访来源(1 有人值守访客机、 2自助访客机、3 微信公众号、4第三方平台、5APP、6 微信小程序)
        bookInfo.setVisitType(visitType);//访问类型（1 邀请、2 预约）
        bookInfo.setApproveType(approveType);//审批类型:1访客 2 车位
        if(StringUtils.isNotEmpty(approveStatus) && approveStatus.equals("9")){//查询已经审批的
            bookInfo.setVisitorApprove(approveStatus);//
        }else{
            bookInfo.setApproveStatus(approveStatus);//审批状态:0 未审批、1 审批 2 拒绝、3 不审批
        }
        bookInfo.setVisitStatus(visitStatus);//-状态（1 正常，2 需要审批，3 现场认证）-
        //总记录数
        int count = visitorBookService.selectCountVisitorBook(bookInfo);
        //构造分页信息

        PageUtil pageInfo = new PageUtil(currentPage, pageSize);
        pageInfo.setTotalCount(count);

        List<VisitorBook> list = visitorBookService.selectVisitorBookPage(bookInfo,pageInfo.getStart(),pageInfo.getPageSize());

        JSONObject result = new JSONObject();
        result.put("currentPage",pageInfo.getPageCurrent());//当前页码
        result.put("pageSize",pageInfo.getPageSize());//页面大小
        result.put("totalPage",pageInfo.getTotalPage());//总页数
        result.put("recordsTotal",pageInfo.getTotalCount());//总记录数
        result.put("data",LingApiUtils.changeBookInfoList(list));//数据

        return AjaxResult.success("查询成功",result);
    }


    @GetMapping("/recentRecord")
    @Log(action="recentRecord",modelName= "recentRecord",description="查询最近的预约记录")
    @RsaRequest(result=true,param =false)
    @TokenVerify
    public AjaxResult recentRecord(@RequestParam(name = "visitorTel", required = true) String visitorTel) throws Exception{
        if(StringUtils.isEmpty(visitorTel)){
            return AjaxResult.error(CodeEnum.PARAM_ERROR.getCode(),CodeEnum.PARAM_ERROR.getMsg(),"");
        }
        //查询条件
        VisitorBook bookInfo = new VisitorBook();
        bookInfo.setVisitorTelephone(visitorTel);
        List<VisitorBook> list = visitorBookService.selectVisitorBookPage(bookInfo,0,1);
        if(list == null || list.size() == 0){
            return AjaxResult.error("查询成功","");
        }
        bookInfo = list.get(0);
        //同步令令接口数据
        JSONObject result = apiService.queryVisitorRecordById(bookInfo.getVisitorId());
        if(LingApiUtils.checkResultCode(result)){//请求成功
            if(result.getJSONObject("responseResult") != null){
                /*return AjaxResult.success("查询成功",result.getJSONObject("responseResult"));*/
                bookInfo = synchBookState(bookInfo,result);//同步令令 预约记录状态
                return AjaxResult.success("查询成功",LingApiUtils.changeRecentRecordBookInfo(bookInfo));
            }
        }
        return AjaxResult.error("查询失败",result);
    }


    @GetMapping("/queryQrCode")
    @Log(action="queryQrCode",modelName= "queryQrCode",description="获取预约详情")
    @Pass
    @RsaRequest(result=true,param =false)
    public AjaxResult queryQrCode(@RequestParam(name = "visitorId", required = true) String visitorId) throws Exception{

        if(StringUtils.isEmpty(visitorId)){
            return AjaxResult.error("查询失败");
        }
        logger.info("visitorId Base64："+visitorId);
        visitorId = new String(Base64.decodeBase64(visitorId.getBytes()));
        logger.info("visitorId Base64："+visitorId);
        //同步令令接口数据
        JSONObject result = apiService.queryVisitorRecordById(visitorId);
        if(LingApiUtils.checkResultCode(result)){//请求成功
            if(result.getJSONObject("responseResult") != null){
                /*return AjaxResult.success("查询成功",result.getJSONObject("responseResult"));*/
                //更新审批状态
                VisitorBook bookInfo = visitorBookService.selectVisitorBookByVisitorID(visitorId);
                if(bookInfo == null){
                    bookInfo = LingApiUtils.getLinglingBookInfo(result);
                }else{
                    bookInfo = synchBookState(bookInfo,result);//同步令令 预约记录状态
                }
                return AjaxResult.success("查询成功",LingApiUtils.changeBookInfo(bookInfo,result));
            }
        }
        return AjaxResult.error("查询失败",result);
    }

    /***
     * 根据令令等级 记录同步状态
     * @param bookInfo 本地数据库存在的预约记录
     * @param result 令令返回的等级记录状态
     * ***/
    private VisitorBook synchBookState(VisitorBook bookInfo,JSONObject result){
        return synchBookState(true,bookInfo,result);
    }
    private VisitorBook synchBookState(boolean update,VisitorBook bookInfo,JSONObject result){
        if(LingApiUtils.checkResultCode(result)) {//请求成功
            JSONObject json = result.getJSONObject("responseResult");
            //取令令的预约时间
            bookInfo.setCreateTime(json.getLongValue("createTime"));
            boolean isDataChange = false;//判断数据是否改变
            //令令返回的审批状态
            String approveStatus = json.getString("approveStatus");
            if(StringUtils.isEmpty(bookInfo.getApproveStatus()) || !bookInfo.getApproveStatus().equals(approveStatus)){
                //以令令状态为准，同步数据
                bookInfo.setApproveStatus(approveStatus);//审核状态
                bookInfo.setPrintStatus(json.getString("printStatus"));//领码状态（1 未领码、2 已领码）
                bookInfo.setApproveTime(json.getLongValue("approveTime"));// 审批时间
                bookInfo.setPrintTime(json.getLongValue("printTime"));// 发码时间
                // TODO: 2020/11/6  这里回头同步车位审批状态
                isDataChange = true;
            }
            //同步访问者微信id
            if(StringUtils.isEmpty(bookInfo.getVisitorWxId())){
                LoginUser user = loginUserService.selectLoginUserByTel(bookInfo.getVisitorTelephone());
                if(user != null){
                    isDataChange = true;
                    bookInfo.setVisitorWxId(user.getWechatId());
                }
            }
            //同步被访问者微信id
            if(StringUtils.isEmpty(bookInfo.getVisitorUserWxId())){
                LoginUser user = loginUserService.selectLoginUserByTel(bookInfo.getVisitUserTelephone());
                if(user != null){
                    isDataChange = true;
                    bookInfo.setVisitorUserWxId(user.getWechatId());
                }
            }
            //更新预约记录
            if(isDataChange && update){
                visitorBookService.updateVisitorBook(bookInfo);
            }
        }

        return bookInfo;
    }

    @PostMapping("/getUserQrCode")
    @Log(action="getUserQrCode",modelName= "getUserQrCode",description="获取用户二维码")
    @RsaRequest(result=true,param =false)
    public AjaxResult queryVisitorRecord(@RequestBody(required = true) JSONObject json) throws Exception{
        //访客审批
        JSONObject result = apiService.getUserQrcode(json);
        if(LingApiUtils.checkResultCode(result)){//请求成功
            if(result.getJSONObject("responseResult") != null){
                return AjaxResult.success("查询成功",result.getJSONObject("responseResult"));
            }
        }
        return AjaxResult.error("查询失败",result);
    }

    @PostMapping("/visitApproveMsg")
    @Log(action="visitApproveMsg",modelName= "visitApproveMsg",description="访客信息回调")
    @Pass
   // @RsaRequest(result=true,param =false)
    public AjaxResult visitApproveMsg(String noticeMsg) throws Exception{
        JSONObject json = JSONObject.parseObject(noticeMsg);
        String noticeName = json.getString("noticeName");
        if(StringUtils.isEmpty(noticeName) || !noticeName.equals("visitApproveMsg")){
            logger.info("审核回调：消息类型不正确，不处理消息.noticeName:{};",noticeName);
            return AjaxResult.error("回调类型不正确，","0");
        }
        ///回调消息
        JSONObject msg = json.getJSONObject("msg");
        String visitorId = msg.getString("Id");
        String fromType = msg.getString("fromType");//拜访来源(1 有人值守访客机、2 自助访客机、3 微信公众号、4 第三方平台、5APP、6 微信小 程序)
        String approveStatus = msg.getString("approveStatus");
        if(StringUtils.isNotEmpty(fromType) && (fromType.equals("1") || fromType.equals("2"))){
            logger.info("审核回调：消息来源访客机，不处理消息.fromType:{};",fromType);
            return AjaxResult.success("回调成功","1");
        }
        VisitorBook bookInfo = visitorBookService.selectVisitorBookByVisitorID(visitorId);
        if(bookInfo == null){
            bookInfo = LingApiUtils.getLinglingBookInfo(apiService.queryVisitorRecordById(visitorId));
            bookInfo.setApproveStatus(approveStatus);
        }else if(!bookInfo.getApproveStatus().equals(approveStatus)){//和数据不一致，同步数据
            //这里在同步令令预约状态
            synchBookState(bookInfo,apiService.queryVisitorRecordById(visitorId));
        }

        //预约成功发送消息
        apiService.approveSuccessSendMessage(bookInfo);

        return AjaxResult.success("回调成功","1");
    }
    @GetMapping("/test")
    @RsaRequest(result=true,param =false)
    public AjaxResult test() throws Exception{
        //访客审批
        JSONObject result = new JSONObject();
        result.put("a","bbbb");
        result.put("b","cccc");
        return AjaxResult.success("查询成功",result);
    }
    @GetMapping("/test1")
    @RsaRequest(result=true,param =true)
    @TokenVerify
    public AjaxResult test1(@RequestParam("visitorUserTel") String visitorUserTel,@RequestParam(name = "visitorId", required = true)String visitorId )throws Exception{
        logger.info(visitorUserTel);
        //访客审批
        JSONObject result = new JSONObject();
        for (int i = 0; i < 10; i++) {
            result.put("a"+i,"bbbb"+i);
        }
        result.put("a","bbbb");
        result.put("b","cccc");
        return AjaxResult.success("查询成功",result);
    }
}
