package ru.nsu.anisimov.distributed.worker;

import ru.nsu.anisimov.distributed.common.Result;
import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.server.PrimeServer;
import java.io.*;
import java.net.Socket;
import java.net.ConnectException;

public class PrimeWorker {

    public static long RECONNECT_DELAY_MS = 5000;
    public static String SERVER_HOST = "localhost";

    public static void main(String[] args) {
        WorkerLoop.run(
                PrimeWorker::connectAndProcess,
                Thread::sleep,
                () -> !Thread.currentThread().isInterrupted(),
                RECONNECT_DELAY_MS
        );
    }

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

    public static boolean checkForNonPrimes(int[] numbers) {
        for (int num : numbers) {
            if (!isPrime(num)) {
                return true;
            }
        }
        return false;
    }

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