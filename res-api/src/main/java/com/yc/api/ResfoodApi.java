package com.yc.api;

import com.yc.api.model.JsonModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient( name = "res-food")  //name 服务名称 所以访问 地址最终会为: http://res-food
public interface ResfoodApi {

	@RequestMapping(value = "resfood/findById/{fid}" , method = RequestMethod.GET)
    // http://localhost:8080/resfood/findById/2
    public JsonModel findById(@PathVariable Integer fid);


}
