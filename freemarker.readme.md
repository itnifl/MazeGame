# FreeMarker in the Maze Game

This document explains how model to code generation with **FreeMarker** is used in the Maze game, what gets generated, and how it fits into the overall build.

---

## Purpose

FreeMarker turns EMF models into concrete Java classes used by the game.

In this project FreeMarker reads difficulty and opponent related models
for example the `DifficultyGameData` model
and produces domain code that the app and tests consume.

---

## Where things live

- **FreeMarker Templates** → [`maze-generator.freemarker/src/main/resources/templates`](./maze-generator.freemarker/src/main/resources/templates)
  Contains FreeMarker `.ftl` templates for opponents and walls domains.

- **Generators** → [`maze-generator.freemarker`](./maze-generator.freemarker)
  Contains `RunFreeMarker.java` and `RunWallsFreeMarker.java` — FreeMarker-based generators that use templates to produce Java code from EMF models.

- **Generator Runner** → [`maze-generator.freemarker-runner`](./maze-generator.freemarker-runner)
  Eclipse plug-in that orchestrates generation during the Tycho build.

- **Generated sources** → [`maze-module-generator`](./maze-module-generator)  
  Receives generated Java sources (for example under `src-gen`) and builds them into a JAR that other modules depend on.

- **Model input** → for example [`models/DifficultyGameData.xmi`](./models/DifficultyGameData.xmi)  
  Canonical input file(s) for the generator.

- **Build plumbing** → [`releng`](./releng)  
  Provides the Tycho target, local p2 mirror and helper scripts so the generator can run headless and offline.

---

## How generation runs

Generation is performed during the Maven/Tycho build using **FreeMarker-based generators** that read EMF models and produce Java source code from templates.

High level flow:

1. Tycho builds the EMF model plug-ins and the `maze-generator.freemarker` plug-in.
2. `maze-generator.freemarker-runner` invokes `RunFreeMarker.java` and `RunWallsFreeMarker.java` in a headless runtime.
3. The FreeMarker generators:
   - Load the XMI model files (e.g., `opponentModel.xmi`, `walls.xmi`)
   - Register the EMF packages (OpponentsPackage, WallsPackage, etc.)
   - Transform the EMF model data into FreeMarker data models
   - Process `.ftl` templates to generate Java source files
4. Files are written into the appropriate output directories.  
5. `maze-module-generator` attaches the generated sources and compiles them into a JAR.

From the rest of the build this looks like a normal Java dependency: the game module just depends on `maze-module-generator`.

**Note**: The generators use FreeMarker (freemarker.jar) as a true template engine, providing proper model-to-text transformation with separation between templates (.ftl) and Java logic.

---

## Commands you will use

Generate sources and build the generator JAR:

```bash
mvn -B -U -e -pl maze-module-generator -am -DskipTests clean verify
```

Build the game afterward, using the freshly generated JAR:

```bash
mvn -B -U -e -pl maze -am -DskipTests=false clean verify
```

Quick one liner to do both from a clean checkout:

```bash
mvn -B -U -e -pl maze-module-generator,maze -am -DskipTests=false clean verify
```

Depending on your setup you may also run the full reactor and let the generator step be triggered transitively.

---

## CI usage

In GitHub Actions the “generate and build game” job runs the generator part first
(for example by building `maze-module-generator`)
then builds the app.

This guarantees that the `maze-module-generator` JAR matches the current models and templates before tests execute.
See the workflow definitions in `.github/workflows`.

---

## What gets generated

Typical outputs include:

* Data classes reflecting difficulty levels, threat values and parameters.
* Helper classes derived from model structure and OCL based rules.
* Boilerplate to load, validate and expose model information at runtime.

### Current Generated Files

The following files are currently generated under `maze-module-generator/src-gen/main/game/maze/generated/`:

| File | Purpose | EMF Model Source |
|------|---------|------------------|
| `CharacterRegistrar.java` | Registers character types from model | `opponents.ecore` |
| `CharacterAttributeSetter.java` | Applies difficulty multipliers using `getHealth()`/`setHealth()`, `getThreatLevel()`/`setThreatLevel()`, `getSpeed()`/`setSpeed()` | `opponents.ecore` |
| `CharacterGraphicsFactory.java` | Creates sprites using `getImageBase()`, `getAnimationFrameCount()`, `getSpriteScale()` | `opponents.ecore` |
| `OpponentRegistry.java` | Lists all enemy types with their stats | `opponents.ecore` |
| `WallRegistry.java` | Lists all wall material types | `walls.ecore` |
| `WallMaterialRenderer.java` | Renders walls by material type | `walls.ecore` |
| `WallCollisionHandler.java` | Handles wall collision logic | `walls.ecore` |

**Note:** The generated code uses actual EMF model method names (`getThreatLevel`, `getImageBase`, `getAnimationFrameCount`, `getSpriteScale`, etc.) from the `CharacterType` interface and its subclasses. Note that `attackDamage` is handled separately in runtime code with `instantDeath` logic, not in the generated multiplier code.

Everything lands under the generated sources folder in `maze-module-generator`
for example `maze-module-generator/src-gen`.
`maze-module-generator` then packages these classes into a JAR, and the game module declares a dependency on that JAR and uses the generated types directly.

### Unit Tests

Generated code is validated by JUnit 5 tests in `maze-module-generator/src/test/java/`:

| Test Class | Coverage |
|------------|----------|
| `CharacterRegistrarTest.java` | Registration lookup, null handling |
| `CharacterAttributeSetterTest.java` | Multiplier application, base threat levels, damage multiplier |
| `CharacterGraphicsFactoryTest.java` | Sprite paths, animation frames, scale |
| `WallRegistryTest.java` | Wall material definitions, registry lookups, model validation |
| `OpponentRegistryTest.java` | Game name constant, enemy listing, model validation |

Run tests with: `mvn -pl maze-module-generator test`

---

## When you change the model or templates

* Edit the Ecore model, XMI model instances or the FreeMarker `.ftl` templates in `maze-generator.freemarker/src/main/resources/templates/`.
* Re run the generator build
  for example `mvn -pl maze-module-generator -am clean verify`.
* Commit the updated sources in `maze-module-generator` if you intend collaborators to build without regenerating locally.
* If you prefer to regenerate only in CI, you can let the workflow refresh outputs and keep local builds using the committed generated code.

---

## Troubleshooting

* **“Cannot resolve … org.eclipse.core.runtime”**
  Refresh the local mirror and clear the Tycho cache:

  ```powershell
  # Windows PowerShell
  Remove-Item -Recurse -Force releng\local-p2 -ErrorAction SilentlyContinue
  mvn -f releng/mirror/pom.xml -U verify
  Remove-Item -Recurse -Force "$Env:USERPROFILE\.m2\repository\.cache\tycho" -ErrorAction SilentlyContinue
  ```

* **Generator runs but no files appear**
  Check that:

  * the runner configuration points at the correct model files
    for example `models/DifficultyGameData.xmi`
  * the output folder is a writable directory inside `maze-module-generator`
    for example `src-gen`
  * the FreeMarker templates are present in `maze-generator.freemarker/src/main/resources/templates/`.

* **Template errors**
  FreeMarker exceptions are surfaced as build failures from the generator.
  Open the build log to find the exact template and line number.

* **Model validation failures**
  The FreeMarker generators validate models before generation:
  - `IllegalStateException` with "null or blank 'id'" → Required field missing in model
  - Warnings about null `wallBaseType`, `baseImage`, or `displayName` → Non-critical, defaults are used
  
  Check the build output for validation warnings that indicate model issues.

---

## Why FreeMarker here

* Keeps models and generated code in sync with a single source of truth.
* Encodes mapping rules once in templates, avoiding repetitive boilerplate.
* FreeMarker is a mature, Apache-licensed template engine available from Maven Central.
* Integrates cleanly with Tycho, EMF and Maven so it runs the same locally and in CI, online or offline.

For more details, see:

* [`maze-generator.freemarker-runner`](./maze-generator.freemarker-runner/readme.md)
* [`maze-module-generator`](./maze-module-generator/readme.md)
