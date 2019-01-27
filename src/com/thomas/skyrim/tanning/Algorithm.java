package com.thomas.skyrim.tanning;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.thomas.skyrim.tanning.algorithm.CoveredChecker;
import com.thomas.skyrim.tanning.algorithm.CoveredTriangle;
import com.thomas.skyrim.tanning.algorithm.writer.CoveredTriangleWriter;
import com.thomas.skyrim.tanning.mesh.data.Edge;
import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.load.Loader;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class was created by thoma on 01-May-18.
 */
public class Algorithm {
    public static final int TIMES = 10;

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

        CoveredChecker checker = new CoveredChecker(meshes);

        Set<CoveredTriangle> processedTriangles = new HashSet<>();

        System.out.println("Start Covering");

        for (Triangle triangle : base.getTriangles()) {
            double covered = checker.isCovered(triangle);
            processedTriangles.add(new CoveredTriangle(triangle, (float) covered));
        }

        processedTriangles = blurTriangles(processedTriangles, base);

        System.out.println("Covering done.");
        CoveredTriangleWriter coveredTriangleWriter = new CoveredTriangleWriter(location, outputLocation);
        coveredTriangleWriter.write(processedTriangles);
        System.out.println("Done");
    }

    private Set<CoveredTriangle> blurTriangles(Set<CoveredTriangle> processedTriangles, Mesh base) {
        SetMultimap<Triangle, Triangle> triToNeighbours = HashMultimap.create();

        for (Triangle triangle : base.getTriangles()) {
            if (triToNeighbours.get(triangle).size() >= 3) continue;
            for (Edge edge : base.getTriangleEdges(triangle)) {
                List<Triangle> triangles = base.getTriangles(edge);
                for (Triangle neighbour : triangles) {
                    if (neighbour.equals(triangle)) continue;
                    triToNeighbours.put(triangle, neighbour);
                    triToNeighbours.put(neighbour, triangle);
                }
            }
        }
        for (int i = 0; i < TIMES; i++) {
            processedTriangles = blur(processedTriangles, triToNeighbours);
        }
        return processedTriangles;
    }

    private Set<CoveredTriangle> blur(Set<CoveredTriangle> processedTriangles, SetMultimap<Triangle, Triangle> triToNeighbours) {
        Set<CoveredTriangle> result = new HashSet<>();
        Map<Triangle, CoveredTriangle> triToCovered = processedTriangles.stream().collect(Collectors.toMap(
                CoveredTriangle::getTriangle,
                c -> c
        ));
        for (CoveredTriangle processedTriangle : processedTriangles) {
            List<CoveredTriangle> neighbours = triToNeighbours.get(processedTriangle.getTriangle())
                    .stream()
                    .map(triToCovered::get)
                    .collect(Collectors.toList());
            float newCoverValue = 0.0F;

            float factor = 1.0f / (float) (neighbours.size() + 1);
            newCoverValue += processedTriangle.getCovered() * factor;
            for (CoveredTriangle neighbour : neighbours) {
                newCoverValue += neighbour.getCovered() * factor;
            }
            result.add(new CoveredTriangle(
                    processedTriangle.getTriangle(),
                    newCoverValue
            ));
        }
        return result;
    }


}