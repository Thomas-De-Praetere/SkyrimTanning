package com.thomas.skyrim.tanning.mesh.project;

import com.thomas.skyrim.tanning.mesh.data.Edge;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.geometric.PlaneCoordinate;

import java.util.Map;

/**
 * This class was created by thoma on 21-May-18.
 */
public class ProjectedFace {
    private final Triangle origin;
    private final Map<Edge, ProjectedEdge> edgeToProjection;
    private final Map<Node, PlaneCoordinate> nodeToProjection;

    public ProjectedFace(Triangle origin, Map<Edge, ProjectedEdge> edgeToProjection, Map<Node, PlaneCoordinate> nodeToProjection) {
        this.origin = origin;
        this.edgeToProjection = edgeToProjection;
        this.nodeToProjection = nodeToProjection;
    }

    public Triangle getOrigin() {
        return origin;
    }

    public Map<Edge, ProjectedEdge> getEdgeToProjection() {
        return edgeToProjection;
    }

    public Map<Node, PlaneCoordinate> getNodeToProjection() {
        return nodeToProjection;
    }
}
