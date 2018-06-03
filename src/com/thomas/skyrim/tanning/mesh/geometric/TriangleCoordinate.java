package com.thomas.skyrim.tanning.mesh.geometric;

/**
 * This class was created by thoma on 03-Jun-18.
 */
public class TriangleCoordinate extends PlaneCoordinate {

    private final GeometricTriangle triangle;

    public TriangleCoordinate(double u, double v, GeometricTriangle triangle) {
        super(u, v, triangle);
        this.triangle = triangle;
    }

    @Override
    public GeometricTriangle getPlane() {
        return triangle;
    }

    public static TriangleCoordinate of(PlaneCoordinate coordinate, GeometricTriangle triangle) {
        return new TriangleCoordinate(coordinate.getU(), coordinate.getV(), triangle);
    }
}
