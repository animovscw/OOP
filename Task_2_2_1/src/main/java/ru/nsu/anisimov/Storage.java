package ru.nsu.anisimov;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents storage of pizzeria.
 */
public class Storage {
    private final BlockingQueue<Order> storage;

    /**
     * Creates storage.
     *
     * @param capacity storage capacity
     */
    public Storage(int capacity) {
        this.storage = new LinkedBlockingQueue<>(capacity);
    }

    /**
     * Stores the order in storage.
     *
     * @param order order
     * @throws InterruptedException exception
     */
    public void storeOrder(Order order) throws InterruptedException {
        storage.put(order);
        System.out.println("[На складе] " + order);
    }

    /**
     * Picks the order
     *
     * @return taken order
     * @throws InterruptedException exception
     */
    public Order takeOrder() throws InterruptedException {
        return storage.take();
    }

    /**
     * Gets the size of the storage.
     *
     * @return size
     */
    public synchronized int getStorageSize() {
        return storage.size();
    }
}
