package com.tengxt.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// 启动 Eureka 服务器端功能
@EnableEurekaServer
@SpringBootApplication
public class SpringBootMainClass {
    public static void main(String[] args) {
        SpringApplication.run(com.tengxt.spring.cloud.SpringBootMainClass.class, args);
    }
}
