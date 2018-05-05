package com.thomas.skyrim.tanning.mesh.data;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class Coordinate extends Triple<Double> implements NumberLike<Coordinate> {

    public Coordinate(Double x, Double y, Double z) {
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
}
