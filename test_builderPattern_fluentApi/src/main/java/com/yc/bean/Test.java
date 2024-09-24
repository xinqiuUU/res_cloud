package com.yc.bean;

import com.yc.App;

public class Test {

	public static void main(String[] args) {

        Apple a = Apple.builder().name("snakeapple").price(20).build();

        Person p = Person.builder().name("zhangsan").age(20).gender("男").build();

        System.out.println(a);
        System.out.println(p);
        StringBuffer sb = new StringBuffer();
        sb.append("123").append("456").append("789");
        System.out.println(sb.toString());
	}
}
