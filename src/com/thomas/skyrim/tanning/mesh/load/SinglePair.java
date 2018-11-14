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
        return Arrays.asList(getFirst(), getSecond());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SinglePair<?> pair = (SinglePair<?>) o;
        return (Objects.equals(getFirst(), pair.getFirst()) && Objects.equals(getSecond(), pair.getSecond()))
                || (Objects.equals(getSecond(), pair.getFirst()) && Objects.equals(getFirst(), pair.getSecond()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst(), getSecond()) + Objects.hash(getSecond(), getFirst());
    }

    public static <U> SinglePair<U> singleOf(U u, U v) {
        return new SinglePair<>(u, v);
    }
}
