package com.thomas.skyrim.tanning.util;

/**
 * This class was created by thoma on 05-May-18.
 */
public class Pair<U, V> {
    private final U u;
    private final V v;

    protected Pair(U u, V v) {
        this.u = u;
        this.v = v;
    }

    public U getU() {
        return u;
    }

    public V getV() {
        return v;
    }

    public static <U, V> Pair<U, V> of(U u, V v) {
        return new Pair<>(u, v);
    }
}
