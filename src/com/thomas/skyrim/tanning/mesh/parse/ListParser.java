package com.thomas.skyrim.tanning.mesh.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class ListParser<T> implements Parser<List<T>> {

	private final Parser<T> parser;

	public ListParser(Parser<T> parser) {
		this.parser = parser;
	}

	@Override
	public Optional<List<T>> parse(ParseIterator iterator) {
		List<T> list = new ArrayList<>();
		if (iterator.getCurrent() != '[') return Optional.of(list);
		iterator.move();
		while (iterator.getCurrent() != ']') {
			parser.parse(iterator).ifPresent(list::add);
			Predicate<Character> pred = (n-> n ==','|| n ==' ');
			iterator.moveUntill(pred.negate());
		}
		iterator.move();
		return Optional.of(list);
	}
}
