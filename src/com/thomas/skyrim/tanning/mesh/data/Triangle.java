package com.thomas.skyrim.tanning.mesh.data;

import java.util.ArrayList;
import java.util.List;

/**
 * We use P1, P2 and P3 as the nodes singleOf the Triangle. Then the triangle is:
 * T(s,t) = P1*(1-s-t) + P2*s + P3*t for (s,t) in ([0, 1],[0, 1])
 *
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
