package com.edu.something.设计模式.FactoryAndStrategy;

/**
 * Created by Administrator on 2017/8/9.
 */
public class OperateSub extends  Operate{

    public double getResult(){
        double result = 0;
        result = getNumber_a()-getNumber_b();
        return  result;
    }
}
