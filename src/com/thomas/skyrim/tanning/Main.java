package com.thomas.skyrim.tanning;

import com.thomas.skyrim.tanning.mesh.load.Loader;

public class Main {

	public static void main(String[] args) {
		String location = "C:\\Users\\thoma\\Downloads\\femalebody_1.nif";
		boolean isBody = true;
		Loader loader = new Loader();
		loader.load(location, isBody);
	}
}
