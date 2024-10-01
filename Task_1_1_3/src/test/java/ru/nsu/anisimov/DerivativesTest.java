package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The tests to test the derivation function.
 */
public class DerivativesTest {
    @Test
    void simpleTest() {
        String expression = "(121+x*4)*x";
        String expected = "(((0+((1*4)+(x*0)))*x)+((121+(x*4))*1))";
        String result = Transformation.getExpression(expression).getDerivative("x").toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void exampleTest() {
        String expression = "(12+(2*x))";
        String expected = "(0+((0*x)+(2*1)))";
        String result = Transformation.getExpression(expression).getDerivative("x").toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void multipleTest() {
        String expression = "x+y*2";
        String expected = "(1+((0*2)+(y*0)))";
        String result = Transformation.getExpression(expression).getDerivative("x").toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void divisionTest() {
        String expression = "(5-x)/2";
        String expected = "((((0-1)*2)+((5-x)*0))/(2*2))";
        String result = Transformation.getExpression(expression).getDerivative("x").toString();
        Assertions.assertEquals(expected, result);
    }

    @Test
    void complexDivisionTest() {
        String expression = "(5-x)/x";
        String expected = "((((0-1)*x)+((5-x)*1))/(x*x))";
        String result = Transformation.getExpression(expression).getDerivative("x").toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void powerTest() {
        String expression = "x*x";
        String expected = "((1*x)+(x*1))";
        String result = Transformation.getExpression(expression).getDerivative("x").toString();

        Assertions.assertEquals(expected, result);
    }
}