package ru.nsu.anisimov;

/**
 * The class represents the operation of multiplying two mathematical expressions.
 */
public class Mul extends Expression {
    Expression left;
    Expression right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * The method calculates the value of a multiplication expression by substituting
     * the values of the variables from the assignment string.
     *
     * @param assignationString string
     * @return The result of multiplication
     */
    @Override
    public double evaluate(String assignationString) {
        return this.left.evaluate(assignationString)
                * this.right.evaluate(assignationString);
    }

    /**
     * The method calculates the derivative of a multiplication expression
     * with respect to a given variable.
     *
     * @param variable the variable with which the derivative is taken.
     * @return New expression
     */
    @Override
    public Expression getDerivative(String variable) {
        return new Add(
                new Mul(
                        this.left.getDerivative(variable),
                        this.right
                ),
                new Mul(
                        this.left,
                        this.right.getDerivative(variable)
                )
        );
    }

    /**
     * Simplifies multiplication expressions.
     * If both subexpressions are Number, evaluates their product and returns the new Number.
     * If one of the subexpressions is Number(0), returns Number(0).
     * If one of the subexpressions is Number(1), returns the other subexpression.
     * Otherwise, returns a new Mul expression with simplified subexpressions.
     *
     * @return Simplified expression
     */
    @Override
    public Expression getSimplified() {
        Mul simpMul = new Mul(
                this.left.getSimplified(),
                this.right.getSimplified()
        );
        if (
                simpMul.left instanceof Number
                        && simpMul.right instanceof Number
        ) {
            return new Number(
                    ((Number) simpMul.left).value
                            * ((Number) simpMul.right).value
            );
        } else if (
                simpMul.left instanceof Number
                        && ((Number) simpMul.left).value == 0
                        || simpMul.right instanceof Number
                        && ((Number) simpMul.right).value == 0
        ) {
            return new Number(0);
        } else if (
                simpMul.left instanceof Number
                        && ((Number) simpMul.left).value == 1
        ) {
            return simpMul.right;
        } else if (
                simpMul.right instanceof Number
                        && ((Number) simpMul.right).value == 1
        ) {
            return simpMul.left;
        } else {
            return simpMul;
        }
    }

    /**
     * Returns a string representation of a multiplication expression.
     *
     * @return String
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
