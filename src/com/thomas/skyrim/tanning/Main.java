package com.thomas.skyrim.tanning;

import com.thomas.skyrim.tanning.mesh.data.Edge;
import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.load.Loader;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String location = "C:\\Users\\thoma\\Downloads\\testSmall.nif";
        boolean isBody = false;
        Loader loader = new Loader();
        List<Mesh> load = loader.load(location, isBody);

    }

    public static void checkMesh(Mesh mesh) {
        for (Edge edge : mesh.getEdges()) {
            for (Node node : edge.getNodes()) {
                assertTrue(node.getEdges().contains(edge));
            }
            for (Triangle triangle : edge.getTriangles()) {
                assertTrue(triangle.getEdges().contains(edge));
            }
        }
        for (Node node : mesh.getNodes()) {
            for (Edge edge : node.getEdges()) {
                assertTrue(edge.getNodes().contains(node));
            }
            for (Triangle triangle : node.getTriangles()) {
                assertTrue(triangle.getNodes().contains(node));
            }
        }
        for (Triangle triangle : mesh.getTriangles()) {
            for (Edge edge : triangle.getEdges()) {
                assertTrue(edge.getTriangles().contains(triangle));
            }
            for (Node node : triangle.getNodes()) {
                assertTrue(node.getTriangles().contains(triangle));
            }
        }
    }

    public static void assertTrue(boolean b) {
        if (!b) throw new IllegalStateException();
    }

}
