# maze-libgdx

libGDX backend for MazeGame: a self-contained LWJGL3 launcher plus the
libGDX implementations of the facades defined in
[maze-common-frontend](../maze-common-frontend/readme.md).

## Status

Phase 4 parity work (completed): the launcher now opens a retro start menu,
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
- Path hint display is hold-based like JavaFX (`P` held) and uses the shared navigation graph in `RealMaze`. The path hint has a per-difficulty budget that mirrors the JavaFX implementation (Easy 45 s, Normal 25 s, Hard 15 s). The remaining budget is shown in the HUD command row as `P Path [ON] [Xs left]` (or `[SPENT]` once exhausted). The penalty rate is 50 points/second. When the budget runs out the hint is hidden automatically and a status message is shown. See [maze-javafx-backend readme](../maze-javafx-backend/readme.md) for the equivalent JavaFX implementation.
- Spanning tree hint uses the same navigation graph source and is rendered as an overlay in gameplay.
- High score list is available in libGDX (`H`) and reads the same `scores.txt` file used by JavaFX.
- Start menu can open high scores directly without starting a game session (`GdxGame.routeToHighScores()` via `LegacyPlayScreenController.forHighScores(...)`).
- High score overlay rendering now has two explicit paths: it renders on a cleared background when no world is active (opened from start menu), and renders on top of the active world plus HUD when opened during gameplay.
- Win state shows completion text and allows returning to start menu (`ESC`).
- `ESC` while in overlays (help, high score, controls) returns to gameplay first, then start menu only from gameplay state.
- Menu music and menu selection sound are optional; if files are missing the game continues without audio failures.
- Win and game-over music are routed through the shared `AudioEngine` singleton on dedicated channels (`WIN_MUSIC`, `GAME_OVER_MUSIC`) alongside `MENU_MUSIC` and `IN_GAME_MUSIC`, and are explicitly stopped when transitioning away from the corresponding screen (and on dispose), preventing music bleed-through between screens.

## What lives here

| Class | Purpose |
| --- | --- |
| `GdxAppLauncher` | `main()` that boots `Lwjgl3Application` with `GdxGame` as screen router root. |
| `GdxGame` | `Game` root that owns shared runtime context and routes between menu, play, and compatibility screens. |
| `GdxGameScreenController` | Legacy gameplay `ApplicationAdapter` still used through a compatibility screen while extraction continues. |
| `DifficultyBoardConfig` | Dedicated board dimension and board-size label policy per selected difficulty. |
| `DifficultyPresentationSupport` (shared backend) | Shared difficulty display-name and board dimension policy used by libGDX and JavaFX. |
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

## Current refactor architecture

The libGDX gameplay path now includes dedicated boundaries that reduce direct
controller responsibilities while preserving current behavior:

- Input command routing:
	- `input.InputSnapshotReader` captures per-frame keyboard and mouse state (libGDX-specific physical layer).
	- The logical input core is shared in `maze-common-frontend` under `main.game.maze.common.input` and consumed here parameterized on `Integer`:
		- `common.input.KeyBindingRegistry<Integer>` maps logical gameplay actions to key bindings.
		- `common.input.InputRouter<Integer>` resolves triggered actions and invokes command objects.
		- `common.input.InputFrame<Integer>` and `common.input.EdgeKeyTracker<Integer>` provide the immutable per-frame snapshot and rising-edge latch.
	- `input.command.*` contains focused libGDX command implementations (implementing `common.input.command.GameCommand<Integer>`) for menu return,
		terminal toggle, high score opening, spanning tree toggle, path hint, and movement.
- Mode update routing:
	- `controller.state.GameModeRouter` dispatches mode handlers in deterministic order.
	- `GdxGameScreenController` now delegates mode checks through this router.
- Render orchestration:
	- `render.GdxGameRenderPipeline` orchestrates world, HUD, and overlay rendering
		through existing view classes by consuming an immutable `RenderState` snapshot.
	- The high-score branch in `render.GdxGameRenderPipeline` handles both start-menu and in-game overlay contexts to avoid blank-screen regressions and to preserve semi-transparent overlay composition.
	- `render.GdxGameRenderCoordinator` now owns the large render-snapshot mapping step,
		keeping `GdxGameScreenController` focused on lifecycle flow instead of render-state assembly.
	- `helper.GdxGameInteractionSupport.executeTerminalCommand(...)` now applies terminal outcomes and forwards status text through one unified status-message call path.

This keeps behavior parity intact while making future extraction of gameplay state
and bootstrap flows lower risk.

## Programming patterns

The libGDX module applies several established design patterns to keep the
gameplay path modular, testable, and aligned with the MVC and SOLID goals of
the wider refactor:

| Pattern | Where it is used | Purpose |
| --- | --- | --- |
| Model-View-Controller (MVC) | `model.GameWorldModel` (state), `view.Gdx*View` (rendering), `controller.*` (lifecycle/wiring) | Separates mutable gameplay state, drawing, and control flow so each layer can change independently. |
| Command | `input.command.GameCommand` + implementations (`MovePlayerCommand`, `ReturnToMenuCommand`, `ToggleTerminalCommand`, `OpenHighScoresCommand`, `ToggleSpanningTreeCommand`, `ApplyPathHintCommand`) implementing shared `common.input.command.GameCommand<Integer>` | Each logical input action is an isolated, headlessly testable object; new actions are added without editing a central dispatch switch (open/closed). |
| Registry / Strategy | shared `common.input.KeyBindingRegistry<Integer>`, `common.input.InputRouter<Integer>`, `helper.GdxGameInputBindingsSupport` | Maps logical actions to key bindings, registers their commands, and builds the platform keyboard `InputProcessor`, removing scattered `Gdx.input` polling and inline input wiring from the controller. |
| Factory | `helper.GdxGamePlayingBridgeFactory`, `helper.GdxGameStartFlowRequestFactory`, `GdxGameScreenController.forHighScores(...)`, `controller.LegacyPlayScreenController.forHighScores(...)` | Centralizes construction/wiring of collaborators and start-flow requests instead of inlining it in the controller. |
| Builder / parameter object (immutable) | `controller.GdxGameScreenOptions` + its `Builder` | Replaces the previous `GdxGameScreenController` telescoping-constructor chain (SR-41); self-documenting builder calls supply arena, runtime config, asset-service ownership, start/overlay flags, return-to-menu action, and forced difficulty through one canonical constructor. |
| Pure helper / metrics value object | `controller.GdxGameScreenMetrics` (+ its `GameStripBounds` record), `render.GdxGameRenderConstants` | Hold the screen's stateless layout geometry, HUD bar sizing, death-display timing, JavaFX speed-parity factor, and render tuning values outside the controller (SRP), so they are unit-testable in isolation and the controller stays thinner. |
| Composition root | `controller.GdxGameScreenAssembler` (+ its `GdxGameCollaborators` record) | Owns the collaborator object-graph assembly that used to live inline in the controller constructor; builds the overlay / input / mouse / playing-bridge / start-flow / render collaborators and returns them as one value object, keeping the constructor down to plain field assignments (SRP). |
| Coordinator / Mediator | `render.GdxGameRenderCoordinator`, `controller.state.GdxOverlayModeCoordinator`, `helper.GdxGameMouseInteractionCoordinator`, `audio.GdxGameAudioCoordinator` | Each coordinator owns one cross-cutting concern (render assembly, overlay routing, mouse wiring, audio transitions) so the screen controller stays thin. |
| Adapter | `adapter.AbstractLegacyAdapterScreen`, `adapter.ApplicationAdapterScreen`, `game.RealMaze`, `movement.GdxWorldView`, `GdxBackend` (installs libGDX impls behind shared facades) | Wraps legacy/external types behind the interfaces the rest of the code expects, enabling gradual migration and parity. |
| State | `controller.state.GameModeRouter`, `controller.state.PlayingModeController`, `GameMode` | Per-mode update handlers are dispatched in deterministic order based on the current `GameMode`. |
| Snapshot / Value object (immutable) | `render.RenderState`, shared `common.input.InputFrame<Integer>` | Per-frame inputs and render data are passed as immutable snapshots, avoiding shared-mutable-state bugs across the loop. |
| Facade | `service.GdxAssetService`, shared `common-graphics` facades (`UiScheduler`, `AudioEngine`) | Hides libGDX resource/loading details behind a small surface so screens never call `new Texture(...)` directly. |
| Singleton | `AudioEngine.get()` (shared channel routing) | One shared audio engine instance routes menu/in-game/win/game-over channels consistently across screens. |

## Enemy damage and wall blocking

Combat is handled by `PlayerCombatStateService`. Each frame it checks whether any tangible enemy is within contact range of the player. Before applying damage it calls `WallCollisionUtil.wallBetween` (from the [mazeworld module](../main.game.maze.mazeworld/readme.md)) to test whether any wall separates the enemy centre from the player centre. If a wall blocks the line of sight, damage is not applied for that enemy this frame.

A phasing ghost (non-tangibility energy > 0) bypasses the wall-blocking check in `PlayerCombatStateService`, so it can pass through walls during movement AND still deal contact damage to the player when bounding boxes overlap. Only the wall-collision guard is skipped; damage is not suppressed.

`combatState.setMaze(maze)` must be called at game start (inside `startGameFromSelection`) to provide the current `MazeArena`. The equivalent wall-blocking logic in the JavaFX backend is documented in the [maze-javafx-backend readme](../maze-javafx-backend/readme.md).

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
- [GhostTangibilityParityTest](src/test/java/main/game/maze/libgdx/GhostTangibilityParityTest.java): F25 visibility level — `EnemySpawn` carries `visibilityLevel`, default = 100, 12-arg constructor defaults, `GdxEnemyRuntime.renderOpacity()` honours `visibilityLevel` cap, full-phasing floor = 0.1, cross-frontend opacity parity with `GhostNonTangibilityService`.

## Coordinate system

libGDX uses a bottom-left origin (positive y is up). The current gameplay
subset is written natively for that convention; reconciliation with the
top-left-origin JavaFX game loop is deferred until the shared character
pipeline is ported behind the common-graphics facades.

---

## Ghost tangibility (phasing system)

Ghost enemies spawn in a *phasing* state and gradually materialise over time.
The libGDX implementation mirrors the JavaFX behaviour exactly:

| Aspect | Rule |
|--------|------|
| Initial energy | Read from the ghost's `nonTangibilityEnergy` field in the XMI opponent model (default 100). |
| Drain rate | Delegated to `GhostNonTangibilityService.drainEnergy(...)` (`0.14 × (1000 / 60)` energy per second, shared constant). |
| Phasing | While energy > 0 the ghost moves through walls using `GhostPhasingMovementService.tick(...)`, which returns `false` for all `wouldCollide` queries. |
| Opacity | Delegated to `GhostNonTangibilityService.calculateOpacity(double energy, int visibilityLevel)` (F25): opacity ceiling is `visibilityLevel / 100.0`; while phasing the value is `clamp(1.0 - energy/MAX_ENERGY + 0.1, 0.1, baseOpacity)`; when energy = 0 opacity equals exactly `baseOpacity`. |
| Visibility cap | `EnemySpawn.visibilityLevel` (0–100, default `EnemySpawn.DEFAULT_VISIBILITY_LEVEL = 100`) carries the configured visibility from the XMI model. `RuntimeVisualModelLoader.visibilityLevelFor(CharacterType)` reads `Ghost.visibilityLevel` and falls back to `DEFAULT_VISIBILITY_LEVEL` for non-ghost enemies. |
| Damage | A phasing ghost bypasses the wall-blocking check in `PlayerCombatStateService` but DOES deal contact damage when bounding boxes overlap. Wall bypass and damage suppression are independent. |

The `EnemySpawn` record carries both `nonTangibilityEnergy` and `visibilityLevel`
(F25). `GdxEnemyRuntime` holds both as final fields; `renderOpacity()` delegates to
the two-arg `GhostNonTangibilityService.calculateOpacity(energy, visibilityLevel)`.
`RuntimeVisualModelLoader` reads both values from the `Ghost` EMF model and wires
them into the canonical 13-arg `EnemySpawn` constructor.

---

## Wall thickness parity

Wall render thickness is read from `StageConstants.WallThicknessPx` (5 px,
shared with the JavaFX renderer). Previously, libGDX used a hard-coded `3f`
which produced thinner walls than JavaFX. Both frontends now draw walls at the
same thickness.
