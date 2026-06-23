# F8 — EnemyMaxCount Caps Enforced at Runtime

## Feature selection rationale

F8 is the easiest open feature to implement because:

| Factor | Why F8 wins |
|--------|-------------|
| Infrastructure | `EnemySpawnPlanner.capsFromDifficulty()` already builds the `EnumMap<EnemyTypes, Integer>` at line 99 of `OpponentRuntimeFactory`; the data is in place |
| Code touch points | One method signature change (`spawnByTarget` in `OpponentRuntimeFactory`) + 3–5 lines of guard logic |
| No UI | Zero rendering or scene-graph changes needed |
| No new model fields | `EnemyMaxCount` and its `maxCount` attribute already exist in the EMF model and in every shipped XMI |
| Analogous pattern | `maxThreat` enforcement at line 236–237 is the exact same pattern; we replicate it per-type |
| Test scaffolding | `EnemySpawnPlannerTest` already shows how to build `Difficulty` + `EnemyMaxCount` objects in-process without JavaFX |

F25 (ghost visibility) was explicitly excluded. F9 (validateMaxThreat) is already partially implemented as a post-spawn OCL check; F8 is the sharper gap.

---

## Current state

File: [maze-javafx-backend/.../runtime/opponents/OpponentRuntimeFactory.java](maze-javafx-backend/src/main/java/main/game/maze/runtime/opponents/OpponentRuntimeFactory.java)

```
line  99  EnumMap<EnemyTypes, Integer> caps = new EnumMap<>(EnemySpawnPlanner.capsFromDifficulty(diff));
          // ↑ caps is built here…

line 154  double threatSum = spawnByTarget(
              characterList, target, maxThreatByDifficulty,
              speedMult, dmgMult, instantDeath, registrar,
              noOfGhostsSpawned, noOfZombiesSpawned, noOfPumpkinBombersSpawned
          );
          // ↑ …but caps is NOT passed to spawnByTarget()

line 183  private static double spawnByTarget(
              List<CharacterType> all, Map<EnemyTypes, Integer> target,
              int maxThreat, ...)
          // ↑ no caps parameter; toSpawn comes straight from target
line 227      int toSpawn = Math.max(0, e.getValue());   // ← no per-type ceiling
```

**Gap:** The resolver computes `target` from ratios, not directly from caps. A type whose ratio count exceeds its `EnemyMaxCount.maxCount` is silently over-spawned.

---

## Implementation plan

### Step 1 — Extract a pure cap-enforcement helper into `EnemySpawnPlanner`

Add a single static method so the logic is testable without touching `OpponentRuntimeFactory`
or JavaFX:

```java
// EnemySpawnPlanner.java  (main.game.maze.opponents.util)
/**
 * Returns the number of enemies of {@code type} that may still be spawned given
 * the per-type cap declared in {@code caps} and the number already spawned.
 * Returns {@code requested} unchanged when no cap is defined for that type.
 */
public static int clampToCapLimit(
        EnemyTypes type,
        int requested,
        Map<EnemyTypes, Integer> caps) {
    int cap = caps.getOrDefault(type, Integer.MAX_VALUE);
    return Math.max(0, Math.min(requested, cap));
}
```

File: [main.game.maze.opponents/src/main/java/main/game/maze/opponents/util/EnemySpawnPlanner.java](main.game.maze.opponents/src/main/java/main/game/maze/opponents/util/EnemySpawnPlanner.java)

### Step 2 — Thread `caps` into `spawnByTarget`

**Signature change** (OpponentRuntimeFactory line 183):

```java
// Before
private static double spawnByTarget(
        List<CharacterType> all,
        Map<EnemyTypes, Integer> target,
        int maxThreat,
        double speedMult, double dmgMult, boolean instantDeath,
        EnemyRegistrar registrar,
        AtomicInteger spawnedGhosts, AtomicInteger spawnedZombies, AtomicInteger spawnedPumpkins)

// After
private static double spawnByTarget(
        List<CharacterType> all,
        Map<EnemyTypes, Integer> target,
        Map<EnemyTypes, Integer> caps,          // ← new
        int maxThreat,
        double speedMult, double dmgMult, boolean instantDeath,
        EnemyRegistrar registrar,
        AtomicInteger spawnedGhosts, AtomicInteger spawnedZombies, AtomicInteger spawnedPumpkins)
```

**Call site** (line 154) — pass `caps`:

```java
double threatSum = spawnByTarget(
    characterList, target,
    caps,                       // ← new
    maxThreatByDifficulty,
    speedMultiplierByDifficulty, dmgMultiplierByDifficulty, instantDeath,
    registrar,
    noOfGhostsSpawned, noOfZombiesSpawned, noOfPumpkinBombersSpawned
);
```

### Step 3 — Apply the cap guard inside the loop (line 227)

```java
// Before
int toSpawn = Math.max(0, e.getValue());

// After
EnemyTypes type = e.getKey();
int requested   = Math.max(0, e.getValue());
int toSpawn     = EnemySpawnPlanner.clampToCapLimit(type, requested, caps);

if (toSpawn < requested) {
    _logger.log(Level.INFO,
        "EnemyMaxCount cap applied: type={0}, requested={1}, capped to={2}",
        new Object[] { type, requested, toSpawn });
}
```

The existing `type` variable declaration at line 226 (`EnemyTypes type = e.getKey();`) is already
there — no duplicate needed.

### Step 4 — libGDX parity (CRR-5)

The libGDX spawner lives in [maze-libgdx/.../runtime/GdxOpponentRuntimeFactory.java](maze-libgdx/src/main/java/main/game/maze/libgdx/runtime/) (or equivalent). Apply the same `clampToCapLimit` call wherever per-type spawn counts are resolved there. Because `EnemySpawnPlanner.clampToCapLimit` is in `maze-common-backend` (or `maze-opponents`), both backends share the same enforcement without code duplication.

### Step 5 — Log message acceptance test

After the fix, starting the game in Easy difficulty must produce a log line similar to:

```
EnemyMaxCount cap applied: type=PUMPKINBOMBER, requested=2, capped to=0
```

(because Easy sets PumpkinBomber max=0 in `difficultiesBasic.xmi`).

---

## Unit test plan

All tests go in:
[main.game.maze.opponents/src/test/java/main/game/maze/opponents/util/EnemySpawnPlannerTest.java](main.game.maze.opponents/src/test/java/main/game/maze/opponents/util/EnemySpawnPlannerTest.java)

No JavaFX dependency; tests run headless in the existing JUnit 5 setup.

### New test cases for `clampToCapLimit`

| Test method | Input | Expected | Covers |
|-------------|-------|----------|--------|
| `clampToCapLimit_requestedBelowCap_unchanged` | type=ZOMBIE, requested=2, cap={ZOMBIE→3} | 2 | Happy path — under cap |
| `clampToCapLimit_requestedEqualsCap_unchanged` | type=ZOMBIE, requested=3, cap={ZOMBIE→3} | 3 | Boundary — at cap |
| `clampToCapLimit_requestedExceedsCap_clamped` | type=ZOMBIE, requested=5, cap={ZOMBIE→3} | 3 | Main enforcement path |
| `clampToCapLimit_zeroCap_returnsZero` | type=GHOST, requested=4, cap={GHOST→0} | 0 | PumpkinBomber=0 case (Easy difficulty) |
| `clampToCapLimit_noCapForType_returnsRequested` | type=GHOST, requested=7, cap={ZOMBIE→3} | 7 | Type absent from caps — no limit |
| `clampToCapLimit_emptyCaps_returnsRequested` | type=ZOMBIE, requested=5, caps=empty | 5 | Null-difficulty fallback path |
| `clampToCapLimit_negativeRequested_returnsZero` | type=ZOMBIE, requested=-1, cap={ZOMBIE→3} | 0 | Defensive — negative input |

### Existing tests that must continue to pass (regression gate)

| Test class | What it covers |
|------------|----------------|
| `EnemySpawnPlannerTest.capsReflectDifficultyEnemyMaxCounts` | caps are correctly read from Difficulty |
| `EnemySpawnPlannerTest.negativeMaxCountsClampToZero` | caps extraction clamps negatives |
| `EnemySpawnPlannerTest.capsForNullDifficultyAreEmpty` | null difficulty produces empty caps |
| `OclConstraintsParityTest` (maze-libgdx) | validateMaxThreat OCL still enforced post-spawn |
| `MaxThreatInvariantTest` (maze-difficulties) | Difficulty.maxThreat ≥ 0 invariant still holds |

### Integration-level verification (manual / log inspection)

Because `spawnByTarget` is private and uses `Platform.runLater`, integration coverage is
done by reading the runtime log rather than asserting in a test. The acceptance criterion
from the feature file is met when:

1. Starting with Easy difficulty produces `EnemyMaxCount cap applied: type=PUMPKINBOMBER`
   (capped to 0) in the game log.
2. The total spawned counts reported at line 165 never exceed the caps from `difficultiesBasic.xmi`.

If a headless integration test is desired later, extract `spawnByTarget` into a dedicated
`SpawnCapEnforcer` class that accepts a `BiConsumer<EnemyTypes, CharacterType>` callback
instead of calling `Platform.runLater` directly — that makes the entire spawn pipeline
testable without a JavaFX runtime.

---

## Files to change

| File | Change |
|------|--------|
| [main.game.maze.opponents/.../util/EnemySpawnPlanner.java](main.game.maze.opponents/src/main/java/main/game/maze/opponents/util/EnemySpawnPlanner.java) | Add `clampToCapLimit()` static method |
| [maze-javafx-backend/.../runtime/opponents/OpponentRuntimeFactory.java](maze-javafx-backend/src/main/java/main/game/maze/runtime/opponents/OpponentRuntimeFactory.java) | Add `caps` param to `spawnByTarget()`; apply `clampToCapLimit` at line 227 |
| [maze-libgdx/.../runtime/\<GdxOpponentFactory\>.java](maze-libgdx/src/main/java/main/game/maze/libgdx/runtime/) | Apply same `clampToCapLimit` at equivalent spawn loop |
| [main.game.maze.opponents/.../util/EnemySpawnPlannerTest.java](main.game.maze.opponents/src/test/java/main/game/maze/opponents/util/EnemySpawnPlannerTest.java) | Add 7 new test cases listed above |

---

## Acceptance criteria (from missing-feature.md F8)

- [ ] Spawning more than `EnemyMaxCount.maxCount` of a given `EnemyTypes` for the current
      difficulty is rejected (capped) at spawn time.
- [ ] A clear `INFO` log message identifies the type, requested count, and effective capped count.
- [ ] All 7 new `clampToCapLimit` unit tests pass.
- [ ] All pre-existing `EnemySpawnPlannerTest`, `OclConstraintsParityTest`, and
      `MaxThreatInvariantTest` tests still pass.
- [ ] Both JavaFX and libGDX backends enforce the cap (CRR-5).
- [ ] Easy difficulty never spawns PumpkinBombers (cap=0 in `difficultiesBasic.xmi`).

---

## RTM update note (WR-4)

When implementing, add rows to the RTM linking:

- Requirement `F8` → `EnemySpawnPlanner.clampToCapLimit`
- Requirement `F8` → `OpponentRuntimeFactory.spawnByTarget` (cap guard)
- Test coverage → `EnemySpawnPlannerTest.clampToCapLimit_*` (7 cases)
