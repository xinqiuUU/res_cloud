package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResSecurityApp {

	public static void main(String[] args) {
		System.out.println("ResSecurityApp服务启动成功");
		SpringApplication.run(ResSecurityApp.class, args);
	}
}
