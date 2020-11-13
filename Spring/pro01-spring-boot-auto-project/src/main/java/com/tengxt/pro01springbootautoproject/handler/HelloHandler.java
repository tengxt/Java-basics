package com.tengxt.pro01springbootautoproject.handler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloHandler {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
