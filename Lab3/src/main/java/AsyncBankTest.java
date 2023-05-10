package main.java;

import java.util.ArrayList;

public class AsyncBankTest {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {
        Bank bank = new Bank(NACCOUNTS, INITIAL_BALANCE);
        ArrayList<TransferThread> threads = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NACCOUNTS; i++) {
            TransferThread t = new TransferThread(bank, i, INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start();
            threads.add(t);
        }

        try {
            for (TransferThread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long wastedTime = endTime - startTime;

        System.out.println("End sum: " + bank.getSum());
        System.out.println("Wasted time: " + wastedTime);
    }
}