package com.yc.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Apple {
    private String name;
    private int price;
}
