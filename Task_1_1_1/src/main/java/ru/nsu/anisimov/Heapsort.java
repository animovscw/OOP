package ru.nsu.anisimov;


/**
 * Convert the array into a max heap using heapify,
 * Then one by one delete the root node of the Max-heap
 * and replace it with the last node and heapify.
 *
 * @author kirill
 * @version 1.0
 */
public class Heapsort {
    /**
     * @param array that contain the input numbers and after the algorithm output the sorted array
     */
    public static void heapsort(int[] array) {
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

    /**
     * @param array array which is used to represent the tree
     * @param len   the array length
     * @param i     index
     */
    public static void heapify(int[] array, int len, int i) {
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
