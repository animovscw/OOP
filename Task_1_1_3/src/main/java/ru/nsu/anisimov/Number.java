package ru.nsu.anisimov;

/**
 * Class representing a numeric constant.
 */
public class Number extends Expression {
    private final double value;

    public Number(double number) {
        super();
        this.value = number;
    }

    public double getValue() {
        return value;
    }

    /**
     * Calculates the value of an expression.
     *
     * @param assignation numeric constant
     * @return Its value
     */
    @Override
    public double evaluate(String assignation) {
        return this.value;
    }

    /**
     * Returns the derivative of a constant with respect to the specified variable.
     *
     * @param variable the variable name
     * @return New Number object with value 0
     */
    @Override
    public Expression getDerivative(String variable) {
        return new Number(0);
    }

    /**
     * Returns a simplified version of an expression.
     *
     * @return A copy of it
     */
    @Override
    public Expression getSimplified() {
        return new Number(this.value);
    }

    /**
     * Returns a string representation of a number.
     *
     * @return Integer if the number is integer, in floating point format otherwise
     */
    @Override
    public String toString() {
        if (this.value % 1 == 0) {
            return "" + (int) this.value;
        } else {
            return "" + this.value;
        }
    }
}