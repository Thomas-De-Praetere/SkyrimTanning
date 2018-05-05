package com.thomas.skyrim.tanning.mesh.data;

/**
 * This class was created by thoma on 05-May-18.
 */
public interface NumberLike<T extends NumberLike<?>> {
    T add(T t);
    T subtract(T t);
    T times(double t);
    T divide(double t);
}
