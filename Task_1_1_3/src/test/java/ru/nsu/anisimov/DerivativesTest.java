package ru.nsu.anisimov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DerivativesTest {
    @Test
    void simpleTest() {
        String expression = "(121+x*4)*x";
        String expected = "(((0+((1*4)+(x*0)))*x)+((121+(x*4))*1))";
        String result = Transformation.getExpression(expression).getDerivative("x").toString();

        assertEquals(expected, result);
    }

    @Test
    void exampleTest() {
        String expression = "(12+(2*x))";
        String expected = "(0+((0*x)+(2*1)))";
        String result = Transformation.getExpression(expression).getDerivative("x").toString();

        assertEquals(expected, result);
    }

    @Test
    void multipleTest() {
        String expression = "x+y*2";
        String expected = "(1+((0*2)+(y*0)))";
        String result = Transformation.getExpression(expression).getDerivative("x").toString();

        assertEquals(expected, result);
    }

    @Test
    void divisionTest() {
        String expression = "(5-x)/2";
//        String expected = "((((5-x)*0)+((0-1)*2))/(2*2))";
        String expected = "((((0-1)*2)+((5-x)*0))/(2*2))";
        String result = Transformation.getExpression(expression).getDerivative("x").toString();
//        System.out.println(result);
        assertEquals(expected, result);
    }
}