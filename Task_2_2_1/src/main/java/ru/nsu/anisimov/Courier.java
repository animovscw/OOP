package ru.nsu.anisimov;

public class Courier implements Runnable {
    private final int id;
    private final int capacity;
    private final Storage storage;

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
                    System.out.println("[Курьер #" + id + "] Доставляет " + order);
                    Thread.sleep(3000);
                }
            }

        } catch (InterruptedException e) {
            System.out.println("[Курьер #" + id + "] Завершает работу");
            Thread.currentThread().interrupt();
        }
    }
}
