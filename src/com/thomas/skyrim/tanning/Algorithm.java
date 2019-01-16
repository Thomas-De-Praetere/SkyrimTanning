package com.thomas.skyrim.tanning;

import com.google.common.io.Files;
import com.thomas.skyrim.tanning.algorithm.CoveredChecker;
import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.load.Loader;
import com.thomas.skyrim.tanning.util.intervalTree.BoundingBoxTree;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        armourLocations.parallelStream().forEach(l -> {
            try {
                project(l, baseBody);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void project(String location, Mesh base) throws IOException {
        List<Mesh> meshes = loader.load(location, false);

        List<BoundingBoxTree> boundingBoxTrees = meshes.stream()
                .map(Mesh::getTriangles)
                .map(BoundingBoxTree::new)
                .collect(Collectors.toList());

        CoveredChecker checker = new CoveredChecker(boundingBoxTrees);

        Set<Triangle> covered = new HashSet<>();
        Set<Triangle> unCovered = new HashSet<>();

        System.out.println("Start Covering");

        for (Triangle triangle : base.getTriangles()) {
            if (checker.isCovered(triangle)) {
                covered.add(triangle);
            } else {
                unCovered.add(triangle);
            }
        }
        BufferedImage image = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.BLACK);
        for (Triangle triangle : covered) {
            graphics.fillPolygon(triangle.toProjectedPolygon(1024));
        }

        ImageIO.write(
                image,
                "png",
                Paths.get(outputLocation, Files.getNameWithoutExtension(Paths.get(location).getFileName().toString()) + ".png").toFile()
        );
    }
}