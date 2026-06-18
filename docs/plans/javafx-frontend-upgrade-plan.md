# JavaFX Frontend Performance and Maintainability Upgrade Plan

**Status:** PLANNED  
**Target:** JavaFX Frontend (`maze-javafx`, `maze-javafx-backend`)  
**Last updated:** 2026-06-18  

---

## 1. Why this plan exists

This plan outlines optimizations and architectural improvements for the JavaFX frontend. The goal is to maximize rendering performance and improve clean separation of concerns.

---

## 2. Technical Evaluations

### 2.1 AssetManager — Centralised Image Cache

#### Current State
Image loading is fragmented across three independent mechanisms:
- `FxMazeCanvasRenderer` holds a `HashMap<String, Image> wallImageCache` backed by `computeIfAbsent`, scoped only to wall textures.
- `PlayerCharacter` and each `ComputerCharacter` subclass (`GhostCharacter`, `ZombieCharacter`, `PumpkinBomberCharacter`) load their own directional images directly in constructors via `getClass().getResource(path)` — synchronously on whichever thread constructs them.
- `JavaFxAudioEngine` (in `maze-javafx`) has a separate `ConcurrentHashMap<String, MediaPlayer>` for audio, unrelated to image loading.

There is no shared, centralised, thread-safe image cache across character types. When a new enemy is spawned mid-game, its images are loaded synchronously on the JavaFX application thread.

#### Gap
Enemy construction blocks the FX thread. Character subclasses cannot share loaded `Image` instances for the same asset path (two `ZombieCharacter` instances each load the same four directional files separately). The wall cache in `FxMazeCanvasRenderer` cannot be reused by character classes.

#### Proposed Improvement
Introduce `FxImageCache` in `maze-javafx-backend` under `javafx.assets`:

```
main.game.maze.javafx.assets.FxImageCache
```

- Backed by `ConcurrentHashMap<String, Image>` for thread safety.
- `getOrLoad(String classpathPath)` — synchronous load with cache, safe to call from background threads.
- `loadAsync(String classpathPath, Consumer<Image> onReady)` — submits load task to a shared `ExecutorService`, calls `onReady` on the FX thread via `Platform.runLater`.
- `preload(Collection<String> paths)` — batch-submits all paths for background loading (called at session start from `FxGameSessionBootstrapper`).
- `clear()` — disposes all cached `Image` objects and shuts down the executor.

Character constructors replace direct `new Image(url.toExternalForm())` calls with `FxImageCache.getInstance().getOrLoad(path)`. `FxMazeCanvasRenderer` delegates its `wallImageCache` lookups to the same singleton.

#### Performance Impact
- Prevents UI thread stalls during enemy spawning.
- Eliminates duplicate `Image` allocations when multiple enemies share a sprite path.
- Warm cache at session start means zero-latency image retrieval during gameplay.

#### Unit Tests

| Test class | Test method | What it verifies |
|---|---|---|
| `FxImageCacheTest` | `getOrLoadReturnsCachedInstance` | Second call for the same path returns the identical `Image` object |
| `FxImageCacheTest` | `concurrentLoadsDoNotDuplicateEntries` | 10 threads requesting the same path result in exactly one `Image` in the map |
| `FxImageCacheTest` | `missingPathReturnsNullWithoutThrowing` | Graceful null return for a non-existent classpath resource |
| `FxImageCacheTest` | `asyncLoadNotifiesCallbackOnFxThread` | `loadAsync` invokes the `Consumer<Image>` argument (verified with a `CountDownLatch`) |
| `FxImageCacheTest` | `clearRemovesAllEntries` | Cache is empty and executor is shut down after `clear()` |
| `FxImageCacheTest` | `preloadAllPathsAreCachedBeforeFirstFrame` | After `preload(paths)` + `awaitCompletion()`, all paths resolve synchronously in `getOrLoad` |

---

### 2.2 Object Pooling — Projectile Node Reuse

#### Current State
`PumpkinBomberCharacter` maintains a `List<PumpkinProjectile> projectiles`. Each call to `tryShootAt(...)` invokes `PumpkinProjectile.createArc(...)`, which allocates a new `PumpkinProjectile` instance wrapping a fresh `ImageView` node. Completed projectiles are removed from the list via an `Iterator`. The discarded `ImageView` is detached from the scene graph and becomes GC-eligible.

No pooling exists anywhere in the JavaFX character layer.

#### Gap
Creating and destroying `ImageView` nodes on every projectile lifecycle is expensive: JavaFX must add the node to the scene graph, run layout passes, apply CSS, then detach and GC it. In high-fire-rate scenarios this creates both GC pressure and layout overhead.

#### Proposed Improvement
Introduce `PumpkinProjectilePool` in `maze-javafx-backend` under `characters.pool`:

```
main.game.maze.characters.pool.PumpkinProjectilePool
```

- Backed by `ArrayDeque<PumpkinProjectile>` with a configurable capacity cap (default 16).
- `acquire()` — returns a pooled instance if available, otherwise creates a new one.
- `release(PumpkinProjectile p)` — calls `p.reset()` and pushes back if below capacity.

`PumpkinProjectile` gains a `reset(double sx, double sy, double tx, double ty, double speed, double arcHeight, double splashRadius, int damage)` method that clears all trajectory state and resets the wrapped `ImageView`'s position and opacity, so the node can be re-added to the scene without re-constructing it.

`PumpkinBomberCharacter.tryShootAt` calls `pool.acquire()` instead of `PumpkinProjectile.createArc`, and the advance/splash loop calls `pool.release(p)` on completion.

#### Performance Impact
Eliminates all runtime allocations during projectile gameplay. Scene-graph node count stays constant rather than fluctuating with fire rate.

#### Unit Tests

| Test class | Test method | What it verifies |
|---|---|---|
| `PumpkinProjectilePoolTest` | `acquireReturnsNewInstanceWhenPoolIsEmpty` | Empty pool creates a valid `PumpkinProjectile` |
| `PumpkinProjectilePoolTest` | `releaseAndAcquireReturnSameInstance` | Released instance is returned on next `acquire` |
| `PumpkinProjectilePoolTest` | `resetClearsTrajectoryProgressToZero` | `reset(...)` sets `t = 0`, new start/end coordinates, new damage values |
| `PumpkinProjectilePoolTest` | `poolDoesNotGrowBeyondCapacity` | Releasing more than `maxSize` items discards the excess (pool size stays capped) |
| `PumpkinProjectilePoolTest` | `concurrentAcquireReleaseIsThreadSafe` | 4 threads simultaneously acquiring and releasing produce no `ConcurrentModificationException` |

---

### 2.3 View Culling — Off-screen Node Suppression

#### Current State
`FxGameRenderCoordinator.updateCameraFollow` computes `translateX`/`translateY` (the camera offset applied to the `gameBoard` `Pane`) but does not feed those bounds back to the canvas renderer or to individual character visibility. `FxMazeCanvasRenderer.drawCanvas` iterates all wall `Vector2D` segments and draws every one regardless of whether it falls within the current viewport. The `Rectangle` clip installed by `installGameBoardClip` prevents pixels from appearing outside the window, but the CPU still executes every `GraphicsContext.drawImage` call.

#### Gap
For large mazes (high difficulty levels) the wall segment list is long. Every wall is drawn even when the camera is showing a small sub-region of the maze. The JavaFX CSS styling engine also processes every `Node` in the scene graph, including invisible off-screen character `ImageView` nodes.

#### Proposed Improvement
1. **Wall canvas culling:** Extract the culling rectangle from `FxGameRenderCoordinator` and pass it into `FxMazeCanvasRenderer.drawCanvas` as optional viewport bounds. Inside the loop, skip any wall segment whose axis-aligned bounding box does not intersect the clip rectangle:

   ```
   [−translateX, −translateY, −translateX + viewportW, −translateY + viewportH]
   ```

2. **Character visibility toggling:** Introduce `FxViewCullingService` in `javafx.render`:

   ```
   main.game.maze.javafx.render.FxViewCullingService
   ```

   Exposes `isInViewport(double nodeX, double nodeY, double nodeW, double nodeH, double vpMinX, double vpMinY, double vpMaxX, double vpMaxY)`. `FxEnemyCoordinator` calls this each frame to `setVisible(false)` on nodes outside the viewport, removing them from the CSS pass without detaching them from the scene graph.

#### Performance Impact
Reduces `GraphicsContext.drawImage` calls proportionally to the fraction of the maze off-screen. For a 5×5-cell viewport on a 15×15-cell maze, roughly 89 % of wall draws are skipped. CSS overhead for off-screen enemy nodes drops to near zero.

#### Unit Tests

| Test class | Test method | What it verifies |
|---|---|---|
| `FxViewCullingServiceTest` | `nodeFullyInsideViewportIsVisible` | Node entirely within bounds returns `true` |
| `FxViewCullingServiceTest` | `nodeFullyOutsideViewportIsNotVisible` | Node with no overlap returns `false` |
| `FxViewCullingServiceTest` | `nodeEdgeTouchingViewportBoundaryIsVisible` | AABB touching exactly on one edge returns `true` (boundary-inclusive) |
| `FxViewCullingServiceTest` | `nodePartiallyOverlappingIsVisible` | Partially overlapping node returns `true` |
| `FxMazeCanvasRendererTest` | `wallSegmentFullyOutsideViewportIsNotDrawn` | Provides a mock `GraphicsContext`; verifies `drawImage` not called for out-of-bounds segment |
| `FxMazeCanvasRendererTest` | `wallSegmentInsideViewportIsDrawn` | Verifies `drawImage` called exactly once for an in-bounds segment |

---

### 2.4 Texture Atlas — Shared Spritesheet

#### Current State
Each character type loads a separate set of image files. `FxImageCache` (once introduced) will prevent duplicate `Image` objects in heap memory, but the images are still loaded and stored individually. There is no spritesheet combining character frames.

#### Gap
Separate image files mean separate heap allocations and separate `Image` objects in memory even after caching. A texture atlas would consolidate all sprites into one `Image`, reducing memory footprint.

#### Proposed Improvement
1. Define `SpriteAtlasDefinition` as an immutable record:

   ```
   main.game.maze.javafx.assets.SpriteAtlasDefinition
   record SpriteAtlasDefinition(String sheetPath, Map<String, Rectangle2D> regions)
   ```

2. Extend `FxImageCache` with `getRegion(String sheetPath, Rectangle2D viewport)` returning a `WritableImage` clipped from the cached sheet `Image`. `ImageView` can also use `setViewport(Rectangle2D)` directly for zero-copy region selection.

3. Ship a default `sprites.png` atlas packing player directional frames, ghost/zombie/pumpkin-bomber frames, and goal sprite. Define atlas coordinates in a companion `sprites-atlas.json` loaded once at startup.

#### Performance Impact
Reduces total heap `Image` count from N (one per sprite file) to 1. Loading time drops to a single file read.

#### Unit Tests

| Test class | Test method | What it verifies |
|---|---|---|
| `SpriteAtlasDefinitionTest` | `lookupByNameReturnsCorrectBounds` | `regions.get("player_right")` returns the expected `Rectangle2D(x, y, w, h)` |
| `SpriteAtlasDefinitionTest` | `unknownNameReturnsNull` | Missing key does not throw |
| `FxImageCacheAtlasTest` | `sameSheetNotLoadedTwiceForTwoRegions` | Two `getRegion` calls with the same sheet path result in one map entry |
| `FxImageCacheAtlasTest` | `regionPixelBoundsAreRespected` | Extracted `WritableImage` has width and height matching the requested `Rectangle2D` |

---

### 2.5 Sprite Batching — Canvas-based Character Rendering

#### Current State
Wall rendering already uses immediate-mode `Canvas` / `GraphicsContext.drawImage` in `FxMazeCanvasRenderer`. However, character entities (`PlayerCharacter`, `GhostCharacter`, `ZombieCharacter`, `PumpkinBomberCharacter`) are all scene-graph `ImageView` nodes added to the `gameBoard` `Pane`. Every position update calls `setLayoutX` / `setLayoutY`, which triggers layout invalidation. JavaFX evaluates CSS, bounds, and z-ordering for every node on every pulse.

`FxGameRenderCoordinator` orchestrates canvas redraws but does not currently hold a character-rendering canvas.

#### Gap
Scene-graph overhead scales with enemy count. Moving character rendering to a shared `Canvas` eliminates layout and CSS costs entirely — the canvas is a single retained-mode node regardless of enemy count.

#### Proposed Improvement
1. Define `IFxFrameRenderer` interface:

   ```
   main.game.maze.javafx.render.IFxFrameRenderer
   void renderFrame(GraphicsContext gc, RenderSnapshot snapshot)
   ```

2. Implement `FxCharacterCanvasRenderer` in `javafx.render`:
   - Accepts a `RenderSnapshot` record (player position, image, each enemy's position, image, opacity, visibility flag).
   - Calls `gc.drawImage(...)` for each visible character in one sequential pass — player first, then enemies sorted by Y for painter-order depth.
   - Uses `gc.setGlobalAlpha(enemy.opacity())` for ghost phase rendering instead of per-node opacity.

3. `FxGameRenderCoordinator` introduces a dedicated `characterCanvas` layered above `mazeCanvas`. After each `updateCameraFollow` tick it calls `FxCharacterCanvasRenderer.renderFrame(...)`.

4. `Character.characterGraphics` is retained as the collision/input-handling node but made invisible (`setVisible(false)`) so it does not render through the scene graph. Position tracking moves to a lightweight `Point2D` field (already present as `characterPosition`).

#### Performance Impact
Scene-graph node count for entities drops from O(enemies) to 1. CSS passes and layout recalculations for enemy nodes are eliminated. Achieves stable 60 fps at high enemy counts.

#### Unit Tests

| Test class | Test method | What it verifies |
|---|---|---|
| `FxCharacterCanvasRendererTest` | `renderCallsDrawImageForEachVisibleEnemy` | `renderFrame` calls `gc.drawImage` exactly N times for N visible enemies |
| `FxCharacterCanvasRendererTest` | `deadPlayerUsesDeathImage` | When `snapshot.playerDead() == true`, death image is passed to `drawImage` |
| `FxCharacterCanvasRendererTest` | `ghostWithOpacityBelowOneAppliesAlpha` | `gc.setGlobalAlpha` called with opacity < 1 before drawing the ghost |
| `FxCharacterCanvasRendererTest` | `invisibleEnemyIsNotDrawn` | Enemy with `visible == false` in snapshot results in no `drawImage` call |
| `FxCharacterCanvasRendererTest` | `enemiesSortedByYAscendingForDepthOrder` | Draw order matches ascending Y coordinate (painter's algorithm) |

---

## 3. Entity Component System (ECS) Evaluation

Evaluating the transition from an object-oriented character model to a data-oriented ECS architecture within the JavaFX environment.

### 3.1 Performance Evaluation
The current `Character` hierarchy stores state (position, direction, hitPoints, directional images) inside the entity objects themselves. Each game-loop tick iterates the active character list and calls `doPositionEvaluation()` on each. With ECS, position data lives in a contiguous `PositionComponent[]` array; a `MovementSystem` iterates the array in one cache-friendly pass. For the canvas-rendering stage proposed in §2.5, a `RenderComponent[]` array feeds `FxCharacterCanvasRenderer` directly, removing the per-entity dispatch overhead.

### 3.2 Maintainability Evaluation
The existing `Character → ComputerCharacter → GhostCharacter / ZombieCharacter / PumpkinBomberCharacter` hierarchy mixes data, movement, and rendering concerns in the same class. Adding a new enemy type requires subclassing `ComputerCharacter` and overriding multiple methods. With ECS, composing a new enemy is attaching a `BehaviourComponent(BehaviorType.WANDER)` and a `RenderComponent(imagePaths)` to a plain entity ID — no subclass needed.

---

## 4. Implementation Stages

| Stage | Work | Depends on |
|---|---|---|
| **Stage 1** | Introduce `FxImageCache` (§2.1); wire `FxGameSessionBootstrapper` to call `preload()` at session start; update all character constructors to use the cache | — |
| **Stage 2** | Implement `FxViewCullingService` and viewport-aware `FxMazeCanvasRenderer.drawCanvas` (§2.3) | Stage 1 (shares cache) |
| **Stage 3** | Introduce `FxCharacterCanvasRenderer` and `IFxFrameRenderer` (§2.5); add character `Canvas` layer to `FxGameRenderCoordinator`; suppress scene-graph `ImageView` rendering | Stage 1, 2 |
| **Stage 4** | Add `PumpkinProjectilePool` (§2.2); update `PumpkinBomberCharacter.tryShootAt` to acquire/release | Stage 3 |
| **Stage 5** | Define `SpriteAtlasDefinition`, pack `sprites.png`, update `FxImageCache` with region support (§2.4) | Stage 1 |
| **Stage 6 (Optional)** | Refactor coordinate state and collision rules into a custom lightweight ECS engine (§3) | Stages 1–5 |
