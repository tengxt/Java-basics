package com.tengxt.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard     //  启用仪表盘监控功能
@SpringBootApplication
public class SpringBootMainClass {
    public static void main(String[] args) {

        SpringApplication.run(SpringBootMainClass.class, args);
    }
}
