package ru.nsu.anisimov.distributed.common;

import java.io.Serializable;

/**
 * Represents a task containing a subarray of numbers to check for primality.
 * Sent from the server to worker nodes.
 */
public class Task implements Serializable {
    private final int[] subArray;

    public Task(int[] subArray) {
        this.subArray = subArray;
    }

    public int[] getSubArray() {
        return subArray;
    }
}