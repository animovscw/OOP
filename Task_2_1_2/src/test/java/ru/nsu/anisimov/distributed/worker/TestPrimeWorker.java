package ru.nsu.anisimov.distributed.worker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPrimeWorker {

    @Test
    public void testIsPrime_basicCases() {
        assertFalse(PrimeWorker.isPrime(-1));
        assertFalse(PrimeWorker.isPrime(0));
        assertFalse(PrimeWorker.isPrime(1));
        assertTrue(PrimeWorker.isPrime(2));
        assertTrue(PrimeWorker.isPrime(3));
        assertFalse(PrimeWorker.isPrime(4));
        assertTrue(PrimeWorker.isPrime(5));
    }

    @Test
    public void testIsPrime_largePrime() {
        assertTrue(PrimeWorker.isPrime(982451653));
    }

    @Test
    public void testIsPrime_largeNonPrime() {
        assertFalse(PrimeWorker.isPrime(1000000000));
    }

    @Test
    public void testCheckForNonPrimes_allPrimes() {
        int[] primes = {2, 3, 5, 7, 11};
        assertFalse(PrimeWorker.checkForNonPrimes(primes));
    }

    @Test
    public void testCheckForNonPrimes_withNonPrime() {
        int[] array = {2, 4, 6};
        assertTrue(PrimeWorker.checkForNonPrimes(array));
    }
}
