package com.thomas.skyrim.tanning.mesh.data;

/**
 * This class was created by thoma on 24-Apr-18.
 */
public class Edge {

	private Node[] nodes;
	private Triangle[] triangles;

	Edge() {
		nodes = new Node[2];
		triangles = new Triangle[2];
	}

	public Node[] getNodes() {
		return nodes;
	}

	public Triangle[] getTriangles() {
		return triangles;
	}

	void setNodes(Node[] nodes) {
		this.nodes = nodes;
	}

	void setTriangles(Triangle[] triangles) {
		this.triangles = triangles;
	}
}
