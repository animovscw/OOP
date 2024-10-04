package ru.nsu.anisimov;

import java.util.Map;

/**
 * Represents the multiplication of two mathematical expressions.
 */
public class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Constructs a new object representing the multiplication of two expressions.
     *
     * @param left  the left operand
     * @param right the right operand
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the multiplication of two expressions based on the provided variable assignments.
     *
     * @param assignations a map containing variable assignments
     * @return The result of evaluating the multiplication
     */
    @Override
    protected double evaluate(Map<String, Integer> assignations) {
        return this.left.evaluate(assignations)
               * this.right.evaluate(assignations);
    }

    /**
     * Computes the derivative of the multiplication expression.
     *
     * @param variable the variable with respect to which the derivative is taken
     * @return A new expression representing the derivative of the multiplication
     */
    @Override
    protected Expression getDerivative(String variable) {
        Expression leftDerivative = this.left.getDerivative(variable);
        Expression rightDerivative = this.right.getDerivative(variable);
        return new Add(
                new Mul(
                        leftDerivative, this.right
                ),
                new Mul(
                        this.left,
                        rightDerivative
                )
        );
    }

    /**
     * Simplifies the multiplication expression.
     * If either operand is 0, returns a new expression representing 0.
     * If one operand is 1, returns the other operand.
     * If both operands are numbers, returns their product as a new expression.
     * Otherwise, returns a new expression of the simplified left and right expressions.
     *
     * @return The simplified expression
     */
    @Override
    protected Expression getSimplified() {
        Expression simplifiedLeft = this.left.getSimplified();
        Expression simplifiedRight = this.right.getSimplified();

        if ((simplifiedLeft instanceof Number
             && ((Number) simplifiedLeft).getValue() == 0)
            || (simplifiedRight instanceof Number
                && ((Number) simplifiedRight).getValue() == 0)) {
            return new Number(0);
        }

        if (simplifiedLeft instanceof Number
            && ((Number) simplifiedLeft).getValue() == 1) {
            return simplifiedRight;
        }

        if (simplifiedRight instanceof Number
            && ((Number) simplifiedRight).getValue() == 1) {
            return simplifiedLeft;
        }

        if (simplifiedLeft instanceof Number
            && simplifiedRight instanceof Number) {
            double product = ((Number) simplifiedLeft).getValue()
                             * ((Number) simplifiedRight).getValue();
            return new Number(product);
        }

        return new Mul(simplifiedLeft, simplifiedRight);
    }

    /**
     * Returns the string representation of the multiplication expression.
     *
     * @return A string
     */
    @Override
    public String toString() {
        return "("
               + this.left.toString()
               + "*"
               + this.right.toString()
               + ")";
    }
}