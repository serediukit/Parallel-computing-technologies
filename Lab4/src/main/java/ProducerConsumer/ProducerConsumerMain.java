package main.java.ProducerConsumer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ProducerConsumerMain {
    public static void main(String[] args) {
        final int size = 1000;
        int[] array = new int[size];
        for(int i = 0; i < array.length; i++) {
            array[i] = ((int) (Math.random() * 100)) % 100;
        }

                Store store = new Store();
        Producer producer = new Producer(store, array);
        Consumer consumer = new Consumer(store, array.length);

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        forkJoinPool.submit(producer);
        forkJoinPool.submit(consumer);

        forkJoinPool.shutdown();

        try {
            forkJoinPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Thread producerThread = new Thread(producer);
//        Thread consumerThread = new Thread(consumer);
//
//        producerThread.start();
//        consumerThread.start();
//
//        try {
//            producerThread.join();
//            consumerThread.join();
//        } catch (InterruptedException ignored) {}

        System.out.println("\nProducer history: " + store.getHistory());
    }
}
