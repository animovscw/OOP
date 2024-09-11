package ru.nsu.anisimov;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HeapsortTest {
    @Test
    void firstTest() {
        int[] test1Array = new int[]{3, 1, 2};
        Heapsort.heapsort(test1Array);
        assertArrayEquals(new int[]{1, 2, 3}, test1Array);
    }

    @Test
    void secondTest() {
        int[] test2Array = new int[]{4, 10, 3, 5, 1};
        Heapsort.heapsort(test2Array);
        assertArrayEquals(new int[]{1, 3, 4, 5, 10}, test2Array);
    }

    @Test
    void thirdTest() {
        int[] test3Array = new int[]{2, 3, 4, 5};
        Heapsort.heapsort(test3Array);
        assertArrayEquals(new int[]{2, 3, 4, 5}, test3Array);
    }

    @Test
    void fourthTest() {
        int[] test4Array = new int[]{1, 1};
        Heapsort.heapsort(test4Array);
        assertArrayEquals(new int[]{1, 1}, test4Array);
    }

    @Test
    void fifthTest() {
        int[] test5Array = new int[]{1};
        Heapsort.heapsort(test5Array);
        assertArrayEquals(new int[]{1}, test5Array);
    }

    @Test
    void sixthTest() {
        int[] test6Array = new int[]{50, 10, 49, 3, 4, 7, 20, 0};
        Heapsort.heapsort(test6Array);
        assertArrayEquals(new int[]{0, 3, 4, 7, 10, 20, 49, 50}, test6Array);
    }

    @Test
    void seventhTest() {
        int[] test7Array = new int[]{};
        Heapsort.heapsort(test7Array);
        assertArrayEquals(new int[]{}, test7Array);
    }

    @Test
    void eighthTest() {
        int[] test8Array = new int[]{99999992, 0, 0, 2, 23432, 1};
        Heapsort.heapsort(test8Array);
        assertArrayEquals(new int[]{0, 0, 1, 2, 23432, 99999992}, test8Array);
    }

    @Test
    void ninthTest() {
        int[] test9Array = new int[]{1, 0, 1, 0, 0, 1, 0};
        Heapsort.heapsort(test9Array);
        assertArrayEquals(new int[]{0, 0, 0, 0, 1, 1, 1}, test9Array);
    }

}