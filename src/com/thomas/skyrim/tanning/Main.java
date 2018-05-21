package com.thomas.skyrim.tanning;

import com.thomas.skyrim.tanning.mesh.data.Edge;
import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String armorLocation = "C:\\Users\\thoma\\Downloads\\testSmall.nif";
        String bodyLocation = "C:\\Users\\thoma\\Downloads\\femalebody_1.nif";
        String outputLocation = "C:\\Users\\thoma\\Downloads";
        Algorithm algorithm = new Algorithm(bodyLocation, outputLocation);
        algorithm.execute(Arrays.asList(armorLocation));
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
