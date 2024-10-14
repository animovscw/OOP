package ru.nsu.anisimov;

import java.util.Objects;

public class Edge<T> {
    private final Vertex<T> source;
    private final Vertex<T> destination;

    public Edge(Vertex<T> source, Vertex<T> destination) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source and Destination vertices cannot be null.");
        }
        this.source = source;
        this.destination = destination;
    }

    public Vertex<T> getSource() {
        return source;
    }

    public Vertex<T> getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge<?> edge = (Edge<?>) o;
        return Objects.equals(source, edge.source) && Objects.equals(destination, edge.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }

    @Override
    public String toString() {
        return "Edge { " + "from " + source.getLabel() + ", to " + destination.getLabel() + " }";
    }
}


