package com.webuilding.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.webuilding.entity.VisitorBook;
import com.webuilding.entity.VisitorInfo;

import java.util.ArrayList;
import java.util.List;

/****
 * 令令请求工具转化类
 * **/
public class LingApiUtils {

    /****
     * 校验请求返回是否成功
     * @param resultJson
     * @return
     */
    public static boolean checkResultCode(JSONObject resultJson){
        if(resultJson!=null && resultJson.getString("statusCode").equals("1")){
            return true;
        }else{
            return false;
        }
    }

    /*****
     * 获取用户信息的json
     * @param userResult
     * @return
     */
    public static JSONObject getUserObject(JSONObject userResult){
        if(userResult != null && userResult.getJSONObject("responseResult") != null){
            JSONArray user = userResult.getJSONObject("responseResult").getJSONArray("user");
            if(user != null && user.size() > 0){
                return user.getJSONObject(0);
            }
        }
        return null;
    }

    /****
     * 获取组织机构信息的json
     * @param departResult
     * @return
     */
    public static JSONObject getDepartObject(JSONObject departResult){
        if(departResult != null && departResult.getJSONObject("responseResult") != null){
            JSONArray depart = departResult.getJSONObject("responseResult").getJSONArray("depart");
            if(depart != null && depart.size() > 0){
                return depart.getJSONObject(0);
            }
        }
        return null;
    }

    /****
     *获取预约信息
     * ***/
    public static VisitorBook getVisitorBookInfo(JSONObject bookJson){
        VisitorBook book = new VisitorBook();
        //预约基本信息
        book.setVisitDepartCode(bookJson.getString("visitDepartCode"));//拜访组织机构 code
        book.setVisitDepartName(bookJson.getString("visitDepartName"));//拜访组织机构名称
        book.setVisitPurpose(bookJson.getString("visitPurpose"));//访问事由
        book.setBaseFloor(bookJson.getIntValue("baseFloor"));//最低楼层
        book.setVisitFloor(bookJson.getString("visitFloor"));//访问楼层
        book.setVisitStartTime(bookJson.getLongValue("visitStartTime"));//访问开始时间
        book.setVisitEndTime(bookJson.getLongValue("visitEndTime"));//访问结束时间
        book.setEffecNumber(bookJson.getIntValue("effecNumber"));//访问有效次数
        book.setFromType(bookJson.getIntValue("fromType"));//拜访来源(1 有人值守访客机、 2自助访客机、3 微信公众号、4第三方平台、5APP、6 微信小程序)
        book.setVisitType(bookJson.getIntValue("visitType"));//访问类型（1 邀请、2 预约）
        book.setVisitUserTelephone(bookJson.getString("visitUserTelephone"));//被访人用户手机号码
        book.setVisitUserName(bookJson.getString("visitUserName"));//被访人用户名称
        book.setRemark(bookJson.getString("remark"));//备注
        book.setCancelApproval(bookJson.getString("cancelApproval"));//是否强制取消审批，默认不取消（1 取消，0 不取消）
        book.setCancelCertificate(bookJson.getString("cancelCertificate"));//是否强制取消现场认证，默认不取消（1 取消，0 不取消）
        book.setCarNO(bookJson.getString("carNO"));//访客车牌号
        //默认审批状态为0
        book.setApproveStatus("0");
        book.setParkingStatus("0");
        //预约访客信息
        book.setVisitorMsg(getVisitorInfo(bookJson));

        return book;
    }

    /****
     *更新访客 返回结果
     * ***/
    public static List<VisitorBook> getUpdateBookResult(VisitorBook book,JSONObject resultJson){
        List<VisitorBook> list = new ArrayList<VisitorBook>();
        //返回结果信息
        JSONArray visitors = resultJson.getJSONArray("visitor");
        if(visitors != null && visitors.size() > 0){
            for(int i = 0; i < visitors.size(); i++){
                //获取访客实际预约信息，同步令令
                VisitorBook linglingBook = getLingLingVisitorBookInfo(book,visitors.getJSONObject(i));
                if(linglingBook != null){
                    list.add(linglingBook);
                }
            }
        }
        return list;
    }

    /*****/
    private static VisitorBook getLingLingVisitorBookInfo(VisitorBook book,JSONObject resultJson){
        VisitorBook linglingBook = new VisitorBook();
        //预约基本信息
        linglingBook.setVisitDepartCode(book.getVisitDepartCode());//拜访组织机构 code
        linglingBook.setVisitDepartName(book.getVisitDepartName());//拜访组织机构名称
        linglingBook.setVisitPurpose(book.getVisitPurpose());//访问事由
        linglingBook.setBaseFloor(book.getBaseFloor());//最低楼层
        linglingBook.setVisitFloor(book.getVisitFloor());//访问楼层
        linglingBook.setVisitStartTime(book.getVisitStartTime());//访问开始时间
        linglingBook.setVisitEndTime(book.getVisitEndTime());//访问结束时间
        linglingBook.setEffecNumber(book.getEffecNumber());//访问有效次数
        linglingBook.setFromType(book.getFromType());//拜访来源(1 有人值守访客机、 2自助访客机、3 微信公众号、4第三方平台、5APP、6 微信小程序)
        linglingBook.setVisitType(book.getVisitType());//访问类型（1 邀请、2 预约）
        linglingBook.setVisitUserTelephone(book.getVisitUserTelephone());//被访人用户手机号码
        linglingBook.setVisitUserName(book.getVisitUserName());//被访人用户名称
        linglingBook.setRemark(book.getRemark());//备注
        linglingBook.setCancelApproval(book.getCancelApproval());//是否强制取消审批，默认不取消（1 取消，0 不取消）
        linglingBook.setCancelCertificate(book.getCancelCertificate());//是否强制取消现场认证，默认不取消（1 取消，0 不取消）
        linglingBook.setCarNO(book.getCarNO());//访客车牌号
        //默认审批状态为0
        linglingBook.setApproveStatus(book.getApproveStatus());
        linglingBook.setParkingStatus(book.getParkingStatus());
        //预约令令返回信息
        linglingBook.setReason(resultJson.getString("reason"));//黑白名单原因
        linglingBook.setVisitStatus(resultJson.getString("visitStatus"));//状态（1 正常，2 需要审批，3 现场认证）
        linglingBook.setVisitorType(resultJson.getString("visitorType"));//黑白名单类型（0 黑，1 白）
        linglingBook.setQrcodeKey(resultJson.getString("qrcodeKey"));//访客二维码数据
        linglingBook.setCodeId(resultJson.getString("codeId"));//访客二维码 id
        linglingBook.setVisitorCode(resultJson.getString("visitorCode"));//访客码
        linglingBook.setVisitorId(resultJson.getString("visitorId"));//访客记录 id
        linglingBook.setVisitorTelephone(resultJson.getString("visitorTelephone"));//访客手机号码
        linglingBook.setCreateTime(System.currentTimeMillis());

        //获取访客信息
        for(VisitorInfo info : book.getVisitorMsg()){
            if(linglingBook.getVisitorTelephone().equals(info.getVisitorTelephone()) && StringUtils.isEmpty(info.getVisitorId())){
                info.setVisitorId(linglingBook.getVisitorId());
                linglingBook.setVisitorName(info.getName());
                linglingBook.setCarNO(info.getCarNO());

                linglingBook.setVisitorMsg(new ArrayList<>());
                linglingBook.getVisitorMsg().add(info);

                return linglingBook;
            }
        }
        return null;
    }

    /****
     *获取预约中访客信息
     * ***/
    public static List<VisitorInfo> getVisitorInfo(JSONObject bookJson){
        //预约访客信息
        JSONArray visitors = bookJson.getJSONArray("visitorMsg");
        List<VisitorInfo> list = new ArrayList<VisitorInfo>();
        if(visitors != null && visitors.size() > 0){
            for(int i = 0; i < visitors.size(); i++){
                JSONObject visitorMsg = visitors.getJSONObject(i);
                VisitorInfo info = new VisitorInfo();
                info.setVisitorTelephone(visitorMsg.getString("visitorTelephone"));//访客电话号码
                //访客身份证信息
                JSONObject idenMsg = visitorMsg.getJSONObject("idenMsg");
                if(idenMsg != null){
                    info.setIDType(idenMsg.getString("IDType"));//访客证件类型，默认身份证（1身份证）
                    info.setName(idenMsg.getString("name"));//访客姓名
                    info.setGender(idenMsg.getString("gender"));//访客性别（1 男，2 女）
                    info.setRace(idenMsg.getString("race"));//访客民族
                    info.setBirthday(idenMsg.getString("birthday"));//访客生日
                    info.setCensus_addr(idenMsg.getString("census_addr"));//访客地址
                    info.setIden_number(idenMsg.getString("iden_number"));//访客证件号码
                    info.setPolice(idenMsg.getString("police"));//访客发证机关
                    info.setActive_from(idenMsg.getString("active_from"));//访客证件有效起始时间
                    info.setActive_to(idenMsg.getString("active_to"));//访客证件有效结束时间
                    info.setCard_id(idenMsg.getString("card_id"));//访客证件卡号
                    info.setHead_img_name(idenMsg.getString("head_img_name"));//访客证件头像
                    info.setCarNO(idenMsg.getString("carNO"));//访客车牌号
                }
                list.add(info);
            }
        }
        return list;
    }

    /***
     * 构造预约详情 接口返回的数据
     * ***/
    public static JSONObject changeBookInfo(VisitorBook bookInfo,JSONObject linglingResult){
        //构造返回数据
        JSONObject resultJson = new JSONObject();
        resultJson.put("visitorId",bookInfo.getVisitorId());//访客记录id
        resultJson.put("visitorName",bookInfo.getVisitorName());//访客姓名
        resultJson.put("visitorTelephone",bookInfo.getVisitorTelephone());//访客联系号码
        resultJson.put("visitorWxId",bookInfo.getVisitorWxId());//访客微信id
        resultJson.put("visitorUserName",bookInfo.getVisitUserName());//被访人姓名
        resultJson.put("visitorUserTel",bookInfo.getVisitUserTelephone());//被访人联系号码
        resultJson.put("visitorUserWxId",bookInfo.getVisitorUserWxId());//被访人微信id
        resultJson.put("visitType",bookInfo.getVisitType());//访问类型（1 邀请，2 预约）
        resultJson.put("visitorType",bookInfo.getVisitorType());//黑名单类型（0 黑，1 白）
        resultJson.put("visitStatus",bookInfo.getVisitStatus());//状态（1 正常，2 需要审批，3 现场认证）
        resultJson.put("approveStatus",bookInfo.getApproveStatus());//审批状态（0，待审批 1 拒绝，2 通过）
        resultJson.put("parkingStatus",bookInfo.getParkingStatus());//审批状态（0，待审批 1 预约失败，2 预约拒绝，3 预约成功）
        resultJson.put("codeId",bookInfo.getCodeId());//访客二维码id
        resultJson.put("qrcodeKey",bookInfo.getQrcodeKey());//访客二维码数据
        resultJson.put("visitorCode",bookInfo.getVisitorCode());//访客码
        resultJson.put("reason",bookInfo.getReason());//黑名单原因
        resultJson.put("visitStartTime",bookInfo.getVisitStartTime());//访问开始时间
        resultJson.put("visitEndTime",bookInfo.getVisitEndTime());//访问结束时间
        resultJson.put("visitDepartName",bookInfo.getVisitDepartName());//访问部门
        resultJson.put("carNO",bookInfo.getCarNO());//访客车牌号
        resultJson.put("visitPurpose",bookInfo.getVisitPurpose());//访问事由
        resultJson.put("remark",bookInfo.getApproveRemark());//拒绝原因

        //新增将令令的放回图片添加进入result.getJSONObject("responseResult")
        try{
            resultJson.put("otherImgs",linglingResult.getJSONObject("responseResult").getJSONArray("otherImgs"));
        }catch (Exception e){}

        return resultJson;
    }

    /***
     * 构造返回最近预约记录 接口返回的数据
     * ***/
    public static JSONObject changeRecentRecordBookInfo(VisitorBook bookInfo){
        //构造返回数据
        JSONObject resultJson = new JSONObject();
        resultJson.put("visitorId",bookInfo.getVisitorId());//访客记录id
        resultJson.put("visitorName",bookInfo.getVisitorName());//访客姓名
        resultJson.put("visitorWxId",bookInfo.getVisitorWxId());//访客微信id
        resultJson.put("visitorUserName",bookInfo.getVisitUserName());//被访人姓名
        resultJson.put("visitorUserWxId",bookInfo.getVisitorUserWxId());//被访人微信id
        resultJson.put("approveStatus",bookInfo.getApproveStatus());//审批状态（0，待审批 1 拒绝，2 通过）
        resultJson.put("parkingStatus",bookInfo.getParkingStatus());//审批状态（0，待审批 1 预约失败，2 预约拒绝，3 预约成功）
        resultJson.put("visitStartTime",bookInfo.getVisitStartTime());//访问开始时间
        resultJson.put("visitEndTime",bookInfo.getVisitEndTime());//访问结束时间
        return resultJson;
    }

    /***
     * 构造登记记录列表 接口返回的数据
     * ***/
    public static JSONArray changeBookInfoList(List<VisitorBook> list){
        JSONArray resultJson = new JSONArray();
        if(list != null && list.size() > 0){
            for(VisitorBook bookInfo : list){
                //构造返回数据
                JSONObject json = new JSONObject();
                json.put("visitorId",bookInfo.getVisitorId());//访客记录id
                json.put("visitorName",bookInfo.getVisitorName());//访客姓名
                json.put("visitorTelephone",bookInfo.getVisitorTelephone());//访客联系号码
                json.put("visitorWxId",bookInfo.getVisitorWxId());//访客微信id
                json.put("visitorUserName",bookInfo.getVisitUserName());//被访人姓名
                json.put("visitorUserTel",bookInfo.getVisitUserTelephone());//被访人联系号码
                json.put("visitorUserWxId",bookInfo.getVisitorUserWxId());//被访人微信id
                json.put("visitType",bookInfo.getVisitType());//访问类型（1 邀请，2 预约）
                json.put("visitorType",bookInfo.getVisitorType());//黑名单类型（0 黑，1 白）
                json.put("visitStatus",bookInfo.getVisitStatus());//状态（1 正常，2 需要审批，3 现场认证）
                json.put("approveStatus",bookInfo.getApproveStatus());//审批状态（0，待审批 1 拒绝，2 通过）
                json.put("parkingStatus",bookInfo.getParkingStatus());//审批状态（0，待审批 1 预约失败，2 预约拒绝，3 预约成功）
                json.put("codeId",bookInfo.getCodeId());//访客二维码id
                json.put("qrcodeKey",bookInfo.getQrcodeKey());//访客二维码数据
                json.put("visitorCode",bookInfo.getVisitorCode());//访客码
                json.put("reason",bookInfo.getReason());//黑名单原因
                json.put("visitStartTime",bookInfo.getVisitStartTime());//访问开始时间
                json.put("visitEndTime",bookInfo.getVisitEndTime());//访问结束时间
                json.put("visitDepartName",bookInfo.getVisitDepartName());//访问部门
                json.put("carNO",bookInfo.getCarNO());//访客车牌号
                json.put("visitPurpose",bookInfo.getVisitPurpose());//访问事由
                json.put("remark",bookInfo.getApproveRemark());//拒绝原因

                resultJson.add(json);
            }
        }

        return resultJson;
    }

    /***
     * 解析令令预约查询信息
     * **/
    public static VisitorBook getLinglingBookInfo(JSONObject result){
        VisitorBook linglingBook = new VisitorBook();
        if(LingApiUtils.checkResultCode(result)) {//请求成功
            JSONObject json = result.getJSONObject("responseResult");
            linglingBook.setVisitorId(json.getString("id"));//访客记录 id
            //预约基本信息
            linglingBook.setVisitorName(json.getString("name"));//访客姓名
            linglingBook.setVisitorTelephone(json.getString("visitorTelephone"));//访客手机号码
            linglingBook.setVisitDepartCode(json.getString("visitDepartCode"));//拜访组织机构 code
            linglingBook.setVisitDepartName(json.getString("visitDepartName"));//拜访组织机构名称
            linglingBook.setVisitPurpose(json.getString("visitPurpose"));//访问事由
            linglingBook.setBaseFloor(json.getIntValue("baseFloor"));//最低楼层
            linglingBook.setVisitFloor(json.getString("visitFloor"));//访问楼层
            linglingBook.setVisitStartTime(json.getLongValue("visitStartTime"));//访问开始时间
            linglingBook.setVisitEndTime(json.getLongValue("visitEndTime"));//访问结束时间
            linglingBook.setEffecNumber(json.getIntValue("effecNumber"));//访问有效次数
            linglingBook.setFromType(json.getIntValue("fromType"));//拜访来源(1 有人值守访客机、 2自助访客机、3 微信公众号、4第三方平台、5APP、6 微信小程序)
            linglingBook.setVisitType(json.getIntValue("visitType"));//访问类型（1 邀请、2 预约）
            linglingBook.setVisitUserTelephone(json.getString("visitUserTelephone"));//被访人用户手机号码
            linglingBook.setVisitUserName(json.getString("visitUserName"));//被访人用户名称
            linglingBook.setRemark(json.getString("remark"));//备注
            linglingBook.setCancelApproval(json.getString("cancelApproval"));//是否强制取消审批，默认不取消（1 取消，0 不取消）
            linglingBook.setCancelCertificate(json.getString("cancelCertificate"));//是否强制取消现场认证，默认不取消（1 取消，0 不取消）
            linglingBook.setCarNO(json.getString("carNO"));//访客车牌号
            linglingBook.setApproveStatus(json.getString("ApproveStatus"));
            linglingBook.setParkingStatus(json.getString("parkingStatus"));
            linglingBook.setPrintStatus(json.getString("printStatus"));//领码状态（1 未领码、2 已领码）
            linglingBook.setApproveTime(json.getLongValue("approveTime"));// 审批时间
            linglingBook.setPrintTime(json.getLongValue("printTime"));// 发码时间
            linglingBook.setCreateTime(json.getLongValue("createTime"));
            linglingBook.setReason(json.getString("reason"));//黑白名单原因
            linglingBook.setVisitStatus(json.getString("visitStatus"));//状态（1 正常，2 需要审批，3 现场认证）
            linglingBook.setVisitorType(json.getString("visitorType"));//黑白名单类型（0 黑，1 白）
            linglingBook.setQrcodeKey(json.getString("qrcodeKey"));//访客二维码数据
            linglingBook.setCodeId(json.getString("codeId"));//访客二维码 id
            linglingBook.setVisitorCode(json.getString("visitorCode"));//访客码
        }
        return linglingBook;
    }
}
