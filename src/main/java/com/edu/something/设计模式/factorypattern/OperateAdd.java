package com.edu.something.设计模式.factorypattern;

/**
 * Created by Administrator on 2017/8/11.
 */
public class OperateAdd extends Operate{
    public double getResult(){
        return getNumber_a()+getNumber_b();
    }
}
