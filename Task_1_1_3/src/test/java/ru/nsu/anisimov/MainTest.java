package ru.nsu.anisimov;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * MainTest is a test class for testing the functionality of the Main class.
 * It tests the main method of the program by simulating user input and capturing the output.
 */
public class MainTest {
    @Test
    void simpleTest() {
        String input = "(x+3)\nx\nx = 9\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Main.main(new String[0]);
        String result = out.toString().replaceAll("\\r\\n?", "\n");
        String expected =
                "(x+3)\nExpression: (x+3)\nDerivative: (1+0)\nAssigned: 12.0\nSimplified: (x+3)\n";

        Assertions.assertEquals(expected, result);
        Assertions.assertEquals(expected.length(), result.length());

        for (int i = 0; i < expected.length(); ++i) {
            if (expected.charAt(i) != result.charAt(i)) {
                System.out.println(
                        "Mismatch at position "
                        + i
                        + ": expected '"
                        + expected.charAt(i)
                        + "' but was '"
                        + result.charAt(i)
                        + "'"
                );
            }

            Assertions.assertEquals(expected.charAt(i), result.charAt(i));

        }
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    void notSimpleTest() {
        String input = "((3+(2*x))+1)\nx\nx = 10";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        Main.main(new String[0]);
        String result = out.toString().replaceAll("\\r\\n?", "\n");
        String expected = "((3+(2*x))+1)\n"
                          + "Expression: ((3+(2*x))+1)\n"
                          + "Derivative: ((0+((0*x)+(2*1)))+0)"
                          + "\nAssigned: 24.0\nSimplified: ((3+(2*x))+1)\n";
        Assertions.assertEquals(expected, result);
        Assertions.assertEquals(expected.length(), result.length());

        for (int i = 0; i < expected.length(); ++i) {
            if (expected.charAt(i) != result.charAt(i)) {
                System.out.println(i + expected.charAt(i) + result.charAt(i));
            }
            Assertions.assertEquals(expected.charAt(i), result.charAt(i));
        }

        System.setIn(System.in);
        System.setOut(System.out);
    }
}