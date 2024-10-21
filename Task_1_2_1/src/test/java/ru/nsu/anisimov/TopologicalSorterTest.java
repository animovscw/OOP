package ru.nsu.anisimov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import java.util.*;

public class TopologicalSorterTest {

    private Graph<String> graph;

    @BeforeEach
    public void setUp() {
        graph = new AdjacencyListGraph<>();
    }

    @Test
    public void testSimpleDAG() {
        Vertex<String> vA = new Vertex<>("A");
        Vertex<String> vB = new Vertex<>("B");
        Vertex<String> vC = new Vertex<>("C");
        Vertex<String> vD = new Vertex<>("D");
        graph.addVertex(vA);
        graph.addVertex(vB);
        graph.addVertex(vC);
        graph.addVertex(vD);
        graph.addEdge(new Edge<>(vA, vB));
        graph.addEdge(new Edge<>(vA, vC));
        graph.addEdge(new Edge<>(vB, vC));
        graph.addEdge(new Edge<>(vC, vD));

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
        Vertex<String> vA = new Vertex<>("A");
        Vertex<String> vB = new Vertex<>("B");
        Vertex<String> vC = new Vertex<>("C");
        Vertex<String> vD = new Vertex<>("D");

        graph.addVertex(vA);
        graph.addVertex(vB);
        graph.addVertex(vC);
        graph.addVertex(vD);

        graph.addEdge(new Edge<>(vA, vB));
        graph.addEdge(new Edge<>(vA, vC));
        graph.addEdge(new Edge<>(vB, vD));
        graph.addEdge(new Edge<>(vC, vD));

        List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);

        Assertions.assertEquals(4, sorted.size());
        Assertions.assertEquals("A", sorted.get(0).getLabel());
        Assertions.assertEquals("D", sorted.get(3).getLabel());

        int indexB = sorted.indexOf(vB);
        int indexC = sorted.indexOf(vC);
        int indexD = sorted.indexOf(vD);

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
        Vertex<String> vA = new Vertex<>("A");
        graph.addVertex(vA);
        List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);

        Assertions.assertEquals(1, sorted.size());
        Assertions.assertEquals("A", sorted.get(0).getLabel());
    }

    @Test
    public void testDisconnectedGraph() {
        Vertex<String> vA = new Vertex<>("A");
        Vertex<String> vB = new Vertex<>("B");
        Vertex<String> vC = new Vertex<>("C");
        Vertex<String> vD = new Vertex<>("D");

        graph.addVertex(vA);
        graph.addVertex(vB);
        graph.addVertex(vC);
        graph.addVertex(vD);

        graph.addEdge(new Edge<>(vA, vB));
        graph.addEdge(new Edge<>(vC, vD));

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