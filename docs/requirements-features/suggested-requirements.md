# Suggested Requirements

## Candidate Additions

### Ghost solidification and JavaFX input focus follow-on suggestions

- **SR-124** *(Observability, ghost solidification)*: When a ghost transitions from phasing to solid, a DEBUG-level event should be emitted including the ghost's ID, position, and the new stored energy value. This allows QA to verify the solidification tick without attaching a debugger, and makes diagnosing future regressions to the `drainNonTangientEnergy` return value trivial.

- **SR-125** *(DDD, ghost solidification)*: The EMF model's `nonTangibilityEnergy` field stores a `double` but the XMI-generated EMF setter silently truncates it to `int`. Consider extracting a `GhostEnergyModel` value object that stores the energy as a `double` internally and exposes `setEnergy(double)` / `getEnergy()` without truncation. This removes the hidden invariant that all callers must currently know about and document.

- **SR-126** *(Observability, JavaFX input focus)*: `GameController.handleKeyPressed` and `handleKeyReleased` should emit FINE-level log entries when `gameBoard` loses focus and when it regains it (via the event filter and `requestFocus` calls). This makes focus transitions visible in logs so future regressions (input stops responding) can be diagnosed without a debugger.

- **SR-127** *(12-Factor, JavaFX input focus)*: The `Platform.runLater` queue flooding fix (moving `updateProjectiles` from the AI thread to `Platform.runLater`) is correct but makes the latency of projectile position updates non-deterministic relative to the game loop. Consider introducing a fixed-rate JavaFX `AnimationTimer` solely for projectile updates, decoupled from the AI thread, so projectile physics are always advanced at frame rate rather than at AI tick rate.

- **SR-128** *(Testing, JavaFX input focus)*: The focus-loss scenario (Tab key stealing focus from `gameBoard`) should be covered by an automated UI integration test using `TestFX` (headless). The test would: load `game.fxml`, start gameplay, fire a `KeyCode.TAB` event at the scene, then fire directional key events and assert they are still received by the game.

- **SR-129** *(Observability, Issues 1-2)*: Player bomb lifecycle events shall be emitted at DEBUG level in both frontends, including `placedAt`, `fuseMs`, `detonatedAt`, and `targetsHit`. This enables parity verification that space-bar attacks are delayed-fuse bomb detonations rather than instant damage paths.

- **SR-130** *(UX, Observability)*: When the bomb inventory changes, the HUD should briefly pulse or tint the bomb counter and emit a compact status message such as `Bombs left: 2`. This makes the remaining inventory visible at a glance and helps QA verify that each planted bomb decrements the counter in both frontends.

- **SR-131** *(Correctness, Issue 1)*: A player bomb detonation MUST only damage what its directional flame actually reaches (capped per-direction budget, stopped by walls and the flame corridor). The libGDX detonation path previously also invoked the debug "kill all enemies" routine, defeating the directional budget. Regression covered by `GdxGameCombatAndEnemyFlowSupportTest.applyDirectionalFlameExplosion_leavesEnemiesOffTheFlameCorridorUntouched`. Suggestion: forbid production code from calling the `killEnemies` debug helper outside the terminal command path (e.g. move it behind a `DebugCommands` boundary) so it cannot be wired into gameplay by accident.

- **SR-132** *(Reliability, Issue 2)*: JavaFX gameplay MUST self-heal keyboard focus so player input cannot become permanently unresponsive after focus is lost to a transient node or dropped to `null`. A per-frame guard (`FxFocusGuard.shouldReassertFocus`) re-asserts focus on `gameBoard` unless a text input control (the in-game terminal) legitimately holds it. Unit covered by `FxFocusGuardTest`. Suggestion (extends SR-128): add a headless `TestFX` integration test that drops the focus owner to `null` mid-play and asserts directional keys still drive movement on the next frame.

- **SR-203** *(Portability, CRR-9, Issue #155)*: The Tycho target definition (`releng/maze.target`) MUST resolve on Windows, Linux and macOS without per-machine edits. `${project_loc:...}` expands to an OS-native path, so on Windows it produces an invalid `file:` URI with backslashes. Suggestion: add a CI matrix job (ubuntu + windows) that runs `make-javafx.ps1 quick-no-tests` so OS-specific target or script regressions are caught before merge. For 12-Factor config, let a `MAZEGAME_P2_URI`-style setting override the mirror location.
  - *Status (PR #152):* target resolution implemented (`Directory` location) and validated on Windows and Ubuntu 24.04; see RTM SR-203. The CI build matrix and the `MAZEGAME_P2_URI` override remain suggestions.

- **SR-204** *(Testability, Observability, Java 21 selection)*: Build-script helpers MUST have automated tests that CI runs. `tools/tests` now holds Pester 5 tests for `make-javafx.ps1` (`Get-PathWithEntryFirst`). Suggestions: (1) add an `Invoke-Pester ./tools/tests` step to the GitHub workflows on Windows and Ubuntu runners; (2) have `install.ps1` fail with a non-zero exit code (or a clear `[!!]` line) when `write-launch-env` fails, instead of logging `[..]` and exiting 0; (3) update the step 2 check list in `Run-P2AndBuildCheck-javafx.ps1`, which still expects `org.eclipse.emf.sdk`, `org.eclipse.emf.codegen` and `org.eclipse.xtext.sdk` features that `releng/mirror/pom.xml` does not mirror, so step 2 reports a false FAIL; (4) DRY: `Run-P2AndBuildCheck-javafx.ps1` keeps its own copy of the Java 21 detection (`Get-Java21Home`, `Use-Java21OrFail`); dot-source `make-javafx.ps1` as `make-libgdx.ps1` already does, so fixes such as the Java 21 PATH ordering (`install.ps1` left an earlier JDK active) land in one place.
  - *Status (PR #152):* (1)–(4) implemented. `script-tests` CI job (Ubuntu + Windows); `install.ps1` exits 1 with `[!!]` lines; step 2 derives its check from `releng/mirror/pom.xml` (`Get-MirrorRequiredUnitIds`, `Get-MirrorAvailableUnitIds`, `Test-MirrorUnits`); `Run-P2AndBuildCheck-javafx.ps1` dot-sources `make-javafx.ps1`. See RTM SR-204.

- **SR-205** *(Compliance, Maintainability)*: The source code is now MIT-licensed (`LICENSE`). Suggestions: (1) audit the third-party assets under `data/` (YouTube-sourced music and sounds, artlist.io and OpenGameArt images) and record each asset's license and attribution in a `THIRD_PARTY_NOTICES.md`, replacing any asset whose terms do not allow redistribution; (2) confirm with past contributors that they agree to MIT for their contributions; (3) add a `.gitattributes` with `*.sh text eol=lf`, so shell scripts keep LF line endings even when copied out of a Windows checkout (for example into WSL).
  - *Status (PR #152):* (3) implemented (`.gitattributes`). (1) and (2) are owner decisions and remain open.

### gameBoard clip ownership follow-on suggestions

- **SR-121** *(DDD, gameBoard clip)*: Extract a `GameBoardClipOwner` interface with a single `setClip(Rectangle)` method. `FxGameRenderCoordinator` implements it; no other class may call `gameBoard.setClip()` directly. This makes the single-owner contract explicit and prevents future regressions where a second class overwrites the clip.

- **SR-122** *(Observability, gameBoard clip)*: `FxGameRenderCoordinator.installGameBoardClip()` silently no-ops when `gameBoard` is null. Add a WARN-level log entry listing `gameBoard == null` so developers can detect premature coordinator construction without a debugger.

- **SR-123** *(12-Factor, gameBoard clip)*: Viewport dimensions are currently read from `Scene.getWidth()/getHeight()` at render time. Add a `MAZE_VIEWPORT_WIDTH` / `MAZE_VIEWPORT_HEIGHT` override environment variable that, if set, bypasses scene introspection — enabling headless integration tests to set a known viewport size without a real JavaFX stage.

### Scrolled-area background follow-on suggestions

- **SR-118** *(DDD, scrolled-area background)*: The `gameBoard` Pane background image in `game.fxml` is now redundant for the in-game state (the maze canvas carries its own background). Consider removing the `<background>` element from `game.fxml` and replacing it with a neutral pre-game placeholder (e.g., a dark-colour CSS fill via `fx:stylesheet`) so the pre-game and in-game states both use the correct difficulty-specific image.

- **SR-119** *(Observability, scrolled-area background)*: `FxMazeCanvasRenderer.drawBackground()` silently skips the fill when the image resource is missing. Add an WARN-level event published to an application-level diagnostic bus (or at minimum a visible in-game notification) so QA can distinguish "background image missing" from "background successfully drawn" without attaching a debugger.

- **SR-120** *(12-Factor, scrolled-area background)*: The background image path is currently embedded in `MazeVisualStyleConfig` (loaded from XMI or properties). For full 12-Factor compliance (config via environment), add a fallback environment variable `MAZE_BG_EASY`, `MAZE_BG_NORMAL`, `MAZE_BG_HARD` that can override the XMI-configured paths at runtime without recompilation.

### Camera scroll clip and libGDX menu music follow-on suggestions

- **SR-113** *(Observability, camera scroll clip)*: When the JavaFX camera clip is recalculated each frame, a DEBUG-level log entry should be emitted at a throttled rate (e.g., once per second) including `translateX`, `translateY`, `clipX`, `clipY`, `viewportWidth`, and `viewportHeight`. This allows QA to diagnose future clip/translation divergences without attaching a debugger.

- **SR-114** *(DDD, camera scroll clip)*: `FxGameRenderCoordinator.computeClipRect` and `computeCameraTranslation` represent a mini viewport domain. Consider extracting a `ViewportScrollState` value object that holds translation + clip-rect together, making the dependency between the two values explicit and preventing future callers from updating one without the other.

- **SR-115** *(12-Factor, libGDX menu music)*: The libGDX audio engine installation sequence (call `GdxBackend.install()` exactly once at startup in `GdxGame.create()`) shall be enforced by a lifecycle contract test: a headless `GdxGame` stub must confirm that after `create()` the `AudioEngine.get()` instance is not a `NoopAudioEngine`. This guards against future regressions where the install call is moved or removed.

- **SR-116** *(Observability, libGDX menu music)*: `GdxBackend.install()` shall emit a one-time INFO log entry confirming the engine type and the thread on which it was installed (render thread). This makes it immediately apparent in run logs whether the audio backend was successfully initialised before any screen was shown.

- **SR-117** *(Parity, CRR-5)*: `MenuScreenController` now owns a `GdxGameAudioCoordinator`. If future screens are added (e.g., a settings screen or loading screen), each should follow the same pattern: own an audio coordinator instance and call `switchTo*Music()` in `show()` and `stopAll()` in `dispose()`. A shared `AudioAwareScreen` abstract base class (implementing `Screen`) could enforce this contract at compile time.

### PR #72: F11 Breakable Walls — follow-on requirements

- **SR-99** *(Parity, CRR-5)*: Breakable wall support shall be available in the libGDX frontend as well as JavaFX. The libGDX `GdxGameScreenController` shall integrate `WallCollisionUtil.findFirstHitWall(...)` and delegate wall damage through a shared entry point equivalent to `GameController.applyProjectileDamageToWall(...)` so that wall destruction and nav-graph rewiring behave identically across frontends.

- **SR-100** *(Visual feedback, GR)*: When a breakable wall absorbs damage but is not destroyed, a visual damage cue (e.g., crack overlay or color tint) shall be rendered on the wall segment to communicate remaining health to the player. Both frontends must use a shared `WallDamagePresenter` interface so the visual cue logic is not duplicated inline.

- **SR-101** *(UX, Layout)*: Easy difficulty board height has been extended to 665 px (was 600 px). Both JavaFX and libGDX frontends shall continue deriving the Easy board height from `StageConstants.BoardMaxY` (single source of truth) rather than hard-coding it, so future layout adjustments require only that one constant to be changed.

- **SR-102** *(DDD, 12-Factor)*: Board dimension constants (`StageConstants.BoardMaxX/Y`, `BoardMaxXMedium/YMedium`, `BoardMaxXLarge/YLarge`) are currently hard-coded Java constants. Consider externalising per-difficulty board sizes to a config file (YAML or XMI) loaded at runtime so layout changes can be applied without recompilation, aligning with 12-Factor principle IV (backing services / config).

- **SR-101** *(Observability, DDD)*: Wall-destruction events shall be published to a domain event bus (e.g., `WallDestroyedEvent`) that subscribers (HUD, audio, achievement system) can consume independently. This decouples sound and UI feedback from the `GameMazeWorld` damage pipeline.

- **SR-102** *(Model-driven breakability, CRR-1)*: The seeded random assignment of breakable walls shall be replaced by reading the `WallMaterial.breakable` and `WallMaterial.hitPoints` attributes from the loaded XMI model, so level designers can configure which wall types are destructible without code changes. The fallback seeded random strategy shall remain active when no material model is available.
  - *Partial implementation (F11 Phase 2)*: HP values are now fully driven by `WallMaterialSpec` (Glass 5 HP, Dirt 10 HP, Wood 20 HP, Stone 40 HP) which mirrors `walls.xmi`. `GameMazeWorld.assignBreakableWalls(long, List<WallMaterialSpec>)` accepts XMI-backed specs from callers with `WallRegistry` access. `DEFAULT_BREAKABLE_MATERIALS` provides the fallback. Remaining work: wire `FxGameSessionBootstrapper` / `RuntimeVisualModelLoader` to call the overload with registry-backed specs so the XMI model is the live authority at startup.

- **SR-104** *(DIP boundary, F11)*: The `WallMaterialSpec` record in `main.game.maze.mazeworld` serves as the dependency-inversion boundary between the `mazeworld` domain and the EMF `walls` model. All breakable-wall HP assignment inside `GameMazeWorld` must reference only `WallMaterialSpec`; never import `WallMaterialBaseType`, `WallDefinition`, or `WallRegistry` in the `mazeworld` module. Callers at the application boundary (bootstrapper, visual model loader) bridge the two modules by building `WallMaterialSpec` instances from registry entries before calling `assignBreakableWalls`.

### F11-EXT: Additional wall damage sources (see `docs/plans/f11-wall-damage-sources-plan.md`)

- **SR-113** *(Gameplay, CRR-5)*: The player shall be able to trigger a Bomberman style flame attack with the space bar. The player starts with three bombs, each bomb deals 100 HP, and any damage in excess of the first enemy's remaining HP shall carry over to the next enemy in the target order. The same behavior shall be available in both JavaFX and libGDX frontends through their existing input command and character runtime boundaries.

- **SR-105** *(Player weapon, F11-EXT/DS-1)*: The player character shall be able to fire projectiles that damage breakable walls. Each shot deals a configurable HP amount (default 3 HP) so Glass walls (5 HP) require 2 shots, Wood (20 HP) requires 7, and Stone (40 HP) requires 14. Both JavaFX and libGDX frontends must support this via the existing `applyProjectileDamageToWall` / `applyWallDamage` pipeline. The player weapon shall implement `ICanDamageWalls` to formalise the damage contract.

- **SR-106** *(PumpkinBomber explosion splash, F11-EXT/DS-2)*: On projectile detonation, `PumpkinBomberCharacter` shall damage all breakable walls whose geometry intersects the explosion's splash radius, in addition to damaging the player. A shared `WallCollisionUtil.findWallsInRadius(cx, cy, radius, walls)` helper shall be introduced in `main.game.maze.mazeworld` so both frontends share the spatial query without duplication.

- **SR-107** *(Zombie melee wall bash, F11-EXT/DS-3)*: When a `ZombieCharacter`'s movement is blocked by a breakable wall for consecutive ticks, it shall apply melee bash damage (`zombie.getDamage() / 4`, minimum 1) per blocked tick to that wall. This allows zombies to slowly pound through Glass and Dirt walls but be effectively stopped by Stone and Steel, creating emergent difficulty variation based on wall material.

- **SR-103** *(12-Factor, WR-5)*: The breakable-wall seed value (currently `42L`) and the HP tiers (10 HP / 20 HP at 30 % / 20 % probability) shall be externalized to a configuration property or environment variable so they can be tuned per deployment without recompilation.

### PR #71: Improved Cross-Platform Installer (install.ps1)

These requirements define the automated developer onboarding experience and installer validation:

- **SR-93** *(Portability, CRR-21)*: The installer script `install.ps1` shall use environment-aware paths (e.g., `$env:ProgramFiles`, `$env:LOCALAPPDATA` on Windows) instead of hard-coded absolute paths. This ensures the script works on systems with non-standard installation layouts (e.g., different drive letters, multi-user environments) and respects OS conventions. Unix paths shall use standard FHS locations (`/usr/lib/jvm`, `/opt/java`, `/opt/homebrew/opt`) with fallback detection.

- **SR-94** *(Modularity, CRR-2)*: The installer shall extract package-manager detection into a reusable `Get-AvailablePackageManager` function to eliminate code duplication between JDK and Maven installation flows. This function shall return the name of the first available package manager (winget, choco, apt, dnf, pacman, brew) so installation orchestration can be decoupled from platform-specific package manager logic.

- **SR-95** *(Configuration, WR-5)*: Maven version shall be defined in a separate `Get-LatestMavenVersion` function rather than hard-coded in the installation logic. This allows Maven version updates without modifying the core installer flow and supports future dynamic version discovery (e.g., querying Maven central for the latest stable release).

- **SR-96** *(Testing, WR-3/WR-6)*: Installer validation tests shall be created to verify that:
  1. The script runs on Windows with JDK 21 and Maven installed without errors.
  2. Java 21 detection correctly identifies JDK 21 and rejects other versions.
  3. Environment variables (JAVA_HOME, PATH_TO_FX) are set correctly and persist across terminal restarts.
  4. VS Code extensions are installed (if `code` CLI is available).
  5. The generated `.vscode/maze.launch.env` file contains valid environment variables that work with the game build.

- **SR-97** *(Error Handling, CRR-10)*: Java version detection shall return -1 on error (instead of 0) to distinguish between "Java version check failed" and "Java does not exist." All detection functions shall provide debug-level logging on failure to aid troubleshooting.

- **SR-98** *(Documentation, WR-18)*: An `INSTALLER.md` file shall document the installer's architecture, supported platforms (Windows, macOS, Linux), package managers, fallback strategies, and known issues (e.g., PATH persistence on Unix shells).

### F10 and F20 follow on suggestions from implementation

- **SR-99** *(DDD, portability)*: Introduce a shared projectile domain service in `maze-common-frontend` that hosts deterministic simulation for `STRAIGHT`, `LOB`, and `BEAM`. Both JavaFX and libGDX currently implement backend local runtime loops. A shared service would reduce divergence risk and simplify parity maintenance.

- **SR-100** *(Observability)*: Add structured debug telemetry for enemy ranged attacks, including `enemyId`, `projectileType`, `blockedByWall`, `hitApplied`, and `damage`. This should be sampled and disabled by default, then enabled in debug runs to support balancing and parity diagnostics.

- **SR-101** *(12-Factor, config)*: Externalize projectile visual tuning values, for example beam lifetime and lob shadow intensity, to a backend neutral config source so QA can tune readability without recompiling either frontend.

### F25 follow-on — Ghost Visibility Level (identified during implementation)

- **SR-51:** Once F16 (DSL / scripted scenario support) is complete, `Ghost.visibilityLevel` shall be configurable via the scenario DSL so level designers can specify per-ghost opacity caps without editing the raw XMI model. The DSL entry shall be validated against the `[0, 100]` domain constraint defined in the Ecore metamodel, and invalid values shall produce a clear authoring-time error.

- **SR-52:** The HUD shall display a low-visibility warning indicator when the active area contains a ghost whose `visibilityLevel` is below a configurable threshold (default 30). The threshold of 30 aligns with the manual test plan (section 1, setup line 14) which configures test ghosts at `visibilityLevel = 30` as the boundary for heavy transparency, and with the implementation plan (section 5.3) which defines `visibilityLevel < 30` as the alert condition. The indicator shall be rendered in both the JavaFX and libGDX frontends using the shared `UiScheduler` / HUD facade to maintain CRR-5 parity. This extends the scope defined in SR-16 (HUD observability).

- **SR-53:** Observability — when the phasing cap is active in `GhostNonTangibilityService.calculateOpacity(double, int)` (i.e. `phasingOpacity > baseOpacity`, meaning the raw phasing formula would exceed the ghost's configured visibility ceiling), a structured debug-level log entry shall be emitted including `energy`, `visibilityLevel`, `baseOpacity`, and `clampedOpacity`. The log entry shall be suppressible in production builds via the standard Java `Logger` level mechanism and shall not allocate on the hot path when the level is disabled.

### JavaFX MVC + Command/Registry refactor (Phase 5 of `docs/plans/javafx-gamecontroller-mvc-command-refactor.md`)

These requirements bring the JavaFX frontend to structural parity (CRR-5) with the libGDX MVC + Command/Registry architecture. They are scheduled to be ratified in Phase 5 of the JavaFX refactor plan, on a dedicated branch and pull request separate from the libGDX round.

- SR-42: The Command and key binding registry input core (logical action enum, key binding registry, input frame contract, input router, edge key tracker, and command/context types) shall be shared in `maze-common-frontend` and consumed by both frontends. The dispatch loop shall be generalized over a frontend neutral key type (or a neutral key code with a per frontend adapter) so adding a new key or action does not require modifying the router. libGDX shall be migrated to consume the promoted types so it keeps compiling and its existing input tests stay green.
- SR-43: JavaFX gameplay input shall be handled via the shared key binding registry resolving logical actions to command objects, replacing the inline `handleKeyPressed` switch on `KeyCode`. Key polling shall be centralized in one JavaFX input snapshot reader, and movement, terminal, high scores, spanning tree, path hint, and return to menu shall each be expressed as a command without scattered keyboard checks in the controller.
- SR-44: JavaFX gameplay mutable runtime state shall live in a dedicated model boundary, separate from lifecycle, rendering, input, and FXML concerns, so the controller coordinates behavior without directly owning every gameplay field.
- SR-50: During JavaFX input migration to command and registry architecture, command dispatch shall emit lightweight structured diagnostics (command name, input action, and mode) through a frontend neutral observability hook enabled in debug builds. This shall support parity troubleshooting between JavaFX and libGDX without changing gameplay behavior.

### Test coverage improvements (from branch `feature/improveTestCoverage`)

- **SR-70**: The `IAudioEngine` test double (`CapturingAudioEngine`) shall be maintained as a first-class shared fixture in `maze-common-frontend` test utilities so all frontend modules can exercise audio side-effects without touching real hardware.
- **SR-71**: All action-layer classes (`GameOverAction`, `WinGameAction`, `StartScreenAction`) shall have unit tests covering at least: construction, happy-path side-effects (audio calls), and graceful no-op when the JavaFX scene is null.
- **SR-72**: The `GdxGameCombatAndEnemyFlowSupport` shall remain a static-utility class with pure methods so it stays testable headlessly without a libGDX GL context.
- **SR-73**: The `DifficultyService` and `CompositionResolverImpl` shall expose all public contracts through the `DifficultyService` / `CompositionResolver` interfaces; direct use of concrete implementation classes in non-test code should be avoided (DIP).
- **SR-74**: Enemy type caps in `CompositionResolverImpl` shall be enforced as a hard ceiling: `redistributeToTotal` shall never push a type above its cap, even when total redistribution requires additional units that cannot be allocated without violating a cap.

### F8 spawn cap enforcement — DDD / 12-Factor / Observability suggestions

- **SR-80** *(Observability)*: `EnemySpawnPlanner.clampToCapLimit(...)` currently relies on callers
  to emit the INFO log when a cap is applied. Consider introducing an optional `SpawnCapListener`
  functional interface that callers register at construction time, so cap events can be routed to
  structured telemetry (metrics counter, event bus) without embedding logging policy in the
  shared helper.

- **SR-81** *(12-Factor, Config)*: `EnemyMaxCount` values are baked into `difficultiesBasic.xmi`.
  Per 12-Factor principle III (Config), externalise per-type caps to a hot-reloadable config layer
  (environment variable overrides or a sidecar YAML) so QA can adjust spawn limits between runs
  without rebuilding.

- **SR-82** *(DDD)*: `clampToCapLimit` and `capsFromDifficulty` belong to the **Difficulty**
  bounded context (they enforce invariants owned by `Difficulty`). Consider moving them out of
  the `opponents.util` package and into a `difficulties.spawn` sub-package so the module boundary
  aligns with the domain boundary. The opponents module would then depend on the difficulties
  module only for data, not for policy.

### 12-Factor App / Observability suggestions

- **SR-75** *(12-Factor, Config)*: Enemy composition profiles (profile name, enemy count, ratios, caps) should be externalized to an XMI or YAML file loaded at runtime so difficulty tuning does not require recompilation.
- **SR-76** *(Observability)*: `CompositionResolverImpl.resolve(...)` should emit a structured trace log (profile name → final composition map) at DEBUG level so difficulty tuning in QA is auditable without a debugger.
- **SR-77** *(Observability)*: `GdxGameCombatAndEnemyFlowSupport.triggerWin(...)` should emit a structured event (timestamp, player position, score) to an optional event sink so win-condition analytics can be collected without modifying game logic.

### Test infrastructure / JaCoCo coverage gate (from branch `feature/workOnUnimplemetedFeature`)

- **SR-83** *(DDD, Modularity)*: `CapturingUiScheduler` and other shared test doubles (`CapturingAudioEngine`, `FakeWorldView`, `SpyActionSink`) should be consolidated into a dedicated `maze-test-util` module so every frontend module can import them without duplicating the helper package in each module's test tree.

- **SR-84** *(12-Factor, Dev/Prod Parity)*: Add a `<profile>` entry named `coverage-local` (activated by `-Pcoverage-local`) that mirrors the CI coverage profile so developers can run `mvn verify -Pcoverage-local` locally and reproduce exactly the JaCoCo gate checks that run in CI.

- **SR-85** *(Observability)*: Upload JaCoCo HTML reports as a CI build artifact so code coverage trends are visible per run in the GitHub Actions summary without downloading the JAR or running locally.

- **SR-86** *(Observability)*: Set per-module JaCoCo thresholds in a dedicated Maven property (e.g., `jacoco.line.minimum`) so the threshold for GL-bound modules like `maze-libgdx` can be adjusted in one place without editing XML execution configurations directly.

- **SR-91** *(12-Factor, Dev/Prod Parity)*: `OpponentRuntimeFactorySpawnTest` calls `OclBootstrap.init()` which requires the OCL ecore delegate JAR — a system-scope dependency only available after Tycho builds the Eclipse plug-ins. Introduce a Maven test profile or a module-level `pom.xml` configuration that copies the OCL jar into the test classpath for `maze-javafx-backend`, so developers can run the full test suite locally without CI. Until then, null-guard paths (already covered by `OpponentRuntimeFactoryNullGuardTest`) provide local coverage.

- **SR-92** *(Observability)*: JaCoCo line-coverage thresholds should be split per package within `maze-javafx-backend` (e.g., separate minimums for `characters`, `javafx.controller`, `runtime`) so coverage regressions in a single package are immediately identifiable rather than masked by a module-level aggregate.

### Wall registry classpath and spawn retry post-fix — DDD / 12-Factor / Observability suggestions

- **SR-87** *(DDD)*: `WallRegistry` and `WallMaterialBaseType` are generated artifacts tightly coupled to the wall sub-domain. Introduce a `WallDomainService` façade that hides the static registry behind an injectable, mockable interface. This removes static dependency coupling from consumers (`FxGameSessionBootstrapper`, `RuntimeVisualModelLoader`) and aligns with DDD's domain-service pattern.

- **SR-88** *(12-Factor, Config IV — Backing Services)*: Wall material image paths and `WallMaterialBaseType` constants are embedded in the EMF model and generated code. Externalising them to an env-overridable config file would let operators swap wall art sets without recompilation, aligning with 12-Factor principle IV.

- **SR-89** *(Observability)*: `FxGameSessionBootstrapper` and `RuntimeVisualModelLoader` catch `ExceptionInInitializerError` and log at SEVERE. A structured metric (e.g., a Micrometer counter `wall.registry.init.failures`) would let an ops dashboard detect misconfigured deployments before players notice broken rendering.

- **SR-90** *(Observability)*: `OpponentRuntimeFactory.spawnByTarget` now shuffles candidates on every slot. Add a DEBUG-level log entry emitting the final spawn list (type, threat, slot count) per session so difficulty balancers can audit the result without attaching a debugger.

### F24 follow-on — Zombie Resurrection (identified during implementation)

- **SR-102** *(Observability)*: When a zombie resurrects (either via `ZombieCharacter.resurrect()` in JavaFX or `GdxGameCombatAndEnemyFlowSupport.tickResurrections()` in libGDX), a structured DEBUG log entry shall be emitted including `zombieId`, `resurrectionTimeMs`, `spawnX`, and `spawnY`. This allows balancers to audit resurrection frequency and position without a debugger.

- **SR-103** *(12-Factor, Config)*: The `RESPAWN_INVULNERABILITY_SECONDS` constant (currently `2f` in `GdxEnemyRuntime`) should be externalized to a config property so QA can adjust the post-respawn grace window without recompilation. The JavaFX equivalent (`PauseTransition` duration is zero; invulnerability is implicit via the dead period) should gain an explicit configurable window as well.

- **SR-104** *(DDD)*: The `/kill` terminal command currently short-circuits directly into `killEnemies()` without going through the game's event model. Introduce a `ZombieDeathEvent` domain event emitted on each kill so subscribers (scoring, analytics, wave managers) can react to death without coupling to the command handler.

- **SR-105** *(Testability)*: `ZombieCharacter.resurrect()` is a private method scheduled via `PauseTransition`. Extracting the resurrection payload into a package-private `ResurrectionHandler` functional interface would allow unit tests to invoke it synchronously without waiting for a JavaFX timer, removing the test-time dependency on `Platform.startup()`.

### Ranged projectile speed (F26) — 12-Factor / Config suggestions

- **SR-112** *(12-Factor, Config)*: The `opponentModel.xmi` projectile speed values (220.0 LOB / 280.0 STRAIGHT) should be exposed through the difficulty UI or a developer overlay so level designers can tune them without needing to edit the XMI by hand. This aligns with the 12-Factor App externalised-config principle.

### DDD boundary suggestions

- **SR-78** *(DDD)*: `PlayerConfig` and `CompositionResolverImpl` should live in a `config` bounded context with its own aggregate root (`DifficultyConfig`) that owns both the player config and enemy composition for a given difficulty level.
- **SR-79** *(DDD)*: The `characters` package in `maze-javafx-backend` has mixed concerns (rendering, game logic, audio). Consider splitting it into a `characters.domain` sub-package (state, damage, death) and `characters.view` sub-package (graphics, animations) aligned with DDD entity vs. value-object separation.

### F27 follow-on — Window sizing and camera follow (identified during implementation)

- **SR-108** *(Observability, 12-Factor)*: When the window is clamped to screen resolution (i.e. `boardSize > screenSize`), a DEBUG-level log entry should be emitted including `boardW`, `boardH`, `screenW`, `screenH`, and `clampedW`/`clampedH`. This allows QA to confirm the correct clamp is applied per difficulty without attaching a debugger.

- **SR-109** *(12-Factor, Config)*: The per-axis camera dead-zone (currently implicit — the camera starts following immediately when the player moves away from the viewport centre) should be externalisable as a configuration property (e.g., `camera.deadzone.pixels = 0`). A non-zero dead zone reduces camera jitter on small player movements and is a common game-feel tuning knob.

- **SR-110** *(DDD, CRR-5)*: Window-resize events (e.g., when the OS changes the available screen area mid-session on multi-monitor setups) are not currently handled. Both frontends should re-evaluate the clamped window size on a `Screen.onChanged` / libGDX `resize` callback so the game adapts dynamically to screen resolution changes without requiring a restart.

- **SR-111** *(Parity, CRR-5)*: A shared `WindowSizingPolicy` interface (or record) in `maze-common-frontend` could expose `clampToScreen(boardW, boardH, screenW, screenH)` so both JavaFX (`App.clampBoardToScreen`) and libGDX (`GdxGameLayoutSupport.resizeWindowForDifficulty`) share the same clamping logic, eliminating the risk of the two implementations diverging.

- **SR-112** *(UX)*: When the maze is larger than the screen and camera follow is active, a minimap overlay showing the full maze and the player's current position would significantly improve navigation. The minimap should be rendered at a configurable opacity (default 50 %) in a corner of the viewport.

- **SR-210** *(Observability)*: When `damageStatesEnabled=true` but `DamageStateResolver.resolveWithFallback` falls back, count the misses per path (e.g. a `SpriteResolutionStats` counter dumped on exit or in the debug overlay) so missing art is visible without enabling FINE logging.

- **SR-211** *(12-Factor, Build)*: Run `tools/sprite-gen/DamageSpriteGenerator.java` from the make scripts (`make-*.ps1 sprites`) and fail CI if regenerating changes committed PNGs, so generated assets never drift from the generator.

- **SR-212** *(DDD)*: Model the damage-state art set as part of `CharacterType` (e.g. an optional `DamageStateArtSet` value object with explicit per-state paths) once hand-drawn art with non-conventional names arrives, keeping the naming convention as the default.

- **SR-213** *(Assets)*: ~~Create the 8 missing directional ghost sprites~~ — done as SR-214 (generated). Replace with hand-drawn art when available, keeping the names. **Warning:** rerunning `tools/sprite-gen` generators overwrites existing files without asking, so commit hand-drawn art first and remove its base sprite from the generator's list (`GHOSTS` / `baseSprites()`) so it is not regenerated.

- **SR-216** *(12-Factor, Config)*: Expose the fire blast lifetime and corridor width (`FireBlast.DEFAULT_LIFETIME_SECONDS`, `FLAME_CORRIDOR_WIDTH`) through `PlayerConfig` / `Player.ecore` so designers can tune the blast without recompiling, with libGDX and JavaFX reading the same value.

- **SR-217** *(Observability)*: Show the number of fire puffs drawn per frame in the debug overlay (both frontends) so the cost of overlapping blasts is visible; cap the total if it grows large.

- **SR-218** *(UX, Parity)*: Use a soft radial-gradient puff texture in libGDX (one small generated texture drawn with `SpriteBatch`) so fire puffs get soft edges like a particle system instead of hard-edged `ShapeRenderer` circles; JavaFX can use `RadialGradient` fills for the same look.

- **SR-219** *(Assets, DDD)*: Ghost walk animation needs per-variant walk-frame naming (e.g. `ghost_medium_walk1-left.png`), because `SpriteAnimationUtil` treats the first digit in a file name as the frame number and the digit in `ghostN` is the variant. Add an explicit walk-frame path pattern on `CharacterType` instead of digit replacement.

- **SR-221** *(12-Factor, Config)*: Allow damage states to be switched off at runtime (e.g. `MAZE_DAMAGE_STATES=off` env var or a terminal command) without editing `opponentModel.xmi`, for performance testing and players who prefer the classic look.

- **SR-222** *(Observability)*: Show each enemy's `DamageState` in the debug overlay label (both frontends) next to behaviour/movement type, so damage-state transitions can be verified in game.

- **SR-223** *(Assets)*: Distinct `pumpkinbomber-elite.png` art for `pb_hard`/`pb_elite_beam`, then its `-left/-up` and damaged/bleeding/tired variants via the sprite-gen tools (the optional `pumpkinbomber-elite-*` set from #80).

- **SR-225** *(UX)*: Leave a fading scorch mark with pumpkin pulp where a pumpkin bomb lands (a few seconds, drawn under enemies), so players can read where the bomber has been aiming.

- **SR-226** *(DDD)*: Have `GdxEnemyRuntime` / `PumpkinBomberCharacter` publish a `ProjectileDetonated(x, y, type, splashRadius)` domain event and let a presentation listener create the burst visual and screen shake, instead of the runtime holding visual state (`ImpactEffect`). This also lets audio and stats subscribe to the same event.

- **SR-227** *(12-Factor, Config)*: Make the pumpkin burst lifetime range and shake window configurable (e.g. `MazeVisualStyleConfig` or env vars), alongside SR-216 for the fire blast.

- **SR-228** *(Observability)*: Count projectile detonations per bomber type (lob/straight, hit/miss) in the existing projectile telemetry, and show active explosion count in the debug overlay.

- **SR-232** *(Gameplay, MDD)*: ~~Gun pickup~~ — done as the laser rifle (SR-241), modelled in the `items` EMF model rather than the DSL (see SR-248). Original text: Gun pickup. The player does not start with it; it is found in the maze. Model it in the Xtext DSL, which already has `loot-table` / `item` with `type weapon` and `loot` references on opponents (nothing reads loot at runtime yet): add the weapon kind (`gun`), damage, ammo and the inflicted `DeathCause` (`shot`). Hits call `subtractHitPoints(dmg, DeathCause.SHOT)` (JavaFX) / the same `ICanDie.subtractHitPoints(dmg, DeathCause.SHOT)` on libGDX `GdxEnemyRuntime`; the shot remains (SR-229) already work.

- **SR-233** *(Gameplay, MDD)*: ~~Sledgehammer pickup~~ — done (SR-242); knocking down walls with it was left out on purpose (the request says it cannot harm walls). Original text: Sledgehammer pickup, found in the maze (not at start), crushes adjacent enemies (`DeathCause.CRUSHED`, remains already in place). Same DSL loot model as SR-232 (`weapon` kind `sledgehammer`, swing damage / reach); it could also let the player knock down breakable walls, which then fall and crush via SR-230.

- **SR-234** *(MDD, DDD)*: Per-enemy death rules in `opponents.ecore`: which `DeathCause`s a character type can suffer (e.g. ghosts immune to crushing) and whether it leaves remains, next to `damageStatesEnabled`; needs EMF regeneration. Today every enemy can die by every cause in both frontends and remains are chosen by sprite naming convention.

- **SR-235** *(MDD)*: Per-material collapse rules in `walls.ecore`: `crushesOnCollapse` (glass might shatter harmlessly) and the rubble palette, instead of the code-side `RubbleMaterial` mapping keyed by the material id prefix; needs regeneration of the walls model code.

- **SR-236** *(Parity bug)*: ~~JavaFX ghosts cannot die~~ — done in PR #163: every JavaFX enemy (`ComputerCharacter`) implements `ICanDie` through the shared `EnemyVitals`, so ghosts die, leave remains and follow the same rules as libGDX (see SR-238).

- **SR-237** *(Observability, 12-Factor)*: Count deaths per `DeathCause` per level in the run summary / debug overlay, and make the remains cap (`RemainsLedger.DEFAULT_CAPACITY`, 60) configurable.

### Weapons, pickups and kill credit — follow-on suggestions (SR-239..SR-247)

- **SR-248** *(MDD, Xtext)*: Add a `pickups` block to the Xtext DSL (spawn interval, caps per map size, and per kind amount / damage / reach / duration / sprite / sounds) that generates `items.xmi`, and connect the existing `loot-table` / `item` grammar to it so enemies can drop items. Today `items.ecore` is loaded with dynamic EMF (no generated code), which keeps the model editable without regeneration; the DSL would add validation and editor support.

- **SR-249** *(Multiplayer, DDD)*: Replace the `String` attacker id with a `PlayerId` value object, keep one `PlayerArsenal` / `PowerShield` per player in a `PlayerLoadout` aggregate, and show per-player standings (kills by cause, points) from `KillLedger.killsBy` on the end screen.

- **SR-250** *(Tech debt, DDD)*: Wall damage is attributed through an ambient `ThreadLocal` (`GameMazeWorld.attributeDamageTo`) so the existing `applyWallDamage` callers did not all change. Pass an explicit `DamageSource(attackerId, cause)` through `applyWallDamage` and the flame / laser engines instead, so attribution cannot leak across threads or be forgotten by a new caller.

- **SR-251** *(12-Factor, Config III)*: Allow overriding the items model (e.g. `MAZE_ITEMS_MODEL=/path/items.xmi`) and the spawn interval (`MAZE_PICKUP_INTERVAL_SECONDS`) through the environment for balancing and tests, falling back to the bundled model.

- **SR-252** *(Observability)*: Count items spawned / collected per kind, shots fired / hit, swings / enemies struck and kills per cause per player for each level; log them at `FINE` and show them in the debug overlay. `KillLedger` already holds the kill data.

- **SR-253** *(Gameplay, DDD)*: Tie the item cap to the difficulty profile (an attribute in `difficulties`) instead of the board width bands in `items.xmi`, so a custom large easy map gets the cap its difficulty intends.

- **SR-254** *(Gameplay)*: Let the laser aim with the mouse (both frontends already track the mouse position), and let a sledgehammer blow push the enemy back a little in the maze, not only visually.

- **SR-255** *(Parity, bug note)*: Found while building this: the libGDX flame-damage and wall-crush adapters used the enemy sprite's bottom-left corner instead of its centre, so a blast or a falling wall could miss an enemy that visually stood in it. Fixed (`GdxWeaponSupport.bodyOf`, `GdxWeaponSupportTest.crushUsesEnemyCentre`); any new libGDX targeting code should use `bodyOf`.

## Ratified Requirements

These requirements have been implemented and verified.

- SR-42: The Command and key binding registry input core is shared in `maze-common-frontend` and consumed by both frontends. The dispatch loop is generalized over a frontend-neutral key type, allowing new keys/actions to be added without modifying the router. libGDX has been migrated to consume the promoted types.
- SR-43: JavaFX gameplay input is handled via the shared key binding registry, resolving logical actions to command objects and replacing the inline `handleKeyPressed` switch. Key polling is centralized, and all actions are expressed as commands.
- SR-44: JavaFX gameplay mutable runtime state lives in a dedicated model (`FxGameWorldModel`), separate from lifecycle, rendering, input, and FXML concerns.
- SR-46: The JavaFX movement thread, animation timer, and watchdog are owned by a dedicated concurrency coordinator (`FxMovementLoopCoordinator`) with unchanged lifecycle semantics.
- SR-46: The JavaFX movement thread, animation timer, and watchdog are owned by a dedicated concurrency coordinator (`FxMovementLoopCoordinator`) with unchanged lifecycle semantics.
- SR-47: JavaFX mode-specific update logic is dispatched by the shared `GameModeRouter` to `FxPlayingModeController`, which owns input routing, route-hint penalty accrual, player movement throttling, and camera follow for the PLAYING mode.
- SR-48: JavaFX frame rendering is orchestrated by `FxGameRenderCoordinator`, which computes camera translation for windowed and fullscreen modes with correct player centre-follow and edge clamping.
- SR-49: JavaFX audio transitions are encapsulated behind a dedicated coordinator (`FxGameAudioCoordinator`) over `GameAudioDirector`.
- SR-45: JavaFX gameplay start and reset flow is encapsulated in `FxGameSessionBootstrapper`, which initializes board sizing, background, maze world, player configuration, player character, canvases (in correct z-order), and enemy spawning. The spawning step is injectable via `BiConsumer<EnemyRegistrar, Difficulty>` to decouple tests from the EMF/XMI model stack. `GameController.setupGame()` delegates to the bootstrapper and wires the resulting objects to actions, subscribers, and the mode router.

### Scoring penalty rules (GR-36, GR-37)

- **SR-75:** The end-screen score display (both GAME_OVER and WON overlays) shall show a dedicated penalty breakdown line informing the player of the exact damage penalty (damage × 10) and, when applicable, the death penalty (5 000 points) so players understand why their final score differs from the in-game HUD score.

- **SR-76:** The scoring constants (`ScoreDeathPenalty`, `ScoreSubtractFactor`, `ScoreWinBonus`, base scores) shall be surfaced in a difficulty/rules summary screen or tooltip so new players can understand the scoring model before starting a run.

- **SR-77:** A dedicated `ScoringRulesService` (or equivalent interface in `maze-common-backend`) shall expose the active penalty constants (death penalty, damage multiplier factor, win bonus) as readable properties so frontends and future UI components can display them without hard-coding `StageConstants` references. This follows the 12-Factor App principle of externalizing configuration and the DDD ubiquitous-language principle of naming domain concepts explicitly.

### F1 Animated Sprites — follow-on suggestions (from implementation, PR #78)

- **SR-108** *(DDD, 12-Factor, Observability — Ghost walk animation)*: Ghost character types currently have `animationFrameCount = 1` because directional walk frames (ghost2-left/right/up/down, ghost3-left/right/up/down) do not exist. Once those 8 sprites are created, `opponentModel.xmi` shall be updated to set `animationFrameCount="3"` for ghost entries and the `missing-sprite-asset-list.md` status flags updated. This is the minimum art-side prerequisite for activating ghost animation; no code change is required.

- **SR-109** *(Observability, 12-Factor)*: The walk animation frame rate (currently `ANIMATION_FPS = 4.0f` constant in `GdxEnemyRuntime` and 250 ms hard-coded in `ComputerCharacter`) shall be externalizable per character type. Consider adding a `walkAnimationFps` attribute to the `CharacterType` Ecore meta-model (default 4) and reading it in both `animationSpecFor` (libGDX) and `ComputerCharacter` (JavaFX) so level designers can tune walk speed without recompiling.

- **SR-110** *(DRY, CRR-5 parity)*: The JavaFX `ComputerCharacter` animation Timeline is owned inside the character object and runs indefinitely. When enemies are removed from the scene (death, teleport) the Timeline must be stopped to prevent orphaned CPU work. A `dispose()` method (or `IDisposable` interface) shall be added to `ComputerCharacter` and called by `FxEnemyCoordinator` on enemy removal and on `dispose()` of the coordinator itself.

- **SR-111** *(DDD, spriteScale full-chain)*: The libGDX `GdxGameRenderPipeline` applies `spriteScale` to the rendered size, but the collision / spawn-exclusion radius in `RuntimeVisualModelLoader` still uses the base `sizeForType` value. Once `spriteScale != 1.0` is in use for an enemy type, the effective collision footprint should respect the scale (or be documented as deliberately separate). Introduce a `effectiveSizeForCollision(EnemySpawn)` helper that applies the scale and uses it uniformly in spawn placement and wall-clearance checks.

- **SR-112** *(Sprite-sheet migration, performance)*: Individual PNG files per frame require one `Texture` load and cache entry per path. For character types with many frames (≥ 4) and multiple direction variants, consider packing sprites into a single atlas per character type using libGDX `TexturePacker` or JavaFX spritesheet utilities. This reduces file handles and improves cache locality. The `SpriteAnimationUtil.deriveAnimationFramePath` approach would need a companion atlas resolver for the packed format.

### Ranged projectile speed (F26)

- **SR-78:** The projectile speed physics contract (`distance = speed × time`) shall be validated via integration-style unit tests for every projectile type variant (STRAIGHT, LOB, BEAM) in both frontends to guard against accidental reintroduction of hardcoded flight durations.

- **SR-79:** `EnemySpawn.projectileSpeed()` shall enforce a minimum floor (e.g. 1 px/s) at the record level to prevent divide-by-zero and infinite flight duration without requiring defensive guards scattered across `GdxEnemyRuntime` and `PumpkinBomberCharacter`.

### F10/F20 Projectile telemetry — follow-on suggestions (PR #77)

- **SR-108** *(12-Factor, Config III)*: Enemy projectile log verbosity (currently `Logger.FINE`) shall be configurable via environment variable `MAZE_LOG_LEVEL` so QA can enable detailed projectile trace logs without recompiling. The `java.util.logging.Logger` hierarchy already supports this via `LogManager`; expose it via installer docs.

- **SR-109** *(Observability)*: `GdxEnemyRuntime.projectileStats()` returns a lightweight `H:N S:N` string. For production observability, consider publishing per-enemy stats as a named metric (e.g., `pumpkinbomber.hits` / `pumpkinbomber.shots`) via a `ProjectileTelemetrySink` interface. This decouples counter accumulation from string formatting and allows future exporters (Micrometer, OpenTelemetry) to consume the data without touching game logic.

- **SR-110** *(DDD)*: `lifetimeHits` and `lifetimeShots` are raw counters embedded in `GdxEnemyRuntime`. Introducing a `ProjectileStats` value object (immutable, `hits + shots + accuracy()`) would make the telemetry a first-class domain concept and allow transfer across the enemy lifecycle (respawn, serialization) without coupling to the mutable runtime state.

- **SR-111** *(Parity, CRR-5)*: `PumpkinBomberCharacter` (JavaFX) logs projectile events at `FINE` level but does not expose a `projectileStats()` accessor. For parity with libGDX, add a `ProjectileStats` record (hits/shots) to `PumpkinBomberCharacter` and expose it via `getProjectileStats()` so unit tests and HUD overlays can read it without parsing log output.

- **SR-112** *(12-Factor, DDD)*: The cascade collapse rule (GR-36) currently uses a hard-coded `ADJACENCY_EPSILON = 0.5 px` inside `GameMazeWorld`. For 12-Factor config compliance, consider moving this threshold to `StageConstants` (or a dedicated `WallConstants`) so it can be tuned without recompilation, and to make the adjacency rule an explicit domain concept rather than a private magic constant.

- **SR-113** *(Observability, DDD)*: Add a `WallCollapseEvent` domain event (destroyed wall geometry, cascade count) emitted by `GameMazeWorld.applyWallDamage` whenever a cascade occurs. Frontends can subscribe to log, animate, or display the count. This turns the cascade into a first-class observable side-effect rather than a silent internal mutation.

- **SR-114** *(Parity, CRR-5)*: The flame corridor half-width constant (`FLAME_CORRIDOR_HALF_WIDTH = 120f` in libGDX, `PLAYER_FLAME_HALF_WIDTH = 120` in JavaFX) is duplicated across two frontends. Consider promoting a single `StageConstants.FLAME_CORRIDOR_HALF_WIDTH_PX` so that tuning one value propagates to both frontends without risk of divergence.

- **SR-115** *(DDD, SRP)*: Cascade collapse logic lives inside `GameMazeWorld`, mixing world-state management with collapse propagation strategy. Introducing a `CascadeCollapsePolicy` interface (with `collectToCollapse(BreakableWall destroyed, List<BreakableWall> candidates)`) would allow alternate collapse strategies (no cascade, distance-based decay, time-delayed collapse) to be injected per game mode without modifying the world model.


- **SR-206** *(Parity, CRR-5)*: Mirror the libGDX wall-damage threading tests (`GdxWallDamageThreadSafetyTest`) for JavaFX: verify `GameController.applyProjectileDamageToWall` defers mutation via `Platform.runLater` when called off the FX thread, applies queued hits idempotently, and that `JavaFxUiScheduler` honours the same inline-vs-post contract as `GdxUiScheduler`. A shared, frontend-agnostic `IUiScheduler` contract test (abstract JUnit class in `maze-common-frontend` test utilities) would let both backends run the same assertions.

- **SR-207** *(DDD, Observability)*: Add a debug-mode assertion (or `FINE` log with thread name) in `GameMazeWorld.applyWallDamage` when invoked off the registered UI thread, so a future regression of the #132 threading contract is detectable at runtime instead of only via a nav-graph `ConcurrentModificationException`.

- **SR-208** *(12-Factor, Observability)*: Builds behind a TLS-intercepting proxy fail Tycho p2 resolution with `PKIX path building failed`. Document (and optionally honour in `make-*.ps1` / `make-*` via an environment variable) `MAVEN_OPTS=-Djavax.net.ssl.trustStoreType=Windows-ROOT` on Windows so the build uses the OS trust store without modifying the JDK.

### Pickup tuning follow-ons (SR-256..SR-259)

- **SR-260** *(Gameplay, MDD)*: Move the heal feedback timing (1 s, three pulses) and the arrow size into the items model next to the heart, so the effect can be tuned like the other item values.

- **SR-261** *(Observability)*: Log at `FINE` how many spawn points a level has after the wall clearance filter (`PickupField.spawnPointsOf`); a level with very few would spawn items in the same spots.

- **SR-262** *(UX)*: Let the respawn delay show on the remains (e.g. the coal pile starts to smoke a few seconds before a zombie comes back), so the player can tell when an enemy is about to return.

### Lives, weapons HUD and controls follow-ons (SR-263..SR-267)

- **SR-268** *(MDD, Xtext)*: Add `startingLives` to the difficulty block of the Xtext DSL so generated `difficulties.xmi` files carry it; today it is set in the shipped XMI and defaults to 1.

- **SR-269** *(Gameplay)*: Decide whether losing a life should cost score points (today only the HP damage penalty applies, and the death penalty only on game over) and whether pickups lying in the maze should be cleared on a respawn.

- **SR-270** *(UX)*: Make the player blink during the respawn protection (both frontends), so it is clear why enemies do not hurt them for two seconds; today the shield bubble shows it.

- **SR-271** *(12-Factor, UX)*: Let players rebind the movement and attack keys (a key map in the user's config, read at start), since D now moves down and some players expect WASD.

- **SR-272** *(Observability)*: Count lives lost and extra lives picked up per level, next to the kill statistics (SR-252).

- **SR-273** *(Portability, CRR-9; bug fixed)*: ~~The game could not be built or run outside the full reactor build after shared-module changes~~ — fixed: the `quick`/full targets of both make scripts now `install` (the local Maven repository used by single-module builds and the IDE was left stale by `verify`), and libGDX `prepare-run` rebuilds when any runtime module has newer sources (`Test-LaunchClassesStale`, also used for the shared modules in JavaFX `prepare-run`; Pester `Test-LaunchClassesStale` cases). Follow-up: run a `prepare-run` smoke step in CI so a stale-launch regression is caught automatically.
