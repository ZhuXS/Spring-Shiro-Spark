package com.zhuxs.result.service.impl;

import com.zhuxs.result.domain.UserDao;
import com.zhuxs.result.domain.entity.User;
import com.zhuxs.result.dto.UserDto;
import com.zhuxs.result.service.UserService;

/**
 * Created by shusesshou on 2017/9/25.
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    @Override
    public User addUser(User user) {
        user = userDao.save(user);
        return user;
    }
}
