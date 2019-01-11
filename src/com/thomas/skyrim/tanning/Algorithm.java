package com.thomas.skyrim.tanning;

import com.thomas.skyrim.tanning.algorithm.CoveredChecker;
import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.load.Loader;
import com.thomas.skyrim.tanning.util.intervalTree.BoundingBoxTree;

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
        armourLocations.parallelStream().forEach(l -> project(l, baseBody));
    }

    private void project(String location, Mesh base) {
        List<Mesh> meshes = loader.load(location, false);

        List<BoundingBoxTree> boundingBoxTrees = meshes.stream()
                .map(Mesh::getTriangles)
                .map(BoundingBoxTree::new)
                .collect(Collectors.toList());

        CoveredChecker checker = new CoveredChecker(boundingBoxTrees);

        Set<Triangle> covered = new HashSet<>();
        Set<Triangle> unCovered = new HashSet<>();

        for (Triangle triangle : base.getTriangles()) {
            if (checker.isCovered(triangle)) {
                covered.add(triangle);
            } else {
                unCovered.add(triangle);
            }
        }
    }

}
