package ru.nsu.anisimov;

/**
 * Represents a customer in the pizzeria.
 */
public class Order {
    private final int id;

    /**
     * Creates a new order.
     * @param id identification
     */
    public Order(int id) {
        this.id = id;
    }

    /**
     * Gets identification.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Заказ " + id;
    }
}