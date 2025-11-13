Here is an updated `maze-feature/README.md` you can drop in:

````markdown
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
mvn -pl maze-repository -am clean verify
```

The second command is the one that actually produces the installable p2 site including this feature.

## Contents

* movements-module plugin
* difficulty-module plugin
* opponents-module plugin

## Build

From the repository root:

```bash
mvn -pl maze-feature -am clean verify
```

## Output

The built feature is consumed by the p2 repository produced by the `maze-repository` module:

```bash
mvn -pl maze-repository -am clean verify
```

The repository is written to `maze-repository/target/repository/`.

## Use in Eclipse

* Add the p2 repository from `maze-repository/target/repository/`.
* Install the “Maze Feature” into your Eclipse instance.

## Versioning note

The feature uses version `1.0.0.qualifier`, and the plugin entries use version `0.0.0` so Tycho resolves the highest available versions during the build.

---

## How this differs from `maze-generated` and how they relate

* **maze-feature** is an Eclipse Tycho artifact. It groups OSGi plugins so they can be installed into Eclipse via p2. It lives on the Eclipse side of the build.
* **maze-generated** is a plain Maven jar that contains Java sources created by the Acceleo generator. It is consumed by the JavaFX application (`main.game.maze`) and by any unit tests that need the generated classes. It lives on the regular Maven side of the build.

There is no direct dependency between `maze-feature` and `maze-generated`. The feature bundles OSGi plugins for Eclipse installation, while the generated jar is a standard Maven artifact used by the app. They serve different packaging targets and are built by different commands:

* Feature and p2: `mvn -pl maze-feature -am clean verify` then `mvn -pl maze-repository -am clean verify`
* Generated jar: `mvn -pl maze-generated -am clean verify` (typically produced after running `maze-generator.acceleo`)

```
::contentReference[oaicite:0]{index=0}
```