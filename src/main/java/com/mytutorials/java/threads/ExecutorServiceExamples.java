package com.mytutorials.java.threads;

import java.util.concurrent.*;
import java.util.logging.Logger;

public class ExecutorServiceExamples {

    private static final Logger LOG = Logger.getLogger(ExecutorServiceMain.class.getName());

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<?> future = executorService.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LOG.info("After 10 seconds runnableTask"); //
        });


        CompletableFuture<String> completableFuture = calculateAsync();
        // CompletableFuture.supplyAsync(() -> "Hello");

        String result = completableFuture.get();
        LOG.info("result = " + result); // result = Hello


        // ScheduledExecutorService with Callable Runnable

        Callable<Object> callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(5000);
            return "Task's execution";
        };

        Runnable runnableTask = () -> {
            LOG.info("After 10 seconds runnableTask"); // This will run after 10 seconds
        };

        try (ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2)) {
            ScheduledFuture<Object> resultFuture
                    = scheduledExecutorService.schedule(callableTask, 1, TimeUnit.SECONDS);

            // Fixed delay
            ScheduledFuture<?> scheduledFuture =
                    scheduledExecutorService.scheduleAtFixedRate(runnableTask, 100, 450, TimeUnit.MILLISECONDS);
        }
    }

    public static CompletableFuture<String> calculateAsync() {

        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        try (ExecutorService executorService = Executors.newCachedThreadPool()) {

            executorService.submit(() -> {
                Thread.sleep(500);
                completableFuture.complete("Hello");
                return null;
            });
            return completableFuture;
        }
    }
}
