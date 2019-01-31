package com.thomas.skyrim.tanning;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String furLocation = "C:\\Users\\thoma\\Documents\\GitHub\\Self\\Test\\body1f_0.nif";
        String elvenLocation = "C:\\Users\\thoma\\Documents\\GitHub\\Self\\Test\\cuirass_0.nif";
        String bodyLocation = "C:\\Users\\thoma\\Documents\\GitHub\\Self\\Test\\femalebody_0.nif";
        String outputLocation = "C:\\Users\\thoma\\Documents\\GitHub\\Self\\Test";
        Algorithm algorithm = new Algorithm(bodyLocation, outputLocation);
        algorithm.execute(Arrays.asList(furLocation, elvenLocation));
    }
}
