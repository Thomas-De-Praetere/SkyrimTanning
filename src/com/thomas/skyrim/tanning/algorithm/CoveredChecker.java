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

import java.util.List;
import java.util.Set;

/**
 *
 */
public class CoveredChecker {
    private final List<BoundingBoxTree> treesOfMeshes;
    private boolean tryr = true;

    public CoveredChecker(List<BoundingBoxTree> treesOfMeshes) {
        this.treesOfMeshes = treesOfMeshes;
    }

    public boolean isCovered(Triangle triangle) {
//        tryr = !tryr;
//        return tryr;
        BoundingBox boundingBox = getBoundingBox(triangle);
        GeometricTriangle geoTri = GeometricTriangle.of(triangle);
        GeometricLine ray = GeometricLine.of(geoTri.getCenter(), geoTri.getCenter().add(geoTri.getNormalVector()));

        for (BoundingBoxTree meshTree : treesOfMeshes) {
            Set<Triangle> inBox = meshTree.getInBox(boundingBox);
//            System.out.println(inBox.size());
            for (Triangle outsideTri : inBox) {
                GeometricTriangle outside = GeometricTriangle.of(outsideTri);
                Pair<TriangleCoordinate, LineCoordinate> pair = outside.edgeIntersection(ray);
                //intersection with covering triangle is in the direction of the ray and on the triangle
                if (pair.getFirst().inTriangle() && pair.getSecond().hasPositiveDirection()) return true;
            }
        }
        return false;
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
