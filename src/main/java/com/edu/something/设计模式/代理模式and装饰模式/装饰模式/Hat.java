package com.edu.something.设计模式.代理模式and装饰模式.装饰模式;

/**
 * Created by Administrator on 2017/8/9.
 */
public class Hat extends Decorator{
    public void show(){
        super.show();
        System.out.println("帽子");
    }
}
