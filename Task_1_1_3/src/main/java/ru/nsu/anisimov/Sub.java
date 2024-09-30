package ru.nsu.anisimov;

/**
 * Class represents the subtraction of two mathematical expressions.
 */
public class Sub extends Expression {
    Expression left;
    Expression right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * The method evaluates the subtraction expression by computing the difference between
     * the evaluations of the left and right expressions.
     *
     * @param assignation AN assignation containing variable assignments
     * @return The result of subtraction
     */
    @Override
    public double evaluate(String assignation) {
        return this.left.evaluate(assignation)
                - this.right.evaluate(assignation);
    }

    /**
     * Computes the derivative of the subtraction expression with respect to a specified variable.
     *
     * @param variable the variable
     * @return A new expression representing the derivative
     */
    @Override
    public Expression getDerivative(String variable) {
        return new Sub(
                this.left.getDerivative(variable),
                this.right.getDerivative(variable)
        );
    }

    /**
     * Simplifies the subtraction expression.
     * If both subexpressions are numbers, subtracts them and returns the new number.
     * If the left and right subexpressions are identical, returns the number 0.
     * Otherwise, returns a new expression consisting of simplified subexpressions.
     *
     * @return A simplified instance
     */
    @Override
    public Expression getSimplified() {
        Sub simpSub = new Sub(
                this.left.getSimplified(),
                this.right.getSimplified()
        );
        if (simpSub.left instanceof Number
                && simpSub.right instanceof Number) {
            return new Number(
                    ((Number) simpSub.left).value
                            - ((Number) simpSub.right).value
            );
        } else if (simpSub.left.toString().equals(
                simpSub.right.toString()
        )) {
            return new Number(0);
        } else {
            return simpSub;
        }
    }

    /**
     * Returns the string representation of the subtraction expression.
     *
     * @return A string representation of the sub expression
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
