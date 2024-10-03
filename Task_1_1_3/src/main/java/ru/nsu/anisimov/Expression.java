package ru.nsu.anisimov;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for all mathematical expressions.
 */
public abstract class Expression {

    /**
     * Evaluates the expression based on the provided variable assignments.
     *
     * @param assignationString a string containing variable assignments
     * @return The result of the evaluated expression
     */
    public double eval(String assignationString) {
        Map<String, Integer> assignations = parseAssignations(assignationString);
        return evaluate(assignations);
    }

    /**
     * Parses a string of variable assignments into a map.
     *
     * @param s the assignment string
     * @return A map where keys are variable names and values are their assigned integer values
     */
    private Map<String, Integer> parseAssignations(String s) {
        Map<String, Integer> assignations = new HashMap<>();
        String[] assignments = s.split(";");
        for (String assignment : assignments) {
            String[] parts = assignment.split("=");
            if (parts.length == 2) {
                String variable = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                assignations.put(variable, value);
            }
        }
        return assignations;
    }

    /**
     * Evaluates the expression based on the provided variable assignments.
     * This method is implemented by the subclasses to define specific evaluation logic.
     *
     * @param assignations a map containing variable assignments
     * @return The result of the evaluated expression
     */
    abstract double evaluate(Map<String, Integer> assignations);

    /**
     * Computes the derivative of the expression with respect to the given variable.
     *
     * @param variable the variable with respect to which the derivative is taken
     * @return A new Expression representing the derivative
     */
    abstract Expression getDerivative(String variable);

    /**
     * Simplifies the expression by performing arithmetic simplifications.
     *
     * @return A new Expression representing the simplified expression.
     */
    abstract Expression getSimplified();

    /**
     * Prints the string representation of the expression.
     */
    public void print() {
        System.out.println(this);
    }
}
