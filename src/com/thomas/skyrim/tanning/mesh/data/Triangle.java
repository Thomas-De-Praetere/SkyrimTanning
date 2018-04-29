package com.thomas.skyrim.tanning.mesh.data;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class Triangle {

    private final Node[] nodes;
    private Edge[] edges;

    public Triangle(Node[] nodes) {
        this.nodes = nodes;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public void setEdges(Edge[] edges) {
        this.edges = edges;
    }
}
