# Frontend Convergence Plan

## Context

Both `maze-javafx-backend` and `maze-libgdx` have evolved similar patterns — immutable input
snapshots via `InputFrame<K>`, render coordinators, and game loop managers — but with no shared
contracts. This plan closes that gap in two ordered phases and formally defers a third.

---

## Current State

| Concern              | JavaFX                                               | libGDX                                                  | Shared?           |
|----------------------|------------------------------------------------------|---------------------------------------------------------|-------------------|
| Input snapshot type  | `InputFrame<KeyCode>` (returned by `JavaFxInputSnapshotReader`) | `InputFrame<Integer>` (returned by `InputSnapshotReader`) | `InputFrame<K>` ✅ |
| Reader contract      | `JavaFxInputSnapshotReader` (pkg-private concrete)   | `InputSnapshotReader` (final concrete)                  | None ❌            |
| Render snapshot      | None — `FxGameRenderCoordinator` draws directly      | Internal `RenderState` consumed by `GdxGameRenderPipeline` | None ❌           |
| Render coordinator   | `FxGameRenderCoordinator`                            | `GdxGameRenderCoordinator` → `GdxGameRenderPipeline`    | None ❌            |
| Game loop            | `FxMovementLoopCoordinator` (AnimationTimer + bg thread) | `GdxGameScreenController.render()` (libGDX callback) | None — defer ⏸  |

---

## Phase 1 — Shared `InputSnapshotReader<K>` Interface

### Goal
Both frontend readers return `InputFrame<K>` already. Extracting a common interface lets
higher-level code depend on the abstraction instead of the concrete classes, and makes the
pattern explicit for future frontends.

### Prerequisite
Neither reader needs structural change — only the interface needs to be defined and both
concrete classes need to implement it.

### Steps

1. **Define interface in `maze-common-frontend`**
   - Package: `main.game.maze.common.input`
   - File: `InputSnapshotReader.java`
   ```java
   public interface InputSnapshotReader<K> {
       InputFrame<K> read(Set<K> trackedKeys);
   }
   ```
   - Note: The JavaFX reader's `read()` currently accepts 5 parameters (held, edge, mouseX,
     mouseY, leftClick). Its public surface must first be collapsed so that pre-collected state
     is injected at construction time rather than per-call (see step 2).

2. **Refactor `JavaFxInputSnapshotReader` (maze-javafx-backend)**
   - Currently accepts mutable input state on every `read(...)` call.
   - Change to: inject an `InputStateProvider` (or read from injected mutable containers) at
     construction, so `read(Set<KeyCode> trackedKeys)` matches the interface signature.
   - Make class package-visibility → `public` so it can be tested and referenced through the
     interface.
   - Update `GameController` / any call sites.

3. **Rename `InputSnapshotReader` in `maze-libgdx`**
   - Currently a final class named `InputSnapshotReader` — same name as the new interface.
   - Rename to `GdxInputSnapshotReader` to avoid clash.
   - Implement `InputSnapshotReader<Integer>`.

4. **Wire through interfaces**
   - Declare fields/params as `InputSnapshotReader<K>` wherever the concrete types were used.
   - `GdxGameInputBindingsSupport` injects `GdxInputSnapshotReader` → typed as
     `InputSnapshotReader<Integer>`.
   - `GameController` (JavaFX) injects `JavaFxInputSnapshotReader` → typed as
     `InputSnapshotReader<KeyCode>`.

5. **Tests**
   - Add `InputSnapshotReaderTest` in `maze-common-frontend` (contract test using a stub
     implementation) to pin the interface contract.
   - Update `JavaFxInputSnapshotReaderTest` to work with the new constructor signature.
   - Update any libGDX reader tests referencing the old class name.

### Files Touched

| File | Action |
|------|--------|
| `maze-common-frontend/.../input/InputSnapshotReader.java` | **Create** |
| `maze-javafx-backend/.../JavaFxInputSnapshotReader.java` | Refactor signature, implement interface |
| `maze-javafx-backend/.../GameController.java` | Update call site |
| `maze-libgdx/.../input/InputSnapshotReader.java` | Rename → `GdxInputSnapshotReader`, implement interface |
| `maze-libgdx/.../GdxGameInputBindingsSupport.java` | Update reference |
| Tests (both modules) | Update / add |

---

## Phase 2 — Shared `RenderSnapshot` Contract

### Goal
libGDX already assembles an internal `RenderState` record and feeds it to
`GdxGameRenderPipeline.render(RenderState)`. JavaFX's `FxGameRenderCoordinator` still draws
directly from live mutable model objects. Once JavaFX follows the same pattern, a minimal
shared contract can be extracted to `maze-common-frontend`.

### Prerequisite
Phase 2 depends on no Phase 1 work but should not start until the team agrees the JavaFX
render refactor is worth the blast radius. Validate first by reviewing
`FxGameRenderCoordinator` draw call sites in `GameController`.

### Steps

1. **Define a minimal `RenderSnapshot` marker/contract in `maze-common-frontend`**
   - Package: `main.game.maze.common.graphics`
   - The contract captures only what both frontends share: the player position, enemy states,
     visible path hints, score/HUD data, and overlay visibility flags.
   - Keep it as a sealed interface or abstract record; each frontend provides a concrete subtype
     with backend-specific rendering resources (textures, Canvas, SpriteBatch).
   ```java
   public interface RenderSnapshot {
       PlayerSnapshot player();
       List<EnemySnapshot> enemies();
       HudSnapshot hud();
       OverlayState overlay();
   }
   ```

2. **Refactor `FxGameRenderCoordinator` (maze-javafx-backend)**
   - Extract an `FxRenderSnapshot` record from the mutable state at the start of each
     AnimationTimer frame (in `FxMovementLoopCoordinator.onPlayerStep`).
   - Pass the snapshot into `FxGameRenderCoordinator.render(FxRenderSnapshot)` instead of
     individual model references.
   - Internal rendering logic remains JavaFX-specific (Canvas, GraphicsContext).

3. **Align `GdxGameRenderCoordinator.FrameInput` with the shared contract**
   - `FrameInput` is a 24-field record — inspect whether the fields covered by `RenderSnapshot`
     can be extracted into an `GdxRenderSnapshot` that implements `RenderSnapshot`.
   - `FrameInput` retains GDX-specific fields (SpriteBatch, OrthographicCamera, TextureAtlas).

4. **Wire common contract**
   - Both coordinator `render()` methods accept their respective typed snapshot (`FxRenderSnapshot`,
     `GdxRenderSnapshot`) which both implement `RenderSnapshot`.
   - Any shared analytics, observability hooks, or test assertions can reference `RenderSnapshot`.

5. **Tests**
   - Unit-test `FxRenderSnapshot` construction (immutability, expected field values).
   - Add a contract test in `maze-common-frontend` asserting required fields are present.
   - Regression-test `FxGameRenderCoordinator.render()` with a stub snapshot.

### Files Touched

| File | Action |
|------|--------|
| `maze-common-frontend/.../graphics/RenderSnapshot.java` | **Create** interface |
| `maze-common-frontend/.../graphics/PlayerSnapshot.java` | **Create** value record |
| `maze-common-frontend/.../graphics/EnemySnapshot.java` | **Create** value record |
| `maze-common-frontend/.../graphics/HudSnapshot.java` | **Create** value record |
| `maze-common-frontend/.../graphics/OverlayState.java` | **Create** value record/enum |
| `maze-javafx-backend/.../javafx/render/FxRenderSnapshot.java` | **Create** implementing class |
| `maze-javafx-backend/.../javafx/render/FxGameRenderCoordinator.java` | Refactor to accept `FxRenderSnapshot` |
| `maze-javafx-backend/.../FxMovementLoopCoordinator.java` | Extract snapshot before calling coordinator |
| `maze-libgdx/.../render/GdxRenderSnapshot.java` | **Create** (wraps subset of `FrameInput`) |
| `maze-libgdx/.../render/GdxGameRenderCoordinator.java` | Adapt `FrameInput` → `GdxRenderSnapshot` |
| Tests (both modules) | Update / add |

---

## Phase 3 — Shared Scheduling Abstraction (DEFERRED)

### Decision: Do not implement.

`FxMovementLoopCoordinator` drives frames via `javafx.animation.AnimationTimer` (JavaFX
Application Thread) and a daemon background thread for enemy AI.

`GdxGameScreenController.render()` is called synchronously by the libGDX render loop on its
own thread — no timer object, no lifecycle management needed.

The primitives are fundamentally incompatible. Any shared interface would be so thin
(`start()` / `stop()`) that it adds complexity without value. Revisit only if a third
frontend with a similar lifecycle to one of these is introduced and sharing becomes concrete.

---

## Sequencing

```
Phase 1 (InputSnapshotReader<K>)   ──► Phase 2 (RenderSnapshot)   ──► Phase 3 (deferred)
  ↑ low risk, self-contained              ↑ higher blast radius
  ↑ start here                            ↑ validate after Phase 1 lands
```

Phase 1 is safe to start immediately. Phase 2 touches the JavaFX render path and should
be reviewed by whoever owns `GameController` before starting.

---

## Acceptance Criteria

- [ ] Phase 1: `InputSnapshotReader<K>` exists in `maze-common-frontend`; both concrete
      readers implement it; no concrete type references leak into coordinator/controller code.
- [ ] Phase 1: All existing reader tests pass; new contract test added.
- [ ] Phase 2: `RenderSnapshot` and its value types exist in `maze-common-frontend`.
- [ ] Phase 2: `FxGameRenderCoordinator.render()` accepts an immutable snapshot; no direct
      mutable model access inside the coordinator.
- [ ] Phase 2: `GdxRenderSnapshot` implements `RenderSnapshot`; `GdxGameRenderPipeline`
      path unchanged.
- [ ] Phase 2: All existing render tests pass; new snapshot construction tests added.
- [ ] Phase 3: No implementation. Decision documented here.
- [ ] RTM updated for any new interfaces/classes added.
- [ ] `requirements.md` and quality-attributes file updated to reflect new shared contracts.
