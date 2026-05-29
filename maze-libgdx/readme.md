# maze-libgdx

libGDX backend for MazeGame: a self-contained LWJGL3 launcher plus the
libGDX implementations of the facades defined in
[maze-common-frontend](../maze-common-frontend/readme.md).

## Status

Phase 4 parity work (current): the launcher now opens a retro start menu,
lets the player choose difficulty from the shared EMF model, then builds a
matching arena size and starts gameplay with visible score and command HUD.
The runtime uses the shared `RealMaze` adapter for walls plus model-derived
player, goal, wall and opponent art, and animates opponents so the board is
not static.

## Gameplay subset

- Retro start menu in libGDX with difficulty selection (`UP`/`DOWN`, `ENTER`).
- Difficulty specific board dimensions aligned with JavaFX (`Easy`, `Normal`, `Hard`).
- Arrow keys and WASD movement tuned to JavaFX-equivalent speed behavior.
- Opponent sprites loaded from the model and animated continuously.
- Bottom command row and top-right score card styled to match JavaFX HUD overlays.
- Camera follows the player when the world is larger than the viewport.
- Win state shows completion text and allows returning to start menu (`ESC`).
- Menu music and menu selection sound are optional; if files are missing the game continues without audio failures.

## What lives here

| Class | Purpose |
| --- | --- |
| `GdxAppLauncher` | `main()` that boots `Lwjgl3Application` with `GdxGameScreen`. |
| `GdxGameScreen` | `ApplicationAdapter` that owns the camera, batch, shape renderer, font, and per-frame update + draw. |
| `game.MazeArena` | Pure-Java arena facade (walls, start, goal, pixel dimensions) implemented by both maze backends. |
| `game.SampleMaze` | Pure-Java maze generator (no libGDX deps); produces the immutable wall list. |
| `game.RealMaze` | Pure-Java `MazeArena` adapter over the shared `GameMazeWorld` (Phase 3, F11). |
| `game.WallSegment` | Pure-Java axis-aligned wall data class. |
| `game.PlayerState` | Pure-Java player position + axis-separated collision against walls. |
| `GdxUiScheduler` | Posts work via `Gdx.app.postRunnable`; falls back to inline execution before libGDX is initialised. |
| `GdxAudioEngine` | Loads sounds via `Gdx.files.internal`, caches `Sound` instances, enforces per-sound cooldown, no-ops gracefully when `Gdx.audio` / `Gdx.files` is null. |
| `GdxCharacterView` | Wraps a `com.badlogic.gdx.scenes.scene2d.Actor`. |
| `GdxBackend.install()` | Swaps the libGDX impls into `UiScheduler` and `AudioEngine`. |

The `game.*` classes deliberately avoid any `com.badlogic.gdx.*` import so
they remain unit-testable in headless CI without a GL context.

## Running

From VS Code: pick the `Launch MazeGame (libGDX backend)` configuration in
[launch.json](../.vscode/launch.json).

From the command line:

```powershell
pwsh ./make-libgdx.ps1 run
```

That target builds `maze-common-frontend` and `maze-libgdx` (copying the
runtime libs into `maze-libgdx/target/libs`), then launches:

```powershell
java -cp "maze-libgdx/target/classes${([IO.Path]::PathSeparator)}maze-libgdx/target/libs/*${([IO.Path]::PathSeparator)}maze-common-frontend/target/classes" main.game.maze.libgdx.GdxAppLauncher
```

The script applies `-XstartOnFirstThread` automatically on macOS only, and
does not add that flag on Windows or Linux.

## Building and testing

```powershell
pwsh ./make-libgdx.ps1 build         # full clean verify of both modules
pwsh ./make-libgdx.ps1 quick         # incremental test build
pwsh ./make-libgdx.ps1 quick-no-tests
```

Or directly via Maven:

```powershell
mvn -pl maze-common-frontend,maze-libgdx -am test
```

## Tests

- [GdxBackendTest](src/test/java/main/game/maze/libgdx/GdxBackendTest.java): adapters degrade gracefully without libGDX initialised.
- [SampleMazeTest](src/test/java/main/game/maze/libgdx/game/SampleMazeTest.java): deterministic generation, axis-aligned walls, immutability, input validation, full-grid connectivity.
- [RealMazeTest](src/test/java/main/game/maze/libgdx/game/RealMazeTest.java): `RealMaze` adapter exposes walls / start / goal derived from `GameMazeWorld`.
- [PlayerStateTest](src/test/java/main/game/maze/libgdx/game/PlayerStateTest.java): collision resolution, border clamping, goal-proximity detection.

## Coordinate system

libGDX uses a bottom-left origin (positive y is up). The current gameplay
subset is written natively for that convention; reconciliation with the
top-left-origin JavaFX game loop is deferred until the shared character
pipeline is ported behind the common-graphics facades.
