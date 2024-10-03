package ru.nsu.anisimov;

import java.util.Map;

/**
 * Represents the subtraction of two mathematical expressions.
 */
public class Sub extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Constructs a new object representing the subtraction of two expressions.
     *
     * @param left  the left operand of the subtraction
     * @param right the right operand of the subtraction
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the subtraction expression based on the provided variable assignments.
     *
     * @param assignations a map containing variable assignments
     * @return The result of the subtraction as a double
     */
    @Override
    protected double evaluate(Map<String, Integer> assignations) {
        return this.left.evaluate(assignations) - this.right.evaluate(assignations);
    }

    /**
     * Computes the derivative of the subtraction expression
     * with respect to the specified variable.
     *
     * @param variable the variable with respect to which the derivative is taken
     * @return A new expression representing the derivative
     */
    @Override
    protected Expression getDerivative(String variable) {
        return new Sub(
                this.left.getDerivative(variable),
                this.right.getDerivative(variable)
        );
    }

    /**
     * Simplifies the subtraction expression.
     * If both subexpressions are numbers, subtracts them and returns the new expression.
     * If the left and right subexpressions are identical, returns the number 0.
     * Otherwise, returns a new expression consisting of simplified subexpressions.
     *
     * @return A simplified Expression
     */
    @Override
    protected Expression getSimplified() {
        Expression simplifiedLeft = this.left.getSimplified();
        Expression simplifiedRight = this.right.getSimplified();

        if (simplifiedLeft instanceof Number
            && simplifiedRight instanceof Number) {
            double difference = ((Number) simplifiedLeft).getValue()
                                - ((Number) simplifiedRight).getValue();
            return new Number(difference);
        }
        if (simplifiedLeft.toString().equals(simplifiedRight.toString())) {
            return new Number(0);
        }
        if (simplifiedRight instanceof Number
            && ((Number) simplifiedRight).getValue() == 0) {
            return simplifiedLeft;
        }
        return new Sub(simplifiedLeft, simplifiedRight);
    }

    /**
     * Returns the string representation of the subtraction expression.
     *
     * @return A string
     */
    @Override
    public String toString() {
        return "("
               + this.left.toString()
               + "-"
               + this.right.toString()
               + ")";
    }
}