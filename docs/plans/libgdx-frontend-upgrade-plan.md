# libGDX Frontend Performance and Maintainability Upgrade Plan

**Status:** PLANNED  
**Target:** libGDX Frontend (`maze-libgdx`)  
**Last updated:** 2026-06-18  

---

## 1. Why this plan exists

This plan outlines optimizations and architectural improvements for the libGDX frontend. The goal is to maximize rendering performance and improve clean separation of concerns.

---

## 2. Technical Evaluations

### 2.1 AssetManager — Async Pre-loading

#### Current State
`GdxAssetService` wraps libGDX's `AssetManager` and provides:
- `queueTexture(String)` — queues a texture for background loading.
- `finishLoading()` — blocks until all queued assets are loaded.
- `updateLoading()` / `loadingProgress()` — incremental polling for loading screens.
- `missingTexturePaths` set — prevents retrying known-missing assets.

However, `getTexture(String)` contains a synchronous fallback: if a texture was not pre-queued, it calls `manager.load(path)` followed by `manager.finishLoadingAsset(path)` (lines 56–59 of `GdxAssetService`). Enemy textures are loaded lazily via an `enemyTextureLoader` function passed to `GdxGameWorldView.render()` — they are not pre-queued at session start.

`RuntimeVisualModel` (a record) pre-computes all asset paths (player, death, background, wall, goal, and per-enemy `imagePath` lists) at session initialisation, but `GdxAssetService` is not currently told to queue those paths upfront.

#### Gap
The synchronous `finishLoadingAsset` fallback in `getTexture` blocks the render thread whenever an enemy texture is first encountered. In dense enemy scenarios this causes frame-rate spikes on the first frame each enemy type is visible.

#### Proposed Improvement
1. Add `preloadSession(RuntimeVisualModel model)` to `GdxAssetService`:

   ```java
   public void preloadSession(RuntimeVisualModel model) {
       queueTexture(model.playerImagePath());
       queueTexture(model.playerDeathImagePath());
       queueTexture(model.backgroundImagePath());
       queueTexture(model.wallImagePath());
       queueTexture(model.goalImagePath());
       model.enemies().forEach(e -> queueTexture(e.imagePath()));
   }
   ```

2. Wire `GameSessionBootstrapper` to call `preloadSession` immediately after `RuntimeVisualModel` is built, followed by a loading screen loop that calls `updateLoading()` each frame until complete.

3. Remove or guard the synchronous `finishLoadingAsset` fallback in `getTexture` — after preloading, `manager.isLoaded(path)` should always be true for session assets. Log a warning if the fallback is reached in production builds.

#### Performance Impact
Eliminates all mid-session render-thread stalls from texture loading. The loading screen absorbs the one-time cost at session startup.

#### Unit Tests

| Test class | Test method | What it verifies |
|---|---|---|
| `GdxAssetServiceTest` | `preloadSessionQueuesAllModelTextures` | After `preloadSession(model)`, each texture path from the model is queued in the `AssetManager` |
| `GdxAssetServiceTest` | `getTextureReturnsManagedInstanceAfterPreload` | `getTexture` returns the `AssetManager`-managed instance (not a new object) when pre-loaded |
| `GdxAssetServiceTest` | `missingPathSkippedOnRetry` | A path in `missingTexturePaths` is not re-queued by `preloadSession` |
| `GdxAssetServiceTest` | `loadingProgressRangeIsZeroToOne` | `loadingProgress()` returns a value in `[0.0, 1.0]` |
| `GdxAssetServiceTest` | `normalizeInternalPathStripsLeadingSlash` | `normalizeInternalPath("/images/foo.png")` returns `"images/foo.png"` |
| `GdxAssetServiceTest` | `normalizeInternalPathReturnsNullForBlank` | Blank or null input returns `null` without throwing |

---

### 2.2 Object Pooling — Zero-allocation Gameplay Loop

#### Current State
`GdxEnemyRuntime` is created once per session via `GdxEnemyRuntime.fromSpawn(...)` and kept alive until the session ends — it is not recycled. There are currently no projectiles or particles in `maze-libgdx` (projectile logic lives in `PumpkinBomberCharacter` in `maze-javafx-backend`).

The only potential allocation hotspot in the movement loop is the `MovementResult` record returned by movement services (`WanderMovementService`, `PatrolMovementService`, etc.) — if these are heap-allocated per tick, GC pressure accumulates at 60 fps with many enemies.

#### Gap
When projectile/particle support is added to the libGDX frontend, naive per-shot allocation will cause GC pauses. `GdxEnemyRuntime` also lacks a `reset()` path, preventing pooled reuse across sessions without re-construction.

#### Proposed Improvement
1. Add `reset()` to `GdxEnemyRuntime` (implementing libGDX `Poolable`) to clear position, accumulator, and direction state while retaining immutable `spawn` and `imagePath` references.

2. Create `GdxEnemyRuntimePool` in `game.pool`:

   ```
   main.game.maze.libgdx.game.pool.GdxEnemyRuntimePool
   ```

   Extends `com.badlogic.gdx.utils.Pool<GdxEnemyRuntime>`. `GameSessionStartCoordinator` calls `pool.obtain()` instead of `fromSpawn(...)`, and session teardown calls `pool.freeAll(activeEnemies)`.

3. Create `GdxProjectileRuntime` (for future PumpkinBomber parity) that implements `Poolable`, with a companion `GdxProjectilePool`.

#### Performance Impact
Zero heap allocations for enemy objects across session boundaries. Preparing for future projectile support with no GC cost during gameplay.

#### Unit Tests

| Test class | Test method | What it verifies |
|---|---|---|
| `GdxEnemyRuntimePoolTest` | `obtainCreatesNewInstanceWhenPoolIsEmpty` | `pool.obtain()` on an empty pool returns a non-null, initialised `GdxEnemyRuntime` |
| `GdxEnemyRuntimePoolTest` | `freeAndObtainReturnSameInstance` | `free` then `obtain` returns the identical object (identity check) |
| `GdxEnemyRuntimePoolTest` | `resetClearsPositionAndAccumulator` | After `reset()`, `getX() == 0`, `getY() == 0`, `getMoveAccumulator() == 0` |
| `GdxEnemyRuntimePoolTest` | `poolPeakCountTracksMaxConcurrent` | `pool.peak` equals the maximum number of simultaneously active instances |
| `GdxEnemyRuntimePoolTest` | `freeAllReturnsBatchToPool` | After `freeAll(list)`, pool free count equals list size |

---

### 2.3 View Culling — Frustum Clipping

#### Current State
`GdxGameWorldView.drawBackground` already performs implicit view-bounded tiling — it calculates `viewMinX/Y` and `viewMaxX/Y` from the `OrthographicCamera` and only tiles the visible area. This is a form of culling.

However, `drawWallsTexture` and `drawWallsFallback` iterate the full `maze.walls()` list and issue a `batch.draw(...)` / `shapes.rect(...)` call for every segment, even those entirely off-screen. For a large maze, `maze.walls()` may contain hundreds of `WallSegment` entries.

Enemy culling is also absent: `drawEnemies` iterates `context.enemies()` which is the full `List<EnemyViewModel>` built by `GdxGameRenderStateAssembler` — off-screen enemies still incur texture-bind and draw-call overhead.

#### Gap
Wall rendering is the highest-impact gap: it issues O(totalWalls) draw calls regardless of camera position. For a 20×20 maze the visible portion at typical zoom is roughly 20–30% of all walls.

#### Proposed Improvement
1. Introduce `GdxFrustumCuller` utility class in `render`:

   ```
   main.game.maze.libgdx.render.GdxFrustumCuller
   ```

   ```java
   public static boolean isWallVisible(WallSegment wall, OrthographicCamera cam, float padding) {
       float halfW = cam.viewportWidth  * cam.zoom * 0.5f + padding;
       float halfH = cam.viewportHeight * cam.zoom * 0.5f + padding;
       float minX = Math.min(wall.x1, wall.x2);
       float maxX = Math.max(wall.x1, wall.x2);
       float minY = Math.min(wall.y1, wall.y2);
       float maxY = Math.max(wall.y1, wall.y2);
       return maxX >= cam.position.x - halfW
           && minX <= cam.position.x + halfW
           && maxY >= cam.position.y - halfH
           && minY <= cam.position.y + halfH;
   }

   public static boolean isEnemyVisible(float x, float y, float size, OrthographicCamera cam, float padding) { ... }
   ```

2. Apply `GdxFrustumCuller.isWallVisible` in `GdxGameWorldView.drawWallsTexture` and `drawWallsFallback`.

3. Filter the `EnemyViewModel` list in `GdxGameRenderStateAssembler` before it is placed into `RenderContext`, using `isEnemyVisible`. This removes off-screen enemies from the batch entirely.

#### Performance Impact
For large mazes, reduces wall draw calls by 70–80% at typical zoom. Removes texture-bind overhead for off-screen enemies.

#### Unit Tests

| Test class | Test method | What it verifies |
|---|---|---|
| `GdxFrustumCullerTest` | `wallFullyInsideFrustumIsVisible` | Wall segment entirely within camera bounds returns `true` |
| `GdxFrustumCullerTest` | `wallFullyOutsideFrustumIsNotVisible` | Wall segment with no overlap returns `false` |
| `GdxFrustumCullerTest` | `wallPartiallyOverlappingFrustumIsVisible` | Partially overlapping wall returns `true` |
| `GdxFrustumCullerTest` | `wallExactlyOnFrustumEdgeIsVisible` | Boundary-touching wall returns `true` (inclusive) |
| `GdxFrustumCullerTest` | `enemyOutsideCameraIsNotVisible` | Enemy centre + size outside bounds returns `false` |
| `GdxFrustumCullerTest` | `paddingExpandsEffectiveFrustum` | Wall just outside strict bounds is visible when `padding > 0` |
| `GdxGameRenderStateAssemblerTest` | `offScreenEnemiesExcludedFromViewModelList` | Assembler omits `EnemyViewModel` for enemies outside camera bounds |

---

### 2.4 Texture Atlas — Single GPU Texture

#### Current State
`GdxAssetService` loads each texture by individual classpath path, producing separate `Texture` objects in GPU memory. `RuntimeVisualModel` stores individual `imagePath` strings for each entity type. `GdxGameWorldView` changes the active texture on every enemy draw (enemy-A texture → enemy-B texture → …), which forces `SpriteBatch` to flush its vertex buffer on each texture change.

No `TextureAtlas` or `TexturePacker` integration exists.

#### Gap
Each unique texture binding flushes `SpriteBatch`. With N enemy types, the batch flushes N times per frame minimum. Consolidating all sprites into one atlas reduces this to one flush for all entities.

#### Proposed Improvement
1. Add `GdxTextureAtlasService` in `service`:

   ```
   main.game.maze.libgdx.service.GdxTextureAtlasService
   ```

   - Wraps a libGDX `TextureAtlas` loaded via `AssetManager` (`manager.load("sprites.atlas", TextureAtlas.class)`).
   - `getRegion(String logicalName)` — returns `TextureRegion` by name; returns `null` gracefully for unknown names.
   - `dispose()` — delegates to `manager.dispose()`.

2. `GdxAssetService.preloadSession` gains an overload that also queues the atlas if a `sprites.atlas` file exists alongside the textures.

3. `GdxGameWorldView` prefers `TextureRegion` from the atlas over raw `Texture` when available. `EnemyViewModel` gains an optional `TextureRegion region` field alongside the existing `Texture texture` field. The draw call uses `batch.draw(region, ...)` when present.

4. Pack player frames, ghost/zombie/pumpkin-bomber directional frames, goal tile, and wall tile into `sprites.atlas` using libGDX `TexturePacker` as a build step.

#### Performance Impact
Reduces GPU texture binds per frame from O(entity types) to 1 (ideally). `SpriteBatch` issues a single draw call for the entire entity layer.

#### Unit Tests

| Test class | Test method | What it verifies |
|---|---|---|
| `GdxTextureAtlasServiceTest` | `regionLookupByNameReturnsCorrectRegion` | `getRegion("player_right")` returns a non-null `TextureRegion` |
| `GdxTextureAtlasServiceTest` | `unknownRegionNameReturnsNull` | Missing name does not throw |
| `GdxTextureAtlasServiceTest` | `disposeReleasesAtlasResources` | After `dispose()`, subsequent `getRegion` calls return `null` without throwing |
| `GdxTextureAtlasServiceTest` | `atlasLoadedOnlyOnceForMultipleGetRegionCalls` | Multiple `getRegion` calls on the same atlas file do not trigger re-loading |

---

### 2.5 Sprite Batching — Draw-Order and Blend Optimisation

#### Current State
`GdxGameWorldView.render()` uses a single `SpriteBatch` and draws in this order: background → goal → walls → enemies → player. This is generally correct for minimising batch flushes since all entities in each pass share the same texture (or atlas region).

However, `drawInfectiousEdgeMist` changes the blend function mid-batch via `batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE)` (line 297 of `GdxGameWorldView`) and restores it afterwards. Changing the blend function forces `SpriteBatch` to flush, then draw mist geometry, then flush again on restore — this means one or more infectious enemies can cause two extra batch flushes per frame.

`drawEnemies` currently interleaves infectious mist and solid-sprite draws within the same loop iteration (lines 122–143), so mist and sprite draws are tightly coupled.

#### Gap
Blend-function changes inside the enemy loop cause batch flushes proportional to infectious enemy count. Moving mist rendering to a separate pass eliminates these intermediate flushes.

#### Proposed Improvement
1. Split `drawEnemies` into two sequential methods:
   - `drawEnemySprites(context, batch, font)` — draws only solid enemy textures; no blend changes.
   - `drawInfectiousMistLayer(context, batch)` — sets additive blend once before the loop, draws all mist passes, restores blend once after.

   Render order becomes: background → goal → walls → **enemySprites** → player → **infectiousMistLayer**.

2. Introduce `GdxRenderPassSorter` in `render`:

   ```
   main.game.maze.libgdx.render.GdxRenderPassSorter
   ```

   ```java
   public record SortedEnemyPasses(
       List<EnemyViewModel> opaqueEnemies,
       List<EnemyViewModel> infectiousEnemies) {}

   public static SortedEnemyPasses sort(List<EnemyViewModel> enemies) { ... }
   ```

   `GdxGameRenderPipeline` (which assembles `RenderContext`) calls `GdxRenderPassSorter.sort` to split the enemy list before constructing the context record, rather than doing it inline in the view.

#### Performance Impact
Reduces batch flushes per frame by 2 × (infectious enemy count). For a game with 3 infectious enemies, this saves 6 unnecessary flushes per frame (360 per second at 60 fps).

#### Unit Tests

| Test class | Test method | What it verifies |
|---|---|---|
| `GdxRenderPassSorterTest` | `nonInfectiousEnemiesGoToOpaqueList` | Enemies with `infectious == false` appear in `opaqueEnemies` only |
| `GdxRenderPassSorterTest` | `infectiousEnemiesGoToInfectiousList` | Enemies with `infectious == true` appear in `infectiousEnemies` only |
| `GdxRenderPassSorterTest` | `emptyInputProducesEmptyLists` | No enemies in → both output lists empty |
| `GdxRenderPassSorterTest` | `sortIsStable` | Original relative order within each group is preserved |
| `GdxGameWorldViewTest` | `infectiousMistPassRenderedAfterOpaqueEnemies` | Verifies `drawInfectiousMistLayer` is called after `drawEnemySprites` within a single `render` invocation |

---

## 3. Entity Component System (ECS) Evaluation

Evaluating the transition from an object-oriented character model to a data-oriented ECS architecture (such as Ashley ECS).

### 3.1 Performance Evaluation
`GdxEnemyRuntime` stores all state (position, direction, accumulator, behaviour labels, non-tangibility energy, visibility level) in one object. Movement services process enemies one at a time via `enemy.advance(world, services, dt)`. With Ashley ECS, position and velocity data live in contiguous `ComponentMapper`-accessed arrays. A `MovementSystem.update(dt)` iterates a packed `ImmutableArray<Entity>` for cache-friendly processing. At high enemy counts (50+) this layout yields measurable CPU cache-hit improvements.

### 3.2 Maintainability Evaluation
By separating data (`PositionComponent`, `VelocityComponent`, `BehaviourComponent`, `RenderComponent`) from behaviour (`MovementSystem`, `RenderSystem`, `InfectionSystem`), new enemy types are composed by attaching components rather than subclassing. For example, adding a poisoning enemy requires only a `PoisonComponent` and a `PoisonSystem` — no changes to existing character classes. This directly supports CRR-2 (SOLID) and CRR-5 (feature parity with JavaFX).

---

## 4. Implementation Stages

| Stage | Work | Depends on |
|---|---|---|
| **Stage 1** | Add `preloadSession(RuntimeVisualModel)` to `GdxAssetService`; wire `GameSessionBootstrapper` to call it; add loading screen progress loop (§2.1) | — |
| **Stage 2** | Implement `GdxFrustumCuller`; apply wall and enemy culling in `GdxGameWorldView` and `GdxGameRenderStateAssembler` (§2.3) | Stage 1 (textures must be loaded before culling is meaningful) |
| **Stage 3** | Implement `GdxRenderPassSorter`; split `drawEnemies` into opaque and mist passes in `GdxGameWorldView` (§2.5) | Stage 2 |
| **Stage 4** | Implement `GdxTextureAtlasService`; pack `sprites.atlas` build step; update `EnemyViewModel` with `TextureRegion` field (§2.4) | Stage 1 |
| **Stage 5** | Add `Poolable` to `GdxEnemyRuntime`; implement `GdxEnemyRuntimePool`; update session lifecycle (§2.2) | Stage 1 |
| **Stage 6 (Optional)** | Refactor gameplay loops into a unified Ashley ECS module (§3) | Stages 1–5 |
