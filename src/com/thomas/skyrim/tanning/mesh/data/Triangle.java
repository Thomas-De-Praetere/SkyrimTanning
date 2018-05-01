package com.thomas.skyrim.tanning.mesh.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class Triangle {

    private final List<Node> nodes;
    private final List<Edge> edges;

    public Triangle(List<Node> nodes) {
        this.nodes = nodes;
        this.edges = new ArrayList<>();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
