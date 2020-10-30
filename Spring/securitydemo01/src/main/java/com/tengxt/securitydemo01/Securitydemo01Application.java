package com.tengxt.securitydemo01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@MapperScan("com.tengxt.securitydemo01.mapper")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class Securitydemo01Application {
	public static void main(String[] args) {
		SpringApplication.run(Securitydemo01Application.class, args);
	}

}
