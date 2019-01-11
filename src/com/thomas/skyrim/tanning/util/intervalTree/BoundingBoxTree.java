package com.thomas.skyrim.tanning.util.intervalTree;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class BoundingBoxTree {
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";
    private static final List<String> evaluationOrder = Arrays.asList(X, Y, Z);
    private final IntervalTree<String, Triangle> internalTree;

    public BoundingBoxTree(List<Triangle> triangles) {
        Map<Triangle, Map<String, DoubleRange>> triangleToBoundingBox = triangles.stream()
                .collect(Collectors.toMap(
                        t -> t,
                        this::createBox
                ));


        internalTree = new IntervalTree<>(evaluationOrder, triangleToBoundingBox);
    }

    private Map<String, DoubleRange> createBox(Triangle triangle) {
        double lx = Double.POSITIVE_INFINITY;
        double ly = Double.POSITIVE_INFINITY;
        double lz = Double.POSITIVE_INFINITY;

        double hx = Double.NEGATIVE_INFINITY;
        double hy = Double.NEGATIVE_INFINITY;
        double hz = Double.NEGATIVE_INFINITY;

        for (Node node : triangle.getNodes()) {
            Coordinate co = node.getCoordinate();
            lx = (lx > co.x()) ? co.x() : lx;
            ly = (ly > co.y()) ? co.y() : ly;
            lz = (lz > co.z()) ? co.z() : lz;


            hx = (hx < co.x()) ? co.x() : hx;
            hy = (hy < co.y()) ? co.y() : hy;
            hz = (hz < co.z()) ? co.z() : hz;
        }

        Map<String, DoubleRange> box = new HashMap<>();
        box.put(X, DoubleRange.of(lx, hx));
        box.put(Y, DoubleRange.of(ly, hy));
        box.put(Z, DoubleRange.of(lz, hz));
        return box;
    }
}
