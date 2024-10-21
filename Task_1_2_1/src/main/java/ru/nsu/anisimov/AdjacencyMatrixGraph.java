package ru.nsu.anisimov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdjacencyMatrixGraph<T> implements Graph<T> {
    private final List<Vertex<T>> verticesList;
    private final Map<Vertex<T>, Integer> vertexIndexMap;
    private boolean[][] matrix;
    private int capacity;

    public AdjacencyMatrixGraph() {
        verticesList = new ArrayList<>();
        vertexIndexMap = new HashMap<>();
        capacity = 10;
        matrix = new boolean[capacity][capacity];
    }

    @Override
    public void addVertex(Vertex<T> vertex) {
        if (verticesList.size() >= capacity) {
            expandMatrix();
        }
        verticesList.add(vertex);
        vertexIndexMap.put(vertex, verticesList.size() - 1);
    }

    private void expandMatrix() {
        capacity *= 2;
        boolean[][] newMatrix = new boolean[capacity][capacity];
        for (int i = 0; i < matrix.length; ++i) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
        }
        matrix = newMatrix;
    }

    @Override
    public void addEdge(Edge<T> edge) {
        Vertex<T> src = edge.getSource();
        Vertex<T> dest = edge.getDestination();
        int srcIndex = vertexIndexMap.get(src);
        int destIndex = vertexIndexMap.get(dest);
        matrix[srcIndex][destIndex] = true;
    }

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

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            line = br.readLine();
            if (line == null) {
                return;
            }
            int numVertices = Integer.parseInt(line.trim());
            for (int i = 0; i < numVertices; i++) {
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

    private Vertex<T> findVertexByLabel(T label) {
        for (Vertex<T> v : verticesList) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public List<Vertex<T>> getVertices() {
        return new ArrayList<>(verticesList);
    }

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

    @Override
    public int hashCode() {
        int result = Objects.hash(verticesList, vertexIndexMap, capacity);
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }

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