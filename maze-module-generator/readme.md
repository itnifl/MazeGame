# maze-module-generator

`maze-module-generator` is the central generator project for the MazeGame modelling stack.

Where the individual modules (`main.game.maze.opponents`, `main.game.maze.difficulties`, `main.game.maze.walls`, `main.game.maze.comp`, etc.) contain the actual EMF models and runtime code, this project collects the generation artefacts and launch configurations that regenerate model based code for those modules.

It is mainly used during development inside Eclipse, not on every Maven build.

---

## Purpose

The project exists to give a single place where you can

- open and run EMF `.genmodel` files for the MazeGame models  
- run Xtext or MWE2 based generators tied to the MazeGame DSLs  
- trigger regeneration of model driven code for the various `main.game.maze.*` modules  
- keep all generator launch configurations in one, versioned project

The idea is that “all model driven generators for MazeGame live here”, so they are easy to discover and maintain.

---

## Typical contents

The exact contents depend on your current setup, but you will usually find:

- EMF generator models  
  `.genmodel` files for domain models such as opponents, difficulties, walls and components.  
  These are used to generate the EMF model, edit and editor code into their respective projects.

- Xtext and MWE2 workflows  
  `.mwe2` files that drive Xtext code generation for DSLs like `main.game.maze.comp` (if applicable).  
  Launch configurations for these workflows are also typically stored in this project.

- Eclipse launch configurations  
  `.launch` files for:
  - EMF “Generate Model / Edit / Editor” actions  
  - Xtext generator workflows  
  - other custom generators that belong to the MazeGame toolchain  

- Documentation and helper notes  
  For example, this `readme.md` and any additional notes on how to extend or debug generators.

Generated Java code itself is **not** stored inside `maze-module-generator`.  
Instead, it is written back into the owning modules (for example `main.game.maze.opponents`, `main.game.maze.difficulties`, `main.game.maze.walls`) according to each generator’s configuration.

---

## How it relates to other generator projects

MazeGame has multiple generator related projects:

- `maze-generator.acceleo`  
  Contains Acceleo templates that turn high level models into Java code and helpers.

- `maze-generator.acceleo-runner`  
  Provides a headless runner for those Acceleo templates in the Tycho build.

- `maze-module-generator`  
  Hosts EMF `.genmodel` files, Xtext workflows and general “developer side” generators for the core modelling projects.

A simple rule of thumb:

- Use `maze-module-generator` when you are **inside Eclipse** and want to regenerate EMF or Xtext based code.  
- Use the `maze-generator.*` projects when you want **Maven / Tycho** to run model to code generation automatically in a build.

---

## Typical usage in Eclipse

A common workflow when you change a model is:

1. Open the relevant `.ecore` or DSL grammar in its home module.  
2. Adjust the model (for example add a new attribute, type or reference).  
3. Switch to `maze-module-generator` and:
   - open the corresponding `.genmodel` and run the standard EMF “Generate” actions, and/or  
   - run the associated `.mwe2` workflow for Xtext, if the change affects a DSL.
4. Inspect the generated code in the owning module, fix compile errors if any, and commit the updated generated files as needed.

By keeping all generator artefacts here, you do not need to remember which module owns which launch configuration.

---

## Role in the build

`maze-module-generator` is mainly a **developer productivity** project:

- EMF and Xtext generated code is usually checked into version control.  
- The Tycho build compiles these generated sources but does not need to regenerate them every time.  
- When the models evolve, you manually run the generators from this project and commit the updated code.

This keeps the CI build fast and deterministic, while still letting you use full model driven workflows during development.

---

## When to modify maze-module-generator

You should update this project when you:

- add a new EMF model that needs `.genmodel` based code generation  
- introduce a new Xtext DSL or change an existing grammar and its MWE2 workflow  
- reorganise where generated code should be written (for example move it to a new module)  
- add or update launch configurations so other developers can run the generators in the same way

Typical steps:

1. Create or import the new `.genmodel` or `.mwe2` file into `maze-module-generator`.  
2. Configure the output paths so generated code lands in the correct `main.game.maze.*` project.  
3. Add or update launch configurations and test generation locally.  
4. Commit both generator configuration and resulting generated sources.

---

## Design guidelines

When maintaining `maze-module-generator`, keep these principles in mind:

- Keep generation logic here, not scattered  
  All EMF and Xtext generator definitions for MazeGame should be easy to find in this project.

- Do not hand edit generated code  
  Changes should go into models, grammars or templates, then be regenerated.

- Document generator entry points  
  If a generator has specific preconditions or parameters, note them in this README or in a short comment near the `.mwe2` or `.genmodel`.

- Make it team friendly  
  Launch configurations and paths should work for all developers with a standard checkout of the repository.

By following these ideas, `maze-module-generator` remains a clear and convenient hub for all model driven code generation in the MazeGame project.
