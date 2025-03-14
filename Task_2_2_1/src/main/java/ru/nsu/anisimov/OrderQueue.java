package ru.nsu.anisimov;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents order queue.
 */
public class OrderQueue {
    private final BlockingQueue<Order> orders = new LinkedBlockingQueue<>();

    /**
     * Adding order to the list.
     *
     * @param order order
     */
    public void addOrder(Order order) {
        orders.add(order);
        System.out.println("[Принят] " + order);
    }

    /**
     * Picking order.
     *
     * @return taken order
     * @throws InterruptedException exception
     */
    public Order takeOrder() throws InterruptedException {
        return orders.take();
    }

    /**
     * Gets the size of the queue.
     *
     * @return size
     */
    public int getQueueSize() {
        return orders.size();
    }
}
