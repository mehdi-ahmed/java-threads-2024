package com.mytutorials.java.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class ExecutorServiceMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        Logger LOG = Logger.getLogger(ExecutorServiceMain.class.getName());

        ExecutorService executorService = newFixedThreadPool(10);

        LOG.info("Before runnableTask");
        Runnable runnableTask = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
                LOG.info("After 10 seconds runnableTask"); // This will run after 10 seconds
            } catch (InterruptedException ex) {
                LOG.warning(ex.getMessage());
            }
        };

        executorService.shutdown();
        executorService.execute(runnableTask);


        Callable<String> callableTask0 = () -> {
            TimeUnit.MILLISECONDS.sleep(5000);
            return "Task's execution";
        };

        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask0);
        callableTasks.add(callableTask0);
        callableTasks.add(callableTask0);

        List<Future<String>> futures = executorService.invokeAll(callableTasks);
        //String result = executorService.invokeAny(callableTasks);

        //Future
        Callable<String> callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(5000);
            return "Task's execution";
        };

        Future<String> future = executorService.submit(callableTask);
        String result;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException ex) {
            LOG.warning(ex.getMessage());
        }

        result = future.get(200, TimeUnit.MILLISECONDS);


        // ScheduledExecutorService
        // try-with-resources
        try (ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()) {
            ScheduledFuture<String> resultFuture =
                    scheduledExecutorService.schedule(callableTask, 1, TimeUnit.MILLISECONDS);

            // delays for one second before executing callableTask.
            scheduledExecutorService.scheduleAtFixedRate(runnableTask, 100, 450, TimeUnit.MILLISECONDS);
        }
    }
}
