package com.thomas.skyrim.tanning.mesh.project;

import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Edge;
import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.geometric.PlaneCoordinate;

import java.util.HashMap;
import java.util.Map;

/**
 * This class was created by thoma on 21-May-18.
 */
public class EdgeProjector {
    private final Mesh base;

    public EdgeProjector(Mesh base) {
        this.base = base;
    }

    public Map<Edge, ProjectedEdge> projectEdges(Mesh mesh, Map<Node, PlaneCoordinate> nodePlaneCoordinateMap) {
        Map<Edge, ProjectedEdge> edgeMap = new HashMap<>();
        for (Edge edge : mesh.getEdges()) {
            ProjectedEdge projectedEdge = projectEdge(edge, nodePlaneCoordinateMap);
            edgeMap.put(edge, projectedEdge);
        }
        return edgeMap;
    }

    private ProjectedEdge projectEdge(Edge edge, Map<Node, PlaneCoordinate> nodePlaneCoordinateMap) {
        PlaneCoordinate start = nodePlaneCoordinateMap.get(edge.getStart());
        PlaneCoordinate end = nodePlaneCoordinateMap.get(edge.getEnd());

        Coordinate meanProjectionVector = getMeanProjectionVector(start, end, edge);
    }

    private Coordinate getMeanProjectionVector(PlaneCoordinate start, PlaneCoordinate end, Edge edge) {
        Coordinate startProjection = edge.getStart().getCoordinate().subtract(start.toCoordinate());
        Coordinate endProjection = edge.getEnd().getCoordinate().subtract(end.toCoordinate());
        return new Coordinate(
                (startProjection.x() + endProjection.x()) / 2,
                (startProjection.y() + endProjection.y()) / 2,
                (startProjection.z() + endProjection.z()) / 2
        );
    }

}
