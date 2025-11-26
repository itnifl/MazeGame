# The Eclipse modules are consumed in two different "worlds":

1. 🔧 **Eclipse / Tycho world** (EMF, OCL, Acceleo, p2, targets)
2. 🎮 **Maze game world** (plain Java / Maven / JavaFX)

The trick is: the same EMF code lives as **Eclipse plug-ins** in the first world, but ends up as **plain JARs** in the second.

---

## 1. Build side: Eclipse modules as `eclipse-plugin`

These modules are pure Eclipse / Tycho:

* `main.game.maze.behaviour` → bundle `main.game.maze.behaviour`
* `main.game.maze.difficulties` → bundle `main.game.maze.difficulties`
* `main.game.maze.opponents` → bundle `main.game.maze.opponents`
* `maze-generator.acceleo-runner` → Acceleo templates + app id
* `maze-generator.runner` → headless Equinox runner

Tycho builds them like this:

```bash
mvn clean verify        # from root
```

Tycho reads the **target** (from `releng`) that points to `releng/local-p2`, resolves EMF/OCL/Acceleo, and produces **OSGi bundles** (JARs with `META-INF/MANIFEST.MF`, `Bundle-SymbolicName`, etc.).

So after the build, you effectively have JARs for:

* `main.game.maze.behaviour`
* `main.game.maze.difficulties`
* `main.game.maze.opponents`
* `maze-generator.acceleo-runner`
* `maze-generator.runner`

These are still “Eclipse modules”, but they are also just JARs in the Maven reactor.

---

## 2. Runtime side: Maze game as plain Maven module

The **`maze` module** (the JavaFX app) is a normal Maven module.
It does *not* run OSGi; it just depends on those JARs as regular libraries.

In the `maze/pom.xml`, you’ll have dependencies like:

```xml
<dependency>
  <groupId>main.game.maze</groupId>
  <artifactId>main.game.maze.difficulties</artifactId>
  <version>${project.version}</version>
</dependency>

<dependency>
  <groupId>main.game.maze</groupId>
  <artifactId>main.game.maze.opponents</artifactId>
  <version>${project.version}</version>
</dependency>

<dependency>
  <groupId>main.game.maze</groupId>
  <artifactId>main.game.maze.behaviour</artifactId>
  <version>${project.version}</version>
</dependency>

<!-- plus whatever generated code module (maze-module-generator / maze-generated) -->
```

Because everything is part of the **same multi-module Maven build**, Maven can just use those artifacts like any other module:

* Java compiler sees `main.game.maze.difficulties.*` and `main.game.maze.opponents.*` classes
* The game can load XMI files using EMF (which comes transitively from those modules)
* You get OCL-backed validation for free because the generated EMF+OCL code is in those bundles

From the game’s point of view, there is **no p2 or OSGi**. It’s just:

```java
import main.game.maze.difficulties.*;
import main.game.maze.opponents.*;
// etc.
```

---

## 3. Where the bridge actually is

Conceptual pipeline:

1. `releng/mirror`
   → fills `releng/local-p2` with EMF/OCL/Acceleo/etc.

2. Tycho builds the Eclipse modules
   → `main.game.maze.behaviour`, `main.game.maze.difficulties`, `main.game.maze.opponents`, `maze-generator.*`

3. `maze-module-generator` (or `maze-generated`)
   → gets generated Java from Acceleo and exposes it as another Maven JAR

4. `maze` module depends on:

   * EMF model modules (difficulty/opponents/movements)
   * Generated-code module
   * JavaFX

So the **Eclipse modules are consumed by the maze game simply as Maven dependencies**. Tycho makes sure they exist and are consistent; Maven makes sure the game sees them as plain libraries.
