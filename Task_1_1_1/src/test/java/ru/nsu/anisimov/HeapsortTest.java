package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest {
    @Test
    void simpleTest() {
        int[] test1Array = new int[]{3, 1, 2};
        Heapsort.heapsort(test1Array);
        assertArrayEquals(new int[]{1, 2, 3}, test1Array);

        int[] test2Array = new int[]{4, 10, 3, 5, 1};
        Heapsort.heapsort(test2Array);
        assertArrayEquals(new int[]{1, 3, 4, 5, 10}, test2Array);
    }

}