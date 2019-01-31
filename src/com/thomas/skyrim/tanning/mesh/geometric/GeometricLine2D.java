package com.thomas.skyrim.tanning.mesh.geometric;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Tuple;
import com.thomas.skyrim.tanning.util.Solver;

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
                (vDist) * t.u() - (uDist) * t.v() + p2.u() * p1.v() - p2.v() * p1.u()
        );
        return topPart / distP1P2;
    }

    public LineCoordinate<GeometricLine2D> project(Tuple t) {
        Tuple perp = p2.subtract(p1).perp();
        GeometricLine2D projectionLine = new GeometricLine2D(t, t.add(perp));

        //This line is P = P1+p(P2-P1) and the projection line is Q = Q1+q(Q2-Q1)
        //Equality results in (minus was brought in the second) q(Q2-Q1)+p(P1-P2)=P1-Q1

        Tuple Q2Q1 = projectionLine.p2.subtract(projectionLine.p1);
        Tuple P1P2 = p1.subtract(p2);
        Tuple P1Q1 = p1.subtract(projectionLine.p1);

        Solver solver = new Solver(3,
                Q2Q1.u(), P1P2.u(), P1Q1.u(),
                Q2Q1.v(), P1P2.v(), P1Q1.v()
        );
        double[][] reduce = solver.reduce();
        double p = reduce[1][2];
        return new LineCoordinate<>(p, this);
    }

    @Override
    public String toString() {
        return p1 + "(l - 1) + l" + p2;
    }
}
