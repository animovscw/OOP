package ru.nsu.anisimov;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Storage {
    private final BlockingQueue<Order> storage;

    public Storage(int capacity) {
        this.storage = new LinkedBlockingQueue<>(capacity);
    }

    public void storeOrder(Order order) throws InterruptedException {
        storage.put(order);
        System.out.println("[На складе] " + order);
    }

    public Order takeOrder() throws InterruptedException {
        return storage.take();
    }

    public synchronized int getStorageSize() {
        return storage.size();
    }
}
