package com.thomas.skyrim.tanning.mesh.project;

import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.geometric.GeometricTriangle;
import com.thomas.skyrim.tanning.mesh.geometric.PlaneCoordinate;

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

    public Map<Node, PlaneCoordinate> projectNodes(Mesh mesh) {
        Map<Node, PlaneCoordinate> map = new HashMap<>();
        for (Node node : mesh.getNodes()) {
            PlaneCoordinate closest = null;
            double dist = Double.POSITIVE_INFINITY;
            for (Triangle triangle : base.getTriangles()) {
                PlaneCoordinate plane = GeometricTriangle.of(triangle).projectPoint(node.getCoordinate());
                if (!plane.inTriangle()) continue;
                double distance = plane.toCoordinate().distance(node.getCoordinate());
                if (dist > distance) {
                    dist = distance;
                    closest = plane;
                }
            }
            if (closest != null) {
                map.put(node, closest);
            }
        }
        return map;
    }

}
