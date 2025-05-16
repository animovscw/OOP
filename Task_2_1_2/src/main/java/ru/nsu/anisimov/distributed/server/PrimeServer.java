package ru.nsu.anisimov.distributed.server;

import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.common.Result;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrimeServer {
    private static final int PORT = 9999;
    private static final int TIMEOUT_MS = 5000;

    public static void main(String[] args) throws Exception {
        int[] array = {6, 8, 7, 13, 5, 9, 4};

        ServerSocket serverSocket = new ServerSocket(PORT);
        serverSocket.setSoTimeout(TIMEOUT_MS);
        System.out.println("Server port " + PORT);

        List<Socket> workers = new ArrayList<>();
        ExecutorService pool = Executors.newCachedThreadPool();
        long endTime = System.currentTimeMillis() + TIMEOUT_MS;
        while (System.currentTimeMillis() < endTime) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("Worker connected " + client.getInetAddress());
                workers.add(client);
            } catch (SocketTimeoutException ignored) {
            }
        }

        if (workers.isEmpty()) {
            System.out.println("No workers connected.");
            serverSocket.close();
            return;
        }

        int chunkSize = (int) Math.ceil((double) array.length / workers.size());
        List<Future<Boolean>> futures = new ArrayList<>();

        for (int i = 0; i < workers.size(); i++) {
            int start = i * chunkSize;
            int end = Math.min(array.length, start + chunkSize);
            int finalStart = start;
            int finalEnd = end;
            Socket workerSocket = workers.get(i);

            futures.add(pool.submit(() -> {
                try (
                        ObjectOutputStream out = new ObjectOutputStream(workerSocket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(workerSocket.getInputStream());
                ) {
                    out.writeObject(new Task(array, finalStart, finalEnd));
                    Result result = (Result) in.readObject();
                    return result.hasNonPrime;
                } catch (Exception e) {
                    System.err.println("Worker failed " + e.getMessage());
                    return true;
                }
            }));
        }

        boolean foundNonPrime = false;
        for (Future<Boolean> f : futures) {
            if (f.get()) {
                foundNonPrime = true;
                break;
            }
        }

        System.out.println("Result " + foundNonPrime);
        pool.shutdown();
        serverSocket.close();
    }
}
