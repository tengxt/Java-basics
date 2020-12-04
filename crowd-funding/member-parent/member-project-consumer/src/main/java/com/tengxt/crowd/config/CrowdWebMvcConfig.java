package com.tengxt.crowd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UploadFileConfig uploadFileConfig;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/do/crowd/launch/page.html").setViewName("project-launch");
        registry.addViewController("/agree/protocol/page.html").setViewName("project-agree");
        registry.addViewController("/return/info/page.html").setViewName("project-return");
        registry.addViewController("/create/confirm/page.html").setViewName("project-confirm");
        registry.addViewController("/create/success.html").setViewName("project-success");
    }


    /**
     * 指定静态资源的映射
     * 当我们访问/uploadFile/**的请求时，系统会把该请求映射到指定的文件目录。
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 就是说 url 中出现 resourceHandler 匹配时，则映射到 location 中去,location 相当于虚拟路径
        // 映射本地文件时，开头必须是 file:/// 开头，表示协议
        registry
                .addResourceHandler(uploadFileConfig.getResourceHandler())
                .addResourceLocations("file:///" + uploadFileConfig.getLocation());
    }
}
