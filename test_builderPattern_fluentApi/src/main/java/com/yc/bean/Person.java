package com.yc.bean;


public class Person {
    private String name;
    private int age;
    private String gender;

    //以下代码都是固定的 可以用工具生成
    public Person( Builder b){
        this.name = b.name;
        this.age = b.age;
        this.gender = b.gender;
    }
    //fluent
    //Person p = new Person.builder().name("张三").age(18).gender("男").build();
    // 内部静态类Builder ， 与外部类属性一致  用于创建对象的属性值
    public static class Builder{
        //通常拥有宿主实体类的全部属性
        private String name;
        private int age;
        private String gender = "男";

        // Person p = new Person.builder().name("张三").age(18).gender("男").build();
        //以实体属性名作为方法名， 为属性赋值 并返回this builder对象
        public Builder name(String name){
            this.name = name;
            return this; // ***为了支持链式编程
        }
        public Builder age(int age){
            this.age = age;
            return this; // ***为了支持链式编程
        }
        public Builder gender(String gender) {
            this.gender = gender; // ***为了支持链式编程
            return this;
        }
        //最后提供一个build方法， 使用builder收集来的属性创建实体类
        //实体类的创建方式多种多样， 只要达到目的即可 ，通常实体类提供全属性的构造器 或者Builder为参数的构造器
        public Person build(){
            return new Person(this);
        }
    }

    //公有的static方法， 用于创建Builder对象 Person.builder()
    public static Builder builder(){
        return new Builder();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
