package ru.nsu.anisimov;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph<String> graph = new AdjacencyListGraph<>();

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

        System.out.println(graph);

        System.out.println("Neighbours of " + VA + ": " + graph.getNeighbours(VA));

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

        graph.deleteEdge(new Edge<>(VB, VC));
        graph.deleteVertex(VD);

        System.out.println("After deletion vertex D:");
        System.out.println(graph);
    }
}
