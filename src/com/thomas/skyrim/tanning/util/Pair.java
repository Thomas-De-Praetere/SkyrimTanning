package com.thomas.skyrim.tanning.util;

/**
 * This class was created by thoma on 05-May-18.
 */
public class Pair<U, V> {
    private final U first;
    private final V second;

    protected Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public static <U, V> Pair<U, V> of(U u, V v) {
        return new Pair<>(u, v);
    }
}
