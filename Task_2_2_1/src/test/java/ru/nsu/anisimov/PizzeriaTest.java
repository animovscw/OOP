package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class PizzeriaTest {

    @Test
    void testPizzeriaStartupAndShutdown() throws InterruptedException {
        Pizzeria pizzeria = new Pizzeria(2, new int[]{2, 3}, 1, new int[]{2}, 5);
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        pizzeria.addOrder(order1);
        pizzeria.addOrder(order2);

        Thread.sleep(5000);

        Assertions.assertTrue(pizzeria.getStorage().getStorageSize() > 0);

        pizzeria.shutdown();
        for (Thread baker : pizzeria.getBakers()) {
            baker.join();
            Assertions.assertFalse(baker.isAlive());
        }
        for (Thread courier : pizzeria.getCouriers()) {
            Assertions.assertFalse(courier.isAlive());
        }
    }

    @Test
    void testOrderQueueHandling() throws InterruptedException {
        Pizzeria pizzeria = new Pizzeria(1, new int[]{2}, 1, new int[]{1}, 2); // Склад вмещает 2 заказа
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
    void testStorageOverflowAndRelease() throws InterruptedException {
        Pizzeria pizzeria = new Pizzeria(1, new int[]{2}, 1, new int[]{2}, 2); // Курьер забирает 2 заказа за раз
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        Order order3 = new Order(3);

        pizzeria.addOrder(order1);
        pizzeria.addOrder(order2);
        pizzeria.addOrder(order3);

        Thread.sleep(5000);

        Assertions.assertEquals(1, pizzeria.getStorage().getStorageSize());

        pizzeria.shutdown();
    }

    @Test
    void testShutdownStopsAllThreads() throws InterruptedException {
        Pizzeria pizzeria = new Pizzeria(2, new int[]{1, 1}, 2, new int[]{1, 1}, 3);
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