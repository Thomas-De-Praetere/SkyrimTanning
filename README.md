# SkyrimTanning
A repository for my python scripts involving armor occlusion on bodies in Skyrim.

This project will consist out of two parts. One part is this, the Python scripts. These script will analyse the Data/Meshes folder and create occlusion maps for each armor. I wanted to do this in C++ or C# but I couldn't find a Nif library and therefore it is in Python. I also want to include all armor pieces that do not have a body attached, like gloves. Therefore the body that we use to calculate the occlusion will be the default player body. Using bodyslide to get your armors and your body the same will be important.

The second part will be an ingame spell, like WetFunction, to apply burning and tanning to the player. For each armor an occlusion map will have been generated and we can apply it as an overlay using slaveTats. This will allow the player to get burned on the skin parts that are not covered by armor.

## Occlusion mapping

The idea is to project each triangle of the occluding shape on a set of convex shapes on the body. Each of these shapes will have points in or on edges of triangles of the body. Using the UV mapping of the body we can calculate the position of these vertices on the texture of the body. The area such a shape covers is not touched by light. We can export this occluded image (black if pixel is covered, white otherwise) as a texture and apply it as an overlay on the body in game.

The projection of a triangle will go as follows:
* We search the closest triangle of the body for each node in the triangle.
* We project each node according to the normal of this triangle on the body. This does not have to be on the closest triangle. We could probably improve the projection by taking the mean normal of the closest set of triangles, but that is for later on.
* We then project the edges of the triangle according to the plane through that edge and the mean projection direction of both nodes. This edge will become a set of edges on the body mesh. Each edge in that set is contained in a triangle of the body.
* We create new shapes using the projected edges as borders and the edges of the body as internal edges. That way we have shapes on the body that use nodes on edges of the body unless they lie in the middle of a triangle of the body. Each edge of the set of projected shapes also lies in the plane of a triangle of the body.
* We can now use the UV projection to project these shapes on the texture. Now we can easily split a projected triangle in half if the UV projection of a body splits over that edge.
This can all seem a bit fuzzy but think about what would go wrong if the occluding shape was a large triangle spanning over triangles that are cut from each other in the uv projection of the body. We would get an elongated shape covering more that it should. It would not follow the flow of the body and a shadow does follow the flow.

We would also try to use a QuadTree over the body to quickly find triangles without having to search the entire space.

Impromptu idea: maybe we only need an outline and not the internal shapes. Just foolow the projected border of a triangle and cut it when the shape cuts (which is automatical in a Nif) This is easier to calculate and therfore faster.
