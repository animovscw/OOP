package ru.nsu.anisimov;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * The class represents the main entity of the pizzeria system.
 * It manages the bakers, couriers, order queue, and storage.
 */
public class Pizzeria {
    private final OrderQueue orderQueue;
    private final Storage storage;
    private final Thread[] bakers;
    private final Thread[] couriers;

    /**
     * Initialization with the specified configuration.
     *
     * @param configPath path
     * @throws IOException exception
     */
    public Pizzeria(String configPath) throws IOException {
        PizzeriaConfig config = loadConfig(configPath);
        this.orderQueue = new OrderQueue();
        this.storage = new Storage(config.storageSize);
        this.bakers = new Thread[config.bakers];
        this.couriers = new Thread[config.couriers];

        for (int i = 0; i < config.bakers; i++) {
            bakers[i] = new Thread(new Baker(i + 1, config.bakerSpeeds[i], orderQueue, storage));
            bakers[i].start();
        }

        for (int i = 0; i < config.couriers; i++) {
            couriers[i] = new Thread(new Courier(i + 1, config.courierCapacities[i], storage));
            couriers[i].start();
        }
    }

    /**
     * Adds a new order to the order queue.
     *
     * @param order The order
     */
    public void addOrder(Order order) {
        orderQueue.addOrder(order);
        logAction(order.getId(), "Принят");
    }

    /**
     * Shuts down the pizzeria by interrupting all threads.
     */
    public void shutdown() {
        for (Thread baker : bakers) {
            baker.interrupt();
        }
        for (Thread courier : couriers) {
            courier.interrupt();
        }
        logAction(0, "Пиццерия закрывается...");
    }

    /**
     * Returns the storage instance.
     *
     * @return the storage instance
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Returns the array of baker threads.
     *
     * @return Array of baker threads
     */
    public Thread[] getBakers() {
        return bakers;
    }

    /**
     * Returns the array of courier threads.
     *
     * @return Array of courier threads
     */
    public Thread[] getCouriers() {
        return couriers;
    }

    /**
     * Logging.
     *
     * @param orderId identification
     * @param state state
     */
    private static void logAction(int orderId, String state) {
        System.out.println("[" + orderId + "] " + state);
    }

    /**
     * Load configuraation.
     *
     * @param path the path
     * @return values
     * @throws IOException exception
     */
    private static PizzeriaConfig loadConfig(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(path), PizzeriaConfig.class);
    }
}