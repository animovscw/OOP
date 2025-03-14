package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class OrderTest {

    @Test
    void testOrderToString() {
        Order order = new Order(1);
        Assertions.assertEquals("Заказ 1", order.toString());
    }
}