package ru.nsu.anisimov.distributed.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for class Task.
 */
public class TestTask {
    @Test
    public void testTaskCreation() {
        int[] array = {1, 2, 3};
        Task task = new Task(array);

        Assertions.assertArrayEquals(array, task.getSubArray());
    }
}