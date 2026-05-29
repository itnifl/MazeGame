# The Eclipse modules are consumed in two different “worlds”

1. 🔧 **Eclipse / Tycho world** (EMF, OCL, FreeMarker, p2, targets)  
2. 🎮 **Maze game world** (plain Java / Maven / JavaFX)

The same EMF based code is built as **Eclipse plug-ins** in the first world  
and reused as **plain JARs** in the second.

---

## １．Build side: Eclipse modules as `eclipse-plugin`

These modules live in the Eclipse / Tycho world:

- `main.game.maze.behaviour` → bundle `main.game.maze.behaviour`  
- `main.game.maze.difficulties` → bundle `main.game.maze.difficulties`  
- `main.game.maze.opponents` → bundle `main.game.maze.opponents`  
- `maze-generator.freemarker` → FreeMarker templates (no UI, pure generator logic)  
- `maze-generator.freemarker-runner` → headless Equinox runner that starts FreeMarker

Tycho builds them from the root with:

```bash
mvn clean verify
```

Tycho:

* reads the **target** definition from `releng` (which points at `releng/local-p2`)
* resolves EMF, OCL and FreeMarker from the local mirror
* compiles the plug-ins as **OSGi bundles** (JARs with `META-INF/MANIFEST.MF`, `Bundle-SymbolicName`, etc.)

After the build you effectively have JARs for:

* `main.game.maze.behaviour`
* `main.game.maze.difficulties`
* `main.game.maze.opponents`
* `maze-generator.freemarker`
* `maze-generator.freemarker-runner`

From Tycho’s point of view these are Eclipse plug-ins.
From Maven’s point of view they are also just normal reactor artifacts.

---

## ２．Runtime side: Maze game as plain Maven module

The **`maze`** module (the JavaFX app) is a normal Maven module.
It does not run OSGi; it just depends on the JARs as regular libraries.

In `maze/pom.xml` you will see dependencies like:

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

<!-- plus the generated code module -->
<dependency>
  <groupId>main.game.maze</groupId>
  <artifactId>maze-module-generator</artifactId>
  <version>${project.version}</version>
</dependency>
```

Because everything is part of the **same multi module Maven build**, the game can treat those bundles like any other JAR:

* the Java compiler sees `main.game.maze.difficulties.*`, `main.game.maze.opponents.*`, `main.game.maze.behaviour.*`
* EMF and OCL are available transitively from those modules
* the game can load XMI models and get validation from the generated EMF plus OCL code

From the game’s point of view there is **no p2 and no OSGi** at runtime. It is simply:

```java
import main.game.maze.difficulties.*;
import main.game.maze.opponents.*;
import main.game.maze.behaviour.*;
// etc.
```

---

## ３．Where the bridge actually is

Conceptual pipeline:

1. `releng/mirror`
   fills `releng/local-p2` with EMF, OCL and related Eclipse bundles.

2. Tycho builds the Eclipse modules
   `main.game.maze.behaviour`, `main.game.maze.difficulties`, `main.game.maze.opponents`, `maze-generator.freemarker`, `maze-generator.freemarker-runner`.

3. `maze-module-generator`
   runs the FreeMarker-based generators (via the runner), receives the generated Java sources in `src-gen`,
   and exposes them as a normal Maven JAR.

   Currently generated classes include:
   - `OpponentRegistry` — lists all enemy types with their stats
   - `CharacterRegistrar` — character type registration and lookup
   - `CharacterAttributeSetter` — difficulty multiplier application
   - `CharacterGraphicsFactory` — sprite path and animation metadata
   - `WallRegistry` — lists all wall material types
   - `WallMaterialRenderer` — renders walls by material type
   - `WallCollisionHandler` — handles wall collision logic

   **Note**: FreeMarker templates are located in `maze-generator.freemarker/src/main/resources/templates/`.

4. `maze` depends on:

   * the EMF model modules (`main.game.maze.behaviour`, `main.game.maze.difficulties`, `main.game.maze.opponents`)
   * the generated code module (`maze-module-generator`)
   * JavaFX and other runtime libraries

So the **Eclipse modules are consumed by the Maze game simply as Maven dependencies**.
Tycho and p2 ensure the plug-ins and generator exist and are consistent in the build world,
while Maven makes sure the game sees them as plain Java libraries in the runtime world.

---

## Related Documentation

| Document | Description |
|----------|-------------|
| [Technology Layman's Guide](docs/technology-laymans-guide.md) | Simple explanation of the technologies in everyday terms |
| [FreeMarker Guide](freemarker.readme.md) | Detailed FreeMarker usage in this project |
| [Model-Driven Code Generation Plan](docs/mdd-code-generation.md) | Architecture for generating code from models |
| [Releng Documentation](releng/readme.md) | Build infrastructure and target platform |
