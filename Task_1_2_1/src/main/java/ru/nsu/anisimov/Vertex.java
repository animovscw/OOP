package ru.nsu.anisimov;

import java.util.Objects;

/**
 * Represents a vertex in a graph with a label of generic type T.
 *
 * @param <T> the type of the label associated with the vertex
 */
public class Vertex<T> {
    private final T label;

    /**
     * Constructs a new Vertex with the specified label.
     *
     * @param label the label of the vertex
     */
    public Vertex(T label) {
        this.label = label;
    }

    /**
     * Returns the label of this vertex.
     *
     * @return the label
     */
    public T getLabel() {
        return label;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Vertex<?> vertex = (Vertex<?>) o;
        return Objects.equals(label, vertex.label);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    /**
     * Returns a string representation of the vertex.
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return "Vertex { " + label + " }";
    }
}