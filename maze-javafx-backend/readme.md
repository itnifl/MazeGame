# maze-javafx-backend

JavaFX game runtime module. This module is now the top level runtime module for the JavaFX client, including the application entry point, launcher shim, controllers, actions, and character logic.

## What lives here

| Package | Contents |
|---|---|
| `main.game.maze` | `App` (JavaFX `Application` entry point), `Launcher`, `GameController`, screen controllers |
| `main.game.maze.characters` | `Character`, `ComputerCharacter`, `GhostCharacter`, `ZombieCharacter`, `PumpkinBomberCharacter`, `PlayerCharacter`, `ProgressBarStatePresenter` |
| `main.game.maze.characters.interfaces` | `ICharacterAction`, `ICanSubscribeAndNotifyPosition` |
| `main.game.maze.actions` | `GameOverAction`, `HighscoreAction`, `MovementNotifierAction`, `RestartGameAction`, `WinGameAction` |
| `main.game.maze.actions.base` | `ActionScreens`, `CharacterActionScreens` |
| `main.game.maze.areas` | `WinArea` |
| `main.game.maze.runtime.opponents` | `OpponentRuntimeFactory` |
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
| **Lifecycle & FXML** | `GameController` | The controller remains the FXML entry point, but only wires dependencies and handles lifecycle events. |
| **Gameplay State** | `FxGameWorldModel` | A pure data model holding scoring, path-hint state, and other gameplay variables. |
| **Input Handling** | `InputRouter<KeyCode>` | A shared component from `maze-common-frontend` that maps key presses to `GameCommand` objects via a `KeyBindingRegistry`. |
| **Concurrency** | `FxMovementLoopCoordinator` | Manages the background `Task` for enemy AI and the `AnimationTimer` for player movement, isolating all threading logic. |
| **Rendering** | `FxGameRenderCoordinator` | Handles camera logic, viewport translation, and canvas drawing orchestration. |
| **Audio** | `FxGameAudioCoordinator` | Manages audio transitions for different game modes (menu, in-game, win, game over). |
| **Game Logic** | `FxPlayingModeController` | Contains the core game loop logic for when the player is actively playing, such as applying penalties and moving the player. |
| **Mode Switching** | `GameModeRouter` | A shared component that will manage transitions between different game states (e.g., playing, game over, high scores). |

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
