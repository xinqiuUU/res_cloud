package com.yc.aspects;


import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.service.JmsMessageProcessor;
import com.yc.web.model.CartItem;
import com.yc.web.model.MessageBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect // 切面
@Component
@Order(10)  // 优先级
public class AccountServiceEmailAspect {

    //存款
    @Pointcut("execution(* com.yc.service.ResorderBizImpl.order(..))") // 所有以update开头的方法
    public void order() {}

    @Pointcut("order() ")
    public void all() {}

    @Autowired
    private JmsMessageProcessor jmsMessageProcessor;

    // 增强类型:后置( after afterReturning , afterThrowing, finally)
    @AfterReturning("all()")
    public void sendEmail(  JoinPoint jp ) {  //连接点 即目标方法的参数
        Object[] obj = jp.getArgs(); // 第一个参数  账户id
        Resorder resorder = (Resorder) obj[0]; //订单对象
        int roid = resorder.getRoid(); // 订单编号
        Set<CartItem> cartItems = (Set<CartItem>) obj[1];// 购物车信息
        double total = cartItems.stream().mapToDouble(CartItem::getSmallCount).sum();
        Resuser resuser = (Resuser) obj[2];// 当前下订的用户
        int userid = resuser.getUserid(); // 下订用户的编号
        String email = resuser.getEmail(); // 下订用户的邮箱
        new Thread( ()->{
//            mailBiz.sendMail(email,"账户变动通知",info);
            jmsMessageProcessor.sendMessage(  new MessageBean(  resorder , total , null ,email , null )  );

        }).start();
    }

}
