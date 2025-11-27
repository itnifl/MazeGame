# MazeGame

## Index

* 🧩 [project-status](project-status/readme.md)
* 🧩 [main.game.maze.behaviour](main.game.maze.behaviour/readme.md)
* 🧩 [main.game.maze.difficulties](main.game.maze.difficulties/readme.md)
* 🧩 [main.game.maze.opponents](main.game.maze.opponents/readme.md)
* 🖥️ [maze](maze/readme.md)
* 🧩 [maze-feature](maze-feature/readme.md)
* 🧩 [maze-module-repository](maze-module-repository/readme.md)
* 🧩 [maze-generator.acceleo](maze-generator.acceleo/readme.md)
* 🧩 [maze-generator.acceleo-runner](maze-generator.acceleo-runner/readme.md)
* 🧩 [maze-module-generator](maze-module-generator/readme.md)
* 🧩 [releng](releng/readme.md)

Also, see: [Acceleo](acceleo.readme.md) in the Maze Game
Also, see Eclipse plugin setup: [Eclipse module worlds](eclipse.modules.md) in the Maze Game

---

## Instructions

* Reach the heart with the lowest possible moves and the highest possible character life left.
* Your score will lower for each move you make and the more life you lose.
* You will get an extra 4 000 in score for reaching the heart.
* You will lose 4 000 in score for dying.
* Press the H key to show high scores.
* You can save your scores when you die or win.
* Try the game a few times to get to know it.

<br/>
<img src="./gameDemo1.png" alt="Game Demo Screenshot" width="45%" />
<br/>

## Bugs

* If you run several instances at the same time, media files in the target directory can corrupt. Delete them in `target\classes\main\game\maze` and copy again from `resources\main\game\maze`.
* The application system is not very testable. Unit tests should have been written first.
* The action screens for win and game over can occasionally fail to show after adding the player flash effect. This is rare.

## Missing implementations

* More music and game sounds
* Animations for die action and happy action
* Read a maze from SVG for play
* More and different levels with their own characters and setup
* Better design for high score
* A menu with instructions and setup
* Refactor score handling out of CharacterActionScreens
* Implement stronger algorithms for gameplay and movement
* Replace `System.out.println` with a logger

## Sources

* Background music, western game soundtracks: [https://www.youtube.com/watch?v=ccvpPJv9J3E](https://www.youtube.com/watch?v=ccvpPJv9J3E)
* Player scream sounds: [https://www.youtube.com/watch?v=3rlV-whFgXQ](https://www.youtube.com/watch?v=3rlV-whFgXQ)
* Game over sounds: [https://www.youtube.com/watch?v=bug1b0fQS8Y](https://www.youtube.com/watch?v=bug1b0fQS8Y)
* Win game music: [https://www.youtube.com/watch?v=tEFU-oqSNjE](https://www.youtube.com/watch?v=tEFU-oqSNjE)
* Vector math: [https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/](https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/)
* Images: [https://opengameart.org/](https://opengameart.org/)

## Prerequisites and setup

This project prefers JDK 25 and JavaFX 25.

* Visual Studio Code: [https://code.visualstudio.com/download](https://code.visualstudio.com/download)
  Extensions:

  * ⬇️ Extension Pack for Java (Required)
  * ⬇️ Maven for Java (Required)
  * ⬇️ Debugger for Java (Required)
  * ⬇️ Test Runner for Java (Required)
  * ⬇️ XML by Red Hat (Optional)
  * ⬇️ OSGi for VS Code (Optional)
  * ⬇️ YAML by Red Hat (Optional)
  * ⬇️ OCL support (Optional)

Download and install:

  * JDK 25: [https://www.oracle.com/java/technologies/downloads/#java25](https://www.oracle.com/java/technologies/downloads/#java25)
  * JavaFX 25 SDK: [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
    Setup guide: [https://dev.java/learn/javafx/install/#javafx-windows](https://dev.java/learn/javafx/install/#javafx-windows)
  * Apache Maven: [https://maven.apache.org/install.html](https://maven.apache.org/install.html)
  * Or install [Chocolatey](https://chocolatey.org/install) and use Chocolatey to [install Maven](https://community.chocolatey.org/packages/maven) for you.
  * Powershell [Powershell 7.x](https://learn.microsoft.com/en-us/powershell/scripting/install/install-powershell-on-windows?view=powershell-7.5#msi) or higher

Environment variables (examples on Windows):

* 🛠️ `JAVA_HOME=C:\Program Files\Java\jdk-25`
* 🛠️ `PATH_TO_FX=C:\Program Files\Java\javafx-sdk-25`
* 🛠️ `PATH+=C:\Program Files\Java\jdk-25\bin`
* 🛠️ `MAVEN_HOME=C:\Program Files\Apache\Apache Maven`
* 🛠️ `PATH+=C:\Program Files\Apache\Apache Maven\bin`

VS Code Java runtime:

* Ctrl + Shift + P → “Java: Clean Java Language Server Workspace”
* Ctrl + Shift + P → “Java: Configure Java Runtime”
* Under JDKs, add `C:\Program Files\Java\jdk-25` and set it as Default
* In the same panel, set JDK for Language Server to JDK 21
* Reload Window

⚡ Finally, in Visual Studio Code select the `App.java` file in the `maze` module and run it.

## Build commands (exact)

```powershell
# PowerShell — refresh local mirror, prove key IU exists, reset Tycho cache, full build

# 1) start clean so the mirror is rebuilt
Remove-Item -Recurse -Force releng/local-p2 -ErrorAction SilentlyContinue
mvn -f releng/mirror/pom.xml -U verify

# 2) prove org.eclipse.core.runtime is in the mirror (True means found)
Select-String -Path releng\local-p2\content.* -Pattern 'org\.eclipse\.core\.runtime' -Quiet

# 3) force Tycho to reread the target (clear its p2 cache)
Remove-Item -Recurse -Force "$Env:USERPROFILE\.m2\repository\.cache\tycho" -ErrorAction SilentlyContinue

# 4) full build (Tycho + app)
mvn -U -DskipTests=false clean verify
```

Other handy targets:

```bash
# build only the app module, then full build with tests
mvn -U -pl :main.game.maze -am clean package
mvn -U clean install

# run all unit tests
mvn test

# run unit tests only in main.game.maze.opponents
mvn -pl main.game.maze.opponents -am test

# Run the JavaFX game (Windows)
# -> Start in VSCode (CTRl + F5)
```

### Makefile usage - Windows Powershell
```
# Default: toolchain info, update mirror if needed, clear Tycho cache, full build
.\make.ps1

# Explicit target:
.\make.ps1 -Target toolchain
.\make.ps1 -Target mirror
.\make.ps1 -Target force-mirror
.\make.ps1 -Target clear-cache
.\make.ps1 -Target build

```

#### Makefile usage - Other
```bash
make force-mirror
make clear-tycho-cache
make build
```

#### **Fast incremental run**
```bash
make build
```

#### **Mirror only, if needed**
```bash
make mirror
```


## Debug

Use the script `Run-P2AndBuildCheck.ps1` to run a build and check, then start by reading the logs.

---

## Module introductions


## Step overview

Here is what each module does, and how they depend on each other.

---

## Infrastructure

### 1. `releng/mirror`

**Type:** Tycho p2 mirror (pure build infra, no code of your own).
**What it does:**
Uses `tycho-p2-extras:mirror` to download Eclipse bundles/features into a local p2 repo:

* EMF (`org.eclipse.emf.*`, `org.eclipse.emf.sdk.feature.group`)
* OCL (`org.eclipse.ocl.*`, `org.eclipse.ocl.all.sdk.feature.group`)
* Acceleo (`org.eclipse.acceleo.feature.group`)
* Equinox runtime (`org.eclipse.equinox.app`, `org.eclipse.equinox.executable.feature.group`)
* Core runtime (`org.eclipse.core.runtime`, etc.)

and writes them to:

* `releng/local-p2/`

This repo is then:

* Referenced from `releng/maze.target`
* Exposed in the root POM as a p2 `<repository>` with id `local-p2`.

**Prerequisites:** none (this must be built first).
**Used by / must exist before:**

* All `eclipse-plugin` modules that require EMF/OCL/Acceleo:

  * `main.game.maze.behaviour`
  * `main.game.maze.difficulties`
  * `main.game.maze.opponents`
  * `maze-generator.acceleo-runner`
  * `maze-generator.runner` (if enabled)

The game itself (maze module) does not fetch anything from releng/local-p2. All the Eclipse / Tycho modules (the eclipse-plugin, eclipse-repository, feature) get their EMF/OCL/Acceleo/etc from releng/local-p2 when Tycho resolves them. The wiring happens via the target definition in releng and the Tycho configuration in your POMs.

Who actually uses releng/local-p2?
- These modules depend on the local p2 mirror during build:
- main.game.maze.behaviour (eclipse-plugin)
- main.game.maze.difficulties (eclipse-plugin)
- main.game.maze.opponents (eclipse-plugin)
- maze-generator.acceleo-runner (eclipse-plugin)
- maze-generator.runner (eclipse-plugin)
- maze-feature (feature)
- maze-module-repository (eclipse-repository)

---

## EMF model plug-ins

### 2. `main.game.maze.behaviour`

**Bundle:** `main.game.maze.behaviour`
**Type:** `eclipse-plugin` EMF model.

**What it does:**

* Defines the “behaviour/movements” Ecore model (`main.game.maze.behaviour.*`).
* Exports:

  * `main.game.maze.behaviour`
  * `main.game.maze.behaviour.impl`
  * `main.game.maze.behaviour.util`
* Requires:

  * `org.eclipse.core.runtime`
  * `org.eclipse.emf.ecore`
  * `org.eclipse.emf.ecore.xmi`

**Prerequisites:**

* External: EMF + core runtime from `releng/local-p2` (so `releng/mirror` must run first).
* No internal MazeGame bundles are required.

**Used by:**

* Included in `maze-feature`.
* Used conceptually by the JavaFX game (`maze`) when you hook behaviour into the game logic.

---

### 3. `main.game.maze.difficulties`

**Bundle:** `main.game.maze.difficulties`
**Type:** `eclipse-plugin` EMF model.

**What it does:**

* Defines the difficulties Ecore model (`main.game.maze.difficulties.*`).
* Registers:

  * The generated package with EMF (`org.eclipse.emf.ecore.generated_package`).
  * A resource factory for `"*.difficulties"` via `org.eclipse.emf.ecore.extension_parser`.
* Exports:

  * `main.game.maze.difficulties`
  * `main.game.maze.difficulties.impl`
  * `main.game.maze.difficulties.util`
* Requires:

  * `org.eclipse.core.runtime`
  * `org.eclipse.emf.ecore`
  * `org.eclipse.emf.common`
  * `org.eclipse.emf.ecore.xmi`
  * `org.eclipse.ocl.pivot`

**Prerequisites:**

* External: same EMF/OCL stuff from `releng/local-p2`.
* Internal: none; it is the “base” model for difficulties.

**Used by:**

* `main.game.maze.opponents` (via `Require-Bundle: main.game.maze.difficulties`)
* `maze-generator.acceleo-runner` (uses the difficulty model as input)
* Indirectly by the JavaFX game (`maze`).

---

### 4. `main.game.maze.opponents`

**Bundle:** `main.game.maze.opponents`
**Type:** `eclipse-plugin` EMF model + OCL.

**What it does:**

* Defines the opponents Ecore model (`main.game.maze.opponents.*`).
* Contains OCL-based constraints / derived features (since it requires both OCL pivot + OCL ecore).
* Exports:
  * `main.game.maze.opponents`
  * `main.game.maze.opponents.impl`
  * `main.game.maze.opponents.util`

* Requires:
  * `main.game.maze.difficulties`
  * `org.eclipse.emf.ecore`
  * `org.eclipse.emf.common`
  * `org.eclipse.emf.ecore.xmi`
  * `org.eclipse.ocl.pivot`
  * `org.eclipse.ocl.ecore`

**Prerequisites:**

* `releng/mirror` (for EMF/OCL).
* `main.game.maze.difficulties` (because of `Require-Bundle: main.game.maze.difficulties`).

**Used by:**

* Included in `maze-feature`.
* Consumed by the JavaFX game (`maze`) when you wire opponents into gameplay.

---

## Acceleo code generation plug-ins

### 5. `maze-generator.acceleo-runner`
**Currently disabled**
**Bundle:** `maze-generator.acceleo-runner`
**Type:** `eclipse-plugin`, Acceleo module.

**What it does:**

* Contains the Acceleo templates for generating Java code from your EMF models.
* Declares an Equinox application:
  * In `plugin.xml`:
    * `extension point="org.eclipse.core.runtime.applications"`
    * Application id: `main.game.maze.gen.app`
    * Run class: `main.game.maze.gen.HeadlessGeneratorApplication`

* Requires:
  * `org.eclipse.core.runtime`
  * `org.eclipse.emf.ecore`
  * `org.eclipse.emf.ecore.xmi`
  * `org.eclipse.ocl.pivot`
  * `org.eclipse.acceleo.engine`
  * `main.game.maze.difficulties`

**Prerequisites:**

* `releng/mirror` (for EMF, OCL, Acceleo).
* `main.game.maze.difficulties` (the difficulty model is part of the input for the templates).

**Used by:**

* `maze-generator.runner`, which actually *runs* the `main.game.maze.gen.app` Acceleo application for code generation.
* `maze-module-generator` indirectly, as the Maven-side bridge that wants the generated sources.

---

### 6. `maze-generator.runner`  *(currently commented out in the root modules, but functionally important)*

**Bundle:** `maze-generator.runner`
**Type:** `eclipse-plugin`, headless runner.

**What it does:**

* Provides the runtime environment for `main.game.maze.gen.app`:

  * Requires:
    * `org.eclipse.core.runtime`
    * `org.eclipse.equinox.app`
    * `org.eclipse.emf.ecore`
    * `org.eclipse.emf.ecore.xmi`
    * `org.eclipse.ocl.pivot`
    * `org.eclipse.acceleo.engine`
    * `maze-generator.acceleo-runner`

* Its POM uses:
  * `tycho-eclipse-plugin` to run the Equinox application (Acceleo headless generation).
  * `gmavenplus-plugin` to compute the `p2.repo.url` property pointing at `releng/local-p2`.

**Prerequisites:**

* `releng/mirror` (to have EMF/OCL/Acceleo in `local-p2`).
* `maze-generator.acceleo-runner` (because it needs the generator plug-in and its application id).

**Used by:**

* The actual headless Acceleo generation step (when you enable it and configure it as a Maven goal).
* Typically integrated with `maze-module-generator` in the Maven build to produce generated Java sources.

---

## Feature and repository modules

### 7. `maze-feature`

**Type:** Eclipse feature project.

**What it does:**

* Bundles your three EMF plug-ins into a feature:

  ```xml
  <plugin id="main.game.maze.behaviour"    …/>
  <plugin id="main.game.maze.difficulties" …/>
  <plugin id="main.game.maze.opponents"    …/>
  ```

* This is what you install in Eclipse or publish via a p2 repository.

**Prerequisites:**

* `main.game.maze.behaviour` (`main.game.maze.behaviour`)
* `main.game.maze.difficulties` (`main.game.maze.difficulties`)
* `main.game.maze.opponents` (`main.game.maze.opponents`)

**Used by:**

* `maze-module-repository`, which turns the feature into a p2 update site.

---

### 8. `maze-module-repository`

**Type:** `eclipse-repository` (p2 repo).

**What it does:**

* Uses `tycho-p2-publisher-plugin` to build a p2 repo containing the `maze.feature`.
* `category.xml` exposes:

  * Feature `maze.feature` under the “maze” category.

**Prerequisites:**

* `maze-feature` (the feature is referenced as `features/maze.feature_1.0.0.qualifier.jar`).

**Used by:**

* Anything that wants to consume the Maze modules via p2:

  * Your own Eclipse installations.
  * A Tycho build that resolves the maze feature from a p2 repo.

---

## Maven bridge and game

### 9. `maze-module-generator`

**Type:** Plain `jar` module (Maven-side helper).

**What it does (from the POM):**

* Sets up a build that:

  * Runs an Acceleo headless generation step (through a plugin, typically using the `maze-generator.runner` Equinox application, and the `p2.repo.url`).

  * Writes generated Java sources under:

    * `${project.build.directory}/generated-sources/acceleo`

  * Uses `build-helper-maven-plugin` (seen in the POM) to:

    * Attach those generated sources:

      ```xml
      <execution>
        <id>add-generated</id>
        <phase>generate-sources</phase>
        <goals><goal>add-source</goal></goals>
        <configuration>
          <sources>
            <source>${project.build.directory}/generated-sources/acceleo</source>
          </sources>
        </configuration>
      </execution>
      ```

* The idea is: this module “bridges” the Eclipse/Tycho-style code generation into a normal Maven source folder, so downstream modules (like the game) can just compile the generated classes.

**Prerequisites:**

* `releng/mirror` (for p2 repo).
* `maze-generator.acceleo-runner` and `maze-generator.runner` (to actually run the Acceleo application).
* The EMF model plug-ins (`main.game.maze.difficulties`, `main.game.maze.opponents`, `main.game.maze.behaviour`) as inputs to generation.

**Used by:**

* The `maze` module, which can depend on this JAR or on its generated sources in the same reactor.

---

### 10. `maze`

**Artifact:** `main.game.maze`
**Type:** Plain Maven module (JavaFX app).

**What it does:**

* Contains the JavaFX Maze game application.

* Uses Maven dependencies (not OSGi) to pull in:

  * The EMF model modules (`main.game.maze.difficulties`, `main.game.maze.opponents`, `main.game.maze.behaviour`) as plain JARs.
  * JavaFX (`org.openjfx:javafx-*`).
  * Optionally, the generated code from `maze-module-generator`.

* Has OS-specific profiles (`windows`, `mac`, `linux`) that set the `javafx.platform` property.

**Prerequisites:**

* All EMF model modules built (`main.game.maze.behaviour`, `main.game.maze.difficulties`, `main.game.maze.opponents`).
* `maze-module-generator` built, if the game uses the generated sources.
* JavaFX available via Maven (nothing to do with p2).

**Used by:**

* This is the final runnable game.

---

## Suggested logical build / dependency order

Putting it all together, the clean conceptual order (respecting prerequisites) is:

1. `releng/mirror`
2. `main.game.maze.behaviour`
3. `main.game.maze.difficulties`
4. `main.game.maze.opponents`  *(needs `main.game.maze.difficulties`)*
5. `maze-generator.acceleo-runner`  *(needs `main.game.maze.difficulties`)*
6. `maze-generator.runner`  *(needs `maze-generator.acceleo-runner`, uses `local-p2`)*
7. `maze-feature`  *(wraps movements, difficulty, opponents)*
8. `maze-module-repository`  *(wraps `maze-feature` into a p2 site)*
9. `maze-module-generator`  *(runs the headless generator and exposes generated sources)*
10. `maze`  *(JavaFX game using the models and generated code)*

In your current root POM, `maze-generator.runner` is commented out, but if you re-enable it, it should sit right after `maze-generator.acceleo-runner` and before anything that relies on the headless generator.

## The modules

### - main.game.maze.behaviour

Movement behaviors for characters and utilities used by the game loop. See the module guide: [main.game.maze.behaviour/readme.md](main.game.maze.behaviour/readme.md).

### - main.game.maze.difficulties

Ecore model and logic for difficulty profiles, defaults, and validations. See the module guide: [main.game.maze.difficulties/readme.md](main.game.maze.difficulties/readme.md).

### - main.game.maze.opponents

Ecore model and runtime helpers for enemies, threat values, and validation rules. See the module guide: [main.game.maze.opponents/readme.md](main.game.maze.opponents/readme.md).

### - maze

The JavaFX application code and entry point for running the game. See the module guide: [maze/readme.md](maze/readme.md).

### - maze-feature

Eclipse feature that groups the plug-ins for p2 builds. See the module guide: [maze-feature/readme.md](maze-feature/readme.md).

### - maze-module-repository

The p2 update site produced by Tycho for the Eclipse artifacts. See the module guide: [maze-module-repository/readme.md](maze-module-repository/readme.md).

### - maze-generator.acceleo-runner

Headless Acceleo generator that turns models into source code for the game. See the module guide: [maze-generator.acceleo-runner/readme.md](maze-generator.acceleo-runner/readme.md).

### - mazer-module-generator

The generated Java sources and jar produced by the Acceleo step. See the module guide: [mazer-module-generator/readme.md](mazer-module-generator/readme.md).

### - project-status

Course and project status notes for context and progress tracking. See: [project-status/readme.md](project-status/readme.md).

### - releng

Build infrastructure, local p2 mirror, and target platform. See: [releng/readme.md](releng/readme.md).

# Utility scripts at the project root

This repository includes two helper scripts for packaging the source and for running a repeatable build with diagnostics. Both scripts live in the root of the repo for easy access.

* 📦 **[pack-source.ps1](./pack-source.ps1)**
* 🧪 **[Run-P2AndBuildCheck.ps1](./Run-P2AndBuildCheck.ps1)**

---

### pack-source.ps1

**What it does**
Creates a clean zip of the workspace for sharing or archival. It excludes build outputs and common development clutter so that the archive only contains what is needed to review or rebuild.

**Typical exclusions**
`.git`, target folders, local p2 mirror under `releng/local-p2`, temporary work areas, and other transient files.
The archive name normally includes a timestamp to make artifacts traceable.

**Quick start**

```powershell
# From the repo root
.\pack-source.ps1
```

**Common options**
Most usage works out of the box. If the script supports switches, you can pass them as regular PowerShell parameters, for example:

```powershell
# Example if supported by your script
.\pack-source.ps1 -Output ".\dist\MazeGame-src.zip"
```

---

### Run-P2AndBuildCheck.ps1

**What it does**
Runs the end to end Tycho and Maven build in a controlled order, regenerates or validates the local p2 mirror, resets Tycho cache if needed, builds modules, runs tests, and writes a single timestamped log that includes per step summaries and captured output. It also echoes the summary to the terminal at the end.

**Typical flow**

1. Optionally clears `releng\local-p2` and rebuilds the mirror.
2. Optionally clears `~\.m2\repository\.cache\tycho` to force a fresh resolve.
3. Performs a clean verify from the root with tests enabled.
4. Prints a compact result table and writes the full log under `releng\test-results`.

**Quick start**

```powershell
# From the repo root
.\Run-P2AndBuildCheck.ps1
```

**Parameters**

```powershell
# Default output folder for logs
.\Run-P2AndBuildCheck.ps1 -LogDirectory "releng\test-results"

# You can add other switches if your script supports them, for example:
# -NoMirror to skip mirroring
# -NoCacheReset to keep the Tycho cache
# -SkipTests to run a faster compile only pass
```

**Outputs**

* A log file named like `p2-and-build-check_yyyyMMdd_HHmmss.log` under the chosen log directory
* A terminal summary showing step name, status, and a short note

---

### When to use which

* Use **pack-source.ps1** when you want to hand off the codebase without build noise or when you need a reproducible snapshot of the current tree.
* Use **Run-P2AndBuildCheck.ps1** when you want a single command to validate the mirror and the full build and to collect evidence in one place for troubleshooting or CI parity.
