package ru.nsu.anisimov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulatorTests {
    private Pizzeria pizzeria;

    @BeforeEach
    void setUp() {
        int[] bakerSpeeds = {2, 3, 1};
        int[] courierCapacities = {2, 3};
        int storageCapacity = 5;
        pizzeria = new Pizzeria(bakerSpeeds.length, bakerSpeeds, courierCapacities.length, courierCapacities, storageCapacity);
    }

    @Test
    void testOrderProcessing() throws InterruptedException {
        Order order = new Order(1);
        pizzeria.addOrder(order);
        assertNotNull(pizzeria.takeOrder());
    }

    @Test
    void testStorageCapacity() throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            pizzeria.storePizza(new Order(i));
        }
        assertEquals(5, pizzeria.getStorageSize());
    }

    @Test
    void testCourierDelivery() throws InterruptedException {
        for (int i = 1; i <= 3; i++) {
            pizzeria.storePizza(new Order(i));
        }
        pizzeria.deliverPizza(1, 2);
        assertTrue(pizzeria.getStorageSize() <= 1);
    }
}
