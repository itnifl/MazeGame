# Missing Sprite Asset List

This file tracks animation frames and sprite assets that are declared or desired
but not yet present in `maze-common-frontend/src/main/resources/main/game/maze/`.

---

## Status key

| Symbol | Meaning |
|--------|---------|
| ✅ | Asset exists |
| ❌ | Asset missing — placeholder needed |
| 🚧 | Planned but not yet designed |

---

## Zombie character variants (NOT walk-cycle frames)

`zombie1-*.png`, `zombie2-*.png`, and `zombie3-*.png` are three **distinct character
designs** (Classic Zombie, Angry Zombie, Classic Zombie variant), each used by a
separate `CharacterType` entry in `opponentModel.xmi`. They are **not** walk-cycle
frames of the same zombie.

| Variant | Right | Left | Up | Down |
|---------|-------|------|----|------|
| zombie1 | ✅ zombie1-right.png | ✅ zombie1-left.png | ✅ zombie1-up.png | ✅ zombie1-down.png |
| zombie2 | ✅ zombie2-right.png | ✅ zombie2-left.png | ✅ zombie2-up.png | ✅ zombie2-down.png |
| zombie3 | ✅ zombie3-right.png | ✅ zombie3-left.png | ✅ zombie3-up.png | ✅ zombie3-down.png |

`animationFrameCount` is **1** (default) for all zombie entries — no walk animation
is active yet. ❌

### To enable zombie walk animation

Dedicated per-variant walk-cycle frames must be created. For example, for zombie_weak_1:
- `zombie_classic_walk1-right.png`, `zombie_classic_walk2-right.png`, `zombie_classic_walk3-right.png`
- (+ left, up, down variants)

These would need a new naming convention distinct from the variant numbers to avoid
ambiguity, and the XMI `ImageTurnRight` path updated to point to frame 1 of the
new walk cycle before `animationFrameCount` can be set above 1.

---

## Zombie hurt / death frames

| Asset | Status |
|-------|--------|
| zombie-hurt1.png | ✅ |
| zombie-hurt2.png | ✅ |
| zombie-hurt3.png | ✅ |
| zombie-hurt4.png | ✅ |
| zombie-hurt5.png | ✅ |
| zombie2-hurt1.jpg | ✅ (note: JPG — consider converting to PNG for consistency) |

---

## Ghost walk cycle

Ghosts currently have 3 base images (`ghost1/2/3.png`) representing three
**character variants**, not walk-cycle frames within one variant.
The XMI has `animationFrameCount` at the default (1) for all ghost entries —
no walk animation is active yet.

| Asset | Status | Notes |
|-------|--------|-------|
| ghost1.png | ✅ | Used as base/up/down for ghost\_weak\_1 |
| ghost1-left.png | ✅ | |
| ghost1-right.png | ✅ | |
| ghost2.png | ✅ | ghost\_medium\_1 base |
| ghost2-left.png | ❌ | Needed for directional walk on ghost\_medium\_1 |
| ghost2-right.png | ❌ | |
| ghost2-up.png | ❌ | |
| ghost2-down.png | ❌ | |
| ghost3.png | ✅ | ghost\_hard\_1 base |
| ghost3-left.png | ❌ | Needed for directional walk on ghost\_hard\_1 |
| ghost3-right.png | ❌ | |
| ghost3-up.png | ❌ | |
| ghost3-down.png | ❌ | |

**To unlock ghost walk animation:** create the 8 missing directional PNG files
and set `animationFrameCount="3"` for the ghost entries in `opponentModel.xmi`.

---

## PumpkinBomber walk cycle

| Asset | Status | Notes |
|-------|--------|-------|
| pumpkinbomber.png | ✅ | Single-frame (no animation) |
| pumpkinbomber-walk1-right.png | 🚧 | Future: full walk cycle |
| pumpkinbomber-walk2-right.png | 🚧 | |
| pumpkinbomber-walk3-right.png | 🚧 | |
| (+ left, up, down variants) | 🚧 | |

---

## Player walk cycle

Player sprites are in the player config (`PlayerConfig`), not in the opponent model.
Walk animation for the player is not yet implemented.

| Asset | Status | Notes |
|-------|--------|-------|
| player-walk1-right.png | 🚧 | Future: player walk animation |
| player-walk2-right.png | 🚧 | |
| player-walk3-right.png | 🚧 | |
| (+ left, up, down variants) | 🚧 | |

---

## Projectile / explosion assets

| Asset | Status |
|-------|--------|
| pumpkin\_lob\_projectile.png | ✅ |
| pumpkin\_straight\_projectile.png | ✅ |
| pumpkin-explode.png | ✅ |

---

## Background / environment

| Asset | Status |
|-------|--------|
| woodWall.png | ✅ |
| zombieBackground.png | ✅ |
| zombieGameOverBackground1.png | ✅ |
| zombieGameOverBackground2.png | ✅ |

---

*Last updated: 2026-06-23 as part of F1 Animated Sprites implementation.*
