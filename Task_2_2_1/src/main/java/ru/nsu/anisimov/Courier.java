package ru.nsu.anisimov;

public class Courier implements Runnable {
    private final int id;
    private final int capacity;
    private final Pizzeria pizzeria;

    public Courier(int id, int capacity, Pizzeria pizzeria) {
        this.id = id;
        this.capacity = capacity;
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                pizzeria.deliverPizza(id, capacity);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
