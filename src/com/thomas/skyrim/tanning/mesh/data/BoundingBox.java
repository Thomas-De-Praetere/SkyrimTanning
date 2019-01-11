package com.thomas.skyrim.tanning.mesh.data;

/**
 *
 */
public class BoundingBox {
    private final Coordinate lower;
    private final Coordinate upper;

    public BoundingBox(Coordinate lower, Coordinate upper) {
        if (!(lower.x <= upper.x && lower.y <= upper.y && lower.z <= upper.z)) {
            throw new IllegalStateException("Bounding box coordinates are not in the correct order.");
        }
        this.lower = lower;
        this.upper = upper;
    }

    public Coordinate getLower() {
        return lower;
    }

    public Coordinate getUpper() {
        return upper;
    }
}
