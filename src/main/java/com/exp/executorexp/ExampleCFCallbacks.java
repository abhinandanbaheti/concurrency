package com.exp.executorexp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

//https://www.callicoder.com/java-8-completablefuture-tutorial/

public class ExampleCFCallbacks {

    Map<Integer, String> hm = new HashMap<>();

    {
        hm.put(1, "a");
        hm.put(2, "b");
    }

    public void testCallback() throws Exception {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(
                () -> {
                    return "Hello";
                });

        // thenApply is the call back method
        CompletableFuture cf1 = cf.thenApply(t ->
        {
            return t + " World";
        });

        // blocking call get()
        System.out.println(cf1.get());
    }

    public void testCallback0() throws Exception {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(
                () -> {
                    return "Hello";
                });

        // thenApply is the call back method
        CompletableFuture cf1 = cf.thenApply(t ->
        {
            return t + " World";
        });

        // blocking call get()
        System.out.println(cf1.get());
    }

    public void testCallback1() throws Exception {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(
                () -> {
                    return "Hello";
                }).thenApply(t -> {
            return t + " World";
        }).thenApply(k -> {
            return k + "!!!";
        });

        // blocking call get()
        System.out.println(cf.get());
    }

    public void testCallback2(ExecutorService executors) throws Exception {
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {

            return "Some Result:" + Thread.currentThread().isDaemon();
        },executors).thenApplyAsync(result -> {
            // Executed in a different thread from ForkJoinPool.commonPool()
            return result + " : Processed Result:" + Thread.currentThread().isDaemon();
        },executors);

        // blocking call get()
        System.out.println(cf.get());
    }

    //Combine two dependent futures using thenCompose()
    public void testCompose1() throws Exception {
        // thenCompose method
    }

    //Combine two independent futures using thenCombine()
    public void testCombine2() throws Exception {
    }

    // Combining multiple CompletableFutures together
    public void testAllOf() throws Exception {
        // CompletableFuture.allOf is used in scenarios when you have a List of independent futures that you want to run in parallel and do something after all of them are complete.
    }


    public void testWrapperSupplyFunction() throws Exception {
        CompletableFuture<String> cf = supplyAsync(() -> {
            return "Hello";
        }).thenApply(result -> {
            return result + " World";
        });
        // blocking call get()
        System.out.println(cf.get());
    }

    public <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Do some framework related operations");
            return supplier.get();
        });
    }


    public static void main(String[] args) {
        ExampleCFCallbacks ec = new ExampleCFCallbacks();
        try {
            //ec.testCallback();
            //ec.testCallback1();
            ExecutorService executors = Executors.newFixedThreadPool(5);

            ec.testCallback2(executors);
            //ec.testCompose1();
            //ec.testWrapperSupplyFunction();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
