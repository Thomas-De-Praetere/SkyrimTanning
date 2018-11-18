package com.thomas.skyrim.tanning;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String armorLocation = "C:\\Users\\thoma\\Downloads\\testSmall.nif";
        String bodyLocation = "C:\\Users\\thoma\\Downloads\\femalebody_1.nif";
        String outputLocation = "C:\\Users\\thoma\\Downloads";
        Algorithm algorithm = new Algorithm(bodyLocation, outputLocation);
        algorithm.execute(Arrays.asList(armorLocation));
    }
}
