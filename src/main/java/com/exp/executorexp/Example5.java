package com.exp.executorexp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

// ThreadPoolExecutor + Callables + ThreadLocal usage

public class Example5 {
    public static void main(String[] args) {
        ExecutorService threadpool = Executors.newFixedThreadPool(4);
        List<Future<Employee>> futureList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            TestCallable tc = new TestCallable();
            futureList.add(threadpool.submit(tc));
        }

        futureList.stream().forEach(t -> {
            try {
                System.out.println("ID: " + t.get().getId() + ", NAME: " + t.get().getName());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
        threadpool.shutdown();
    }
}


class TestCallable implements Callable {

    @Override
    public Object call() throws Exception {
        Employee e = TestThreadLocal.get();
        return e;
    }
}

// singleton class
class TestThreadLocal {
    private static ThreadLocal<Employee> th = new ThreadLocal<>();
    static AtomicInteger at = new AtomicInteger();

    public static Employee get() {
        if (th.get() == null) {
            set();
        }
        return th.get();
    }

    private static void set() {
        String name = Thread.currentThread().getName();
        int num = at.incrementAndGet();
        th.set(new Employee(name, num));
    }
}

class Employee {
    String name;
    int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
