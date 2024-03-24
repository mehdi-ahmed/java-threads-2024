package com.mytutorials.java.threads;

public class NewThread extends Thread {

    public void run() {
        long startTime = System.currentTimeMillis();
        int i = 0;

        while (true) {

            System.out.println(this.getName() + ": New Thread is running..." + i++);

            //Wait for one sec so it doesn't print too fast
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
