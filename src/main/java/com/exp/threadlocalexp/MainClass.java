package com.exp.threadlocalexp;

// Singleton class

public class MainClass {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Test());
            t.start();
//            try {
//                t.join();
//            }
//            catch(Exception e){
//
//            }
        }
    }
}

class Test implements Runnable {

    public void run() {
        int num = ThreadLocalManager.get();
        //sleep(num);
        System.out.println("Getting val: " + Thread.currentThread().getName() + " : " + ThreadLocalManager.get());
    }

    public void sleep(int num) {
        try {
            Thread.sleep(num * 1000);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

class ThreadLocalManager {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();


    public static Integer get() {
        if (threadLocal.get() == null) {
            set();
        }
        return threadLocal.get();
    }

    public static void set() {
        int num = (int) (Math.random() * 10D);
        System.out.println("Setting val: " + Thread.currentThread().getName() + " : " + num);
        threadLocal.set(num);
    }
}
