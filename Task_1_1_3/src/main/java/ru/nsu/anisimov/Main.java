package ru.nsu.anisimov;

import java.util.Scanner;

public class Main {
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