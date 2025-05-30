package ru.nsu.anisimov.distributed.worker;

import org.junit.jupiter.api.Test;
import ru.nsu.anisimov.distributed.common.Result;
import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.server.PrimeServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TestPrimeWorker {

    @Test
    public void testIsPrime_basicCases() {
        assertFalse(PrimeWorker.isPrime(-1));
        assertFalse(PrimeWorker.isPrime(0));
        assertFalse(PrimeWorker.isPrime(1));
        assertTrue(PrimeWorker.isPrime(2));
        assertTrue(PrimeWorker.isPrime(3));
        assertFalse(PrimeWorker.isPrime(4));
        assertTrue(PrimeWorker.isPrime(5));
    }

    @Test
    public void testIsPrime_largePrime() {
        assertTrue(PrimeWorker.isPrime(982451653));
    }

    @Test
    public void testIsPrime_largeNonPrime() {
        assertFalse(PrimeWorker.isPrime(1000000000));
    }

    @Test
    public void testCheckForNonPrimes_allPrimes() {
        int[] primes = {2, 3, 5, 7, 11};
        assertFalse(PrimeWorker.checkForNonPrimes(primes));
    }

    @Test
    public void testCheckForNonPrimes_withNonPrime() {
        int[] array = {2, 4, 6};
        assertTrue(PrimeWorker.checkForNonPrimes(array));
    }

    @Test
    void testConnectAndProcess_successful() throws Exception {
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
                    assertFalse(result.getHasNonPrime());
                } catch (IOException | ClassNotFoundException e) {
                    fail("Server simulation failed: " + e.getMessage());
                }
            });

            PrimeWorker.connectAndProcess();

            executor.shutdown();
            assertTrue(executor.awaitTermination(1, TimeUnit.SECONDS));
        }
    }
}
