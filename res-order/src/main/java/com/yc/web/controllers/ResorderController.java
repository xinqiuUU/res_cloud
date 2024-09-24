package com.yc.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.service.ResorderBiz;
import com.yc.web.model.CartItem;
import com.yc.web.model.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resorder")
@Slf4j
public class ResorderController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ResorderBiz resorderBiz;

    // 提交订单
    @RequestMapping(value = "/confirmOrder" , method = { RequestMethod.POST})
    public ResponseResult confirmOrder(@RequestBody Resorder order, HttpServletRequest request) {
        Map<String,Object> userClaims = (Map<String,Object>) request.getAttribute("userClaims");
        String userid = userClaims.get("userid").toString();
        if ( !redisTemplate.hasKey("cart:"+userid)){
            return ResponseResult.error("暂无任何商品");
        }
        Map<String, CartItem> cart = (Map<String, CartItem>) redisTemplate.opsForValue().get("cart:"+userid);
        order.setUserid(Integer.valueOf(userid));
        //orderTime 下单时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        order.setOrdertime(  formatter.format(  now  )  );

        // 配送时间
        if ( order.getDeliverytime() == null  || order.getDeliverytime().equals("")){
            LocalDateTime deliveryTime = now.plusHours(1);
            order.setDeliverytime( formatter.format( deliveryTime ) );
        }
        // 订单状态
        order.setStatus(0);
        try {
            Resuser resuser =  Resuser.builder().userid(  Integer.valueOf(userid) ).build();
            resuser.setEmail( userClaims.get("email").toString() );
            resorderBiz.order(order ,new HashSet( cart.values()) ,resuser);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.error("下单失败");
        }
        redisTemplate.delete("cart:"+userid);
        return ResponseResult.ok("下单成功");
    }

    // 支付订单
    @GetMapping("payAction")
    public Map<String,Object> payAction( Integer flag) throws InterruptedException {
        log.info("flag:"+flag);
        //TODO: 测试慢请求
        if ( flag == null){
            Thread.sleep(1000);
        }else if ( flag == 1 || flag == 2){    // 模拟支付失败
            throw new RuntimeException("测试异常");
        }
        Map<String,Object> map = new HashMap<>();
        //取出当前用户的订单金额，调用第三方接口，完成支付
        map.put("code",1);
        map.put("msg","支付成功");
        return map;
    }

}
