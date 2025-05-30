package ru.nsu.anisimov.distributed.worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import ru.nsu.anisimov.distributed.common.Result;
import ru.nsu.anisimov.distributed.common.Task;

/**
 * Worker node for distributed prime number checking.
 * Connects to the server, receives tasks, processes them, and sends results back.
 */
public class PrimeWorker {
    private static long RECONNECT_DELAY_MS = 5000;
    private static String SERVER_HOST = "localhost";
    private static int SERVER_PORT = 9999;
    private static final int DISCOVERY_PORT = 8888;
    private static final int DISCOVERY_TIMEOUT = 3000;

    /**
     * Main entry point for the worker.
     * Runs an infinite loop trying to connect and process tasks.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        discoverServer();

        WorkerLoop.run(
                () -> connectAndProcess(),
                Thread::sleep,
                () -> !Thread.currentThread().isInterrupted(),
                RECONNECT_DELAY_MS
        );
    }

    private static void discoverServer() {
        try (DatagramSocket ds = new DatagramSocket(DISCOVERY_PORT)) {
            ds.setSoTimeout(DISCOVERY_TIMEOUT);
            byte[] buf = new byte[256];
            DatagramPacket pkt = new DatagramPacket(buf, buf.length);
            System.out.println("Waiting for server broadcast on UDP port " + DISCOVERY_PORT);
            ds.receive(pkt);

            String msg = new String(pkt.getData(), 0, pkt.getLength()).trim();
            if (msg.startsWith("SERVER:")) {
                String[] parts = msg.split(":");
                SERVER_HOST = parts[1];
                SERVER_PORT = Integer.parseInt(parts[2]);
                System.out.printf("Discovered server at %s:%d%n", SERVER_HOST, SERVER_PORT);
                return;
            }
        } catch (SocketTimeoutException e) {
            System.err.println("Discovery timeout, defaulting to " + SERVER_HOST + ":"
                    + SERVER_PORT);
        } catch (IOException e) {
            System.err.println("Discovery error: " + e.getMessage());
        }
    }

    /**
     * Connects to the server, receives a task, processes it, and sends the result back.
     *
     * @return true if processing was successful, false otherwise
     */
    public static boolean connectAndProcess() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Task task = (Task) in.readObject();
            boolean hasNonPrime = checkForNonPrimes(task.getSubArray());

            out.writeObject(new Result(hasNonPrime));
            out.flush();
            System.out.println("Processed task, hasNonPrime = " + hasNonPrime);
            return true;
        } catch (ConnectException e) {
            System.err.println("Connection refused: " + e.getMessage());
        } catch (SocketTimeoutException e) {
            System.err.println("Read timeout: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Protocol error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Checks if any number in the given array is non-prime.
     *
     * @param numbers array of integers to check
     * @return {@code true} if at least one non-prime is found, {@code false} otherwise
     */
    public static boolean checkForNonPrimes(int[] numbers) {
        return Arrays.stream(numbers).anyMatch(n -> !isPrime(n));
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