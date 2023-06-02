package main.java.ProducerConsumer;

public class Consumer implements Runnable {
    private final Store store;
    private final int limit;

    Consumer(Store store, int limit){
        this.store = store;
        this.limit = limit;
    }

    public void run(){
        while (store.getHistory().size() < limit) {
            try {
                store.get();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
