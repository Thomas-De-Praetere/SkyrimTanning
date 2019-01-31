package com.thomas.skyrim.tanning.algorithm;

import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.data.Tuple;

import java.awt.*;


/**
 *
 */
public class CoveredTriangle {
    private final Triangle triangle;
    private final double covered;

    public CoveredTriangle(Triangle triangle, double covered) {
        this.triangle = triangle;
        this.covered = covered;
    }

    public void write(Graphics2D graphics, int size) {
        graphics.setColor(new Color((float) covered, (float) covered, (float) covered));
        Polygon polygon = new Polygon();
        for (Node node : triangle.getNodes()) {
            Tuple uv = node.getUv();
            polygon.addPoint((int) Math.round(uv.u() * (double) size), (int) Math.round(uv.v() * (double) size));
        }
        graphics.fillPolygon(polygon);
    }

    public Triangle getTriangle() {
        return triangle;
    }

    public double getCovered() {
        return covered;
    }

    public boolean isCovered() {
        return covered < 1.0;
    }

}
