package ru.nsu.anisimov;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Custom blocking queue implementation.
 */
public class BlockingQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    /**
     * Creates blocking queue.
     *
     * @param capacity capacity
     */
    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Inserts an element into the queue.
     *
     * @param item the element
     * @throws InterruptedException exception
     */
    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.add(item);
        notifyAll();
    }

    /**
     * Removes and returns the first element from the queue.
     *
     * @return element
     * @throws InterruptedException exception
     */
    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T item = queue.poll();
        notifyAll();
        return item;
    }

    /**
     * Returns the size of the queue.
     *
     * @return queue size
     */
    public synchronized int size() {
        return queue.size();
    }
}