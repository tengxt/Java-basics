package com.tengxt.crowd.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalHandler {
    // 首页，直接访问，而不用加额外的路径
    @RequestMapping("/")
    public String showPortalPage(){
        return "portal";
    }
}
