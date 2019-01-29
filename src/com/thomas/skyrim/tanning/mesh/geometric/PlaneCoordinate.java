package com.thomas.skyrim.tanning.mesh.geometric;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;

/**
 * This class was created by thoma on 09-May-18.
 */
public class PlaneCoordinate {
    private final double u;
    private final double v;
    private final GeometricPlane triangle;

    public PlaneCoordinate(double u, double v, GeometricPlane triangle) {
        this.u = u;
        this.v = v;
        this.triangle = triangle;
    }

    public double getU() {
        return u;
    }

    public double getV() {
        return v;
    }

    public GeometricPlane getPlane() {
        return triangle;
    }

    public boolean inTriangle() {
        return 0 <= u && 0 <= v && u + v <= 1;
    }

    public Coordinate toCoordinate() {
        return triangle.getP1().times(1 - u - v).add(triangle.getP2().times(u)).add(triangle.getP3().times(v));
    }
}
