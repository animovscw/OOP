package ru.nsu.anisimov;

/**
 * Exception thrown when there is an error reading a graph from a file.
 * This is a runtime exception and does not require explicit handling.
 */
public class GraphFileReadException extends RuntimeException {

    /**
     * Constructs a new GraphFileReadException with the specified detail message and cause.
     *
     * @param message the detail message, which is saved for later retrieval
     * @param cause the cause
     */
    public GraphFileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}