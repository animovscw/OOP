package ru.nsu.anisimov;

import java.util.List;

/**
 * Main class to demonstrate the functionality of the Graph implementation.
 */
public class Main {
    public static void main(String[] args) {
        Graph<String> graph = new AdjacencyListGraph<>();

        Vertex<String> vertexA = new Vertex<>("vertexA");
        Vertex<String> vertexB = new Vertex<>("vertexB");
        Vertex<String> vertexC = new Vertex<>("vertexC");
        Vertex<String> vertexD = new Vertex<>("vertexD");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addVertex(vertexD);

        graph.addEdge(new Edge<>(vertexA, vertexB));
        graph.addEdge(new Edge<>(vertexA, vertexC));
        graph.addEdge(new Edge<>(vertexB, vertexC));
        graph.addEdge(new Edge<>(vertexC, vertexD));

        System.out.println(graph);

        System.out.println("Neighbours of " + vertexA + ": " + graph.getNeighbours(vertexA));

        try {
            List<Vertex<String>> sorted = TopologicalSorter.topologicalSort(graph);
            System.out.println("Topological Sort:");
            for (Vertex<String> v : sorted) {
                System.out.print(v.getLabel() + " ");
            }
            System.out.println();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        graph.deleteEdge(new Edge<>(vertexB, vertexC));
        graph.deleteVertex(vertexD);

        System.out.println("After deletion vertex vertexD:");
        System.out.println(graph);
    }
}
