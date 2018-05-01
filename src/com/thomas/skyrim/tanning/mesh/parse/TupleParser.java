package com.thomas.skyrim.tanning.mesh.parse;

import com.thomas.skyrim.tanning.mesh.data.Tuple;

import java.util.List;
import java.util.Optional;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class TupleParser implements Parser<Tuple> {

    @Override
    public Optional<Tuple> parse(ParseIterator iterator) {
        Parser<List<Double>> parser = new ListParser<>(new DoubleParser());
        Optional<List<Double>> parse = parser.parse(iterator);
        if (parse.isPresent()) {
            List<Double> doubles = parse.get();
            if (doubles.size() == 2) {
                return Optional.of(new Tuple(doubles.get(0), doubles.get(1)));
            }
        }
        return Optional.empty();
    }
}
