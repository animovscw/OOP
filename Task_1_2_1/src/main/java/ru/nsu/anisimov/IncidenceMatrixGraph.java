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

public class IncidenceMatrixGraph<T> implements Graph<T> {
    private final List<Vertex<T>> verticesList;
    private final Map<Vertex<T>, Integer> vertexIndexMap;
    private final List<Edge<T>> edgesList;
    private int capacityVertices;
    private int capacityEdges;
    private int[][] matrix;

    public IncidenceMatrixGraph() {
        verticesList = new ArrayList<>();
        vertexIndexMap = new HashMap<>();
        edgesList = new ArrayList<>();
        capacityVertices = 10;
        capacityEdges = 10;
        matrix = new int[capacityVertices][capacityEdges];
    }

    @Override
    public void addVertex(Vertex<T> vertex) {
        if (verticesList.size() >= capacityVertices) {
            expandVertices();
        }
        verticesList.add(vertex);
        vertexIndexMap.put(vertex, verticesList.size() - 1);
    }

    private void expandVertices() {
        capacityVertices *= 2;
        int[][] newMatrix = new int[capacityVertices][capacityEdges];
        for (int i = 0; i < matrix.length; ++i) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
        }
        matrix = newMatrix;
    }

    private void expandEdges() {
        capacityEdges *= 2;
        int[][] newMatrix = new int[capacityVertices][capacityEdges];
        for (int i = 0; i < matrix.length; ++i) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
        }
        matrix = newMatrix;
    }

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

    @Override
    public int hashCode() {
        int result = Objects.hash(verticesList, vertexIndexMap, edgesList, capacityVertices, capacityEdges);
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }

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
}