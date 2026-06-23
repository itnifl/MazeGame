# maze-javafx-backend

JavaFX frontend module for MazeGame. Provides the primary desktop UI built on JavaFX.

## Key responsibilities

- **App** — entry point; sizes the window to `min(boardSize, screenResolution)` (windowed only, never fullscreen)
- **FxGameRenderCoordinator** — renders the maze canvas and handles player-follow camera translation
- **FxGameLayoutSupport / FxDifficultyPickerSupport** — scene layout and difficulty selection UI
- **Router** — view-to-view navigation (main menu → difficulty picker → game → end screen)

## Camera follow (F27)

`FxGameRenderCoordinator.computeCameraTranslation(viewW, viewH, worldW, worldH, playerX, playerY)`
returns `[translateX, translateY]` for the canvas group transform:

- Both axes fit: returns `[0, 0]`
- World wider than viewport: `translateX = viewW/2 − playerX`, clamped to `[viewW − worldW, 0]`
- World taller than viewport: `translateY = viewH/2 − playerY`, clamped to `[viewH − worldH, 0]`

`App.clampBoardToScreen(boardW, boardH, screenW, screenH)` ensures the stage is never larger than the primary screen's visual bounds.

## Building & running

```bash
mvn package -pl maze-javafx-backend -am
java -jar target/maze-javafx-backend-*.jar
```

Requires **Java 21**. See [root README](../README.md) for auto-detection details.

## Tests

```bash
mvn test -pl maze-javafx-backend
```

Pure-math helpers (`computeCameraTranslation`, `clampBoardToScreen`) are tested headlessly — no JavaFX toolkit required.

## Background rendering (BUG-5)

`FxMazeCanvasRenderer.drawCanvas()` fills the full-map canvas with the difficulty-specific background image (tiled via `ImagePattern`) **before** painting walls. This ensures all areas revealed by the camera scroll have a background, because the `gameBoard` Pane's own background image only tiles within the Pane's window-sized layout bounds.
