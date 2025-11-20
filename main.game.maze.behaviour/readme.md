# Movements Module

## Overview

This module contains reusable movement logic for the Maze Game characters. It provides utilities, actions, and services that handle position updates, timing, and movement notifications.

## Build

From the repository root:

```bash
mvn -pl movements-module -am -DskipTests clean verify
```

## Test

Run unit tests for this module:

```bash
mvn -pl movements-module -DskipTests=false test
```

## What it provides

* Core movement utilities for characters
* Movement notifier action and related interfaces
* Resources required by the game runtime

## Output

The built jar is created at:

```
movements-module/target/main.game.maze.behaviour-1.0.0-SNAPSHOT.jar
```

## Clean

```bash
mvn -pl movements-module clean
```
