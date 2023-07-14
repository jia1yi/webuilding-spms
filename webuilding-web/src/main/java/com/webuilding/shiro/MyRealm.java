package com.webuilding.shiro;

import com.webuilding.common.CommonConfig;
import com.webuilding.common.Constant;
import com.webuilding.common.SpringContextBeanService;
import com.webuilding.config.GlobalConfig;
import com.webuilding.config.TokenSecret;
import com.webuilding.util.JWTUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 */

@Component
public class MyRealm extends AuthorizingRealm {
    /*
    private IUserService userService;
    private IUserToRoleService userToRoleService;
    private IMenuService menuService;
    private IRoleService roleService;*/
    CommonConfig commonConfig;

    @Autowired
    private GlobalConfig globalConfig;
    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }


    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
       /* if (userToRoleService == null) {
            this.userToRoleService = SpringContextBeanService.getBean(IUserToRoleService.class);
        }
        if (menuService == null) {
            this.menuService = SpringContextBeanService.getBean(IMenuService.class);
        }
        if (roleService == null) {
            this.roleService = SpringContextBeanService.getBean(IRoleService.class);
        }

        String userNo = JWTUtil.getUserNo(principals.toString());
        User user = userService.selectById(userNo);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(null != user){
            UserToRole userToRole = userToRoleService.selectByUserNo(user.getUserNo());

            //控制菜单级别按钮  类中用@RequiresPermissions("user:list") 对应数据库中code字段来控制controller
            ArrayList<String> pers = new ArrayList<>();
            List<Menu> menuList = menuService.findMenuByRoleCode(userToRole.getRoleCode());
            for (Menu per : menuList) {
                if (!ComUtil.isEmpty(per.getCode())) {
                    pers.add(String.valueOf(per.getCode()));
                }
            }
            Set<String> permission = new HashSet<>(pers);
            simpleAuthorizationInfo.addStringPermissions(permission);
        }*/
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if(Constant.METHOD_URL_SET.contains(request.getRequestURI())){
            //request.setAttribute("currentUser",new User());
            return new SimpleAuthenticationInfo(token, token, this.getName());
        }
        // 解密获得username，用于和数据库进行对比
        String userNo = JWTUtil.getUserNo(token);
        if (userNo == null) {
            throw new AuthenticationException("token invalid");
        }
        if (commonConfig == null) {
            commonConfig = SpringContextBeanService.getBean("commonConfig");
        }
        TokenSecret tokenSecret = globalConfig.getTokenMaps().get(userNo);
        if (!JWTUtil.verify(token, userNo, tokenSecret.getSecret())) {
        //if (!JWTUtil.verify(token, userNo, commonConfig.getTokenSecret())) {
            throw new AuthenticationException("token invalid");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }

}
