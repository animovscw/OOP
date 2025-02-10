package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SequentialPrimeNumberCheckTest {
    @Test
    void testNonPrimeSequential() {
        int[] arr1 = {6, 8, 7, 13, 5, 9, 4};
        int[] arr2 = {1, 6, 8, 7, 13, 5, 9, 4};
        int[] arr3 = {49, 6, 8, 7, 13, 5, 9, 4};
        Assertions.assertTrue(SequentialPrimeNumberCheck.NonPrimeSequential(arr1));
        Assertions.assertTrue(SequentialPrimeNumberCheck.NonPrimeSequential(arr2));
        Assertions.assertTrue(SequentialPrimeNumberCheck.NonPrimeSequential(arr3));
    }

    @Test
    void testPrimeSequential() {
        int[] arr1 = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        int[] arr2 = {2, 20319251, 6997901, 6997927, 6997937, 17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        Assertions.assertFalse(SequentialPrimeNumberCheck.NonPrimeSequential(arr1));
        Assertions.assertFalse(SequentialPrimeNumberCheck.NonPrimeSequential(arr2));
    }
}