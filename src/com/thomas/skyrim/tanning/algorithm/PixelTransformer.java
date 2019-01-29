package com.thomas.skyrim.tanning.algorithm;

import com.google.common.collect.ListMultimap;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Tuple;
import com.thomas.skyrim.tanning.mesh.geometric.GeometricLine2D;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PixelTransformer {

    private final ListMultimap<CoveredTriangle, CoveredTriangle> neighbours;
    private final CoveredTriangle triangle;

    private boolean initialised;

    private Tuple center;
    private Tuple A;
    private Tuple B;
    private Tuple C;

    private GeometricLine2D a;
    private GeometricLine2D b;
    private GeometricLine2D c;

    private float g;
    private float ga;
    private float gb;
    private float gc;

    private float da;
    private float db;
    private float dc;

    public PixelTransformer(ListMultimap<CoveredTriangle, CoveredTriangle> neighbours, CoveredTriangle triangle) {
        this.neighbours = neighbours;
        this.triangle = triangle;
        initialised = false;
    }

    public void intialize(int size) {
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

        initialised = true;
    }

    private Optional<Float> getNeighbourValue(Node n2, Node n3, List<CoveredTriangle> coveredTriangles) {
        return coveredTriangles.stream()
                .filter(coveredTriangle -> coveredTriangle.getTriangle().getNodes().containsAll(Arrays.asList(n2, n3)))
                .map(CoveredTriangle::getCovered)
                .map(g1 -> (g1 + g) / 2)
                .findFirst();
    }

    public float getCoveredValue(Tuple location) {
        if (!initialised) throw new IllegalStateException("not initialised");
        float trueGA = getG(location, a, da, ga);
        float trueGB = getG(location, b, db, gb);
        float trueGC = getG(location, c, dc, gc);
        return (trueGA + trueGB + trueGC) / 3;
    }

    private float getG(Tuple location, GeometricLine2D lineToUse, float distToUse, float coverToUse) {
        float distance = (float) lineToUse.distance(location);

        float max = Math.max(coverToUse, g);
        float min = Math.min(coverToUse, g);

        float result = g * (distance / distToUse) + coverToUse * (1 - distance / distToUse);

        return Math.min(max, Math.max(min, result));
    }

    public CoveredTriangle getTriangle() {
        return triangle;
    }
}
