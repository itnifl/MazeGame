# Acceleo in the Maze Game

This document explains how model to code generation with **Acceleo** is used in the Maze game, what gets generated, and how it fits into the overall build.

---

## Purpose

Acceleo turns EMF models into concrete Java classes used by the game.

In this project Acceleo reads difficulty and opponent related models  
for example the `DifficultyGameData` model  
and produces domain code that the app and tests consume.

---

## Where things live

- **Templates** → [`maze-generator.acceleo`](./maze-generator.acceleo)  
  Contains the `.mtl` templates and the Acceleo generation logic.

- **Headless runner** → [`maze-generator.acceleo-runner`](./maze-generator.acceleo-runner)  
  Eclipse plug in that starts the Acceleo `Generate` module in a headless runtime.

- **Generated sources** → [`maze-module-generator`](./maze-module-generator)  
  Receives generated Java sources (for example under `src-gen`) and builds them into a JAR that other modules depend on.

- **Model input** → for example [`models/DifficultyGameData.xmi`](./models/DifficultyGameData.xmi)  
  Canonical input file(s) for the generator.

- **Build plumbing** → [`releng`](./releng)  
  Provides the Tycho target, local p2 mirror and helper scripts so the generator can run headless and offline.

---

## How generation runs

Generation is performed **headless** during the Maven or Tycho build by launching an Eclipse runtime with the Acceleo engine.

High level flow:

1. Tycho resolves an Eclipse runtime from the local mirror in `releng/local-p2`.  
2. `maze-generator.acceleo-runner` starts inside that runtime and invokes the Acceleo `Generate` module from `maze-generator.acceleo`.  
3. The runner loads the configured model inputs  
   for example `models/DifficultyGameData.xmi`.  
4. Acceleo templates render Java files.  
5. Files are written into `maze-module-generator/src-gen`  
   or a similar generated sources folder.  
6. `maze-module-generator` attaches that folder as a source root and compiles the generated classes into a JAR that the game uses.

From the rest of the build this looks like a normal Java dependency: the game module just depends on `maze-module-generator`.

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
| `CharacterGraphicsFactory.java` | Creates sprites using `getImageBase()` | `opponents.ecore` |
| `BehaviorDispatcher.java` | Dispatches behaviour by character type | `opponents.ecore` |
| `package-info.java` | Package documentation | — |

**Note:** The generated code uses actual EMF model method names (`getThreatLevel`, `getImageBase`, etc.) from the `CharacterType` interface and its subclasses. Note that `attackDamage` is handled separately in runtime code with `instantDeath` logic, not in the generated multiplier code.

Everything lands under the generated sources folder in `maze-module-generator`
for example `maze-module-generator/src-gen`.
`maze-module-generator` then packages these classes into a JAR, and the game module declares a dependency on that JAR and uses the generated types directly.

### Unit Tests

Generated code is validated by JUnit 5 tests in `maze-module-generator/src/test/java/`:

| Test Class | Coverage |
|------------|----------|
| `CharacterRegistrarTest.java` | Registration lookup, null handling |
| `CharacterAttributeSetterTest.java` | Multiplier application, base threat levels |
| `CharacterGraphicsFactoryTest.java` | Sprite paths, animation frames, scale |

Run tests with: `mvn -pl maze-module-generator test`

---

## When you change the model or templates

* Edit the Ecore model, XMI model instances or the `.mtl` templates in `maze-generator.acceleo`.
* Re run the generator build
  for example `mvn -pl maze-module-generator -am clean verify`.
* Commit the updated sources in `maze-module-generator` if you intend collaborators to build without running Acceleo locally.
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
  * the Acceleo `Generate` module is the one your runner actually calls.

* **Template errors**
  Acceleo exceptions are surfaced as build failures from the headless runner.
  Open the build log to find the exact template and line number.

---

## Why Acceleo here

* Keeps models and generated code in sync with a single source of truth.
* Encodes mapping rules once in templates, avoiding repetitive boilerplate.
* Integrates cleanly with Tycho, EMF and Maven so it runs the same locally and in CI, online or offline.

For more details, see:

* [`maze-generator.acceleo-runner`](./maze-generator.acceleo-runner/readme.md)
* [`maze-module-generator`](./maze-module-generator/readme.md)
