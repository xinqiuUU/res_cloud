package com.yc.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Resuser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer userid;  // 用户编号 主键  自增

    private String username; // 用户名
    private String pwd;  // 密码
    private String email; // 邮箱

    @TableField(exist = false) // 表示这个字段不对应数据库的字段
    private String captcha; // 验证码
}
