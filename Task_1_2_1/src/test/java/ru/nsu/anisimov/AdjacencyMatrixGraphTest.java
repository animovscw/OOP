package ru.nsu.anisimov;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdjacencyMatrixGraphTest {
    private AdjacencyMatrixGraph<String> graph;

    @BeforeEach
    public void setUp() {
        graph = new AdjacencyMatrixGraph<>();
    }

    @Test
    public void testAddVertex() {
        Vertex<String> vertexA = new Vertex<>("A");
        graph.addVertex(vertexA);
        Assertions.assertTrue(graph.getVertices().contains(vertexA));
    }

    @Test
    public void testAddEdge() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addEdge(new Edge<>(vertexA, vertexB));

        List<Vertex<String>> neighbours = graph.getNeighbours(vertexA);
        Assertions.assertTrue(neighbours.contains(vertexB));
    }

    @Test
    public void testDeleteVertex() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.deleteVertex(vertexA);
        Assertions.assertFalse(graph.getVertices().contains(vertexA));
    }

    @Test
    public void testDeleteEdge() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addEdge(new Edge<>(vertexA, vertexB));
        graph.deleteEdge(new Edge<>(vertexA, vertexB));

        List<Vertex<String>> neighbours = graph.getNeighbours(vertexA);
        Assertions.assertFalse(neighbours.contains(vertexB));
    }
}