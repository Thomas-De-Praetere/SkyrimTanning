package com.thomas.skyrim.tanning.mesh.data;

/**
 * Edges need triangles to know from which triangle to which we go when projecting.
 * <p>
 * This class was created by thoma on 24-Apr-18.
 */
public class Edge {

    private final Node[] nodes;
    private final Triangle[] triangles;

    public Edge(Node[] nodes, Triangle[] triangles) {
        this.nodes = nodes;
        this.triangles = triangles;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public Triangle[] getTriangles() {
        return triangles;
    }
}
