package ru.nsu.anisimov;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IncidenceMatrixGraphTest {
    private IncidenceMatrixGraph<String> graph;

    @BeforeEach
    public void setUp() {
        graph = new IncidenceMatrixGraph<>();
    }

    @Test
    public void testAddVertex() {
        Vertex<String> vertexA = new Vertex<>("A");
        graph.addVertex(vertexA);
        Assertions.assertTrue(graph.getVertices().contains(vertexA));
    }

    @Test
    public void testExpandVertices() {
        for (int i = 0; i < 15; ++i) {
            graph.addVertex(new Vertex<>("V" + i));
        }
        Assertions.assertEquals(15, graph.getVertices().size());
    }

    @Test
    public void testAddEdge() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        Edge<String> edge = new Edge<>(vertexA, vertexB);
        graph.addEdge(edge);

        List<Vertex<String>> neighbours = graph.getNeighbours(vertexA);
        Assertions.assertTrue(neighbours.contains(vertexB));
    }

    @Test
    public void testExpandEdges() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        for (int i = 0; i < 15; ++i) {
            graph.addEdge(new Edge<>(vertexA, vertexB));
        }
        List<Vertex<String>> neighbours = graph.getNeighbours(vertexA);
        Assertions.assertEquals(15, graph.getEdges().size());
    }

    @Test
    public void testDeleteVertex() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.deleteVertex(vertexA);
        Assertions.assertFalse(graph.getVertices().contains(vertexA));
        Assertions.assertEquals(1, graph.getVertices().size());
    }

    @Test
    public void testDeleteEdge() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        Edge<String> edge = new Edge<>(vertexA, vertexB);
        graph.addEdge(edge);
        graph.deleteEdge(edge);

        List<Vertex<String>> neighbours = graph.getNeighbours(vertexA);
        Assertions.assertFalse(neighbours.contains(vertexB));
    }

    @Test
    public void testGetNeighbours() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addEdge(new Edge<>(vertexA, vertexB));

        List<Vertex<String>> neighbours = graph.getNeighbours(vertexA);
        Assertions.assertEquals(1, neighbours.size());
        Assertions.assertEquals(vertexB, neighbours.get(0));
    }

    @Test
    public void testFindVertexByLabel() {
        Vertex<String> vertexA = new Vertex<>("A");
        graph.addVertex(vertexA);
        Vertex<String> result = graph.findVertexByLabel("A");
        Assertions.assertEquals(vertexA, result);
    }

    @Test
    public void testGraphEquality() {
        IncidenceMatrixGraph<String> anotherGraph = new IncidenceMatrixGraph<>();
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addEdge(new Edge<>(vertexA, vertexB));

        anotherGraph.addVertex(vertexA);
        anotherGraph.addVertex(vertexB);
        anotherGraph.addEdge(new Edge<>(vertexA, vertexB));

        Assertions.assertEquals(graph, anotherGraph);
    }

    @Test
    public void testHashCode() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addEdge(new Edge<>(vertexA, vertexB));

        int hashCode = graph.hashCode();
        Assertions.assertNotNull(hashCode);
    }

    @Test
    public void testToString() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addEdge(new Edge<>(vertexA, vertexB));

        String result = graph.toString();
        Assertions.assertTrue(result.contains("Vertices:"));
        Assertions.assertTrue(result.contains("Edges:"));
    }
}