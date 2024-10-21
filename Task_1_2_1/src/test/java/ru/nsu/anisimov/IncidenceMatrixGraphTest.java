package ru.nsu.anisimov;

import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IncidenceMatrixGraphTest {
    private IncidenceMatrixGraph<String> graph;
    private File tempFile;

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

        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addEdge(new Edge<>(vertexA, vertexB));

        IncidenceMatrixGraph<String> anotherGraph = new IncidenceMatrixGraph<>();
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

    private File createTempFile(String content) throws IOException {
        tempFile = File.createTempFile("testGraph", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(content);
        }
        return tempFile;
    }

    @Test
    public void testReadFromFile() throws IOException {
        String fileContent = "3\n0 A\n1 B\n2 C\n2\nA B\nB C\n";

        File testFile = createTempFile(fileContent);
        graph.readFromFile(testFile.getAbsolutePath());

        List<Vertex<String>> vertices = graph.getVertices();

        Assertions.assertEquals(3, vertices.size());
        Assertions.assertTrue(vertices.stream().anyMatch(v -> v.getLabel().equals("A")));
        Assertions.assertTrue(vertices.stream().anyMatch(v -> v.getLabel().equals("B")));
        Assertions.assertTrue(vertices.stream().anyMatch(v -> v.getLabel().equals("C")));

        List<Edge<String>> edges = graph.getEdges();

        Assertions.assertEquals(2, edges.size());
        Assertions.assertTrue(edges.contains(new Edge<>(new Vertex<>("A"), new Vertex<>("B"))));
        Assertions.assertTrue(edges.contains(new Edge<>(new Vertex<>("B"), new Vertex<>("C"))));
    }
}