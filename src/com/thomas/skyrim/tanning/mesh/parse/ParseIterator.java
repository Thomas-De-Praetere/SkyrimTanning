package com.thomas.skyrim.tanning.mesh.parse;

import java.util.function.Predicate;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class ParseIterator {

    private String toParse;
    private int i;

    public ParseIterator(String toParse) {
        this.toParse = toParse;
        i = 0;
    }


    public char getCurrent() {
        return toParse.charAt(i);
    }

    public String move(int amount) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < amount; j++) {
            builder.append(getCurrent());
            i++;
        }
        return builder.toString();
    }

    public String moveUntil(Predicate<Character> border) {
        StringBuilder builder = new StringBuilder();
        while (!border.test(getCurrent())) {
            builder.append(getCurrent());
            i++;
        }
        return builder.toString();
    }

    public String move() {
        return move(1);
    }

    boolean isDone() {
        return i >= toParse.length();
    }

    @Override
    public String toString() {
        return toParse.substring(i);
    }
}
