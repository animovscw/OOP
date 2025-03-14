package ru.nsu.anisimov;

/**
 * The class processes orders and places them in storage.
 */
class Baker implements Runnable {
    private final int id;
    private final int speed;
    private final OrderQueue orderQueue;
    private final Storage storage;

    /**
     * Creates a new baker.
     *
     * @param id identification
     * @param speed speed
     * @param orderQueue queue
     * @param storage storage
     */
    public Baker(int id, int speed, OrderQueue orderQueue, Storage storage) {
        this.id = id;
        this.speed = speed;
        this.orderQueue = orderQueue;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = orderQueue.takeOrder();
                logAction(order.getId(), "Готовится");
                Thread.sleep(speed * 1000L);
                storage.storeOrder(order);
                logAction(order.getId(), "На складе");
            }
        } catch (InterruptedException e) {
            System.out.println("[Пекарь #" + id + "] Завершает работу");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Logging.
     *
     * @param orderId identification of the order
     * @param state state order
     */
    private void logAction(int orderId, String state) {
        System.out.println("[" + orderId + "] " + state);
    }
}