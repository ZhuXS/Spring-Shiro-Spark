package com.zhuxs.result.service.impl;

import com.zhuxs.result.domain.entity.Permission;
import com.zhuxs.result.exception.ResultException;
import com.zhuxs.result.domain.UserDao;
import com.zhuxs.result.domain.entity.Role;
import com.zhuxs.result.domain.entity.User;
import com.zhuxs.result.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shusesshou on 2017/9/25.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User addUser(User user) {
        user = userDao.save(user);
        return user;
    }

    @Override
    public List<User> listUsers() {
        List<User> users = userDao.findAll();
        return users;
    }

    @Override
    public User updateRolesById(long id, List<Role> roles) {
        if(!userDao.exists(id)){
            throw new ResultException();
        }
        try {
            User user = userDao.findOne(id);
            user.setRoles(roles);
            user = userDao.save(user);
            return user;
        }catch (Exception e){
            //throw e;
            throw new ResultException();
        }
    }

    @Override
    public User updatePermissionsById(long id, List<Permission> permissions) {
        if(!userDao.exists(id)){
            throw new ResultException();
        }
        try {
            User user = userDao.findOne(id);
            user.setPermissions(permissions);
            user = userDao.save(user);
            return user;
        }catch (Exception e){
            throw new ResultException();
        }
    }

    @Override
    public void delUserById(long id) {
        userDao.delete(id);
    }
}
