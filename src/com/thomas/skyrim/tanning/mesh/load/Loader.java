package com.thomas.skyrim.tanning.mesh.load;

import com.google.common.base.Preconditions;
import com.thomas.skyrim.tanning.mesh.data.*;
import com.thomas.skyrim.tanning.mesh.parse.*;
import com.thomas.skyrim.tanning.util.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
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
            String name = iterator.getContent().split("::", 2)[0];
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
            List<Mesh> meshes = new ArrayList<>();
            for (Info info : infoList) {
                List<Node> nodes = createNodes(info);
                List<Triangle> triangles = createTriangles(info, nodes);
                List<Edge> edges = createEdges(info, nodes, triangles);
            }
            return meshes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Edge> createEdges(Info info, List<Node> nodes, List<Triangle> triangles) {
        List<Edge> edges = new ArrayList<>();
        Map<Pair<Node>, Edge> nodeToEdge = new HashMap<>();
        for (Triangle triangle : triangles) {

        }
    }

    private List<Triangle> createTriangles(Info info, List<Node> nodes) {
        List<Triangle> triangles = new ArrayList<>();
        for (Triple<Integer> triple : info.triangles) {
            Node node1 = nodes.get(triple.getX());
            Node node2 = nodes.get(triple.getY());
            Node node3 = nodes.get(triple.getZ());

            Node[] triNodes = {node1, node2, node3};

            Triangle triangle = new Triangle(triNodes);
            triangles.add(triangle);

            Arrays.stream(triNodes).forEach(n -> n.getTriangles().add(triangle));
        }
        return triangles;
    }


    private List<Node> createNodes(Info info) {
        Preconditions.checkArgument(info.nodes.size() == info.uvs.size());
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < info.nodes.size(); i++) {
            Triple<Double> coordinate = info.nodes.get(i);
            Tuple uv = info.uvs.get(i);
            nodes.add(new Node(new Coordinate(coordinate.getX(), coordinate.getY(), coordinate.getZ()), uv));
        }
        return nodes;
    }
}
