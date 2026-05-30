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
  `AdaptiveAggressiveMovementService`, `AntiLoopWanderMovementService`, and
  `EnemySpawnUnstuckService`.
  Aggressive enemies switch to shortest-path follow mode after 4 seconds of
  blocked movement and keep it for up to 20 seconds before returning to
  directional chase. Wander and patrol use anti-loop scoring to avoid short
  repeating circles when alternate cardinal moves exist.

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

Production code (for example [App.java](../maze/src/main/java/main/game/maze/App.java))
calls the appropriate `install()` before any gameplay code touches the
singletons.

## Tests

- [UiSchedulerTest](src/test/java/main/game/maze/common/graphics/UiSchedulerTest.java)
- [AudioEngineTest](src/test/java/main/game/maze/common/graphics/AudioEngineTest.java)
- [PropertiesMazeVisualStyleLoaderTest](src/test/java/main/game/maze/common/graphics/config/PropertiesMazeVisualStyleLoaderTest.java)
- [XmiMazeVisualStyleLoaderTest](src/test/java/main/game/maze/common/graphics/config/XmiMazeVisualStyleLoaderTest.java)
