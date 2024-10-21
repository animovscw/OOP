package ru.nsu.anisimov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

public class TopologicalSorterTest {

    private Graph<String> graph;

    @BeforeEach
    public void setUp() {
        graph = new AdjacencyListGraph<>();
    }

    @Test
    public void testSimpleDag() {
        Vertex<String> VA = new Vertex<>("A");
        Vertex<String> VB = new Vertex<>("B");
        Vertex<String> VC = new Vertex<>("C");
        Vertex<String> VD = new Vertex<>("D");
        graph.addVertex(VA);
        graph.addVertex(VB);
        graph.addVertex(VC);
        graph.addVertex(VD);
        graph.addEdge(new Edge<>(VA, VB));
        graph.addEdge(new Edge<>(VA, VC));
        graph.addEdge(new Edge<>(VB, VC));
        graph.addEdge(new Edge<>(VC, VD));

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
        Vertex<String> VA = new Vertex<>("A");
        Vertex<String> VB = new Vertex<>("B");
        Vertex<String> VC = new Vertex<>("C");
        Vertex<String> VD = new Vertex<>("D");

        graph.addVertex(VA);
        graph.addVertex(VB);
        graph.addVertex(VC);
        graph.addVertex(VD);

        graph.addEdge(new Edge<>(VA, VB));
        graph.addEdge(new Edge<>(VA, VC));
        graph.addEdge(new Edge<>(VB, VD));
        graph.addEdge(new Edge<>(VC, VD));

        List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);

        Assertions.assertEquals(4, sorted.size());
        Assertions.assertEquals("A", sorted.get(0).getLabel());
        Assertions.assertEquals("D", sorted.get(3).getLabel());

        int indexB = sorted.indexOf(VB);
        int indexC = sorted.indexOf(VC);
        int indexD = sorted.indexOf(VD);

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
        Vertex<String> VA = new Vertex<>("A");
        graph.addVertex(VA);
        List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);

        Assertions.assertEquals(1, sorted.size());
        Assertions.assertEquals("A", sorted.get(0).getLabel());
    }

    @Test
    public void testDisconnectedGraph() {
        Vertex<String> VA = new Vertex<>("A");
        Vertex<String> VB = new Vertex<>("B");
        Vertex<String> VC = new Vertex<>("C");
        Vertex<String> VD = new Vertex<>("D");

        graph.addVertex(VA);
        graph.addVertex(VB);
        graph.addVertex(VC);
        graph.addVertex(VD);

        graph.addEdge(new Edge<>(VA, VB));
        graph.addEdge(new Edge<>(VC, VD));

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