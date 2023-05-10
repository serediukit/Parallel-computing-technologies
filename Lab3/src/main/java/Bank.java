package main.java;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;
    private Lock locker = new ReentrantLock();

    public Bank(int n, int initialBalance){
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
    }

    public synchronized void transferSynchronized(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
    }

    public void transferSynchronizedBlock(int from, int to, int amount) {
        synchronized (this) {
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        }
    }

    public void transferLocker(int from, int to, int amount) {
        locker.lock();
        try {
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        } finally {
            locker.unlock();
        }
    }

    public void test(){
        System.out.println("Transactions:" + ntransacts + " Sum: " + getSum());
    }

    public int size(){
        return accounts.length;
    }

    public int getSum() {
        int sum = 0;
        for (int account : accounts) sum += account;
        return sum;
    }
}
