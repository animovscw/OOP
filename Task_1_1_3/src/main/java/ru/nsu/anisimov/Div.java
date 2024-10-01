package ru.nsu.anisimov;

/**
 * Represents a division of two mathematical expressions.
 */
public class Div extends Expression {
    Expression left;
    Expression right;

    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * The method evaluates the division expression,
     * substituting the values of variables from the given assignment string.
     *
     * @param assignation string
     * @return the result
     */
    @Override
    public double evaluate(String assignation) {
        return this.left.evaluate(assignation)
                / this.right.evaluate(assignation);
    }

    /**
     * The method calculates the derivative of the division expression
     * with respect to the given variable.
     *
     * @param variable the variable with which the derivative is taken.
     * @return New expression
     */
    @Override
    public Expression getDerivative(String variable) {
        return new Div(
                new Add(
                        new Mul(
                                this.left.getDerivative(variable),
                                this.right
                        ),
                        new Mul(
                                this.left,
                                this.right.getDerivative(variable)
                        )
                ),
                new Mul(
                        this.right,
                        this.right
                )
        );
    }

    /**
     * Simplifies the division expression.
     * If both numerator and denominator are objects, it computes their division.
     * If the numerator is zero, the result is zero.
     * Otherwise, it returns a new simplified division expression.
     *
     * @return A simplified expression
     */
    @Override
    public Expression getSimplified() {
        Div simpDiv = new Div(
                this.left.getSimplified(),
                this.right.getSimplified()
        );
        if (
                simpDiv.left instanceof Number
                        && simpDiv.right instanceof Number
        ) {
            return new Number(
                    ((Number) simpDiv.left).getValue()
                            / ((Number) simpDiv.right).getValue()
            );
        } else if (
                this.left instanceof Number
                        && ((Number) this.left).getValue() == 0
        ) {
            return new Number(0);
        } else {
            return simpDiv;
        }
    }

    /**
     * Returns a string representation of the division expression.
     *
     * @return A string representing the division expression
     */
    @Override
    public String toString() {
        return "("
                + this.left.toString()
                + "/"
                + this.right.toString()
                + ")";
    }
}
