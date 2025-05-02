package ru.nsu.anisimov.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestResultTest {

    @Test
    void tupleConstructorAndToString() {
        TestResult tr = new TestResult(5, 2, 1);
        assertEquals(5, tr.getPassed());
        assertEquals(2, tr.getFailed());
        assertEquals(1, tr.getSkipped());

        String s = tr.toString();
        assertTrue(s.contains("passed:5"));
        assertTrue(s.contains("failed:2"));
        assertTrue(s.contains("skipped:1"));
    }
}
