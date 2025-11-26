# maze-module-repository

## Overview
This module builds a p2 repository that contains the Maze Game feature and its Eclipse plugins. The repository is a consumable update site that Eclipse can install from and Tycho can resolve against during builds.

## Who uses this and why
- **Eclipse (developers and testers)** use the repository to install the “Maze Feature” into an IDE or product for manual testing, model editing, or running the headless generator.
- **Tycho (CI and local builds)** uses the repository as a dependency source so OSGi bundles and features resolve deterministically. This keeps the build stable and reproducible.
- **Other teams or machines** can consume the repository artifact without contacting public update sites, which supports offline or pinned builds.

## Build
From the repository root:
```bash
mvn -pl maze-module-repository -am -DskipTests clean verify
````

## Output

The built p2 repository is written to:

```
maze-module-repository/target/repository/
```

## How others consume it

### Use in Eclipse

1. Open Eclipse.
2. Add a new software site with:

   ```
   file:/absolute/path/to/maze-module-repository/target/repository/
   ```
3. Install the **Maze Feature** (feature group).

### Use in Tycho (as a p2 repository)

Add a p2 repository entry that points to the built site:

```xml
<repositories>
  <repository>
    <id>maze-local</id>
    <layout>p2</layout>
    <url>file:${maven.multiModuleProjectDirectory}/maze-module-repository/target/repository</url>
  </repository>
</repositories>
```

### Use in Tycho target-platform-configuration

If you manage repositories via the Tycho target configuration, reference the site there so all Eclipse parts resolve from it.

## Relationship to other modules

- **maze-feature → maze-module-repository**
  - The feature is the payload this repository publishes. When you build the repository, it picks up the feature jar and its included plugins and writes them into the p2 site under `target/repository`.
  - Rebuild the feature whenever any included plugin changes, then rebuild the repository so Eclipse and Tycho see the updated units.
  - Typical sequence:
    ```bash
    mvn -pl maze-feature -am clean verify
    mvn -pl maze-module-repository -am clean verify
    ```

- **main.game.maze.behaviour, main.game.maze.difficulties, main.game.maze.opponents → maze-feature → maze-module-repository**
  - These are Eclipse plugins. The feature lists them. The repository packages the feature and these plugins into installable units.
  - If any of these plugins change, rebuild the feature and then the repository so the site contains the fresh versions.

- **releng (mirror and target) ↔ Tycho parts (plugins, feature, repository)**
  - The releng folder can build a local mirror and define a target platform that Tycho uses to resolve dependencies consistently and offline. While the repository you are reading about publishes your output, the releng target tells Tycho where to look for inputs during the build.
  - You can either resolve against public update sites through releng, or you can point Tycho at the locally built repository in `maze-module-repository/target/repository` for self contained builds.

- **mazer-module-generator and maze (plain Maven modules)**
  - These are not installed from p2 and do not get published in this repository. They are built and consumed through normal Maven dependency resolution.
  - The game app in `maze` may still be built in the same reactor as the Tycho parts, but it does not consume anything from this p2 site.

### Data flow summary
```
[main.game.maze.behaviour] 
[main.game.maze.difficulties]  >--> [maze-feature] --> [maze-module-repository p2 site]
[main.game.maze.opponents]  /

releng (target and mirror) --> provides input repositories for Tycho resolution

maze-generator.acceleo-runner --> writes sources --> [mazer-module-generator jar] --> used by [maze app]
```

### Who consumes the repository

- **Eclipse** installs the Maze Feature from `maze-module-repository/target/repository` for local testing and tool support.
- **Tycho builds** can list this repository as a p2 source so they resolve your feature and plugins without contacting public update sites.



## Clean

```bash
mvn -pl maze-module-repository clean
```

## Notes

* Build the feature first if you need to ensure the newest feature binary is published into the repository:

  ```bash
  mvn -pl maze-feature -am clean verify
  ```

  Then run the repository build:

  ```bash
  mvn -pl maze-module-repository -am clean verify
  ```
* In CI, you can upload the `maze-module-repository/target/repository/` folder as an artifact for other jobs to consume.