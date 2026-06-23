# maze-libgdx

libGDX frontend module for MazeGame. Renders the game using an OpenGL-backed libGDX screen.

## Key responsibilities

- **GdxAppLauncher** — LWJGL3 entry point; configures the window and launches the game
- **GdxGameLayoutSupport** — camera follow, window sizing, arena construction, and goal placement
- **GdxGameScreen** — primary game screen: input, update, and render loop
- **GdxGameRenderPipeline** — draws walls, player, enemies, projectiles, and overlays

## Camera follow (F27)

`GdxGameLayoutSupport.updateCameraFollow(viewport, maze, player, camera)` — called every frame. Two-mode behaviour:

| Condition | camX | camY |
|---|---|---|
| maze fits viewport axis | `mazeSize / 2` (fixed) | `viewportSize / 2` (bottom-anchor) |
| maze exceeds viewport axis | `clamp(playerPos, halfView, mazeSize − halfView)` | same formula |

The pure-math version `computeCameraPosition(viewW, viewH, mazeW, mazeH, px, py)` is package-private and used in headless tests (`GdxCameraFollowTest`) without requiring a GL context.

`resizeWindowForDifficulty(difficulty)` caps the window at the screen's display mode dimensions.

## Building & running

```bash
mvn package -pl maze-libgdx -am
java -jar target/maze-libgdx-*.jar
```

Requires **Java 21**. See [root README](../README.md) for auto-detection details.

## Tests

```bash
mvn test -pl maze-libgdx
```

Most rendering tests require a GL context and are skipped headlessly. Camera-follow and scoring parity tests run headlessly via the extracted pure-math helpers.
