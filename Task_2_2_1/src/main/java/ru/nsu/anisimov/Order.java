package ru.nsu.anisimov;

public class Order {
    private final int id;

    public Order(int id) {
        this.id = id;

    }

    @Override
    public String toString() {
        return "Заказ " + id;
    }
}