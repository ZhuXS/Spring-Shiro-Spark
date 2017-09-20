package com.zhuxs.result.shiro;

import com.zhuxs.result.domain.PermissionDao;
import com.zhuxs.result.domain.UserDao;
import com.zhuxs.result.domain.entity.Permission;
import com.zhuxs.result.domain.entity.Role;
import com.zhuxs.result.domain.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shusesshou on 2017/9/20.
 */
public class ShiroRealm extends AuthorizingRealm{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前用户
        User user = userDao.findUserByUsername((String)principalCollection.getPrimaryPrincipal());

        //把principals放session中，key=userId value=principals
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //赋予角色
        for(Role role:user.getRoles()){
            info.addRole(role.getName());
        }
        //赋予权限
        List<Permission> permissions = user.getRoles()
                .stream()
                .flatMap(role -> role.getPermissions().stream())
                .distinct()
                .collect(Collectors.toList());
        for(Permission permission:permissions){
            info.addStringPermission(permission.getName());
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();

        User user = userDao.findUserByUsername(userName);
        if(user != null){
            //登陆成功
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user",user);
            return new SimpleAuthenticationInfo(
                    userName, //用户
                    user.getPassword(), //密码
                    getName() //realm name
            );
        } else {
            throw new UnknownAccountException();
        }
    }
}
