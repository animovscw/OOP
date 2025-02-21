package ru.nsu.anisimov;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * General test to check different realizations.
 */
public class PrimeNumberCheckTest {
    @ParameterizedTest
    @ArgumentsSource(PrimeNumberCheckProvider.class)
    void testNonPrimeNumbers(PrimeNumberCheck checker) throws InterruptedException {
        int[] arr1 = {6, 8, 7, 13, 5, 9, 4};
        int[] arr2 = {1, 6, 8, 7, 13, 5, 9, 4};
        int[] arr3 = {49, 6, 8, 7, 13, 5, 9, 4};

        long startTime = System.nanoTime();
        Assertions.assertTrue(checker.hasNonPrime(arr1));
        Assertions.assertTrue(checker.hasNonPrime(arr2));
        Assertions.assertTrue(checker.hasNonPrime(arr3));
        long endTime = System.nanoTime();
        System.out.println("Execution time (NonPrime test, "
                + checker.getClass().getSimpleName() + "): " + (endTime - startTime) + " ns");
    }

    @ParameterizedTest
    @ArgumentsSource(PrimeNumberCheckProvider.class)
    void testPrimeNumbers(PrimeNumberCheck checker) throws InterruptedException {
        int[] arr1 = {
            20319251, 6997901, 6997927, 6997937, 17858849,
            6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053
        };
        int[] arr2 = {
            2, 20319251, 6997901, 6997927, 6997937, 17858849,
            6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053
        };
        long startTime = System.nanoTime();
        Assertions.assertFalse(checker.hasNonPrime(arr1));
        Assertions.assertFalse(checker.hasNonPrime(arr2));
        long endTime = System.nanoTime();
        System.out.println("Execution time (Prime test, "
                + checker.getClass().getSimpleName() + "): " + (endTime - startTime) + " ns");
    }

    static class PrimeNumberCheckProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(new SequentialPrimeNumberCheck()),
                    Arguments.of(new ParallelStreamPrimeNumberCheck()),
                    Arguments.of(new ParallelThreadPrimeNumberCheck())
            );
        }
    }
}