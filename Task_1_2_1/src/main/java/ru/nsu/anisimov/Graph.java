package ru.nsu.anisimov;

import java.util.List;

/**
 * Defines the operations for a graph data structure.
 *
 * @param <T> the type of the vertices in the graph
 */
public interface Graph<T> {
    void addVertex(Vertex<T> vertex);

    void addEdge(Edge<T> edge);

    void deleteVertex(Vertex<T> vertex);

    void deleteEdge(Edge<T> edge);

    List<Vertex<T>> getNeighbours(Vertex<T> vertex);

    void readFromFile(String filename);

    List<Vertex<T>> getVertices();
}