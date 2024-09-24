package com.yc.service;

import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.web.model.CartItem;

import java.util.Set;

public interface ResorderBiz {
    /*
     *  添加订单
     *  resorder: 订单信息
     *  cartItems: 购物车信息 这是一个Set
     *  resuser: 当前下订的用户
     */
    public int order(Resorder resorder, Set<CartItem> cartItems, Resuser resuser);
}
