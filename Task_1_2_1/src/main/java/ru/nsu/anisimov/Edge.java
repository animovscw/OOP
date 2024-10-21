package ru.nsu.anisimov;

import java.util.Objects;

/**
 * Represents an edge in a graph, connecting a source vertex to a destination vertex.
 *
 * @param <T> the type of elements stored in the vertices of the graph
 */
public class Edge<T> {
    private final Vertex<T> source;
    private final Vertex<T> destination;

    /**
     * Constructs a new edge with the specified source and destination vertices.
     *
     * @param source the source vertex of the edge
     * @param destination the destination vertex of the edge
     */
    public Edge(Vertex<T> source, Vertex<T> destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Returns the source vertex of the edge.
     *
     * @return the source vertex
     */
    public Vertex<T> getSource() {
        return source;
    }

    /**
     * Returns the destination vertex of the edge.
     *
     * @return the destination vertex
     */
    public Vertex<T> getDestination() {
        return destination;
    }

    /**
     * Compares this edge to another object for equality.
     *
     * @param o the object to compare to
     * @return true if the edges are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge<?> edge = (Edge<?>) o;
        return Objects.equals(source, edge.source)
               && Objects.equals(destination, edge.destination);
    }

    /**
     * Returns a hash code value for the edge.
     *
     * @return a hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }

    /**
     * Returns a string representation of the edge.
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return "Edge { " + "from " + source.getLabel() + ", to " + destination.getLabel() + " }";
    }
}