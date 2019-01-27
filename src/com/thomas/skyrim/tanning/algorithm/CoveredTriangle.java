package com.thomas.skyrim.tanning.algorithm;

import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.data.Tuple;

import java.awt.*;

/**
 *
 */
public class CoveredTriangle {
    //TODO -make this remember it's neighbours
    //TODO -make this paint itself with a gradient. Starting from it's covered value in the center
    //TODO  to the mean covered value at the edges with the neighbours.
    private final Triangle triangle;
    private final float covered;

    public CoveredTriangle(Triangle triangle, float covered) {
        this.triangle = triangle;
        this.covered = covered;
    }

    public void write(Graphics2D graphics, int size) {
        graphics.setColor(new Color(covered, covered, covered));
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

    public float getCovered() {
        return covered;
    }

    public boolean isCovered() {
        return covered < 1.0;
    }
}
