package com.thomas.skyrim.tanning.mesh.geometric;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Tuple;

public class GeometricLine2D {
    protected final Tuple p1;
    protected final Tuple p2;

    public GeometricLine2D(Tuple p1, Tuple p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public static GeometricLine of(Coordinate p1, Coordinate p2) {
        return new GeometricLine(p1, p2);
    }

    public Tuple getP1() {
        return p1;
    }

    public Tuple getP2() {
        return p2;
    }

    /**
     * from wikipedia
     */
    public double distance(Tuple t) {
        double uDist = p2.u() - p1.u();
        double vDist = p2.v() - p1.v();
        double distP1P2 = Math.sqrt(vDist * vDist + uDist * uDist);

        double topPart = Math.abs(
                (p2.v() - p1.v()) * t.u() - (p2.u() - p1.u()) * t.v() + p2.u() * p1.v() - p2.v() * p1.u()
        );
        return topPart / distP1P2;
    }

    @Override
    public String toString() {
        return p1 + "(l - 1) + l" + p2;
    }
}
