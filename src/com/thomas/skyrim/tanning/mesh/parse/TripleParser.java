package com.thomas.skyrim.tanning.mesh.parse;

import com.thomas.skyrim.tanning.mesh.data.Triple;

import java.util.List;
import java.util.Optional;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class TripleParser<T> implements Parser<Triple<T>> {

    private final Parser<T> parser;

    public TripleParser(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    public Optional<Triple<T>> parse(ParseIterator iterator) {
        Parser<List<T>> listParser = new ListParser<>(parser);
        Optional<List<T>> parse = listParser.parse(iterator);
        if (parse.isPresent()) {
            List<T> doubles = parse.get();
            if (doubles.size() == 3) {
                return Optional.of(new Triple<>(doubles.get(0), doubles.get(1), doubles.get(2)));
            }
        }
        return Optional.empty();
    }

    public static TripleParser<Double> getDoubleParser() {
        return new TripleParser<>(new DoubleParser());
    }

    public static TripleParser<Integer> getIntParser() {
        return new TripleParser<>(new IntParser());
    }
}
