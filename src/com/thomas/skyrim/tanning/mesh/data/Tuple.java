package com.thomas.skyrim.tanning.mesh.data;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class Tuple implements NumberLike<Tuple> {

    private final double u;
    private final double v;

    public Tuple(double u, double v) {
        this.u = u;
        this.v = v;
    }

    public double u() {
        return u;
    }

    public double v() {
        return v;
    }

    @Override
    public Tuple add(Tuple co) {
        return new Tuple(u + co.u, v + co.v);
    }

    @Override
    public Tuple subtract(Tuple co) {
        return new Tuple(u - co.u, v - co.v);
    }

    @Override
    public Tuple times(double t) {
        return new Tuple(u * t, v * t);
    }

    @Override
    public Tuple divide(double t) {
        return new Tuple(u / t, v / t);
    }
}
