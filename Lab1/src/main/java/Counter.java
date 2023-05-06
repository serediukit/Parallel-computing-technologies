package main.java;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
            printCount();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            count--;
            printCount();
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }

    public void printCount() {
        System.out.println(count);
    }
}

