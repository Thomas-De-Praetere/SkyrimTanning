package com.thomas.skyrim.tanning.algorithm;

import com.google.common.collect.ListMultimap;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Tuple;
import com.thomas.skyrim.tanning.mesh.geometric.GeometricLine2D;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PixelTransformer {

    private final Tuple center;
    private final Tuple A;
    private final Tuple B;
    private final Tuple C;

    private final GeometricLine2D a;
    private final GeometricLine2D b;
    private final GeometricLine2D c;

    private final float g;
    private final float ga;
    private final float gb;
    private final float gc;

    private final float da;
    private final float db;
    private final float dc;

    public PixelTransformer(int size, ListMultimap<CoveredTriangle, CoveredTriangle> neighbours, CoveredTriangle triangle) {
        A = triangle.getTriangle().getN1().getUv().times(size);
        B = triangle.getTriangle().getN2().getUv().times(size);
        C = triangle.getTriangle().getN3().getUv().times(size);

        center = (A.add(B).add(C)).divide(3.0);

        a = new GeometricLine2D(B, C);
        b = new GeometricLine2D(C, A);
        c = new GeometricLine2D(A, B);

        da = (float) a.distance(center);
        db = (float) b.distance(center);
        dc = (float) c.distance(center);

        g = triangle.getCovered();
        ga = getNeighbourValue(triangle.getTriangle().getN2(), triangle.getTriangle().getN3(), neighbours.get(triangle)).orElse(g);
        gb = getNeighbourValue(triangle.getTriangle().getN3(), triangle.getTriangle().getN1(), neighbours.get(triangle)).orElse(g);
        gc = getNeighbourValue(triangle.getTriangle().getN1(), triangle.getTriangle().getN2(), neighbours.get(triangle)).orElse(g);
    }

    private Optional<Float> getNeighbourValue(Node n2, Node n3, List<CoveredTriangle> coveredTriangles) {
        return coveredTriangles.stream()
                .filter(coveredTriangle -> coveredTriangle.getTriangle().getNodes().containsAll(Arrays.asList(n2, n3)))
                .map(CoveredTriangle::getCovered)
                .findFirst();
    }

    public float getCoveredValue(Tuple location) {
        float trueGA = getG(location, a, da, ga);
        float trueGB = getG(location, b, db, gb);
        float trueGC = getG(location, c, dc, gc);
        return (trueGA + trueGB + trueGC) / 3;
    }

    private float getG(Tuple location, GeometricLine2D lineToUse, float distToUse, float coverToUse) {
        float distance = (float) lineToUse.distance(location);
        return g * (1 - distance / distToUse) + coverToUse * (distance / distToUse);
    }

}
