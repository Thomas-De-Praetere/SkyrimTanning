package com.thomas.skyrim.tanning.mesh.parse;

import java.util.Optional;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class DoubleParser implements Parser<Double> {

    @Override
    public Optional<Double> parse(ParseIterator iterator) {
        String content = iterator.getContent();
        String s = content.split("[, \\]]", 2)[0];
        try {
            double v = Double.parseDouble(s);
            return Optional.of(v);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            iterator.move(s.length());
        }
    }
}
