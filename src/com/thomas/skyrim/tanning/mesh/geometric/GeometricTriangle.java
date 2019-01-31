package com.thomas.skyrim.tanning.mesh.geometric;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.util.Pair;

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
                triangle.getN1().getCoordinate(),
                triangle.getN2().getCoordinate(),
                triangle.getN3().getCoordinate(),
                triangle
        );
    }

    public Triangle getOrigin() {
        return origin;
    }

    public Coordinate getCenter() {
        return p1.add(p2).add(p3).divide(3.0);
    }

    @Override
    public TriangleCoordinate projectPoint(Coordinate point) {
        return TriangleCoordinate.of(super.projectPoint(point), this);
    }

    @Override
    public Pair<TriangleCoordinate, LineCoordinate<GeometricLine>> edgeIntersection(GeometricLine line) {
        Pair<? extends PlaneCoordinate, LineCoordinate<GeometricLine>> pair = super.edgeIntersection(line);
        return Pair.of(TriangleCoordinate.of(pair.getFirst(), this), pair.getSecond());
    }
}
