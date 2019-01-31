package com.thomas.skyrim.tanning.algorithm;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.geometric.GeometricLine;
import com.thomas.skyrim.tanning.mesh.geometric.GeometricTriangle;
import com.thomas.skyrim.tanning.mesh.geometric.LineCoordinate;
import com.thomas.skyrim.tanning.mesh.geometric.TriangleCoordinate;
import com.thomas.skyrim.tanning.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class CoveredChecker {
    private final List<Mesh> meshes;
    private Random random = new Random();

    public CoveredChecker(List<Mesh> meshes) {
        this.meshes = meshes;
    }

    public double isCovered(Triangle triangle) {
        GeometricTriangle geoTri = GeometricTriangle.of(triangle);
        Coordinate normalVector = geoTri.getNormalVector();
        int amount = 10;
        List<GeometricLine> rays = generateRays(amount, geoTri.getCenter(), normalVector);

        double covered = 0.0;
        for (GeometricLine ray : rays) {
            if (!isRayCovered(ray)) covered += 1.0;
        }
        return covered / (double) rays.size();
    }

    private boolean isRayCovered(GeometricLine ray) {
        for (Mesh mesh : meshes) {
            if (meshCoversRay(ray, mesh)) return true;
        }
        return false;
    }

    private boolean meshCoversRay(GeometricLine ray, Mesh mesh) {
        for (Triangle meshTri : mesh.getTriangles()) {
            GeometricTriangle triangle = GeometricTriangle.of(meshTri);

            Pair<TriangleCoordinate, LineCoordinate<GeometricLine>> pair = triangle.edgeIntersection(ray);
            //intersection with covering triangle is in the direction of the ray and on the triangle
            if (pair.getFirst().inTriangle() && pair.getSecond().hasPositiveDirection()) {
                return true;
            }
        }
        return false;
    }

    private List<GeometricLine> generateRays(int amount, Coordinate center, Coordinate normalVector) {
        List<GeometricLine> rays = new ArrayList<>();
        rays.add(GeometricLine.of(center, center.add(normalVector)));
        int tries = 0;
        while (tries < amount) {
            Coordinate vector = new Coordinate(random.nextDouble(), random.nextDouble(), random.nextDouble());
            double dot = normalVector.dot(vector);
            if (dot > 0.0) {
                rays.add(GeometricLine.of(center, center.add(vector)));
            } else {
                Coordinate accordingToNormal = Coordinate.ORIGIN.subtract(vector);
                rays.add(GeometricLine.of(center, center.add(accordingToNormal)));
            }
            tries++;
        }
        return rays;
    }
}
