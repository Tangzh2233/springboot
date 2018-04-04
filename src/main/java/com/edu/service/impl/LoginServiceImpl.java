package com.edu.service.impl;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.ForkedTransaction;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.edu.dao.domain.Dlog;
import com.edu.dao.domain.User;
import com.edu.dao.mapper.ideaDemo.DlogMapper;
import com.edu.dao.mapper.ideaDemo.UserMapper;
import com.edu.service.ILoginService;
import com.edu.something.RMI.RmiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/9/14.
 */
@Service
public class LoginServiceImpl implements ILoginService{

    private final static ThreadLocal<mContext> myContext = new ThreadLocal<>();
    private final static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Resource
    private DlogMapper dlogMapper;

    @Override
    public User login(String name) {
        createCat();
        Transaction t = null;
        double account = 1;
        User user = new User();
        try {
            if("liu".equals(name)){
                t = Cat.getProducer().newTransaction("PigeonService", "method1");
                t.setStatus(Transaction.SUCCESS);
                Cat.logEvent("login事件","失败", "1","11111");
                t.setStatus("1");
            }else if("yu".equals(name)){
                mContext context = this.getContext();
                t = Cat.getProducer().newTransaction("PigeonCall", "method1");
                Cat.logEvent("PigeonCall.app","local");
                Cat.logEvent("PigeonCall.server","client");
                Cat.logEvent("PigeonCall.port","8848");
                Cat.logRemoteCallClient(context);
                RmiClient client = new RmiClient();
                //调用
                client.start(context);

            }else {
               // t = Cat.getProducer().newTransaction("loginTime", "ABC");
                t = Cat.getProducer().newTransaction("Cache.Redis","缓存test");
                t.setStatus(Transaction.SUCCESS);
                Event event = Cat.newEvent("Cache.Server", "127.0.0.1");
                event.setStatus("1");
                t.addChild(event);
            }
            Cat.logMetricForCount(name);
            Cat.logMetricForDuration(name,1);
            Cat.logMetricForSum("金额",account);
            if("exception".equals(name)){
                throw new NullPointerException();
            }
            user = userMapper.getUserByName(name);
        }catch (Exception e){
            t.setStatus(e);
        }finally {
            t.setStatus(Transaction.SUCCESS);
            t.complete();
        }
        logger.info("返回参数: "+user.toString());
        return user;
    }

    @Override
    public <User> User select(User user) {
        return user;
    }

    @Override
    public int addDlog(Dlog dlog) {
        int tableName = (int)Integer.valueOf(dlog.getId());
        System.out.println("表名: "+tableName%2);
        return dlogMapper.addDlog(dlog,String.valueOf(tableName%2));
    }

    public static void main(String[] args) {
        Transaction t = Cat.getProducer().newTransaction("Cross", "ABC");
        Cat.logEvent("Cross","main","0","");
        User user = new User();
        try {
            t.setStatus(Transaction.SUCCESS);
        }catch (Exception e){
            t.setStatus(e);
        }finally {
            t.complete();
        }
    }
    /*private void test(){
        LoginServiceImpl loginService = new LoginServiceImpl();
    }*/
    private mContext getContext(){
        mContext context = myContext.get();
        if(context==null){
            context = new mContext();
            myContext.set(context);
        }
        return context;
    }

    private void createCat(){
        Transaction t = Cat.getProducer().newTransaction("Tang", "a");
        Cat.logEvent("Tangevent","b");
        ForkedTransaction ft = Cat.getProducer().newForkedTransaction("Forke", "a");
        MyThread myThread = new MyThread(ft);
        new Thread(myThread).start();
        t.setStatus(Message.SUCCESS);
        t.complete();

    }
    class MyThread implements Runnable{
        private ForkedTransaction forkedTransaction;

        public MyThread(ForkedTransaction f){
            this.forkedTransaction = f;
        }
        @Override
        public void run() {
            forkedTransaction.fork();
            Cat.logEvent("forkevent","b");
            forkedTransaction.setStatus(Message.SUCCESS);
            forkedTransaction.complete();
        }
    }
}
