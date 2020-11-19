package com.tengxt.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy        // 启用 Zuul 的网关代理功能
@SpringBootApplication
public class SpringBootMainClass {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainClass.class, args);
    }
}
