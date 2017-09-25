package com.zhuxs.result.controller;

import com.zhuxs.result.dto.UserDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shusesshou on 2017/9/22.
 */
@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Autowired
    //private UserDao userDao;

    @PostMapping(value = "/login")
    public String login(@RequestBody UserDto userDto){
        logger.info("================userInfo================username: " + userDto.getUsername() + ",pw: " + userDto.getPassword());
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userDto.getUsername(),userDto.getPassword());
        //User user = new User("root","root","root","root");
        //userDao.save(user);
        

        try{
            subject.login(token);
        } catch (Exception e){
            logger.error("======登录失败======");
            return "Failure";
        }
        return SecurityUtils.getSubject().getSession().getAttribute("username").toString();
    }
}
