package com.thomas.skyrim.tanning.algorithm.writer;

import com.google.common.io.Files;
import com.thomas.skyrim.tanning.algorithm.PixelTransformer;
import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import com.thomas.skyrim.tanning.mesh.data.Node;
import com.thomas.skyrim.tanning.mesh.data.Tuple;
import com.thomas.skyrim.tanning.mesh.geometric.GeometricPlane;
import com.thomas.skyrim.tanning.mesh.geometric.PlaneCoordinate;
import com.thomas.skyrim.tanning.util.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CoveredTriangleWriter {
    private final String originalLocation;
    private final String outputLocation;

    public CoveredTriangleWriter(String originalLocation, String outputLocation) {
        this.originalLocation = originalLocation;
        this.outputLocation = outputLocation;
    }

    public void write(Collection<PixelTransformer> triangles) throws IOException {
        int imageSize = 1024;
        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        setBackground(graphics, imageSize);
        for (PixelTransformer triangle : triangles) {
            triangle.intialize(imageSize);
            Set<Pair<Integer, Integer>> coveredPixels = getCoverePixels(triangle, imageSize);
            for (Pair<Integer, Integer> coveredPixel : coveredPixels) {
                float coveredValue = (float) triangle.getCoveredValue(new Tuple(
                        coveredPixel.getFirst() + 0.5,
                        coveredPixel.getSecond() + 0.5)
                );
                image.setRGB(
                        coveredPixel.getFirst(),
                        coveredPixel.getSecond(),
                        new Color(coveredValue, coveredValue, coveredValue).getRGB()
                );
            }
        }
        BufferedImage result1 = image;
        BufferedImage prev = image;
        for (int i = 0; i < 10; i++) {
            result1 = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
            BufferedImageOp blur = new ConvolveOp(getBlurKernel(3));
            blur.filter(prev, result1);
            prev = result1;
        }

        ImageIO.write(
                result1,
                "png",
                Paths.get(outputLocation, Files.getNameWithoutExtension(Paths.get(originalLocation).getFileName().toString()) + ".png").toFile()
        );
    }

    private Set<Pair<Integer, Integer>> getCoverePixels(PixelTransformer triangle, int imageSize) {
        int lx = Integer.MAX_VALUE;
        int ly = Integer.MAX_VALUE;
        int ux = Integer.MIN_VALUE;
        int uy = Integer.MIN_VALUE;
        for (Node node : triangle.getTriangle().getTriangle().getNodes()) {
            double x = node.getUv().times(imageSize).u();
            double y = node.getUv().times(imageSize).v();
            lx = (x < (double) lx) ? (int) Math.floor(x) : lx;
            ly = (y < (double) ly) ? (int) Math.floor(y) : ly;
            ux = (x > (double) ux) ? (int) Math.floor(x) : ux;
            uy = (y > (double) uy) ? (int) Math.floor(y) : uy;
        }
        Tuple n1 = triangle.getTriangle().getTriangle().getN1().getUv().times(imageSize);
        Tuple n2 = triangle.getTriangle().getTriangle().getN2().getUv().times(imageSize);
        Tuple n3 = triangle.getTriangle().getTriangle().getN3().getUv().times(imageSize);
        GeometricPlane geo = GeometricPlane.of(
                new Coordinate(n1.u(), n1.v(), 0),
                new Coordinate(n2.u(), n2.v(), 0),
                new Coordinate(n3.u(), n3.v(), 0)
        );
        Set<Pair<Integer, Integer>> coveredPixel = new HashSet<>();
        for (int i = lx; i < ux + 1; i++) {
            for (int j = ly; j < uy + 1; j++) {
                PlaneCoordinate planeCoordinate = geo.projectPoint(new Coordinate(i + 0.5, j + 0.5, 0));
                if (planeCoordinate.inTriangle()) coveredPixel.add(Pair.of(i, j));
            }
        }
        return coveredPixel;
    }


    private void setBackground(Graphics2D graphics, int size) {
        Polygon polygon = new Polygon();
        polygon.addPoint(0, 0);
        polygon.addPoint(0, size);
        polygon.addPoint(size, size);
        polygon.addPoint(size, 0);
        graphics.setColor(Color.WHITE);
        graphics.fillPolygon(polygon);
    }

    private Kernel getBlurKernel(int size) {
        return getBlurKernel(size, size);
    }

    private Kernel getBlurKernel(int width, int height) {
        int dataItems = width * height;
        float[] data = new float[dataItems];
        for (int i = 0; i < dataItems; i++) {
            data[i] = 1.0f / (float) (dataItems - 1);
        }
        data[dataItems / 2+1] = 0.0f;
        return new Kernel(
                width,
                height,
                data
        );
    }

    private Kernel getGaussKernel() {
        float[] data = new float[]{1, 4, 6, 4, 1, 4, 16, 24, 16, 4, 6, 24, 36, 24, 6, 4, 16, 24, 16, 4, 1, 4, 6, 4, 1};
        for (int i = 0; i < data.length; i++) {
            data[i] = data[i] / 256.f;
        }
        return new Kernel(5, 5, data);
    }
}
