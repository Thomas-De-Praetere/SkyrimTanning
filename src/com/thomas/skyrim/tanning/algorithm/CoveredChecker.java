package com.thomas.skyrim.tanning.algorithm;

import com.thomas.skyrim.tanning.mesh.data.BoundingBox;
import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.geometric.GeometricLine;
import com.thomas.skyrim.tanning.mesh.geometric.GeometricTriangle;
import com.thomas.skyrim.tanning.mesh.geometric.LineCoordinate;
import com.thomas.skyrim.tanning.mesh.geometric.TriangleCoordinate;
import com.thomas.skyrim.tanning.util.Pair;
import com.thomas.skyrim.tanning.util.intervalTree.BoundingBoxTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 */
public class CoveredChecker {
    private final List<BoundingBoxTree> treesOfMeshes;
    private Random random = new Random();

    public CoveredChecker(List<BoundingBoxTree> treesOfMeshes) {
        this.treesOfMeshes = treesOfMeshes;
    }

    public double isCovered(Triangle triangle) {
        BoundingBox boundingBox = getBoundingBox(triangle);
        GeometricTriangle geoTri = GeometricTriangle.of(triangle);
        Coordinate normalVector = geoTri.getNormalVector();
        int amount = 10;
        List<GeometricLine> rays = generateRays(amount, geoTri.getCenter(), normalVector);

        double covered = 0.0;
        for (GeometricLine ray : rays) {
            boolean rayCovered = false;
            for (BoundingBoxTree meshTree : treesOfMeshes) {
                boolean thisCovers = false;
                Set<Triangle> inBox = meshTree.getInBox(boundingBox);
                for (Triangle outsideTri : inBox) {
                    GeometricTriangle outside = GeometricTriangle.of(outsideTri);

                    Pair<TriangleCoordinate, LineCoordinate> pair = outside.edgeIntersection(ray);
                    //intersection with covering triangle is in the direction of the ray and on the triangle
                    if (pair.getFirst().inTriangle() && pair.getSecond().hasPositiveDirection()) {
                        thisCovers = true;
                        rayCovered = true;
                        break;
                    }
                }
                if (thisCovers) break;
            }
            if (!rayCovered) covered += 1.0;
        }
        return covered / (double) rays.size();
    }

    private List<GeometricLine> generateRays(int amount, Coordinate center, Coordinate normalVector) {
        List<GeometricLine> rays = new ArrayList<>();
        rays.add(GeometricLine.of(center, center.add(normalVector)));
        int tries = 0;
        while (tries < amount) {
            Coordinate vector = new Coordinate(random.nextDouble(), random.nextDouble(), random.nextDouble());
            double dot = normalVector.dot(vector);
            if (dot > 0.0) rays.add(GeometricLine.of(center, center.add(vector)));
            tries++;
        }
        return rays;
    }


    private BoundingBox getBoundingBox(Triangle triangle) {
        GeometricTriangle geoTri = GeometricTriangle.of(triangle);
        Coordinate center = geoTri.getCenter();
        Coordinate co = new Coordinate(1, 1, 1);
        return new BoundingBox(
                center.subtract(co),
                center.add(co)
        );
    }
}
