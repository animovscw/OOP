package ru.nsu.anisimov.distributed.server;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.anisimov.distributed.common.Result;
import ru.nsu.anisimov.distributed.common.Task;


/**
 * Tests for server.
 */
public class PrimeServerTest {

    private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(obj);
        }
        return bos.toByteArray();
    }

    @Test
    public void testProcessSubTask_returnsTrue() throws Exception {
        Socket mockSocket = mock(Socket.class);

        byte[] resultBytes = serialize(new Result(true));
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(resultBytes));
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        boolean result = PrimeServer.processSubTask(mockSocket, new Task(new int[]{4, 6, 8}));
        Assertions.assertTrue(result);
    }

    @Test
    public void testProcessSubTask_returnsFalse() throws Exception {
        Socket mockSocket = mock(Socket.class);

        byte[] resultBytes = serialize(new Result(false));
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(resultBytes));
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        boolean result = PrimeServer.processSubTask(mockSocket, new Task(new int[]{2, 3, 5}));
        Assertions.assertFalse(result);
    }

    @Test
    public void testProcessSubTask_onClassNotFound_returnsTrue() throws Exception {
        Socket mockSocket = mock(Socket.class);

        byte[] serializedData;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(new Result(true));
            serializedData = bos.toByteArray();
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(serializedData);
        when(mockSocket.getInputStream()).thenReturn(new InputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }
        });

        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        boolean result = PrimeServer.processSubTask(mockSocket, new Task(new int[]{1, 2, 3}));
        Assertions.assertTrue(result);
    }

    @Test
    public void testProcessArray_withAllPrimeResults() throws Exception {
        Socket mockSocket = mock(Socket.class);

        byte[] resultBytes = serialize(new Result(false));
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(resultBytes));
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        int[] inputArray = {2, 3, 5};
        boolean result = PrimeServer.processArray(inputArray, List.of(mockSocket));
        Assertions.assertFalse(result);
    }

    @Test
    public void testProcessArray_withNonPrimeDetected() throws Exception {
        Socket mockSocket = mock(Socket.class);

        byte[] resultBytes = serialize(new Result(true));
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(resultBytes));
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        int[] inputArray = {2, 4, 5};
        boolean result = PrimeServer.processArray(inputArray, List.of(mockSocket));
        Assertions.assertTrue(result);
    }
}
