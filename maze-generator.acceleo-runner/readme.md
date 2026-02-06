# maze-generator.acceleo-runner

This project contains the headless code generation runner used in the Maven / Tycho build for MazeGame.

Where `maze-generator.acceleo` holds the standalone Java generators (`RunAcceleo.java`, `RunWallsAcceleo.java`),  
`maze-generator.acceleo-runner` is the executable plug-in that invokes these generators during the build.

---

## Purpose

`maze-generator.acceleo-runner` is responsible for:

- invoking the standalone Java generators from `maze-generator.acceleo` in a headless runtime  
- passing the correct model paths and output directories to the generators  
- writing generated Java sources into the appropriate `target` folders or target modules  
- integrating smoothly into the Tycho reactor so generation happens as part of `mvn clean verify`

This allows the entire model-to-code generation pipeline to run automatically in CI and on developer machines without manual Eclipse steps.

**Note**: The generators are now standalone Java code that uses EMF directly — they do not require the Acceleo 3 engine or Eclipse workspace resources.

---

## Project contents

Typical files in `maze-generator.acceleo-runner` include:

- `pom.xml`  
  Tycho configuration that declares this as an Eclipse plug-in and sets up the execution phase (usually `generate-sources` or `prepare-package`).

- `META-INF/MANIFEST.MF`  
  Plug-in metadata declaring dependencies on:
  - the Acceleo runtime
  - the EMF model plug-ins (opponents, difficulties, walls)
  - the `maze-generator.acceleo` plug-in

- `plugin.xml`  
  Optional extension declarations or application entries used when starting the runner in headless mode.

- A small Java entry point  
  For example a class that:
  - sets up command-line arguments for the standalone generators
  - invokes `RunAcceleo` (for opponents) and `RunWallsAcceleo` (for walls)
  - configures the output directories

The generated sources themselves are **not** stored in this project.  
They are written to `target/generated-sources/acceleo` or into the relevant `main.game.maze.*` modules, depending on configuration.

---

## How it works in the Tycho build

During a full Tycho build of the MazeGame reactor:

1. Tycho builds all required EMF model plug-ins and the `maze-generator.acceleo` plug-in.  
2. Tycho builds `maze-generator.acceleo-runner`, which depends on those plug-ins.  
3. At the configured build phase, Tycho launches the runner in an OSGi/Eclipse environment.  
4. The runner:
   - locates the model resources (for example `.opponents`, `.difficulties`, `.walls` or `.xmi` files)
   - runs the Acceleo `Generate` module
   - writes generated sources to the configured output directories
5. After generation, Tycho compiles the generated sources together with the hand-written code in the target modules.

This ensures that generated code is always up to date with the current models whenever `mvn clean verify` is run.

---

## Running the runner via Maven

In most cases you do not need to call the runner directly.  
It is wired into the main multi-module build.

If you want to build just the generator part, you can run (from the repository root):

```bash
mvn -f maze-generator.acceleo-runner/pom.xml clean verify
```

This will:

* resolve all dependencies (models, Acceleo, EMF, etc.)
* start the runner as part of the Tycho lifecycle
* generate and compile the sources

Check the `target` folders of the relevant modules (or `target/generated-sources/acceleo`) to verify that files were created.

---

## Configuration aspects

Key configuration points typically found in `maze-generator.acceleo-runner`:

* **Model locations**
  How and where the runner finds the model instances:

  * through workspace-relative paths
  * through platform URIs (`platform:/resource/...` or `platform:/plugin/...`)
  * via manually registered resource factories and URI mappings

* **Output directories**
  Where the generated sources are written:

  * a central `target/generated-sources/acceleo` folder, or
  * directly into the corresponding `main.game.maze.*` modules

* **Launch class and arguments**
  The Java class that acts as entry point and the arguments passed to it:

  * root model URI
  * output path
  * optional flags (for example cleaning previous output)

Any changes to the models, the generator templates, or the target layout may require updating this configuration.

---

## Troubleshooting

Common issues when working with `maze-generator.acceleo-runner`:

* **No files are generated**

  * Check that the runner is actually executed in the Maven phase you expect.
  * Verify that the model URIs in the runner match the locations of your `.ecore` / `.xmi` files.
  * Confirm that `maze-generator.acceleo` is on the runtime classpath and the `Generate` module name is correct.

* **Model loading errors**

  * Ensure that all EMF model plug-ins are listed as dependencies in `MANIFEST.MF`.
  * Register needed resource factories and URI mappings if you use custom file extensions.

* **Compilation errors in generated code**

  * Inspect the generated sources to see which imports or types are missing.
  * Adjust the Acceleo templates to add required imports or fully qualified names.
  * Re-run the build after fixes to regenerate the code.

---

## When to modify maze-generator.acceleo-runner

You typically update this project when you:

* add a new model that should be processed by Acceleo
* change where models are stored or how they are loaded
* change the output structure for generated code
* rename or restructure Acceleo modules in `maze-generator.acceleo`

In such cases:

1. Update the runner’s Java entry point and configuration to reflect the new models or paths.
2. Adjust `MANIFEST.MF` dependencies so all required plug-ins are available.
3. Run `mvn clean verify` and verify that generated sources appear as expected.

By keeping the logic for headless generation in `maze-generator.acceleo-runner` and the templates in `maze-generator.acceleo`, the MazeGame project maintains a clean separation between **what** is generated and **how** it is executed in automated builds.


