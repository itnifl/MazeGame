# Demo: Sample Models And Validation Behavior

> Add a short demo script that shows the effect of each sample model and the behavior on validation errors

This demo describes how to:

1. Show the effect of the **difficulty model** at runtime (how `difficulties.xmi` and the code work together).
2. Show how the **opponent models** behave when validation succeeds or fails (using the sample XMI files and JUnit tests). 
3. <span style="color:red; font-weight:bold">🔴 Show how the **behaviour models** behave when validation succeeds or fails (using the sample XMI files and JUnit tests).</span>

---

## 1. Prerequisites

* Visual Studio Code

* Eclipse

* Chek out the root [readme.md](readme.md) for more prerequisites.

* Project built from the root. For a full build including the local p2 mirror you can use:

  ```bash
  # Windows (PowerShell, from the repo root)
  ./make.ps1 all

  # Linux / macOS (or Windows with make installed)
  make all
  ```

* If you already have the local p2 mirror and just want a quick rebuild, you can still use:

  ```bash
  mvn clean install
  ```

- Runtime XMI files on the classpath:

  * `maze/src/main/resources/xmi/difficulties/difficulties.xmi`
  * `maze/src/main/resources/xmi/opponents/opponentModel.xmi`

- Sample test models:

  * `main.game.maze.difficulties/src/test/resources/difficultiesBasic.xmi`
  * `main.game.maze.opponents/src/test/java/main/game/maze/opponents/opponentsBasic.xmi`
  * `main.game.maze.opponents/src/test/java/main/game/maze/opponents/opponentModelSuccessMaxThreat.xmi`
  * `main.game.maze.opponents/src/test/java/main/game/maze/opponents/opponentModelFailMaxThreat.xmi`
  * `maze/src/test/patrol_behavior_example.xmi` (behaviour / movements patrol example used by `TestPatrolMovementController`)
  * <span style="color:red; font-weight:bold">🔴 Missing on behaviour model</span>

- Main application class:

  * `maze/src/main/java/main/game/maze/App.java`

Run it in Visual Studio Code.

---

## 2. Runtime demo: effect of the difficulty model

### 2.1 Start the game

You can start the game in one of two ways.

**From Maven (command line + Visual Studio Code):**

```bash
# On Windows with Powershell 7.x on the project root
./Run-P2AndBuildCheck.ps1
# In Visual Studio Code:
# Ctrl + Shift + P → "Java: Clean Java Language Server Workspace"
# Then from the repository root
mvn clean install

# Then run the built solution:
# Run -> 'Start Debugging' in VSCode (CTRl + F5)
```

**From Eclipse:**

* Right click `App.java` in the `maze` module.
* Choose `Run As → Java Application`.
* This requires that you have compiled a Maven Build (see above) and that you are using the following VM arguments for the Run Cofiguration:

```
--module-path "${project_loc:main.game.maze}/target/libs" --add-modules javafx.controls,javafx.fxml,javafx.media
```

**After starting the game**

When the game starts, you will see a **"Select difficulty"** dialog.
This dialog is created in `App.setDifficulty`, which uses `DifficultyService` to read:

* `/xmi/difficulties/difficulties.xmi`
* The contained `EasyDifficulty`, `NormalDifficulty`, `HardDifficulty`
* The `currentDifficulty` reference in the model

The selected `Difficulty` instance is then passed into:

* `GameController.setStartDifficulty(...)`
* `OpponentRuntimeFactory.instantiateFromModel(this, startDifficulty)`

### 2.2 Show the effect of each difficulty

<span style="color:red; font-weight:bold">🔴 We need more here about behaviour module<span>
Repeat the following loop three times, once for each difficulty.

1. **Start the game**, pick one difficulty in the dialog:

   * First run: choose **Easy**
   * Second run: choose **Normal**
   * Third run: choose **Hard**
   * <span style="color:red; font-weight:bold">🔴 We should output the current threat level somewhere, and adjust scoring based on it.</span>

2. Play a short round and observe:

   * Enemy **max counts** per type (caps) change:

     * Loaded from `diff:getEnemyMaxCount` in `difficulties.xmi`.
   * Enemy **movement speed** and **damage** change:

     * Multipliers are read from the selected `Difficulty` and applied in `OpponentRuntimeFactory`
       (`monstersMovementSpeedMultiplier`, `monstersDamageMultiplier`, `instantDeath`).
   * Overall **threat level and density** change:

     * `maxThreat` from the difficulty model limits how many enemies are spawned.

3. Explain to the audience:

   * The **structure and base values** (caps, multipliers, max threat) are stored in `difficulties.xmi`.
   * The **behavior** (how enemies are chosen, how often they spawn, how multipliers are applied) is implemented in Java:

     * `DifficultyService` loads the model.
     * `App` and `GameController` choose a `Difficulty` object.
     * `OpponentRuntimeFactory` uses that `Difficulty` to:

       * Cap the number of each enemy type.
       * Scale movement speed and damage.
       * Adjust threat-based spawning.

4. Connect this to the **behaviour / movements module**:

   * The path-finding and patrol logic for enemies lives in the **behaviour module**:

     * EMF model: `main.game.maze.behaviour/src/main/resources/movements/movements.ecore`
     * Java API: `main.game.maze.behaviour` package (for example `PatrolBehavior`, `MovementBehavior`, `PathCalculator`, `DijkstraPathCalculator`, `AstarPathCalculator`).
   * In the running game, enemy movement is the combination of:

     * **Difficulty model** → how fast enemies move and how hard they hit.
     * **Behaviour / movements model** → where they move (patrol paths or patrol zones) and how they turn or accelerate.
   * For a focused patrol demo (outside the full game) you can run:

     * `maze/src/main/java/main/game/maze/ai/TestPatrolMovementController.java`

       * It loads `maze/src/test/patrol_behavior_example.xmi` via `PatrolHelper`.
       * It simulates an enemy moving along the patrol path and prints positions, showing how the behaviour model drives runtime behaviour.

You do **not** swap different XMI files on the command line; you always use the same `difficulties.xmi`, and the game code uses that model plus the dialog choice to decide how to behave.

---

## 3. Model validation demo: valid versus invalid opponent models

<span style="color:red; font-weight:bold">🔴 We need more here about behaviour module<span>

This part uses the **sample opponent models** and the **JUnit tests** to show how validation behaves, including failures.

### 3.1 How validation works

* Opponent models are EMF instances of `OpponentModel`.

* OCL and code-based constraints are hooked up through `OpponentsValidator`.

* At runtime, `OpponentRuntimeFactory` calls:

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

* If validation fails, the game does **not** silently continue; it throws an exception with diagnostic details.

### 3.2 Run the validation demo tests

From the repository root, run:

```bash
# Run only the opponent model validation tests
mvn -pl main.game.maze.opponents test -Dtest=ModelLoadSmokeTest
```

This gives a clear, automated demo of:

* **Correct models**: load and validate without errors.
* **Incorrect models**: load without parse errors, but fail semantic validation.

### 3.3 Optional: provoke a runtime validation error in the game

If you want to show what happens in the actual game:

1. Copy the invalid sample to the runtime location:

   * Take `opponentModelFailMaxThreat.xmi`.
   * Replace `maze/src/main/resources/xmi/opponents/opponentModel.xmi` with that content (or intentionally break the existing file).

2. Rebuild and run the game:

   ```bash
   mvn clean install
   ```

   Then run the game as described above.

3. When `OpponentRuntimeFactory.instantiateFromModel(...)` runs, it will:

   * Load the opponent model.
   * Call `validateOrFail(...)`.
   * Throw an `IllegalStateException` with diagnostic details because the constraints (for example, related to max threat) are violated.

Explain that this is **intentional**: invalid DSL models are rejected early, with structured diagnostics, instead of producing undefined behavior or weird bugs in the running game.

### 3.4 Behaviour / movements model: loading and validation

Although the behaviour / movements module does not currently have a separate JUnit suite, it has a clear loading and validation path that you can demo:

* The EMF behaviour model is generated into the `main.game.maze.behaviour` module:

  * Types like `PatrolBehavior`, `PatrolPoint`, `PatrolZone`, `Position`, `MovementBehavior`, and the different `PathCalculator` implementations.

* A small helper in the game module, `maze/src/main/java/main/game/maze/config/PatrolHelper.java`, is responsible for:

  * Loading a patrol model from XMI:

    ```java
    String modelPath = "src/test/patrol_behavior_example.xmi";
    PatrolBehavior model = PatrolHelper.loadPatrolModel(modelPath);
    ```

  * Validating the model in `PatrolHelper.fromModel(...)`:

    * It checks that either a patrol path or a patrol zone exists.
    * It verifies that all waypoints are within the map bounds.
    * It rejects NaN coordinates and structurally invalid patrol definitions by throwing `IllegalArgumentException` with a descriptive message.

* The sample XMI for this is:

  * `maze/src/test/patrol_behavior_example.xmi`

  It defines a `behaviour:PatrolBehavior` with several `<path time="…">` elements and `<point posX="…" posY="…"/>` waypoints.

* To show the behaviour model in action:

  1. Open `TestPatrolMovementController`:

     * `maze/src/main/java/main/game/maze/ai/TestPatrolMovementController.java`

  2. Run its `main` method from your IDE (VS Code or Eclipse) with the project built.

  3. The demo will:

     * Load `patrol_behavior_example.xmi` through `PatrolHelper`.
     * Convert it to a runtime `PatrolDefinition`.
     * Use `PatrolFollower` and `PatrolMovementController` to step through the patrol.
     * Print out the simulated movement, or throw validation errors if you intentionally break the XMI (for example, by moving a waypoint outside the map).

This ties the **behaviour / movements model** directly to:

* EMF-based model loading (`PatrolBehavior`).
* Procedural validation (`PatrolHelper.fromModel`).
* Runtime movement behaviour (`PatrolMovementController`, `PatrolFollower`).

---

## 4. Short version demo script:

You can use the following as a spoken script:

1. **Start the game.**

   * The difficulty choices you see here come from `difficulties.xmi`.
   * See Easy, Normal, Hard, and briefly demonstrate how the enemy behavior changes for each run.
   * XMI provides the data, while the Java code (`DifficultyService`, `App`, `GameController`, `OpponentRuntimeFactory`) uses that data to apply multipliers and caps.

2. **Run the model validation tests.**

   * Run `mvn -pl main.game.maze.opponents test -Dtest=ModelLoadSmokeTest`.
   * <span style="color:red; font-weight:bold">🔴 We need something for difficulty and behaviour module also</span>
   * See that valid samples pass and the invalid sample is explicitly expected to fail validation.

3. **(Optional) Show a runtime failure.**

   * Swap in the invalid opponent model.
   * Restart the game and show that it aborts with a clear "Invalid opponent model" error instead of starting with a broken configuration.

4. **(Optional) Show the behaviour / movements module.**

   * Open `maze/src/test/patrol_behavior_example.xmi` and show the patrol waypoints.
   * Run `TestPatrolMovementController` from the `maze` module. <span style="color:red; font-weight:bold">🔴 Must be implemented</span>
   * See the point  that:

     * The patrol path is defined in the EMF behaviour model.
     * `PatrolHelper` validates the model and converts it into runtime data. <span style="color:red; font-weight:bold">Must be implemented</span>
     * The controller then moves a simulated enemy along that path, using the same behaviour logic that the game will use once you integrate patrols into real enemies.

This ties together:

* The **sample models** (difficulties, opponents, and behaviour / movements patrol).
* The **runtime behavior** of the game.
* The **validation behavior** for both correct and incorrect models.
* The **build pipeline**, where `make.ps1` / `make` handle the local p2 mirror plus Maven build, and `Run-P2AndBuildCheck.ps1` gives a quick "build and run" flow for the demo.

## 5. Build automation: GitHub Actions, `make`, and `make.ps1`

The project has a small but coherent build story that is shared between local development and CI.

- **GitHub Actions workflows** live in `.github/workflows/`:
  - `buildtest.yml` runs on pull requests and performs a **full Tycho + game build**:
    - Checks out the repo and sets up Temurin JDK 24 with Maven caching.
    - Caches the local p2 mirror in `releng/local-p2` based on `releng/mirror/pom.xml` and `releng/maze.target`.
    - Runs a Tycho build of the Eclipse plug-ins and p2 repository, then uploads the generated p2 site.
    - In a separate `game` job, installs Xvfb and runs `mvn … -pl maze -am … clean verify` to build and test the JavaFX game headless on Linux.
  - `main.yml` is a **JavaFX-only pipeline** that triggers on pull requests touching the `maze` module or workflow files:
    - Sets up JDK 24 and runs the same `xvfb-run … mvn -pl maze -am … clean verify`.
    - Uploads Surefire test reports and the built game jar from `maze/target`.

- **`make` (Makefile)** is a Windows-friendly command-line shortcut that mirrors the CI steps:
  - `make` or `make all` runs:
    1. `toolchain-info` → prints Maven and Java versions.
    2. `mirror` → (re)builds the local p2 mirror into `releng\local-p2` and updates `.mirror.stamp` based on `releng/mirror/pom.xml`.
    3. `clear-tycho-cache` → removes the local Tycho p2 cache.
    4. `build` → runs `mvn -U -DskipTests=false clean verify` for the full multi-module build.
  - Additional targets like `force-mirror` and `clean-mirror` let you control the local p2 mirror explicitly.

- **`make.ps1`** is a PowerShell wrapper that exposes the same flow with a single parameter:
  - Usage: `./make.ps1 all`, `./make.ps1 mirror`, `./make.ps1 build`, `./make.ps1 clear-cache`, `./make.ps1 toolchain`.
  - Internally it:
    - Shows toolchain info (`mvn -version`, `java -version`).
    - Checks whether the mirror in `releng\local-p2` is outdated based on its stamp and `releng\mirror\pom.xml`, and rebuilds it via `mvn -f releng/mirror/pom.xml -U verify` if needed.
    - Clears the Tycho cache.
    - Runs the same full Maven build as the CI pipelines (`mvn -U -DskipTests=false clean verify`).

In short: **GitHub Actions** enforce the full build and tests on pull requests, while **`make`** and **`make.ps1`** give you the same steps locally with one command.```

