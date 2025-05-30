package ru.nsu.anisimov.distributed.server;

import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.common.Result;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrimeServer {
    public static final int PORT = 9999;
    public static final int TIMEOUT_MS = 5000;
    public static final int WORKER_TIMEOUT_MS = 3000;

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

            }
        }
        serverSocket.close();
        return workers;
    }

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

    public static boolean processArray(int[] array, List<Socket> workers) throws Exception {
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
                        return true; // Treat worker failure as non-prime
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
}