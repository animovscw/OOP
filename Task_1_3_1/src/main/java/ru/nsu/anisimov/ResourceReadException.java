package ru.nsu.anisimov;

/**
 * Custom exception to handle resource reading errors.
 */
public class ResourceReadException extends Exception {
    /**
     * Constructs a new ResourceReadException with a specified message and cause.
     *
     * @param message message
     * @param cause   the cause of the exception
     */
    public ResourceReadException(String message, Throwable cause) {
        super(message, cause);
    }
}