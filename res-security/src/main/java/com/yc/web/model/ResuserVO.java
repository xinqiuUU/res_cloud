package com.yc.web.model;

import lombok.Data;

@Data   //前端传给后端的数据
public class ResuserVO {
    private Integer userid;
    private String username;
    private String password;
    private String email;
    private String captcha;  //验证码
}
