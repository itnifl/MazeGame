# Opponents Module

## Overview

This module defines the opponent metamodel and generated code for enemies in the Maze Game. It includes EMF models, OCL constraints, validators, and utilities used by the game runtime.

## Build

From the repository root:

```bash
mvn -pl opponents-module -am -DskipTests clean verify
```

## Test

Run unit tests for this module:

```bash
mvn -pl opponents-module -DskipTests=false test
```

## What it provides

* EMF model and implementation for opponents
* OCL based validation and derived values
* Utilities for loading opponent data at runtime

## Output

The built jar is created at:

```
opponents-module/target/main.game.maze.opponents-1.0.0-SNAPSHOT.jar
```

## Clean

```bash
mvn -pl opponents-module clean
```