package com.yc.service;

import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.bean.Resuser;
import com.yc.dao.ResorderMapper;
import com.yc.dao.ResorderitemMapper;
import com.yc.web.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@Slf4j
public class ResorderBizImpl implements ResorderBiz{

    @Autowired
    private ResorderMapper resorderMapper; // 订单表

    @Autowired
    private ResorderitemMapper resorderitemMapper; // 订单项表

    // 提交订单
    @Override
    public int order(Resorder resorder, Set<CartItem> cartItems, Resuser resuser) {
        resorder.setUserid( resuser.getUserid() ); // 订单所属用户编号
        this.resorderMapper.insert( resorder ); // 保存订单信息
        for ( CartItem ci : cartItems){
            Resorderitem orderitem = new Resorderitem(); // 订单项对象
            orderitem.setRoid( resorder.getRoid() ); //订单号 不管循环几次 订单号都是一样的
            orderitem.setFid( ci.getResfood().getFid() ); // 商品id
            orderitem.setDealprice( ci.getResfood().getRealprice() ); // 商品成交价格
            orderitem.setNum( ci.getNum() ); // 商品数量
            this.resorderitemMapper.insert( orderitem ); // 保存订单项信息
        }
        return 1;
    }
}
