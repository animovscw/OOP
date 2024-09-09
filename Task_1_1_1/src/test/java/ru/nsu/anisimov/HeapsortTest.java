package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest {
    @Test
    void simpleTest() {
        int[] testArray = new int[] {1, 2, 3};
        int[] NewArray = new int[] {3, 1, 2};
        Heapsort.heapsort(NewArray);
        assertArrayEquals(testArray, NewArray);
    }

}