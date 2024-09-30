package ru.nsu.anisimov;

public abstract class Expression {

    public abstract double evaluate(String assignation);

    public abstract Expression getDerivative(String variable);

    public abstract Expression getSimplified();

    public void print() {
        System.out.println(this);
    }
}
