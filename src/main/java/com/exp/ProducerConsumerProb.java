package com.exp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProducerConsumerProb {


    public static void main(String[] args) {
        BlockingQueue<Integer> LL = new LinkedBlockingDeque();
        Thread t1 = new Thread(new Producer(LL));
        Thread t2= new Thread(new Consumer(LL));
        t1.start();
        t2.start();
    }
}

class Producer implements Runnable {
    BlockingQueue<Integer> LL;

    Producer(BlockingQueue LL) {
        this.LL = LL;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("Putting:: " + i);
                LL.put(i);
                Thread.sleep(1000);
            } catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }

    }
}

class Consumer implements Runnable {
    BlockingQueue<Integer> LL;

    Consumer(BlockingQueue LL) {
        this.LL = LL;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("Taking:: " + LL.take());
            } catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
