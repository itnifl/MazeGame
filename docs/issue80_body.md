## Summary

When an enemy takes damage, its sprite should visually reflect its current health state. As HP decreases the character should progressively look damaged, bleeding, and tired. When a damage-state sprite is not present on disk, the system must fall back silently to the healthy directional sprite — no crash, no log spam.

---

## Context — ecore model (`opponents.ecore`)

`CharacterType` (abstract base for all enemies) currently carries:

| Attribute | Default | Notes |
|-----------|---------|-------|
| `health` | 100 | max HP |
| `ImageBase` | `/main/game/maze/zombie.png` | idle / base sprite |
| `ImageTurnLeft/Right/Up/Down` | directional paths | used by F1 animation system |
| `animationFrameCount` | 1 | walk-cycle frames |
| `spriteScale` | 1.0 | render scale |

There are **no damage-state image fields** today. The ecore needs one new boolean:

```xml
<!-- proposed addition to CharacterType in opponents.ecore -->
<eStructuralFeatures xsi:type="ecore:EAttribute"
    name="damageStatesEnabled"
    eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EBoolean"
    defaultValueLiteral="false"/>
```

When `damageStatesEnabled = false` (default), all damage-state logic is skipped and the healthy sprite is always shown. Opt-in per `CharacterType` entry in the XMI — no breaking change.

---

## All enemy types from `opponentModel.xmi`

### Zombie (3 variants)

| XMI id | displayName | HP | Behavior | Base image set |
|--------|-------------|-----|----------|----------------|
| `zombie_weak_1` | Classic Zombie | 120 | PATROL | `zombie1-{left,right,up,down}.png` all exist |
| `zombie_angry_1` | Angry Zombie | 60 | AGGRESSIVE | `zombie2-{left,right,up,down}.png` all exist |
| `zombie_weak_2` | Classic Zombie | 120 | WANDER | `zombie3-{left,right,up,down}.png` all exist |

All directional healthy sprites exist. Generic hurt frames `zombie-hurt1..5.png` and `zombie2-hurt1.jpg` exist but are **not** directional damage-state sprites — they are not used by the damage state system.

### Ghost (3 variants)

| XMI id | displayName | HP | Behavior | Base image set |
|--------|-------------|-----|----------|----------------|
| `ghost_weak_1` | Ghost Zombie | 120 | PATROL | `ghost1.png`, `ghost1-left.png`, `ghost1-right.png` (up/down use base) |
| `ghost_medium_1` | Ghost Zombie | 120 | WANDER | `ghost2.png` only — all 4 directions share same image |
| `ghost_hard_1` | Ghost Zombie | 120 | WANDER | `ghost3.png` only — all 4 directions share same image |

### PumpkinBomber (2 active variants)

| XMI id | displayName | HP | Behavior | Base image |
|--------|-------------|-----|----------|------------|
| `pb_normal` | Pumpkin Bomber | 80 | AGGRESSIVE | `pumpkinbomber.png` — single image for all directions |
| `pb_hard` | Pumpkin Bomber Elite | 120 | AGGRESSIVE | `pumpkinbomber.png` — single image for all directions |
| `pb_elite_beam` | | 160 | | **disabled** (`enabled=false`), skip for now |

---

## Fallback-first design — convention-based path derivation

No extra XMI image path attributes are needed. Damage-state sprite paths are **derived by convention** from the existing directional path and resolved at runtime. If the derived path does not resolve to an actual resource, the system falls back to the healthy directional sprite for that direction.

### Derivation rule

Given `ImageTurnRight = /main/game/maze/zombie1-right.png`:

| HP ratio | Damage state | Derived path | Fallback if missing |
|----------|-------------|--------------|---------------------|
| > 75 % | HEALTHY | `zombie1-right.png` | — |
| 51-75 % | DAMAGED | `zombie1-damaged-right.png` | `zombie1-right.png` |
| 26-50 % | BLEEDING | `zombie1-bleeding-right.png` | `zombie1-right.png` |
| <= 25 % | TIRED | `zombie1-tired-right.png` | `zombie1-right.png` |

For images **without a direction suffix** (e.g. `pumpkinbomber.png` or `ghost2.png`):

| Derived path | Fallback |
|--------------|----------|
| `pumpkinbomber-damaged.png` | `pumpkinbomber.png` |
| `pumpkinbomber-bleeding.png` | `pumpkinbomber.png` |
| `pumpkinbomber-tired.png` | `pumpkinbomber.png` |

The derivation logic lives in a new shared utility `DamageStateResolver` in `maze-common-frontend`, analogous to `SpriteAnimationUtil`.

### Derivation algorithm (pseudo-code)

```
deriveStatePath(basePath, state):
  if state == HEALTHY: return basePath
  stem, ext = split(basePath)              // "zombie1-right", ".png"
  knownSuffixes = ["-left", "-right", "-up", "-down"]
  for suffix in knownSuffixes:
    if stem.endsWith(suffix):
      return stem.removeSuffix(suffix) + "-" + state.label + suffix + ext
      // e.g. "zombie1-damaged-right.png"
  // no direction suffix found
  return stem + "-" + state.label + ext   // e.g. "pumpkinbomber-damaged.png"
```

Resolution (both frontends): attempt to load derived path; if resource is null, use the original healthy path for that direction. Log at FINE/DEBUG level only — not at WARNING (would be too noisy when no damage sprites are installed yet).

---

## Damage states

```java
public enum DamageState {
    HEALTHY,   // > 75 %
    DAMAGED,   // 51-75 %
    BLEEDING,  // 26-50 %
    TIRED;     // <= 25 %

    public String label() {
        return name().toLowerCase(Locale.ROOT); // "damaged", "bleeding", "tired"
    }

    public static DamageState of(int currentHp, int maxHp) {
        if (maxHp <= 0) return HEALTHY;
        double ratio = (double) currentHp / maxHp;
        if (ratio > 0.75) return HEALTHY;
        if (ratio > 0.50) return DAMAGED;
        if (ratio > 0.25) return BLEEDING;
        return TIRED;
    }
}
```

---

## Required new sprite assets (all currently missing)

The feature degrades gracefully — enabling `damageStatesEnabled=true` in the XMI before all assets exist will simply show the healthy sprite for any missing state. Assets can be added incrementally.

### Zombie damage states (36 files total)

| Files | Variant | States |
|-------|---------|--------|
| `zombie1-damaged-{left,right,up,down}.png` (4) | zombie_weak_1 | damaged |
| `zombie1-bleeding-{left,right,up,down}.png` (4) | zombie_weak_1 | bleeding |
| `zombie1-tired-{left,right,up,down}.png` (4) | zombie_weak_1 | tired |
| `zombie2-damaged-{left,right,up,down}.png` (4) | zombie_angry_1 | damaged |
| `zombie2-bleeding-{left,right,up,down}.png` (4) | zombie_angry_1 | bleeding |
| `zombie2-tired-{left,right,up,down}.png` (4) | zombie_angry_1 | tired |
| `zombie3-damaged-{left,right,up,down}.png` (4) | zombie_weak_2 | damaged |
| `zombie3-bleeding-{left,right,up,down}.png` (4) | zombie_weak_2 | bleeding |
| `zombie3-tired-{left,right,up,down}.png` (4) | zombie_weak_2 | tired |

### Ghost damage states (15 files total)

| Files | Variant | Notes |
|-------|---------|-------|
| `ghost1-damaged-left.png`, `ghost1-damaged-right.png`, `ghost1-damaged.png` (3) | ghost_weak_1 | left/right + base (used for up/down) |
| `ghost1-bleeding-left.png`, `ghost1-bleeding-right.png`, `ghost1-bleeding.png` (3) | ghost_weak_1 | |
| `ghost1-tired-left.png`, `ghost1-tired-right.png`, `ghost1-tired.png` (3) | ghost_weak_1 | |
| `ghost2-damaged.png`, `ghost2-bleeding.png`, `ghost2-tired.png` (3) | ghost_medium_1 | no directional variants |
| `ghost3-damaged.png`, `ghost3-bleeding.png`, `ghost3-tired.png` (3) | ghost_hard_1 | no directional variants |

### PumpkinBomber damage states (6 files total)

| Files | Notes |
|-------|-------|
| `pumpkinbomber-damaged.png`, `pumpkinbomber-bleeding.png`, `pumpkinbomber-tired.png` (3) | shared by pb_normal and pb_hard |
| `pumpkinbomber-elite-damaged.png`, `pumpkinbomber-elite-bleeding.png`, `pumpkinbomber-elite-tired.png` (3) | optional distinct look for pb_hard |

---

## Required ecore change

Edit `opponents.ecore` — add one attribute to `CharacterType`:

```xml
<eStructuralFeatures xsi:type="ecore:EAttribute"
    name="damageStatesEnabled"
    eType="ecore:EDataType http://www.eclipse.org/emf/2002/Ecore#//EBoolean"
    defaultValueLiteral="false"/>
```

Regenerate EMF code (`CharacterType.java` + `CharacterTypeImpl.java`). No XMI changes until sprites exist — set `damageStatesEnabled="true"` on a `characterTypes` entry only once its damage sprites are present.

---

## Implementation plan

### 1. `maze-common-frontend` — shared logic
- Add `DamageState` enum with `DamageState.of(currentHp, maxHp)`
- Add `DamageStateResolver.deriveStatePath(basePath, state)` — convention-based path derivation
- Add `DamageStateResolver.resolveWithFallback(anchor, basePath, state)` — attempts derived path, returns `basePath` if resource not found (logs at FINE only)
- Unit tests: `DamageStateTest` (threshold boundaries at 0, 25, 50, 75, 100 %), `DamageStateResolverTest` (path derivation for direction-suffixed and non-suffixed paths, fallback on missing resource)

### 2. `main.game.maze.opponents` — ecore + EMF codegen
- Add `damageStatesEnabled` boolean (default false) to `CharacterType` in `opponents.ecore`
- Regenerate `CharacterType.java`, `CharacterTypeImpl.java`, `OpponentsPackage.java`

### 3. JavaFX — `ComputerCharacter`
- On every HP change: compute `DamageState.of(currentHp, maxHp)`, call `DamageStateResolver.resolveWithFallback` for the current direction, update the displayed image via `Platform.runLater()`
- Guard with `characterModel.isDamageStatesEnabled()` — if false, no path derivation
- Works alongside existing walk-cycle `Timeline`: damage state is re-resolved on each frame advance when `animationFrames != null`

### 4. libGDX — `GdxEnemyRuntime` + `GdxGameRenderPipeline`
- `GdxEnemyRuntime` already tracks HP; extend `currentFramePath(clock)` to also apply `DamageStateResolver` based on current HP ratio
- `EnemyAnimationSpec` gains a `damageStatesEnabled` flag passed through from `RuntimeVisualModelLoader` reading `CharacterType.isDamageStatesEnabled()`
- Asset preloading in `MenuScreenController.queueMenuAndGameplayAssets` should attempt to queue damage-state paths, skipping silently if the resource is absent

### 5. Asset tracking
- Update `missing-sprite-asset-list.md` with the damage-state asset tables above

---

## Acceptance criteria

- [ ] `DamageState.of(hp, maxHp)` returns the correct tier at every boundary (0, 25, 50, 75, 100 %)
- [ ] `DamageStateResolver.deriveStatePath` correctly handles paths with and without direction suffixes
- [ ] Missing derived sprite resource => healthy directional sprite is displayed; **no exception, no WARNING log**
- [ ] `damageStatesEnabled = false` (default) => behaviour identical to today — no regression
- [ ] `damageStatesEnabled = true` with all assets missing => healthy sprite shown, game does not crash
- [ ] `damageStatesEnabled = true` with assets present => correct damage-state sprite per HP tier and direction
- [ ] Implemented in both JavaFX and libGDX frontends
- [ ] Ecore updated, EMF code regenerated, existing OCL constraints still pass
- [ ] Unit tests for `DamageState`, `DamageStateResolver`, and both frontend integrations
- [ ] RTM updated with new requirement row
- [ ] `missing-sprite-asset-list.md` updated with damage-state asset tables
