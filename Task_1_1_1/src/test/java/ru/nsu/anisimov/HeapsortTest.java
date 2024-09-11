package ru.nsu.anisimov;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HeapsortTest {
    @Test
    void Test1() {
        int[] test1Array = new int[]{3, 1, 2};
        Heapsort.heapsort(test1Array);
        assertArrayEquals(new int[]{1, 2, 3}, test1Array);
    }

    @Test
    void Test2() {
        int[] test2Array = new int[]{4, 10, 3, 5, 1};
        Heapsort.heapsort(test2Array);
        assertArrayEquals(new int[]{1, 3, 4, 5, 10}, test2Array);
    }

    @Test
    void Test3() {
        int[] test3Array = new int[]{2, 3, 4, 5};
        Heapsort.heapsort(test3Array);
        assertArrayEquals(new int[]{2, 3, 4, 5}, test3Array);
    }

    @Test
    void Test4() {
        int[] test4Array = new int[]{1, 1};
        Heapsort.heapsort(test4Array);
        assertArrayEquals(new int[]{1, 1}, test4Array);
    }

    @Test
    void Test5() {
        int[] test5Array = new int[]{1};
        Heapsort.heapsort(test5Array);
        assertArrayEquals(new int[]{1}, test5Array);
    }

    @Test
    void Test6() {
        int[] test6Array = new int[]{50, 10, 49, 3, 4, 7, 20, 0};
        Heapsort.heapsort(test6Array);
        assertArrayEquals(new int[]{0, 3, 4, 7, 10, 20, 49, 50}, test6Array);
    }

    @Test
    void Test7() {
        int[] test7Array = new int[]{};
        Heapsort.heapsort(test7Array);
        assertArrayEquals(new int[]{}, test7Array);
    }

    @Test
    void Test8() {
        int[] test8Array = new int[]{99999992, 0, 0, 2, 23432, 1};
        Heapsort.heapsort(test8Array);
        assertArrayEquals(new int[]{0, 0, 1, 2, 23432, 99999992}, test8Array);
    }

    @Test
    void Test9() {
        int[] test9Array = new int[]{1, 0, 1, 0, 0, 1, 0};
        Heapsort.heapsort(test9Array);
        assertArrayEquals(new int[]{0, 0, 0, 0, 1, 1, 1}, test9Array);
    }
}