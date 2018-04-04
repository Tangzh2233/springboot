package com.edu.something.设计模式.代理模式and装饰模式.代理模式;

/**
 * Created by Administrator on 2017/8/11.
 */
public class Client {

    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        proxy.sendChocolate();
        proxy.sendDolls();
        proxy.sendFlower();
    }
}
