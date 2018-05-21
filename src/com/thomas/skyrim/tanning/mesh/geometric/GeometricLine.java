package com.thomas.skyrim.tanning.mesh.geometric;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Edge;

/**
 * This class was created by thoma on 01-May-18.
 */
public class GeometricLine {
    protected final Coordinate p1;
    protected final Coordinate p2;

    GeometricLine(Coordinate p1, Coordinate p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public static GeometricLine of(Edge edge) {
        return new GeometricLine(
                edge.getStart().getCoordinate(),
                edge.getEnd().getCoordinate()
        );
    }

    public Coordinate getP1() {
        return p1;
    }

    public Coordinate getP2() {
        return p2;
    }

    @Override
    public String toString() {
        return p1 + "(l - 1) + l" + p2;
    }
}
