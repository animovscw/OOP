package ru.nsu.anisimov;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.naming.InsufficientResourcesException;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class Pizzeria {
    private final Queue<Order> orderQueue = new LinkedList<>();
    private final Queue<Order> storage = new LinkedList<>();
    private final int storageCapacity;
    private final Thread[] bakers;
    private final Thread[] couriers;

    public Pizzeria(int bakerCount, int[] bakerSpeed, int courierCount, int[] courierCapacities, int storageCapacity) {
        this.storageCapacity = storageCapacity;
        this.bakers = new Thread[bakerCount];
        this.couriers = new Thread[courierCount];

        for (int i = 0; i < bakerCount; ++i) {
            bakers[i] = new Thread(new Baker(i + 1, bakerSpeed[i], this));
            bakers[i].start();
        }

        for (int i = 0; i < courierCount; ++i) {
            couriers[i] = new Thread(new Courier(i + 1, courierCapacities[i], this));
            couriers[i].start();
        }
    }

    public synchronized void addOrder(Order order) {
        orderQueue.add(order);
        notifyAll();
    }

    public synchronized Order takeOrder() throws InterruptedException {
        while (orderQueue.isEmpty()) {
            wait();
        }
        return orderQueue.poll();
    }

    public synchronized void storePizza(Order order) throws InterruptedException {
        while (getStorageSize() >= storageCapacity) {
            wait();
        }
        storage.add(order);
        System.out.println("[Заказ " + order.getId() + "] Перемещен на склад");
        notifyAll();
    }

    public synchronized void deliverPizza(int courierId, int capacity) throws InterruptedException {
        while (storage.isEmpty()) {
            wait();
        }
        int count = Math.min(capacity, storage.size());
        for (int i = 0; i < count; ++i) {
            Order order = storage.poll();
            System.out.println("[Заказ " + order.getId() + "] Доставляется курьером " + courierId);
        }
        notifyAll();
    }

    public synchronized int getStorageSize() {
        return storage.size();
    }

    public void shutdown() {
        for (Thread baker : bakers) {
            baker.interrupt();
        }
        for (Thread courier : couriers) {
            courier.interrupt();
        }
    }
}
