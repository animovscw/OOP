package ru.nsu.anisimov;

import java.util.Map;

/**
 * Represents a variable in a mathematical expression.
 */
public class Variable extends Expression {
    private final String variableName;

    /**
     * Constructs a new object with the specified name.
     *
     * @param name the name of the variable
     */
    public Variable(String name) {
        this.variableName = name;
    }

    /**
     * Evaluates the value of the variable based on the provided variable assignments.
     *
     * @param assignations a map containing variable assignments
     * @return The value of the variable as a double
     */
    @Override
    protected double evaluate(Map<String, Integer> assignations) {
        if (assignations.containsKey(this.variableName)) {
            return assignations.get(this.variableName);
        }
        return 0;
    }

    /**
     * Computes the derivative of the variable with respect to the specified variable.
     *
     * @param variable the variable with respect to which the derivative is taken
     * @return A new expression representing 1 if the variable matches, otherwise 0
     */
    @Override
    protected Expression getDerivative(String variable) {
        if (variable.equals(this.variableName)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * Simplifies the variable expression.
     *
     * @return The variable itself
     */
    @Override
    protected Expression getSimplified() {
        return this;
    }

    /**
     * Returns the string representation of the variable.
     *
     * @return The name of the variable
     */
    @Override
    public String toString() {
        return this.variableName;
    }
}