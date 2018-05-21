package com.thomas.skyrim.tanning;

import com.thomas.skyrim.tanning.mesh.data.Edge;
import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.geometric.PlaneCoordinate;
import com.thomas.skyrim.tanning.mesh.load.Loader;
import com.thomas.skyrim.tanning.mesh.project.EdgeProjector;
import com.thomas.skyrim.tanning.mesh.project.NodeProjector;
import com.thomas.skyrim.tanning.mesh.project.ProjectedEdge;
import com.thomas.skyrim.tanning.mesh.project.ProjectedTriangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class was created by thoma on 01-May-18.
 */
public class Algorithm {
    private final String baseBodyLocation;
    private final String outputLocation;
    private final Loader loader;

    public Algorithm(String baseBodyLocation, String outputLocation) {
        this.baseBodyLocation = baseBodyLocation;
        this.outputLocation = outputLocation;
        this.loader = new Loader();
    }

    public void execute(List<String> armourLocations) {
        Mesh baseBody = loader.load(baseBodyLocation, true).get(0);
        armourLocations.parallelStream().forEach(l -> project(l, baseBody));
    }

    private void project(String location, Mesh base) {
        List<Mesh> load = loader.load(location, false);
        for (Mesh mesh : load) {
            Map<Triangle, ProjectedTriangle> projectedTriangles = projectMesh(base, mesh);
        }
    }

    private Map<Triangle, ProjectedTriangle> projectMesh(Mesh base, Mesh mesh) {
        NodeProjector nodeProjector = new NodeProjector(base);
        Map<Node, PlaneCoordinate> nodePlaneCoordinateMap = nodeProjector.projectNodes(mesh);
        EdgeProjector edgeProjector = new EdgeProjector(base);
        Map<Edge, ProjectedEdge> edgeToProjection = edgeProjector.projectEdges(mesh, nodePlaneCoordinateMap);
        Map<Triangle, ProjectedTriangle> projectedTriangles = new HashMap<>();
        for (Triangle triangle : mesh.getTriangles()) {
            ProjectedTriangle projection = projectTriangle(triangle, nodePlaneCoordinateMap, edgeToProjection);
            projectedTriangles.put(triangle, projection);
        }
        return projectedTriangles;
    }

    private ProjectedTriangle projectTriangle(Triangle triangle, Map<Node, PlaneCoordinate> nodePlaneCoordinateMap, Map<Edge, ProjectedEdge> edgeToProjection) {
        Map<Node, PlaneCoordinate> nodeMap = new HashMap<>();
        Map<Edge, ProjectedEdge> edgeMap = new HashMap<>();

        for (Node node : triangle.getNodes()) {
            nodeMap.put(node, nodePlaneCoordinateMap.get(node));
        }
        for (Edge edge : triangle.getEdges()) {
            edgeMap.put(edge, edgeToProjection.get(edge));
        }
        return new ProjectedTriangle(triangle, edgeMap, nodeMap);
    }
}
