package com.thomas.skyrim.tanning.mesh.geometric;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.util.Pair;
import com.thomas.skyrim.tanning.util.Solver;

/**
 * This class was created by thoma on 21-May-18.
 */
public class GeometricPlane {
    protected final Coordinate p1;
    protected final Coordinate p2;
    protected final Coordinate p3;

    GeometricPlane(Coordinate p1, Coordinate p2, Coordinate p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public static GeometricPlane of(Coordinate p1, Coordinate p2, Coordinate p3) {
        return new GeometricPlane(p1, p2, p3);
    }

    public Coordinate getP1() {
        return p1;
    }

    public Coordinate getP2() {
        return p2;
    }

    public Coordinate getP3() {
        return p3;
    }

    public Coordinate getNormalVector() {
        Coordinate u = p1.subtract(p2);
        Coordinate v = p1.subtract(p3);
        return new Coordinate(
                u.y() * v.z() - u.z() * v.y(),
                u.z() * v.x() - u.x() * v.z(),
                u.x() * v.y() - u.y() * v.x()
        );
    }

    public PlaneCoordinate projectPoint(Coordinate point) {
        return edgeIntersection(
                new GeometricLine(point, point.subtract(getNormalVector()))
        ).getU();
    }

    public Pair<PlaneCoordinate, LineCoordinate> edgeIntersection(GeometricLine line) {
        Coordinate A = p1;
        Coordinate B = p2.subtract(p1);
        Coordinate C = p3.subtract(p1);

        Coordinate L = line.getP1();
        Coordinate M = line.getP2().subtract(line.getP1());
        Coordinate N = L.subtract(A);

        //solve Bu + Cv - Ml = N (with u and v the parameters of the triangle and l the one of the line)

        Solver solver = new Solver(4,
                B.x(), C.x(), -M.x(), N.x(),
                B.y(), C.y(), -M.y(), N.y(),
                B.z(), C.z(), -M.z(), N.z()
        );

        double[][] reduce = solver.reduce();

        double u = reduce[0][3];
        double v = reduce[1][3];
        double l = reduce[2][3];

        return Pair.of(
                new PlaneCoordinate(u, v, this),
                new LineCoordinate(l, line)
        );
    }

    @Override
    public String toString() {
        return
                p1 + "(1 - u - v) + " +
                        p2 + "u + v" +
                        p3;
    }
}
