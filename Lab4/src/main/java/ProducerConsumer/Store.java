package main.java.ProducerConsumer;

import java.util.*;

public class Store {
    private final Queue<Integer> queue;
    private final List<Integer> history;

    public Store() {
        this.queue = new LinkedList<>();
        this.history = new ArrayList<>();
    }

    public synchronized void get() throws InterruptedException {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        int value = queue.poll();
        System.out.printf("Get %-5d   Current queue %s%n", value, queue);
        notifyAll();
    }

    public synchronized void put(int value) throws InterruptedException {
        while (!queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        queue.offer(value);
        history.add(value);
        System.out.printf("Put %-5d   Current queue %s%n", value, queue);
        notifyAll();
    }

    public List<Integer> getHistory() {
        return history;
    }
}
