package com.thomas.skyrim.tanning.mesh.data;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class Coordinate extends Triple<Double> implements NumberLike<Coordinate> {

    public Coordinate(double x, double y, double z) {
        super(x, y, z);
    }

    @Override
    public Coordinate add(Coordinate co) {
        return new Coordinate(x + co.x, y + co.y, z + co.z);
    }

    @Override
    public Coordinate subtract(Coordinate co) {
        return new Coordinate(x - co.x, y - co.y, z - co.z);
    }

    @Override
    public Coordinate times(double t) {
        return new Coordinate(x * t, y * t, z * t);
    }

    @Override
    public Coordinate divide(double t) {
        return new Coordinate(x / t, y / t, z / t);
    }

    public double distance(Coordinate other) {
        if (this == other) return 0.0;
        double xDif = x - other.x;
        double yDif = y - other.y;
        double zDif = z - other.z;
        return Math.sqrt(xDif * xDif + yDif * yDif + zDif * zDif);
    }
}
