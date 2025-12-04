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

```

The maze world and rendering logic use `WallRegistry` to

* resolve the correct texture for a given wall id
* decide whether a wall can be destroyed
* know how many hits it can sustain before it breaks

---

## How walls are used

Other modules use this module in several ways.

* Maze generation
  The maze generator chooses wall types for cells and edges and stores only identifiers.
  At runtime these identifiers are resolved through `WallRegistry`.

* Maze world and rendering
  The maze world computes board vectors and tiles.
  When drawing the maze the rendering code asks the wall registry for the `baseImage` and for behavioural flags such as `breakable`.

* Game logic
  Combat and interaction systems use `hitPoints` and `breakable` to update or replace walls when they are destroyed.

This separation keeps the maze data small and declarative, while the wall module centralises all details about materials and behaviour.

---

## Adding a new wall type

The recommended workflow for extending the set of walls is:
<br/>
- Update the EMF model:<br/>
Add a new wall type, material or property in the walls model.<br/>
If you maintain a separate .xmi configuration of instances, add an instance entry there as well.
-  Regenerate the Java code:<br/>
Run the Acceleo generator (for example from the `maze-generator.acceleo` module) so that the generated API and registry input are updated.<br/>
-  Wire the new wall into the registry:<br/>
If the registry is generated from the model, this happens automatically.<br/>
If there is a manual section in `WallRegistry`, add a new `WallDefinition` entry with a unique `id`.
- Use the new wall in the maze generator or in hand written mazes:<br/>
Refer to the new wall by its `id`. The runtime lookup will take care of image, hit points and behaviour.<br/>
<br/>

## Design guidelines

When you extend or modify this module, keep these principles in mind.

* The model is authoritative
  Whenever possible, express changes in the EMF model and let the generator update the Java code.

* Identifiers are stable
  Once a wall `id` is used in saved mazes or external tools, avoid changing it.
  Instead you can add aliases or migration logic in the registry if needed.

* Separate appearance and logic
  Use `baseImage` for pure visual representation and separate fields for gameplay relevant data such as `breakable` and `hitPoints`.

* Keep the registry small and explicit
  Each wall type should have a clearly defined purpose.
  If you need many visual variants, prefer a parameter on a single logical type instead of many almost identical types.

---

## Dependencies

This module is intended to be lightweight.

* It depends on the shared domain model infrastructure used in the other `main.game.maze` modules.
* It is referenced by the maze world, maze generator and behaviour modules, but has no dependency on JavaFX or UI specific code.

This makes it easy to reuse the wall definitions in tests, headless maze generators or future tools without bringing in the full game runtime.


