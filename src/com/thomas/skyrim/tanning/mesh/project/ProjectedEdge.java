package com.thomas.skyrim.tanning.mesh.project;

import com.thomas.skyrim.tanning.mesh.data.Edge;
import com.thomas.skyrim.tanning.mesh.geometric.LineCoordinate;
import com.thomas.skyrim.tanning.mesh.geometric.PlaneCoordinate;

import java.util.List;

/**
 * This class was created by thoma on 21-May-18.
 */
public class ProjectedEdge {
    private final Edge origin;
    private final PlaneCoordinate start;
    private final List<LineCoordinate> points;
    private final PlaneCoordinate end;

    public ProjectedEdge(Edge origin, PlaneCoordinate start, List<LineCoordinate> points, PlaneCoordinate end) {
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

    public List<LineCoordinate> getPoints() {
        return points;
    }

    public PlaneCoordinate getEnd() {
        return end;
    }
}
