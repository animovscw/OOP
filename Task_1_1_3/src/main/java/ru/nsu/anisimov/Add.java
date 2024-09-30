package ru.nsu.anisimov;

/**
 * Class represents the sum of two mathematical expressions.
 */
public class Add extends Expression {
    Expression left;
    Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * The method calculates the result of adding two expressions.
     *
     * @param assignation the string
     * @return The result of evaluating the sum
     */
    @Override
    public double evaluate(String assignation) {
        return this.left.evaluate(assignation)
                + this.right.evaluate(assignation);
    }

    /**
     * The method returns the derivative of the sum with respect to the specified variable.
     *
     * @param variable the variable
     * @return The derivative of the sum as a new Add expression
     */
    @Override
    public Expression getDerivative(String variable) {
        return new Add(
                this.left.getDerivative(variable),
                this.right.getDerivative(variable)
        );
    }

    /**
     * Simplifies the expression. If both sides are constants, returns a new
     * Number expression representing their sum. Otherwise, returns a new Sum of
     * the simplified left and right expressions.
     *
     * @return The simplified expression
     */
    @Override
    public Expression getSimplified() {
        Add simpAdd = new Add(
                this.left.getSimplified(),
                this.right.getSimplified()
        );
        if (simpAdd.left instanceof Number
                && simpAdd.right instanceof Number
        ) {
            return new Number(
                    ((Number) simpAdd.left).value +
                            ((Number) simpAdd.right).value

            );
        } else {
            return simpAdd;
        }
    }

    /**
     * The method returns a string representation of the expression.
     *
     * @return A string representation of the add expression
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
