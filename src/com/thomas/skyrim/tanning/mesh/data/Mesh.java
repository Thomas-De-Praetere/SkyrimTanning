package com.thomas.skyrim.tanning.mesh.data;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class Mesh {

    private final String name;
    private final List<Triangle> triangles;
    private final List<Node> nodes;
    private final List<Edge> edges;

    private final ListMultimap<Node, Triangle> nodeToTriangle;
    private final ListMultimap<Node, Edge> nodeToEdge;
    private final ListMultimap<Edge, Triangle> edgeToTriangle;
    private final ListMultimap<Triangle, Edge> triangleToEdge;


    public Mesh(String name, List<Triangle> triangles, List<Node> nodes, List<Edge> edges) {
        this.name = name;
        this.triangles = new ArrayList<>(Objects.requireNonNull(triangles));
        this.nodes = new ArrayList<>(Objects.requireNonNull(nodes));
        this.edges = new ArrayList<>(Objects.requireNonNull(edges));

        nodeToTriangle = ArrayListMultimap.create();
        nodeToEdge = ArrayListMultimap.create();
        edgeToTriangle = ArrayListMultimap.create();
        triangleToEdge = ArrayListMultimap.create();

        for (Edge edge : edges) {
            nodeToEdge.put(edge.getStart(), edge);
            nodeToEdge.put(edge.getEnd(), edge);
        }
        for (Triangle triangle : triangles) {
            triangle.getNodes().forEach(n -> nodeToTriangle.put(n, triangle));
            Node n1 = triangle.getN1();
            Node n2 = triangle.getN2();
            Node n3 = triangle.getN3();

            Edge n1n2 = getEdge(n1, n2);
            Edge n2n3 = getEdge(n2, n3);
            Edge n3n1 = getEdge(n3, n1);

            edgeToTriangle.put(n1n2, triangle);
            edgeToTriangle.put(n2n3, triangle);
            edgeToTriangle.put(n3n1, triangle);
            triangleToEdge.putAll(triangle, Arrays.asList(n1n2, n2n3, n3n1));
        }

    }

    private Edge getEdge(Node n1, Node n2) {
        return nodeToEdge.get(n1).stream()
                .filter(e -> e.getNodes().contains(n2))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    public String getName() {
        return name;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public List<Triangle> getTriangles(Node node) {
        return nodeToTriangle.get(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Edge> getTriangleEdges(Triangle t) {
        return triangleToEdge.get(t);
    }

    public List<Triangle> getTriangles(Edge edge) {
        return edgeToTriangle.get(edge);
    }

    @Override
    public String toString() {
        return "Mesh{" +
                "name='" + name + '\'' +
                ", triangles:" + triangles.size() +
                ", nodes:" + nodes.size() +
                ", edges:" + edges.size() +
                '}';
    }
}
