````markdown
# maze-module-repository

This project defines the Eclipse p2 repository for the MazeGame modules.

While individual plugins and features live in their own projects (for example `main.game.maze.opponents`, `main.game.maze.difficulties`, `maze-feature`), this project is responsible for packaging them into an installable update site that Eclipse and Tycho can consume.

---

## Purpose

`maze-module-repository` is used to

- assemble one or more features (for example `maze-feature`) into a p2 repository  
- define categories that group features in the Eclipse “Install New Software…” dialog  
- provide a stable update site URL for MazeGame tooling  
- act as a source repository that can be mirrored by `releng/mirror` into `releng/local-p2`

In short, it answers the question:

> “Where can Eclipse install MazeGame tooling from?”

---

## Typical contents

Inside `maze-module-repository` you normally find:

- `category.xml`  
  Defines which features belong in this repository and how they are grouped into categories such as “MazeGame Tools” or “MazeGame Runtime”.

- `pom.xml`  
  Tycho build configuration for producing the p2 repository under `target/repository`.
  It references the features that should be included (for example `maze-feature`).

- Optional metadata files  
  Such as `p2.inf` or additional branding resources, if needed.

The actual p2 repository (artifacts and metadata) is generated into:

```text
maze-module-repository/target/repository/
````

This folder contains `artifacts.jar`, `content.jar` and the feature/plugin artifacts.

---

## Role in the Tycho build

During a full Tycho build of the MazeGame reactor:

1. All MazeGame plugins and features (for example `maze-feature`) are built.
2. `maze-module-repository` runs and assembles those features into a p2 repository under `target/repository`.
3. This repository can be:

   * consumed directly by Eclipse as an update site
   * referenced from target definitions such as `releng/maze.target`
   * mirrored by `releng/mirror` into `releng/local-p2` for offline builds

Because the repository is produced as part of the normal build, it is always in sync with the current versions of the features and plugins.

---

## Using the repository in Eclipse

After building `maze-module-repository` with Maven/Tycho:

```bash
mvn -f maze-module-repository/pom.xml clean verify
```

you can install MazeGame tooling into Eclipse by:

1. Opening **Help → Install New Software…**
2. Clicking **Add…** and pointing the location to
   `path/to/MazeGame/maze-module-repository/target/repository`
3. Selecting the category that contains the MazeGame features (for example “MazeGame Tools”)
4. Completing the installation wizard and restarting Eclipse

For shared use you can publish the contents of `target/repository` to a web server and use the HTTP URL instead of a local file path.

---

## Using the repository in target definitions

Target files such as `releng/maze.target` can reference the repository produced by `maze-module-repository`:

* When the repository is local (for development), use a `file:` URL pointing at `maze-module-repository/target/repository`.
* When the repository is published (for teams or CI), use the HTTP URL of the hosted update site.

This way all developers and builds resolve MazeGame features from a single, versioned location.

---

## When to modify maze-module-repository

You should update this project when you:

* add a new feature that should be part of the MazeGame update site
* remove or deprecate an existing feature
* want to change the categories or labels shown in Eclipse
* adjust version ranges or include rules for the features in the repository

Typical steps:

1. Edit `category.xml` to include or remove features and to adjust category names.
2. Update `pom.xml` if new features are introduced or project structure changes.
3. Run `mvn clean verify` to regenerate `target/repository`.
4. Test installation in Eclipse from the updated repository.

---

## Design guidelines

When maintaining `maze-module-repository`, keep these principles in mind:

* Treat it as the public entry point for MazeGame tooling
  Only include features that you intend users or developers to install.

* Keep category names clear and concise
  They appear directly in the Eclipse installation UI.

* Keep the repository reproducible
  All content should be produced by the Tycho build, not manually copied into `target/repository`.

By following these guidelines, `maze-module-repository` provides a clean, predictable update site for the MazeGame modules that works well both for local development and for shared distribution.

