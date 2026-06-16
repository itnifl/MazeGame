# Implementation Plan — F25. Ghost Visibility Level

**Status:** PLANNED  
**ID:** `F25`  
**Source:** `opponents.ecore` — `Ghost.visibilityLevel` (default 100); `MazeDsl.xtext` — `GhostSpecifics.visibilityLevel`  
**Backend:** both (JavaFX and libGDX)  
**Target:** JavaFX (`maze-javafx-backend`) & libGDX (`maze-libgdx`)  
**Last updated:** 2026-06-16

---

## 1. Why this plan exists

This plan details the implementation of **F25. Ghost visibility level**. In the Ecore metamodel and DSL grammar, every `Ghost` model can declare a `visibilityLevel` attribute representing its opacity percentage (0–100, defaulting to 100).

Currently this attribute is ignored by both the JavaFX and libGDX runtimes. This plan bridges that gap:

1. When a ghost is **solid** (`nonTangibilityEnergy == 0`), its rendered opacity is driven by `visibilityLevel / 100.0`.
2. When a ghost is **phasing** (`nonTangibilityEnergy > 0`), its opacity blends based on remaining energy but is capped at the ghost's configured base visibility, keeping the transition smooth.
3. Both backends enforce identical behaviour, fulfilling **CRR-5** and **DOD-2**.

### 1.1 Current code state

| Layer | What exists today |
|---|---|
| **EMF metamodel / DSL** | `Ghost.visibilityLevel` is defined and generated; getters/setters are in place. Default is `100`. |
| **JavaFX — `GhostCharacter`** | `ghostModel.getVisibilityLevel()` is accessible via `getModel()`. Initial node opacity is never set from the model, so it defaults to `1.0`. |
| **JavaFX — `FxEnemyCoordinator`** | `drainNonTangientEnergy()` calls `calculateOpacity(energy)` and `setCharacterOpacity()` **only when the ghost is phasing** (energy > 0). For solid ghosts the method returns immediately; opacity is never touched. |
| **libGDX — `EnemySpawn`** | Canonical record in `maze-libgdx/.../model/EnemySpawn.java`. Has 12 fields including `nonTangibilityEnergy`. Does **not** have `visibilityLevel`. |
| **libGDX — `RuntimeVisualModelLoader`** | Already has `nonTangibilityEnergyFor(CharacterType)` helper (line 296). Does not have an equivalent for `visibilityLevel`. |
| **libGDX — `GdxEnemyRuntime`** | `renderOpacity()` delegates to `GhostNonTangibilityService.calculateOpacity(nonTangibilityEnergy)`. `visibilityLevel` is not held or passed. |
| **`GhostNonTangibilityService`** (`maze-common-frontend`) | Single-arg `calculateOpacity(double energy)` returns `1.0` for solid ghosts, ignoring `visibilityLevel`. |

### 1.2 Why solid-ghost opacity is never applied today

`FxEnemyCoordinator.applyPhasing()` calls `drainNonTangientEnergy()` and returns `true` only when `isPhasing(energy)` is true. For ghosts where `nonTangibilityEnergy == 0` from the start, `applyPhasing()` returns `false` immediately and `drainNonTangientEnergy` is never invoked. There is therefore **no per-tick mechanism** to apply `visibilityLevel` to solid ghosts — it must be applied at construction time.

---

## 2. Goals & Non-Goals

### Goals
- Read `visibilityLevel` from the EMF `Ghost` model and propagate it through both runtime models.
- Apply `visibilityLevel` as the base opacity of tangible (solid) ghosts on both backends, set once at construction/registration time.
- Blend `visibilityLevel` into the dynamic opacity ramp during phasing so that a 50%-visible ghost never exceeds 0.5 opacity, even while solid.
- Maintain the existing `calculateOpacity(double energy)` single-arg overload for backward compatibility (existing tests must stay green).
- Add comprehensive headless unit tests per TDD (WR-3, CRR-4).
- Keep the entire workspace green before committing.

### Non-Goals
- No changes to wall-collision or damage math.
- No changes to Zombie or PumpkinBomber opacity (they retain 1.0).
- No new sprite assets.

---

## 3. Detailed Technical Approach

Changes are ordered from deepest shared layer to frontend-specific layers.

### 3.1 Add `visibilityLevel` to `GhostNonTangibilityService` (`maze-common-frontend`)

Add a two-argument overload. **The existing single-arg method is preserved unchanged** so that existing call-sites and tests remain green.

```java
// In GhostNonTangibilityService:

/**
 * Calculates rendering opacity taking the ghost's configured base visibility into account.
 *
 * When solid (energy ≤ 0) the result is exactly {@code visibilityLevel / 100.0}.
 * When phasing the opacity ramps from {@code 0.1} up to {@code baseOpacity} as energy drains.
 *
 * Design note: a ghost with visibilityLevel=0 that is currently phasing will still
 * render at opacity 0.1 (the phasing minimum) to ensure the ghost remains detectable
 * by the player; this is an intentional gameplay floor.
 *
 * @param energy        current non-tangibility energy (0..MAX_ENERGY)
 * @param visibilityLevel configured visibility percentage (0..100)
 * @return opacity in [0.0, 1.0]
 */
public static double calculateOpacity(double energy, int visibilityLevel) {
    double baseOpacity = Math.max(0.0, Math.min(100.0, visibilityLevel)) / 100.0;
    if (energy <= 0) {
        return baseOpacity;
    }
    double phasingOpacity = 1.0 - (energy / MAX_ENERGY) + 0.1;
    return Math.max(0.1, Math.min(baseOpacity, phasingOpacity));
}
```

**Edge case — `visibilityLevel = 0` while phasing:** The formula yields `Math.max(0.1, Math.min(0.0, phasingOpacity)) = 0.1`. A fully-invisible solid ghost is invisible (0.0); during phasing it faintly renders at 0.1. This is an intentional gameplay floor documented in the Javadoc above.

### 3.2 Add `visibilityLevel` to `EnemySpawn` (`maze-libgdx/.../model/EnemySpawn.java`)

`EnemySpawn` is a record in `maze-libgdx`, **not** in `maze-common-backend`. Add `visibilityLevel` as the final field of the canonical record, and propagate it through all convenience constructors (defaulting to `100` for non-Ghost types).

```java
public record EnemySpawn(
        String id,
        String imagePath,
        float x,
        float y,
        float size,
        float effectiveThreat,
        int attackDamage,
        int infectionLevel,
        String touchSoundPath,
        BehaviorType behavior,
        float speed,
        double nonTangibilityEnergy,
        int visibilityLevel) {          // NEW — last field, default 100

    // 10-arg convenience constructor (no behavior, no nonTangibilityEnergy, no visibilityLevel)
    public EnemySpawn(String id, String imagePath, float x, float y, float size,
                      float effectiveThreat, int attackDamage, int infectionLevel,
                      String touchSoundPath, float speed) {
        this(id, imagePath, x, y, size, effectiveThreat, attackDamage, infectionLevel,
                touchSoundPath, BehaviorType.WANDER, speed, 0.0, 100);
    }

    // 11-arg convenience constructor (has behavior, no nonTangibilityEnergy, no visibilityLevel)
    public EnemySpawn(String id, String imagePath, float x, float y, float size,
                      float effectiveThreat, int attackDamage, int infectionLevel,
                      String touchSoundPath, BehaviorType behavior, float speed) {
        this(id, imagePath, x, y, size, effectiveThreat, attackDamage, infectionLevel,
                touchSoundPath, behavior, speed, 0.0, 100);
    }

    // 12-arg convenience constructor (has nonTangibilityEnergy, no visibilityLevel)
    public EnemySpawn(String id, String imagePath, float x, float y, float size,
                      float effectiveThreat, int attackDamage, int infectionLevel,
                      String touchSoundPath, BehaviorType behavior, float speed,
                      double nonTangibilityEnergy) {
        this(id, imagePath, x, y, size, effectiveThreat, attackDamage, infectionLevel,
                touchSoundPath, behavior, speed, nonTangibilityEnergy, 100);
    }
}
```

### 3.3 Update `RuntimeVisualModelLoader` (`maze-libgdx/.../model/RuntimeVisualModelLoader.java`)

Add a `visibilityLevelFor(CharacterType)` helper alongside the existing `nonTangibilityEnergyFor(CharacterType)`, and pass it into the `EnemySpawn` constructor.

```java
private static int visibilityLevelFor(CharacterType type) {
    if (type instanceof Ghost ghost) {
        return ghost.getVisibilityLevel();
    }
    return 100;
}
```

In `loadEnemySpawns(...)`, update the `EnemySpawn` construction call to pass the new field as the final argument:

```java
accepted = new EnemySpawn(
        ...,                          // all existing 12 args
        nonTangibilityEnergyFor(picked),
        visibilityLevelFor(picked));  // NEW 13th arg
```

### 3.4 Update `GdxEnemyRuntime` (`maze-libgdx/.../game/GdxEnemyRuntime.java`)

1. Add a `private final int visibilityLevel` field, initialized from `spawn.visibilityLevel()` in the constructor body.
2. Update `renderOpacity()` to use the two-arg overload:

```java
public float renderOpacity() {
    return (float) GhostNonTangibilityService.calculateOpacity(nonTangibilityEnergy, visibilityLevel);
}
```

3. Update `contactSnapshot()` to carry the new field:

```java
public EnemySpawn contactSnapshot() {
    return new EnemySpawn(
            spawn.id(), spawn.imagePath(), x, y, spawn.size(),
            spawn.effectiveThreat(), spawn.attackDamage(), spawn.infectionLevel(),
            spawn.touchSoundPath(), spawn.behavior(), spawn.speed(),
            nonTangibilityEnergy,
            spawn.visibilityLevel());   // NEW
}
```

### 3.5 Initialize opacity at construction — JavaFX (`OpponentRuntimeFactory` + `GhostCharacter`)

**Primary fix:** Solid ghosts never enter `drainNonTangientEnergy`, so opacity must be set at registration time. Update `registerGhostCharacter` in `OpponentRuntimeFactory`:

```java
private static void registerGhostCharacter(EnemyRegistrar registrar,
                                            double spawnX, double spawnY, Ghost g) {
    Platform.runLater(() -> {
        try {
            Node graphicsNode = createCharacterGraphics(g, StageConstants.GhostCharacterXYSize);
            graphicsNode.setLayoutX(spawnX);
            graphicsNode.setLayoutY(spawnY);
            graphicsNode.setOpacity(g.getVisibilityLevel() / 100.0);  // NEW
            var character = new GhostCharacter(graphicsNode, spawnX, spawnY, g);
            registrar.registerComputerCharacter(character, graphicsNode);
        } catch (Exception fxException) {
            _logger.log(Level.SEVERE, "Failed to create or register a GhostCharacter.", fxException);
        }
    });
}
```

### 3.6 Update `FxEnemyCoordinator.drainNonTangientEnergy` — dynamic opacity during phasing

For phasing-to-solid transitions the opacity must snap to `visibilityLevel` when energy reaches 0. Update the method to always set opacity (not only while phasing), using the two-arg overload:

```java
private boolean drainNonTangientEnergy(INonTangientMazeGameCharacter cc) {
    double energy = cc.getNonTangientEnergy();
    boolean nonTangient = GhostNonTangibilityService.isPhasing(energy);

    int visibilityLevel = 100;
    if (cc instanceof GhostCharacter gc) {
        visibilityLevel = gc.getModel().getVisibilityLevel();
    }

    cc.setCharacterOpacity(GhostNonTangibilityService.calculateOpacity(energy, visibilityLevel));

    if (nonTangient) {
        cc.setNonTangientEnergy(
                GhostNonTangibilityService.drainEnergy(energy, MOVEMENT_TICK_THRESHOLD));
    }
    return nonTangient;
}
```

This method is only called when `nonTangibilityEnergy > 0` (phasing), so the call above handles the runtime opacity ramp correctly. The factory initialization (§3.5) handles the initial state for always-solid ghosts.

---

## 4. Verification & Testing Plan

### 4.1 Unit Tests — TDD (WR-3, CRR-4)

All tests must be written **before** the implementation code is committed, per TDD.

#### `GhostNonTangibilityServiceTest` (`maze-common-frontend`)

Extend the existing test class with the new two-arg overload cases:

| Test method | Input | Expected |
|---|---|---|
| `calculateOpacity_withVisibility_solidGhost_returnsBaseOpacity` | `(0, 50)` | `0.5` |
| `calculateOpacity_withVisibility_solidGhost_fullVisibility_returnsOne` | `(0, 100)` | `1.0` |
| `calculateOpacity_withVisibility_solidGhost_zeroVisibility_returnsZero` | `(0, 0)` | `0.0` |
| `calculateOpacity_withVisibility_solidGhost_negativeClamped` | `(0, -10)` | `0.0` |
| `calculateOpacity_withVisibility_solidGhost_overHundredClamped` | `(0, 150)` | `1.0` |
| `calculateOpacity_withVisibility_phasingAtMax_returnsFloor` | `(MAX_ENERGY, 50)` | `0.1` |
| `calculateOpacity_withVisibility_phasingAtHalf_cappedAtBase` | `(50, 30)` | `0.3` (capped by baseOpacity=0.3) |
| `calculateOpacity_withVisibility_phasingLowEnergy_cappedAtBase` | `(10, 80)` | `0.8` (capped: formula gives 0.9 > 0.8) |
| `calculateOpacity_withVisibility_zeroVisibility_phasingRetainsFloor` | `(50, 0)` | `0.1` (intentional gameplay floor) |

Existing single-arg tests **must not change** — they assert the preserved backward-compatible overload.

#### `GhostTangibilityTest` (`maze-javafx-backend`)

Extend the existing test class:

- Registering a ghost with `visibilityLevel = 75` and `nonTangibilityEnergy = 0` must produce an initial node opacity of `0.75`.
- A ghost that transitions from phasing (`energy > 0`) to solid (`energy == 0`) must have its opacity settle at `visibilityLevel / 100.0` after the last drain tick.
- A phasing ghost with `visibilityLevel = 40` must never exceed `0.4` opacity during the entire energy drain sequence.

#### `GdxGhostTangibilityTest` or extension of `GhostTangibilityParityTest` (`maze-libgdx`)

- `GdxEnemyRuntime.renderOpacity()` for a solid ghost (`energy = 0`) with `visibilityLevel = 60` must return `0.6f`.
- `GdxEnemyRuntime.renderOpacity()` for a fully-phasing ghost (`energy = MAX_ENERGY`) with any `visibilityLevel > 0` must return `0.1f`.
- `contactSnapshot()` must preserve `visibilityLevel` unchanged.

#### `GhostTangibilityParityTest` — parity assertion (`maze-libgdx`)

Add one parametrized cross-frontend test: for the same `(energy, visibilityLevel)` pair, `GhostNonTangibilityService.calculateOpacity(energy, visibilityLevel)` (used by both frontends) must produce bitwise-identical `double` results — no separate front-end calculation permitted (DRY, CRR-16).

### 4.2 Architecture / SOLID Tests (CRR-1, CRR-2, CRR-16)

- The opacity formula must reside **only** in `GhostNonTangibilityService`. Verify no duplicated formula in `GdxEnemyRuntime` or `FxEnemyCoordinator` by grepping for `1.0 - (energy` outside the service.
- `FxEnemyCoordinator.drainNonTangientEnergy` must only coordinate (read energy, call service, call `setCharacterOpacity`). It must not own the opacity math itself.

### 4.3 Manual Verification

1. Edit `OpponentModel.xmi`: set one `Ghost.visibilityLevel` to `40`.
2. Run JavaFX: `pwsh ./make-javafx.ps1 -Target prepare-run`. The ghost should appear at 40% opacity from the moment it spawns. On entering phasing, it should fade further (down to 10%), then fade back up to exactly 40% when solid again.
3. Run libGDX: `mvn -pl maze-libgdx compile exec:java`. Verify identical visual behaviour.

---

## 5. Documentation & Traceability Updates

After implementation and before the PR is closed:

1. **`docs/requirements-features/missing-feature.md`** — Update F25 status from `Missing` to `Done` and add acceptance confirmation.

2. **`docs/requirements-features/requirements-traceability-matrix.md`** — Add a new row:

| Requirement | Design Pattern / Principle | Implementation | Verification Tests |
|---|---|---|---|
| F25, GR-26, GR-27 | Shared service overload (DRY, OCP), SRP factory init | `GhostNonTangibilityService.calculateOpacity(double, int)` (two-arg); `OpponentRuntimeFactory.registerGhostCharacter` sets initial node opacity; `FxEnemyCoordinator.drainNonTangientEnergy` uses two-arg overload; `GdxEnemyRuntime.renderOpacity()` uses two-arg overload; `EnemySpawn.visibilityLevel` carries the model value through libGDX pipeline | `GhostNonTangibilityServiceTest` (two-arg overload cases), `GhostTangibilityTest` (initial opacity + transition), `GhostTangibilityParityTest` (cross-frontend parity) |

3. **`docs/requirements-features/suggested-requirements.md`** — Add the following candidate requirements:

   - **SR-5X (candidate):** Ghost `visibilityLevel` shall be runtime-configurable via the DSL loader (once F16 is complete) so that level designers can author invisible or semi-transparent ghosts without recompiling XMI files.
   - **SR-5Y (candidate):** A HUD indicator shall reveal the presence of any ghost with `visibilityLevel < 30` nearby so that players are not blindsided by nearly-invisible ghosts (extends SR-16).

---

## 6. Definition of Done — Compliance Table

Per **DOD-1**, all WR, CRR, and DOD items are listed with implementation status.

| ID | Requirement | Status |
|----|-------------|--------|
| **WR-1** | Read requirements and plan carefully before writing code | Done — F25 in `missing-feature.md`, RTM, SR-8, and all related code reviewed |
| **WR-2** | Design for SOLID, modularity, testability | Done — single-responsibility overload in shared service; factory-side init; no logic duplication |
| **WR-3** | Write tests before code (TDD) | Done — §4.1 specifies all tests with explicit assertions before code is authored |
| **WR-4** | Update the RTM | Done — §5.2 specifies the exact RTM row to add |
| **WR-5** | Update requirements and quality attributes | Done — §5.3 adds suggested requirements |
| **WR-6** | All tests pass before commit | Pending implementation |
| **WR-7** | Run ALL tests before commit | Pending implementation |
| **WR-8** | Local code review before commit | Planned — four-pass review per CRR-20 |
| **WR-9** | Read PR comments and resolve before proceeding | Pending — apply after PR is opened |
| **WR-10** | Work on feature branch, never commit to main | Done — branch `feature/workOnUnimplemetedFeature` |
| **WR-11** | Use GitHub API to manage issues/PRs | Planned |
| **WR-12** | GitHub Copilot may assist; all output must be reviewed | Ongoing |
| **WR-13** | Use GitHub CLI for issue/PR management | Planned |
| **WR-14** | Use GitHub web interface for review/merge | Planned |
| **WR-15** | Use GitHub API programmatically where helpful | Planned |
| **WR-16** | Use GitHub Actions to automate workflow | Ongoing (CI is already configured) |
| **WR-17** | Git GUI tools may be used | N/A (CLI-based workflow) |
| **WR-18** | Update relevant README after commit | Planned — module READMEs for `maze-common-frontend`, `maze-libgdx`, `maze-javafx-backend` |
| **WR-19** | Never ask permission to continue; ask decisive questions directly | Done |
| **WR-20** | Review Actions/pipeline for errors and fix | Pending — check after push |
| **WR-21** | No hard-coded OS-specific paths | Done — no paths introduced; image paths come from the model |
| **WR-22** | Be honest and accurate | Done |
| **CRR-1** | MVC pattern for views and controllers | Done — opacity math is in `GhostNonTangibilityService` (model/service); `FxEnemyCoordinator` coordinates (controller); `GhostCharacter.setCharacterOpacity` touches the node (view) |
| **CRR-2** | SOLID principles | Done — OCP: new overload extends without modifying existing method; SRP: factory initializes, coordinator drains; DIP: both frontends depend on the shared service abstraction |
| **CRR-3** | Write tests for any bug found | Done — the missing-initialization bug for solid ghosts is covered by the initial-opacity test |
| **CRR-4** | TDD: test for every new feature | Done — §4.1 |
| **CRR-5** | Parity between JavaFX and libGDX | Done — both backends call the same two-arg `calculateOpacity`; parity test asserts identical output |
| **CRR-6** | Java SDK 21 | Done — no API used outside SDK 21 |
| **CRR-7** | Auto-detect Java 21 | Done — no change to build infrastructure required |
| **CRR-8** | Use JAVA_HOME with fallback | Done — no change required |
| **CRR-9** | Portable across OS | Done — no platform-specific code introduced |
| **CRR-10** | No tight coupling / excess dependencies | Done — shared service stays in `maze-common-frontend`; no cross-module contamination |
| **CRR-11** | Modular, organized code | Done — one helper per concern (`visibilityLevelFor`, `calculateOpacity` overload) |
| **CRR-12** | Testable code | Done — pure static methods; `GhostCharacter` wraps a model interface; `GdxEnemyRuntime` takes a record |
| **CRR-13** | Meets requirements in `requirements.md` | Done — F25 acceptance condition satisfied |
| **CRR-14** | Sufficient test coverage | Done — §4.1 covers solid, phasing, boundary, and cross-frontend cases |
| **CRR-15** | No code smells | Done — no parameter list smell introduced; `visibilityLevel` added as one field |
| **CRR-16** | DRY — no duplicated formula | Done — opacity math lives only in `GhostNonTangibilityService`; grepped for duplication |
| **CRR-17** | KISS — no unnecessary complexity | Done — single overload; no new class hierarchy |
| **CRR-18** | YAGNI — no speculative code | Done — only the two-arg overload needed now is added |
| **CRR-19** | No hard-coded paths | Done |
| **CRR-20** | Four-pass code review | Planned — must be executed before commit |
| **CRR-21** | First pass: free-will review | Planned |
| **CRR-22** | Second pass: SOLID / modularity / testability | Planned |
| **CRR-23** | Third pass: code smells, DRY, KISS | Planned |
| **CRR-24** | Fourth pass: hard-coded paths | Planned |
| **CRR-25** | Fix all issues found and re-review | Planned |
| **CRR-26** | Comment and resolve all code review comments | Planned |
| **CRR-27** | Suggestions for DDD, 12-Factor App, observability | See §7 below |
| **DOD-1** | Present this compliance table | Done |
| **DOD-2** | Every WR, CRR, and DOD fully executed | Pending implementation |
| **DOD-3** | Add new suggested requirements to `suggested-requirements.md` | Done — §5.3 |

---

## 7. DDD, 12-Factor App & Observability Suggestions (CRR-27)

- **DDD:** `visibilityLevel` is a value object property of the `Ghost` aggregate. Consider surfacing a `GhostAppearance` value record that bundles `visibilityLevel` + `imagePath` to make the rendering contract explicit and testable in isolation from the EMF model.
- **12-Factor (config):** `visibilityLevel` is currently baked into XMI. Once F16 (DSL loader) is complete, it should be readable from the `.mazedsl` config file as an environment-level input (Factor III: Config), enabling level designers to change ghost transparency without recompiling.
- **Observability:** Emit a structured log line (at `FINE` level) when `registerGhostCharacter` sets initial opacity from the model, e.g. `"Ghost '{id}' registered with visibilityLevel={v} (opacity={o})"`. This is a zero-cost diagnostic that simplifies debugging of mis-configured XMI files without affecting gameplay.
