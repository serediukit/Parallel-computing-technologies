package main.java;

import java.util.concurrent.atomic.AtomicBoolean;

public class Load {
    public static void main(String[] args) {
        for(int row = 0; row < 100; row++) {
            AtomicBoolean isThread1Done = new AtomicBoolean(false);
            AtomicBoolean isDone = new AtomicBoolean(false);

            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < 100; i++) {
                    System.out.print("-");
                    isThread1Done.set(true);
                    while (isThread1Done.get()) {
                        Thread.yield();
                    }
                    if(i == 99) {
                        isDone.set(true);
                    }
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < 100; i++) {
                    System.out.print("|");
                    isThread1Done.set(false);
                    while (!isThread1Done.get() && !isDone.get()) {
                        Thread.yield();
                    }
                }
            });

            thread1.start();
            thread2.start();

            try {
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println();
        }
    }
}
