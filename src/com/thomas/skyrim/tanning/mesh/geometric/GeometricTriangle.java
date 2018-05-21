package com.thomas.skyrim.tanning.mesh.geometric;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Triangle;

/**
 * This class was created by thoma on 05-May-18.
 */
public class GeometricTriangle extends GeometricPlane {
    private final Triangle origin;

    GeometricTriangle(Coordinate p1, Coordinate p2, Coordinate p3, Triangle origin) {
        super(p1, p2, p3);
        this.origin = origin;
    }

    public static GeometricTriangle of(Triangle triangle) {
        return new GeometricTriangle(
                triangle.getNodes().get(0).getCoordinate(),
                triangle.getNodes().get(1).getCoordinate(),
                triangle.getNodes().get(2).getCoordinate(),
                triangle
        );
    }

    public Triangle getOrigin() {
        return origin;
    }


}
