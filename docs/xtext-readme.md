# Xtext Setup and Learning Guide for MazeGame

This guide explains how Xtext is wired in this repository, how to run it reliably, and what to study next if you want to understand and extend the setup.

## Who this guide is for

- Team members who need to build the project with DSL support.
- Team members who need to change the Maze DSL grammar.
- Team members who want to understand how the DSL, EMF models, and generated code fit together.

## Important decisions in this repository

- Xtext generated artifacts under `src/main/xtext-gen` and `src/test/xtext-gen` are currently committed in this repository.
- CI workflows now include the Maze DSL modules `main.game.maze.dsl`, `main.game.maze.dsl.ide`, `main.game.maze.dsl.ui`, and `main.game.maze.dsl.tests` in the Tycho build step.
- Generation of DSL artifacts is performed via Maven using an MWE2 workflow in the DSL module. CI validates the DSL modules in the Tycho build step, while local regeneration is still done from the DSL module build.
- Use Java 21 for Xtext generation and for Tycho/reactor builds that include DSL modules.
- CI uses Java 24 for the Tycho DSL build step in `.github/workflows/main.yml` and `.github/workflows/buildtest.yml`.
   Java 24 is used for both DSL and game-focused jobs. The DSL generation and Xtext dependency graph is validated with Java 21 locally.
- FreeMarker is used for code generation (not Acceleo).

## Where Xtext lives in the repo

- Core grammar and runtime: main.game.maze.dsl
- IDE language server support: main.game.maze.dsl.ide
- Eclipse UI integration: main.game.maze.dsl.ui
- DSL tests: main.game.maze.dsl.tests
- Grammar file: main.game.maze.dsl/src/main/java/main/game/maze/dsl/MazeDsl.xtext
- Workflow file: main.game.maze.dsl/src/main/java/main/game/maze/dsl/GenerateMazeDsl.mwe2

## One-time environment setup

1. Install Maven 3.9.6 or newer.
2. Ensure Java 21 is installed.
3. Ensure the releng local p2 mirror can be resolved as documented in the root readme.
4. If VS Code Java tooling points to a different runtime, still run Maven commands with Java 21 for DSL builds.

## Build and generation flow

### Recommended command sequence on Windows PowerShell

1. Set Java 21 for this shell session.
   - $env:JAVA_HOME = "C:\\Path\\To\\jdk-21"
   - $env:Path = "$env:JAVA_HOME\\bin;" + $env:Path
2. Generate DSL sources.
   - mvn -f main.game.maze.dsl/pom.xml generate-sources -DskipTests
3. Build from root.
   - mvn -U clean verify

This generation is wired in the DSL module build using the MWE2 launcher and runs in generate-sources.

## Why Java 21 locally

The DSL generation and Tycho/Xtext dependency graph in this repository is validated with Java 21 locally. CI uses Java 24 for all workflows including DSL builds. Local regeneration of DSL artifacts using MWE2 should be done with Java 21 to ensure consistent results. Once regenerated, commits can be validated by CI with Java 24.

## What is generated and how it is handled

Generation creates parser/runtime/editor artifacts under module source trees (for example `src-gen` and `src/main/xtext-gen`). These are reproducible outputs from grammar and workflow inputs.

Current repository strategy:

1. Generated Xtext artifacts are committed to keep CI and local builds consistent.
2. Regenerate when grammar/workflow changes are made.
3. Review generated diffs together with grammar changes to avoid drift.

## Changing the DSL safely

1. Edit grammar in MazeDsl.xtext.
2. Run generation command for main.game.maze.dsl.
3. Build root reactor with Java 21.
4. Run module tests and root tests as needed.
5. Verify downstream game configuration consumers still work.

## Common failure patterns and fixes

- Problem: Could not find workflow module.
  - Fix: Confirm GenerateMazeDsl.mwe2 location and launcher argument in main.game.maze.dsl/pom.xml.
- Problem: File not found for MazeDsl.xtext in workflow.
  - Fix: Confirm grammar path referenced by workflow language configuration.
- Problem: Cannot resolve ecore datatypes like EDouble.
  - Fix: Ensure MazeDsl.xtext imports Ecore metamodel with alias ecore.
- Problem: Inconsistent Xtext bundle behavior.
  - Fix: Keep Xtext module versions aligned across DSL, IDE, UI, and tests.

## What to read next

1. Root architecture and build flow
   - readme.md
   - releng/readme.md
2. DSL usage and examples
   - main.game.maze.dsl/readme.md
   - docs/dsl-reference.md
   - docs/dsl-tutorial.md
3. Technology docs
   - Xtext official docs and grammar language reference
   - Eclipse Modeling Framework (EMF) and Ecore basics
   - MWE2 workflow basics
   - Tycho build fundamentals for Eclipse plugin projects

## Contribution checklist for DSL changes

1. Grammar update done.
2. Generation succeeds on Java 21.
3. Root verify succeeds.
4. Stage regenerated tracked Xtext artifacts when they change, such as `src/main/xtext-gen` and `src/test/xtext-gen`; do not stage ignored output folders like `src-gen`.
5. Documentation updated if syntax or behavior changed.

## Recommended Tools for Xtext Development

### IDEs and Editors

| Tool | Description | Link |
|------|-------------|------|
| **Eclipse IDE for DSL Developers** | Official IDE with full Xtext support, debugging, and generated editor preview | [eclipse.org/downloads](https://www.eclipse.org/downloads/packages/release/2024-12/r/eclipse-ide-dsl-developers) |
| **VS Code with Xtext extensions** | Lightweight editor with LSP support for Xtext languages | [code.visualstudio.com](https://code.visualstudio.com/) |
| **IntelliJ IDEA** | Via Xtext LSP support or third-party plugins | [jetbrains.com/idea](https://www.jetbrains.com/idea/) |

### Xtext Resources

| Resource | Description | Link |
|----------|-------------|------|
| **Xtext Documentation** | Official documentation covering grammar, validation, scoping, generators | [eclipse.org/Xtext/documentation](https://www.eclipse.org/Xtext/documentation/) |
| **Xtext Community Forum** | Community support and discussions | [eclipse.org/forums/xtext](https://www.eclipse.org/forums/index.php/f/27/) |
| **Xtext GitHub** | Source code and issue tracker | [github.com/eclipse/xtext](https://github.com/eclipse/xtext) |
| **Xtext Examples** | Official example projects | [github.com/eclipse/xtext/tree/main/org.eclipse.xtext.xtext.ui.examples](https://github.com/eclipse/xtext/tree/main/org.eclipse.xtext.xtext.ui.examples) |

### Learning Resources

| Resource | Description | Link |
|----------|-------------|------|
| **Xtext 15 Minutes Tutorial** | Quick start guide for creating your first DSL | [eclipse.org/Xtext/documentation/102_domainmodelwalkthrough](https://www.eclipse.org/Xtext/documentation/102_domainmodelwalkthrough.html) |
| **Implementing Domain-Specific Languages with Xtext and Xtend** | Comprehensive book by Lorenzo Bettini | [packtpub.com](https://www.packtpub.com/product/implementing-domain-specific-languages-with-xtext-and-xtend-second-edition/9781786464965) |
| **MWE2 Workflow Engine** | Documentation for the workflow configuration | [eclipse.org/Xtext/documentation/306_mwe2](https://www.eclipse.org/Xtext/documentation/306_mwe2.html) |
| **Xtext Grammar Language** | Complete grammar language reference | [eclipse.org/Xtext/documentation/301_grammarlanguage](https://www.eclipse.org/Xtext/documentation/301_grammarlanguage.html) |

### Debugging and Profiling

| Tool | Description |
|------|-------------|
| **Xtext Debug Perspective** | Eclipse perspective for debugging grammar rules and parser |
| **ANTLR Plugin for Eclipse** | Visualize generated ANTLR grammar and parse trees |
| **AST View** | Inspect the Abstract Syntax Tree of parsed models |

### Build Tools

| Tool | Description | Link |
|------|-------------|------|
| **Maven Tycho** | Build Xtext projects as OSGi bundles | [eclipse.org/tycho](https://eclipse.dev/tycho/) |
| **Gradle Xtext Plugin** | Alternative build system for Xtext projects | [github.com/xtext/xtext-gradle-plugin](https://github.com/xtext/xtext-gradle-plugin) |

