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

    protected GeometricTriangle(Coordinate p1, Coordinate p2, Coordinate p3) {
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

    public Pair<PlaneCoordinate, GeometricLine.LineCoordinate> edgeIntersection(GeometricLine line) {
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
                new GeometricLine.LineCoordinate(l, line)
        );
    }

    public static class PlaneCoordinate {
        private final double u;
        private final double v;
        private final GeometricTriangle triangle;

        public PlaneCoordinate(double u, double v, GeometricTriangle triangle) {
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

        public GeometricTriangle getTriangle() {
            return triangle;
        }

        public Coordinate toCoordinate() {
            return triangle.p1.times(1 - u - v).add(triangle.p2.times(u)).add(triangle.p3.times(v));
        }
    }
}
