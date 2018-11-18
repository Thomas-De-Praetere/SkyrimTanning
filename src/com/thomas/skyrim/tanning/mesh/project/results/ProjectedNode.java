package com.thomas.skyrim.tanning.mesh.project.results;

import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.geometric.TriangleCoordinate;

/**
 *
 */
public class ProjectedNode {
    private final Node node;
    private final TriangleCoordinate projectedCoordinate;

    public ProjectedNode(Node node, TriangleCoordinate projectedCoordinate) {
        this.node = node;
        this.projectedCoordinate = projectedCoordinate;
    }

    public Node getNode() {
        return node;
    }

    public TriangleCoordinate getProjectedCoordinate() {
        return projectedCoordinate;
    }
}
