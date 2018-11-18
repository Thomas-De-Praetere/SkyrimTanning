package com.thomas.skyrim.tanning.mesh.data;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Edges need triangles to know from which triangle to which we go when projecting.
 * <p>
 * This class was created by thoma on 24-Apr-18.
 */
public class Edge {

    private final Node start;
    private final Node end;

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public Set<Node> getNodes() {
        return Sets.newHashSet(start, end);
    }
}
