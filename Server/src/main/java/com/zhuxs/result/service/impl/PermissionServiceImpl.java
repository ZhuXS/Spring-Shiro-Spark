package com.zhuxs.result.service.impl;

import com.zhuxs.result.domain.PermissionDao;
import com.zhuxs.result.domain.entity.Permission;
import com.zhuxs.result.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by shusesshou on 2017/9/25.
 */
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public Permission addPermission(Permission permission) {
        permissionDao.save(permission);
        return null;
    }
}
