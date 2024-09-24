package com.yc.test1;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        //初始化流控规则
        initFlowRules();
        while(true){
            // 模拟请求
            try(Entry entry = SphU.entry("test")){

            }catch (BlockException e){
                // 处理被流控的请求
                System.out.println("流控");
            }
        }
    }
    // 初始化流控规则 配置文件配置
    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("test");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS); // 限流模式  QPS模式:每秒的请求数
        // Set limit QPS to 20.
        rule.setCount(20);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
