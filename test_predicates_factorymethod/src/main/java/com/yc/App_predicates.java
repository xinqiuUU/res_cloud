package com.yc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App_predicates {
    public static void main(String[] args) {
        // 使用Predicate过滤集合
        List<String> ns = Arrays.asList("Alice", "Bob", "Charlie", "David");  //创建 不可变集合
        List<String> names=new ArrayList<>( ns );   //转为可变集合，以便后面使用 removeIf删除元素
        // 定义一个Predicate，判断字符串长度是否大于3
        Predicate<String> lengthPredicate = s -> s.length() > 3;
        // 使用removeIf方法移除不满足Predicate的元素
        names.removeIf(lengthPredicate);
        System.out.println( "使用Predicate过滤集合:"+names); // [Bob]


        //TODO: Predicate<T> and 方法使用
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Predicate<Integer> predicate1 = x -> x > 3;
        Predicate<Integer> predicate2 = x -> x < 9;
        List<Integer> collect = list.stream().filter(  predicate1.and(predicate2)  ).collect(Collectors.toList());
        System.out.println("Predicate<T> and 方法使用:"+collect);

        //TODO: or方法使用
        collect = list.stream().filter(predicate1.or(predicate2)).collect(Collectors.toList());
        System.out.println("or方法使用:"+collect);
        //TODO: negate使用  否定
        List<String> list3 = Arrays.asList("java","c++","c","c#","php","kotlin","javascript");
        Predicate<String> predicate3 = x -> x.endsWith("+");
        List<String> collect3 = list3.stream().filter(predicate3.negate()).collect(Collectors.toList());
        System.out.println(" negate使用  否定:"+collect3);
        //TODO: test使用
        List<String> list4 = Arrays.asList("java", "c++", "c", "c#", "php", "kotlin", "javascript");
        Predicate<String> predicate4 = x -> x.endsWith("+");
        List<String> collect4 = list4.stream().filter(  predicate4::test ).collect(Collectors.toList());
        System.out.println("test使用:"+collect4);
        //TODO:链式调用
        Predicate<String> predicate5 = x -> x.startsWith("c");
        //以c开头或者以t结尾
        boolean ret = predicate5.or(x -> x.endsWith("t")).test("javascript");
        System.out.println( "链式调用:"+ ret);
        //!（以c开头且长度等于4）
        boolean ret2 = predicate5.and(x -> x.length() == 4).negate().test("java");
        System.out.println("链式调用:"+ret2);


        // 使用BiPredicate判断两个字符串是否相等
        BiPredicate<String, String> equalPredicate = (s1, s2) -> s1.equals(s2);
        System.out.println(equalPredicate.test("Hello", "Hello")); // true
        System.out.println(equalPredicate.test("Hello", "World")); // false
    }
}
