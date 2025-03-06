package ru.nsu.anisimov;

import java.util.Queue;

class Baker implements Runnable {
    private final int id;
    private final int speed;
    private final Pizzeria pizzeria;

    public Baker(int id, int speed, Pizzeria pizzeria) {
        this.id = id;
        this.speed = speed;
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = pizzeria.takeOrder();
                if (order == null) {
                    break;
                }
                System.out.println("[Заказ " + order.getId() + "] Готовится пекарем " + id);
                Thread.sleep(speed * 1000L);
                pizzeria.storePizza(order);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}