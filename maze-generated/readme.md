# maze generated

## Purpose

This module is the home for code that the Acceleo generator produces from the EMF model. It packages those sources as a plain jar so other modules can depend on a stable artifact without having to run the generator first. Keeping the generated sources here also makes IDE import simple and allows the game to compile on a clean machine.

## What the jar contains

* Java classes produced from the model, such as data types, factories, and helpers that the game logic reads at runtime
* Any small utilities that the templates emit to support the generated model code
* Optional resources the generator places under `src/main/resources` when needed

After a build, the jar is created at `target/maze-generated-<version>.jar`.

## How other modules use it

* **main.game.maze** depends on this jar to access model driven classes during app startup and gameplay
* **Unit tests** in the app and related modules import this jar to validate behavior that relies on generated types
* **Eclipse plug in modules** can also use it when they need the same generated classes at runtime

## Source layout

* `src/main/java` — generated sources written by the Acceleo run
* `src/main/resources` — resources referenced by generated code when present
* `target` — compiled classes and the packaged jar

## Regeneration workflow

1. Run the generator module so it writes updated sources into this module.

   ```bash
   mvn -pl maze-generator.acceleo -am -DskipTests clean verify
   ```
2. Build this module or the whole project to produce the jar.

   ```bash
   mvn clean verify
   ```

## Notes

* You can commit the generated sources to keep the project build friendly for new contributors and for continuous integration.
* If templates or the input model change, run the generator again before compiling the app so this module reflects the latest output.