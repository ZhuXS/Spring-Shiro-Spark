package com.zhuxs.result.controller;

import com.zhuxs.result.Exception.ResultException;
import com.zhuxs.result.domain.entity.User;
import com.zhuxs.result.domain.enums.ErrorCode;
import com.zhuxs.result.dto.ErrorDto;
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

/**
 * Created by shusesshou on 2017/9/22.
 */
@RestController
public class AuthController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String PATH = "/login";
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = PATH)
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto,
                                         UriComponentsBuilder uriComponentsBuilder){
        HttpHeaders headers = ApplicationUtil.getHttpHeaders(uriComponentsBuilder,PATH);
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
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        UserDto loginUserDto = convertToDto(user);

        return new ResponseEntity<>(loginUserDto,headers, HttpStatus.OK);
    }

    @GetMapping(value = "notAuthc")
    public void notAuthc(UriComponentsBuilder uriComponentsBuilder){
        throw new ResultException("Please Login", ErrorCode.NOTAUTHC);
    }
    private UserDto convertToDto(User user){
        return modelMapper.map(user,UserDto.class);
    }
}
