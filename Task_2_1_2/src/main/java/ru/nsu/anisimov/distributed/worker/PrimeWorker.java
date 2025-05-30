package ru.nsu.anisimov.distributed.worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.ConnectException;
import ru.nsu.anisimov.distributed.common.Result;
import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.server.PrimeServer;

/**
 * Worker node for distributed prime number checking.
 * Connects to the server, receives tasks, processes them, and sends results back.
 */
public class PrimeWorker {
    public static long RECONNECT_DELAY_MS = 5000;
    public static String SERVER_HOST = "localhost";

    /**
     * Main entry point for the worker.
     * Runs an infinite loop trying to connect and process tasks.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        WorkerLoop.run(
                PrimeWorker::connectAndProcess,
                Thread::sleep,
                () -> !Thread.currentThread().isInterrupted(),
                RECONNECT_DELAY_MS
        );
    }

    /**
     * Connects to the server, receives a task, processes it, and sends the result back.
     *
     * @throws IOException if connection or communication fails
     */
    public static void connectAndProcess() throws IOException {
        try (Socket socket = new Socket(SERVER_HOST, PrimeServer.PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            socket.setSoTimeout(PrimeServer.WORKER_TIMEOUT_MS);

            Task task = (Task) in.readObject();
            boolean hasNonPrime = checkForNonPrimes(task.getSubArray());

            out.writeObject(new Result(hasNonPrime));
            out.flush();
        } catch (ClassNotFoundException e) {
            throw new IOException("Protocol error", e);
        } catch (ConnectException e) {
            throw e;
        }
    }

    /**
     * Checks if any number in the given array is non-prime.
     *
     * @param numbers array of integers to check
     * @return {@code true} if at least one non-prime is found, {@code false} otherwise
     */
    public static boolean checkForNonPrimes(int[] numbers) {
        for (int num : numbers) {
            if (!isPrime(num)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a number is prime.
     *
     * @param n the number to check
     * @return {@code true} if the number is prime, {@code false} otherwise
     */
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}