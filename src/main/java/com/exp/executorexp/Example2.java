package com.exp.executorexp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Example2 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future> futs = new ArrayList<Future>();
        for (int i = 0; i < 5; i++) {
            Test1 tr = new Test1(i);
            futs.add(executorService.submit(tr));
        }
        for (Future fut : futs) {
            try {
                System.out.println("Completed : " + fut.get());
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        executorService.shutdown();
    }
}

class Test1 implements Callable {

    int val;
    public Test1(int val){
        this.val = val;
    }

    public Object call() throws Exception {
        int val1= (int) (Math.random() * 10D);
        Thread.currentThread().setName(this.val + ":"+val1);
        System.out.println("Setting val: Thread" + Thread.currentThread().getName()  + " : Waiting for : " + val1);
        Thread.sleep(val1*1000);
        return Thread.currentThread().getName();
    }
}
