package ru.nsu.anisimov;

import java.util.Map;

/**
 * Represents the division of two mathematical expressions.
 */
public class Div extends Expression {
    private final Expression numerator;
    private final Expression denominator;

    /**
     * Constructs a new object representing the division of two expressions.
     *
     * @param numerator   the numerator expression
     * @param denominator the denominator expression
     */
    public Div(Expression numerator, Expression denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Evaluates the division expression based on the provided variable assignments.
     *
     * @param assignations a map containing variable assignments
     * @return The result of the division as a double
     * @throws ArithmeticException If division by zero occurs.
     */
    @Override
    protected double evaluate(Map<String, Integer> assignations) {
        double denomValue = this.denominator.evaluate(assignations);
        if (denomValue == 0) {
            throw new ArithmeticException("Division by zero.");
        }
        return this.numerator.evaluate(assignations) / denomValue;
    }

    /**
     * Computes the derivative of the division expression with respect to the specified variable.
     *
     * @param variable the variable with respect to which the derivative is taken
     * @return A new expression representing the derivative
     */
    @Override
    protected Expression getDerivative(String variable) {
        Expression numeratorDerivative = new Sub(
                new Mul(this.numerator.getDerivative(variable), this.denominator),
                new Mul(this.numerator, this.denominator.getDerivative(variable))
        );
        Expression denominatorSquared = new Mul(this.denominator, this.denominator);
        return new Div(numeratorDerivative, denominatorSquared);
    }

    /**
     * Simplifies the division expression.
     * If both numerator and denominator are numbers,
     * computes their division and returns a new Number.
     * If the numerator is zero, returns the number 0.
     * If the denominator is one, returns the numerator.
     * Otherwise, returns a new expression consisting of simplified subexpressions.
     *
     * @return A simplified Expression
     */
    @Override
    protected Expression getSimplified() {
        Expression simplifiedNumerator = this.numerator.getSimplified();
        Expression simplifiedDenominator = this.denominator.getSimplified();

        if (simplifiedNumerator instanceof Number
            && ((Number) simplifiedNumerator).getValue() == 0) {
            return new Number(0);
        }

        if (simplifiedDenominator instanceof Number
            && ((Number) simplifiedDenominator).getValue() == 1) {
            return simplifiedNumerator;
        }

        if (simplifiedNumerator instanceof Number
            && simplifiedDenominator instanceof Number) {
            double denomValue = ((Number) simplifiedDenominator).getValue();
            if (denomValue == 0) {
                throw new ArithmeticException("Division by zero.");
            }
            return new Number(((Number) simplifiedNumerator).getValue() / denomValue);
        }

        return new Div(simplifiedNumerator, simplifiedDenominator);
    }

    /**
     * Returns the string representation of the division expression.
     *
     * @return A string
     */
    @Override
    public String toString() {
        return "("
               + this.numerator.toString()
               + "/"
               + this.denominator.toString()
               + ")";
    }
}