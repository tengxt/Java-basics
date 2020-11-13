package com.tengxt.pro01springbootautoproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.tengxt.pro01springbootautoproject.mapper")
@SpringBootApplication
public class Pro01SpringBootAutoProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(Pro01SpringBootAutoProjectApplication.class, args);
	}

}
