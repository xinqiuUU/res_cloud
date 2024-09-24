package com.yc.aspects;

import com.yc.bean.Resfood;
import com.yc.web.model.JsonModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect // 切面
@Component
@Order(9)  // 优先级
@Slf4j
public class ReadAspect {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    // 定义切入点，拦截 ResfoodController 的 findById 方法，并接收 fid 参数
//    @Pointcut("execution(* com.yc.web.controllers.ResfoodController.findById(Integer)) && args(fid)")
//    public void findAccountPointcut(Integer fid) {}

    // 定义切入点，拦截 ResfoodController 的 findById 方法，并接收 fid 参数
    @Pointcut("execution(* com.yc.web.controllers.ResfoodController.findById(..))")
    public void findAccountPointcut() {}


    // 在方法执行后执行   无法获取返回值
//    @AfterReturning(pointcut = "findAccountPointcut(fid)", argNames = "fid")
//    public void afterFindAccount(Integer fid) {
//        String key = "detailCount:" + fid;
//        Long count = redisTemplate.opsForValue().increment(key, 1); // 增加计数
//        log.info("AspectJ 后置通知 - fid: " + fid + " 查询次数: " + count);
//    }

    // 在方法执行后执行   可以获取返回值
    @Around("findAccountPointcut()")
    public Object countIncrement(ProceedingJoinPoint jp) {
        // 从目标方法的参数中获取 fid
        Object[] args = jp.getArgs();
        int fid = args.length > 0? (int) args[0] : 0;

        String key = "detailCount:" + fid;
        Long count = redisTemplate.opsForValue().increment(key, 1); // 增加计数

        log.info("AspectJ 环绕通知 - fid: " + fid + " 查询次数: " + count);
        JsonModel jm = null;
        try {
            // 执行目标方法 浏览次数
            jm = (JsonModel) jp.proceed();  // 执行目标方法   获取目标方法的返回值
            Resfood rf = (Resfood) jm.getObj();
            rf.setDetailCount(count);
            // 点赞次数
            Integer praiseInt = (Integer) this.redisTemplate.opsForValue().get("praise:" + String.valueOf(fid));
            long praise = praiseInt==null?0:praiseInt.longValue();
            rf.setPraise(praise);

            jm.setObj(rf);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return jm;
    }
}
