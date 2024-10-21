package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopologicalSorterTest {

    private Graph<String> graph;

    @BeforeEach
    public void setUp() {
        graph = new AdjacencyListGraph<>();
    }

    @Test
    public void testSimpleDag() {
        Vertex<String> A = new Vertex<>("A");
        Vertex<String> B = new Vertex<>("B");
        Vertex<String> C = new Vertex<>("C");
        Vertex<String> D = new Vertex<>("D");
        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);
        graph.addEdge(new Edge<>(A, B));
        graph.addEdge(new Edge<>(A, C));
        graph.addEdge(new Edge<>(B, C));
        graph.addEdge(new Edge<>(C, D));

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
        Vertex<String> A = new Vertex<>("A");
        Vertex<String> B = new Vertex<>("B");
        Vertex<String> C = new Vertex<>("C");
        Vertex<String> D = new Vertex<>("D");

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);

        graph.addEdge(new Edge<>(A, B));
        graph.addEdge(new Edge<>(A, C));
        graph.addEdge(new Edge<>(B, D));
        graph.addEdge(new Edge<>(C, D));

        List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);

        Assertions.assertEquals(4, sorted.size());
        Assertions.assertEquals("A", sorted.get(0).getLabel());
        Assertions.assertEquals("D", sorted.get(3).getLabel());

        int indexB = sorted.indexOf(B);
        int indexC = sorted.indexOf(C);
        int indexD = sorted.indexOf(D);

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
        Vertex<String> A = new Vertex<>("A");
        graph.addVertex(A);
        List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);

        Assertions.assertEquals(1, sorted.size());
        Assertions.assertEquals("A", sorted.get(0).getLabel());
    }

    @Test
    public void testDisconnectedGraph() {
        Vertex<String> A = new Vertex<>("A");
        Vertex<String> B = new Vertex<>("B");
        Vertex<String> C = new Vertex<>("C");
        Vertex<String> D = new Vertex<>("D");

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);

        graph.addEdge(new Edge<>(A, B));
        graph.addEdge(new Edge<>(C, D));

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