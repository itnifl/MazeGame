# MazeGame DSL UI Module

Eclipse-specific editor integration for the MazeGame Domain-Specific Language.

## Purpose

This module provides the **Eclipse IDE integration** for the MazeGame DSL. When installed into Eclipse, it enables rich editing features for `.mazegame` files within the Eclipse workbench.

## Features Provided

| Feature | Description |
|---------|-------------|
| **Syntax Highlighting** | Color-coded keywords, strings, numbers, and comments |
| **Content Assist** | Ctrl+Space proposals with context awareness |
| **Outline View** | Hierarchical tree view of game elements |
| **Quick Fixes** | One-click solutions for validation errors |
| **Folding** | Collapse/expand game and opponent blocks |
| **Formatting** | Auto-format DSL files to consistent style |
| **Hyperlinking** | Ctrl+Click navigation to definitions |

## Module Structure

```
main.game.maze.dsl.ui/
├── META-INF/
│   └── MANIFEST.MF          # OSGi bundle manifest
├── src/
│   └── main/
│       ├── java/            # Custom UI services
│       │   └── main/game/maze/dsl/ui/
│       │       ├── MazeDslOutlineTreeProvider.java
│       │       ├── MazeDslQuickfixProvider.java
│       │       └── MazeDslHighlightingConfiguration.java
│       └── xtext-gen/       # Generated Eclipse integration
├── build.properties         # Tycho build configuration
├── plugin.properties        # Bundle metadata
├── plugin.xml               # Eclipse extension points
└── pom.xml                  # Maven/Tycho build file
```

## Key Classes

| Class | Purpose |
|-------|---------|
| `MazeDslOutlineTreeProvider` | Custom outline view structure |
| `MazeDslQuickfixProvider` | Quick fix proposals for validation errors |
| `MazeDslHighlightingConfiguration` | Syntax highlighting rules |
| `MazeDslProposalProvider` | Custom content assist proposals |

## Eclipse Extension Points

The `plugin.xml` registers:

- Editor for `.mazegame` file extension
- Outline page provider
- Content assist processor
- Quick fix processor
- Syntax highlighting

## Build

This module is built as part of the main reactor:

```bash
mvn clean verify
```

## Dependencies

- `main.game.maze.dsl` - Core grammar and runtime
- `main.game.maze.dsl.ide` - IDE services
- Xtext UI libraries (2.42.0)
- Eclipse Platform UI

## Installing in Eclipse

1. Build the project: `mvn clean verify`
2. Locate the update site: `maze-module-repository/target/repository/`
3. In Eclipse: Help → Install New Software → Add → Local → Select the repository folder
4. Select "MazeGame DSL Feature" and complete the installation

## Related Documentation

| Document | Description |
|----------|-------------|
| [DSL Core Module](../main.game.maze.dsl/readme.md) | Core grammar and runtime |
| [DSL IDE Module](../main.game.maze.dsl.ide/readme.md) | Language server |
| [DSL Tests](../main.game.maze.dsl.tests/readme.md) | Test suite |
| [Xtext Setup Guide](../docs/xtext-readme.md) | Build and development setup |
| [DSL Tutorial](../docs/dsl-tutorial.md) | Getting started guide |
