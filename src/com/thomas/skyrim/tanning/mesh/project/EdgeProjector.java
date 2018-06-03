package com.thomas.skyrim.tanning.mesh.project;

import com.thomas.skyrim.tanning.mesh.data.*;
import com.thomas.skyrim.tanning.mesh.geometric.EdgeCoordinate;
import com.thomas.skyrim.tanning.mesh.geometric.GeometricPlane;
import com.thomas.skyrim.tanning.mesh.geometric.PlaneCoordinate;
import com.thomas.skyrim.tanning.mesh.geometric.TriangleCoordinate;

import java.util.*;

/**
 * This class was created by thoma on 21-May-18.
 */
public class EdgeProjector {
    private final Mesh base;

    public EdgeProjector(Mesh base) {
        this.base = base;
    }

    public Map<Edge, ProjectedEdge> projectEdges(Mesh mesh, Map<Node, TriangleCoordinate> nodePlaneCoordinateMap) {
        Map<Edge, ProjectedEdge> edgeMap = new HashMap<>();
        for (Edge edge : mesh.getEdges()) {
            ProjectedEdge projectedEdge = projectEdge(edge, nodePlaneCoordinateMap);
            edgeMap.put(edge, projectedEdge);
        }
        return edgeMap;
    }

    private ProjectedEdge projectEdge(Edge edge, Map<Node, TriangleCoordinate> nodePlaneCoordinateMap) {
        TriangleCoordinate start = nodePlaneCoordinateMap.get(edge.getStart());
        TriangleCoordinate end = nodePlaneCoordinateMap.get(edge.getEnd());

        Coordinate meanProjectionVector = getMeanProjectionVector(start, end, edge);
        GeometricPlane intersectionPlane = GeometricPlane.of(start.toCoordinate(), end.toCoordinate(), start.toCoordinate().subtract(meanProjectionVector));

        List<EdgeCoordinate> projectedCoordinates = walkEdge(start, end, intersectionPlane);

        return new ProjectedEdge(edge, start, projectedCoordinates, end);
    }

    private List<EdgeCoordinate> walkEdge(TriangleCoordinate start, TriangleCoordinate end, GeometricPlane intersectionPlane) {
        if (start.getPlane().getOrigin().equals(end.getPlane().getOrigin())) return Collections.emptyList();

        List<EdgeCoordinate> coordinates = new ArrayList<>();

        Triangle currentTriangle = start.getPlane().getOrigin();
        EdgeCoordinate currentProjection = findStartProjection(start, end, intersectionPlane);
        coordinates.add(currentProjection);
        while (!currentProjection.getLine().getOrigin().getTriangles().contains(end.getPlane().getOrigin())) {
            Optional<Triangle> other = getOtherTriangle(currentTriangle, currentProjection);
            if (!other.isPresent()) break;
            currentTriangle = other.get();
            currentProjection = findNextProjection(currentProjection, currentTriangle, intersectionPlane);
            coordinates.add(currentProjection);
        }
        return coordinates;
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
