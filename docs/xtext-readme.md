# Xtext Setup and Learning Guide for MazeGame

This guide explains how Xtext is wired in this repository, how to run it reliably, and what to study next if you want to understand and extend the setup.

## Who this guide is for

- Team members who need to build the project with DSL support.
- Team members who need to change the Maze DSL grammar.
- Team members who want to understand how the DSL, EMF models, and generated code fit together.

## Important decisions in this repository

- Xtext generated artifacts are NOT committed. They are generated locally by each developer.
- CI only builds the game module (maze/), skipping DSL modules that need generated code.
- Generation runs from Maven using an MWE2 workflow in the DSL module.
- Use Java 21 for Xtext generation and reactor builds that include DSL modules.
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

## Why Java 21 here

The DSL generation and Tycho/Xtext dependency graph in this repository is validated with Java 21. Running this flow with newer JDKs can trigger classpath or workflow errors depending on environment state.

## What is generated and why it is ignored

Generation creates parser/runtime/editor artifacts under module source trees (for example src-gen and some generated sources under src/main). These are reproducible outputs from grammar and workflow inputs. They are kept out of version control because:

1. They can be regenerated deterministically from the grammar.
2. CI only builds the game module (maze/) and skips DSL modules that need these artifacts.
3. This avoids unnecessary churn in code reviews from regenerated files.

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
4. No generated artifacts staged (src-gen is in .gitignore).
5. Documentation updated if syntax or behavior changed.
