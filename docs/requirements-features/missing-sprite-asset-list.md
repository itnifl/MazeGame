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
| ghost2-left.png | ✅ | Generated (tools/sprite-gen/GhostDirectionalSpriteGenerator.java) |
| ghost2-right.png | ✅ | Generated (tools/sprite-gen/GhostDirectionalSpriteGenerator.java) |
| ghost2-up.png | ✅ | Generated (tools/sprite-gen/GhostDirectionalSpriteGenerator.java) |
| ghost2-down.png | ✅ | Generated (tools/sprite-gen/GhostDirectionalSpriteGenerator.java) |
| ghost3.png | ✅ | ghost\_hard\_1 base |
| ghost3-left.png | ✅ | Generated (tools/sprite-gen/GhostDirectionalSpriteGenerator.java) |
| ghost3-right.png | ✅ | Generated (tools/sprite-gen/GhostDirectionalSpriteGenerator.java) |
| ghost3-up.png | ✅ | Generated (tools/sprite-gen/GhostDirectionalSpriteGenerator.java) |
| ghost3-down.png | ✅ | Generated (tools/sprite-gen/GhostDirectionalSpriteGenerator.java) |

All directional ghost sprites now exist and `opponentModel.xmi` points
`ImageTurnLeft/Right/Up/Down` of ghost\_medium\_1 / ghost\_hard\_1 at them.

**Ghost walk animation is still not possible:** `SpriteAnimationUtil` replaces the first
digit in the file name with the frame number, and the digit in `ghostN` is the *variant*,
not a frame. Setting `animationFrameCount="3"` would cycle between the three different
ghost designs (the same flicker seen with zombies). Real walk frames need their own
naming, e.g. `ghost_medium_walk1-left.png` (SR-219).

---

## PumpkinBomber walk cycle

| Asset | Status | Notes |
|-------|--------|-------|
| pumpkinbomber.png | ✅ | Single-frame (no animation); used for right and down |
| pumpkinbomber-left.png | ✅ | Generated mirror (`PumpkinBomberDirectionalSpriteGenerator`) |
| pumpkinbomber-up.png | ✅ | Generated brightened tint (same generator) |
| pumpkinbomber-walk1-right.png | 🚧 | Future: full walk cycle |
| pumpkinbomber-walk2-right.png | 🚧 | |
| pumpkinbomber-walk3-right.png | 🚧 | |
| (+ left, up, down variants) | 🚧 | |

---

## Enemy damage-state sprites (issue #80)

Paths follow `DamageStateResolver.deriveStatePath` (`zombie1-right.png` →
`zombie1-damaged-right.png`, `ghost2.png` → `ghost2-damaged.png`). All 84 files
are **generated**: 78 from the healthy sprites by
[tools/sprite-gen/DamageSpriteGenerator.java](../../tools/sprite-gen/DamageSpriteGenerator.java)
(scratches → blood → desaturated slump), and the 6 bomber left/up damage sprites by
[tools/sprite-gen/PumpkinBomberDirectionalSpriteGenerator.java](../../tools/sprite-gen/PumpkinBomberDirectionalSpriteGenerator.java)
(mirror / tint of the bomber damage sprites, so the marks match). Replace any of them with hand-drawn art
under the same name at any time. `DamageStateSpriteAssetsTest` verifies all of them.

| Variant | Damaged | Bleeding | Tired |
|---------|---------|----------|-------|
| zombie1 | ✅ zombie1-damaged-{left,right,up,down}.png | ✅ zombie1-bleeding-{left,right,up,down}.png | ✅ zombie1-tired-{left,right,up,down}.png |
| zombie2 | ✅ zombie2-damaged-{left,right,up,down}.png | ✅ zombie2-bleeding-{left,right,up,down}.png | ✅ zombie2-tired-{left,right,up,down}.png |
| zombie3 | ✅ zombie3-damaged-{left,right,up,down}.png | ✅ zombie3-bleeding-{left,right,up,down}.png | ✅ zombie3-tired-{left,right,up,down}.png |
| ghost1 | ✅ ghost1-damaged{,-left,-right}.png | ✅ ghost1-bleeding{,-left,-right}.png | ✅ ghost1-tired{,-left,-right}.png |
| ghost2 | ✅ ghost2-damaged{,-left,-right,-up,-down}.png | ✅ ghost2-bleeding{,-left,-right,-up,-down}.png | ✅ ghost2-tired{,-left,-right,-up,-down}.png |
| ghost3 | ✅ ghost3-damaged{,-left,-right,-up,-down}.png | ✅ ghost3-bleeding{,-left,-right,-up,-down}.png | ✅ ghost3-tired{,-left,-right,-up,-down}.png |
| pumpkinbomber | ✅ pumpkinbomber-damaged{,-left,-up}.png | ✅ pumpkinbomber-bleeding{,-left,-up}.png | ✅ pumpkinbomber-tired{,-left,-up}.png |
| pumpkinbomber-elite | 🚧 optional | 🚧 optional | 🚧 optional |

`pb_hard` uses the same bomber sprites, so the resolver derives the `pumpkinbomber-*`
names for it; the `pumpkinbomber-elite-*` set is only needed if the elite gets its
own base image. Damage states are **enabled** (`damageStatesEnabled=true`) for every
zombie, ghost and pumpkin bomber in `opponentModel.xmi`, in both frontends.
`RuntimeVisualModelLoaderTest.shippedEnemiesHaveDamageStatesWithAssets` fails if an
enabled enemy resolves to a missing damage sprite.

---

## Enemy death remains (SR-229)

Generated by [tools/sprite-gen/RemainsSpriteGenerator.java](../../tools/sprite-gen/RemainsSpriteGenerator.java)
into `main/game/maze/remains/` (256x256, transparent). Picked by `RemainsSpriteResolver`;
`RemainsSpriteResolverTest.shippedSpritesExist` fails if any is missing.

| Cause | Files | Notes |
|-------|-------|-------|
| Fire / bomb | ✅ `burned-{1,2,3}.png` | coal pile with glowing cracks and ash; shared by all enemies, picked by position |
| Shot | ✅ `{zombie1,zombie2,zombie3,ghost1,ghost2,ghost3,pumpkinbomber}-shot.png` | bleeding sprite lying on its side in a blood pool, chunks torn out |
| Crushed | ✅ `{zombie1,zombie2,zombie3,ghost1,ghost2,ghost3,pumpkinbomber}-crushed.png` | flattened body on a blood splat with gore |
| Fallback | ✅ `generic-shot.png`, `generic-crushed.png` | blood only, for enemies without their own remains |

Collapsing-wall rubble (SR-230) is drawn procedurally (`WallRubbleModel`) and needs no sprite files.

---

## Pickups (SR-239)

Generated by [tools/sprite-gen/PickupSpriteGenerator.java](../../tools/sprite-gen/PickupSpriteGenerator.java)
into `main/game/maze/pickups/` (64x64, transparent). Paths come from `items.xmi`;
`PickupsTest.catalogueFromModel` fails if a sprite or sound named there is missing.

| Item | File | Notes |
|------|------|-------|
| Bomb | ✅ `bomb.png` | black bomb with a lit fuse |
| Laser rifle | ✅ `laser-rifle.png` | also drawn in the player's hands while selected |
| Sledgehammer | ✅ `sledgehammer.png` | also drawn in the player's hands while selected |
| Shield cube | ✅ `shield.png` | glowing blue cube |
| Heart | ✅ `heart.png` | small red heart |

The laser beam, sledgehammer swing, hit reactions, pickup sparkle and shield bubble are drawn
procedurally (`common.graphics.fire` puff effects) and need no sprite files.

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

*Last updated: 2026-11-20 — pickup sprites generated (SR-239).*
