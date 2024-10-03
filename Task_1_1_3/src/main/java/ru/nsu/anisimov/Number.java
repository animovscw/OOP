package ru.nsu.anisimov;

import java.util.Map;

/**
 * Represents a numerical constant in a mathematical expression.
 */
public class Number extends Expression {
    private final double value;

    /**
     * Constructs a new Number object representing a constant value.
     *
     * @param value the value
     */
    public Number(double value) {
        this.value = value;
    }

    /**
     * Retrieves the numerical value of the constant.
     *
     * @return The numerical value
     */
    public double getValue() {
        return value;
    }

    /**
     * Evaluates the numerical constant.
     *
     * @param assignations a map containing variable assignments (not used)
     * @return The numerical value
     */
    @Override
    protected double evaluate(Map<String, Integer> assignations) {
        return value;
    }

    /**
     * Computes the derivative of the numerical constant, which is always zero.
     *
     * @param variable the variable with respect to which the derivative is taken
     * @return A new Number object representing zero.
     */
    @Override
    protected Expression getDerivative(String variable) {
        return new Number(0);
    }

    /**
     * Simplifies the numerical constant, which is already in its simplest form.
     *
     * @return The numerical constant itself
     */
    @Override
    protected Expression getSimplified() {
        return this;
    }

    /**
     * Returns the string representation of the numerical constant.
     *
     * @return The numerical value as a string
     */
    @Override
    public String toString() {
        if (value == (int) value) {
            return String.valueOf((int) value);
        }
        return String.valueOf(value);
    }
}