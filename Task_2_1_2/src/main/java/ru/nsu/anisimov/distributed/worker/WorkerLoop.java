package ru.nsu.anisimov.distributed.worker;

import java.net.ConnectException;

/**
 * Worker loop implementation that handles reconnection logic and error handling.
 */
public class WorkerLoop {

    /**
     * Functional interface for establishing connection and processing tasks.
     */
    public interface Connector {
        /**
         * Establishes connection and processes tasks.
         *
         * @throws Exception if any error occurs during connection or processing
         */
        void connectAndProcess() throws Exception;
    }

    /**
     * Functional interface for sleep operations.
     */
    public interface Sleeper {
        /**
         * Sleeps for specified time.
         *
         * @param ms milliseconds to sleep
         * @throws InterruptedException if sleep is interrupted
         */
        void sleep(long ms) throws InterruptedException;
    }

    /**
     * Functional interface for continuation condition checking.
     */
    public interface Continuator {
        /**
         * Checks if worker should continue running.
         *
         * @return true if worker should continue, false otherwise
         */
        boolean shouldContinue();
    }

    /**
     * Runs worker loop with specified components.
     *
     * @param connector connection and task processor
     * @param sleeper sleep implementation
     * @param continuator continuation condition checker
     * @param delayMs delay between attempts in milliseconds
     */
    public static void run(Connector connector,
                           Sleeper sleeper,
                           Continuator continuator,
                           long delayMs) {
        while (continuator.shouldContinue()) {
            try {
                connector.connectAndProcess();
                System.out.println("Task completed. Waiting before reconnect...");
                sleeper.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Worker interrupted. Shutting down.");
                break;
            } catch (ConnectException e) {
                System.err.println("Connection failed. Retrying in " + delayMs + "ms");
                try {
                    sleeper.sleep(delayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                try {
                    sleeper.sleep(delayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}