package com.edu.something.ProducerAndConsumer;

import java.util.concurrent.BlockingDeque;

/**
 * @author Tangzhihao
 * @date 2018/3/30
 */

public class Consumer implements Runnable{
    private BlockingDeque<Integer> deque;

    public Consumer(BlockingDeque deque){
        this.deque = deque;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag){
            try {
                doNext();
                /*if(deque.size()==0){
                    flag = false;
                }*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int doNext() throws InterruptedException {
        Integer poll = deque.take();
        System.out.println(Thread.currentThread().getName()+"消费："+poll.toString());
        return poll;
    }
}
