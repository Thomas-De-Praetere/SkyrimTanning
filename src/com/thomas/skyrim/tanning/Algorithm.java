package com.thomas.skyrim.tanning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.SetMultimap;
import com.thomas.skyrim.tanning.algorithm.CoveredChecker;
import com.thomas.skyrim.tanning.algorithm.CoveredTriangle;
import com.thomas.skyrim.tanning.algorithm.PixelTransformer;
import com.thomas.skyrim.tanning.algorithm.writer.CoveredTriangleWriter;
import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Triangle;
import com.thomas.skyrim.tanning.mesh.load.Loader;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class was created by thoma on 01-May-18.
 */
public class Algorithm {
    public static final int TIMES = 20;

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
            processedTriangles.add(new CoveredTriangle(triangle, covered));
        }

        SetMultimap<Triangle, Triangle> triToNeighbours = createNeighbourMap(base);
        processedTriangles = blurTriangles(processedTriangles, triToNeighbours);
        List<PixelTransformer> pixelTransformers = createPixelTransformers(processedTriangles, triToNeighbours);

        System.out.println("Covering done.");
        CoveredTriangleWriter coveredTriangleWriter = new CoveredTriangleWriter(location, outputLocation);
        coveredTriangleWriter.write(pixelTransformers);
        System.out.println("Done");
    }

    private List<PixelTransformer> createPixelTransformers(Set<CoveredTriangle> processedTriangles, SetMultimap<Triangle, Triangle> triToNeighbours) {
        Map<Triangle, CoveredTriangle> translationMap = processedTriangles.stream()
                .collect(Collectors.toMap(CoveredTriangle::getTriangle, c -> c));
        ListMultimap<CoveredTriangle, CoveredTriangle> neighbourMap = ArrayListMultimap.create();
        for (Triangle triangle : triToNeighbours.keySet()) {
            CoveredTriangle key = translationMap.get(triangle);
            for (Triangle neigh : triToNeighbours.get(triangle)) {
                neighbourMap.put(
                        key,
                        translationMap.get(neigh)
                );
            }
        }
        List<PixelTransformer> transformers = new ArrayList<>();
        for (CoveredTriangle processedTriangle : processedTriangles) {
            PixelTransformer transformer = new PixelTransformer(neighbourMap, processedTriangle);
            transformers.add(transformer);
        }
        return transformers;
    }


    private SetMultimap<Triangle, Triangle> createNeighbourMap(Mesh base) {
        SetMultimap<Triangle, Triangle> triToNeighbours = HashMultimap.create();

        for (Triangle triangle : base.getTriangles()) {
            // if (triToNeighbours.get(triangle).size() >= 3) continue;
            for (Node node : triangle.getNodes()) {
                List<Triangle> triangles = base.getTriangles(node);
                for (Triangle neighbour : triangles) {
                    if (neighbour.equals(triangle)) continue;
                    triToNeighbours.put(triangle, neighbour);
                    triToNeighbours.put(neighbour, triangle);
                }
            }
        }
        return triToNeighbours;
    }

    private Set<CoveredTriangle> blurTriangles(Set<CoveredTriangle> processedTriangles, SetMultimap<Triangle, Triangle> triToNeighbours) {
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
            double newCoverValue = 0.0;

            double factor = 1.0 / (double) (neighbours.size() + 1);
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