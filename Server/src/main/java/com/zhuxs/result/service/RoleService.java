package com.zhuxs.result.service;

import com.zhuxs.result.domain.entity.Permission;
import com.zhuxs.result.domain.entity.Role;

import java.util.List;

/**
 * Created by shusesshou on 2017/9/25.
 */
public interface RoleService {
    Role addRole(Role role);

    List<Role> listRoles();

    Role updatePermissionsById(List<Permission> permissions);
}