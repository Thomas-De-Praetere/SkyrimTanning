package com.thomas.skyrim.tanning.mesh.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Edges need triangles to know from which triangle to which we go when projecting.
 * <p>
 * This class was created by thoma on 24-Apr-18.
 */
public class Edge {

    private final List<Node> nodes;
    private final Set<Triangle> triangles;

    public Edge(List<Node> nodes) {
        this.nodes = nodes;
        this.triangles = new HashSet<>();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Set<Triangle> getTriangles() {
        return triangles;
    }
}
