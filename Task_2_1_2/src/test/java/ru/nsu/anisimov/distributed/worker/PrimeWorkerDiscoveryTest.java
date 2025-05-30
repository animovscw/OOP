package ru.nsu.anisimov.distributed.worker;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for PrimeWorker discovery logic (UDP broadcast parsing).
 */
public class PrimeWorkerDiscoveryTest {

    private String originalHost;
    private int    originalPort;

    @AfterEach
    void restoreStatics() throws Exception {
        Field hostField = PrimeWorker.class.getDeclaredField("SERVER_HOST");
        Field portField = PrimeWorker.class.getDeclaredField("SERVER_PORT");
        hostField.setAccessible(true);
        portField.setAccessible(true);
        hostField.set(null, originalHost);
        portField.setInt(null, originalPort);
    }

    @Test
    void discoverServer_shouldParseAndSetServerHostAndPort() throws Exception {
        Field hostField = PrimeWorker.class.getDeclaredField("SERVER_HOST");
        Field portField = PrimeWorker.class.getDeclaredField("SERVER_PORT");
        hostField.setAccessible(true);
        portField.setAccessible(true);
        originalHost = (String) hostField.get(null);
        originalPort = portField.getInt(null);

        String newHost = "192.168.42.42";
        int newPort = 4242;
        String discoveryMsg = "SERVER:" + newHost + ":" + newPort;
        byte[] buf = discoveryMsg.getBytes();

        Field discoveryPortField = PrimeWorker.class.getDeclaredField("DISCOVERY_PORT");
        discoveryPortField.setAccessible(true);
        int discoveryPort = discoveryPortField.getInt(null);

        Thread sender = new Thread(() -> {
            try {
                Thread.sleep(100);
                try (DatagramSocket ds = new DatagramSocket()) {
                    DatagramPacket packet = new DatagramPacket(
                            buf, buf.length,
                            InetAddress.getByName("localhost"),
                            discoveryPort
                    );
                    ds.send(packet);
                }
            } catch (Exception e) {
                Assertions.fail("Failed to send discovery packet: " + e.getMessage());
            }
        });
        sender.setDaemon(true);
        sender.start();

        Method discoverMethod = PrimeWorker.class.getDeclaredMethod("discoverServer");
        discoverMethod.setAccessible(true);
        discoverMethod.invoke(null);

        Assertions.assertEquals(newHost, hostField.get(null));
        Assertions.assertEquals(newPort, portField.getInt(null));
    }
}
