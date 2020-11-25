package com.tengxt.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        // 浏览器访问的地址

        // 目标视图的名称（前缀prefix:classpath:/templates/  后缀suffix: .html）

        // 前往用户注册页面
        registry.addViewController("/auth/to/member/reg/page.html").setViewName("member-reg");

        // 前往登录页面
        registry.addViewController("/auth/to/member/login/page.html").setViewName("member-login");

        // 前往登录完成后的用户主页面
        registry.addViewController("/auth/to/member/center/page.html").setViewName("member-center");

        // 前往“我的众筹”页面
        registry.addViewController("/auth/to/member/crowd/page.html").setViewName("member-crowd");
    }
}
