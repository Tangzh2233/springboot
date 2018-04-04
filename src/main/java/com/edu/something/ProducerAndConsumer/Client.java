package com.edu.something.ProducerAndConsumer;

import javax.swing.table.TableRowSorter;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Tangzhihao
 * @date 2018/3/30
 */

public class Client {
    public static void main(String[] args) {
        BlockingDeque<Integer> deque = new LinkedBlockingDeque();
        Produer produer = new Produer(deque);
        Consumer consumer = new Consumer(deque);
        Consumer consumer1 = new Consumer(deque);
        new Thread(produer,"Producer").start();
        new Thread(consumer,"Consumer").start();
        new Thread(consumer1,"Consumer2").start();

    }
}
