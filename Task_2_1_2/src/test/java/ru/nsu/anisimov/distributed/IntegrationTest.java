package ru.nsu.anisimov.distributed;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.nsu.anisimov.distributed.server.PrimeServer;
import ru.nsu.anisimov.distributed.worker.PrimeWorker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Tag("integration")
public class IntegrationTest {

    @Test
    public void testFullSystemWorkflow() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(() -> {
            try {
                PrimeServer.main(new String[]{});
            } catch (Exception e) {
                throw new RuntimeException("Server failed", e);
            }
        });

        Thread.sleep(2000);

        executor.submit(() -> PrimeWorker.main(new String[]{}));
        executor.submit(() -> PrimeWorker.main(new String[]{}));

        Thread.sleep(5000);

        executor.shutdownNow();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }
}