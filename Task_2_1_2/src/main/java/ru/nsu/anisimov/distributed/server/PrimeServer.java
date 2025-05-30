package ru.nsu.anisimov.distributed.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import ru.nsu.anisimov.distributed.common.Result;
import ru.nsu.anisimov.distributed.common.Task;

/**
 * Prime number checking server that distributes work across multiple worker nodes.
 */
public class PrimeServer {
    public static final int PORT = 9999;
    public static final int TIMEOUT_MS = 5000;
    public static final int WORKER_TIMEOUT_MS = 3000;

    /**
     * Waits for worker connections within specified timeout.
     *
     * @return list of connected worker sockets
     * @throws IOException if server socket error occurs
     */
    public static List<Socket> waitForWorkers() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        serverSocket.setSoTimeout(TIMEOUT_MS);
        System.out.println("Server listening on port " + PORT);

        List<Socket> workers = new ArrayList<>();
        long endTime = System.currentTimeMillis() + TIMEOUT_MS;

        while (System.currentTimeMillis() < endTime) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("Worker connected: " + client.getInetAddress());
                client.setSoTimeout(WORKER_TIMEOUT_MS);
                workers.add(client);
            } catch (SocketTimeoutException ignored) {
                // Expected during waiting period
            }
        }
        serverSocket.close();
        return workers;
    }

    /**
     * Processes sub-task by sending it to worker and receiving result.
     *
     * @param workerSocket worker connection socket
     * @param subArray array slice to check
     * @return true if subarray contains non-prime numbers
     * @throws IOException if communication error occurs
     */
    public static Boolean processSubTask(Socket workerSocket, int[] subArray) throws IOException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(workerSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(workerSocket.getInputStream());

            out.writeObject(new Task(subArray));
            out.flush();

            Result result = (Result) in.readObject();
            return result.getHasNonPrime();
        } catch (ClassNotFoundException e) {
            throw new IOException("Protocol error", e);
        } catch (SocketTimeoutException e) {
            throw new IOException("Worker timeout", e);
        }
    }

    /**
     * Closes all worker connections.
     *
     * @param workers list of worker sockets to close
     */
    public static void closeAllConnections(List<Socket> workers) {
        for (Socket worker : workers) {
            try {
                if (!worker.isClosed()) {
                    worker.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing worker connection: " + e.getMessage());
            }
        }
    }

    /**
     * Distributes array checking across workers.
     *
     * @param array numbers to check
     * @param workers available worker connections
     * @return true if array contains non-prime numbers
     */
    public static boolean processArray(int[] array, List<Socket> workers) {
        ExecutorService pool = Executors.newFixedThreadPool(workers.size());
        List<Future<Boolean>> futures = new ArrayList<>();
        AtomicBoolean hasNonPrime = new AtomicBoolean(false);

        try {
            int chunkSize = (int) Math.ceil((double) array.length / workers.size());

            for (int i = 0; i < workers.size(); i++) {
                int start = i * chunkSize;
                int end = Math.min(array.length, start + chunkSize);
                int[] subArray = Arrays.copyOfRange(array, start, end);
                Socket workerSocket = workers.get(i);

                futures.add(pool.submit(() -> {
                    try {
                        return processSubTask(workerSocket, subArray);
                    } catch (IOException e) {
                        System.err.println("Worker error: " + e.getMessage());
                        return true;
                    }
                }));
            }

            for (Future<Boolean> f : futures) {
                try {
                    if (f.get()) {
                        hasNonPrime.set(true);
                    }
                } catch (ExecutionException | InterruptedException e) {
                    Thread.currentThread().interrupt();
                    hasNonPrime.set(true);
                    System.err.println("Task execution error: " + e.getMessage());
                }
            }
        } finally {
            pool.shutdown();
            closeAllConnections(workers);
        }
        return hasNonPrime.get();
    }

    /**
     * Main server entry point.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            Random random = new Random();
            int size = 20;
            int maxValue = 1000000;
            int[] array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(maxValue) + 1;
            }
            array[0] = 2;
            array[1] = 982451653;
            array[2] = 2147483647;
            array[3] = 4;
            array[4] = 1000000000;
            array[5] = 123456789;
            List<Socket> workers = waitForWorkers();
            if (workers.isEmpty()) {
                System.out.println("No workers connected. Exiting.");
                return;
            }
            boolean result = processArray(array, workers);
            System.out.println("Result: "
                    + (result ? "Contains non-prime" : "All primes"));
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}