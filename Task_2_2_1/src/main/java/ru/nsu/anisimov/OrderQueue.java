package ru.nsu.anisimov;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderQueue {
    private final BlockingQueue<Order> orders = new LinkedBlockingQueue<>();

    public void addOrder(Order order) {
        orders.add(order);
        System.out.println("[Принят] " + order);
    }

    public Order takeOrder() throws InterruptedException{
        return orders.take();
    }
}
