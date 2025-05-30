package ru.nsu.anisimov.distributed.server;

import ru.nsu.anisimov.distributed.common.Result;
import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.worker.PrimeWorker;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Prime number checking server that distributes work across multiple worker nodes.
 */
public class PrimeServer {
    public static final int PORT = 9999;
    public static final int TIMEOUT_MS = 5000;
    public static final int WORKER_TIMEOUT_MS = 3000;
    private static final int DISCOVERY_PORT = 8888;

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
                // Ignore expected timeout
            }
        }
        serverSocket.close();
        return workers;
    }

    private static int[] prepareArray() {
        int size = 20;
        int maxVal = 1000000;
        return new Random().ints(size, 1, maxVal).toArray();
    }

    public static boolean processSubTask(Socket worker, Task task) throws IOException, ClassNotFoundException {
        try (ObjectOutputStream out = new ObjectOutputStream(worker.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(worker.getInputStream())) {
            out.writeObject(task);
            out.flush();
            Result r = (Result) in.readObject();
            return r.hasNonPrime();
        }
    }

    public static boolean processArray(int[] array, List<Socket> workers) throws InterruptedException {
        int n = array.length;
        int w = workers.size();
        int chunk = (n + w - 1) / w;

        ExecutorService pool = Executors.newFixedThreadPool(w);
        List<Future<Boolean>> futures = new ArrayList<>();
        AtomicBoolean hasNonPrime = new AtomicBoolean(false);

        for (int i = 0; i < w; i++) {
            int start = i * chunk;
            int end = Math.min(n, start + chunk);
            if (start >= end) break;

            int[] subArray = Arrays.copyOfRange(array, start, end);
            Task subtask = new Task(subArray);
            Socket sock = workers.get(i);

            futures.add(pool.submit(() -> {
                try {
                    return processSubTask(sock, subtask);
                } catch (Exception e) {
                    System.err.println("Worker failed, master processes subtask: " + e.getMessage());
                    return PrimeWorker.checkForNonPrimes(subArray);
                }
            }));
        }

        for (Future<Boolean> f : futures) {
            try {
                if (f.get()) {
                    hasNonPrime.set(true);
                }
            } catch (ExecutionException e) {
                System.err.println("Task execution error: " + e.getMessage());
                hasNonPrime.set(true);  // assume failure means non-prime for safety
            }
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        workers.forEach(socket -> {
            try {
                socket.close();
            } catch (IOException ignored) {}
        });

        return hasNonPrime.get();
    }

    private static void broadcastDiscovery() {
        try (DatagramSocket ds = new DatagramSocket()) {
            ds.setBroadcast(true);
            String msg = "SERVER:" + InetAddress.getLocalHost().getHostAddress() + ":" + PORT;
            byte[] data = msg.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(
                    data,
                    data.length,
                    InetAddress.getByName("255.255.255.255"),
                    DISCOVERY_PORT
            );
            ds.send(packet);
            System.out.println("Discovery broadcast sent: " + msg);
        } catch (IOException e) {
            System.err.println("Failed to send discovery broadcast: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            broadcastDiscovery();
            System.out.println("Waiting for workers...");
            List<Socket> workers = waitForWorkers();

            int[] array = prepareArray();
            System.out.println("Checking array: " + Arrays.toString(array));

            boolean hasNonPrime = processArray(array, workers);

            System.out.printf("Final result: %s%n", hasNonPrime
                    ? "Contains non-prime numbers" : "All numbers are prime");

        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
