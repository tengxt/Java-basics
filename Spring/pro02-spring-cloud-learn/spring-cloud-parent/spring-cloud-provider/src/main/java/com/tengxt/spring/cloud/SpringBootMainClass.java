package com.tengxt.spring.cloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

//@EnableEurekaClient     // 启用 Eureka 客户端功能，必须是 Eureka 注册中心
//@EnableDiscoveryClient    // 启用发现服务功能，不局限于 Eureka 注册中心

// 使用 @EnableCircuitBreaker 注解开启断路器功能
@EnableCircuitBreaker

@SpringBootApplication
public class SpringBootMainClass {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainClass.class, args);
    }
}
