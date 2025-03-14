package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PizzeriaTest {

    @Test
    void testPizzeriaStartupAndShutdown() throws InterruptedException {
        Pizzeria pizzeria = new Pizzeria(2, new int[]{2, 3}, 1, new int[]{2}, 5);
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        pizzeria.addOrder(order1);
        pizzeria.addOrder(order2);

        Thread.sleep(5000);

        assertTrue(pizzeria.getStorage().getStorageSize() > 0);

        pizzeria.shutdown();
        for (Thread baker : pizzeria.getBakers()) {
            baker.join();
            assertFalse(baker.isAlive());
        }
        for (Thread courier : pizzeria.getCouriers()) {
            assertFalse(courier.isAlive());
        }
    }
}
