package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * The tests the Transformation class.
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
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("a", "b", "c", "+", "*", "d", "-"));
        ArrayList<String> result = Transformation.getReversePolish(expression);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void expressionWithDivisionAndLongVariablesTest() {
        String expression = "number1/(value+3)-factor";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("number1", "value", "3", "+", "/", "factor", "-"));
        ArrayList<String> result = Transformation.getReversePolish(expression);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void nestedBracketsTest() {
        String expression = "(a+b)*(c-(d/e))";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("a", "b", "+", "c", "d", "e", "/", "-", "*"));
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
}
