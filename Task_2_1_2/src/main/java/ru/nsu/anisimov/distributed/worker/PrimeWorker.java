package ru.nsu.anisimov.distributed.worker;

import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.common.Result;
import ru.nsu.anisimov.distributed.server.PrimeServer;

import java.io.*;
import java.net.Socket;
import java.net.ConnectException;

public class PrimeWorker {
    private static final String SERVER_HOST = "localhost";
    private static final int RECONNECT_DELAY_MS = 5000;

    public static void main(String[] args) {
        System.out.println("Worker started. Connecting to server...");

        while (!Thread.currentThread().isInterrupted()) {
            try {
                connectAndProcess();
                System.out.println("Task completed. Waiting before reconnect...");
                Thread.sleep(RECONNECT_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Worker interrupted. Shutting down.");
                break;
            } catch (ConnectException e) {
                System.err.println("Connection failed. Retrying in " + RECONNECT_DELAY_MS + "ms");
                try {
                    Thread.sleep(RECONNECT_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                try {
                    Thread.sleep(RECONNECT_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public static void connectAndProcess() throws Exception {
        try (Socket socket = new Socket(SERVER_HOST, PrimeServer.PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            socket.setSoTimeout(PrimeServer.WORKER_TIMEOUT_MS);
            System.out.println("Connected to server. Waiting for task...");

            Task task = (Task) in.readObject();
            boolean hasNonPrime = checkForNonPrimes(task.getSubArray());

            out.writeObject(new Result(hasNonPrime));
            out.flush();
            System.out.println("Result sent to server");
        }
    }

    public static boolean checkForNonPrimes(int[] numbers) {
        for (int number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }

    static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2 || number == 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;

        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}