package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
@Data
public class Resfood implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer fid;
    private String fname;
    private Double normprice;
    private Double realprice;
    private String detail;
    private String fphoto;

    @TableField(exist = false)  // 表示该字段不是数据库表中的字段
    private Long detailCount;  // 用于存储查询次数

    @TableField(exist = false)  // 表示该字段不是数据库表中的字段
    private Long praise;  // 点赞次数

}
