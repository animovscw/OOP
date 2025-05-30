package ru.nsu.anisimov.distributed.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
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
    private static final int DISCOVERY_PORT = 8888;

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

    private static int[] prepareArray() {
        int size = 20;
        int maxVal = 1000000;
        Random rnd = new Random();
        int[] arr = rnd.ints(size, 1, maxVal).toArray();
        return arr;
    }

    /**
     * Processes sub-task by sending it to worker and receiving result.
     *
     * @return true if subarray contains non-prime numbers
     * @throws IOException if communication error occurs
     */
    public static boolean processSubTask(Socket worker, Task task) {
        try (ObjectOutputStream out = new ObjectOutputStream(worker.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(worker.getInputStream())) {

            out.writeObject(task);
            out.flush();

            Result r = (Result) in.readObject();
            return r.hasNonPrime();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Worker comms error: " + e.getMessage());
            return true;
        }
    }

    /**
     * Distributes array checking across workers.
     *
     * @param array numbers to check
     * @param workers available worker connections
     * @return true if array contains non-prime numbers
     */
    public static boolean processArray(int[] array, List<Socket> workers)
            throws InterruptedException {
        int n = array.length;
        int w = workers.size();
        int chunk = (n + w - 1) / w;

        ExecutorService pool = Executors.newFixedThreadPool(w);
        List<Future<Boolean>> futures = new ArrayList<>();
        AtomicBoolean hasNonPrime = new AtomicBoolean(false);

        for (int i = 0; i < w; i++) {
            int start = i * chunk;
            int end = Math.min(n, start + chunk);
            Task subtask = new Task(Arrays.copyOfRange(array, start, end));
            Socket sock = workers.get(i);
            futures.add(pool.submit(() -> processSubTask(sock, subtask)));
        }

        for (Future<Boolean> f : futures) {
            try {
                if (f.get()) {
                    hasNonPrime.set(true);
                }
            } catch (ExecutionException ee) {
                System.err.println("Execution error: " + ee.getMessage());
                hasNonPrime.set(true);
            }
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        workers.forEach(s -> {
            try {
                s.close();
            } catch (IOException ignored) {
                // Ignore close exception
            }
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

    /**
     * Main server entry point.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            broadcastDiscovery();
            System.out.println("Waiting for workers...");
            List<Socket> workers = waitForWorkers();
            if (workers.isEmpty()) {
                System.out.println("No workers connected. Exiting.");
                return;
            }
            int[] array = prepareArray();
            boolean hasNonPrime = processArray(array, workers);
            System.out.printf("Final result: %s%n", hasNonPrime
                    ? "Contains non-prime" : "All primes");
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}