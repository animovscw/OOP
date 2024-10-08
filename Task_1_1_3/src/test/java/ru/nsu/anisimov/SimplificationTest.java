package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The tests to test the simplification function.
 */
public class SimplificationTest {
    @Test
    void mulWithNullTest() {
        String expression = "(x*0)";
        String expected = "0";
        String result = Transformation.getExpression(expression).getSimplified().toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void mulWithOneTest() {
        String expression = "(x*1)";
        String expected = "x";
        String result = Transformation.getExpression(expression).getSimplified().toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void additionOfTwoIdenticalTest() {
        String expression = "(3+3)";
        String expected = "6";
        String result = Transformation.getExpression(expression).getSimplified().toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void varAndNumTest() {
        String expression = "((x*1)+(0+3))";
        String expected = "(x+3)";
        String result = Transformation.getExpression(expression).getSimplified().toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void subFromItselfTest() {
        String expression = "(x-x)";
        String expected = "0";
        String result = Transformation.getExpression(expression).getSimplified().toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void divByOneTest() {
        String expression = "(10/1)";
        String expected = "10";
        String result = Transformation.getExpression(expression).getSimplified().toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void universalTest() {
        String expression = "((((5-x)*0)+((0-1)*2))/(2*2))";
        String expected = "-0.5";
        String result = Transformation.getExpression(expression).getSimplified().toString();

        Assertions.assertEquals(expected, result);
    }
}
