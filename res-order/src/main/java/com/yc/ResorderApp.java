package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class ResorderApp {

    public static void main(String[] args) {
        System.out.println("ResorderApp服务启动成功");
        SpringApplication.run(ResorderApp.class, args);
    }
}
