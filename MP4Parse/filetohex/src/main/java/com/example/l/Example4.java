package com.example.l;

public class Example4 {
    public static void main(String[] args) {
        boy boys = new boy();
        boy.sex = "男孩";//静态变量的继承
        System.out.println("继承而来的字段sex的值为：" + boy.sex);
        boys.method1();//来自父类的方法
        boys.method2();//自己改写后的方法
    }
}
