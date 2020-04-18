package com.exp.executorexp;

import java.util.concurrent.Executor;

public class CustomExecutor {


    public static void main(String[] args) {
        TestExecutor a = new TestExecutor();
        for (int i = 0; i < 5; i++) {
            a.execute(() -> System.out.println("CAlling from main"));
        }
    }
}

class TestExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        try {
            command.run();
            System.out.println("In TestExecutor: waiting for 2 secs");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
