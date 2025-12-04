# main.game.maze.walls

This module contains the Ecore wall model. The module [maze-generator.acceleo ](../maze-generator.acceleo/readme.md) uses model and its generated together with a corresponding xmi to generate a Wall registry. 
It defines what kinds of walls exist in the game, how they look, and how set the parameters related to later character interaction with them.

Typical responsibilities:

- Define wall types and materials used by mazes.
- Provide a generated Java API for these walls.
- Expose a lightweight registry that the game uses at runtime.

---

## Structure

The module is organised in three main parts.

### １．EMF model

The EMF model describes the abstract concept of walls that can appear in a maze.

Typical elements in the model include

- Wall type  
  For example plain wall, breakable wall, decorative wall, door and secret wall.

- Material  
  For example stone, wood, metal, glass.  
  In code this is reflected by the `WallMaterialBaseType` enumeration.

- Properties  
  For example  
  - whether the wall is breakable  
  - hit points or durability  
  - base image or texture key  
  - optional tags used by generators or behaviours  

The model is the single source of truth. Any change here should be reflected automatically in the generated Java code and in the maze generator.

### ２．Generated Java code

From the EMF model and the .xmi configuration the Acceleo templates generate Java classes under `main.game.maze.walls.generated` (package name and folder layout may vary slightly depending on the current generator setup).

Generated output typically includes

- Value objects for walls and materials.
- A small factory or builder API for creating walls from identifiers.
- Enum or constant classes mirroring the modelled types.
- Convenience methods used by other modules such as the maze world and the maze generator.

Generated code is not meant to be edited by hand. If you want to change behaviour or add a new field, update the model or the generator template instead.

### ３．Runtime registry

At runtime the game uses a registry to look up wall definitions by identifier.

The core class is

```java
public final class WallRegistry {

    ....
    // Internal map with all registered walls
}
