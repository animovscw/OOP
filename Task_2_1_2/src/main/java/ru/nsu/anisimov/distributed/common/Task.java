package ru.nsu.anisimov.distributed.common;

import java.io.Serializable;

public class Task implements Serializable {
    public int[] array;
    public int start;
    public int end;

    public Task(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }
}
