# maze-libgdx

libGDX backend for MazeGame: a self-contained LWJGL3 launcher plus the
libGDX implementations of the facades defined in
[maze-common-graphics](../maze-common-graphics/readme.md).

## Status

Phase 2 (current): a runnable gameplay subset ships in this module. The
launcher opens a real LWJGL3 window and drops you straight into a
procedurally generated maze with arrow-key controls and a goal cell. The
deeper game pipeline (characters, opponents, behaviours, DSL-driven
configuration) still lives in Tycho-packaged Eclipse plugins; those
bundles will be repackaged as plain Maven jars before the libGDX module
consumes them. See [missing-feature.md](../missing-feature.md) for the
gap list.

## Gameplay subset

- 16 by 12 grid maze, 48 px cells, generated with a seeded depth-first
  recursive backtracker so each run is identical for reproducible bug reports.
- Player rendered as a sky-blue square, goal cell as a green square.
- Arrow keys or WASD to move; axis-separated collision resolution lets the
  player slide along walls.
- HUD line via libGDX `BitmapFont`; switches to a win message when the
  player overlaps the goal cell.

## What lives here

| Class | Purpose |
| --- | --- |
| `GdxAppLauncher` | `main()` that boots `Lwjgl3Application` with `GdxGameScreen`. |
| `GdxGameScreen` | `ApplicationAdapter` that owns the camera, batch, shape renderer, font, and per-frame update + draw. |
| `game.SampleMaze` | Pure-Java maze generator (no libGDX deps); produces the immutable wall list. |
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

That target builds `maze-common-graphics` and `maze-libgdx` (copying the
runtime libs into `maze-libgdx/target/libs`), then launches:

```powershell
java -cp "maze-libgdx/target/classes;maze-libgdx/target/libs/*;maze-common-graphics/target/classes" main.game.maze.libgdx.GdxAppLauncher
```

## Building and testing

```powershell
pwsh ./make-libgdx.ps1 build         # full clean verify of both modules
pwsh ./make-libgdx.ps1 quick         # incremental test build
pwsh ./make-libgdx.ps1 quick-no-tests
```

Or directly via Maven:

```powershell
mvn -pl maze-common-graphics,maze-libgdx -am test
```

## Tests

- [GdxBackendTest](src/test/java/main/game/maze/libgdx/GdxBackendTest.java): adapters degrade gracefully without libGDX initialised.
- [SampleMazeTest](src/test/java/main/game/maze/libgdx/game/SampleMazeTest.java): deterministic generation, axis-aligned walls, immutability, input validation.
- [PlayerStateTest](src/test/java/main/game/maze/libgdx/game/PlayerStateTest.java): collision resolution, border clamping, goal-proximity detection.

## Coordinate system

libGDX uses a bottom-left origin (positive y is up). The current gameplay
subset is written natively for that convention; reconciliation with the
top-left-origin JavaFX game loop is deferred until the shared character
pipeline is ported behind the common-graphics facades.
