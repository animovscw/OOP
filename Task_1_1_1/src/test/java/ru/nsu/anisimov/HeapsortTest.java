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

        int[] test3Array = new int[]{2, 3, 4, 5};
        Heapsort.heapsort(test3Array);
        assertArrayEquals(new int[]{2, 3, 4, 5}, test3Array);

        int[] test4Array = new int[]{1, 1, 1};
        Heapsort.heapsort(test4Array);
        assertArrayEquals(new int[]{1, 1, 1}, test4Array);

        int[] test5Array = new int[]{1};
        Heapsort.heapsort(test5Array);
        assertArrayEquals(new int[]{1}, test5Array);

        int[] test6Array = new int[]{50, 10, 49, 3, 4, 7, 20};
        Heapsort.heapsort(test6Array);
        assertArrayEquals(new int[]{3, 4, 7, 10, 20, 49, 50}, test6Array);

        int[] test7Array = new int[]{};
        Heapsort.heapsort(test7Array);
        assertArrayEquals(new int[]{}, test7Array);

        int[] test8Array = new int[]{99999992, 0, 0, 2, 23432};
        Heapsort.heapsort(test8Array);
        assertArrayEquals(new int[]{0, 0, 2, 23432, 99999992}, test8Array);

        int[] test9Array = new int[]{1, 0, 1, 0, 0, 1, 0};
        Heapsort.heapsort(test9Array);
        assertArrayEquals(new int[]{0, 0, 0, 0, 1, 1, 1}, test9Array);

    }

}