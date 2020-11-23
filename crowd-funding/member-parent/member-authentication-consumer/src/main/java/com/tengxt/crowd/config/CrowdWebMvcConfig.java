package com.tengxt.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        // 浏览器访问的地址
        String urlPath = "/auth/to/member/reg/page.html";

        // 目标视图的名称（前缀prefix:classpath:/templates/  后缀suffix: .html）
        String viewName = "member-reg";

        // 添加 view-controller
        registry.addViewController(urlPath).setViewName(viewName);
    }
}
