# maze-generator.acceleo

This project contains the Acceleo templates that generate Java code and other artefacts from the EMF models used by MazeGame.

It is the place where we turn models such as opponents, difficulties and walls into concrete Java sources that can be compiled and used at runtime.

Typical inputs

- Walls model and other domain models

Typical outputs

- Generated Java sources under the corresponding `maze-generator.acceleo` modules  
- Small helper files for inspection or debugging of the model contents

The actual code generation is executed either inside Eclipse or via the companion project `maze-generator.acceleo` in a Tycho Maven build.

---

## What the generator produces

The Acceleo templates in this project generate several kinds of output for MazeGame, for example

- Java types that mirror the EMF models  
  such as enums, data classes and registries for walls, opponents and difficulties

- Runtime configuration helpers  
  such as registries or lookup tables that are easier to maintain through models

- Optional text files used for debugging or documentation of the model contents

Especially, we generate a Wall Registry for different types of Maze Walls: (main.game.maze.walls)[../main.game.maze.walls/readme.md]

---

## Running the generator in Eclipse

For development and experimentation you can run the Acceleo templates from inside Eclipse.

Typical workflow

1. Open the `.mtl` file that contains the main `Generate` module.
2. Right click the file and choose
   `Run As` then `Launch Acceleo Application` or an existing launch configuration.
3. Select the correct model resource or root element in the launch configuration.
4. Run the launch.
   The generator will write files into the output folders configured in the template.

You can repeat this process whenever you change the model or the templates.

---

## Running the generator in Maven Tycho

For automated builds and continuous integration the generator is executed headless through the project

[maze-generator.acceleo-runner](../maze-generator.acceleo-runner/readme.md)

That runner project is an Eclipse plug in with a Maven `pom.xml` that uses Tycho to start Acceleo and write the generated sources into

`target/generated sources/acceleo`

During a full Tycho build the sequence is

1. Tycho builds the models and the generator plug in.
2. The runner plug in is started and invokes the Acceleo templates.
3. Generated sources are placed under `target/generated sources/acceleo` or under the corresponding module folders, depending on the configuration.
4. Tycho compiles the generated sources together with hand written code.

If you see an empty `generated sources/acceleo` folder in a Maven build, it usually means that

* the runner has not been configured or wired correctly, or
* the generator was not pointed at the expected model resources.

---

## Customising templates

When you want to change what is generated you do it by editing the `.mtl` templates in this project.

Recommended approach

1. Locate the template that generates the code you want to change
   for example `GenerateWalls.mtl` or `GenerateOpponents.mtl`.

2. Adjust the Acceleo expressions
   for example add new fields, change method names or restructure generated classes.

3. Run the generator in Eclipse on a small test model to validate the output.

4. Once you are satisfied, run the headless generator via Maven to regenerate the sources in a clean build.

Remember that generated files are overwritten the next time the generator runs.
Any custom logic belongs in the templates or in separate hand written classes, not in the generated files.

---

## Adding a new model to the generator

If you introduce a new EMF model and want Acceleo to generate code from it, the high level steps are

1. Register the new model URI in the main module
   Extend the `module Generate` declaration with the new namespace URI.

2. Create a new template or extend an existing one
   Write `.mtl` templates that navigate the new model and produce the desired code.

3. Update the runner configuration
   Ensure the runner plug in knows where to find the new model instances
   and passes them to the generator on the command line or through EMF resource loading.

4. Verify output
   Run the generator both in Eclipse and via Maven to ensure the new model is handled correctly.

---

## Troubleshooting

Some common issues and hints

* No generated files appear
  Check that the template output paths are correct
  and that the model root used when launching the generator actually contains the expected elements.

* Headless build fails to load models
  Verify that the EMF models and their `.ecore` and `.xmi` files are available in the runtime,
  and that the runner plug in registers any required resource factories.

* Compilation errors in generated code
  Inspect the generated files to see which types or imports are missing,
  then adjust the templates or the generator configuration to fix them.

By keeping all model to code generation logic in `maze-generator.acceleo` and using `maze-generator.acceleo` for headless execution, the MazeGame project maintains a clean, reproducible and model driven build pipeline.
