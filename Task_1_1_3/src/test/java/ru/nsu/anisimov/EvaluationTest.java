package ru.nsu.anisimov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EvaluationTest {
    @Test
    void simpleEvalTest() {
        String expression = "2+x";
        double expected = 5;
        double result = Transformation.getExpression(expression).evaluate("x = 3");

        assertEquals(expected, result);
    }

    @Test
    void manyVariablesTest() {
        String expression = "(x+y-long)*story/short";
        double expected = 10;
        double result =
                Transformation.getExpression(expression)
                        .evaluate("x = 3; y = 8; long = 1; story = 2; short = 2");

        assertEquals(expected, result);
    }

    @Test
    void hardEvalTest() {
        String expression = "(((x+y)-2+x)*z)*3";
        double expected = 180;
        double result = Transformation.getExpression(expression).evaluate("x = 3; y = 8; z = 5");

        assertEquals(expected, result);
    }
}