package com.thomas.skyrim.tanning.mesh.load;

import com.thomas.skyrim.tanning.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class was created by thoma on 30-Apr-18.
 */
public class SinglePair<U> extends Pair<U, U> {

    protected SinglePair(U u, U v) {
        super(u, v);
    }


    public List<U> asList() {
        return Arrays.asList(getU(), getV());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SinglePair<?> pair = (SinglePair<?>) o;
        return (Objects.equals(getU(), pair.getU()) && Objects.equals(getV(), pair.getV()))
                || (Objects.equals(getV(), pair.getU()) && Objects.equals(getU(), pair.getV()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getU(), getV()) + Objects.hash(getV(), getU());
    }

    public static <U> SinglePair<U> singleOf(U u, U v) {
        return new SinglePair<>(u, v);
    }
}
