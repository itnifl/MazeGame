# maze repository

## Overview

This module builds a p2 repository that contains the Maze Game plugins and features. You can point Eclipse or Tycho at this repository to install or resolve the artifacts.

## Build

From the repository root:

```bash
mvn -pl maze-repository -am -DskipTests clean verify
```

## Output

The built p2 repository is written to:

```
maze-repository/target/repository/
```

## Use in Eclipse

1. Open Eclipse.
2. Add a new software site with the location:

   ```
   file:/absolute/path/to/maze-repository/target/repository/
   ```
3. Install the maze feature group.

## Use in Tycho

In a Tycho build, add a p2 repository entry that points to the built site:

```xml
<repositories>
  <repository>
    <id>maze-local</id>
    <layout>p2</layout>
    <url>file:${project.basedir}/../maze-repository/target/repository</url>
  </repository>
</repositories>
```

## Clean

```bash
mvn -pl maze-repository clean
```
