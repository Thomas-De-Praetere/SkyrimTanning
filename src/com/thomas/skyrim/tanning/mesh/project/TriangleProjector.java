package com.thomas.skyrim.tanning.mesh.project;

import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.project.results.ProjectedFace;
import com.thomas.skyrim.tanning.mesh.project.results.ProjectedNode;

import java.util.Map;
import java.util.Stack;

/**
 *
 */
public class TriangleProjector {
    private final Map<Node, ProjectedNode> nodeToProjectedNode;
    private final Mesh baseMesh;

    public TriangleProjector(Map<Node, ProjectedNode> nodeToProjectedNode, Mesh baseMesh) {
        this.nodeToProjectedNode = nodeToProjectedNode;
        this.baseMesh = baseMesh;
    }

    public ProjectedFace project(Triangle triangle) {
        Stack<ProjectedNode> stack = new Stack<>();
        triangle.getNodes().stream().map(nodeToProjectedNode::get).forEach(stack::push);
    }
}
