package com.zhuxs.result.service;

import com.zhuxs.result.domain.entity.Permission;

import java.util.List;

/**
 * Created by shusesshou on 2017/9/25.
 */
public interface PermissionService {
    Permission addPermission(Permission permission);
    List<Permission> listPermissions();
}
