# Projectile Sprite Asset List

**Purpose:** Collect all art and FX assets needed for polished PumpkinBomber projectile gameplay.
**Date:** 2026-06-19

## 1. Naming convention suggestion

1. Use snake case and keep backend neutral paths.
2. Runtime root path for drop in assets: `/main/game/maze/projectiles/pumpkinbomber/`
3. Files placed there are auto picked by JavaFX projectile loading with no code changes.

## 1.1 Drop in file mapping now active

1. `STRAIGHT` uses `/main/game/maze/projectiles/pumpkinbomber/pumpkin_straight_projectile.png`
2. `LOB` uses `/main/game/maze/projectiles/pumpkinbomber/pumpkin_lob_projectile.png`
3. `BEAM` uses `/main/game/maze/projectiles/pumpkinbomber/pumpkin_beam_core_segment.png`
4. If these are missing, runtime falls back to generated placeholder circles.

## 2. Straight projectile assets

1. `pumpkin_straight_projectile.png`
2. `pumpkin_straight_trail_01.png`
3. `pumpkin_straight_trail_02.png`
4. `pumpkin_straight_impact_01.png`
5. `pumpkin_straight_impact_02.png`
6. `pumpkin_straight_impact_03.png`

## 3. Lob projectile assets

1. `pumpkin_lob_projectile.png`
2. `pumpkin_lob_spin_01.png`
3. `pumpkin_lob_spin_02.png`
4. `pumpkin_lob_spin_03.png`
5. `pumpkin_lob_shadow_soft.png`
6. `pumpkin_lob_land_dust_01.png`
7. `pumpkin_lob_land_dust_02.png`
8. `pumpkin_lob_land_dust_03.png`
9. `pumpkin_lob_explosion_core_01.png`
10. `pumpkin_lob_explosion_ring_01.png`

## 4. Beam assets

1. `pumpkin_beam_charge_01.png`
2. `pumpkin_beam_charge_02.png`
3. `pumpkin_beam_core_segment.png`
4. `pumpkin_beam_glow_segment.png`
5. `pumpkin_beam_hit_01.png`
6. `pumpkin_beam_hit_02.png`
7. `pumpkin_beam_hit_03.png`
8. `pumpkin_beam_blocked_spark_01.png`
9. `pumpkin_beam_blocked_spark_02.png`

## 5. Shared combat FX assets

1. `pumpkin_muzzle_flash_01.png`
2. `pumpkin_muzzle_flash_02.png`
3. `pumpkin_smoke_puff_01.png`
4. `pumpkin_smoke_puff_02.png`
5. `pumpkin_smoke_puff_03.png`
6. `pumpkin_ground_burn_mark.png`

## 6. Optional animation strips

1. `pumpkin_lob_spin_strip_8f.png`
2. `pumpkin_lob_explosion_strip_12f.png`
3. `pumpkin_beam_charge_strip_6f.png`
4. `pumpkin_beam_hit_strip_8f.png`

## 7. Suggested technical specs

1. Projectiles: 32x32 or 48x48.
2. Explosions: 96x96 or 128x128.
3. Beam segments: power of two widths for smooth tiling.
4. Keep transparent padding tight to reduce overdraw.
5. Export as PNG with premultiplied alpha friendly edges.

## 8. Audio companion checklist

1. `pumpkin_throw.wav`
2. `pumpkin_lob_whistle.wav`
3. `pumpkin_explosion.wav`
4. `pumpkin_beam_charge.wav`
5. `pumpkin_beam_fire.wav`
6. `pumpkin_beam_blocked.wav`

## 8.1 Runtime mapping and current placeholder

1. Opponent model `explosionSound` now points to `/main/game/maze/error.wav` as a temporary placeholder that already exists.
2. Final target for PumpkinBomber explosion should be `/main/game/maze/pumpkin-pop.wav`.
3. Place the final file in `maze-common-frontend/src/main/resources/main/game/maze/pumpkin-pop.wav`.
4. After placing it, update `opponentModel.xmi` entries from `/main/game/maze/error.wav` to `/main/game/maze/pumpkin-pop.wav`.
5. Keep duration short, about 150ms to 350ms, so repeated lob impacts stay readable in combat.

## 9. Placement checklist for you

1. Put final sprites in `maze-common-frontend/src/main/resources/main/game/maze/projectiles/pumpkinbomber/`.
2. Put final projectile sound in `maze-common-frontend/src/main/resources/main/game/maze/`.
3. Keep old file names until code paths are switched in one dedicated commit.
4. Add a quick visual smoke test scene for each projectile mode after placement.
5. Record final file mapping in module readme after import.
