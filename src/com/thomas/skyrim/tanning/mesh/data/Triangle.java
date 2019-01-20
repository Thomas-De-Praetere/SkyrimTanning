package com.thomas.skyrim.tanning.mesh.data;

import com.google.common.collect.Sets;

import java.awt.*;
import java.util.Set;

/**
 * We use P1, P2 and P3 as the nodes singleOf the Triangle. Then the triangle is:
 * T(s,t) = P1*(1-s-t) + P2*s + P3*t for (s,t) in ([0, 1],[0, 1])
 * <p>
 * This class was created by thoma on 24-Apr-18.
 */
public class Triangle {

    private final Node n1;
    private final Node n2;
    private final Node n3;

    public Triangle(Node n1, Node n2, Node n3) {
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }

    public Node getN1() {
        return n1;
    }

    public Node getN2() {
        return n2;
    }

    public Node getN3() {
        return n3;
    }

    public Set<Node> getNodes() {
        return Sets.newHashSet(n1, n2, n3);
    }
}