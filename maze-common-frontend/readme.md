# maze-common-frontend

Backend-agnostic facades for UI threading, audio playback, and character views.
This module is the seam that lets the game code stay independent of whether the
runtime renderer is JavaFX, libGDX, or something else.

## What lives here

- `IUiScheduler` and `UiScheduler` (singleton holder): post work onto the UI
  thread, query whether the current thread is the UI thread.
- `IAudioEngine` and `AudioEngine` (singleton holder): play sound resources by
  classpath path, with optional per-sound cooldown.
- `ICharacterView`: a renderer-neutral view facade exposing position, scale,
  opacity, visibility, view order, effect clearing, and parent detachment.
- `MazeVisualStyleConfig` plus `XmiMazeVisualStyleLoader` and
  `PropertiesMazeVisualStyleLoader`: shared model driven style and
  asset mapping for both backends, including
  difficulty background images, wall type ids, menu icon, and common
  menu or gameplay audio resource paths. `XmiMazeVisualStyleLoader` configures
  its `DocumentBuilderFactory` with OWASP XXE hardening (secure-processing,
  disallow-doctype-decl, external general/parameter entities and external DTD
  loading disabled, XInclude off, entity expansion off) so loading from
  arbitrary file paths is safe.
- Shared enemy movement helpers:
  `AdaptiveAggressiveMovementService`, `AntiLoopWanderMovementService`,
  `EnemySpawnUnstuckService`, `GhostNonTangibilityService`, and
  `GhostPhasingMovementService`.
  Aggressive enemies switch to shortest-path follow mode after 4 seconds of
  blocked movement and keep it for up to 20 seconds before returning to
  directional chase. Wander and patrol use anti-loop scoring to avoid short
  repeating circles when alternate cardinal moves exist.
  Ghost phasing (non-tangibility) is governed by two shared services:
  - `GhostNonTangibilityService`: stateless utility that determines whether a
    ghost is phasing (`energy > 0`), computes the per-tick energy drain, and
    calculates the semi-transparent rendering opacity. Both frontends MUST use
    this service exclusively.
    - Single-arg overload `calculateOpacity(double energy)` — legacy path; assumes
      `visibilityLevel = 100` (fully visible cap).
    - Two-arg overload `calculateOpacity(double energy, int visibilityLevel)` (F25) —
      clips the opacity ceiling to `visibilityLevel / 100.0` so a ghost configured
      with e.g. `visibilityLevel = 50` never renders above 50 % opacity even when
      fully materialised. Input is clamped to `[0, 100]`. When energy = 0 the
      returned opacity equals exactly `visibilityLevel / 100.0` (the solid-state
      floor). The gameplay floor of 0.1 applies only while the ghost is phasing.
  - `GhostPhasingMovementService`: stateful per-ghost wall-ignoring movement.
    A phasing ghost picks a random cardinal direction and bounces at board
    boundaries, bypassing all wall collision checks. Call `reset()` when a new
    game session starts so per-enemy direction state does not leak across
    sessions. Both frontends MUST delegate phasing movement to this service.
- Shared live path snapshot support for enemy overlays:
  `ActivePathPoint`,
  `AdaptiveAggressiveMovementService.currentPathForEnemy(...)`, and
  `PatrolMovementService.currentPathForEnemy(...)`.
  Frontends use these APIs for `/showenemypath` and `/sep` so overlays show
  the active runtime path being followed, rather than a newly computed path.

- Shared generic input core (`main.game.maze.common.input` and
  `main.game.maze.common.input.command`), backend-neutral and parameterized on the
  physical key type `K` (libGDX uses `Integer`, JavaFX uses `javafx.scene.input.KeyCode`):
  - `GameAction`: logical action enum (return to menu, toggle terminal, open high scores,
    toggle spanning tree, apply path hint, move player).
  - `InputFrame<K>`: immutable per-frame snapshot (held keys, edge keys, mouse x/y as
    `double`, left-click) with `isHeld(K)` / `isEdge(K)`.
  - `EdgeKeyTracker<K>`: rising-edge latch for poll-based frontends.
  - `KeyBindingRegistry<K>` (with `BindingKind`, `KeyBinding<K>`): maps actions to keys and
    commands; `InputRouter<K>` resolves triggered actions and runs their commands.
  - `command.GameCommand<K>` and `command.GameCommandContext`: the command contract and a
    neutral side-effect facade. Each frontend supplies its own physical snapshot reader and
    command-context adapter.


## Default behaviour with no backend installed

The defaults are deliberately inert so tests and library consumers do not
require a windowing system:

- `UiScheduler.get()` returns a `SynchronousUiScheduler` (runs work inline,
  reports itself as the UI thread).
- `AudioEngine.get()` returns a `NoopAudioEngine` (records the resources it
  was asked to play, never touches the audio device).

`reset()` on either singleton restores the inert default. `AudioEngine.set(...)`
disposes the previous engine before installing a new one.

## Installing a real backend

Backend modules expose an `install()` method that swaps the singletons:

- `main.game.maze.javafx.JavaFxBackend.install()` (see [maze-javafx](../maze-javafx/readme.md))
- `main.game.maze.libgdx.GdxBackend.install()` (see [maze-libgdx](../maze-libgdx/readme.md))

Production code (for example [App.java](../maze-javafx-backend/src/main/java/main/game/maze/App.java))
calls the appropriate `install()` before any gameplay code touches the
singletons.

## Tests

- [UiSchedulerTest](src/test/java/main/game/maze/common/graphics/UiSchedulerTest.java)
- [AudioEngineTest](src/test/java/main/game/maze/common/graphics/AudioEngineTest.java)
- [PropertiesMazeVisualStyleLoaderTest](src/test/java/main/game/maze/common/graphics/config/PropertiesMazeVisualStyleLoaderTest.java)
- [XmiMazeVisualStyleLoaderTest](src/test/java/main/game/maze/common/graphics/config/XmiMazeVisualStyleLoaderTest.java)
- [EdgeKeyTrackerTest](src/test/java/main/game/maze/common/input/EdgeKeyTrackerTest.java)
- [KeyBindingRegistryTest](src/test/java/main/game/maze/common/input/KeyBindingRegistryTest.java)
- [InputRouterTest](src/test/java/main/game/maze/common/input/InputRouterTest.java)
- [GhostNonTangibilityServiceTest](src/test/java/main/game/maze/common/movement/GhostNonTangibilityServiceTest.java): single-arg backward-compat, two-arg overload (F25) — solid at full/partial visibility, phasing capped at `baseOpacity`, floor=0.1 while phasing, zero-visibility floor, clamp for negative and >100 input.
