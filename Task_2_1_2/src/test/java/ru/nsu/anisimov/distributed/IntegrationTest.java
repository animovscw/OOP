package ru.nsu.anisimov.distributed;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.anisimov.distributed.common.Task;
import ru.nsu.anisimov.distributed.common.Result;
import ru.nsu.anisimov.distributed.server.PrimeServer;
import ru.nsu.anisimov.distributed.worker.PrimeWorker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {
    private static final int TEST_PORT = 9999;
    private static final int[] TEST_ARRAY = {6, 8, 7, 13, 5, 9, 4};
    private ExecutorService executor;
    private ServerSocket serverSocket;

    @BeforeEach
    void setUp() throws IOException {
        executor = Executors.newFixedThreadPool(3);
        serverSocket = new ServerSocket(TEST_PORT);
    }

    @AfterEach
    void tearDown() throws IOException {
        executor.shutdownNow();
        serverSocket.close();
    }

    @Test
    void testServerWithWorkers() throws Exception {
        // Start server in background
        executor.submit(() -> {
            try {
                List<Socket> workers = PrimeServer.waitForWorkers();
                if (workers.isEmpty()) {
                    fail("No workers connected");
                }
                boolean result = PrimeServer.processArray(TEST_ARRAY, workers);
                assertTrue(result, "Expected array to contain non-prime numbers");
            } catch (Exception e) {
                fail("Server error: " + e.getMessage());
            }
        });

        // Start workers in background
        executor.submit(() -> startTestWorker());
        executor.submit(() -> startTestWorker());

        // Give time for processing
        Thread.sleep(3000);
    }

    private void startTestWorker() {
        try (Socket socket = new Socket("localhost", TEST_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            Task task = (Task) in.readObject();
            boolean hasNonPrime = PrimeWorker.checkForNonPrimes(task.getSubArray());
            out.writeObject(new Result(hasNonPrime));

        } catch (Exception e) {
            fail("Worker error: " + e.getMessage());
        }
    }

    @Test
    void testNoWorkersScenario() {
        assertDoesNotThrow(() -> {
            List<Socket> emptyList = new ArrayList<>();
            PrimeServer.closeAllConnections(emptyList);
        });
    }

    @Test
    void testWorkerTimeoutHandling() throws IOException {
        Socket testSocket = new Socket();
        try {
            assertThrows(IOException.class, () -> {
                PrimeServer.processSubTask(testSocket, new int[]{1, 2, 3});
            });
        } finally {
            testSocket.close();
        }
    }
}