package com.yc.service;

import com.yc.model.MessageBean;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log
public class MailBiz {

    @Autowired  // 注入JavaMailSender
    private JavaMailSender MailSender;

    @Value("${spring.mail.properties.mail.smtp.from}")
    public String fromemail;

//    @Autowired
//    private RedisTemplate redisTemplate;
    //返回前端的数据

    @Async  // 异步发送邮件
    public void send(String to, String subject, String text , MessageBean mb) {
//        SimpleMailMessage message = new SimpleMailMessage(); //不包括附件

        MimeMessage mm = MailSender.createMimeMessage();  //可以包括附件
        try{
            // 邮件内容                                        true 表示可以加附件
            MimeMessageHelper message = new MimeMessageHelper(mm, true,"UTF-8");
            message.setFrom(fromemail);// 发件人
            message.setTo(to);// 收件人
            message.setSubject(subject);// 邮件主题
            message.setText(text , true);// 邮件内容 一定要有 true 代表当成html代码
            MailSender.send( mm );// 发送邮件
        }catch (Exception e){
            e.printStackTrace();
            log.info("邮件发送失败:"+e.getMessage());
        }

    }

}
