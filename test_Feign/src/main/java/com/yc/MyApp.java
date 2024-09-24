package com.yc;

import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.util.List;

public class MyApp {
    public static void main(String[] args) {
//        GitHub github = Feign.builder()
//                .decoder(new GsonDecoder())
//                .target(GitHub.class, "https://api.github.com");
//
//        System.out.println( github );
//        List<Contributor> contributors = github.contributors("OpenFeign", "feign");
//        for (Contributor contributor : contributors) {
//            System.out.println(contributor);
//        }

        GitHub github = Feign.builder()
                .decoder(new GsonDecoder())
                .contract( new SpringMvcContract())
                .target(GitHub.class, "https://api.github.com");

        System.out.println( github );

    }
}