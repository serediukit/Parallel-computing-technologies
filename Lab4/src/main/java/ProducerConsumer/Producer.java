package main.java.ProducerConsumer;

public class Producer implements Runnable {
    private final Store store;
    int[] array;

    Producer(Store store, int[] array){
        this.store = store;
        this.array = array;
    }

    public void run(){
        for (int a : array) {
            try {
                store.put(a);
            } catch (InterruptedException ignored) {}
        }
    }
}
