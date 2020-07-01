package com.education.teacher.controller;

import com.education.common.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @PostMapping("login")
    public Result login() {
        return Result.ok().data("token", "admin");
    }

    @GetMapping("info")
    public Result info(String token) {

        // "roles":["admin"],
        // "name":"admin",
        // "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}
        return Result.ok().data("roles", "[\"admin\"]")
                .data("name", "admin")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
