# PumpkinBomber Projectile Rollout Plan

**Status:** READY FOR EXECUTION
**Scope:** JavaFX and libGDX gameplay behavior and visuals
**Date:** 2026-06-19

## 1. Goal

Make PumpkinBomber ranged combat feel intentional in live gameplay, not only technically present.

## 2. Current baseline

1. `STRAIGHT`, `LOB`, and `BEAM` behaviors are implemented in runtime code.
2. Full solution tests pass under Java 21.
3. Basic projectile and beam visuals are in place.

## 3. Recommended gameplay first implementation path

1. Keep PumpkinBomber as the first and only ranged enemy for this iteration.
2. Make `LOB` the default PumpkinBomber projectile mode for most encounters.
3. Reserve `BEAM` for elite variants or late game scenarios.
4. Use `STRAIGHT` for tutorial style readability and early difficulty.

## 4. Data model tuning pass

1. Create three PumpkinBomber profiles in opponent model data.
2. Profile A: `projectileType=STRAIGHT`, low damage, short cooldown.
3. Profile B: `projectileType=LOB`, medium damage, medium cooldown, meaningful splash radius.
4. Profile C: `projectileType=BEAM`, high readability beam effect, longer cooldown.
5. Ensure per difficulty cap and threat budgets still gate spawn counts.

## 5. Per mode balancing targets

1. `STRAIGHT`
2. Player can sidestep at medium distance.
3. Walls reliably protect player.

4. `LOB`
5. Punishes static hiding behind walls.
6. Splash should reward movement, not produce unavoidable damage.

7. `BEAM`
8. Telegraph with short pre fire visual, then instant hit.
9. Keep cooldown high enough to avoid oppressive loops.

## 6. Frontend parity checklist

1. Same model parameters must produce same damage outcomes in JavaFX and libGDX.
2. Beam line lifetime should be visually comparable across both frontends.
3. Lob impact location and splash radius should feel equivalent.
4. Wall blocking behavior must stay strict for `STRAIGHT` and beam line of sight.

## 7. Implementation tasks

1. Add per profile spawn fixtures in test data for both frontends.
2. Add one integration test per projectile type for JavaFX side behavior contracts.
3. Add one rendering focused assertion per projectile type in libGDX pipeline tests.
4. Add manual playtest script for F10 and F20 based on `manual-test-plan-missing-features.md`.

## 8. Telemetry and debugging support

1. Add debug event logging for each ranged attack with enemy id and projectile type.
2. Add optional HUD debug counter for projectile hits and misses.
3. Add optional beam blocked reason and wall blocked reason logging when debug mode is enabled.

## 9. Release sequence

1. Phase 1: Ship PumpkinBomber with `STRAIGHT` and `LOB` only.
2. Phase 2: Enable `BEAM` for elite profile after balancing.
3. Phase 3: Reuse shared projectile strategy for other ranged enemy classes.

## 10. Acceptance criteria

1. Full Maven root `test` succeeds under Java 21.
2. Manual parity checks pass in both frontends for F10 and F20 scenarios.
3. No severe readability regressions in combat encounters.
4. No wall interaction regressions for non projectile melee contact damage.
