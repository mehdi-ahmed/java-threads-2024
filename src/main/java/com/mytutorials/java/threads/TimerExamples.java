package com.mytutorials.java.threads;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

public class TimerExamples {

    public static void main(String[] args) {

        TimerTask task = new TimerTask() {

            public void run() {
                System.out.println("Task performed on: " + new Date() + "n " + "\r\n"
                        + "Thread's name: " + Thread.currentThread().getName());
            }
        };

        Timer timer = new Timer("Timer");
        long delay = 1000L;
        long period = 5000L;

        // timer.schedule(task, delay);

        timer.scheduleAtFixedRate(task, delay, period);
    }
}
