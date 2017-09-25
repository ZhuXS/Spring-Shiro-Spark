package com.zhuxs.result.service.impl;

import com.zhuxs.result.domain.RoleDao;
import com.zhuxs.result.domain.entity.Permission;
import com.zhuxs.result.domain.entity.Role;
import com.zhuxs.result.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shusesshou on 2017/9/25.
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDao roleDao;
    @Override
    public Role addRole(Role role) {
        role = roleDao.save(role);
        return role;
    }

    @Override
    public List<Role> listRoles() {
        List<Role> roles = roleDao.findAll();
        return roles;
    }

    @Override
    public Role updatePermissionsById(List<Permission> permissions) {
        return null;
    }
}
