package com.thomas.skyrim.tanning.algorithm;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.thomas.skyrim.tanning.algorithm.writer.CoveredTriangleWriter;
import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.data.Tuple;
import com.thomas.skyrim.tanning.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PixelTransformerTest {

    private double maxSize = 4;

    @Test
    public void printTest() throws IOException {
        CoveredTriangle centerTriangle = new CoveredTriangle(
                new Triangle(createNode(1, 1), createNode(2, 1), createNode(1, 2)),
                0.5f
        );
        CoveredTriangle longEdgeNeighbour = new CoveredTriangle(
                new Triangle(createNode(2, 1), createNode(1, 2), createNode(2, 2)),
                0.75f
        );
        CoveredTriangle standingEdge = new CoveredTriangle(
                new Triangle(createNode(1, 1), createNode(2, 1), createNode(1, 0)),
                0.25f
        );

        ListMultimap<CoveredTriangle, CoveredTriangle> neighbours = ArrayListMultimap.create();
        neighbours.put(centerTriangle, longEdgeNeighbour);
        neighbours.put(centerTriangle, standingEdge);
        neighbours.put(standingEdge, centerTriangle);
        neighbours.put(longEdgeNeighbour, centerTriangle);

        PixelTransformer centerTransformer = new PixelTransformer(neighbours, centerTriangle);
        PixelTransformer longTransformer = new PixelTransformer(neighbours, longEdgeNeighbour);
        PixelTransformer standingTransformer = new PixelTransformer(neighbours, standingEdge);

        CoveredTriangleWriter writer = new CoveredTriangleWriter("C:\\Users\\thoma\\Documents\\GitHub\\Self\\Test\\Test.nif", "C:\\Users\\thoma\\Documents\\GitHub\\Self\\Test");
        writer.write(Arrays.asList(centerTransformer, longTransformer, standingTransformer));
    }

    private Map<Pair<Double, Double>, Node> mapToNode;

    @Before
    public void before() {
        mapToNode = new HashMap<>();
    }

    private Node createNode(double x, double y) {
        Pair<Double, Double> pair = Pair.of(x, y);
        if (!mapToNode.containsKey(pair)) {
            Coordinate c = new Coordinate(x, y, 0.0);
            Tuple uv = new Tuple(x / maxSize, y / maxSize);
            Node node = new Node(c, uv);
            mapToNode.put(pair, node);
        }
        return mapToNode.get(pair);
    }
}