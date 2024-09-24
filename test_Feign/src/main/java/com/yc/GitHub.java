package com.yc;

import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface GitHub {
    // 传入的参数后可以拼接成url: https://api.github.com/repos/OpenFeign/feign/contributors
//    @RequestLine("GET /repos/{owner}/{repo}/contributors")
//    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

    @GetMapping("/{owner}/{repo}/contributors")
    public List<Contributor> contributors(@PathVariable("owner") String owner, @PathVariable("repo") String repo);


}