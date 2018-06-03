package com.thomas.skyrim.tanning.mesh.project;

import com.thomas.skyrim.tanning.mesh.data.Edge;
import com.thomas.skyrim.tanning.mesh.geometric.EdgeCoordinate;
import com.thomas.skyrim.tanning.mesh.geometric.LineCoordinate;
import com.thomas.skyrim.tanning.mesh.geometric.PlaneCoordinate;

import java.util.List;

/**
 * This class was created by thoma on 21-May-18.
 */
public class ProjectedEdge {
    private final Edge origin;
    private final PlaneCoordinate start;
    private final List<EdgeCoordinate> points;
    private final PlaneCoordinate end;

    public ProjectedEdge(Edge origin, PlaneCoordinate start, List<EdgeCoordinate> points, PlaneCoordinate end) {
        this.origin = origin;
        this.start = start;
        this.points = points;
        this.end = end;
    }

    public Edge getOrigin() {
        return origin;
    }

    public PlaneCoordinate getStart() {
        return start;
    }

    public List<EdgeCoordinate> getPoints() {
        return points;
    }

    public PlaneCoordinate getEnd() {
        return end;
    }
}
