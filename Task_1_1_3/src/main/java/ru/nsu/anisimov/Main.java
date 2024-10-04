package ru.nsu.anisimov;

import java.util.Scanner;

/**
 * The class is the entry point of the application, allowing users
 * to input a mathematical expression in infix notation, compute its derivative,
 * evaluate it for a given variable assignment, and simplify it.
 */
public class Main {
    /**
     * The main method reads a mathematical expression from the user in infix notation,
     * calculates its derivative with respect to a variable, evaluates the expression
     * with a specific variable assignment, and simplifies the expression.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            String infix = scanner.nextLine();
            Expression expression = Transformation.getExpression(infix);
            expression.print();
            System.out.println("Expression: " + expression);
            String variable = scanner.nextLine();
            Expression derivative = expression.getDerivative(variable);
            System.out.println("Derivative: " + derivative);
            String assignation = scanner.nextLine();
            double assignedValue = expression.eval(assignation);
            System.out.println("Assigned Value: " + assignedValue);
            Expression simplified = expression.getSimplified();
            System.out.println("Simplified Expression: " + simplified);
        }
    }
}