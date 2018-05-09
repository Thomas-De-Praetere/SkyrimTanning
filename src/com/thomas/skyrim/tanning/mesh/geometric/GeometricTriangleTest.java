package com.thomas.skyrim.tanning.mesh.geometric;


import com.thomas.skyrim.tanning.mesh.data.Coordinate;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class was created by thoma on 07-May-18.
 */
public class GeometricTriangleTest {

    private static final double EPSILON = 10E-6;

    @Test
    public void test() {
        GeometricTriangle triangle = new GeometricTriangle(
                new Coordinate(0., 0., 0.),
                new Coordinate(2., 0., 0.),
                new Coordinate(0., 2., 0.)
        );

        Coordinate c1 = new Coordinate(.5, .5, .5);
        Coordinate c2 = new Coordinate(10., 40., 30.);

        PlaneCoordinate pr1 = triangle.projectPoint(c1);
        PlaneCoordinate pr2 = triangle.projectPoint(c2);

        Coordinate co1 = pr1.toCoordinate();
        Coordinate co2 = pr2.toCoordinate();

        assertTrue(pr1.inTriangle());
        assertFalse(pr2.inTriangle());

        assertEquals(.5, co1.x(), EPSILON);
        assertEquals(.5, co1.y(), EPSILON);
        assertEquals(0., co1.z(), EPSILON);

        assertEquals(10., co2.x(), EPSILON);
        assertEquals(40., co2.y(), EPSILON);
        assertEquals(0., co2.z(), EPSILON);
    }
}