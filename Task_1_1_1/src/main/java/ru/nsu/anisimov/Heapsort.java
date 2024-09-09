package ru.nsu.anisimov;

import java.util.Scanner;

public class Heapsort {

    public static void heapsort(int array[]) {
        int len = array.length;
        for (int i = len / 2 - 1; i >= 0; --i) {
            heapify(array, len, i);
        }
        for (int i = len - 1; i >= 0; --i) {
            int tmp = array[0];
            array[0] = array[i];
            array[i] = tmp;
            heapify(array, i, 0);
        }

    }

    public static void heapify(int array[], int len, int i) {
        int parent = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < len && array[parent] < array[left]) {
            parent = left;
        }
        if (right < len && array[parent] < array[right]) {
            parent = right;
        }
        if (parent != i) {
            int tmp = array[i];
            array[i] = array[parent];
            array[parent] = tmp;
            heapify(array, len, parent);
        }
    }

    public static void main(String[] args) {
    }
}
