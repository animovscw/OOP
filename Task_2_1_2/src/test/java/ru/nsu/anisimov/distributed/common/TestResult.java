package ru.nsu.anisimov.distributed.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestResult {
    @Test
    public void testResultCreation() {
        Result result1 = new Result(true);
        assertTrue(result1.getHasNonPrime());

        Result result2 = new Result(false);
        assertFalse(result2.getHasNonPrime());
    }
}