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
            String name = iterator.moveUntil(c -> c == ':');
            iterator.move(2);
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
            System.out.println("Running script.");

            Process p = Runtime.getRuntime().exec("python Skyrim.py -i " + location + ((isBody) ? "" : " -e"));
            String result = new BufferedReader(new InputStreamReader(p.getInputStream())).lines().collect(Collectors.joining("\n"));
            String[] split = result.split("::!::");

            System.out.println(String.format("Received %d meshes.", split.length));

            List<Info> infoList = new ArrayList<>();
            for (String s : split) {
                new InfoParser().parse(new ParseIterator(s)).ifPresent(infoList::add);
            }
            List<Mesh> meshes = new ArrayList<>();
            for (Info info : infoList) {
                List<Node> nodes = createNodes(info);
                List<Triangle> triangles = createTriangles(info, nodes);
                List<Edge> edges = createEdges(triangles);
                meshes.add(new Mesh(info.name, triangles, nodes, edges));
            }
            return meshes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Edge> createEdges(List<Triangle> triangles) {
        List<Edge> edges = new ArrayList<>();
        Map<Pair<Node>, Edge> nodeToEdge = new HashMap<>();
        for (Triangle triangle : triangles) {
            List<Node> triNodes = triangle.getNodes();

            List<Edge> triEdges = new ArrayList<>(3);

            List<int[]> list = Arrays.asList(new int[]{0, 1}, new int[]{1, 2}, new int[]{2, 0});
            for (int[] ints : list) {
                Pair<Node> pair = Pair.of(triNodes.get(ints[0]), triNodes.get(ints[1]));
                Edge edge = getEdge(pair, edges, nodeToEdge);

                triEdges.add(edge);
            }
            for (Edge edge : triEdges) {
                edge.getTriangles().add(triangle);
                for (Node node : edge.getNodes()) {
                    node.getEdges().add(edge);
                }
            }
            triangle.getEdges().addAll(triEdges);
        }
        return edges;
    }

    private Edge getEdge(Pair<Node> pair, List<Edge> edges, Map<Pair<Node>, Edge> nodeToEdge) {
        if (nodeToEdge.containsKey(pair)) return nodeToEdge.get(pair);
        Edge edge = new Edge(pair.asList());
        edges.add(edge);
        nodeToEdge.put(pair, edge);
        return edge;
    }

    private List<Triangle> createTriangles(Info info, List<Node> nodes) {
        List<Triangle> triangles = new ArrayList<>();
        for (Triple<Integer> triple : info.triangles) {
            Node node1 = nodes.get(triple.getX());
            Node node2 = nodes.get(triple.getY());
            Node node3 = nodes.get(triple.getZ());

            List<Node> triNodes = Arrays.asList(node1, node2, node3);

            Triangle triangle = new Triangle(triNodes);
            triangles.add(triangle);

            triNodes.forEach(n -> n.getTriangles().add(triangle));
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
