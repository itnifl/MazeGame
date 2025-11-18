# Releng

## Overview

This folder contains build infrastructure for the Eclipse and Tycho setup. It provides a local p2 mirror and the target platform used by the plug in modules.

## Layout

* `releng/mirror/pom.xml` p2 mirror definition
* `releng/local-p2/` local p2 repository created by the mirror
* `releng/maze.target` target platform that points to the local p2 repository

## Refresh the local p2 mirror

```bash
mvn -f releng/mirror/pom.xml -U verify
```

## Clean the mirror and rebuild

```bash
rm -rf releng/local-p2
mvn -f releng/mirror/pom.xml -U verify
```

## Use the target platform in Maven

The root `pom.xml` already points Tycho to `releng/maze.target`. No extra action is required in module poms.

## Notes

* Always run the mirror step before a full reactor build when you change the set of required Eclipse units.
* Commit `releng/mirror/pom.xml` and `releng/maze.target`. Do not commit `releng/local-p2/`.
