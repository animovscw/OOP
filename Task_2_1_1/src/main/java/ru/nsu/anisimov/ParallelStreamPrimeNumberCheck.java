package ru.nsu.anisimov;

import java.util.Arrays;

public class ParallelStreamPrimeNumberCheck {
    public static boolean NonPrimeParallelStream(int[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !SequentialPrimeNumberCheck.isPrime(num));
    }
}