package com.thomas.skyrim.tanning.mesh.geometric;

/**
 * This class was created by thoma on 03-Jun-18.
 */
public class EdgeCoordinate extends LineCoordinate {
    private final GeometricEdge origin;

    public EdgeCoordinate(double lambda, GeometricEdge line) {
        super(lambda, line);
        this.origin = line;
    }

    @Override
    public GeometricEdge getLine() {
        return origin;
    }
}
