# maze-javafx

JavaFX implementations of the facades defined in
[maze-common-frontend](../maze-common-frontend/readme.md).

## What lives here

- `JavaFxUiScheduler`: dispatches work via `Platform.runLater`, falling back to
  inline execution when the JavaFX toolkit is not yet initialised.
- `JavaFxAudioEngine`: caches a `MediaPlayer` per classpath resource, enforces
  per-sound cooldown, swallows `MediaException` so audio glitches never crash
  gameplay.
- `FxCharacterView`: wraps a `javafx.scene.Node` and routes every mutation
  through `UiScheduler.get().runOnUiThread`.
- `JavaFxBackend.install()`: one-shot bootstrap that swaps the JavaFX impls
  into `UiScheduler` and `AudioEngine`.

## Bootstrapping

Call `JavaFxBackend.install()` from your `Application#start` method before any
game code touches the singletons. The desktop entry point
[App.java](../maze/src/main/java/main/game/maze/App.java) already does this.

## Running the JavaFX backend

From the workspace root, use the `Launch MazeGame (JavaFX)` configuration in
[.vscode/launch.json](../.vscode/launch.json), or from the command line:

```powershell
mvn -pl maze -am package
mvn -pl maze javafx:run
```

## Tests

- [JavaFxUiSchedulerTest](src/test/java/main/game/maze/javafx/JavaFxUiSchedulerTest.java)
