package com.yc.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.api.ResfoodApi;
import com.yc.api.model.JsonModel;
import com.yc.bean.Resfood;
import com.yc.web.model.CartItem;
import com.yc.web.model.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rescart")
public class CartController {

    @Autowired
    private RedisTemplate redisTemplate;

    //   批量加入购物车  批量修改购物车
    //  使用场景 ：没登录
    /*
        用户先把商品加入到购物车，然后再登录去结算的场景
        此时有token，浏览器中有购物车数据 一次性将此数据传到服务器
     */
    @RequestMapping(value = "addAllCartItems",method = {RequestMethod.POST , RequestMethod.PUT})
    public ResponseResult addAllCartItems(@RequestBody List<CartItem> cartItemList , HttpServletRequest request) {
        //拦截器已经将用户信息放入request中 取出即可
        Map<String,Object> userClaims = (Map<String,Object>) request.getAttribute("userClaims");
        String userid = userClaims.get("userid").toString();

        //获取原来用户的购物车数据
        Map<String, CartItem> cart = new HashMap<>();
        if ( this.redisTemplate.hasKey(  "cart:" + userid)){
            cart = (Map<String, CartItem>) this.redisTemplate.opsForValue().get( "cart:" + userid);
        }
        //循环新传上来的购物车数据  如有相同则累加  没有 则创建一个新的加进去
        for (CartItem ci : cartItemList) {
            Resfood rf = ci.getResfood();
            // 获取商品信息  因为前端传过来的商品信息  没有价格  只有fid
            rf = getFoodInfo( rf.getFid() );

            if (  cart.containsKey( rf.getFid() +"")){
                int num = cart.get( rf.getFid() +"" ).getNum();
                cart.get( rf.getFid()+"" ).setNum( num +  ci.getNum()  ); // 累加
            }else{
                ci.setResfood( rf );
                ci.setSmallCount( ci.getResfood().getRealprice()*ci.getNum() );
                cart.put( rf.getFid()+"", ci ); // 新的购物项 加入到购物车中
            }
        }
        //将新的购物车数据存入redis
        this.redisTemplate.opsForValue().set( "cart:" + userid, cart );
        return ResponseResult.ok().setData( cart );
    }


    @Autowired
    private RestTemplate restTemplate;
    //ResfoodApi接口由 OpenFeign通过动态代理的方式， 访问服务端的接口
    @Autowired
    private ResfoodApi resfoodApi;
    //   获取商品信息
    private Resfood getFoodInfo(Integer fid) {
        //方案一:直接使用服务的ip:端口号  方式访问固定的ip:端口号
        //Map<String,Object> result = this.restTemplate.getForObject("http://localhost:8080/resfood/findById/"+fid,Map.class);
        //方案二:使用服务的名称  方式访问  服务名称在nacos中注册
        // 因为当前服务也注册到了 nacos  获取nacos中的服务实例清单 ， 服务名->实例列表
        // String url = "http://res-food/resfood/findById/"+fid;
        // Map<String,Object> result = this.restTemplate.getForObject( url , Map.class);

        //方案三:利用feign 调用服务, 以面向对象方式 完成
        JsonModel result = resfoodApi.findById( fid );

        ObjectMapper om = new ObjectMapper();
        Resfood resfood = om.convertValue( result.getObj() , Resfood.class);
        return resfood;
    }

    //  加入购物车
    /*
        一次添加(或减少)一个购物项
        使用场景: 已登录
            用户已经登录，点击加入购物车按钮，将商品加入到购物车
     */
    @RequestMapping(value = "addCart",method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseResult addCart( @RequestParam Integer fid, @RequestParam Integer num, HttpServletRequest request) {
        //拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String userid = userClaims.get("userid").toString();

        Resfood resfood = getFoodInfo( fid );

        //获取原来用户的购物车数据
        Map<String, CartItem> cart = new HashMap<>();
        if ( this.redisTemplate.hasKey(  "cart:" + userid)){
            cart = (Map<String, CartItem>) this.redisTemplate.opsForValue().get( "cart:" + userid);
        }
        //一个购物车  有 多个CartItem 购物项
        CartItem ci;
        if ( cart.containsKey( fid+"" )){
            ci = cart.get( fid+"" );
            ci.setNum( ci.getNum() + num );
            cart.put( fid+"", ci );
        }else{
            ci = new CartItem();
            ci.setNum( num );
            ci.setResfood( resfood );
            cart.put( fid+"", ci );
        }
        //处理数量
        if ( ci.getNum() <= 0){
            cart.remove( fid+"" );
        }
        //将新的购物车数据存入redis
        this.redisTemplate.opsForValue().set( "cart:" + userid, cart );
        return ResponseResult.ok().setData( cart );
    }

    /*
        获取购物车信息
        使用场景: 已登录
            用户已经登录，点击购物车按钮，查看购物车信息
     */
    @RequestMapping(value = "getCartInfo",method = {RequestMethod.GET})
    public ResponseResult getCartInfo( HttpServletRequest request) {
        //拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String userid = userClaims.get("userid").toString();
        Map<String, CartItem> cart = (Map<String, CartItem>) redisTemplate.opsForValue().get( "cart:" + userid );
        //从request中取出 用户数据 再取出userid 从redis中取出购物车数据  再返回
        return ResponseResult.ok().setData( cart );
    }
    /*
        清空购物车
        使用场景: 已登录
            用户已经登录，点击清空购物车按钮，将购物车清空
     */
    @RequestMapping(value = "clearAll",method = {RequestMethod.DELETE})
    public ResponseResult clearAll( HttpServletRequest request) {
        //拦截器已经将用户信息放入request中 取出即可
        Map<String, Object> userClaims = (Map<String, Object>) request.getAttribute("userClaims");
        String userid = userClaims.get("userid").toString();
        //从request中取出 用户数据 再取出userid 从redis中删除购物车数据  再返回
        redisTemplate.delete("cart:" + userid);
        return ResponseResult.ok();
    }


}
