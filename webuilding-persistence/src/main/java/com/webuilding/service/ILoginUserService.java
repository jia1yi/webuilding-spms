package com.webuilding.service;

import com.webuilding.entity.LoginUser;

import java.util.List;

/**
 * 登录用户 服务层
 */
public interface ILoginUserService
{
    /**
     * 查询登录用户信息
     *
     * @param user 查询条件
     * @return 登录用户集合
     */
    public List<LoginUser> selectLoginUserList(LoginUser user);

    /**
     * 根据手机号查询登录用户
     *
     * @param telephone 查询的手机号
     * @return 登录用户集合
     */
    public LoginUser selectLoginUserByTel(String telephone);

    /**
     * 新增登录用户信息
     *
     * @param user 新增登录用户
     * @return 结果
     */
    public int insertLoginUser(LoginUser user);


    /**
     * 更新登录用户信息
     *
     * @param user 更新登录用户
     * @return 结果
     */
    public int updateLoginUser(LoginUser user);

    /**
     * 刷新登录信息
     *
     * @param phoneNumber 登录手机号
     * @param weixinID 登录微信号
     * @return 结果
     */
    public int updateLoginInfo(String phoneNumber,String weixinID);
}
