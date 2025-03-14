package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class OrderQueueTest {

    @Test
    void testAddAndTakeOrder() throws InterruptedException {
        OrderQueue queue = new OrderQueue();
        Order order = new Order(1);

        queue.addOrder(order);
        Assertions.assertEquals(1, queue.getQueueSize());

        Order takenOrder = queue.takeOrder();
        Assertions.assertEquals(order, takenOrder);
        Assertions.assertEquals(0, queue.getQueueSize());
    }
}
