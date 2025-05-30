package ru.nsu.anisimov.distributed.worker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPrimeWorker {
    @Test
    public void testIsPrime() {
        assertFalse(PrimeWorker.isPrime(1));
        assertTrue(PrimeWorker.isPrime(2));
        assertTrue(PrimeWorker.isPrime(3));
        assertFalse(PrimeWorker.isPrime(4));
        assertTrue(PrimeWorker.isPrime(5));
        assertFalse(PrimeWorker.isPrime(9));
        assertTrue(PrimeWorker.isPrime(13));
        assertFalse(PrimeWorker.isPrime(15));
    }

    @Test
    public void testCheckForNonPrimes() {
        assertTrue(PrimeWorker.checkForNonPrimes(new int[]{4, 5, 6}));
        assertFalse(PrimeWorker.checkForNonPrimes(new int[]{2, 3, 5}));
        assertTrue(PrimeWorker.checkForNonPrimes(new int[]{1, 2, 3}));
    }
}