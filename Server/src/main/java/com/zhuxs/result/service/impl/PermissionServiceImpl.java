package com.zhuxs.result.service.impl;

import com.zhuxs.result.domain.PermissionDao;
import com.zhuxs.result.domain.UserDao;
import com.zhuxs.result.domain.entity.Permission;
import com.zhuxs.result.domain.entity.User;
import com.zhuxs.result.exception.ResultException;
import com.zhuxs.result.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shusesshou on 2017/9/25.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public Permission addPermission(Permission permission) {
        permissionDao.save(permission);
        return null;
    }

    @Override
    public List<Permission> listPermissions() {
        List<Permission> permissions = permissionDao.findAll();
        return permissions;
    }

    @Override
    public List<Permission> getPermissionsByUserId(long userId) {
        if(!userDao.exists(userId)){
            throw new ResultException();
        }
        try {
            User user = userDao.findOne(userId);
            List<Permission> permissions = user.getPermissions();
            return permissions;
        }catch (Exception e){
            throw new ResultException();
        }
    }

    @Override
    public void delPermissionById(long id) {
        permissionDao.delete(id);
    }

}
