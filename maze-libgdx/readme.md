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

- [GdxBackendTest](src/test/java/main/game/maze/libgdx/GdxBackendTest.java): adapters degrade gracefully without libGDX initialised.
- [SampleMazeTest](src/test/java/main/game/maze/libgdx/game/SampleMazeTest.java): deterministic generation, axis-aligned walls, immutability, input validation, full-grid connectivity.
- [RealMazeTest](src/test/java/main/game/maze/libgdx/game/RealMazeTest.java): `RealMaze` adapter exposes walls / start / goal derived from `GameMazeWorld`.
- [PlayerStateTest](src/test/java/main/game/maze/libgdx/game/PlayerStateTest.java): collision resolution, border clamping, goal-proximity detection.
- [GhostTangibilityParityTest](src/test/java/main/game/maze/libgdx/GhostTangibilityParityTest.java): F25 visibility level — `EnemySpawn` carries `visibilityLevel`, default = 100, 12-arg constructor defaults, `GdxEnemyRuntime.renderOpacity()` honours `visibilityLevel` cap, full-phasing floor = 0.1, cross-frontend opacity parity with `GhostNonTangibilityService`.
- [GdxEnemyRuntimeProjectileTest](src/test/java/main/game/maze/libgdx/game/GdxEnemyRuntimeProjectileTest.java): F26 projectile speed — verifies that after 1 second of flight at `speed=100` over `distance=200`, a LOB projectile is exactly 100 px from its spawn point (`distance = speed × time`); also covers STRAIGHT wall-blocking, LOB splash, BEAM instant-damage, arc bounds, negative-dt guard, projectile cap, splash-radius exclusion, and zero-range no-fire.


Most rendering tests require a GL context and are skipped headlessly. Camera-follow and scoring parity tests run headlessly via the extracted pure-math helpers.
