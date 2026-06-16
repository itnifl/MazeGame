# Suggested Requirements

## Candidate Additions



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

### 12-Factor App / Observability suggestions

- **SR-75** *(12-Factor, Config)*: Enemy composition profiles (profile name, enemy count, ratios, caps) should be externalized to an XMI or YAML file loaded at runtime so difficulty tuning does not require recompilation.
- **SR-76** *(Observability)*: `CompositionResolverImpl.resolve(...)` should emit a structured trace log (profile name → final composition map) at DEBUG level so difficulty tuning in QA is auditable without a debugger.
- **SR-77** *(Observability)*: `GdxGameCombatAndEnemyFlowSupport.triggerWin(...)` should emit a structured event (timestamp, player position, score) to an optional event sink so win-condition analytics can be collected without modifying game logic.

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
