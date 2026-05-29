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
- Start menu supports mouse selection for difficulty and start button.
- A "Loading ..." overlay is rendered on the start menu while the level is being built, and is cleared once gameplay begins.
- Difficulty-specific board dimensions aligned with JavaFX (`Easy`, `Normal`, `Hard`).
- OCL-derived difficulty parameters from the shared opponents / difficulties XMI model are applied at spawn time: monster population cap, max threat level, monster speed multiplier, monster damage multiplier, and instant-death (modeled via a threat above `CollisionDamage.INSTANT_KILL_THREAT_THRESHOLD`).
- Arrow keys and WASD movement tuned to JavaFX-equivalent speed behavior.
- Opponent sprites loaded from the model and animated continuously.
- Background image, wall type selection, goal sprite, menu icon, and menu/in-game sound paths now come from shared `MazeVisualStyleConfig` loaded from XMI first, with properties fallback, so JavaFX and libGDX follow the same style model.
- Bottom command row and top-right score card styled to match JavaFX HUD overlays.
- HP bar is visible at the top and the playable area is bounded below it and above the bottom row.
- The maze world is bottom-anchored to the bottom HUD bar: when the maze fits the gameplay viewport vertically, its bottom edge is pinned to the top of the bottom command row instead of floating.
- Camera follows the player when the world is larger than the viewport.
- Path hint display is hold-based like JavaFX (`P` held) and uses the shared navigation graph in `RealMaze`.
- Spanning tree hint uses the same navigation graph source and is rendered as an overlay in gameplay.
- High score list is available in libGDX (`H`) and reads the same `scores.txt` file used by JavaFX.
- Win state shows completion text and allows returning to start menu (`ESC`).
- `ESC` while in overlays (help, high score, controls) returns to gameplay first, then start menu only from gameplay state.
- Menu music and menu selection sound are optional; if files are missing the game continues without audio failures.
- Win and game-over music are routed through the shared `AudioEngine` singleton on dedicated channels (`WIN_MUSIC`, `GAME_OVER_MUSIC`) alongside `MENU_MUSIC` and `IN_GAME_MUSIC`, and are explicitly stopped when transitioning away from the corresponding screen (and on dispose), preventing music bleed-through between screens.

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
