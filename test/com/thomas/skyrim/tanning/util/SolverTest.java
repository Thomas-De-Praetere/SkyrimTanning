package com.thomas.skyrim.tanning.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class SolverTest {
    private final double DELTA = 10E-6;

    @Test
    public void test() {
        Solver solver = new Solver(4, 1, 0, 0, 2, 0, 1, 0, 4, 0, 0, 4, 8);
        double[][] reduce = solver.reduce();
        assertEquals(2, reduce[0][3], DELTA);
        assertEquals(4, reduce[1][3], DELTA);
        assertEquals(2, reduce[2][3], DELTA);

        solver = new Solver(4, 1, 1, 1, 6, 0, 2, 5, -4, 2, 5, -1, 27);
        reduce = solver.reduce();
        assertEquals(5, reduce[0][3], DELTA);
        assertEquals(3, reduce[1][3], DELTA);
        assertEquals(-2, reduce[2][3], DELTA);


    }

}