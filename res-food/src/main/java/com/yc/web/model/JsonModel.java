package com.yc.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonModel implements Serializable {
    private Integer code;  // 响应码  0 失败 1 成功
    private Object obj;
    private String msg;  // 错误信息
}
