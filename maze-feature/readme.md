# maze-feature

Eclipse feature that groups the Maze game plugins for build and distribution.

## Overview

This feature collects the runtime plugins so they can be built together and published into a p2 repository. The feature is what Eclipse installs; the repository is where the feature is published.

## What command makes this happen

To build the feature itself:
```bash
mvn -pl maze-feature -am clean verify
````

To assemble the p2 repository that contains this feature:

```bash
mvn -pl maze-module-repository -am clean verify
```

The second command is the one that actually produces the installable p2 site including this feature.

## Contents

* main.game.maze.behaviour plugin
* main.game.maze.difficulties plugin
* main.game.maze.opponents plugin

## Build

From the repository root:

```bash
mvn -pl maze-feature -am clean verify
```

## Output

The built feature is consumed by the p2 repository produced by the `maze-module-repository` module:

```bash
mvn -pl maze-module-repository -am clean verify
```

The repository is written to `maze-module-repository/target/repository/`.

## Use in Eclipse

* Add the p2 repository from `maze-module-repository/target/repository/`.
* Install the “Maze Feature” into your Eclipse instance.

## Versioning note

The feature uses version `1.0.0.qualifier`, and the plugin entries use version `0.0.0` so Tycho resolves the highest available versions during the build.

---

## Relationship to other modules

- **mazer-module-generator**
  - Output destination. The generator writes Java sources into `mazer-module-generator/src/main/java`.
  - The `mazer-module-generator` module then packages those sources into a jar that other modules can depend on.
  - Typical run that produces sources:
    ```bash
    mvn -pl maze-generator.acceleo -am -DskipTests clean verify
    ```

- **main.game.maze**
  - Consumer of the generated jar. The app compiles and runs against the classes packaged by `mazer-module-generator`.
  - After generation, build the app:
    ```bash
    mvn -pl maze -am -DskipTests=false clean verify
    ```

- **releng**
  - Provides the Eclipse target and optional local p2 mirror used to run Acceleo headless in a stable and offline friendly way.
  - If you refresh the mirror or target, regenerate to ensure the generator runs against the same platform:
    ```bash
    mvn -f releng/mirror/pom.xml -U verify
    mvn -pl maze-generator.acceleo -am -DskipTests clean verify
    ```

- **main.game.maze.behaviour, main.game.maze.difficulties, main.game.maze.opponents**
  - Independent of the generator outputs. These are Eclipse plug ins and do not consume the generated jar directly.
  - Changes in the metamodel or OCL within `main.game.maze.difficulties` can affect templates and the produced sources. Regenerate after such changes.

- **maze-feature and maze-module-repository**
  - The generator and the generated jar are not published to the p2 site. They are plain Maven artifacts.
  - The feature and repository collect only Eclipse plug ins and features. Build order can still include the generator so that the app side has fresh sources before any end to end build.

### End to end flow

```
models/*.xmi + templates
│
▼
maze-generator.acceleo  —(writes Java)→  mazer-module-generator  —(jar)→  main.game.maze
▲
│
releng target and mirror provide the headless Eclipse runtime for the generator

```
