package ru.nsu.anisimov;

import java.util.Arrays;

/**
 * Check for prime numbers in sequential.
 */
public class SequentialPrimeNumberCheck implements PrimeNumberCheck {

    //O(sqrt(n))

    /**
     * Check if the number is prime.
     *
     * @param number number
     * @return true if prime, false otherwise
     */
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number == 2 || number == 3) {
            return true;
        }
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }
        for (int i = 5; i <= Math.sqrt(number); i = i + 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasNonPrime(int[] numbers) {
        return Arrays.stream(numbers).anyMatch(num -> !isPrime(num));
    }
}