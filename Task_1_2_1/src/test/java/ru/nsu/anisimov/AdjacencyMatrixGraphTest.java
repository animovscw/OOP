package ru.nsu.anisimov;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests to check the work of the class.
 */
public class AdjacencyMatrixGraphTest {
    private AdjacencyMatrixGraph<String> graph;
    private File tempFile;

    @BeforeEach
    public void setUp() {
        graph = new AdjacencyMatrixGraph<>();
    }

    private File createTempFile(String content) throws IOException {
        tempFile = File.createTempFile("testGraph", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(content);
        }
        return tempFile;
    }

    @Test
    public void testAddVertex() {
        Vertex<String> vertexA = new Vertex<>("A");
        graph.addVertex(vertexA);

        Assertions.assertEquals(1, graph.getVertices().size());
        Assertions.assertTrue(graph.getVertices().contains(vertexA));
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

        Assertions.assertEquals(1, neighbours.size());
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
        Edge<String> edge = new Edge<>(vertexA, vertexB);
        graph.addEdge(edge);

        graph.deleteEdge(edge);

        Assertions.assertEquals(0, graph.getNeighbours(vertexA).size());
    }

    @Test
    public void testGetNeighbours() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        Edge<String> edge = new Edge<>(vertexA, vertexB);
        graph.addEdge(edge);

        List<Vertex<String>> neighbours = graph.getNeighbours(vertexA);

        Assertions.assertEquals(1, neighbours.size());
        Assertions.assertTrue(neighbours.contains(vertexB));
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

        List<Vertex<String>> neighboursA = graph.getNeighbours(vertices.get(0));

        Assertions.assertEquals(1, neighboursA.size());
        Assertions.assertTrue(neighboursA.contains(vertices.get(1)));

        List<Vertex<String>> neighboursB = graph.getNeighbours(vertices.get(1));

        Assertions.assertEquals(1, neighboursB.size());
        Assertions.assertTrue(neighboursB.contains(vertices.get(2)));
    }

    @Test
    public void testFindVertexByLabel() {
        Vertex<String> vertexA = new Vertex<>("A");
        graph.addVertex(vertexA);

        Assertions.assertEquals(vertexA, graph.findVertexByLabel("A"));
        Assertions.assertNull(graph.findVertexByLabel("B"));
    }

    @Test
    public void testExpandMatrix() {
        for (int i = 0; i < 10; ++i) {
            graph.addVertex(new Vertex<>("Vertex" + i));
        }

        Assertions.assertEquals(10, graph.getVertices().size());
    }

    @Test
    public void testEqualsAndHashCode() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        Edge<String> edge = new Edge<>(vertexA, vertexB);
        graph.addEdge(edge);

        AdjacencyMatrixGraph<String> anotherGraph = new AdjacencyMatrixGraph<>();
        anotherGraph.addVertex(vertexA);
        anotherGraph.addVertex(vertexB);
        anotherGraph.addEdge(edge);

        Assertions.assertEquals(graph, anotherGraph);
        Assertions.assertEquals(graph.hashCode(), anotherGraph.hashCode());
    }

    @Test
    public void testToString() {
        Vertex<String> vertexA = new Vertex<>("A");
        graph.addVertex(vertexA);
        String result = graph.toString();

        Assertions.assertTrue(result.contains("Vertices:"));
        Assertions.assertTrue(result.contains("A"));
    }

    @Test
    public void testGetEdges() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        Edge<String> edge = new Edge<>(vertexA, vertexB);
        graph.addEdge(edge);

        Collection<Edge<String>> edges = graph.getEdges();

        Assertions.assertEquals(1, edges.size());
        Assertions.assertTrue(edges.contains(edge));
    }
}