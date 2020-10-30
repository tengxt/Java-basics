package com.tengxt.securitydemo01.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
@Controller
public class TestController {

    @GetMapping("hello")
    @ResponseBody
    public String hello() {
        return "hello security";
    }

    @GetMapping("index")
    public String index() {
        return "/success.html";
    }

    @ResponseBody
    @GetMapping("update")
//    @Secured({"ROLE_sale","ROLE_manager"})
//    @PreAuthorize("hasAnyAuthority('admins')") // 方法之前进行校验
    @PostAuthorize("hasAnyAuthority('admins')") // 方法执行之后校验
    public String update() {
        System.out.println("update init...");
        return "hello update";
    }
}
