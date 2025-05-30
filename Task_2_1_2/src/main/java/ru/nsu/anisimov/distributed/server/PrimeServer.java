package ru.nsu.anisimov.distributed.server;

import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.common.Result;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class PrimeServer {
    public static final int PORT = 9999;
    public static final int TIMEOUT_MS = 5000;
    public static final int WORKER_TIMEOUT_MS = 3000;

    private static List<Socket> waitForWorkers() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        serverSocket.setSoTimeout(TIMEOUT_MS);
        System.out.println("Server port " + PORT);

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

    private static Boolean processSubTask(Socket workerSocket, int[] subArray) throws IOException {
        try (
                ObjectOutputStream out = new ObjectOutputStream(workerSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(workerSocket.getInputStream())
        ) {
            out.writeObject(new Task(subArray));
            out.flush();

            workerSocket.setSoTimeout(WORKER_TIMEOUT_MS);
            Result result = (Result) in.readObject();
            return result.hasNonPrime;
        } catch (ClassNotFoundException | SocketTimeoutException e) {
            throw new IOException("Worker communication error", e);
        }
    }

    private static void closeAllConnections(List<Socket> workers) {
        for (Socket worker : workers) {
            try {
                worker.close();
            } catch (IOException e) {
                System.err.println("ERROR closing worker connection: " + e.getMessage());
            }
        }
    }

    private static boolean processArray(int[] array, List<Socket> workers) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(workers.size());
        List<Future<Boolean>> futures = new ArrayList<>();

        int chunkSize = (int) Math.ceil((double) array.length / workers.size());

        for (int i = 0; i < workers.size(); i++) {
            int start = i * chunkSize;
            int end = Math.min(array.length, start + chunkSize);
            int[] subArray = Arrays.copyOfRange(array, start, end);

            Socket workerSocket = workers.get(i);
            futures.add(pool.submit(() -> processSubTask(workerSocket, subArray)));
        }

        boolean foundNonPrime = false;
        for (Future<Boolean> f : futures) {
            try {
                if (f.get()) {
                    foundNonPrime = true;
                }
            } catch (ExecutionException e) {
                System.err.println("Worker failed: " + e.getCause().getMessage());
                foundNonPrime = true;
            }
        }

        pool.shutdown();
        closeAllConnections(workers);
        return foundNonPrime;
    }

    public static void main(String[] args) throws Exception {
        int[] array = {6, 8, 7, 13, 5, 9, 4};

        List<Socket> workers = waitForWorkers();
        if (workers.isEmpty()) {
            System.out.println("No workers connected.");
            return;
        }

        boolean result = processArray(array, workers);
        System.out.println("Final result: " + (result ? "Contains non-prime" : "All primes"));
    }
}
