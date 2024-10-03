package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The class tests the Transformation class and the ability to convert infix expressions to reverse Polish notation,
 * as well as the construction and evaluation of the resulting Expression objects.
 */
public class TransformationTest {

    @Test
    void basicAdditionTest() {
        String expression = "5+7";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("5", "7", "+"));
        ArrayList<String> result = Transformation.getReversePolish(expression);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void subtractionWithMultiplicationTest() {
        String expression = "8-4*3";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("8", "4", "3", "*", "-"));
        ArrayList<String> result = Transformation.getReversePolish(expression);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void expressionWithBracketsAndVariablesTest() {
        String expression = "a*(b+c)-d";
        ArrayList<String> expected = new ArrayList<>(
                Arrays.asList("a", "b", "c", "+", "*", "d", "-")
        );
        ArrayList<String> result = Transformation.getReversePolish(expression);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void expressionWithDivisionAndLongVariablesTest() {
        String expression = "number1/(value+3)-factor";
        ArrayList<String> expected = new ArrayList<>(
                Arrays.asList("number1", "value", "3", "+", "/", "factor", "-")
        );
        ArrayList<String> result = Transformation.getReversePolish(expression);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void nestedBracketsTest() {
        String expression = "(a+b)*(c-(d/e))";
        ArrayList<String> expected = new ArrayList<>(
                Arrays.asList("a", "b", "+", "c", "d", "e", "/", "-", "*")
        );
        ArrayList<String> result = Transformation.getReversePolish(expression);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void complexExpressionToStringTest() {
        String expression = "(x+y)*(z-2)";
        String expected = "((x+y)*(z-2))";
        String result = Transformation.getExpression(expression).toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void nestedBracketsExpressionTest() {
        String expression = "((x+y)*(z-w))/q";
        String expected = "(((x+y)*(z-w))/q)";
        String result = Transformation.getExpression(expression).toString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void evaluateExpressionTest() {
        String expression = "x+y";
        Expression expr = Transformation.getExpression(expression);
        String assignation = "x=3; y=4";
        double expected = 7.0; // 3 + 4 = 7
        double result = expr.eval(assignation);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void derivativeTest() {
        String expression = "x*y+y";
        Expression expr = Transformation.getExpression(expression);
        String variable = "x";
        String expected = "y";
        Expression derivative = expr.getDerivative(variable).getSimplified();

        Assertions.assertEquals(expected, derivative.toString());
    }


    @Test
    void simplifiedExpressionTest() {
        String expression = "x+0";
        Expression expr = Transformation.getExpression(expression);
        String expectedSimplified = "x";
        Expression simplified = expr.getSimplified();

        Assertions.assertEquals(expectedSimplified, simplified.toString());
    }
}