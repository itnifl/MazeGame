# main.game.maze

JavaFX app code for the Maze game.
Loads models at runtime, applies difficulty settings, and runs the game loop.

## Features

• JavaFX based game window and loop
• Loads XMI models for difficulties and opponents
• Uses generated code from the maze generated module
• Simple start menu and game controller

## Requirements

• Java 24 or newer
• Maven
• Linux macOS or Windows

## Build

```bash
mvn -pl maze -am clean verify
```

## Run

From the project root

```bash
mvn -pl maze -am -Djavafx.platform=linux exec:java -Dexec.mainClass=main.game.maze.App
```

Replace the platform value with mac or win as needed.

Or run the built jar

```bash
java -jar maze/target/main.game.maze-1.0.0-SNAPSHOT.jar
```

## Models

Default sample XMI files live under

```
models/
```

You can point the app to another XMI via args or config if added.

## Generated code

The app depends on sources produced by the Acceleo generator

```
maze-generator.acceleo  →  maze-generated
```

The CI workflow runs the generator before building this module.

## Tests

```bash
mvn -pl maze -am -DskipTests=false test
```

## Project layout

• `src/main/java` app code and controllers
• `src/main/resources` images sounds and model files
• `src/test/java` unit tests

## Common run flags

```bash
# Software rendering
-Dprism.order=sw

# JavaFX platform override in CI
-Djavafx.platform=linux
```

## Troubleshooting

• If images or sounds do not load, confirm resource paths in the resources folder
• If models fail to load, confirm that the XMI matches the current Ecore version and that registries are initialized in the app startup
