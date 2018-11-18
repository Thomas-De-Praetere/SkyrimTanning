package com.thomas.skyrim.tanning;

import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.load.Loader;
import com.thomas.skyrim.tanning.mesh.project.MeshProjector;
import com.thomas.skyrim.tanning.mesh.project.results.ProjectedMesh;

import java.util.ArrayList;
import java.util.List;

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
        MeshProjector projector = new MeshProjector(base);

        List<Mesh> load = loader.load(location, false);
        List<ProjectedMesh> projectedMeshes = new ArrayList<>();
        for (Mesh mesh : load) {
            ProjectedMesh project = projector.project(mesh);
            projectedMeshes.add(project);
        }
        //Do more
    }

}
