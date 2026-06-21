# Suggested Requirements

## Candidate Additions

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

### BUG-1 / BUG-2 post-fix — DDD / 12-Factor / Observability suggestions

- **SR-87** *(DDD)*: `WallRegistry` and `WallMaterialBaseType` are generated artifacts tightly coupled to the wall sub-domain. Introduce a `WallDomainService` façade that hides the static registry behind an injectable, mockable interface. This removes static dependency coupling from consumers (`FxGameSessionBootstrapper`, `RuntimeVisualModelLoader`) and aligns with DDD's domain-service pattern.

- **SR-88** *(12-Factor, Config IV — Backing Services)*: Wall material image paths and `WallMaterialBaseType` constants are embedded in the EMF model and generated code. Externalising them to an env-overridable config file would let operators swap wall art sets without recompilation, aligning with 12-Factor principle IV.

- **SR-89** *(Observability)*: `FxGameSessionBootstrapper` and `RuntimeVisualModelLoader` catch `ExceptionInInitializerError` and log at SEVERE. A structured metric (e.g., a Micrometer counter `wall.registry.init.failures`) would let an ops dashboard detect misconfigured deployments before players notice broken rendering.

- **SR-90** *(Observability)*: `OpponentRuntimeFactory.spawnByTarget` now shuffles candidates on every slot. Add a DEBUG-level log entry emitting the final spawn list (type, threat, slot count) per session so difficulty balancers can audit the result without attaching a debugger.

### DDD boundary suggestions

- **SR-78** *(DDD)*: `PlayerConfig` and `CompositionResolverImpl` should live in a `config` bounded context with its own aggregate root (`DifficultyConfig`) that owns both the player config and enemy composition for a given difficulty level.
- **SR-79** *(DDD)*: The `characters` package in `maze-javafx-backend` has mixed concerns (rendering, game logic, audio). Consider splitting it into a `characters.domain` sub-package (state, damage, death) and `characters.view` sub-package (graphics, animations) aligned with DDD entity vs. value-object separation.

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
