package com.thomas.skyrim.tanning.mesh.data;

import java.util.HashSet;
import java.util.Set;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class Node {

    private final Coordinate coordinate;
    private final Tuple uv;

    public Node(Coordinate coordinate, Tuple uv) {
        this.coordinate = coordinate;
        this.uv = uv;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Tuple getUv() {
        return uv;
    }
}
