package ru.nsu.anisimov;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph<String> graph = new AdjacencyListGraph<>();

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

        System.out.println(graph);

        System.out.println("Neighbours of " + A + ": " + graph.getNeighbours(A));

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

        graph.deleteEdge(new Edge<>(B, C));
        graph.deleteVertex(D);

        System.out.println("After deletion vertex D:");
        System.out.println(graph);
    }
}
