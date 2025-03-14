package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class StorageTest {

    @Test
    void testStoreAndTakeOrder() throws InterruptedException {
        Storage storage = new Storage(2);
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        storage.storeOrder(order1);
        storage.storeOrder(order2);
        Assertions.assertEquals(2, storage.getStorageSize());

        Order takenOrder1 = storage.takeOrder();
        Order takenOrder2 = storage.takeOrder();
        Assertions.assertEquals(order1, takenOrder1);
        Assertions.assertEquals(order2, takenOrder2);
        Assertions.assertEquals(0, storage.getStorageSize());
    }
}
