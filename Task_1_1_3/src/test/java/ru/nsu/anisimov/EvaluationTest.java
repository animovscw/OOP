package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The class tests the evaluation function of the Transformation class and Expression objects.
 */
public class EvaluationTest {

    @Test
    void simpleTest() {
        String expression = "x+3";
        double expected = 12;
        double result = Transformation.getExpression(expression).eval("x=9");

        Assertions.assertEquals(expected, result);
    }

    @Test
    void multipleTest() {
        String expression = "(x+y-z)*w/q";
        double expected = 1;
        double result =
                Transformation.getExpression(expression)
                        .eval("x=1; y=1; z=1; w=1; q=1");

        Assertions.assertEquals(expected, result);
    }

    @Test
    void toughTest() {
        String expression = "(((x+y)-2+x)*z)*3";
        double expected = 180;
        double result = Transformation.getExpression(expression).eval("x=3; y=8; z=5");

        Assertions.assertEquals(expected, result);
    }

    @Test
    void subtractionTest() {
        String expression = "x-4";
        double expected = 6;
        double result = Transformation.getExpression(expression).eval("x=10");

        Assertions.assertEquals(expected, result);
    }

    @Test
    void multiplicationTest() {
        String expression = "x*5";
        double expected = 25;
        double result = Transformation.getExpression(expression).eval("x=5");

        Assertions.assertEquals(expected, result);
    }

    @Test
    void divisionTest() {
        String expression = "x/2";
        double expected = 5;
        double result = Transformation.getExpression(expression).eval("x=10");

        Assertions.assertEquals(expected, result);
    }
}