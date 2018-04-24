package com.thomas.skyrim.tanning.mesh.load;

import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Triple;
import com.thomas.skyrim.tanning.mesh.data.Tuple;
import com.thomas.skyrim.tanning.mesh.parse.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class Loader {

	private static class Info {

		private final String name;
		private final List<Triple<Double>> nodes;
		private final List<Triple<Integer>> triangles;
		private final List<Tuple> uvs;

		public Info(String name, List<Triple<Double>> nodes, List<Triple<Integer>> triangles, List<Tuple> uvs) {
			this.name = name;
			this.nodes = nodes;
			this.triangles = triangles;
			this.uvs = uvs;
		}
	}

	private static class InfoParser implements Parser<Info> {

		@Override
		public Optional<Info> parse(ParseIterator iterator) {
			String name = iterator.getContent().split("::", 1)[0];
			iterator.move(name.length() + 2);
			List<Triple<Double>> nodes = new ListParser<>(TripleParser.getDoubleParser()).parse(iterator).orElse(new ArrayList<>());
			iterator.move(2);
			List<Triple<Integer>> triangles = new ListParser<>(TripleParser.getIntParser()).parse(iterator).orElse(new ArrayList<>());
			iterator.move(2);
			List<Tuple> uvs = new ListParser<>(new TupleParser()).parse(iterator).orElse(new ArrayList<>());
			return Optional.of(new Info(name, nodes, triangles, uvs));
		}
	}

	public List<Mesh> load(String location, boolean isBody) {
		try {
			Process p = Runtime.getRuntime().exec("python Skyrim.py -i " + location + ((isBody) ? "" : " -e"));
			String result = new BufferedReader(new InputStreamReader(p.getInputStream())).lines().collect(Collectors.joining("\n"));
			String[] split = result.split("::!::");
			List<Info> infoList = new ArrayList<>();
			for (String s : split) {
				new InfoParser().parse(new ParseIterator(s)).ifPresent(infoList::add);
			}
			return new ArrayList<>();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
