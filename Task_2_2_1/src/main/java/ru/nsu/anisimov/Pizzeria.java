package ru.nsu.anisimov;

import java.util.LinkedList;
import java.util.Queue;

public class Pizzeria {
    private final OrderQueue orderQueue;
    private final Storage storage;
    private final Thread[] bakers;
    private final Thread[] couriers;

    public Pizzeria(int bakerCount, int[] bakerSpeed, int courierCount, int[] courierCapacities, int storageCapacity) {
        this.orderQueue = new OrderQueue();
        this.storage = new Storage(storageCapacity);
        this.bakers = new Thread[bakerCount];
        this.couriers = new Thread[courierCount];

        for (int i = 0; i < bakerCount; ++i) {
            bakers[i] = new Thread(new Baker(i + 1, bakerSpeed[i],orderQueue,storage));
            bakers[i].start();
        }

        for (int i = 0; i < courierCount; i++) {
            couriers[i] = new Thread(new Courier(i + 1, courierCapacities[i], storage));
            couriers[i].start();
        }
    }

    public void addOrder(Order order) {
        orderQueue.addOrder(order);
    }

    public void shutdown() {
        for (Thread baker : bakers) {
            baker.interrupt();
        }
        for (Thread courier : couriers) {
            courier.interrupt();
        }
        System.out.println("Пиццерия закрывается...");
    }
}
