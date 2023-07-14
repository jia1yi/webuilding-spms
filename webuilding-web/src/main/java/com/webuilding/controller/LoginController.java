package com.webuilding.controller;

import com.alibaba.fastjson2.JSONObject;
import com.webuilding.annotation.Log;
import com.webuilding.annotation.Pass;
import com.webuilding.common.AjaxResult;
import com.webuilding.common.CodeEnum;
import com.webuilding.config.GlobalConfig;
import com.webuilding.config.TokenSecret;
import com.webuilding.exceprion.ParamJsonException;
import com.webuilding.rsa.RsaRequest;
import com.webuilding.service.IApiService;
import com.webuilding.service.ILoginUserService;
import com.webuilding.util.JWTUtil;
import com.webuilding.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  登录接口
 */
@RestController
@RequestMapping("/certAuth/auth")
public class LoginController extends BaseController{

    @Autowired
    protected ILoginUserService loginUserService;

    @Autowired
    IApiService apiService;
    @Autowired
    protected GlobalConfig globalConfig;
    /*{
        "visitorUserTel":"手机号",
            "wechatId":"微信id",
            "Authentication":"认证信息"
    }*/
    @PostMapping("/sms/login")
    @Log(action="login",modelName= "authentication",description="用户登录接口")
    @Pass
    @RsaRequest(result=true,param =false)
    public AjaxResult login(@RequestBody(required = true) JSONObject json) throws Exception{
        //获取参数
        String phoneNumber = json.getString("visitorUserTel");
        String weixinID = json.getString("wechatId");
        String Authentication = json.getString("Authentication");
        //校验参数
        if(StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(weixinID) || StringUtils.isEmpty(Authentication)){
            throw new ParamJsonException();
        }
        //获取验证码
        String code = apiService.getVerificationCode(phoneNumber);
        if(StringUtils.isEmpty(code)){
            return AjaxResult.error(CodeEnum.VERIFICATION_CODE_ERROR.getCode(),"登录失败",CodeEnum.VERIFICATION_CODE_ERROR.getMsg());
        }
        //校验认证信息
        String info = DigestUtils.md5Hex(phoneNumber+":"+code).toLowerCase();
        logger.info("认证信息:{}",info);
        logger.info("请求值:{}",json);
        if(!info.equals(Authentication)){//校验不通过
            return AjaxResult.error(CodeEnum.VERIFICATION_CODE_ERROR.getCode(),"登录失败",CodeEnum.VERIFICATION_CODE_ERROR.getMsg());
        }

        //更新登录信息成功
        if(loginUserService.updateLoginInfo(phoneNumber,weixinID) > 0){
            //每次登录都是一个新的UUID
            String secretUUID = commonConfig.getTokenSecret();
            String token = JWTUtil.sign(phoneNumber,secretUUID ,commonConfig.getTokenExpireTime());
            JSONObject tokeObject = new JSONObject();
            tokeObject.put("Token",token);


            TokenSecret tokenSecret = new TokenSecret();
            tokenSecret.setToken(token);
            tokenSecret.setSecret(secretUUID);
            globalConfig.add(phoneNumber,tokenSecret);
            //logger.info("登录成功保存token和手机号---tel："+phoneNumber+",token"+token);
            return AjaxResult.success("登录成功",tokeObject);
        }else{//失败
            return AjaxResult.error("登录失败","同步用户信息失败！");
        }
    }

}
