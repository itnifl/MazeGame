# MazeGame DSL IDE Module

Language server support for the MazeGame Domain-Specific Language.

## Purpose

This module provides the **IDE-agnostic** language server implementation for the MazeGame DSL. It enables editors and IDEs that support the Language Server Protocol (LSP) to provide rich editing features for `.mazegame` files.

## Features Provided

| Feature | Description |
|---------|-------------|
| **Code Completion** | Context-aware suggestions for keywords, types, and references |
| **Hover Information** | Tooltips showing documentation and type information |
| **Go to Definition** | Navigate to referenced elements |
| **Find References** | Locate all usages of a symbol |
| **Rename Refactoring** | Safely rename symbols across the workspace |
| **Diagnostics** | Real-time error and warning reporting |

## Module Structure

```
main.game.maze.dsl.ide/
├── META-INF/
│   └── MANIFEST.MF          # OSGi bundle manifest
├── src/
│   └── main/
│       ├── java/            # Custom IDE services
│       └── xtext-gen/       # Generated LSP infrastructure
├── build.properties         # Tycho build configuration
├── plugin.properties        # Bundle metadata
└── pom.xml                  # Maven/Tycho build file
```

## Key Classes

| Class | Purpose |
|-------|---------|
| `MazeDslIdeSetup` | Entry point for IDE infrastructure setup |
| `MazeDslIdeModule` | Guice module for IDE service bindings |
| `MazeDslContentAssistService` | Content assist proposals |

## Build

This module is built as part of the main reactor:

```bash
mvn clean verify
```

Or build just the DSL modules:

```bash
mvn -f main.game.maze.dsl/pom.xml generate-sources -DskipTests
mvn clean verify
```

## Dependencies

- `main.game.maze.dsl` - Core grammar and runtime
- Xtext IDE libraries (2.42.0)
- Eclipse LSP4J for Language Server Protocol

## Related Documentation

| Document | Description |
|----------|-------------|
| [DSL Core Module](../main.game.maze.dsl/readme.md) | Core grammar and runtime |
| [DSL UI Module](../main.game.maze.dsl.ui/readme.md) | Eclipse integration |
| [DSL Tests](../main.game.maze.dsl.tests/readme.md) | Test suite |
| [Xtext Setup Guide](../docs/xtext-readme.md) | Build and development setup |
| [DSL Reference](../docs/dsl-reference.md) | Complete syntax documentation |
