package ru.nsu.anisimov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AdjacencyListGraph<T> implements Graph<T> {
    private final Map<Vertex<T>, List<Vertex<T>>> adjList;
    private final Set<Vertex<T>> vertices;

    public AdjacencyListGraph() {
        adjList = new HashMap<>();
        vertices = new HashSet<>();
    }

    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.add(vertex);
        adjList.put(vertex, new ArrayList<>());
    }

    @Override
    public void addEdge(Edge<T> edge) {
        Vertex<T> src = edge.getSource();
        Vertex<T> dest = edge.getDestination();
        adjList.get(src).add(dest);
    }

    @Override
    public void deleteVertex(Vertex<T> vertex) {
        vertices.remove(vertex);
        adjList.remove(vertex);
        for (List<Vertex<T>> neighbours : adjList.values()) {
            neighbours.remove(vertex);
        }
    }

    @Override
    public void deleteEdge(Edge<T> edge) {
        Vertex<T> src = edge.getSource();
        Vertex<T> dest = edge.getDestination();
        adjList.get(src).remove(dest);
    }

    @Override
    public List<Vertex<T>> getNeighbours(Vertex<T> vertex) {
        if (!adjList.containsKey(vertex)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(adjList.get(vertex));
    }

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            line = br.readLine();
            int numVertices = Integer.parseInt(line.trim());
            for (int i = 0; i < numVertices; ++i) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] parts = line.trim().split("\\s+", 2);
                T label = (T) parts[1];
                addVertex(new Vertex<>(label));
            }
            line = br.readLine();
            int numEdges = Integer.parseInt(line.trim());
            for (int i = 0; i < numEdges; ++i) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] parts = line.trim().split("\\s+", 2);
                T srcLabel = (T) parts[0];
                T destLabel = (T) parts[1];
                Vertex<T> src = findVertexByLabel(srcLabel);
                Vertex<T> dest = findVertexByLabel(destLabel);
                if (src != null && dest != null) {
                    addEdge(new Edge<>(src, dest));
                }
            }
        } catch (IOException e) {
            throw new GraphFileReadException("Failed to read the graph from file: " + filename, e);
        }
    }


    private Vertex<T> findVertexByLabel(T label) {
        for (Vertex<T> v : vertices) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public List<Vertex<T>> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdjacencyListGraph<?> that = (AdjacencyListGraph<?>) o;

        if (!adjList.equals(that.adjList)) {
            return false;
        }
        return vertices.equals(that.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjList, vertices);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyListGraph:\n");
        sb.append("Vertices:\n");
        for (Vertex<T> v : vertices) {
            sb.append(v).append("\n");
        }
        sb.append("Adjacency List:\n");
        for (Map.Entry<Vertex<T>, List<Vertex<T>>> entry : adjList.entrySet()) {
            sb.append(entry.getKey().getLabel()).append(": ");
            for (Vertex<T> neighbor : entry.getValue()) {
                sb.append(neighbor.getLabel()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}