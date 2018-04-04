package com.edu.something.jdktest.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Tangzhihao
 * @date 2018/3/28
 */

public class TheThread implements Runnable {

    private int ticket = 20;
    Object lockObject = new Object();
    ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            while (ticket > 0) {
                maipiao();
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void maipiao() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "获取锁");
        try {
            if (ticket > 0) {
                ticket--;
                System.out.println(Thread.currentThread().getName() + "正在卖票，还剩" + ticket);
            } else {
                System.out.println("已售完");
                return;
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放锁");
        }

    }

    public void maipiao1() {
        if (lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + "获取锁");
            try {
                if (ticket > 0) {
                    ticket--;
                    System.out.println(Thread.currentThread().getName() + "正在卖票，还剩" + ticket);
                } else {
                    System.out.println("已售完");
                    return;
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放锁");
            }

        }else {
            System.out.println(Thread.currentThread().getName() + "获取锁失败");
        }
    }

    public void maipiao2() throws InterruptedException {
        if (lock.tryLock(4, TimeUnit.SECONDS)) {
            System.out.println(Thread.currentThread().getName() + "获取锁");
            try {
                if (ticket > 0) {
                    ticket--;
                    System.out.println(Thread.currentThread().getName() + "正在卖票，还剩" + ticket);
                } else {
                    System.out.println("已售完");
                    return;
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放锁");
            }

        }else{
            System.out.println(Thread.currentThread().getName() + "获取锁失败");
        }
    }
}
