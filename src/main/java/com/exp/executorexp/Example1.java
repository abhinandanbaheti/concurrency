package com.exp.executorexp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example1 {

    public static void main(String[] args){
        ExecutorService executors = Executors.newFixedThreadPool(3);
        Test tr = new Test();
        for(int i=0; i<5; i++){
            Thread t = new Thread(tr);
            t.currentThread().setName("Thread-" + (int) (Math.random() * 100D));
            executors.execute(t);
        }
        executors.shutdown();
    }
}

class Test implements Runnable{

    public void run() {
        System.out.println("Executing: " + Thread.currentThread().getName());
        int num = (int) (Math.random() * 10D);
        System.out.println("waiting for: " + num + " : "  + Thread.currentThread().getName());
        try {
            Thread.sleep(num * 1000);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
