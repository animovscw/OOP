package ru.nsu.anisimov.distributed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.nsu.anisimov.distributed.server.PrimeServer;
import ru.nsu.anisimov.distributed.worker.PrimeWorker;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegrationTest {

    static class TestablePrimeServer extends PrimeServer {
        public static List<Socket> testWaitForWorkers() throws IOException {
            return waitForWorkers();
        }

        public static boolean testProcessArray(int[] array, List<Socket> workers) throws Exception {
            return processArray(array, workers);
        }
    }

    // Вспомогательный класс для тестирования воркера
    static class TestablePrimeWorker extends PrimeWorker {
        public static void testConnectAndProcess() throws Exception {
            connectAndProcess();
        }
    }

    @Test
    @Timeout(10)
    void testServerWithSingleWorker() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<Boolean> serverFuture = executor.submit(() -> {
            try {
                int[] array = {2, 3, 4, 5, 6};
                List<Socket> workers = TestablePrimeServer.testWaitForWorkers();
                if (workers.isEmpty()) return false;
                return TestablePrimeServer.testProcessArray(array, workers);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Thread.sleep(500); // Wait for server to start

        Future<?> workerFuture = executor.submit(() -> {
            try {
                TestablePrimeWorker.testConnectAndProcess();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        try {
            assertTrue(serverFuture.get(5, TimeUnit.SECONDS));
        } finally {
            workerFuture.cancel(true);
            executor.shutdownNow();
        }
    }

    @Test
    @Timeout(10)
    void testServerWithMultipleWorkers() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<Boolean> serverFuture = executor.submit(() -> {
            try {
                int[] array = new int[100];
                Arrays.fill(array, 7); // All primes
                array[50] = 4; // One non-prime

                List<Socket> workers = TestablePrimeServer.testWaitForWorkers();
                if (workers.size() < 2) return false;
                return TestablePrimeServer.testProcessArray(array, workers);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Thread.sleep(500); // Wait for server to start

        Future<?> worker1 = executor.submit(() -> {
            try {
                TestablePrimeWorker.testConnectAndProcess();
            } catch (Exception ignored) {}
        });

        Future<?> worker2 = executor.submit(() -> {
            try {
                TestablePrimeWorker.testConnectAndProcess();
            } catch (Exception ignored) {}
        });

        try {
            assertTrue(serverFuture.get(5, TimeUnit.SECONDS));
        } finally {
            worker1.cancel(true);
            worker2.cancel(true);
            executor.shutdownNow();
        }
    }
}