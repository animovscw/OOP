package ru.nsu.anisimov.distributed.server;

import org.junit.jupiter.api.Test;
import ru.nsu.anisimov.distributed.common.Result;
import java.io.*;
import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        ByteArrayInputStream inStream = new ByteArrayInputStream(serialize(new Result(true)));
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        when(mockSocket.getInputStream()).thenReturn(inStream);
        when(mockSocket.getOutputStream()).thenReturn(outStream);

        boolean result = PrimeServer.processSubTask(mockSocket, new int[]{4, 6, 8});
        assertTrue(result);
    }

    @Test
    public void testProcessSubTask_returnsFalse() throws Exception {
        Socket mockSocket = mock(Socket.class);
        ByteArrayInputStream inStream = new ByteArrayInputStream(serialize(new Result(false)));
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        when(mockSocket.getInputStream()).thenReturn(inStream);
        when(mockSocket.getOutputStream()).thenReturn(outStream);

        boolean result = PrimeServer.processSubTask(mockSocket, new int[]{2, 3, 5});
        assertFalse(result);
    }

    @Test
    public void testProcessSubTask_throwsOnInvalidClass() {
        Socket mockSocket = mock(Socket.class);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ByteArrayInputStream inStream = new ByteArrayInputStream(new byte[]{1, 2, 3}); // corrupted

        try {
            when(mockSocket.getInputStream()).thenReturn(inStream);
            when(mockSocket.getOutputStream()).thenReturn(outStream);

            assertThrows(IOException.class, () -> {
                PrimeServer.processSubTask(mockSocket, new int[]{2, 3});
            });
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testCloseAllConnections_closesSockets() throws IOException {
        Socket mockSocket1 = mock(Socket.class);
        Socket mockSocket2 = mock(Socket.class);

        when(mockSocket1.isClosed()).thenReturn(false);
        when(mockSocket2.isClosed()).thenReturn(false);

        PrimeServer.closeAllConnections(List.of(mockSocket1, mockSocket2));

        verify(mockSocket1, times(1)).close();
        verify(mockSocket2, times(1)).close();
    }

    @Test
    public void testProcessArray_withAllPrimeResults() throws Exception {
        Socket mockSocket = mock(Socket.class);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ByteArrayInputStream inStream = new ByteArrayInputStream(serialize(new Result(false)));

        when(mockSocket.getOutputStream()).thenReturn(outStream);
        when(mockSocket.getInputStream()).thenReturn(inStream);

        int[] inputArray = {2, 3, 5, 7};
        boolean result = PrimeServer.processArray(inputArray, List.of(mockSocket));

        assertFalse(result);
    }

    @Test
    public void testProcessArray_withNonPrimeDetected() throws Exception {
        Socket mockSocket = mock(Socket.class);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ByteArrayInputStream inStream = new ByteArrayInputStream(serialize(new Result(true)));

        when(mockSocket.getOutputStream()).thenReturn(outStream);
        when(mockSocket.getInputStream()).thenReturn(inStream);

        int[] inputArray = {2, 3, 4, 5};
        boolean result = PrimeServer.processArray(inputArray, List.of(mockSocket));

        assertTrue(result);
    }

    @Test
    public void testProcessArray_workerFails() throws Exception {
        Socket mockSocket = mock(Socket.class);
        when(mockSocket.getOutputStream()).thenThrow(new IOException("Failure"));

        int[] inputArray = {2, 3, 5};
        boolean result = PrimeServer.processArray(inputArray, List.of(mockSocket));

        assertTrue(result);
    }
}
