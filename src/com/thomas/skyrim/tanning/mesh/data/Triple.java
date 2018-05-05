package com.thomas.skyrim.tanning.mesh.data;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class Triple<T> {

    protected final T x;
    protected final T y;
    protected final T z;

    public Triple(T x, T y, T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public T x() {
        return x;
    }

    public T y() {
        return y;
    }

    public T z() {
        return z;
    }

}
