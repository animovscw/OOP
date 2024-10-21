package ru.nsu.anisimov;

import java.util.*;

public class TopologicalSorter<T> {
    public static <T> List<Vertex<T>> topologicalSort(Graph<T> graph) {
        List<Vertex<T>> sorted = new ArrayList<>();
        Map<Vertex<T>, Integer> inDegree = new HashMap<>();

        for (Vertex<T> vertex : graph.getVertices()) {
            inDegree.put(vertex, 0);
        }

        for (Vertex<T> vertex : graph.getVertices()) {
            for (Vertex<T> neighbour : graph.getNeighbours(vertex)) {
                inDegree.put(neighbour, inDegree.get(neighbour) + 1);
            }
        }

        Queue<Vertex<T>> queue = new LinkedList<>();
        for (Map.Entry<Vertex<T>, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            Vertex<T> vertex = queue.poll();
            sorted.add(vertex);
            for (Vertex<T> neighbour : graph.getNeighbours(vertex)) {
                inDegree.put(neighbour, inDegree.get(neighbour) - 1);
                if (inDegree.get(neighbour) == 0) {
                    queue.offer(neighbour);
                }
            }
        }
        return sorted;
    }
}