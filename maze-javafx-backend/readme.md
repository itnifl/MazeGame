# maze-javafx-backend

JavaFX-specific game logic module. Contains all controllers, character implementations, actions, and
runtime factories that depend on the JavaFX UI toolkit.

## What lives here

| Package | Contents |
|---|---|
| `main.game.maze` | `App` (JavaFX Application entry point), `GameController`, all screen controllers |
| `main.game.maze.characters` | `Character`, `ComputerCharacter`, `GhostCharacter`, `ZombieCharacter`, `PumpkinBomberCharacter`, `PlayerCharacter`, `ProgressBarStatePresenter` |
| `main.game.maze.characters.interfaces` | `ICharacterAction` (uses `javafx.scene.Node`), `ICanSubscribeAndNotifyPosition` (uses `javafx.geometry.Bounds`) |
| `main.game.maze.actions` | `GameOverAction`, `HighscoreAction`, `MovementNotifierAction`, `RestartGameAction`, `WinGameAction` |
| `main.game.maze.actions.base` | `ActionScreens`, `CharacterActionScreens` |
| `main.game.maze.areas` | `WinArea` |
| `main.game.maze.runtime.opponents` | `OpponentRuntimeFactory` |
| `main.game.maze.service` | `CharacterIntersectionFixerService` |
| `main.game.maze.util` | `Dialogs` |

## Dependency chain

```
maze (launcher)
  └── maze-javafx-backend           ← this module
        ├── maze-javafx             (FxCharacterView, FXML resources, ScreenNameFXMLConstants)
        ├── maze-common-backend     (interfaces, DTOs, constants, DifficultyService, OclBootstrap)
        ├── maze-common-frontend    (AudioEngine, ICharacterView)
        ├── main.game.maze.mazeworld
        ├── main.game.maze.difficulties
        ├── main.game.maze.opponents
        ├── main.game.maze.behaviour
        └── maze-module-generator   (CharacterRegistrar, WallRegistry)
```

## Why a separate module?

Keeps game logic with JavaFX dependencies isolated from the launcher (`maze`), which becomes a
thin assembly module containing only `Launcher.java` and static resources (images, sounds, level
definitions). This makes it straightforward to test game logic without the launcher jar and to
reuse controllers in other JavaFX host applications.

## Tests

Tests for the classes in this module live in `maze/src/test/` because the `maze` assembly module
depends on this module transitively, giving the test sources access to all classes here without
moving the test files.
