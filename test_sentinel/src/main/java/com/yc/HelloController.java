package com.yc;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @SentinelResource(value = "hello" , blockHandler = "blockHandler") // 配置限流规则
    public String hello() {
        return "hello Sentinel";
    }
    public String blockHandler(BlockException ex) {
        return "blockHandler";
    }

}
