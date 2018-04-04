package com.edu.controller.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.*;
import com.jiupai.cornerstone.monitor.cat.dubbo.CatTransactionFilter;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tangzhihao
 * @date 2018/1/19
 */
@Configuration
public class MyBubboFilter implements Filter{

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        URL url = invoker.getUrl();
        String sideKey = url.getParameter(Constants.SIDE_KEY);
        String logName = invoker.getInterface().getSimpleName()+"."+invocation.getMethodName();
        Result result = invoker.invoke(invocation);

        return null;
    }

    public void myFilter(Result result){
        //todo
    }
}
