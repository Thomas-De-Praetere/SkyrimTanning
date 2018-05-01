package com.thomas.skyrim.tanning.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class was created by thoma on 30-Apr-18.
 */
public class Pair<U> {
    private final U u;
    private final U v;

    Pair(U u, U v) {
        this.u = u;
        this.v = v;
    }

    public U getU() {
        return u;
    }

    public U getV() {
        return v;
    }

    public List<U> asList() {
        return Arrays.asList(u, v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?> pair = (Pair<?>) o;
        return (Objects.equals(u, pair.u) && Objects.equals(v, pair.v))
                || (Objects.equals(v, pair.u) && Objects.equals(u, pair.v));
    }

    @Override
    public int hashCode() {
        return Objects.hash(u, v) + Objects.hash(v, u);
    }

    public static <U> Pair<U> of(U u, U v) {
        return new Pair<>(u, v);
    }
}