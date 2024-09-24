package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Resorderitem implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer roiid;
    private Integer roid;
    private Integer fid;
    private Double dealprice;
    private Integer num;

}
