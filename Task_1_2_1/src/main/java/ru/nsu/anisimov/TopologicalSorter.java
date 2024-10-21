package ru.nsu.anisimov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * A utility class for performing topological sorting on a directed acyclic graph (DAG).
 *
 * @param <T> the type of elements stored in the vertices of the graph
 */
public class TopologicalSorter<T> {

    /**
     * Performs a topological sort on the given directed acyclic graph (DAG).
     *
     * @param graph the directed acyclic graph to be sorted
     * @return a list of vertices in topologically sorted order
     */
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