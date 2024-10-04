package ru.nsu.anisimov;

import java.util.Map;

/**
 * Represents the sum of two mathematical expressions.
 */
public class Add extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Constructs a new object representing the addition of two expressions.
     *
     * @param left  the left operand of the addition
     * @param right the right operand of the addition
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the sum of two expressions based on the provided variable assignments.
     *
     * @param assignations a map containing variable assignments
     * @return The result of evaluating the sum as a double
     */
    @Override
    protected double evaluate(Map<String, Integer> assignations) {
        return this.left.evaluate(assignations) + this.right.evaluate(assignations);
    }

    /**
     * Computes the derivative of the sum with respect to the specified variable.
     *
     * @param variable the variable with respect to which the derivative is taken
     * @return A new expression representing the derivative of the sum
     */
    @Override
    protected Expression getDerivative(String variable) {
        return new Add(
                this.left.getDerivative(variable),
                this.right.getDerivative(variable)
        );
    }

    /**
     * Simplifies the addition expression. If both operands are constants, returns a new
     * expression representing their sum. Otherwise, returns a new
     * expression of the simplified left and right expressions.
     *
     * @return The simplified expression
     */
    @Override
    protected Expression getSimplified() {
        Expression simplifiedLeft = this.left.getSimplified();
        Expression simplifiedRight = this.right.getSimplified();
        if (simplifiedLeft instanceof Number
            && simplifiedRight instanceof Number) {
            double sum = ((Number) simplifiedLeft).getValue()
                         + ((Number) simplifiedRight).getValue();
            return new Number(sum);
        }

        if (simplifiedLeft instanceof Number
            && ((Number) simplifiedLeft).getValue() == 0) {
            return simplifiedRight;
        }

        if (simplifiedRight instanceof Number
            && ((Number) simplifiedRight).getValue() == 0) {
            return simplifiedLeft;
        }

        return new Add(simplifiedLeft, simplifiedRight);
    }

    /**
     * Returns the string representation of the addition expression.
     *
     * @return A string
     */
    @Override
    public String toString() {
        return "("
               + this.left.toString()
               + "+"
               + this.right.toString()
               + ")";
    }
}