package com.yc.service;

import com.yc.bean.Resorder;
import com.yc.service.DepositeEmailContentServiceImpl.StrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VelocityTemplateBizImpl {

    @Autowired
    private StrategyContext strategyContext;

    public String genEmailContent(String opType, Resorder resorder, double money, String email) {
        String info = "";
        info = strategyContext.getEmailContent(opType, resorder, money, email);
        return info;
    }
}
