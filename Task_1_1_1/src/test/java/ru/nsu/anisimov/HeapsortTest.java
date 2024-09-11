package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest {
    @Test
    void Test1() {
        int[] test1Array = new int[] {3, 1, 2};
        Heapsort.heapsort(test1Array);
        assertArrayEquals(new int[] {1, 2, 3}, test1Array);
    }

    @Test
    void Test2() {
        int[] test2Array = new int[] {4, 10, 3, 5, 1};
        Heapsort.heapsort(test2Array);
        assertArrayEquals(new int[] {1, 3, 4, 5, 10}, test2Array);
    }

    @Test
    void Test3() {
        int[] test3Array = new int[] {2, 3, 4, 5};
        Heapsort.heapsort(test3Array);
        assertArrayEquals(new int[] {2, 3, 4, 5}, test3Array);
    }

    @Test
    void Test4() {
        int[] test4Array = new int[] {1, 1};
        Heapsort.heapsort(test4Array);
        assertArrayEquals(new int[] {1, 1}, test4Array);
    }

    @Test
    void Test5() {
        int[] test5Array = new int[] {1};
        Heapsort.heapsort(test5Array);
        assertArrayEquals(new int[] {1}, test5Array);
    }

    @Test
    void Test6() {
        int[] test6Array = new int[] {50, 10, 49, 3, 4, 7, 20, 0};
        Heapsort.heapsort(test6Array);
        assertArrayEquals(new int[] {0, 3, 4, 7, 10, 20, 49, 50}, test6Array);
    }

    @Test
    void Test7() {
        int[] test7Array = new int[] {};
        Heapsort.heapsort(test7Array);
        assertArrayEquals(new int[] {}, test7Array);
    }

    @Test
    void Test8() {
        int[] test8Array = new int[] {99999992, 0, 0, 2, 23432, 1};
        Heapsort.heapsort(test8Array);
        assertArrayEquals(new int[] {0, 0, 1, 2, 23432, 99999992}, test8Array);
    }

    @Test
    void Test9() {
        int[] test9Array = new int[] {1, 0, 1, 0, 0, 1, 0};
        Heapsort.heapsort(test9Array);
        assertArrayEquals(new int[] {0, 0, 0, 0, 1, 1, 1}, test9Array);
    }

    @Test
    void Test10() {
        int[] test10Array = new int[] {46, 47, 42, 36, 90, 46, 19, 64, 37, 59, 41, 2, 71, 74, 51, 9, 21, 80, 67, 69, 72, 66, 4, 8, 5, 77, 26, 41, 15, 80, 8, 7, 56, 60, 43, 78, 33, 77, 56, 41, 70, 18, 42, 37, 1, 94, 53, 47, 41, 84};
        Heapsort.heapsort(test10Array);
        assertArrayEquals(new int[] {1, 2, 4, 5, 7, 8, 8, 9, 15, 18, 19, 21, 26, 33, 36, 37, 37, 41, 41, 41, 41, 42, 42, 43, 46, 46, 47, 47, 51, 53, 56, 56, 59, 60, 64, 66, 67, 69, 70, 71, 72, 74, 77, 77, 78, 80, 80, 84, 90, 94}, test10Array);
    }

    @Test
    void Test11() {
        int[] test11Array = new int[] {18136, 38008, 51142, 82335, 79658, 24934, 14921, 76384, 97380, 62524, 86715, 68879, 93557, 79885, 14022, 27584, 42933, 89908, 48611, 32949};
        Heapsort.heapsort(test11Array);
        assertArrayEquals(new int[] {14022, 14921, 18136, 24934, 27584, 32949, 38008, 42933, 48611, 51142, 62524, 68879, 76384, 79658, 79885, 82335, 86715, 89908, 93557, 97380}, test11Array);
    }

    @Test
    void Test13() {
        int[] test13Array = new int[] {810, 230, 1138, 489, 631, 184, 894, 532, 466, 93, 193, 1075, 1226, 28, 86, 192, 706, 1139, 1190, 517, 998, 328, 776, 819, 329, 175, 163, 63, 226, 870, 985, 656, 653, 967, 201, 848, 534, 245, 1032, 396, 783};
        Heapsort.heapsort(test13Array);
        assertArrayEquals(new int[] {28, 63, 86, 93, 163, 175, 184, 192, 193, 201, 226, 230, 245, 328, 329, 396, 466, 489, 517, 532, 534, 631, 653, 656, 706, 776, 783, 810, 819, 848, 870, 894, 967, 985, 998, 1032, 1075, 1138, 1139, 1190, 1226}, test13Array);
    }
}