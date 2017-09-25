package com.zhuxs.result.controller;

import com.zhuxs.result.dto.PermissionDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by shusesshou on 2017/9/25.
 */
@RequestMapping(value = AdminController.PATH,produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class AdminController {
    public static final String PATH = "admin";

    public static final String SUBPATH_RULE = "rules";
    public static final String SUBPATH_USER = "users";
    public static final String SUBPATH_PERMISSION = "permissions";

    @PostMapping(value = SUBPATH_PERMISSION)
    public ResponseEntity addPermission(PermissionDto permissionDto){
        return null;
    }

}
