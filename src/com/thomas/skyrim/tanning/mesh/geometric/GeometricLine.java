package com.thomas.skyrim.tanning.mesh.geometric;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Edge;

/**
 * This class was created by thoma on 01-May-18.
 */
public class GeometricLine {
    private final Coordinate p1;
    private final Coordinate p2;

    public static GeometricLine of(Edge edge) {
        return new GeometricLine(
                edge.getNodes().get(0).getCoordinate(),
                edge.getNodes().get(1).getCoordinate()
        );
    }

    GeometricLine(Coordinate p1, Coordinate p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}
