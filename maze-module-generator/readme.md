# maze-module-generator

`maze-module-generator` is a Java-based module that hosts **Model-Driven Code Generation** artifacts for the MazeGame build.

It depends on `main.game.maze.walls`, `main.game.maze.opponents`, and `main.game.maze.difficulties`, and is wired into the Maven lifecycle so that generated Java sources in `src-gen` are compiled together with the rest of the project.

The module is packaged as a regular JAR (not an Eclipse plugin) and is meant to run headless as part of `mvn clean verify`.

---

## Generated Code (src-gen)

The following Java classes are generated from EMF models and demonstrate true Model-Driven Engineering:

| File | Source Model | Purpose |
|------|--------------|----------|
| `OpponentRegistry.java` | `opponents.ecore` | Lists all enemy types with their stats |
| `WallRegistry.java` | `walls.ecore` | Lists all wall material types |
| `CharacterRegistrar.java` | `opponents.ecore` | Type-safe switch dispatch for character registration |
| `CharacterAttributeSetter.java` | `opponents.ecore` | Applies difficulty multipliers using `getHealth()`, `getThreatLevel()`, `getSpeed()` |
| `CharacterGraphicsFactory.java` | `opponents.ecore` | Creates sprites using `getImageBase()`, `getAnimationFrameCount()`, `getSpriteScale()` |

**Note**: Additional templates exist (`DifficultyConfigurator.mtl`, `BehaviorDispatcher.mtl`, etc.) but are not yet integrated into the build process.

### Key Methods in Generated Code

**CharacterRegistrar**:
- `register()` - Type-safe character registration with handlers
- `getKnownTypes()` - Returns all model character types
- `isKnownType(String)` - Checks if a type is in the model

**CharacterAttributeSetter**:
- `applyDifficultyMultipliers()` - Applies health/threat/speed multipliers per type
- `getBaseHealth(String)` - Returns model-defined health per type
- `getBaseThreatLevel(String)` - Returns model-defined threat level per type

**CharacterGraphicsFactory**:
- `getSpritePath()` - Returns sprite using `getImageBase()` from model
- `getAnimationFrameCount()` - Returns frame count using `getAnimationFrameCount()` from model
- `getSpriteScale()` - Returns scale factor using `getSpriteScale()` from model

---

## Unit Tests

Comprehensive JUnit 5 tests validate the generated code:

```
src/test/java/main/game/maze/generated/
├── CharacterRegistrarTest.java      # Tests type registration and lookup
├── CharacterAttributeSetterTest.java # Tests stats and multipliers
└── CharacterGraphicsFactoryTest.java # Tests graphics properties
```

Run tests with:
```bash
mvn test -f maze-module-generator/pom.xml
```

---

## Purpose

The project exists to:

- host Java based generators that use the `main.game.maze.walls` model and code
- write generated Java sources into `src-gen` in a reproducible way
- register `src-gen` as an additional source folder during Maven builds
- keep walls related generation concerns in a dedicated, versioned module

In other words, this module is the place where you put code that takes the walls model and produces extra Java artefacts for MazeGame.

---

## Maven configuration

The `pom.xml` shows three key aspects:

1. It is a child of the main `MazeGame` reactor:

   - `groupId`: `main.game.maze`
   - `artifactId`: `maze-module-generator`
   - `packaging`: `jar`

2. It depends on the walls module:

   ```xml
   <dependency>
     <groupId>main.game.maze</groupId>
     <artifactId>main.game.maze.walls</artifactId>
     <version>${project.version}</version>
   </dependency>
```

This allows generator code in `maze-module-generator` to use the walls model, enums and helper classes directly.

3. It uses `build-helper-maven-plugin` to add `src-gen` as a source folder:

   ```xml
   <plugin>
     <groupId>org.codehaus.mojo</groupId>
     <artifactId>build-helper-maven-plugin</artifactId>
     <version>3.5.0</version>
     <executions>
       <execution>
         <id>add-generated</id>
         <phase>generate-sources</phase>
         <goals>
           <goal>add-source</goal>
         </goals>
         <configuration>
           <sources>
             <source>src-gen</source>
           </sources>
         </configuration>
       </execution>
     </executions>
   </plugin>
   ```

   This means anything you generate into `src-gen` during `generate-sources` will be treated as normal Java sources by the compiler.

---

## Typical contents

You will usually find:

* `src/`
  Hand written Java code that performs the actual generation, for example:

  * reading definitions from `main.game.maze.walls`
  * building derived registries or helper classes
  * writing Java code into `src-gen` using standard file APIs

* `src-gen/`
  Generated Java sources written by the generator code in this module.
  These files are treated as additional sources by Maven (and should normally be kept out of version control).

* `pom.xml`
  The Maven configuration shown above.

The generator logic itself lives in this module; the code it produces is consumed by other MazeGame modules through standard Java dependencies.

---

## How the generator is used in the build

During a normal multi module build, for example:

```bash
mvn clean verify
```

the sequence for this module is:

1. Maven runs the `generate-sources` phase.
2. Your generator code in `maze-module-generator` (if wired into that phase, for example via a plugin or a custom main that you call) writes Java files into `src-gen`.
3. `build-helper-maven-plugin` adds `src-gen` as an extra source root for this module.
4. The `compile` phase compiles both `src` and `src-gen`.
5. The resulting JAR can then be used by other modules that depend on `maze-module-generator`.

If the generator is purely internal to this module, other projects may only need the generated effects indirectly through the walls module or other consumers.

---

## Integration with main.game.maze.walls

Because this module depends on `main.game.maze.walls`, you can:

* read model level classes and enums from the walls module
* derive additional structures such as:

  * precomputed lookup tables
  * static registries
  * helper classes for rendering or game logic that depend on wall definitions

The overall pattern is:

* `main.game.maze.walls` remains the main source of truth for wall definitions.
* `maze-module-generator` uses those definitions to generate additional Java code into `src-gen`.
* The generated code is then available as part of this module’s JAR to any consumer that needs it.

---

## Running and maintaining the generator

To run the generator as part of the normal build:

```bash
mvn -f maze-module-generator/pom.xml clean verify
```

If you add or change generator logic:

1. Implement or update the generator classes under `src/`.
2. Make sure they are invoked during or before `generate-sources`
   (for example via a plugin configuration or a small main method hooked into the lifecycle).
3. Confirm that new files appear under `src-gen`.
4. Rebuild the main reactor and fix any compile errors in consumers if the generated API changed.

---

## Design guidelines

When working on `maze-module-generator`, keep these points in mind:

* Keep all generated code in `src-gen`
  Do not mix generated and manually written code in `src`.

* Treat `main.game.maze.walls` as the authoritative input
  Do not duplicate wall definitions in this module; derive everything from the existing model and code.

* Make generation idempotent
  Running the generator multiple times should produce the same `src-gen` contents without manual cleanup.

* Prefer not to commit `src-gen` to version control
  Let Maven recreate it on each build, so that generated code always matches the current generator logic and walls model.

With this setup, `maze-module-generator` provides a clean, Maven friendly way to generate additional code from the MazeGame walls module.
