# maze-libgdx

libGDX implementations of the facades defined in
[maze-common-graphics](../maze-common-graphics/readme.md).

> **Status: work in progress.** The interface adapters (`GdxUiScheduler`,
> `GdxAudioEngine`, `GdxCharacterView`) compile and are unit tested, and
> `GdxAppLauncher` opens a real LWJGL3 window. The gameplay loop itself is
> still JavaFX-only; the libGDX launcher currently renders placeholder text
> while the rest of the game (controllers, walls, mazeworld, scene graph) is
> migrated to depend on the common-graphics interfaces instead of JavaFX
> directly.

## What lives here

- `GdxUiScheduler`: posts work via `Gdx.app.postRunnable`, falling back to
  inline execution when libGDX is not yet initialised. Identifies the render
  thread by capturing it in the constructor.
- `GdxAudioEngine`: loads sounds via `Gdx.files.internal`, caches `Sound`
  instances, enforces per-sound cooldown, no-ops gracefully when
  `Gdx.audio`/`Gdx.files` is null.
- `GdxCharacterView`: wraps a `com.badlogic.gdx.scenes.scene2d.Actor`.
  Coordinate-system reconciliation (libGDX uses a bottom-left origin, JavaFX
  uses top-left) is deferred until the gameplay loop itself is backend-neutral.
- `GdxBackend.install()`: swaps the libGDX impls into `UiScheduler` and
  `AudioEngine`.
- `GdxAppLauncher`: `main()` that creates a 1024x768 LWJGL3 window and runs
  the WIP `MazeGameGdxListener`.

## Running the libGDX backend

Use the `Launch MazeGame (libGDX backend, WIP)` configuration in
[.vscode/launch.json](../.vscode/launch.json), or from the command line:

```powershell
mvn -pl maze-libgdx -am package
java -cp "maze-libgdx/target/classes;maze-libgdx/target/libs/*;maze-common-graphics/target/classes" main.game.maze.libgdx.GdxAppLauncher
```

## Tests

- [GdxBackendTest](src/test/java/main/game/maze/libgdx/GdxBackendTest.java) (verifies the
  adapters degrade gracefully when libGDX is not initialised, so they can run
  in headless CI).
