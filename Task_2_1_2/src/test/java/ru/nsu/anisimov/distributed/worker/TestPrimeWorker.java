package ru.nsu.anisimov.distributed.worker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestPrimeWorker {

    @Test
    void testIsPrime() {
        assertAll(
                () -> assertFalse(PrimeWorker.isPrime(0)),
                () -> assertFalse(PrimeWorker.isPrime(1)),
                () -> assertTrue(PrimeWorker.isPrime(2)),
                () -> assertTrue(PrimeWorker.isPrime(3)),
                () -> assertFalse(PrimeWorker.isPrime(4)),
                () -> assertTrue(PrimeWorker.isPrime(5)),
                () -> assertFalse(PrimeWorker.isPrime(6)),
                () -> assertTrue(PrimeWorker.isPrime(7)),
                () -> assertFalse(PrimeWorker.isPrime(8)),
                () -> assertFalse(PrimeWorker.isPrime(9)),
                () -> assertFalse(PrimeWorker.isPrime(10)),
                () -> assertTrue(PrimeWorker.isPrime(11)),
                () -> assertTrue(PrimeWorker.isPrime(13)),
                () -> assertFalse(PrimeWorker.isPrime(15)),
                () -> assertTrue(PrimeWorker.isPrime(17)),
                () -> assertTrue(PrimeWorker.isPrime(19)),
                () -> assertFalse(PrimeWorker.isPrime(21)),
                () -> assertTrue(PrimeWorker.isPrime(23)),
                () -> assertFalse(PrimeWorker.isPrime(25)),
                () -> assertTrue(PrimeWorker.isPrime(29)),
                () -> assertTrue(PrimeWorker.isPrime(7919)) // Large prime
        );
    }

    @Test
    void testCheckForNonPrimes() {
        assertAll(
                () -> assertFalse(PrimeWorker.checkForNonPrimes(new int[]{2, 3, 5, 7, 11})),
                () -> assertTrue(PrimeWorker.checkForNonPrimes(new int[]{2, 3, 4, 5})),
                () -> assertTrue(PrimeWorker.checkForNonPrimes(new int[]{1, 2, 3})),
                () -> assertFalse(PrimeWorker.checkForNonPrimes(new int[]{})),
                () -> assertTrue(PrimeWorker.checkForNonPrimes(new int[]{4})),
                () -> assertFalse(PrimeWorker.checkForNonPrimes(new int[]{5}))
        );
    }
}