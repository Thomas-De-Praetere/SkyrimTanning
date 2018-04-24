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

	public String getContent() {
		return toParse.substring(i);
	}

	public char getCurrent() {
		return toParse.charAt(i);
	}

	public void move(int amount) {
		i += amount;
	}

	public void moveUntill(Predicate<Character> border) {
		while (!border.test(getCurrent())) {
			move();
		}
	}

	public void move() {
		move(1);
	}

	boolean isDone() {
		return i >= toParse.length();
	}
}
