# Demo: Sample Models And Validation Behaviour

This demo shows how the **models** and the **runtime code** work together in MazeGame:

1. How the **difficulty model** affects the running game.
2. How the **opponent models** behave when validation succeeds or fails.
3. How the **behaviour / movements model** drives patrol movement and can be validated.
4. How the **walls model** and FreeMarker-generated code are wired into the game.
5. How the **MazeGame DSL** provides a textual syntax for game configuration.

---

## 1．Prerequisites

- Visual Studio Code (recommended demo environment).
- Eclipse (optional, for alternative run configurations).
- All prerequisites from the root guide:  
  See [readme.md](readme.md#prerequisites-and-setup).

- Project built from the root. For a full build including the local p two mirror you can use:

```bash
  # Windows (PowerShell, from the repo root)
  ./make-javafx.ps1 all

  # Linux / macOS (or Windows with make installed)
  make -f make-javafx all
```

* For a quick rebuild when the mirror already exists:

  ```bash
  mvn clean install
  ```

* Runtime XMI files on the classpath:

  * `maze/src/main/resources/xmi/difficulties/difficulties.xmi`
  * `maze/src/main/resources/xmi/opponents/opponentModel.xmi`
  * `main.game.maze.walls/xmi/walls.xmi` (model for wall types and properties)

* DSL configuration files (`.mazegame`):

  * `main.game.maze.dsl/src/main/resources/levels/tutorial.mazegame`
  * `main.game.maze.dsl/src/main/resources/levels/challenge.mazegame`
  * `main.game.maze.dsl/src/main/resources/levels/survival.mazegame`

* Sample test models:
  * `main.game.maze.opponents/src/test/java/main/game/maze/opponents/opponentsBasic.xmi`
  * `main.game.maze.opponents/src/test/java/main/game/maze/opponents/opponentModelSuccessMaxThreat.xmi`
  * `main.game.maze.opponents/src/test/java/main/game/maze/opponents/opponentModelFailMaxThreat.xmi`
  * `main.game.maze.behaviour/xmi/patrol_behavior_example.xmi` (behaviour / movements patrol example)

* Main application class:

  * `maze/src/main/java/main/game/maze/App.java`

In Visual Studio Code, use the supplied `.vscode/launch.json` and run **Start Debugging** from the `maze` module.

---

## 2．Runtime demo: effect of the difficulty model

### 2．1 Start the game

You can start the game in one of two ways.

**From Maven and Visual Studio Code**

```bash
# From the repository root
mvn clean install
```

Then, in VS Code:

* Open the workspace.
* Use “Run → Start Debugging” (or the configured Java launch for `main.game.maze.App`).

**From Eclipse**

* Right click `App.java` in the `maze` module.
* Choose **Run As → Java Application**.
* Make sure the VM arguments include something like:

  ```text
  --module-path "${project_loc:main.game.maze}/target/libs" --add-modules javafx.controls,javafx.fxml,javafx.media
  ```

(depending on your JavaFX setup in `maze/pom.xml`).

**After starting the game**

When the game starts, you see a **Select difficulty** dialog.

Internally:

* `App.setDifficulty` uses `DifficultyService` to read:

  * `/xmi/difficulties/difficulties.xmi`
  * the `EasyDifficulty`, `NormalDifficulty`, `HardDifficulty` objects
  * the `currentDifficulty` reference

* The selected `Difficulty` is then passed into:

  * `GameController.setStartDifficulty(...)`
  * `OpponentRuntimeFactory.instantiateFromModel(this, startDifficulty)`

### 2．2 Show the effect of each difficulty

Repeat this loop three times, once per difficulty:

1．**Start the game** and pick a difficulty:

* First run: **Easy**
* Second run: **Normal**
* Third run: **Hard**

2．Play a short round each time and ask the audience to notice:

* **Enemy max counts per type**

  * Caps are loaded from `difficulties.xmi`.
  * Hard mode should have more allowed enemies or different compositions than Easy.

* **Enemy movement speed and damage**

  * Multipliers (for example `monstersMovementSpeedMultiplier`, `monstersDamageMultiplier`, `instantDeath`) are read from the selected `Difficulty`.
  * `OpponentRuntimeFactory` applies these multipliers when instantiating enemies.

* **Overall threat and density**

  * `maxThreat` in the difficulty model limits how many enemies and which combinations can spawn.
  * Hard mode should feel denser and more dangerous than Easy.

3．Explain the division of responsibilities:

* **Model side (in `difficulties.xmi`)**

  * Stores structure and base values:

    * caps per enemy type
    * threat values
    * multipliers
    * global `maxThreat`

* **Code side**

  * `DifficultyService` loads the model.
  * `App` and `GameController` choose a `Difficulty` instance.
  * `OpponentRuntimeFactory` uses that `Difficulty` to:

    * cap enemy counts
    * scale movement speed and damage
    * guide threat based spawning

4．Connect to the **behaviour / movements module**:

* The **behaviour module** decides where enemies go.
* The **difficulty model** controls how fast and how hard they hit.
* Together they define how dangerous a level feels:

  * difficulty → numeric parameters
  * behaviour → movement and pathfinding logic

---

## 3．Model validation demo: opponent models

This part uses the **sample opponent models** and **JUnit tests** to show how validation behaves.

### 3．1 How validation works

* Opponent models are EMF instances of `OpponentModel`.

* OCL and code based constraints are wired through `OpponentsValidator`.

* At runtime, `OpponentRuntimeFactory` performs validation, for example:

  ```java
  private static void validateOrFail(OpponentModel model) {
      BasicDiagnostic diag = new BasicDiagnostic();
      boolean ok = OpponentsValidator.INSTANCE.validate(model, diag, null);
      if (!ok) {
          throw new IllegalStateException(
              "Invalid opponent model: " + ", " + diag.getChildren() + ", " + diag.getMessage()
          );
      }
  }
  ```

* If validation fails, the game throws an exception with detailed diagnostics instead of silently continuing.

### 3．2 Run the validation demo tests

From the repository root:

```bash
# Run only the opponent model validation tests
mvn -pl main.game.maze.opponents test -Dtest=ModelLoadSmokeTest
```

Explain the result:

* **Correct models**
  `opponentsBasic.xmi` and `opponentModelSuccessMaxThreat.xmi` load and validate without errors.

* **Incorrect models**
  `opponentModelFailMaxThreat.xmi` parses successfully but fails semantic validation (for example max threat exceeded).

This shows that:

* Syntax correct but semantically invalid models are caught early.
* The validation errors are explicit and testable.

### 3．3 Optional: provoke a runtime validation error in the game

To show that the game also fails fast on invalid models:

1．Replace the runtime opponent model:

* Copy the contents of `opponentModelFailMaxThreat.xmi`.
* Paste it over `maze/src/main/resources/xmi/opponents/opponentModel.xmi`.

2．Rebuild and run the game:

```bash
mvn clean install
```

Then start `App` as described in section 2.

3．During startup:

* `OpponentRuntimeFactory.instantiateFromModel(...)` will:

  * load the opponent model
  * call `validateOrFail(...)`
* Since the constraints are violated, it throws `IllegalStateException` with diagnostic details.

Explain that this is intentional:

* Invalid DSL models are rejected with clear messages.
* You avoid strange runtime bugs from inconsistent configuration.

---

## 4．Behaviour / movements model demo

This part shows how a **behaviour model** (patrol definition) is:

* loaded as EMF,
* validated at a code level,
* and used to drive patrol movement.

### 4．1 Behaviour model artefacts

Relevant files and modules:

* EMF behaviour model and generated classes:

  * Module: `main.game.maze.behaviour`
  * Packages: for example `PatrolBehavior`, `PatrolPoint`, `MovementBehavior`, `PathCalculator` implementations.

* Sample patrol model instance:

  * `main.game.maze.behaviour/xmi/patrol_behavior_example.xmi`
    Contains a `behaviour:PatrolBehavior` with a path and waypoints.

* Helper and controller code (typical setup):

  * `maze/src/main/java/main/game/maze/config/PatrolHelper.java`
    will:

    * load the XMI patrol model
    * check basic structural rules
    * convert it into a runtime patrol definition

  * `maze/src/main/java/main/game/maze/ai/TestPatrolMovementController.java`
    demo harness that:

    * uses `PatrolHelper` to load the patrol model
    * steps through the patrol path using runtime behaviour classes
    * prints or visualises movement over time

### 4．2 Running the patrol example

1．Open `patrol_behavior_example.xmi`:

* File: `main.game.maze.behaviour/xmi/patrol_behavior_example.xmi`
* Show that it describes:

  * a patrol path
  * a list of waypoints with coordinates and timing

2．Run the patrol demo harness from your IDE:

* Main class: `TestPatrolMovementController`
  in `maze/src/main/java/main/game/maze/ai/`

3．Observe:

* The patrol model is loaded as an EMF object (for example a `PatrolBehavior` instance).
* `PatrolHelper` validates and transforms the model into runtime structures:

  * ensures there is at least one path
  * checks coordinates are within expected bounds
  * rejects obviously invalid structures via exceptions
* `PatrolMovementController` and related classes then step through the patrol:

  * position over time changes according to the model
  * you get a clear mapping from XMI → behaviour → movement

4．Optional: show a validation failure

* Edit `patrol_behavior_example.xmi` to introduce an error (for example move a waypoint far outside the map or remove required elements).
* Re run `TestPatrolMovementController`.
* The helper or controller should fail clearly instead of running with nonsense data.

This demonstrates that the **behaviour model** is treated similarly to the opponent model:

* loaded as EMF,
* validated,
* and used as the source for runtime behaviour.

---

## 5．Walls model and generated code wiring

This section shows how the **walls model** and **FreeMarker-generated code** are connected from design time to runtime.

### 5．1 The walls model at design time

* The wall definitions live in the `main.game.maze.walls` module.
* The key model file is:

  * `main.game.maze.walls/xmi/walls.xmi`

This XMI file describes:

* which wall types exist,
* ids and display names,
* base material,
* flags like breakable or unbreakable,
* hit points and related properties.

From the modelling point of view, `walls.xmi` is the single source of truth for wall types.

### 5．2 The walls plug in JAR in the p two repository

When you run the Tycho build and create the p two repository via `maze-module-repository`, the walls plug in is published as an OSGi bundle.

You can see it here:

* `maze-module-repository/target/repository/plugins/main.game.maze.walls_1.0.0.202512041940.jar`

This JAR is:

* the Eclipse plug in version of the walls module,
* containing the generated EMF model code and runtime support for walls.

Tycho uses this JAR when resolving plug ins in the Eclipse world.
Headless tools such as `maze-generator.freemarker-runner` can load the walls model through this bundle.

### 5．3 How `maze-generator.freemarker-runner` uses the walls model

* `maze-generator.freemarker` contains the FreeMarker templates that read walls-related model data.
* `maze-generator.freemarker-runner` is the headless Equinox application that:

  1．starts in an Eclipse runtime (using the p two repository that contains `main.game.maze.walls_1.0.0.202512041940.jar`),
  2．loads EMF models, including `walls.xmi` and its generated Ecore package,
  3．invokes the FreeMarker `Generate` module,
  4．writes Java sources derived from the walls model into a generated sources folder.

Conceptually:

* `walls.xmi` → EMF model
* `maze-generator.freemarker` → templates that describe how to turn that model into Java code
* `maze-generator.freemarker-runner` → headless launcher that runs the templates in the build

The result from this step is plain Java code that mirrors the walls definitions.

### 5．4 From generated walls code to the game: `maze-module-generator` and its JAR

Once the FreeMarker runner has written the generated Java sources, the Maven side bridge takes over:

* The `maze-module-generator` module has a dependency on `main.game.maze.walls` and is configured to include a generated sources folder (for example `src-gen`) as a source directory.

* In `maze-module-generator/pom.xml`, the `build-helper-maven-plugin` registers `src-gen` as an extra source root.

During the Maven build:

1．`maze-module-generator` receives the generated walls-related Java sources in `src-gen`.
2．Maven compiles both `src` and `src-gen`.
3．The compiled classes are packaged into:

* `maze-module-generator-1.0.0-SNAPSHOT.jar`

This JAR now contains:

* generated helper code based on `walls.xmi` and other models,
* any hand written generator side helpers in `maze-module-generator`.

This JAR is a plain Maven artifact and can be used from the `maze` game module.

---

## 6．How `maze-module-generator` is used in the game

The `maze` module declares an explicit dependency on the FreeMarker-generated module.
In `maze/pom.xml` you will find:

```xml
<!-- This is our FreeMarker-generated module -->
<dependency>
    <groupId>main.game.maze</groupId>
    <artifactId>maze-module-generator</artifactId>
    <version>${project.version}</version>
</dependency>
```

This line is important for two reasons:

1．It tells Maven that the game needs whatever classes were generated from the models.
These include walls-related helpers, as well as any other generated types.

2．It connects the runtime directly to the artifact built from `maze-module-generator`:

* `maze-module-generator-1.0.0-SNAPSHOT.jar`

So the runtime flow looks like this:

* Design time:

  * walls and other models are edited in XMI (`walls.xmi`, `difficulties.xmi`, opponent models).

* Build time:

  * Tycho builds the EMF plug ins and publishes `main.game.maze.walls_1.0.0.202512041940.jar` into the p two repository under `maze-module-repository/target/repository`.
  * `maze-generator.freemarker-runner` runs FreeMarker templates against those models.
  * `maze-module-generator` compiles the generated sources into `maze-module-generator-1.0.0-SNAPSHOT.jar`.

* Runtime:

  * The `maze` module depends on `maze-module-generator` in `maze/pom.xml`.
  * When you run the game, the JVM simply loads classes from `maze-module-generator-1.0.0-SNAPSHOT.jar` like any other library.
  * These generated classes encapsulate model knowledge so game code can access it through a clean, Java friendly API.

In a live demo, you can summarise this as:

> "`walls.xmi` and the other XMI models are turned into Java code by FreeMarker.
> That code is compiled into `maze-module-generator-1.0.0-SNAPSHOT.jar`.
> The `maze` game module imports that JAR through a normal Maven dependency and uses the generated classes at runtime.”

---

## 7．Short demo script (for live presentations)

Use this as a compact spoken script.

1．**Start the game**

* Run the Maze game.
* Show the **Select difficulty** dialog.
* Explain that `difficulties.xmi` defines:

  * Easy, Normal, Hard
  * caps, multipliers, and max threat
* Play briefly on each difficulty and point out:

  * more or fewer enemies
  * faster or slower enemies
  * damage differences

2．**Run the opponent validation tests**

* Run:

  ```bash
  mvn -pl main.game.maze.opponents test -Dtest=ModelLoadSmokeTest
  ```

* Explain:

  * valid models pass,
  * the invalid sample is expected to fail validation,
  * the diagnostics describe why (for example max threat exceeded).

3．**Optional: show a runtime failure**

* Replace the runtime opponent model with `opponentModelFailMaxThreat.xmi`.
* Restart the game and show that it aborts with a clear “Invalid opponent model …” error instead of starting with a broken configuration.

4．**Show the behaviour / movements module**

* Open `main.game.maze.behaviour/xmi/patrol_behavior_example.xmi` and briefly show the patrol definition.
* Run `TestPatrolMovementController`.
* Explain:

  * the patrol path is defined in the EMF behaviour model,
  * a helper loads and validates it,
  * the controller moves a simulated enemy along that path using the same core logic the game will use once you integrate patrols into real enemies.

5．**Explain the walls and generated code pipeline**

* Mention `main.game.maze.walls/xmi/walls.xmi` as the wall definition model.

* Mention that Tycho builds `main.game.maze.walls_1.0.0.202512041940.jar` into the p two repository under `maze-module-repository/target/repository/plugins/`.

* Explain that FreeMarker templates run via `maze-generator.freemarker-runner`, and their output is compiled into `maze-module-generator-1.0.0-SNAPSHOT.jar`.

* Point at the dependency in `maze/pom.xml`:

  ```xml
  <!-- This is our FreeMarker-generated module -->
  <dependency>
      <groupId>main.game.maze</groupId>
      <artifactId>maze-module-generator</artifactId>
      <version>${project.version}</version>
  </dependency>
  ```

* Conclude with:

  > “The game just sees `maze-module-generator-1.0.0-SNAPSHOT.jar` as a normal library.
  > Inside that jar is Java code that was generated from models like `walls.xmi` and `difficulties.xmi`.
  > That is how the modelling side and the game side are connected.”

---

## 8．Build helpers and CI (optional to mention in the demo)

* **GitHub Actions** (in `.github/workflows/`) run:

  * full Tycho build of the Eclipse modules and p two repository,
  * full Maven build and test of the `maze` game (headless JavaFX on Linux).

* **`make-javafx` (Makefile)** and **`make-javafx.ps1`** provide local shortcuts for the JavaFX backend, with parallel `make-libgdx` and `make-libgdx.ps1` for the libGDX backend:

  * `make -f make-javafx all` or `./make-javafx.ps1 all`:

    * refresh the local p two mirror when needed,
    * clear the Tycho cache,
    * run `mvn -U -DskipTests=false clean verify` for the whole project.

For the demo you can summarise this as:

> “The same steps you see locally are also run automatically in CI, so generated code, models and the game are always in sync.”
---

## 9．MazeGame DSL demo

This section demonstrates the **Xtext-based Domain-Specific Language** for game configuration.

### 9．1 What is the MazeGame DSL?

The DSL provides a **human-readable textual syntax** for defining game levels instead of editing raw XMI files.

**Before (XMI):**
```xml
<opp:OpponentModel xmi:version="2.0" name="TutorialLevel">
  <characterTypes xsi:type="opp:Zombie" id="Guard1" health="50" threatLevel="10"/>
</opp:OpponentModel>
```

**After (DSL):**
```text
game TutorialLevel {
    opponent Guard1 {
        type zombie
        health 50
        threatLevel 10
    }
}
```

### 9．2 DSL project structure

The DSL is implemented across four Eclipse plugin modules:

| Module | Purpose |
|--------|---------|
| `main.game.maze.dsl` | Core grammar, parser, validator, generator |
| `main.game.maze.dsl.ide` | Language server support |
| `main.game.maze.dsl.ui` | Eclipse editor integration |
| `main.game.maze.dsl.tests` | Automated tests |

### 9．3 Open and explore a DSL file

1. Open one of the example DSL files:
   * `main.game.maze.dsl/src/main/resources/levels/tutorial.mazegame`
   * `main.game.maze.dsl/src/main/resources/levels/challenge.mazegame`
   * `main.game.maze.dsl/src/main/resources/levels/survival.mazegame`

2. Show the key elements:
   * **game** declaration with name
   * **difficulty** block with level, maxThreat, enemy limits
   * **patrol** definitions with waypoints
   * **opponent** configurations with type-specific stats
   * **loot-table** definitions

### 9．4 Demonstrate validation

1. Open `tutorial.mazegame` in an Xtext-enabled editor.

2. Try adding an invalid configuration:
   ```text
   opponent BadEnemy {
     type zombie
     threatLevel 150    // Error: exceeds 100
   }
   ```

3. Show that the editor immediately displays an error marker.

4. Use the quick fix (Ctrl+1) to correct the value.

5. Explain the validation rules:
  * Threat level must be 0-100
   * Patrol paths need at least 2 waypoints
   * Character-specific blocks must match character type
   * Total threat cannot exceed maxThreat

### 9．5 Show code generation output

When a `.mazegame` file is saved, the generator produces:

1. **Java Factory Class** (`*Factory.java`):
   ```java
   public class TutorialLevelFactory {
       public static Zombie createTutorialZombie() { ... }
       public static PatrolBehavior createEntranceGuardPatrol() { ... }
       public static Difficulty createDifficulty() { ... }
       public static List<CharacterType> createAllOpponents() { ... }
   }
   ```

2. **XMI Model Instances**:
   * `tutoriallevel-config.xmi` - Opponents model
   * `tutoriallevel-difficulty.xmi` - Difficulty settings

### 9．6 DSL syntax overview

Show the key constructs:

```text
game MyLevel {
    // Difficulty settings
    difficulty {
        level easy | normal | hard
        maxThreat 50
        limit zombie max 3
    }

    // Patrol paths
    patrol GuardRoute {
        visionRange 100.0
        path [(0, 0), (100, 0) : 2000 ms, (100, 100)]
    }

    // Enemy definitions
    opponent Enemy1 {
        type zombie | ghost | pumpkinbomber
        health 100
        threatLevel 25
        behavior patrol
        patrol GuardRoute
        
        zombie-stats { attackDamage 15 }
    }

    // Loot drops
    loot-table Rewards {
        item HealthPotion { type food value 25 }
    }
}
```

### 9．7 Run the DSL tests

From the repository root:

```bash
# Run DSL parsing and validation tests (and required upstream modules)
mvn -pl main.game.maze.dsl.tests -am test
```

Explain the test coverage:
* **Parsing tests** - Valid syntax is accepted
* **Validation tests** - Invalid configurations are rejected with proper errors
* **Generator tests** - Correct Java and XMI output is produced

### 9．8 Key benefits of the DSL

Summarise for the audience:

1. **Readability** - Game designers can understand and edit configurations
2. **Validation** - Errors are caught immediately in the editor, not at runtime
3. **Autocomplete** - Content assist suggests valid options
4. **Integration** - Generates code compatible with existing EMF models
5. **Documentation** - See [DSL Reference Guide](docs/dsl-reference.md) and [DSL Tutorial](docs/dsl-tutorial.md)

---

## Related Documentation

| Document | Description |
|----------|-------------|
| [Technology Layman's Guide](docs/technology-laymans-guide.md) | Simple explanation of Xtext, metamodels, and FreeMarker in everyday terms |
| [DSL Reference Guide](docs/dsl-reference.md) | Complete syntax reference for MazeDsl |
| [DSL Tutorial](docs/dsl-tutorial.md) | Step-by-step guide to creating game levels |
| [FreeMarker Guide](freemarker.readme.md) | Code generation with FreeMarker |
| [Xtext Setup Guide](docs/xtext-readme.md) | Build and development setup for the DSL |
| [Main README](readme.md) | Project overview and module index |
