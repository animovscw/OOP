package ru.nsu.anisimov;

/**
 * Abstract class for all mathematical expressions.
 */
public abstract class Expression {

    public abstract double evaluate(String assignation);

    public abstract Expression getDerivative(String variable);

    public abstract Expression getSimplified();

    public void print() {
        System.out.println(this);
    }
}
