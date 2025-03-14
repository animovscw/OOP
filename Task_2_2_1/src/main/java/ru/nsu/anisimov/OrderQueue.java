package ru.nsu.anisimov;

/**
 * Represents order queue.
 */
public class OrderQueue {
    private final BlockingQueue<Order> orders = new BlockingQueue<>(100);

    /**
     * Adding order to the list.
     *
     * @param order order
     */
    public void addOrder(Order order) {
        try {
            orders.put(order);
            System.out.println("[Принят] " + order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
