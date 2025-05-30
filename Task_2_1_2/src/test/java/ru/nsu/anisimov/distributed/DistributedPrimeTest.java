package ru.nsu.anisimov.distributed;

import org.junit.jupiter.api.Test;
import ru.nsu.anisimov.distributed.server.PrimeServer;
import ru.nsu.anisimov.distributed.worker.PrimeWorker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DistributedPrimeTest {

    @Test
    public void testServerWithTwoWorkers() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);

        try {
            service.submit(() -> {
                try {
                    PrimeServer.main(new String[]{});
                } catch (Exception e) {
                    System.err.println("Server error: " + e.getMessage());
                }
            });

            Thread.sleep(1000);

            service.submit(() -> PrimeWorker.main(new String[]{}));
            service.submit(() -> PrimeWorker.main(new String[]{}));

            Thread.sleep(3000);
        } finally {
            service.shutdownNow();
            service.awaitTermination(1, TimeUnit.SECONDS);
        }
    }
}