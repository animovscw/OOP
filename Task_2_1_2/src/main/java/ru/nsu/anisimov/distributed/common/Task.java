package ru.nsu.anisimov.distributed.common;

import java.io.Serializable;

public class Task implements Serializable {
    private final int[] subArray;

    public Task(int[] subArray) {
        this.subArray = subArray;
    }

    public int[] getSubArray() {
        return subArray;
    }
}