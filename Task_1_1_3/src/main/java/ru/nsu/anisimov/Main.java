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
        Scanner scanner = new Scanner(System.in);

        String infix = scanner.nextLine();
        Expression e = Transformation.getExpression(infix);
        e.print();
        System.out.println("Expression: " + e);
        String variable = scanner.nextLine();
        System.out.println("Derivative: " + e.getDerivative(variable).toString());
        String assignation = scanner.nextLine();
        System.out.println("Assigned: " + e.evaluate(assignation));
        System.out.println("Simplified: " + e.getSimplified().toString());

        scanner.close();
    }
}