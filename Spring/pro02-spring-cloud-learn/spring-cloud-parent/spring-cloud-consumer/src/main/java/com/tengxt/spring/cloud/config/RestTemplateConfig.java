package com.tengxt.spring.cloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    @LoadBalanced // 让 RestTemplate 有负载均衡功能，通过调用 ribbon 访问 provider 集群
    public RestTemplate getRestTemplate() {
        return builder.build();
    }
}
