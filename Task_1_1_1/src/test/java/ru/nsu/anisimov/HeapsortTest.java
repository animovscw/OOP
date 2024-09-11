package ru.nsu.anisimov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class HeapsortTest {
    @Test
    void firstTest() {
        int[] testArray = new int[] {3, 1, 2};
        Heapsort.heapsort(testArray);
        assertArrayEquals(new int[] {1, 2, 3}, testArray);
    }

    @Test
    void secondTest() {
        int[] testArray = new int[] {4, 10, 3, 5, 1};
        Heapsort.heapsort(testArray);
        assertArrayEquals(new int[] {1, 3, 4, 5, 10}, testArray);
    }

    @Test
    void thirdTest() {
        int[] testArray = new int[] {2, 3, 4, 5};
        Heapsort.heapsort(testArray);
        assertArrayEquals(new int[] {2, 3, 4, 5}, testArray);
    }

    @Test
    void fourthTest() {
        int[] testArray = new int[] {1, 1};
        Heapsort.heapsort(testArray);
        assertArrayEquals(new int[] {1, 1}, testArray);
    }

    @Test
    void fifthTest() {
        int[] testArray = new int[] {1};
        Heapsort.heapsort(testArray);
        assertArrayEquals(new int[] {1}, testArray);
    }

    @Test
    void sixthTest() {
        int[] testArray = new int[] {50, 10, 49, 3, 4, 7, 20, 0};
        Heapsort.heapsort(testArray);
        assertArrayEquals(new int[] {0, 3, 4, 7, 10, 20, 49, 50}, testArray);
    }

    @Test
    void seventhTest() {
        int[] testArray = new int[] {};
        Heapsort.heapsort(testArray);
        assertArrayEquals(new int[] {}, testArray);
    }

    @Test
    void eighthTest() {
        int[] testArray = new int[] {99999992, 0, 0, 2, 23432, 1};
        Heapsort.heapsort(testArray);
        assertArrayEquals(new int[] {0, 0, 1, 2, 23432, 99999992}, testArray);
    }

    @Test
    void ninthTest() {
        int[] testArray = new int[] {1, 0, 1, 0, 0, 1, 0};
        Heapsort.heapsort(testArray);
        assertArrayEquals(new int[] {0, 0, 0, 0, 1, 1, 1}, testArray);
    }

    @Test
    void tenthTest() {
        int[] testArray = new int[] {73, 65, 65, 49, 97, 41, 24, 18, 81};
        Heapsort.heapsort(testArray);
        assertArrayEquals(new int[] {18, 24, 41, 49, 65, 65, 73, 81, 97}, testArray);
    }

    @Test
    void eleventhTest() {
        int[] testArray = new int[] {46, 88, 20, 88, 10, 89, 59, 80, 86, 23};
        Heapsort.heapsort(testArray);
        assertArrayEquals(new int[] {10, 20, 23, 46, 59, 80, 86, 88, 88, 89}, testArray);
    }
}