package com.yc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
    解决浏览器的跨域问题
    开发浏览器调用API资源
    用于配置跨域资源共享（CORS）
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) { // 重写父类或接口中的方法
        registry.addMapping("/**")            // 对所有路径启用 CORS 配置
                .allowedOrigins("*")                     // 允许所有来源的请求
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的 HTTP 方法
                .allowedHeaders("*") // 允许所有的请求头
                .maxAge(3600);     // 预检请求的有效期（秒）
    }

    //WEB 的 RestTemplate 模板客户端 发送web请求 获取数据  用于调用API资源
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
