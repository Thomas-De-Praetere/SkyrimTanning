package com.thomas.skyrim.tanning.mesh.project;

import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.geometric.GeometricTriangle;
import com.thomas.skyrim.tanning.mesh.geometric.TriangleCoordinate;
import com.thomas.skyrim.tanning.mesh.project.results.ProjectedNode;

import java.util.HashMap;
import java.util.Map;

/**
 * This class was created by thoma on 21-May-18.
 */
public class NodeProjector {
    private final Mesh base;

    public NodeProjector(Mesh base) {
        this.base = base;
    }

    public Map<Node, ProjectedNode> projectNodes(Mesh mesh) {
        Map<Node, ProjectedNode> map = new HashMap<>();
        for (Node node : mesh.getNodes()) {
            TriangleCoordinate closest = null;
            double dist = Double.POSITIVE_INFINITY;
            for (Triangle triangle : base.getTriangles()) {
                TriangleCoordinate plane = GeometricTriangle.of(triangle).projectPoint(node.getCoordinate());
                if (!plane.inTriangle()) continue;
                double distance = plane.toCoordinate().distance(node.getCoordinate());
                if (dist > distance) {
                    dist = distance;
                    closest = plane;
                }
            }
            if (closest != null) {
                map.put(node, new ProjectedNode(node, closest));
            }
        }
        return map;
    }

}
