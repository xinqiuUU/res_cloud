package com.yc.web.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class ResfoodVO implements Serializable {
    private Integer fid;
    private String fname;
    private Double normprice;
    private Double realprice;
    private String detail;
    private String fphoto;

    private Long detailCount;  // 用于存储查询次数

    //序列化为JSON时不会包含在响应中
    @JsonIgnore  // 忽略这个字段 在做响应是忽略这个字段 不生产响应的json字段
    private MultipartFile fphotofile;  //上传的图片
}
