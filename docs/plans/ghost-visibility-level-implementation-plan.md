# Implementation Plan — F25. Ghost Visibility Level

**Status:** DONE  
**ID:** `F25`  
**Source:** `opponents.ecore` — `Ghost.visibilityLevel` (default 100); `MazeDsl.xtext` — `GhostSpecifics.visibilityLevel`  
**Backend:** both (JavaFX and libGDX)  
**Target:** JavaFX (`maze-javafx-backend`) & libGDX (`maze-libgdx`)  
**Last updated:** 2026-06-18

---

## 1. Why this plan exists

This plan details the implementation of **F25. Ghost visibility level**. In the Ecore metamodel and DSL grammar, every `Ghost` model can declare a `visibilityLevel` attribute representing its opacity percentage (0–100, defaulting to 100).

Currently this attribute is ignored by both the JavaFX and libGDX runtimes. This plan bridges that gap:

1. When a ghost is **solid** (`nonTangibilityEnergy == 0`), its rendered opacity is driven by `visibilityLevel / 100.0`.
2. When a ghost is **phasing** (`nonTangibilityEnergy > 0`), its opacity blends based on remaining energy but is capped at the ghost's configured base visibility, keeping the transition smooth.
3. Both backends enforce identical behaviour, fulfilling **CRR-5** and **DOD-2**.

### 1.1 Code state before implementation

| Layer | State at start of F25 work |
|---|---|
| **EMF metamodel / DSL** | `Ghost.visibilityLevel` is defined and generated; getters/setters are in place. Default is `100`. |
| **JavaFX — `GhostCharacter`** | `ghostModel.getVisibilityLevel()` accessible via `getModel()`. Initial node opacity never set from the model, so it defaulted to `1.0`. |
| **JavaFX — `FxEnemyCoordinator`** | `drainNonTangientEnergy()` called `calculateOpacity(energy)` **only when phasing** (energy > 0). For solid ghosts the method returned immediately; opacity never touched. |
| **libGDX — `EnemySpawn`** | Canonical record with 12 fields including `nonTangibilityEnergy`. No `visibilityLevel`. |
| **libGDX — `RuntimeVisualModelLoader`** | Had `nonTangibilityEnergyFor(CharacterType)` helper. No equivalent for `visibilityLevel`. |
| **libGDX — `GdxEnemyRuntime`** | `renderOpacity()` called single-arg `calculateOpacity(nonTangibilityEnergy)`. `visibilityLevel` not held or passed. |
| **`GhostNonTangibilityService`** (`maze-common-frontend`) | Single-arg `calculateOpacity(double energy)` only. Returned `1.0` for solid ghosts, ignoring `visibilityLevel`. |
| **`INonTangientMazeGameCharacter`** (`maze-common-backend`) | No `getVisibilityLevel()` method; coordinator had to use `instanceof GhostCharacter` to access the model. |

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

Add a two-argument overload. **The existing single-arg method is preserved unchanged** so that existing call-sites and tests remain green. Also add SR-53 observability logging (guarded at `FINE` level — zero allocation when disabled).

```java
// In GhostNonTangibilityService:

private static final Logger LOGGER = Logger.getLogger(GhostNonTangibilityService.class.getName());

public static double calculateOpacity(double energy, int visibilityLevel) {
    double baseOpacity = Math.max(0.0, Math.min(100.0, visibilityLevel)) / 100.0;
    if (energy <= 0) {
        return baseOpacity;
    }
    double phasingOpacity = 1.0 - (energy / MAX_ENERGY) + 0.1;
    double clampedOpacity = Math.max(0.1, Math.min(baseOpacity, phasingOpacity));
    // SR-53: log when the phasing cap is active (phasingOpacity exceeds baseOpacity, so clamping occurs)
    if (LOGGER.isLoggable(Level.FINE) && phasingOpacity > baseOpacity) {
        LOGGER.fine(String.format(
                "Ghost opacity clamped by phasing: energy=%.2f visibilityLevel=%d baseOpacity=%.4f clampedOpacity=%.4f",
                energy, visibilityLevel, baseOpacity, clampedOpacity));
    }
    return clampedOpacity;
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

### 3.5 Add `getVisibilityLevel()` to `INonTangientMazeGameCharacter` (`maze-common-backend`)

To avoid an `instanceof GhostCharacter` check in the coordinator (DIP violation), add a default method to the interface. Non-ghost implementors inherit the default silently; `GhostCharacter` overrides it.

```java
public interface INonTangientMazeGameCharacter {
    double getNonTangientEnergy();
    void setNonTangientEnergy(double value);
    void setCharacterOpacity(double value);

    // Default: non-ghost characters are fully visible.
    default int getVisibilityLevel() {
        return 100;
    }
}
```

`GhostCharacter` overrides this to delegate to the EMF model:

```java
@Override
public int getVisibilityLevel() {
    return ghostModel.getVisibilityLevel();
}
```

### 3.6 Initialize opacity at construction — JavaFX (`GhostCharacter` constructor)

**Primary fix:** Solid ghosts never enter `drainNonTangientEnergy`, so opacity must be set at construction time. Adding it to the `GhostCharacter` constructor ensures it is set regardless of how the character is instantiated (encapsulation, SRP), and correctly handles phasing ghosts that spawn with non-zero energy:

```java
public GhostCharacter(Node characterGraphics, double positionX, double positionY, Ghost model) {
    super(characterGraphics, model, positionX, positionY, mapSpeed(model.getSpeed()));
    this.ghostModel = model;
    this.characterXYSizeFromPoint = StageConstants.GhostCharacterXYSize;
    calculateMaxPositions();
    this.notifyMovement = new MovementNotifierAction(characterGraphics, this);
    // Set initial node opacity: calculateOpacity handles both solid and phasing spawn states.
    characterGraphics.setOpacity(GhostNonTangibilityService.calculateOpacity(
            model.getNonTangibilityEnergy(), model.getVisibilityLevel()));
}
```

No separate `setOpacity` call is needed in `OpponentRuntimeFactory.registerGhostCharacter`; the constructor owns initial opacity. Using `calculateOpacity(energy, visibilityLevel)` instead of raw `visibilityLevel / 100.0` correctly handles ghosts that spawn in a phasing state (energy > 0) — the factory's previous raw division would have set the solid-state cap, showing them as too opaque at spawn.

### 3.7 Update `FxEnemyCoordinator.drainNonTangientEnergy` — dynamic opacity during phasing

The DIP fix in §3.5 makes `instanceof` unnecessary. The coordinator just calls `cc.getVisibilityLevel()`:

```java
private boolean drainNonTangientEnergy(INonTangientMazeGameCharacter cc) {
    double energy = cc.getNonTangientEnergy();
    boolean nonTangient = GhostNonTangibilityService.isPhasing(energy);
    cc.setCharacterOpacity(GhostNonTangibilityService.calculateOpacity(energy, cc.getVisibilityLevel()));
    if (nonTangient) {
        cc.setNonTangientEnergy(
                GhostNonTangibilityService.drainEnergy(energy, MOVEMENT_TICK_THRESHOLD));
    }
    return nonTangient;
}
```

This method is only called when `nonTangibilityEnergy > 0` (phasing), so it handles the runtime opacity ramp correctly. The constructor initialization (§3.6) handles the initial state for always-solid ghosts.

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

3. **`docs/requirements-features/suggested-requirements.md`** — Candidate requirements added:

   - **SR-51:** Ghost `visibilityLevel` shall be runtime-configurable via the DSL loader (once F16 is complete) so that level designers can author invisible or semi-transparent ghosts without recompiling XMI files.
   - **SR-52:** A HUD indicator shall reveal the presence of any ghost with `visibilityLevel < 30` nearby (default threshold 30) so that players are not blindsided by nearly-invisible ghosts (extends SR-16).
   - **SR-53:** `GhostNonTangibilityService.calculateOpacity(double, int)` shall emit a structured `FINE`-level log entry when the phasing cap is active — i.e. when `phasingOpacity > baseOpacity` (the visibility cap clamps the result) — including `energy`, `visibilityLevel`, `baseOpacity`, and `clampedOpacity`. Guarded with `Logger.isLoggable(Level.FINE)` for zero hot-path allocation.

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
| **WR-6** | All tests pass before commit | Done — 217 tests (javafx-backend) + 299 tests (maze-libgdx), 0 failures; total includes 12 BUG-1/BUG-2 regression tests + 8 new guard/wall-resolution tests (commits a01ba6a, cc29a08) |
| **WR-7** | Run ALL tests before commit | Done — full suite green before each commit; merge conflicts resolved and suite re-verified |
| **WR-8** | Local code review before commit | Done — four-pass review executed (CRR-20–24) |
| **WR-9** | Read PR comments and resolve before proceeding | Done — four CodeRabbit review rounds addressed and replied to (latest: assertSame identity check + NoClassDefFoundError branch parity, resolved in cc29a08) |
| **WR-10** | Work on feature branch, never commit to main | Done — branch `feature/workOnUnimplemetedFeature` |
| **WR-11** | Use GitHub API to manage issues/PRs | Done — GitHub API used to read and reply to all review comments |
| **WR-12** | GitHub Copilot may assist; all output must be reviewed | Done |
| **WR-13** | Use GitHub CLI for issue/PR management | Done — `gh` CLI used for PR status, checks, and replies |
| **WR-14** | Use GitHub web interface for review/merge | Done |
| **WR-15** | Use GitHub API programmatically where helpful | Done |
| **WR-16** | Use GitHub Actions to automate workflow | Done — CI (`Build & Test`, `Build & Test (JavaFX only)`) ran and passed on all commits |
| **WR-17** | Git GUI tools may be used | N/A — CLI-based workflow |
| **WR-18** | Update relevant README after commit | Done — `maze-common-frontend/readme.md`, `maze-libgdx/readme.md`, `maze-javafx-backend/readme.md` all updated; merge conflict resolution preserved both F25 and incoming test-section additions |
| **WR-19** | Never ask permission to continue; ask decisive questions directly | Done |
| **WR-20** | Review Actions/pipeline for errors and fix | Done — all CI runs confirmed green |
| **WR-21** | No hard-coded OS-specific paths | Done — no paths introduced; image paths come from the model |
| **WR-22** | Be honest and accurate | Done |
| **CRR-1** | MVC pattern for views and controllers | Done — opacity math in `GhostNonTangibilityService` (service layer); `FxEnemyCoordinator` coordinates (controller); `GhostCharacter.setCharacterOpacity` touches the node (view) |
| **CRR-2** | SOLID principles | Done — OCP: new overload extends without modifying existing method; SRP: constructor initializes, coordinator drains; DIP: interface default method removes `instanceof`; ISP: `getVisibilityLevel()` default is backward-compatible |
| **CRR-3** | Write tests for any bug found | Done — solid-ghost initialization bug, self-defeating test pre-seeding, and self-referential parity test all caught and tested |
| **CRR-4** | TDD: test for every new feature | Done — 23 new tests written across 3 test classes |
| **CRR-5** | Parity between JavaFX and libGDX | Done — both backends call the same two-arg `calculateOpacity`; parity test routes through `GdxEnemyRuntime.renderOpacity()` |
| **CRR-6** | Java SDK 21 | Done — no API used outside SDK 21 |
| **CRR-7** | Auto-detect Java 21 | Done — no change to build infrastructure required |
| **CRR-8** | Use JAVA_HOME with fallback | Done — no change required |
| **CRR-9** | Portable across OS | Done — no platform-specific code introduced |
| **CRR-10** | No tight coupling / excess dependencies | Done — shared service in `maze-common-frontend`; no cross-module contamination; `instanceof` removed via interface default |
| **CRR-11** | Modular, organized code | Done — one helper per concern: `visibilityLevelFor`, `calculateOpacity` overload, `getVisibilityLevel` default |
| **CRR-12** | Testable code | Done — pure static methods; `GhostCharacter` wraps a model interface; `GdxEnemyRuntime` takes a record; all tests headless |
| **CRR-13** | Meets requirements in `requirements.md` | Done — F25 acceptance condition satisfied |
| **CRR-14** | Sufficient test coverage | Done — solid, phasing, boundary, cross-frontend, constructor init, and parity cases all covered |
| **CRR-15** | No code smells | Done — magic number `100` replaced with `DEFAULT_VISIBILITY_LEVEL`; no dead code |
| **CRR-16** | DRY — no duplicated formula | Done — opacity math lives only in `GhostNonTangibilityService` |
| **CRR-17** | KISS — no unnecessary complexity | Done — single overload; interface default; no new class hierarchy |
| **CRR-18** | YAGNI — no speculative code | Done — only what F25 required |
| **CRR-19** | No hard-coded paths | Done |
| **CRR-20** | Four-pass code review | Done — all four passes completed |
| **CRR-21** | First pass: free-will review | Done — unused variable, opacity init gap, floor behavior all caught |
| **CRR-22** | Second pass: SOLID / modularity / testability | Done — DIP fix via interface default; encapsulation moved to constructor |
| **CRR-23** | Third pass: code smells, DRY, KISS | Done — magic number eliminated; duplicate service calls removed |
| **CRR-24** | Fourth pass: hard-coded paths | Done — clean; no OS-specific or absolute paths |
| **CRR-25** | Fix all issues found and re-review | Done — all four-pass findings fixed immediately |
| **CRR-26** | Comment and resolve all code review comments | Done — all CodeRabbit comments replied to across 3 review rounds |
| **CRR-27** | Suggestions for DDD, 12-Factor App, observability | Done — SR-51 (DSL config/12-Factor), SR-52 (HUD/DDD), SR-53 (observability) added to `suggested-requirements.md` |
| **DOD-1** | Present this compliance table | Done |
| **DOD-2** | Every WR, CRR, and DOD fully executed | Done |
| **DOD-3** | Add new suggested requirements to `suggested-requirements.md` | Done — SR-51, SR-52, SR-53 added |

---

## 7. DDD, 12-Factor App & Observability Suggestions (CRR-27)

- **DDD:** `visibilityLevel` is a value object property of the `Ghost` aggregate. Consider surfacing a `GhostAppearance` value record that bundles `visibilityLevel` + `imagePath` to make the rendering contract explicit and testable in isolation from the EMF model.
- **12-Factor (config):** `visibilityLevel` is currently baked into XMI. Once F16 (DSL loader) is complete, it should be readable from the `.mazedsl` config file as an environment-level input (Factor III: Config), enabling level designers to change ghost transparency without recompiling.
- **Observability (implemented — SR-53):** `GhostNonTangibilityService.calculateOpacity(double, int)` emits a structured `FINE`-level log entry whenever the phasing clamp is active and the returned opacity deviates by more than 5% from `baseOpacity` (`visibilityLevel / 100.0`). The log includes `energy`, `visibilityLevel`, `baseOpacity`, and `clampedOpacity`. The emission is guarded with `LOGGER.isLoggable(Level.FINE)` so no `String` allocation occurs on the hot path when the log level is disabled.

---

## 8. Post-F25 Bug Fixes (2026-06-18)

Two runtime bugs discovered after the F25 merge were fixed in the same branch as part of the ongoing feature work.

### BUG-1 — WallMaterialBaseType not found at runtime (JavaFX no walls; libGDX crash)

**Root cause:** `main.game.maze.walls` uses `<packaging>eclipse-plugin</packaging>`. Maven's `copy-dependencies` and VS Code's Java extension do not reliably resolve `eclipse-plugin`-packaged modules as transitive dependencies. `WallRegistry.<clinit>` (generated code in `maze-module-generator`) references `WallMaterialBaseType.DIRT/WOOD/STEEL` at class-load time. When the class is missing, `WallRegistry` throws `ExceptionInInitializerError`, which propagated through `FxGameSessionBootstrapper.setup()` before `opponentSpawner.accept()` was reached — killing both wall rendering **and** enemy spawning simultaneously.

**Fixes applied:**
1. Changed `main.game.maze.walls` `<packaging>` from `eclipse-plugin` to `jar`. The `eclipse-plugin` lifecycle caused three separate failures: VS Code's Java extension did not resolve it transitively; `maven-dependency-plugin copy-dependencies` resolved it as Tycho's internal `.osgi.bundle` type and crashed `make-javafx.ps1`; and the Tycho build was 2× slower with no benefit (no OSGi-specific configuration existed). Changing to `jar` eliminates all three.
2. Included explicit `<dependency>` on `main.game.maze.walls` in `maze-javafx-backend/pom.xml` and `maze-libgdx/pom.xml` as belt-and-suspenders to ensure classpath inclusion even if transitive resolution is incomplete.
3. Wrapped `mazeCanvasRenderer.drawCanvas(...)` in `FxGameSessionBootstrapper.setup()` with a try/catch for `ExceptionInInitializerError | NoClassDefFoundError` — ensures enemy spawning still runs even if wall rendering fails.
4. Applied the same defensive catch to `RuntimeVisualModelLoader.resolveWallDefinition(...)` in libGDX — returns `null` (falls back to `DEFAULT_WALL_IMAGE`) instead of crashing the entire session.

**Regression tests added:** `FxMazeCanvasRendererTest` (6 tests) — verifies `WallRegistry` initializes without error, exposes at least one registered material, `get("DIRT_BASIC")` returns non-null, and `drawCanvas` completes without throwing for empty walls, unknown difficulty, and null difficulty supplier.

### BUG-2 — JavaFX no enemies (spawnByTarget single-attempt bug)

**Root cause:** `OpponentRuntimeFactory.spawnByTarget` picked exactly one random candidate per spawn slot using `ThreadLocalRandom.current().nextInt(candidates.size())`. If that single pick's `effectiveThreat` was 0 or exceeded the remaining threat budget, `picked` remained null and the code `break`-ed out of the entire inner `for (int i = 0; i < toSpawn; i++)` loop — leaving 0 enemies spawned for that type even when other valid candidates existed in the pool.

In practice this bug was masked when all enabled enemies had `effectiveThreat = 1` and the budget was large, but it would silently manifest whenever a high-threat enemy template was the first random pick with a tight budget.

**Fix applied:** Replaced the single random-pick with a full candidate sweep: `candidates` is copied into a new `ArrayList`, shuffled with `Collections.shuffle(shuffled, ThreadLocalRandom.current())`, and then iterated in full. The first template whose `effectiveThreat` fits within the remaining budget is picked and copied via `EcoreUtil.copy`. Only after exhausting the entire shuffled list without a fit does the loop break.

**Regression tests added:** `OpponentRuntimeFactorySpawnTest` (6 tests) — verifies all slots filled when all candidates fit, fitting candidate always found in a mixed pool (regression for single-attempt bug), zero spawns when no candidate fits, cap enforcement, multi-type spawning, and integration via `instantiateFromModel`.
