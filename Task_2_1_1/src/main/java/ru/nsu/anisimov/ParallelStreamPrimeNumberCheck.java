package ru.nsu.anisimov;

import java.util.Arrays;

public class ParallelStreamPrimeNumberCheck implements PrimeNumberCheck {
    @Override
    public boolean hasNonPrime(int[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !SequentialPrimeNumberCheck.isPrime(num));
    }
}