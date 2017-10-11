package com.zhuxs.result.shiro;

import com.zhuxs.result.domain.PermissionDao;
import com.zhuxs.result.domain.UserDao;
import com.zhuxs.result.domain.entity.Permission;
import com.zhuxs.result.domain.entity.Role;
import com.zhuxs.result.domain.entity.User;
import com.zhuxs.result.dto.PermissionDto;
import com.zhuxs.result.dto.RoleDto;
import com.zhuxs.result.dto.UserDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.io.Serializable;
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
    @Autowired
    private ShiroSessionDao shiroSessionDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @org.springframework.transaction.annotation.Transactional
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前用户
        //UserDto user = convertToDto(userDao.findUserByUsername((String)principalCollection.getPrimaryPrincipal()));
        User currentUser = userDao.findUserByUsername((String)principalCollection.getPrimaryPrincipal());
        UserDto user = convertToDto(currentUser);

        //把principals放session中，key=userId value=principals
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //赋予角色
        for(RoleDto role:user.getRoles()){
            info.addRole(role.getName());
        }
        //赋予权限
        for(PermissionDto permission:user.getPermissions()){
            info.addStringPermission(permission.getName());
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();

        User user = userDao.findUserByUsername(userName);
        UserDto userDto = convertToDto(user);
        if(user != null){
            //登陆成功
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user",userDto);
            session.setAttribute("id",user.getId());
            session.setAttribute("username",user.getUsername());
            session.setAttribute("name",user.getName());
            return new SimpleAuthenticationInfo(
                    userName, //用户
                    user.getPassword(), //密码
                    getName() //realm name
            );
        } else {
            throw new UnknownAccountException();
        }
    }

    private UserDto convertToDto(User user){
        //return modelMapper.map(user,UserDto.class);
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        List<Role> roles = user.getRoles();
        List<Permission> permissions = user.getPermissions();
        userDto.setRoles(roles.stream()
                .map(role -> {
                    return convertToDto(role);
                })
                .collect(Collectors.toList()));
        userDto.setPermissions(permissions.stream()
                .map(permission -> {
                    return convertToDto(permission);
                })
                .collect(Collectors.toList()));

        return userDto;
    }

    private RoleDto convertToDto(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());
        return roleDto;
    }

    private PermissionDto convertToDto(Permission permission){
        return modelMapper.map(permission,PermissionDto.class);
    }
}
