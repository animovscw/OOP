package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParallelThreadPrimeNumberCheckTest {
    @Test
    void testNonPrimeParallelThread() throws InterruptedException {
        int[] arr1 = {6, 8, 7, 13, 5, 9, 4};
        int[] arr2 = {1, 6, 8, 7, 13, 5, 9, 4};
        int[] arr3 = {49, 6, 8, 7, 13, 5, 9, 4};
        Assertions.assertTrue(ParallelThreadPrimeNumberCheck.NonPrimeParallelThread(arr1, 5));
        Assertions.assertTrue(ParallelThreadPrimeNumberCheck.NonPrimeParallelThread(arr2, 4));
        Assertions.assertTrue(ParallelThreadPrimeNumberCheck.NonPrimeParallelThread(arr3, 6));
    }

    @Test
    void testPrimeParallelThread() throws InterruptedException {
        int[] arr1 = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        int[] arr2 = {2, 20319251, 6997901, 6997927, 6997937, 17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        Assertions.assertFalse(ParallelThreadPrimeNumberCheck.NonPrimeParallelThread(arr1, 5));
        Assertions.assertFalse(ParallelThreadPrimeNumberCheck.NonPrimeParallelThread(arr2, 4));
    }
}