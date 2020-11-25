package com.tengxt.crowd.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PortalHandler {
    // 首页，直接访问，而不用加额外的路径
    @RequestMapping("/")
    public String showPortalPage() {
        return "portal";
    }

    // 退出登录
    @RequestMapping("/auth/do/member/logout.html")
    public String doLogout(HttpSession session) {
        // 清除session域数据
        session.invalidate();

        // 重定向到首页
        return "redirect:http://localhost:9004/";
    }
}
