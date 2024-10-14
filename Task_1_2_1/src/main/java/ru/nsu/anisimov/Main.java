package ru.nsu.anisimov;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph<String> graph = new AdjacencyListGraph<>();

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

        System.out.println(graph);

        System.out.println("Neighbours of " + vA + ": " + graph.getNeighbours(vA));

        TopologicalSorter<String> sorter = new TopologicalSorter<>();
        try {
            List<Vertex<String>> sorted = sorter.topologicalSort(graph);
            System.out.println("Topological Sort:");
            for (Vertex<String> v : sorted) {
                System.out.print(v.getLabel() + " ");
            }
            System.out.println();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        graph.deleteEdge(new Edge<>(vB, vC));
        graph.deleteVertex(vD);

        System.out.println("After deletion vertex D:");
        System.out.println(graph);
    }
}
