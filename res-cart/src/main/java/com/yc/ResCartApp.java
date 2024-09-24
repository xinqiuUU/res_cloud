package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResCartApp {

	public static void main(String[] args) {
		System.out.println("ResCartApp服务启动成功");
		SpringApplication.run(ResCartApp.class, args);
	}
}
