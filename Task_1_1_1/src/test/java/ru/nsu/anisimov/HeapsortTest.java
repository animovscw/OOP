package ru.nsu.anisimov;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HeapsortTest {
    @Test
    void firstTest() {
        int[] Array = new int[]{3, 1, 2};
        Heapsort.heapsort(Array);
        assertArrayEquals(new int[]{1, 2, 3}, Array);
    }

    @Test
    void secondTest() {
        int[] Array = new int[]{4, 10, 3, 5, 1};
        Heapsort.heapsort(Array);
        assertArrayEquals(new int[]{1, 3, 4, 5, 10}, Array);
    }

    @Test
    void thirdTest() {
        int[] Array = new int[]{2, 3, 4, 5};
        Heapsort.heapsort(Array);
        assertArrayEquals(new int[]{2, 3, 4, 5}, Array);
    }

    @Test
    void fourthTest() {
        int[] Array = new int[]{1, 1};
        Heapsort.heapsort(Array);
        assertArrayEquals(new int[]{1, 1}, Array);
    }

    @Test
    void fifthTest() {
        int[] Array = new int[]{1};
        Heapsort.heapsort(Array);
        assertArrayEquals(new int[]{1}, Array);
    }

    @Test
    void sixthTest() {
        int[] Array = new int[]{50, 10, 49, 3, 4, 7, 20, 0};
        Heapsort.heapsort(Array);
        assertArrayEquals(new int[]{0, 3, 4, 7, 10, 20, 49, 50}, Array);
    }

    @Test
    void seventhTest() {
        int[] Array = new int[]{};
        Heapsort.heapsort(Array);
        assertArrayEquals(new int[]{}, Array);
    }

    @Test
    void eighthTest() {
        int[] Array = new int[]{99999992, 0, 0, 2, 23432, 1};
        Heapsort.heapsort(Array);
        assertArrayEquals(new int[]{0, 0, 1, 2, 23432, 99999992}, Array);
    }

    @Test
    void ninthTest() {
        int[] Array = new int[]{1, 0, 1, 0, 0, 1, 0};
        Heapsort.heapsort(Array);
        assertArrayEquals(new int[]{0, 0, 0, 0, 1, 1, 1}, Array);
    }

    @Test
    void tenthTest() {
        int[] Array = new int[]{73, 65, 65, 49, 97, 41, 24, 18, 81};
        Heapsort.heapsort(Array);
        assertArrayEquals(new int[]{18, 24, 41, 49, 65, 65, 73, 81, 97}, Array);
    }

    @Test
    void eleventhTest() {
        int[] Array = new int[]{46, 88, 20, 88, 10, 89, 59, 80, 86, 23};
        Heapsort.heapsort(Array);
        assertArrayEquals(new int[]{10, 20, 23, 46, 59, 80, 86, 88, 88, 89}, Array);
    }
}