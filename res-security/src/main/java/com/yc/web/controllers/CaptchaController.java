package com.yc.web.controllers;


import com.github.cage.Cage;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

/*
    用于生成验证码图片的控制器
 */
@RestController
@RequestMapping("/ressecurity")
@Slf4j
public class CaptchaController {

    private final Cage cage = new Cage();

    //因为验证码生成后要存在session中，所以这个方法的参数由springmvc自动注入 HttpSession
    //因为使用的是springboot 3 这里的HttpSession 是 jakarta.servlet.http.HttpSession
    @GetMapping("/captcha")
    public String getCaptcha(HttpSession session) {
        // 生成验证码
        String token = cage.getTokenGenerator().next();
        log.info("生成的验证码是：" + token);
        session.setAttribute("captcha", token); // 把token存到session中
        // 生成验证码图片
        // 把图片转成base64字符串，返回给客户端
        byte[] image = cage.draw(token);
        return Base64.getEncoder().encodeToString(image);
    }

}
