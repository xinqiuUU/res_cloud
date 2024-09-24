package com.yc.service.DepositeEmailContentServiceImpl;

import com.yc.bean.Resorder;

/*
    策略接口
 */
public interface EmailContentStrategyService {

    default String getEmailContent(Resorder resorder, double money, String email) {
        return "";
    }
}
