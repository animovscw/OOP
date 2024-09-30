package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

public class EvaluationTest {
    @Test
    void simpleTest() {
        String expression = "x+3";
        double expected = 12;
        double result = Transformation.getExpression(expression).evaluate("x = 9");

        Assertions.assertEquals(expected, result);
    }

    @Test
    void multipleTest() {
        String expression = "(x+y-z)*w/q";
        double expected = 1;
        double result =
                Transformation.getExpression(expression)
                        .evaluate("x = 1; y = 1; z = 1; w = 1; q = 1"
                        );

        Assertions.assertEquals(expected, result);
    }

    @Test
    void toughTest() {
        String expression = "(((x+y)-2+x)*z)*3";
        double expected = 180;
        double result = Transformation.getExpression(expression).evaluate("x = 3; y = 8; z = 5");

        Assertions.assertEquals(expected, result);
    }
}