package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests to check the work of the class.
 */
public class TopologicalSorterTest {

    private Graph<String> graph;

    @BeforeEach
    public void setUp() {
        graph = new AdjacencyListGraph<>();
    }

    @Test
    public void testSimpleDag() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        Vertex<String> vertexC = new Vertex<>("C");
        Vertex<String> vertexD = new Vertex<>("D");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexD);
        graph.addEdge(new Edge<>(vertexA, vertexB));
        graph.addEdge(new Edge<>(vertexA, vertexC));
        graph.addEdge(new Edge<>(vertexB, vertexC));
        graph.addEdge(new Edge<>(vertexC, vertexD));

        List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);

        Assertions.assertEquals(4, sorted.size());

        Map<String, Integer> position = new HashMap<>();
        for (int i = 0; i < sorted.size(); i++) {
            position.put(sorted.get(i).getLabel(), i);
        }

        Assertions.assertAll("Check vertex order",
                () -> Assertions.assertTrue(position.get("A") < position.get("B"), "A before B"),
                () -> Assertions.assertTrue(position.get("A") < position.get("C"), "A before C"),
                () -> Assertions.assertTrue(position.get("B") < position.get("C"), "B before C"),
                () -> Assertions.assertTrue(position.get("C") < position.get("D"), "C before D")
        );
    }

    @Test
    public void testMultipleOrderings() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        Vertex<String> vertexC = new Vertex<>("C");
        Vertex<String> vertexD = new Vertex<>("D");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexD);

        graph.addEdge(new Edge<>(vertexA, vertexB));
        graph.addEdge(new Edge<>(vertexA, vertexC));
        graph.addEdge(new Edge<>(vertexB, vertexD));
        graph.addEdge(new Edge<>(vertexC, vertexD));

        List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);

        Assertions.assertEquals(4, sorted.size());
        Assertions.assertEquals("A", sorted.get(0).getLabel());
        Assertions.assertEquals("D", sorted.get(3).getLabel());

        int indexB = sorted.indexOf(vertexB);
        int indexC = sorted.indexOf(vertexC);
        int indexD = sorted.indexOf(vertexD);

        Assertions.assertTrue(indexB < indexD);
        Assertions.assertTrue(indexC < indexD);
    }

    @Test
    public void testEmptyGraph() {
        List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);

        Assertions.assertTrue(sorted.isEmpty());
    }

    @Test
    public void testSingleVertex() {
        Vertex<String> vertexA = new Vertex<>("A");
        graph.addVertex(vertexA);
        List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);

        Assertions.assertEquals(1, sorted.size());
        Assertions.assertEquals("A", sorted.get(0).getLabel());
    }

    @Test
    public void testDisconnectedGraph() {
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");
        Vertex<String> vertexC = new Vertex<>("C");
        Vertex<String> vertexD = new Vertex<>("D");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexD);

        graph.addEdge(new Edge<>(vertexA, vertexB));
        graph.addEdge(new Edge<>(vertexC, vertexD));

        List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);
        Assertions.assertEquals(4, sorted.size());
        Map<String, Integer> position = new HashMap<>();
        for (int i = 0; i < sorted.size(); ++i) {
            position.put(sorted.get(i).getLabel(), i);
        }

        Assertions.assertAll(
                () -> Assertions.assertTrue(position.get("A") < position.get("B"), "A before B"),
                () -> Assertions.assertTrue(position.get("C") < position.get("D"), "C before D")
        );
    }
}