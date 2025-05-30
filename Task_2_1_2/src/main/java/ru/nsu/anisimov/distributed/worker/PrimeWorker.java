package ru.nsu.anisimov.distributed.worker;

import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.common.Result;
import ru.nsu.anisimov.distributed.server.PrimeServer;

import java.io.*;
import java.net.*;

public class PrimeWorker {
    private static final String SERVER_HOST = "localhost";
    private static final int RECONNECT_DELAY_MS = 5000;

    public static void main(String[] args) {
        System.out.println("Worker starting...");

        while (true) {
            try {
                connectAndProcess();
                System.out.println("Task completed. Waiting for new tasks...");
                Thread.sleep(RECONNECT_DELAY_MS);
            } catch (Exception e) {
                System.err.println("Worker error: " + e.getMessage());
                try {
                    Thread.sleep(RECONNECT_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    private static void connectAndProcess() throws Exception {
        try (Socket socket = new Socket(SERVER_HOST, PrimeServer.PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("Connected to server. Waiting for task...");
            socket.setSoTimeout(PrimeServer.WORKER_TIMEOUT_MS);

            Task task = (Task) in.readObject();
            boolean hasNonPrime = checkForNonPrimes(task.getSubArray());

            out.writeObject(new Result(hasNonPrime));
            out.flush();
        }
    }

    private static boolean checkForNonPrimes(int[] numbers) {
        for (int number : numbers) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2 || number == 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;

        for (int i = 5; i <= Math.sqrt(number); i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}