package com.thomas.skyrim.tanning.util.intervalTree;

import com.thomas.skyrim.tanning.util.Pair;

/**
 *
 */
public class DoubleRange extends Pair<Double, Double> {
    private DoubleRange(Double first, Double second) {
        super(first, second);
    }

    public double getStart() {
        return getFirst();
    }

    public double getEnd() {
        return getSecond();
    }

    /**
     * Returns the mathematical length enclosed by this interval. E.g. [0, 2] has length 2 (but contains 3 elements)
     *
     * @return length of the Range, for a Range [a, b]: |b - a|
     */
    public double getLength() {
        return Math.abs(getEnd() - getStart());
    }

    public boolean isEmpty() {
        return getStart() == getEnd();
    }

    public boolean isAscending() {
        return getStart() <= getEnd();
    }

    /**
     * @return a new range with the opposite orientation
     */
    public DoubleRange reverse() {
        return new DoubleRange(getEnd(), getStart());
    }

    /**
     * @param translation the size of the translation
     * @return a new translated range
     */
    public DoubleRange translate(double translation) {
        return new DoubleRange(getStart() + translation, getEnd() + translation);
    }

    public boolean contains(double number) {
        return (isAscending()) ? getStart() <= number && number <= getEnd() : getEnd() <= number && number <= getStart();
    }

    public static DoubleRange of(Double first, Double second) {
        return new DoubleRange(first, second);
    }

}
