package com.thomas.skyrim.tanning.mesh.geometric;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.util.Pair;

/**
 * This class was created by thoma on 05-May-18.
 */
public class GeometricTriangle {
    private final Coordinate p1;
    private final Coordinate p2;
    private final Coordinate p3;

    GeometricTriangle(Coordinate p1, Coordinate p2, Coordinate p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public static GeometricTriangle of(Triangle triangle) {
        return new GeometricTriangle(
                triangle.getNodes().get(0).getCoordinate(),
                triangle.getNodes().get(1).getCoordinate(),
                triangle.getNodes().get(2).getCoordinate()
        );
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
        //Vector singleOf edge
        Coordinate n = line.getP2().subtract(line.getP1());
        //Root singleOf edge
        Coordinate V = line.getP1();

        Coordinate A = p2.subtract(p1);
        Coordinate B = p3.subtract(p1);
        Coordinate C = p1.subtract(n);

        double Q = A.y() - A.x() * V.y() / V.x();
        double R = B.y() - B.x() * V.y() / V.x();
        double S = C.x() - C.y();

        double v = (V.z() * (S * A.x() + Q * C.x()) - V.x() * (S * A.z() + Q * C.z())) /
                (V.z() * (R * A.x() - Q * B.x()) - V.x() * (R * A.z() - Q * B.z()));
        double u = (S - v * R) / Q;
        double l = -(u * A.x() + v * B.x() + C.x()) / V.x();

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
