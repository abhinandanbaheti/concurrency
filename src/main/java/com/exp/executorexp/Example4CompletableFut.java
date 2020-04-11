package com.exp.executorexp;
//https://www.callicoder.com/java-8-completablefuture-tutorial/
//https://dzone.com/articles/20-examples-of-using-javas-completablefuture
//https://www.deadcoderising.com/java8-writing-asynchronous-code-with-completablefuture/

// Concurrent Async operations over collections

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Example4CompletableFut {

    public void testWrapperSupplyFunction() throws Exception {
        List<emp> el = getEmp();
        List<CompletableFuture<String>> LL = el.stream().map( t -> this.supplyAsync(() -> {
            String name = t.getName();
            System.out.println(name);
            return "Hello " + name;
        })).collect(Collectors.toList());

        CompletableFuture<Void> futures = CompletableFuture.allOf(LL.toArray(new CompletableFuture[LL.size()]));
        System.out.println(futures.isDone() + ":" +  futures.isCompletedExceptionally());
        System.out.println(futures.thenApply(v -> LL.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()))
                .join());
    }

    public <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Do some framework related operations");
            return supplier.get();
        });
    }

    public static void main(String[] args) throws Exception{

        List<emp> el = getEmp();
        List<CompletableFuture<Integer>> LL = el.stream().map(t -> CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("id:" + t.getId());
                    int sal = calcSal(t.getId());
                    System.out.println("sal:" + sal);
                    return sal;
                }
        )).collect(Collectors.toList());


        System.out.println("Status Check 1: " + LL);

        System.out.println("test1");
        CompletableFuture<Void> tfuture = CompletableFuture.allOf(LL.toArray(new CompletableFuture[LL.size()]));
        System.out.println(tfuture.isDone() + ":" + tfuture.isCompletedExceptionally());

        System.out.println("test2");
        System.out.println(tfuture.thenApply(v -> LL.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()))
                .join());


        System.out.println("Status Check 2: " + LL);
        System.out.println(tfuture.isDone() + ":" + tfuture.isCompletedExceptionally());


        System.out.println("Supply Async Wrapper function: " + LL);
        Example4CompletableFut ec = new Example4CompletableFut();
        ec.testWrapperSupplyFunction();

    }


    public static List<emp> getEmp() {
        List<emp> emp = new ArrayList<>();
        emp.add(new emp(1, "a"));
        emp.add(new emp(2, "b"));
        return emp;
    }

    public static int calcSal(int id) {

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return id * 100;
    }
}

class emp {
    int id;
    String name;

    public emp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        emp emp = (emp) o;

        if (id != emp.id) return false;
        return name != null ? name.equals(emp.name) : emp.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
