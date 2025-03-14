package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;

class PizzeriaTest {
    @Test
    void testOrderQueueHandling() throws InterruptedException, IOException {
        Pizzeria pizzeria = new Pizzeria("config.json");
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        Order order3 = new Order(3);

        pizzeria.addOrder(order1);
        pizzeria.addOrder(order2);
        pizzeria.addOrder(order3);

        Thread.sleep(7000);

        Assertions.assertTrue(pizzeria.getStorage().getStorageSize() <= 2);

        pizzeria.shutdown();
    }

    @Test
    void testStorageOverflowAndRelease() throws InterruptedException, IOException {
        Pizzeria pizzeria = new Pizzeria("config.json");
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        Order order3 = new Order(3);

        pizzeria.addOrder(order1);
        pizzeria.addOrder(order2);
        pizzeria.addOrder(order3);

        Thread.sleep(5000);

        Assertions.assertEquals(2, pizzeria.getStorage().getStorageSize());

        pizzeria.shutdown();
    }

    @Test
    void testShutdownStopsAllThreads() throws InterruptedException, IOException {
        Pizzeria pizzeria = new Pizzeria("config.json");
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        pizzeria.addOrder(order1);
        pizzeria.addOrder(order2);

        Thread.sleep(3000);

        pizzeria.shutdown();

        for (Thread baker : pizzeria.getBakers()) {
            baker.join();
            Assertions.assertFalse(baker.isAlive());
        }

        for (Thread courier : pizzeria.getCouriers()) {
            courier.join();
            Assertions.assertFalse(courier.isAlive());
        }
    }
}