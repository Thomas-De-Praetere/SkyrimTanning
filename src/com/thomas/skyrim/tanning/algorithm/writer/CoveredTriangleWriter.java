package com.thomas.skyrim.tanning.algorithm.writer;

import com.google.common.io.Files;
import com.thomas.skyrim.tanning.algorithm.CoveredTriangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;

public class CoveredTriangleWriter {
    private final String originalLocation;
    private final String outputLocation;

    public CoveredTriangleWriter(String originalLocation, String outputLocation) {
        this.originalLocation = originalLocation;
        this.outputLocation = outputLocation;
    }

    public void write(Collection<CoveredTriangle> triangles) throws IOException {
        int imageSize = 1024;
        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        setBackground(graphics, imageSize);
        for (CoveredTriangle triangle : triangles) {
            if (!triangle.isCovered()) continue;
            triangle.write(graphics, imageSize);
        }
        BufferedImage result1 = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        BufferedImageOp blur = new ConvolveOp(getBlurKernel(5));
        blur.filter(image, result1);

        ImageIO.write(
                result1,
                "png",
                Paths.get(outputLocation, Files.getNameWithoutExtension(Paths.get(originalLocation).getFileName().toString()) + ".png").toFile()
        );
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
            data[i] = 1.0f / (float) dataItems;
        }
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
