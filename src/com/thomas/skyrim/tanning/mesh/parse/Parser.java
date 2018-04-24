package com.thomas.skyrim.tanning.mesh.parse;

import java.util.Optional;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public interface Parser<T> {

	Optional<T> parse(ParseIterator iterator);
}
