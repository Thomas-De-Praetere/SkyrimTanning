package com.thomas.skyrim.tanning.mesh.geometric;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;

/**
 * This class was created by thoma on 09-May-18.
 */
public class LineCoordinate<T> {
    private final double lambda;
    private final T line;

    public LineCoordinate(double lambda, T line) {
        this.lambda = lambda;
        this.line = line;
    }

    public double getLambda() {
        return lambda;
    }

    public boolean onLine() {
        return 0 <= lambda && lambda <= 1;
    }

    public boolean hasPositiveDirection() {
        return 0 <= lambda;
    }

    public T getLine() {
        return line;
    }

}
