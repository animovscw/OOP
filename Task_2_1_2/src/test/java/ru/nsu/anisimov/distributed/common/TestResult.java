package ru.nsu.anisimov.distributed.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for class Result.
 */
public class TestResult {
    @Test
    public void testResultCreation() {
        Result result1 = new Result(true);
        Assertions.assertTrue(result1.getHasNonPrime());

        Result result2 = new Result(false);
        Assertions.assertFalse(result2.getHasNonPrime());
    }
}