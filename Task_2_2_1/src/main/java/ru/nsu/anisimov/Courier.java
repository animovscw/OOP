package ru.nsu.anisimov;

/**
 * Represents a courier who delivers orders.
 */
public class Courier implements Runnable {
    private final int id;
    private final int capacity;
    private final Storage storage;

    /**
     * Creates a new courier.
     *
     * @param id identification
     * @param capacity capacity
     * @param storage storage
     */
    public Courier(int id, int capacity, Storage storage) {
        this.id = id;
        this.capacity = capacity;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < capacity; ++i) {
                    Order order = storage.takeOrder();
                    logAction(order.getId(), "Доставляется");
                    Thread.sleep(3000);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("[Курьер #" + id + "] Завершает работу");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Logging.
     *
     * @param orderId order identification
     * @param state order state
     */
    private void logAction(int orderId, String state) {
        System.out.println("[" + orderId + "] " + state);
    }
}