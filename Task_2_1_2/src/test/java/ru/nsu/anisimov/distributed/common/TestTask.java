package ru.nsu.anisimov.distributed.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTask {
    @Test
    public void testTaskCreation() {
        int[] array = {1, 2, 3};
        Task task = new Task(array);

        assertArrayEquals(array, task.getSubArray());
    }
}