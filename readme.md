# MazeGame

## Index

* 🧩 [project-status](project-status/README.md)
* 🧩 [movements-module](movements-module/README.md)
* 🧩 [difficulty-module](difficulty-module/README.md)
* 🧩 [opponents-module](opponents-module/README.md)
* 🖥️ [maze](maze/README.md)
* 🧩 [maze-feature](maze-feature/README.md)
* 🧩 [maze-repository](maze-repository/README.md)
* 🧩 [maze-generator.acceleo](maze-generator.acceleo/README.md)
* 🧩 [maze-generated](maze-generated/README.md)
* 🧩 [releng](releng/README.md)

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

This project prefers JDK 24 and JavaFX 25.

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

* JDK 24: [https://www.oracle.com/java/technologies/downloads/#java24](https://www.oracle.com/java/technologies/downloads/#java24)

* JavaFX 25 SDK: [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
  Setup guide: [https://dev.java/learn/javafx/install/#javafx-windows](https://dev.java/learn/javafx/install/#javafx-windows)

* Apache Maven: [https://maven.apache.org/install.html](https://maven.apache.org/install.html)

Environment variables (examples on Windows):

* 🛠️ `JAVA_HOME=C:\Program Files\Java\jdk-24`
* 🛠️ `PATH_TO_FX=C:\Program Files\Java\javafx-sdk-25`
* 🛠️ `PATH+=C:\Program Files\Java\jdk-24\bin`
* 🛠️ `MAVEN_HOME=C:\Program Files\Apache\Apache Maven`
* 🛠️ `PATH+=C:\Program Files\Apache\Apache Maven\bin`

VS Code Java runtime:

* Ctrl + Shift + P → “Java: Clean Java Language Server Workspace”
* Ctrl + Shift + P → “Java: Configure Java Runtime”
* Under JDKs, add `C:\Program Files\Java\jdk-24` and set it as Default
* In the same panel, set JDK for Language Server to JDK 24
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
mvn -pl maze -am clean javafx:run -Djavafx.platform=windows
```

## Debug

1. Verify the mirror really contains the platform feature and runtime
   • In `releng/mirror/pom.xml`, make sure you mirror at least one umbrella feature that pulls the platform units:

```xml
<iu><id>org.eclipse.platform.feature.group</id></iu>
<!-- or -->
<iu><id>org.eclipse.rcp.feature.group</id></iu>
```

• After `mvn -f releng/mirror/pom.xml -U verify`, confirm presence:

```powershell
Select-String releng\local-p2\content.* -Pattern 'org\.eclipse\.platform\.feature\.group|org\.eclipse\.core\.runtime'
```

If this prints nothing, the mirror selection is too narrow.

2. Ensure Tycho actually uses your target and mirror
   • Root POM must have `target-platform-configuration` pointing to `releng/maze.target` and `pomDependencies` set to `ignore`.
   • `maze.target` must reference `file:./local-p2` (relative to `releng/`).
   • Always clear Tycho p2 cache before retesting:

```powershell
Remove-Item -Recurse -Force "$Env:USERPROFILE\.m2\repository\.cache\tycho"
```

3. Make feature and plug-in ids match the bundles
   • In `feature.xml`, use the bundle symbolic names (the `Bundle-SymbolicName` from each plug-in’s `MANIFEST.MF`), not folder names.

4. Common causes of “requires org.eclipse.core.runtime … but it could not be found”
   • Mirror missing `org.eclipse.platform.feature.group` or `org.eclipse.rcp.feature.group`.
   • Target not loaded by Tycho (wrong path, not referenced, cache not cleared).
   • Building offline without a complete local mirror. Use `-o` only after the mirror is verified.

5. Extra diagnostics when resolution fails

```bash
mvn -X -Dtycho.debug.resolver=true -Dtycho.p2.transport.min-cache-minutes=0 clean verify
```

This prints the exact IUs Tycho is trying to resolve and from which repositories.

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
     Command:
     `mvn -f releng/mirror/pom.xml -U verify`

2. releng/maze.target points Tycho to that local p2 so dependency resolution is stable and offline friendly.
     Command used when building (Tycho reads the target): same as in step 3.

3. The root build starts. Tycho reads releng/maze.target to resolve Eclipse plug in and feature dependencies.
     Command:
     `mvn -U -DskipTests=false clean verify`

4. movements-module compiles as an Eclipse plug in that provides movement behavior code.
     Command:
     `mvn -pl movements-module -am -DskipTests=false clean verify`

5. difficulty-module compiles as an Eclipse plug in that provides the Ecore model, OCL, and related runtime for difficulties.
     Command:
     `mvn -pl difficulty-module -am -DskipTests=false clean verify`

6. opponents-module compiles as an Eclipse plug in that depends on difficulty-module and EMF runtime.
     Command:
     `mvn -pl opponents-module -am -DskipTests=false clean verify`

7. maze-feature collects the project’s Eclipse plug ins into one installable feature.
     Command:
     `mvn -pl maze-feature -am -DskipTests=false clean verify`

8. maze-repository assembles a p2 update site that contains the feature and the plug ins for installation in an Eclipse based product or workspace.
     Command:
     `mvn -pl maze-repository -am -DskipTests=false clean verify`

9. maze-generator.acceleo runs headless during the generate sources phase, launches the Acceleo application, reads the model input, and writes Java sources into maze-generated.
     Command:
     `mvn -pl maze-generator.acceleo -am -DskipTests clean verify`

10. maze-generated is a plain jar module that compiles the generated sources and publishes a stable artifact.
      Command:
      `mvn -pl maze-generated -am -DskipTests=false clean verify`

11. main.game.maze compiles the JavaFX application, depends on the jar from maze-generated, and runs unit tests.
      Command:
      `mvn -pl maze -am -Djavafx.platform=windows -DskipTests=false clean verify`
      (or use `-Djavafx.platform=linux` on Linux runners)

12. The build outputs two deliverables: the JavaFX game artifacts from the Maven side and an installable p2 repository from the Tycho side.
      Command to produce both in one go: same as in step 3.

## The modules

### - movements-module

Movement behaviors for characters and utilities used by the game loop. See the module guide: [movements-module/README.md](movements-module/README.md).

### - difficulty-module

Ecore model and logic for difficulty profiles, defaults, and validations. See the module guide: [difficulty-module/README.md](difficulty-module/README.md).

### - opponents-module

Ecore model and runtime helpers for enemies, threat values, and validation rules. See the module guide: [opponents-module/README.md](opponents-module/README.md).

### - maze

The JavaFX application code and entry point for running the game. See the module guide: [maze/README.md](maze/README.md).

### - maze-feature

Eclipse feature that groups the plug-ins for p2 builds. See the module guide: [maze-feature/README.md](maze-feature/README.md).

### - maze-repository

The p2 update site produced by Tycho for the Eclipse artifacts. See the module guide: [maze-repository/README.md](maze-repository/README.md).

### - maze-generator.acceleo

Headless Acceleo generator that turns models into source code for the game. See the module guide: [maze-generator.acceleo/README.md](maze-generator.acceleo/README.md).

### - maze-generated

The generated Java sources and jar produced by the Acceleo step. See the module guide: [maze-generated/README.md](maze-generated/README.md).

### - project-status

Course and project status notes for context and progress tracking. See: [project-status/README.md](project-status/README.md).

### - releng

Build infrastructure, local p2 mirror, and target platform. See: [releng/README.md](releng/README.md).

