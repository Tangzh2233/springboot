package com.edu.something.设计模式.代理模式and装饰模式.代理模式;

/**
 * Created by Administrator on 2017/8/11.
 * 代理类，代理Subject的实际实现类RealSubject
 */
public class Proxy implements Subject{

    private RealSubject realSubject;
    public Proxy(){
        realSubject = new RealSubject();
    }
    @Override
    public void sendFlower() {
        realSubject.sendFlower();
    }

    @Override
    public void sendDolls() {
        realSubject.sendDolls();
    }

    @Override
    public void sendChocolate() {
        realSubject.sendChocolate();
    }
}
