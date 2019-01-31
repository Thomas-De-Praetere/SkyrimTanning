package com.thomas.skyrim.tanning.util;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
