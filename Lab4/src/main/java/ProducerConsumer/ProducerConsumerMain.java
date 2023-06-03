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


        Store storeT = new Store();
        Producer producerT = new Producer(storeT, array);
        Consumer consumerT = new Consumer(storeT, array.length);

        Thread producerThread = new Thread(producerT);
        Thread consumerThread = new Thread(consumerT);

        long startTimeThread = System.currentTimeMillis();
        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException ignored) {}

        long endTimeThread = System.currentTimeMillis();


        System.out.println("\nProducer history: " + storeT.getHistory());


        Store store = new Store();
        Producer producer = new Producer(store, array);
        Consumer consumer = new Consumer(store, array.length);

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        long startTimeFJP = System.currentTimeMillis();
        forkJoinPool.submit(producer);
        forkJoinPool.submit(consumer);

        forkJoinPool.shutdown();

        try {
            forkJoinPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTimeFJP = System.currentTimeMillis();


        System.out.println("\nProducer history: " + store.getHistory());

        System.out.println("\nTime THREAD: " + (endTimeThread - startTimeThread));
        System.out.println("\nTime FJP: " + (endTimeFJP - startTimeFJP));
    }
}
