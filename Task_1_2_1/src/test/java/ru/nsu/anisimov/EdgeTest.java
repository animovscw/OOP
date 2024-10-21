package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EdgeTest {
    @Test
    public void testEdgeCreation() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        Edge<String> edge = new Edge<>(vertexA, vertexB);

        Assertions.assertEquals(vertexA, edge.getSource());
        Assertions.assertEquals(vertexB, edge.getDestination());
    }

    @Test
    public void testEdgeEquality() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        Edge<String> edge1 = new Edge<>(vertexA, vertexB);
        Edge<String> edge2 = new Edge<>(vertexA, vertexB);

        Assertions.assertEquals(edge1, edge2);
    }

    @Test
    public void testEdgeHashCode() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        Edge<String> edge = new Edge<>(vertexA, vertexB);

        Assertions.assertNotNull(edge.hashCode());
    }

    @Test
    public void testToString() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        Edge<String> edge = new Edge<>(vertexA, vertexB);

        String result = edge.toString();
        Assertions.assertTrue(result.contains("from A"));
        Assertions.assertTrue(result.contains("to B"));
    }
}