package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Resorder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer roid; // 订单编号
    private Integer userid; // 所属用户编号
    private String address; // 收货地址
    private String tel; // 联系电话
    private String ordertime; // 下单时间
    private String deliverytime; // 配送时间
    private String ps; // 备注
    private Integer status; // 订单状态

}
