package com.thomas.skyrim.tanning.mesh.geometric;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Edge;

/**
 * This class was created by thoma on 21-May-18.
 */
public class GeometricEdge extends GeometricLine {
    private final Edge origin;

    GeometricEdge(Coordinate p1, Coordinate p2, Edge origin) {
        super(p1, p2);
        this.origin = origin;
    }

    public static GeometricEdge of(Edge edge) {
        return new GeometricEdge(
                edge.getStart().getCoordinate(),
                edge.getEnd().getCoordinate(),
                edge
        );
    }

    public Edge getOrigin() {
        return origin;
    }
}
