package ru.nsu.anisimov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulatorTests {
    private OrderQueue orderQueue;
    private Storage storage;

    @BeforeEach
    void setUp() {
        orderQueue = new OrderQueue();
        storage = new Storage(5);
    }

    @Test
    void testOrderQueueFunctionality() throws InterruptedException {
        Order order = new Order(1);
        orderQueue.addOrder(order);
        assertEquals(order, orderQueue.takeOrder());
    }

    @Test
    void testStorageCapacityLimit() throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            storage.storeOrder(new Order(i));
        }
        assertEquals(5, storage.getStorageSize());

        Thread thread = new Thread(() -> {
            try {
                storage.storeOrder(new Order(6));
            } catch (InterruptedException ignored) {
            }
        });
        thread.start();

        Thread.sleep(500);
        assertEquals(5, storage.getStorageSize());

        storage.takeOrder();
        Thread.sleep(500);
        assertEquals(5, storage.getStorageSize());
    }

    @Test
    void testStorageOrderRetrieval() throws InterruptedException {
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        storage.storeOrder(order1);
        storage.storeOrder(order2);

        assertEquals(order1, storage.takeOrder());
        assertEquals(order2, storage.takeOrder());
    }

    @Test
    void testBakerProcessing() throws InterruptedException {
        Baker baker = new Baker(1, 1, orderQueue, storage);
        Thread bakerThread = new Thread(baker);

        orderQueue.addOrder(new Order(1));
        bakerThread.start();

        Thread.sleep(1500);
        assertEquals(1, storage.getStorageSize());

        bakerThread.interrupt();
    }

    @Test
    void testCourierDelivery() throws InterruptedException {
        Courier courier = new Courier(1, 2, storage);
        Thread courierThread = new Thread(courier);

        storage.storeOrder(new Order(1));
        storage.storeOrder(new Order(2));
        courierThread.start();

        Thread.sleep(3500);
        assertEquals(0, storage.getStorageSize());

        courierThread.interrupt();
    }
}
