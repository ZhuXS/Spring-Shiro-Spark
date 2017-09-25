package com.zhuxs.result.service.impl;

import com.zhuxs.result.domain.RoleDao;
import com.zhuxs.result.domain.entity.Role;
import com.zhuxs.result.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by shusesshou on 2017/9/25.
 */
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDao roleDao;
    @Override
    public Role addRole(Role role) {
        role = roleDao.save(role);
        return role;
    }
}
