package ru.nsu.anisimov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public void testMainExecution() {
        try {
            Main.main(new String[]{});
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void testMainGraphOperations() {
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>();
        Vertex<String> vertexA = new Vertex<>("A");
        Vertex<String> vertexB = new Vertex<>("B");

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);

        Assertions.assertEquals(2, graph.getVertices().size());
        Assertions.assertEquals(0, graph.getEdges().size());
    }
}
