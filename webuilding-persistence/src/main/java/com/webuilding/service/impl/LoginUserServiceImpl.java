package com.webuilding.service.impl;

import com.webuilding.entity.LoginUser;
import com.webuilding.mapper.LoginUserMapper;
import com.webuilding.service.ILoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登录用户 服务层实现
 * 
 */
@Service
public class LoginUserServiceImpl implements ILoginUserService{

    @Autowired
    private LoginUserMapper loginUserMapper;
    /**
     * 查询登录用户信息
     *
     * @param user 查询条件
     * @return 登录用户集合
     */
    public List<LoginUser> selectLoginUserList(LoginUser user){
        return loginUserMapper.selectLoginUserList(user);
    }

    /**
     * 根据手机号查询登录用户
     *
     * @param telephone 查询的手机号
     * @return 登录用户集合
     */
    public LoginUser selectLoginUserByTel(String telephone){
        return loginUserMapper.selectLoginUserByTel(telephone);
    }

    /**
     * 新增登录用户信息
     *
     * @param user 新增登录用户
     * @return 结果
     */
    public int insertLoginUser(LoginUser user){
        return loginUserMapper.insertLoginUser(user);
    }


    /**
     * 更新登录用户信息
     *
     * @param user 更新登录用户
     * @return 结果
     */
    public int updateLoginUser(LoginUser user){
        return loginUserMapper.updateLoginUser(user);
    }

    /**
     * 刷新登录信息
     *
     * @param phoneNumber 登录手机号
     * @param weixinID 登录微信号
     * @return 结果
     */
    public int updateLoginInfo(String phoneNumber,String weixinID){
        //如果是noWechatId 不更新数据库，这个微信id无意义，直接返回成功
        if(weixinID != null && weixinID.equals("noWechatId")){
            return 1;
        }
        LoginUser loginUser = loginUserMapper.selectLoginUserByTel(phoneNumber);
        if(loginUser != null){
            loginUser.setWechatId(weixinID);
            return loginUserMapper.updateLoginUser(loginUser);
        }else{
            loginUser = new LoginUser();
            loginUser.setWechatId(weixinID);
            loginUser.setTelephone(phoneNumber);
            return loginUserMapper.insertLoginUser(loginUser);
        }
    }
}
