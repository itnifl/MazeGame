# Demo: Sample Models And Validation Behaviour

This demo shows how the **models** and the **runtime code** work together in MazeGame:

1. How the **difficulty model** affects the running game.
2. How the **opponent models** behave when validation succeeds or fails.
3. How the **behaviour / movements model** drives patrol movement and can be validated.

---

## 1. Prerequisites

- Visual Studio Code (recommended demo environment).
- Eclipse (optional, for alternative run configurations).
- All prerequisites from the root guide:  
  See [readme.md](readme.md#prerequisites-and-setup).

- Project built from the root. For a full build including the local p2 mirror you can use:

```bash
  # Windows (PowerShell, from the repo root)
  ./make.ps1 all

  # Linux / macOS (or Windows with make installed)
  make all
```

For a quick rebuild when the mirror already exists:

  ```bash
  mvn clean install
  ```

* Runtime XMI files on the classpath:

  * `maze/src/main/resources/xmi/difficulties/difficulties.xmi`
  * `maze/src/main/resources/xmi/opponents/opponentModel.xmi`

* Sample test models:

  * `main.game.maze.difficulties/src/test/resources/difficultiesBasic.xmi`
  * `main.game.maze.opponents/src/test/java/main/game/maze/opponents/opponentsBasic.xmi`
  * `main.game.maze.opponents/src/test/java/main/game/maze/opponents/opponentModelSuccessMaxThreat.xmi`
  * `main.game.maze.opponents/src/test/java/main/game/maze/opponents/opponentModelFailMaxThreat.xmi`
  * `maze/src/test/patrol_behavior_example.xmi` (behaviour / movements patrol example)

* Main application class:

  * `maze/src/main/java/main/game/maze/App.java`

In Visual Studio Code, use the supplied `.vscode/launch.json` and run **Start Debugging** from the `maze` module.

---

## 2. Runtime demo: effect of the difficulty model

### 2.1 Start the game

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

### 2.2 Show the effect of each difficulty

Repeat this loop three times, once per difficulty:

1. **Start the game** and pick a difficulty:

   * First run: **Easy**
   * Second run: **Normal**
   * Third run: **Hard**

2. Play a short round each time and ask the audience to notice:

   * **Enemy max counts per type**

     * Caps are loaded from `difficulties.xmi` (for example via functions like `diff:getEnemyMaxCount` in the model).
     * Hard mode should have more allowed enemies or different compositions than Easy.

   * **Enemy movement speed and damage**

     * Multipliers (for example `monstersMovementSpeedMultiplier`, `monstersDamageMultiplier`, `instantDeath`) are read from the selected `Difficulty`.
     * `OpponentRuntimeFactory` applies these multipliers when instantiating enemies.

   * **Overall threat and density**

     * `maxThreat` in the difficulty model limits how many enemies and which combinations can spawn.
     * Hard mode should feel denser and more dangerous than Easy.

3. Explain the division of responsibilities:

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

4. Connect to the **behaviour / movements module**:

   * The **behaviour module** decides *where* enemies go.
   * The **difficulty model** controls *how fast* and *how hard* they hit.
   * Together they define how dangerous a level *feels*:

     * difficulty → numeric parameters
     * behaviour → movement and pathfinding logic

---

## 3. Model validation demo: opponent models

This part uses the **sample opponent models** and **JUnit tests** to show how validation behaves.

### 3.1 How validation works

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

### 3.2 Run the validation demo tests

From the repository root:

```bash
# Run only the opponent model validation tests
mvn -pl main.game.maze.opponents test -Dtest=ModelLoadSmokeTest
```

Explain the result:

* **Correct models** (for example `opponentsBasic.xmi`, `opponentModelSuccessMaxThreat.xmi`)
  load and validate without errors.

* **Incorrect models** (for example `opponentModelFailMaxThreat.xmi`)
  parse successfully but fail semantic validation (max threat exceeded, inconsistent caps, and so on).

This shows that:

* Syntax correct but semantically invalid models are caught early.
* The validation errors are explicit and testable.

### 3.3 Optional: provoke a runtime validation error in the game

To show that the game also fails fast on invalid models:

1. Replace the runtime opponent model:

   * Copy the contents of `opponentModelFailMaxThreat.xmi`.
   * Paste it over `maze/src/main/resources/xmi/opponents/opponentModel.xmi`.

2. Rebuild and run the game:

   ```bash
   mvn clean install
   ```

   Then start `App` as described in section 2.

3. During startup:

   * `OpponentRuntimeFactory.instantiateFromModel(...)` will:

     * load the opponent model
     * call `validateOrFail(...)`
   * Since the constraints are violated, it throws `IllegalStateException` with diagnostic details.

Explain that this is **intentional**:

* Invalid DSL models are rejected with clear messages.
* You avoid strange runtime bugs from inconsistent configuration.

---

## 4. Behaviour / movements model demo

This part shows how a **behaviour model** (patrol definition) is:

* loaded as EMF,
* validated at a code level,
* and used to drive patrol movement.

### 4.1 Behaviour model artefacts

Relevant files and modules:

* EMF behaviour model and generated classes:

  * Module: `main.game.maze.behaviour`
  * Packages: for example `PatrolBehavior`, `PatrolPoint`, `MovementBehavior`, `PathCalculator` implementations.

* Sample patrol model instance:

  * `maze/src/test/patrol_behavior_example.xmi`
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

### 4.2 Running the patrol example

1. Open `patrol_behavior_example.xmi`:

   * File: `maze/src/test/patrol_behavior_example.xmi`
   * Show that it describes:

     * a patrol path
     * a list of waypoints with coordinates and timing

2. Run the patrol demo harness from your IDE:

   * Main class: `TestPatrolMovementController`
     (in `maze/src/main/java/main/game/maze/ai/`)

3. Observe:

   * The patrol model is loaded as an EMF object (for example a `PatrolBehavior` instance).
   * `PatrolHelper` validates and transforms the model into runtime structures:

     * ensures there is at least one path
     * checks coordinates are within expected bounds
     * rejects obviously invalid structures via exceptions
   * `PatrolMovementController` and related classes then step through the patrol:

     * position over time changes according to the model
     * you get a clear mapping from XMI → behaviour → movement

4. Optional: show a validation failure

   * Edit `patrol_behavior_example.xmi` to introduce an error (for example move a waypoint far outside the map or remove required elements).
   * Re run `TestPatrolMovementController`.
   * The helper or controller should fail clearly instead of running with nonsense data.

This demonstrates that the **behaviour model** is treated similarly to the opponent model:

* loaded as EMF,
* validated,
* and used as the source for runtime behaviour.

---

## 5. Short demo script (for live presentations)

Use this as a compact spoken script.

1. **Start the game**

   * Run the Maze game.
   * Show the **Select difficulty** dialog.
   * Explain that `difficulties.xmi` defines:

     * Easy, Normal, Hard
     * caps, multipliers, and max threat
   * Play briefly on each difficulty and point out:

     * more or fewer enemies
     * faster or slower enemies
     * damage differences

2. **Run the opponent validation tests**

   * Run:

     ```bash
     mvn -pl main.game.maze.opponents test -Dtest=ModelLoadSmokeTest
     ```

   * Explain:

     * valid models pass,
     * the invalid sample is expected to fail validation,
     * the diagnostics describe *why* (for example max threat exceeded).

3. **Optional: show a runtime failure**

   * Replace the runtime opponent model with `opponentModelFailMaxThreat.xmi`.
   * Start the game.
   * Show that it aborts with “Invalid opponent model …” instead of silently misbehaving.

4. **Show the behaviour / movements module**

   * Open `maze/src/test/patrol_behavior_example.xmi` and briefly show the patrol definition.
   * Run `TestPatrolMovementController`.
   * Explain:

     * the patrol path is defined in the EMF behaviour model,
     * a helper loads and validates it,
     * the controller moves a simulated enemy along that path using the same core logic the game will use.

This ties together:

* **Sample models** (difficulties, opponents, behaviour / movements).
* **Runtime behaviour** (enemy counts, speed, and patrol movement).
* **Validation behaviour** (what happens with valid versus invalid models).

---

## 6. Build helpers and CI (optional to mention in the demo)

* **GitHub Actions** (in `.github/workflows/`) run:

  * full Tycho build of the Eclipse modules and p2 repository,
  * full Maven build and test of the `maze` game (headless JavaFX on Linux).

* **`make` (Makefile)** and **`make.ps1`** provide local shortcuts:

  * `make` or `./make.ps1 all`:

    * refresh the local p2 mirror when needed,
    * clear the Tycho cache,
    * run `mvn -U -DskipTests=false clean verify` for the whole project.

For the demo you can summarise this as:

> “The same steps you see locally are also run automatically in CI, so generated code, models and the game are always in sync.”
