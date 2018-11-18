package com.thomas.skyrim.tanning.mesh.project;

import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.project.results.ProjectedFace;
import com.thomas.skyrim.tanning.mesh.project.results.ProjectedMesh;
import com.thomas.skyrim.tanning.mesh.project.results.ProjectedNode;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MeshProjector {
    private final Mesh baseMesh;

    public MeshProjector(Mesh baseMesh) {
        this.baseMesh = baseMesh;
    }

    public ProjectedMesh project(Mesh mesh) {
        //First project all nodes
        Map<Node, ProjectedNode> projectedNodes = projectNodes(mesh);

        //Use that info to project the triangles to faces.
        Map<Triangle, ProjectedFace> projectedTriangles = projectTriangles(mesh, projectedNodes);

        return new ProjectedMesh();
    }

    private Map<Triangle, ProjectedFace> projectTriangles(Mesh mesh, Map<Node, ProjectedNode> projectedNodes) {
        TriangleProjector projector = new TriangleProjector(projectedNodes, baseMesh);
        Map<Triangle, ProjectedFace> map = new HashMap<>();
        for (Triangle triangle : mesh.getTriangles()) {
            ProjectedFace face = projector.project(triangle);
            map.put(triangle, face);
        }
        return map;
    }

    private Map<Node, ProjectedNode> projectNodes(Mesh mesh) {
        NodeProjector projector = new NodeProjector(baseMesh);
        return projector.projectNodes(mesh);
    }
}
