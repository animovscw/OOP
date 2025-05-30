package ru.nsu.anisimov.distributed;

import static org.junit.jupiter.api.Assertions.fail;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.nsu.anisimov.distributed.server.PrimeServer;
import ru.nsu.anisimov.distributed.worker.PrimeWorker;

/**
 * Integration tests for distributed prime number checking system.
 */
@Tag("integration")
public class IntegrationTest {

    @Test
    public void testFullSystemWorkflow() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(() -> PrimeServer.main(new String[]{}));

        Thread.sleep(500);

        try (DatagramSocket ds = new DatagramSocket()) {
            String msg = "SERVER:localhost:" + PrimeServer.PORT;
            byte[] buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(
                    buf, buf.length,
                    InetAddress.getByName("255.255.255.255"),
                    8888
            );
            ds.setBroadcast(true);
            ds.send(packet);
        }

        executor.submit(() -> PrimeWorker.main(new String[]{}));
        executor.submit(() -> PrimeWorker.main(new String[]{}));

        Thread.sleep(2000);

        executor.shutdownNow();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            fail("Integration test timed out before completion");
        }
    }
}
