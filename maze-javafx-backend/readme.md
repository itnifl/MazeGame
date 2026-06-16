# maze-javafx-backend

JavaFX game runtime module. This module is now the top level runtime module for the JavaFX client, including the application entry point, launcher shim, controllers, actions, and character logic.

## What lives here

| Package | Contents |
|---|---|
| `main.game.maze` | `App` (JavaFX `Application` entry point), `Launcher`, `GameController`, `FxGameWorldModel`, `FxMovementLoopCoordinator`, `FxEnemyCoordinator`, screen controllers |
| `main.game.maze.characters` | `Character`, `ComputerCharacter`, `GhostCharacter`, `ZombieCharacter`, `PumpkinBomberCharacter`, `PlayerCharacter`, `FxPositionBounds`, `ProgressBarStatePresenter` |
| `main.game.maze.characters.interfaces` | `PositionBounds` (JavaFX-backend neutral bounds); `ICharacterAction` and `ICanSubscribeAndNotifyPosition` promoted to `maze-common-backend` |
| `main.game.maze.actions` | `GameOverAction`, `HighscoreAction`, `MovementNotifierAction`, `RestartGameAction`, `WinGameAction` |
| `main.game.maze.actions.base` | `ActionScreens`, `CharacterActionScreens` |
| `main.game.maze.areas` | `WinArea` |
| `main.game.maze.javafx.audio` | `FxGameAudioCoordinator` |
| `main.game.maze.javafx.controller.state` | `FxPlayingModeController` |
| `main.game.maze.javafx.hud` | `FxHudCoordinator` — transient HUD message display with auto-clear timer |
| `main.game.maze.javafx.lifecycle` | `FxGameSessionBootstrapper` — arena build, player construction, canvas z-ordering, enemy spawning (injectable) |
| `main.game.maze.javafx.menu` | `FxDifficultyPickerSupport` — difficulty dialog + restart confirmation flow |
| `main.game.maze.javafx.render` | `FxGameRenderCoordinator`, `FxMazeCanvasRenderer`, `FxPathHintCoordinator` |
| `main.game.maze.runtime.opponents` | `OpponentRuntimeFactory`, `EnemyRegistrar` (ISP interface decoupling factory from controller) |
| `main.game.maze.service` | `CharacterIntersectionFixerService` |
| `main.game.maze.util` | `Dialogs` |

## Dependency chain

```text
maze-javafx-backend
  ├── maze-javafx             (FxCharacterView, JavaFX backend adapters, FXML resources)
  ├── maze-common-backend     (interfaces, DTOs, constants, DifficultyService, OclBootstrap)
  ├── maze-common-frontend    (AudioEngine, ICharacterView and shared frontend services)
  ├── main.game.maze.mazeworld
  ├── main.game.maze.difficulties
  ├── main.game.maze.opponents
  ├── main.game.maze.behaviour
  └── maze-module-generator   (CharacterRegistrar, WallRegistry)
```

## Runtime notes

### GameController refactor progress

The JavaFX `GameController` has been refactored to an MVC (Model-View-Controller) plus Command architecture, mirroring the libGDX frontend for structural parity. The original 1800-line god class has been decomposed into a slim coordinator that delegates responsibilities to dedicated classes.

| Responsibility | Owner | Notes |
|---|---|---|
| **Lifecycle & FXML** | `GameController` (~488 lines) | The controller is the FXML entry point; wires all coordinators and delegates every domain concern. Original god class was 1872 lines. |
| **Session Bootstrap** | `FxGameSessionBootstrapper` | Encapsulates arena build, player construction, canvas z-ordering, and enemy spawning. Spawning is injectable for testability. |
| **Gameplay State** | `FxGameWorldModel` | Pure data model holding scoring, path-hint state, and enemy overlay flags. |
| **Input Handling** | `InputRouter<KeyCode>` | Shared component from `maze-common-frontend`; maps key presses to `GameCommand` objects via `KeyBindingRegistry`. |
| **Concurrency** | `FxMovementLoopCoordinator` | Owns the background `Task` for enemy AI and the `AnimationTimer` for player movement. |
| **Enemy AI & Visuals** | `FxEnemyCoordinator` | Enemy movement AI, infection mist/warning, debug-label overlays, and enemy path canvas drawing. |
| **Path Hint** | `FxPathHintCoordinator` | Path-hint energy budget, countdown label, budget exhaustion message, navigation path drawing, and input flow (`showNavigationPath` / `clearNavigationPath` / `refreshPathCanvas`). |
| **HUD Messages** | `FxHudCoordinator` | Transient status messages with optional auto-clear duration. |
| **Difficulty Picker** | `FxDifficultyPickerSupport` | ESC-key difficulty dialog + restart confirmation flow. Extracted from controller. |
| **Maze Canvas** | `FxMazeCanvasRenderer` | Static maze wall canvas rendering from `List<Vector2D>` with image caching. |
| **Camera & Spanning Tree** | `FxGameRenderCoordinator` | Camera translation (windowed / fullscreen), spanning-tree canvas drawing. |
| **Audio** | `FxGameAudioCoordinator` | Audio transitions for menu, in-game, win, and game-over states. |
| **Game Logic** | `FxPlayingModeController` | PLAYING-mode update loop: input routing, route-hint penalty, player movement throttling. |
| **Mode Switching** | `GameModeRouter` | Shared deterministic mode state machine; dispatches to the active mode controller. |
| **Enemy Spawn Interface** | `EnemyRegistrar` | ISP boundary — `OpponentRuntimeFactory` depends on this interface, not on `GameController` directly. |

This new structure improves testability, isolates responsibilities, and ensures that both the JavaFX and libGDX frontends are built on the same architectural foundation, satisfying **CRR-5**.

Current extraction steps preserve behavior by routing side effects back into `GameController` through small sink interfaces. This keeps gameplay and threading behavior stable while controller ownership is reduced incrementally.

### Start menu and viewport

The JavaFX client starts on `startScreen.fxml`, where the player selects difficulty before loading the game scene.

Current runtime behavior:

- difficulty is selected from the shared `DifficultyService` model list
- selected difficulty sets board size before game setup
- when the chosen board is larger than the display, the stage enters fullscreen
- the maze viewport follows the player while HUD elements remain fixed
- command menu and score panel stay visible while the board scrolls

### Enemy damage and wall blocking

Enemies and player exchange position notifications in both directions.

- enemy movement path: enemy notifies player, `PlayerCharacter.doPositionEvaluation` runs, and `GameController.isWallBetween(...)` prevents through wall damage
- player movement path: player notifies enemies, each enemy class runs its own position evaluation and the same wall check before damage

`GameController.isWallBetween` delegates to `WallCollisionUtil.wallBetweenVectors` in the MazeWorld module.

### Path hint budget

Holding `P` reveals the shortest path to the goal with a score penalty and a finite budget.

| Difficulty | Budget |
|---|---|
| Easy | 45 s |
| Normal | 25 s |
| Hard | 15 s |

Penalty rate is 50 points per second while active.

### Shutdown behavior

When the window is closed, the game disposes active controller resources, disposes audio channels, exits JavaFX, and then terminates the JVM process.

### Wall rendering constants

Wall thickness and segment length come from `StageConstants` in `main.game.maze.mazeworld`.

- `StageConstants.WallThicknessPx`
- `StageConstants.WallSegmentLengthPx`

Both JavaFX and libGDX frontends read the same values to preserve visual parity.

### Ghost tangibility rules

Ghosts phase while `nonTangibilityEnergy > 0`.

- while phasing: can pass through walls, rendered semi transparent, cannot damage player
- after energy reaches zero: becomes solid, blocked by walls, can damage on contact

Both frontends use shared services in `maze-common-frontend` to keep behavior aligned.

## Running

Use the VS Code launch configuration `Launch MazeGame (JavaFX)` in [.vscode/launch.json](../.vscode/launch.json).

For command line build and test:

```powershell
mvn -pl maze-javafx-backend -am clean verify
```

## Tests

This module owns its own tests under `maze-javafx-backend/src/test`.

### Action-layer tests

- `GameOverActionTest` — verifies `startGameOverMusic()` uses the correct channel and resource, and that repeated calls each produce an audio invocation.
- `WinGameActionTest` — verifies construction succeeds and that `WinGame()` stops `IN_GAME_MUSIC` before the null-scene early-return guard.
- `StartScreenActionTest` — verifies `Load()` without a scene produces zero stop calls, and null-root handling does not throw.

### Coordinator tests (require `Platform.startup()`)

- `FxHudCoordinatorTest` — 6 tests for `setMessage`, empty-string clear, null-supplier guard, `dispose`, duration set, and message replacement.
- `FxPathHintCoordinatorTest` — 5 tests for `refreshPathCanvas`, enemy-drawer invocation, global-alpha reset, null-canvas guard, and `dispose`.
- `FxDifficultyPickerSupportTest` — 4 tests for the extracted `resolvePickResult(...)` routing logic without real JavaFX dialogs.

### Character tests

- `ZombieCharacterTest` — construction, `getDamage`, partial HP subtraction, audio-on-overlap, death-subscriber management, and `getModel`.
- `PumpkinBomberCharacterTest` — construction, `getDamage`, partial HP subtraction, `setHitPoints`/`addHitPoints`, `doPositionEvaluation` no-throw, and `getModel`.

### Test doubles (`src/test/java/main/game/maze`)

- `SpyActionSink` — records all `ActionSink` method calls for verifying command dispatch without real side-effects. Must stay in package `main.game.maze` to access the package-private `ActionSink` interface.
