package ru.nsu.anisimov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of a graph using an adjacency matrix.
 *
 * @param <T> the type of elements stored in the vertices of the graph
 */
public class AdjacencyMatrixGraph<T> implements Graph<T> {
    private final List<Vertex<T>> verticesList;
    private final Map<Vertex<T>, Integer> vertexIndexMap;
    private boolean[][] matrix;
    private int capacity;

    /**
     * Constructs a new, empty adjacency matrix graph with an initial capacity.
     */
    public AdjacencyMatrixGraph() {
        verticesList = new ArrayList<>();
        vertexIndexMap = new HashMap<>();
        capacity = 10;
        matrix = new boolean[capacity][capacity];
    }

    /**
     * Adds a vertex to the graph.
     * If the current capacity is reached, the adjacency matrix is expanded.
     *
     * @param vertex the vertex to be added
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        if (verticesList.size() >= capacity) {
            expandMatrix();
        }
        verticesList.add(vertex);
        vertexIndexMap.put(vertex, verticesList.size() - 1);
    }

    /**
     * Expands the adjacency matrix to accommodate more vertices.
     * The new capacity will be double the current capacity.
     */
    private void expandMatrix() {
        capacity *= 2;
        boolean[][] newMatrix = new boolean[capacity][capacity];
        for (int i = 0; i < matrix.length; ++i) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
        }
        matrix = newMatrix;
    }

    /**
     * Adds an edge between two vertices in the graph.
     *
     * @param edge the edge to be added
     */
    @Override
    public void addEdge(Edge<T> edge) {
        Vertex<T> src = edge.getSource();
        Vertex<T> dest = edge.getDestination();
        int srcIndex = vertexIndexMap.get(src);
        int destIndex = vertexIndexMap.get(dest);
        matrix[srcIndex][destIndex] = true;
    }

    /**
     * Deletes a vertex from the graph and removes all associated edges.
     *
     * @param vertex the vertex to be deleted
     */
    @Override
    public void deleteVertex(Vertex<T> vertex) {
        int index = vertexIndexMap.get(vertex);
        verticesList.remove(index);
        vertexIndexMap.remove(vertex);
        for (int i = index; i < verticesList.size(); ++i) {
            vertexIndexMap.put(verticesList.get(i), i);
        }
        for (int i = 0; i < verticesList.size() + 1; ++i) {
            for (int j = index; j < verticesList.size(); ++j) {
                matrix[i][j] = matrix[i][j + 1];
            }
            matrix[i][verticesList.size()] = false;
        }
    }

    /**
     * Deletes an edge between two vertices in the graph.
     *
     * @param edge the edge to be deleted
     */
    @Override
    public void deleteEdge(Edge<T> edge) {
        Vertex<T> src = edge.getSource();
        Vertex<T> dest = edge.getDestination();
        int srcIndex = vertexIndexMap.get(src);
        int destIndex = vertexIndexMap.get(dest);
        if (matrix[srcIndex][destIndex]) {
            matrix[srcIndex][destIndex] = false;
        }
    }

    /**
     * Returns a list of neighbours of a given vertex.
     *
     * @param vertex the vertex whose neighbours are to be returned
     * @return the list of neighbours
     */
    @Override
    public List<Vertex<T>> getNeighbours(Vertex<T> vertex) {
        List<Vertex<T>> neighbours = new ArrayList<>();
        if (!vertexIndexMap.containsKey(vertex)) {
            return neighbours;
        }
        int index = vertexIndexMap.get(vertex);
        for (int j = 0; j < verticesList.size(); j++) {
            if (matrix[index][j]) {
                neighbours.add(verticesList.get(j));
            }
        }
        return neighbours;
    }

    /**
     * Reads a graph from a file.
     * The first line of the file should contain the number of vertices.
     * Each of the following lines should contain a vertex label.
     * After the vertices, the next line should contain the number of edges,
     * followed by lines containing the source and destination vertex labels for each edge.
     *
     * @param filename the name of the file to read from
     */
    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            line = br.readLine();
            if (line == null) {
                return;
            }
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
            if (line == null) {
                return;
            }
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
            e.printStackTrace();
        }
    }

    /**
     * Finds a vertex by its label.
     *
     * @param label the label of the vertex to find
     * @return the vertex if found, or null otherwise
     */
    Vertex<T> findVertexByLabel(T label) {
        for (Vertex<T> v : verticesList) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Returns a list of all vertices in the graph.
     *
     * @return the list
     */
    @Override
    public List<Vertex<T>> getVertices() {
        return new ArrayList<>(verticesList);
    }

    /**
     * Compares this graph to another object for equality.
     *
     * @param o the object to compare to
     * @return true if the graphs are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdjacencyMatrixGraph<?> that = (AdjacencyMatrixGraph<?>) o;
        if (capacity != that.capacity) {
            return false;
        }
        if (!verticesList.equals(that.verticesList)) {
            return false;
        }
        return Arrays.deepEquals(matrix, that.matrix);
    }

    /**
     * Returns a hash code value for the graph.
     *
     * @return a hash code value
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(verticesList, vertexIndexMap, capacity);
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyMatrixGraph:\n");
        sb.append("Vertices:\n");
        for (Vertex<T> v : verticesList) {
            sb.append(v).append("\n");
        }
        sb.append("Edges:\n");
        for (int i = 0; i < verticesList.size(); i++) {
            for (int j = 0; j < verticesList.size(); j++) {
                if (matrix[i][j]) {
                    sb.append("Edge from ").append(verticesList.get(i).getLabel())
                            .append(" to ").append(verticesList.get(j).getLabel()).append("\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * Returns a collection of all edges in the graph.
     *
     * @return a collection
     */
    public Collection<Edge<T>> getEdges() {
        List<Edge<T>> edges = new ArrayList<>();
        for (int i = 0; i < verticesList.size(); i++) {
            for (int j = 0; j < verticesList.size(); j++) {
                if (matrix[i][j]) {
                    edges.add(new Edge<>(verticesList.get(i), verticesList.get(j)));
                }
            }
        }
        return edges;
    }
}