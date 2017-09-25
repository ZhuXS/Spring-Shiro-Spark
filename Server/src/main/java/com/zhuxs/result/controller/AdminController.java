package com.zhuxs.result.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by shusesshou on 2017/9/25.
 */
@RequestMapping(value = AdminController.PATH,produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
public class AdminController {
    public static final String PATH = "admin";

}
