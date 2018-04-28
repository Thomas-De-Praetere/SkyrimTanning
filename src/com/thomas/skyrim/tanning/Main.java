package com.thomas.skyrim.tanning;

import com.thomas.skyrim.tanning.mesh.data.Mesh;
import com.thomas.skyrim.tanning.mesh.load.Loader;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		String location = "C:\\Users\\thoma\\Downloads\\femalebody_1.nif";
		boolean isBody = true;
		Loader loader = new Loader();
		List<Mesh> load = loader.load(location, isBody);

		System.out.println(load.toString());
	}
}
