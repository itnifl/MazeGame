# maze-generated

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

## Relationship to other modules

* **maze-generator.acceleo** → produces the Java sources that live here. Run it first to update code, then build this module to publish the jar.
* **main.game.maze** → depends on the jar from this module to compile and run the game logic that is model driven.
* **releng** → provides the target and optional local p2 mirror used when the generator runs headless, ensuring consistent generation inputs.
* **movements-module, difficulty-module, opponents-module** → independent Eclipse plug ins. Changes in the metamodel or OCL inside `difficulty-module` may require regenerating this module so that the app sees the updated classes.
* **maze-feature and maze-repository** → collect only Eclipse plug ins and features for p2 distribution. This module is a plain Maven jar and is not published to the p2 site.

## Typical workflow

1. Update model or templates.
2. Run the generator:

   ```bash
   mvn -pl maze-generator.acceleo -am -DskipTests clean verify
   ```
3. Build the generated jar and the app:

   ```bash
   mvn -pl maze -am -DskipTests=false clean verify
   ```

