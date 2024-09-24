package com.yc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {
    public static void main( String[] args ) {
        System.out.println( "sentinel 客户端 服务启动" );
        SpringApplication.run(App.class, args);
    }
}
