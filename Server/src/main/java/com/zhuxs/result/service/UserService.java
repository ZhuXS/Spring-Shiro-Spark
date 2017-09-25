package com.zhuxs.result.service;

import com.zhuxs.result.domain.entity.User;
import com.zhuxs.result.dto.UserDto;

import java.util.List;

/**
 * Created by shusesshou on 2017/9/25.
 */
public interface UserService {
    User addUser(User user);
    List<User> listUsers();
}
