# MazeGame

## Index

* 🧩 [project-status](project-status/readme.md)
* 🧩 [movements-module](movements-module/readme.md)
* 🧩 [difficulty-module](difficulty-module/readme.md)
* 🧩 [opponents-module](opponents-module/readme.md)
* 🖥️ [maze](maze/readme.md)
* 🧩 [maze-feature](maze-feature/readme.md)
* 🧩 [maze-repository](maze-repository/readme.md)
* 🧩 [maze-generator.acceleo](maze-generator.acceleo/readme.md)
* 🧩 [maze-generated](maze-generated/readme.md)
* 🧩 [releng](releng/readme.md)

Also, see: [Acceleo](acceleo.readme.md) in the Maze Game

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
* Generate a random maze on demand
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

  * ⬇️ Extension Pack for Java
  * ⬇️ Maven for Java
  * ⬇️ Debugger for Java
  * ⬇️ Test Runner for Java
  * ⬇️ XML by Red Hat
  * ⬇️ OSGi for VS Code
  * ⬇️ YAML by Red Hat
  * ⬇️ OCL support

* JDK 25: [https://www.oracle.com/java/technologies/downloads/#java25](https://www.oracle.com/java/technologies/downloads/#java25)

* JavaFX 25 SDK: [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
  Setup guide: [https://dev.java/learn/javafx/install/#javafx-windows](https://dev.java/learn/javafx/install/#javafx-windows)

* Apache Maven: [https://maven.apache.org/install.html](https://maven.apache.org/install.html)

Environment variables (examples on Windows):

* 🛠️ `JAVA_HOME=C:\Program Files\Java\jdk-24`
* 🛠️ `PATH_TO_FX=C:\Program Files\Java\javafx-sdk-24`
* 🛠️ `PATH+=C:\Program Files\Java\jdk-24\bin`
* 🛠️ `MAVEN_HOME=C:\Program Files\Apache\Apache Maven`
* 🛠️ `PATH+=C:\Program Files\Apache\Apache Maven\bin`

VS Code Java runtime:

* Ctrl + Shift + P → “Java: Clean Java Language Server Workspace”
* Ctrl + Shift + P → “Java: Configure Java Runtime”
* Under JDKs, add `C:\Program Files\Java\jdk-24` and set it as Default
* In the same panel, set JDK for Language Server to JDK 25
* Reload Window

⚡ Finally, in Visual Studio Code select the `App.java` file in the `maze` module and run it.

## Build commands (exact)

```powershell
# PowerShell — refresh local mirror, prove key IU exists, reset Tycho cache, full build

# 1) start clean so the mirror is rebuilt
Remove-Item -Recurse -Force releng\local-p2 -ErrorAction SilentlyContinue
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

# run unit tests only in opponents-module
mvn -pl opponents-module -am test

# run the JavaFX game (Windows)
java `
  --module-path "$Env:PATH_TO_FX\lib" `
  --add-modules javafx.controls,javafx.fxml,javafx.media `
  -jar .\maze\target\main.game.maze-1.0.0-SNAPSHOT.jar
```

## Debug

Use the script `Run-P2AndBuildCheck.ps1` to run a build and check, then start by reading the logs.

---

## Module introductions

The releng folder prepares the build environment for all Eclipse parts of the project. It creates a local p2 mirror with the needed Eclipse and Modeling units and defines the target file that points [Tycho](https://github.com/eclipse-tycho) to that mirror. With this in place the build is repeatable and offline friendly, since all required bundles such as EMF, OCL, Acceleo, and the platform units are available from a local path. When the root build starts, Tycho reads the target from releng to resolve plug in and feature dependencies for the modules that follow.

The maze generator [Acceleo](https://www.eclipse.org/acceleo/) module is the [Headless mode](https://en.wikipedia.org/wiki/Headless_software) code generator. During the generate sources phase it launches the Acceleo application inside an Eclipse runtime, reads your model input, and writes Java sources into the maze generated module. The maze generated module is a plain jar module that simply compiles and packages those produced sources so that the game app and tests can depend on a stable artifact. In short, the generator produces code and maze generated ships it as a jar.

The maze feature-module collects the Eclipse plug ins from your project into a single feature that describes what should be installed together. The maze-repository module then assembles a p2 update site that contains that feature and its plug ins, ready for installation in an Eclipse based product or workspace. Together these pieces let you build two deliverables at once. A runnable JavaFX app from the ordinary Maven modules and an Eclipse installable set of plug ins and features from the Tycho side, all resolved against the target and mirror produced by releng.

```
+------------------------------- releng -------------------------------+
| prepares build env for Eclipse parts                                 |
|                                                                      |
|   +----------------------+        +-------------------------------+  |
|   | mirror (local p2)   | <----> | Eclipse & Modeling units      |  |
|   | file:releng/local-p2|        | EMF / OCL / Acceleo / platform|  |
|   +----------+-----------+        +-------------------------------+  |
|              |                                                     |
|   +----------v-----------+                                         |
|   | maze.target          |  -> Tycho resolves bundles/features     |
|   | (points to mirror)   |                                         |
|   +----------------------+                                         |
+---------------------------------------------------------------------+

                      root `mvn clean verify`
                                   |
                  +----------------+----------------+
                  |                                 |
            (ordinary Maven)                  (Tycho build)
                  |                                 |
   +--------------+------------+          +---------+---------+
   |                           |          |                   |
+--v------------------+   +----v-----+    |   Eclipse plug-ins|
| maze-generator.     |   | maze-    |    |   from project    |
| acceleo (headless)  |   | generated|    |   (e.g.,          |
| - runs Acceleo app  |   | (plain JAR)   |   movements,      |
|   in Eclipse runtime|   | - compiles     |   difficulties,   |
| - reads model input |   |   generated src|   opponents)      |
| - writes Java to    |   +----+-----+     |                   |
|   maze-generated/src|        |           +---------+---------+
+----------+----------+        |                     |
           |                   | depends             |
           | generates         |                     |
           |                   |                     |
      +----v-------------------v----+        +-------v--------+
      | main.game.maze (JavaFX app) |        | maze-feature   |
      | and tests depend on the JAR |        | - collects     |
      +-------------+---------------+        |   plug-ins     |
                    |                        +-------+--------+
                    | uses                             |
                    |                                  |
                    |                          +-------v--------+
                    |                          | maze-repository|
                    |                          | - builds p2    |
                    |                          |   update site  |
                    |                          +----------------+
                    |
Outputs:
- Runnable JavaFX app JAR(s) from Maven side (maze + maze-generated)
- Installable p2 site (features + plug-ins) from Tycho side
(all resolved against releng/maze.target → releng/local-p2 mirror)
```

## Step overview

1. releng/mirror builds a local p2 mirror that contains EMF, OCL, Acceleo, and platform units.
<br/>Command:
     `mvn -f releng/mirror/pom.xml -U verify`

2. releng/maze.target points Tycho to that local p2 so dependency resolution is stable and offline friendly.<br/>
Command used when building (Tycho reads the target): same as in step 3.

3. The root build starts. Tycho reads releng/maze.target to resolve Eclipse plug in and feature dependencies.<br/>
Command:
     `mvn -U -DskipTests=false clean verify`

4. movements-module compiles as an Eclipse plug in that provides movement behavior code.<br/>
Command:
     `mvn -pl movements-module -am -DskipTests=false clean verify`

5. difficulty-module compiles as an Eclipse plug in that provides the Ecore model, OCL, and related runtime for difficulties.<br/>
Command:
     `mvn -pl difficulty-module -am -DskipTests=false clean verify`

6. opponents-module compiles as an Eclipse plug in that depends on difficulty-module and EMF runtime.
<br/>Command:
     `mvn -pl opponents-module -am -DskipTests=false clean verify`

7. maze-feature collects the project’s Eclipse plug ins into one installable feature.<br/>
Command:
     `mvn -pl maze-feature -am -DskipTests=false clean verify`

8. maze-repository assembles a p2 update site that contains the feature and the plug ins for installation in an Eclipse based product or workspace.<br/>
Command:
     `mvn -pl maze-repository -am -DskipTests=false clean verify`

9. maze-generator.acceleo runs headless during the generate sources phase, launches the Acceleo application, reads the model input, and writes Java sources into maze-generated.<br/>
Command:
     `mvn -pl maze-generator.acceleo -am -DskipTests clean verify`

10. maze-generated is a plain jar module that compiles the generated sources and publishes a stable artifact.<br/>
Command:
      `mvn -pl maze-generated -am -DskipTests=false clean verify`

11. main.game.maze compiles the JavaFX application, depends on the jar from maze-generated, and runs unit tests.<br/>
Command:
      `mvn -pl maze -am -Djavafx.platform=windows -DskipTests=false clean verify`
      (or use `-Djavafx.platform=linux` on Linux runners)

12. The build outputs two deliverables: the JavaFX game artifacts from the Maven side and an installable p2 repository from the Tycho side.
      Command to produce both in one go: same as in step 3.

## The modules

### - movements-module

Movement behaviors for characters and utilities used by the game loop. See the module guide: [movements-module/readme.md](movements-module/readme.md).

### - difficulty-module

Ecore model and logic for difficulty profiles, defaults, and validations. See the module guide: [difficulty-module/readme.md](difficulty-module/readme.md).

### - opponents-module

Ecore model and runtime helpers for enemies, threat values, and validation rules. See the module guide: [opponents-module/readme.md](opponents-module/readme.md).

### - maze

The JavaFX application code and entry point for running the game. See the module guide: [maze/readme.md](maze/readme.md).

### - maze-feature

Eclipse feature that groups the plug-ins for p2 builds. See the module guide: [maze-feature/readme.md](maze-feature/readme.md).

### - maze-repository

The p2 update site produced by Tycho for the Eclipse artifacts. See the module guide: [maze-repository/readme.md](maze-repository/readme.md).

### - maze-generator.acceleo

Headless Acceleo generator that turns models into source code for the game. See the module guide: [maze-generator.acceleo/readme.md](maze-generator.acceleo/readme.md).

### - maze-generated

The generated Java sources and jar produced by the Acceleo step. See the module guide: [maze-generated/readme.md](maze-generated/readme.md).

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
