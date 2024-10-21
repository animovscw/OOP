package ru.nsu.anisimov;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests to check the work of the class.
 */
public class GraphFileReadExceptionTest {

    @Test
    public void testExceptionMessage() {
        String message = "File reading failed";
        Throwable cause = new IOException("File not found");
        GraphFileReadException exception = new GraphFileReadException(message, cause);

        Assertions.assertEquals(message, exception.getMessage(),
                "Exception message should match the provided message");
        Assertions.assertEquals(cause, exception.getCause(),
                "Exception cause should match the provided cause");
    }
}