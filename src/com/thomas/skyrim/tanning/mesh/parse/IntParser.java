package com.thomas.skyrim.tanning.mesh.parse;

import java.util.Optional;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class IntParser implements Parser<Integer> {

	@Override
	public Optional<Integer> parse(ParseIterator iterator) {
		String content = iterator.getContent();
		String s = content.split(",", 1)[0];
		try {
			int v = Integer.parseInt(s);
			return Optional.of(v);
		} catch (Exception e) {
			return Optional.empty();
		} finally {
			iterator.move(s.length());
		}
	}
}
