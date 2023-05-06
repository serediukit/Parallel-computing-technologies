package main.java;

public class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
        printCount();
    }

    public synchronized void decrement() {
        count--;
        printCount();
    }

    public int getCount() {
        return count;
    }

    public void printCount() {
        System.out.println(count);
    }
}

