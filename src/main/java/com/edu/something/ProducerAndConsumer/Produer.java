package com.edu.something.ProducerAndConsumer;

import java.util.Random;
import java.util.concurrent.BlockingDeque;

/**
 * @author Tangzhihao
 * @date 2018/3/30
 */

public class Produer implements Runnable{
    private BlockingDeque<Integer> deque;

    public Produer(BlockingDeque deque){
        this.deque = deque;
    }

    @Override
    public void run() {
        int i = 0;
        while (i<1000){
            try {
                doNext();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
    public void doNext() throws InterruptedException {
        int i = new Random().nextInt(100);
        deque.put(i);
        System.out.println(Thread.currentThread().getName()+"生产："+i);
    }
}
