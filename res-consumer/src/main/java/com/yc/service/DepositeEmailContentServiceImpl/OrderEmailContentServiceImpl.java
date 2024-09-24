package com.yc.service.DepositeEmailContentServiceImpl;

import com.yc.bean.Resorder;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;

@Service("order")
public class OrderEmailContentServiceImpl implements EmailContentStrategyService{
    @Autowired
    private VelocityContext context;// 模板上下文
    @Autowired
    private VelocityEngine velocityEngine; // 模板引擎

    @Autowired
    @Qualifier("consumerTemplate") // 模板名称transferTemplate注入
    private Template transferTemplate;

    @Autowired
    @Qualifier("fullDf") // 模板名称withdrawTemplate注入
    private DateFormat fullDf;
    @Autowired
    @Qualifier("partDf") // 模板名称withdrawTemplate注入
    private DateFormat partDf;

    @Override
    public String getEmailContent(Resorder resorder, double money, String email) {
        Date date = new Date();
        //托管了
//        VelocityContext context = new VelocityContext();
//        context.put("accountid", account.getAccountid());
        context.put("email", email);
        context.put("subject", "下订单通知");
        context.put("optime", fullDf.format(date));
        context.put("money",money );
        context.put("roid", resorder.getRoid());
        context.put("currentDate", partDf.format(date));
        context.put("accountid", resorder.getUserid());

        try(StringWriter writer = new StringWriter()){
            transferTemplate.merge(context,writer);  //合并内容。替换占位符
            return writer.toString();       //从流获取最终的字符后
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
