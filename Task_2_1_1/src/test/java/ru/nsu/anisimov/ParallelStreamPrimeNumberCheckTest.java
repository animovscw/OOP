package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParallelStreamPrimeNumberCheckTest {
    @Test
    void testParallelStream() {
        int[] arr1 = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        int[] arr2 = {1, 6, 8, 7, 13, 5, 9, 4};
        Assertions.assertTrue(ParallelStreamPrimeNumberCheck.NonPrimeParallelStream(arr2));
        Assertions.assertFalse(ParallelStreamPrimeNumberCheck.NonPrimeParallelStream(arr1));
    }

}