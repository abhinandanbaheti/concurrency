package com.exp.executorexp;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Example3CompletableFut {
    public static void main(String[] args) {
        try {
            List<Integer> list = Arrays.asList(5, 9, 14);

            list.stream().map(num -> CompletableFuture.supplyAsync(
                    () -> getNumber(num))).map(CompletableFuture -> CompletableFuture.thenApply(n -> n * n)).map(t -> t.join()).
                    forEach(s -> System.out.println(s));


            List<CompletableFuture<Integer>> LL = list.stream().map(num -> CompletableFuture.supplyAsync(()->getNumber(num))).collect(Collectors.toList());

            System.out.println(CompletableFuture.allOf(LL.toArray(new CompletableFuture[LL.size()]))
                    .thenApply(v -> LL.stream().map(CompletableFuture::join).collect(Collectors.toList())).join());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getNumber(int a) {
        try {
            Thread.sleep(a * 1000);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return a * a;
    }
}