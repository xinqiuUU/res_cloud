package com.yc.web.model;


import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//消息实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageBean {
    private Resorder resorder;
    private Double money;
    private Integer toaccountid;
    private String email;
    private String opType;
}
