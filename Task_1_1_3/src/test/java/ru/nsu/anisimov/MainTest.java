package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void testExpression() {
        Main main = new Main();
        int result = main.expression(2, 3);
        Assertions.assertEquals(5, result);
    }
}