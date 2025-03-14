package ru.nsu.anisimov;

class Baker implements Runnable {
    private final int id;
    private final int speed;
    private final OrderQueue orderQueue;
    private final Storage storage;

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
                System.out.println("[Пекарь #" + id + "] Готовит " + order);
                Thread.sleep(speed * 1000L);
                storage.storeOrder(order);
            }

        } catch (InterruptedException e) {
            System.out.println("[Пекарь #" + id + "] Завершает работу");
            Thread.currentThread().interrupt();
        }
    }
}