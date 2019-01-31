package com.thomas.skyrim.tanning.algorithm;

import com.google.common.collect.ListMultimap;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Tuple;
import com.thomas.skyrim.tanning.mesh.geometric.GeometricLine2D;
import com.thomas.skyrim.tanning.mesh.geometric.LineCoordinate;

import java.util.*;

public class PixelTransformer {

    private final ListMultimap<CoveredTriangle, CoveredTriangle> neighbours;
    private final CoveredTriangle triangle;

    private boolean initialised;

    private Map<GeometricLine2D, Double> outerLineToCover;
    private Map<GeometricLine2D, Double> innerLineToCover;

    private double centerValue;

    public PixelTransformer(ListMultimap<CoveredTriangle, CoveredTriangle> neighbours, CoveredTriangle triangle) {
        this.neighbours = neighbours;
        this.triangle = triangle;
        initialised = false;
    }

    public void intialize(int size) {
        Tuple A = triangle.getTriangle().getN1().getUv().times(size);
        Tuple B = triangle.getTriangle().getN2().getUv().times(size);
        Tuple C = triangle.getTriangle().getN3().getUv().times(size);

        Tuple center = (A.add(B).add(C)).divide(3.0);

        GeometricLine2D a = new GeometricLine2D(B, C);
        GeometricLine2D b = new GeometricLine2D(C, A);
        GeometricLine2D c = new GeometricLine2D(A, B);

        GeometricLine2D ad = new GeometricLine2D(A, center);
        GeometricLine2D bd = new GeometricLine2D(B, center);
        GeometricLine2D cd = new GeometricLine2D(C, center);

        double g = triangle.getCovered();
        Optional<Double> n2N3Neighbour = getNeighbourValue(g, triangle.getTriangle().getN2(), triangle.getTriangle().getN3(), neighbours.get(triangle));
        Optional<Double> n3N1Neighbour = getNeighbourValue(g, triangle.getTriangle().getN3(), triangle.getTriangle().getN1(), neighbours.get(triangle));
        Optional<Double> n1N2Neighbour = getNeighbourValue(g, triangle.getTriangle().getN1(), triangle.getTriangle().getN2(), neighbours.get(triangle));
        double ga = n2N3Neighbour.orElse(g);
        double gb = n3N1Neighbour.orElse(g);
        double gc = n1N2Neighbour.orElse(g);

        centerValue = (ga + gb + gc) / 3.0;

        outerLineToCover = new HashMap<>();
        outerLineToCover.put(a, ga);
        outerLineToCover.put(b, gb);
        outerLineToCover.put(c, gc);

        innerLineToCover = new HashMap<>();
        innerLineToCover.put(ad, (gb + gc) / 2);
        innerLineToCover.put(bd, (ga + gc) / 2);
        innerLineToCover.put(cd, (ga + gb) / 2);

        initialised = true;
    }

    private Optional<Double> getNeighbourValue(double g, Node n2, Node n3, List<CoveredTriangle> neighbours) {
        return neighbours.stream()
                .filter(coveredTriangle -> containsNodes(n2, n3, coveredTriangle))
                .map(CoveredTriangle::getCovered)
                .map(g1 -> (g1 + g) / 2)
                .findFirst();
    }

    private boolean containsNodes(Node n2, Node n3, CoveredTriangle coveredTriangle) {
        Set<Node> nodes = coveredTriangle.getTriangle().getNodes();
        return nodes.contains(n2) && nodes.contains(n3);
    }

    public double getCoveredValue(Tuple location) {
        if (!initialised) throw new IllegalStateException("not initialised");
        GeometricLine2D nearestInner = getNearest(innerLineToCover.keySet(), location);
        GeometricLine2D nearestOuter = getNearest(outerLineToCover.keySet(), location);
        double distToInner = nearestInner.distance(location);
        double distToOuter = nearestOuter.distance(location);
        double total = distToInner + distToOuter;
        double lambda = distToInner / total;
        return (1.0 - lambda * lambda) * innerLineValue(nearestInner, location)
                + lambda * lambda * outerLineToCover.get(nearestOuter);
    }

    private double innerLineValue(GeometricLine2D nearestInner, Tuple location) {
        LineCoordinate<GeometricLine2D> project = nearestInner.project(location);
        double lambda = project.getLambda();
        return lambda * lambda * centerValue + (1.0 - lambda * lambda) * innerLineToCover.get(nearestInner);
    }

    private GeometricLine2D getNearest(Set<GeometricLine2D> lines, Tuple location) {
        GeometricLine2D closest = lines.stream().findAny().orElseThrow(IllegalStateException::new);
        double dist = Double.POSITIVE_INFINITY;
        for (GeometricLine2D line : lines) {
            double distance = line.distance(location);
            if (dist > distance) {
                dist = distance;
                closest = line;
            }
        }
        return closest;
    }

    public CoveredTriangle getTriangle() {
        return triangle;
    }

}
