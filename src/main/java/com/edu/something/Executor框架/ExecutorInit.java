package com.edu.something.Executor框架;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.concurrent.*;

/**
 * @author Tangzhihao
 * @date 2018/3/1
 * description Executos Learning
 */

public class ExecutorInit {
    private Executor executor;
    private ExecutorService executorService;
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private ThreadPoolExecutor threadPoolExecutor;

    public void initExecutors(){
        //创建固定大小的线程池
        ExecutorService fixPool = Executors.newFixedThreadPool(10);
        //创建一个单线程的Executor(执行者)
        ExecutorService singlePool = Executors.newSingleThreadExecutor();

        ExecutorService cachePool = Executors.newCachedThreadPool();

        ScheduledExecutorService schedulePool = Executors.newScheduledThreadPool(1);

    }

    public static void main(String[] args) {
        ExecutorInit init = new ExecutorInit();
        init.initFixedPool();
    }

    /**
     * @author: Tangzhihao
     * @date: 2018/3/1 19:09
     * @description:创建固定大小线程池
     */
    public void initFixedPool(){
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        System.out.println("=========fixPool Start============");
        for (int i=0;i<10;i++) {
            fixedPool.execute(new TestThread());
            Future<?> future = fixedPool.submit(new TestThread());
        }
        fixedPool.shutdown();
    }

    class TestThread implements Runnable{

        @Override
        public void run() {
            System.out.println("=========Thread Start==========");
        //    for (int i=0;i<4;i++){
            System.out.println("我是线程"+Thread.currentThread().getId());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //    }
        }
    }
}
