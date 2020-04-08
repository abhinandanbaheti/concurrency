package com.exp.threadlocalexp;


public class ThreadLocalExp {

    public static void main(String[] args) {
        Test1 runnable = new Test1();
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        t1.start();
        try {
            t1.join(); //wait for thread 1 to terminate   or // starts second thread after when first thread t1 has died.
            //t2.join(); //wait for thread 2 to terminate
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        t2.start();
        t3.start();
    }
}

class Test1 implements Runnable {
    private static int count=0;

    ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    public void run() {
        count++;
        System.out.println("************** Invoked *********** : " + count);
        int val= (int) (Math.random() * 100D);
        Thread.currentThread().setName("Thread-" + val);
        System.out.println(Thread.currentThread().getName() + " - Setting val " + val);
        threadLocal.set(val);
        try{
            Thread.sleep(2000);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(Thread.currentThread().getName() + "- val= " + threadLocal.get());
    }
}
