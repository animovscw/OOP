package ru.nsu.anisimov.distributed.worker;

import java.net.ConnectException;

public class WorkerLoop {

    public interface Connector {
        void connectAndProcess() throws Exception;
    }

    public interface Sleeper {
        void sleep(long ms) throws InterruptedException;
    }

    public interface Continuator {
        boolean shouldContinue();
    }

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
