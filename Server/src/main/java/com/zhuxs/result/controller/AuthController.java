package com.zhuxs.result.controller;

import com.zhuxs.result.domain.entity.Role;
import com.zhuxs.result.dto.RoleDto;
import com.zhuxs.result.exception.ResultException;
import com.zhuxs.result.domain.entity.User;
import com.zhuxs.result.domain.enums.ErrorCode;
import com.zhuxs.result.dto.UserDto;
import com.zhuxs.result.utils.ApplicationUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shusesshou on 2017/9/22.
 */
@RestController
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String SUBPATH_LOGIN = "/login";
    public static final String SUBPATH_USERINFO = "/userInfo";
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = SUBPATH_LOGIN)
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto,
                                         UriComponentsBuilder uriComponentsBuilder){
        HttpHeaders headers = ApplicationUtil.getHttpHeaders(uriComponentsBuilder,SUBPATH_LOGIN);
        logger.info("================userInfo================username: " + userDto.getUsername() + ",pw: " + userDto.getPassword());
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userDto.getUsername(),userDto.getPassword());
        //User user = new User("root","root","root","root");
        //userDao.save(user);
        try{
            subject.login(token);
        } catch (Exception e){
            logger.error("======登录失败======");
            throw new ResultException();
        }
        UserDto loginUserDto = (UserDto) SecurityUtils.getSubject().getSession().getAttribute("user");

        return new ResponseEntity<>(loginUserDto,headers, HttpStatus.OK);
    }

    @GetMapping(value = SUBPATH_USERINFO)
    public ResponseEntity<UserDto> getUserInfo(UriComponentsBuilder uriComponentsBuilder){
        HttpHeaders headers = ApplicationUtil.getHttpHeaders(uriComponentsBuilder,SUBPATH_USERINFO);
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getSession().getAttribute("user");

        return new ResponseEntity<UserDto>(userDto,headers,HttpStatus.OK);
    }

    @GetMapping(value = "notAuthc")
    public void notAuthc(UriComponentsBuilder uriComponentsBuilder){
        throw new ResultException("Not Authc", ErrorCode.NOTAUTHC);
    }

    @GetMapping(value = "notAuthz")
    public void notAuthz(UriComponentsBuilder uriComponentsBuilder){
        UserDto loginUserDto = (UserDto) SecurityUtils.getSubject().getSession().getAttribute("user");
        throw new ResultException(
                "UserName: " + loginUserDto.getName()
                        + "\nPermissions: " + loginUserDto.getPermissions().toString()
                        + "\nRoles: " + loginUserDto.getRoles().toString()
                , ErrorCode.NOTAUTHC);
    }

    private UserDto convertToDto(User user){
        //return modelMapper.map(user,UserDto.class);
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        List<Role> roles = user.getRoles();
        userDto.setRoles(roles.stream()
                    .map(role -> {
                        return convertToDto(role);
                    })
                    .collect(Collectors.toList()));

        return userDto;
    }

    private RoleDto convertToDto(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());
        return roleDto;
    }
}
