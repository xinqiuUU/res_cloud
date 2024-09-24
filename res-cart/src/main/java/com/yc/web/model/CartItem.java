package com.yc.web.model;

import com.yc.bean.Resfood;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Resfood resfood; // 商品对象  所有的商品对象都实现Commodity接口 都有getRealprice()方法
    private Integer num;
    private Double smallCount;

    public Double getSmallCount() {
        if ( resfood != null){
            smallCount = resfood.getRealprice() * num;
        }
        return smallCount;
    }
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Resfood getResfood() {
        return resfood;
    }

    public void setResfood(Resfood resfood) {
        this.resfood = resfood;
    }
    public void setSmallCount(Double smallCount) {
        this.smallCount = smallCount;
    }

}
