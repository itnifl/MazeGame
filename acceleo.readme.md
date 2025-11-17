# Acceleo in the Maze Game

This document explains exactly how model-to-code generation with **Acceleo** is used in the Maze game, what gets generated, and how it fits into the overall build.

---

## Purpose

Acceleo turns your EMF model into concrete Java classes used by the game.
In this project, Acceleo reads the **DifficultyGameData** model and produces domain code that the app and tests consume.

---

## Where things live

* **Templates and launcher** → [`maze-generator.acceleo`](./maze-generator.acceleo)
  Contains the `.mtl` templates and a small headless launcher.

* **Generated sources** → [`mazer-module-generator`](./mazer-module-generator)
  Receives generated Java sources and builds them into a jar that other modules depend on.

* **Model input** → [`models/DifficultyGameData.xmi`](./models/DifficultyGameData.xmi)
  The canonical input file for the generator.

* **Build plumbing** → [`releng`](./releng)
  Provides the Tycho target and a local p2 mirror so the generator can run headlessly and offline.

---

## How generation runs

Generation is performed **headless** during the Maven build by launching an Eclipse runtime with the Acceleo engine.

High-level flow:

1. Tycho resolves an Eclipse runtime from the local mirror in `releng/local-p2`.
2. The generator app starts inside that runtime.
3. The app loads `models/DifficultyGameData.xmi`.
4. Acceleo templates render Java files.
5. Files are written into `mazer-module-generator/src/main/java`.
6. `mazer-module-generator` compiles those sources and publishes a jar that the game uses.

---

## The two launcher classes

* **`HeadlessGeneratorApp.java`**
  The entry point that Tycho starts inside the Eclipse runtime.
  Responsibilities:

  * Parse arguments `(inputModelPath, outputSourceDir)`.
  * Initialize EMF and Acceleo services.
  * Invoke the template root with the loaded model.
  * Exit with a nonzero code on failure so the build clearly fails.

* **`RunAcceleo.java`**
  A small helper that encapsulates “generate from model” logic.
  Responsibilities:

  * Register EMF packages and resource factories used by the model.
  * Resolve template modules and call the generated Acceleo Java stubs.
  * Write files to the output directory while preserving package structure.

These are kept minimal on purpose: all domain logic lives in the `.mtl` templates.

---

## Commands you will use

Generate sources and build all dependencies:

```bash
mvn -B -U -e -pl maze-generator.acceleo -am -DskipTests clean verify
```

Build the game afterward, using the freshly generated jar:

```bash
mvn -B -U -e -pl maze -am -DskipTests=false clean verify
```

Quick one-liner to do both from a clean checkout:

```bash
mvn -B -U -e -pl maze-generator.acceleo,maze -am -DskipTests=false clean verify
```

---

## CI usage

In GitHub Actions, the “Generate and build game” job runs the generator first, then builds the app. This guarantees the `mazer-module-generator` jar matches the current model before tests execute. See the workflow in `.github/workflows`.

---

## What gets generated

Typical outputs include:

* Data classes reflecting difficulty levels and parameters.
* Utility functions derived from OCL invariants and derived features in the model.
* Boilerplate to load and validate models at runtime.

Everything lands under `mazer-module-generator/src/main/java`, then `mazer-module-generator` packages it as a jar. The game module declares a dependency on that jar and uses the generated types directly.

---

## When you change the model or templates

* Edit the Ecore model or the `.mtl` templates.
* Re-run the generator command shown above.
* Commit the updated sources in `mazer-module-generator` so collaborators can build without running Acceleo locally.
* If you want to regenerate automatically in CI only, you can keep local workflows the same and rely on the workflow job to refresh outputs.

---

## Troubleshooting

* **“Cannot resolve … org.eclipse.core.runtime”**
  Refresh the local mirror and clear Tycho cache:

  ```powershell
  # Windows PowerShell
  Remove-Item -Recurse -Force releng\local-p2 -ErrorAction SilentlyContinue
  mvn -f releng/mirror/pom.xml -U verify
  Remove-Item -Recurse -Force "$Env:USERPROFILE\.m2\repository\.cache\tycho" -ErrorAction SilentlyContinue
  ```

* **Generator runs but no files appear**
  Check that the launcher arguments point to `models/DifficultyGameData.xmi` and to `mazer-module-generator/src/main/java`, and verify that `HeadlessGeneratorApp` finds your root template.

* **Template errors**
  Acceleo exceptions are surfaced as a build failure from `HeadlessGeneratorApp`. Open the build log to see the exact template and line number.

---

## Why Acceleo here

* Keeps model and code in sync with a single source of truth.
* Encodes mapping rules once in templates, avoiding repetitive boilerplate.
* Plays well with Tycho and EMF, so it runs the same locally and in CI, online or offline.

If you want to dive into the details, open the module READMEs:

* [`maze-generator.acceleo`](./maze-generator.acceleo/readme.md)
* [`mazer-module-generator`](./mazer-module-generator/readme.md)
