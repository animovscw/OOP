package ru.nsu.anisimov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of a graph using an incidence matrix.
 *
 * @param <T> the type of elements stored in the vertices of the graph
 */
public class IncidenceMatrixGraph<T> implements Graph<T> {
    private final List<Vertex<T>> verticesList;
    private final Map<Vertex<T>, Integer> vertexIndexMap;
    private final List<Edge<T>> edgesList;
    private int capacityVertices;
    private int capacityEdges;
    private int[][] matrix;

    /**
     * Constructs a new, empty incidence matrix graph
     * with initial capacities for vertices and edges.
     */
    public IncidenceMatrixGraph() {
        verticesList = new ArrayList<>();
        vertexIndexMap = new HashMap<>();
        edgesList = new ArrayList<>();
        capacityVertices = 10;
        capacityEdges = 10;
        matrix = new int[capacityVertices][capacityEdges];
    }

    /**
     * Adds a vertex to the graph. If the current capacity for vertices is reached,
     * the capacity is expanded.
     *
     * @param vertex the vertex to be added
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        if (verticesList.size() >= capacityVertices) {
            expandVertices();
        }
        verticesList.add(vertex);
        vertexIndexMap.put(vertex, verticesList.size() - 1);
    }

    /**
     * Expands the capacity of the vertex storage in the incidence matrix.
     */
    private void expandVertices() {
        capacityVertices *= 2;
        int[][] newMatrix = new int[capacityVertices][capacityEdges];
        for (int i = 0; i < matrix.length; ++i) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
        }
        matrix = newMatrix;
    }

    /**
     * Expands the capacity of the edge storage in the incidence matrix.
     */
    private void expandEdges() {
        capacityEdges *= 2;
        int[][] newMatrix = new int[capacityVertices][capacityEdges];
        for (int i = 0; i < matrix.length; ++i) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
        }
        matrix = newMatrix;
    }

    /**
     * Adds an edge to the graph. If the current capacity for edges is reached,
     * the capacity is expanded.
     *
     * @param edge the edge to be added
     */
    @Override
    public void addEdge(Edge<T> edge) {
        if (edgesList.size() >= capacityEdges) {
            expandEdges();
        }
        edgesList.add(edge);
        int edgeIndex = edgesList.size() - 1;
        int srcIndex = vertexIndexMap.get(edge.getSource());
        int destIndex = vertexIndexMap.get(edge.getDestination());
        matrix[srcIndex][edgeIndex] = 1;
        matrix[destIndex][edgeIndex] = -1;
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
        Iterator<Edge<T>> it = edgesList.iterator();
        List<Integer> edgesToRemove = new ArrayList<>();
        int currentEdge = 0;
        while (it.hasNext()) {
            Edge<T> edge = it.next();
            if (edge.getSource().equals(vertex) || edge.getDestination().equals(vertex)) {
                it.remove();
                edgesToRemove.add(currentEdge);
            }
            ++currentEdge;
        }
        for (int edgeIndex : edgesToRemove) {
            for (int i = 0; i < verticesList.size(); ++i) {
                for (int j = edgeIndex; j < edgesList.size(); ++j) {
                    matrix[i][j] = matrix[i][j + 1];
                }
                matrix[i][edgesList.size()] = 0;
            }
        }
    }

    /**
     * Deletes an edge from the graph.
     *
     * @param edge the edge to be deleted
     */
    @Override
    public void deleteEdge(Edge<T> edge) {
        int index = edgesList.indexOf(edge);
        edgesList.remove(index);
        for (int i = 0; i < verticesList.size(); ++i) {
            for (int j = index; j < edgesList.size(); ++j) {
                matrix[i][j] = matrix[i][j + 1];
            }
            matrix[i][edgesList.size()] = 0;
        }
    }

    /**
     * Returns a list of neighbours of a given vertex.
     *
     * @param vertex the vertex whose neighbors are to be returned
     * @return the list of neighbours
     */
    @Override
    public List<Vertex<T>> getNeighbours(Vertex<T> vertex) {
        List<Vertex<T>> neighbours = new ArrayList<>();
        if (!vertexIndexMap.containsKey(vertex)) {
            return neighbours;
        }
        int index = vertexIndexMap.get(vertex);
        for (int j = 0; j < edgesList.size(); ++j) {
            if (matrix[index][j] == 1) {
                neighbours.add(edgesList.get(j).getDestination());
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
        IncidenceMatrixGraph<?> that = (IncidenceMatrixGraph<?>) o;
        if (capacityVertices != that.capacityVertices) {
            return false;
        }
        if (capacityEdges != that.capacityEdges) {
            return false;
        }
        if (!verticesList.equals(that.verticesList)) {
            return false;
        }
        if (!vertexIndexMap.equals(that.vertexIndexMap)) {
            return false;
        }
        if (!edgesList.equals(that.edgesList)) {
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
        int result = Objects.hash(
                verticesList, vertexIndexMap,
                edgesList, capacityVertices, capacityEdges
        );
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
        sb.append("IncidenceMatrixGraph:\n");
        sb.append("Vertices:\n");
        for (Vertex<T> v : verticesList) {
            sb.append(v).append("\n");
        }
        sb.append("Edges:\n");
        for (Edge<T> e : edgesList) {
            sb.append(e).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a list of all edges in the graph.
     *
     * @return a list
     */
    public List<Edge<T>> getEdges() {
        return new ArrayList<>(edgesList);
    }
}