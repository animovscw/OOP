package ru.nsu.anisimov.distributed.worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.anisimov.distributed.common.Result;
import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.server.PrimeServer;

/**
 * Tests for PrimeWorker.
 */
public class PrimeWorkerTest {

    @Test
    public void testIsPrime_basicCases() {
        Assertions.assertFalse(PrimeWorker.isPrime(-1));
        Assertions.assertFalse(PrimeWorker.isPrime(0));
        Assertions.assertFalse(PrimeWorker.isPrime(1));
        Assertions.assertTrue(PrimeWorker.isPrime(2));
        Assertions.assertTrue(PrimeWorker.isPrime(3));
        Assertions.assertFalse(PrimeWorker.isPrime(4));
        Assertions.assertTrue(PrimeWorker.isPrime(5));
    }

    @Test
    public void testIsPrime_largePrime() {
        Assertions.assertTrue(PrimeWorker.isPrime(982451653));
    }

    @Test
    public void testIsPrime_largeNonPrime() {
        Assertions.assertFalse(PrimeWorker.isPrime(1_000_000_000));
    }

    @Test
    public void testCheckForNonPrimes_allPrimes() {
        int[] primes = {2, 3, 5, 7, 11};
        Assertions.assertFalse(PrimeWorker.checkForNonPrimes(primes));
    }

    @Test
    public void testCheckForNonPrimes_withNonPrime() {
        int[] array = {2, 4, 6};
        Assertions.assertTrue(PrimeWorker.checkForNonPrimes(array));
    }

    @Test
    public void testConnectAndProcess_successful() throws Exception {
        try (ServerSocket fakeServer = new ServerSocket(PrimeServer.PORT)) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> {
                try (Socket socket = fakeServer.accept();
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    int[] array = {2, 3, 5};
                    out.writeObject(new Task(array));
                    out.flush();

                    Result result = (Result) in.readObject();
                    Assertions.assertFalse(result.hasNonPrime());
                } catch (IOException | ClassNotFoundException e) {
                    Assertions.fail("Server simulation failed: " + e.getMessage());
                }
            });

            boolean success = PrimeWorker.connectAndProcess();
            Assertions.assertTrue(success);

            executor.shutdown();
            Assertions.assertTrue(executor.awaitTermination(1, TimeUnit.SECONDS));
        }
    }

    @Test
    public void testServerDiscoveryParsing() throws Exception {
        String serverMessage = "SERVER:192.168.1.100:5555";
        byte[] data = serverMessage.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length);

        String msg = new String(packet.getData(), 0, packet.getLength()).trim();
        Assertions.assertTrue(msg.startsWith("SERVER:"));

        String[] parts = msg.split(":");
        Assertions.assertEquals("192.168.1.100", parts[1]);
        Assertions.assertEquals("5555", parts[2]);
    }

    @Test
    public void testConnectAndProcess_ConnectionRefusedHandled() {
        boolean result = PrimeWorker.connectAndProcess();
        Assertions.assertFalse(result);
    }
    @Test
    public void testConnectAndProcess_ConnectExceptionHandled() {
        boolean result = PrimeWorker.connectAndProcess();
        Assertions.assertFalse(result);
    }

    @Test
    public void testConnectAndProcess_ClassNotFoundExceptionHandled() throws Exception {
        try (ServerSocket server = new ServerSocket(PrimeWorker.SERVER_PORT)) {
            Thread serverThread = new Thread(() -> {
                try (Socket client = server.accept();
                     ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {
                    // Отправим объект неизвестного типа (мусор)
                    out.writeObject(new Object() {
                        private void writeObject(ObjectOutputStream oos) throws IOException {
                            oos.writeUTF("junk");
                        }
                    });
                    out.flush();
                } catch (IOException ignored) {}
            });
            serverThread.start();

            boolean result = PrimeWorker.connectAndProcess();
            Assertions.assertFalse(result);
        }
    }

    @Test
    public void testConnectAndProcess_IOExceptionHandled() throws Exception {
        try (ServerSocket server = new ServerSocket(PrimeWorker.SERVER_PORT)) {
            Thread serverThread = new Thread(() -> {
                try (Socket socket = server.accept()) {
                    socket.close();
                } catch (IOException ignored) {}
            });
            serverThread.start();

            boolean result = PrimeWorker.connectAndProcess();
            Assertions.assertFalse(result);
        }
    }

    @Test
    public void testDiscoveryMessageParsing() {
        String discovery = "SERVER:10.0.0.5:12345";
        byte[] buf = discovery.getBytes();
        DatagramPacket pkt = new DatagramPacket(buf, buf.length);

        String msg = new String(pkt.getData(), 0, pkt.getLength()).trim();
        Assertions.assertTrue(msg.startsWith("SERVER:"));

        String[] parts = msg.split(":");
        Assertions.assertEquals("10.0.0.5", parts[1]);
        Assertions.assertEquals("12345", parts[2]);
    }
}
