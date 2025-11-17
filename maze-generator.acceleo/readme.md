# maze-generator.acceleo

## Overview

This module holds the Acceleo templates and a small headless launcher used to generate Java source code for the game. It reads your EMF model and writes the produced sources into the `mazer-module-generator` module so that the app and tests can depend on a regular jar.

---

## The two launchers and when to use them

### `RunAcceleo.java` — Eclipse Application (Tycho-driven)

* **What it is:** An Eclipse Application entry point that Tycho runs inside an OSGi runtime during the Maven build.
* **Where it is used:** Bound to the Maven `generate-sources` phase via the Tycho eclipserun configuration in this module’s `pom.xml`. The application id (declared in this plug-in’s `plugin.xml`) is referenced by the build.
* **What it does:**

  1. Starts an Eclipse runtime.
  2. Locates the Acceleo templates.
  3. Loads the input XMI model.
  4. Invokes Acceleo to generate Java files into `mazer-module-generator/src/main/java`.
* **Why this path:** Running under Tycho guarantees the exact Eclipse bundles (EMF, OCL, Acceleo) from the local p2 mirror, so builds are reproducible and offline-friendly.

**Command that uses it:**

```bash
# From repo root — runs the Eclipse Application inside Tycho
mvn -pl maze-generator.acceleo -am -DskipTests clean verify
```

### `HeadlessGeneratorApp.java` — Plain Java entry point (dev utility)

* **What it is:** A simple `public static void main(String[] args)` wrapper to run the generator directly from an IDE or a plain JVM when you do not want to spin up the full Tycho OSGi runtime.
* **How to run it:** Run as a normal Java application and pass two arguments:
  `args[0]` — absolute or project-relative path to the input model XMI
  `args[1]` — output source directory (usually `mazer-module-generator/src/main/java`)
* **What it does:** Performs the same generate step as the Eclipse Application but uses direct bootstrap code. It is ideal for quick iteration while editing templates.

**Example invocation (from the project root):**

```bash
# Adjust paths as needed
java -cp "<compiled-classes-and-deps>" main.game.maze.gen.HeadlessGeneratorApp ^
  models/DifficultyGameData.xmi ^
  mazer-module-generator/src/main/java
```

> Tip: In VS Code or Eclipse you can create a run configuration for `HeadlessGeneratorApp` with those two arguments to regenerate quickly after template edits.

---

## Inputs and outputs

**Inputs**

* EMF model file: `models/DifficultyGameData.xmi`
* Acceleo templates in this module (the generator locates them via the bundle)

**Outputs**

* Generated Java sources: `mazer-module-generator/src/main/java`
  These are compiled and packaged by the `mazer-module-generator` module into a jar that `main.game.maze` depends on.

---

## How it fits in the build

1. `releng/mirror` builds a local p2 mirror containing EMF, OCL, Acceleo, and platform units.
2. `releng/maze.target` points Tycho to that mirror.
3. During `mvn … verify`, Tycho launches `RunAcceleo` in the `generate-sources` phase.
4. Files are written to `mazer-module-generator/src/main/java`.
5. `mazer-module-generator` compiles and publishes a jar consumed by `main.game.maze` and tests.

---

## Build and regenerate

**Full generator run (Tycho, recommended)**

```bash
mvn -pl maze-generator.acceleo -am -DskipTests clean verify
```

**End-to-end build (includes generator)**

```bash
mvn -U -DskipTests=false clean verify
```

**Refresh the local p2 mirror (if you changed `releng/mirror`)**

```bash
mvn -f releng/mirror/pom.xml -U verify
```

---

## Notes and troubleshooting

* If you change templates, re-run the generator (Tycho or `HeadlessGeneratorApp`) so the `mazer-module-generator` sources are updated before compiling the app.
* If Tycho reports missing bundles (for example `org.eclipse.core.runtime`), rebuild the mirror and ensure the target points to `releng/local-p2`.
* Keep the output directory (`mazer-module-generator/src/main/java`) under version control policy that fits your workflow. Many teams commit generated sources for simpler IDE import, but you can also ignore and always regenerate in CI.
