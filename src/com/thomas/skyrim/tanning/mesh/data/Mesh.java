package com.thomas.skyrim.tanning.mesh.data;

import java.util.List;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class Mesh {

    private final String name;
    private final List<Triangle> triangles;
    private final List<Node> nodes;
    private final List<Edge> edges;

    public Mesh(String name, List<Triangle> triangles, List<Node> nodes, List<Edge> edges) {
        this.name = name;
        this.triangles = triangles;
        this.nodes = nodes;
        this.edges = edges;
    }

    public String getName() {
        return name;
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
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
