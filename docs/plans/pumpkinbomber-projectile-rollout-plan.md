# PumpkinBomber Projectile Rollout Plan

**Status:** IN PROGRESS — Phase 1 complete, Phase 2 data model in place (disabled), telemetry pending
**Scope:** JavaFX and libGDX gameplay behavior and visuals
**Date:** 2026-06-19
**Updated:** 2026-06-23

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

| # | Item | Status |
|---|---|---|
| 4.1 | Three PumpkinBomber profiles in opponent model data | **DONE** |
| 4.2 | Profile A: `projectileType=STRAIGHT`, low damage, short cooldown (`pb_hard`) | **DONE** |
| 4.3 | Profile B: `projectileType=LOB`, medium damage, medium cooldown, meaningful splash radius (`pb_normal`) | **DONE** |
| 4.4 | Profile C: `projectileType=BEAM`, high readability beam effect, longer cooldown (`pb_elite_beam`, disabled) | **DONE** |
| 4.5 | Per difficulty cap and threat budgets gate spawn counts | **DONE** |

> **Note:** `pb_elite_beam` profile is defined with `enabled=false`. Enable it for Phase 2 after balancing.

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

| Item | Status |
|---|---|
| Same model parameters produce same damage outcomes in JavaFX and libGDX | **DONE** |
| Beam line lifetime is visually comparable across both frontends | **DONE** |
| Lob impact location and splash radius feel equivalent | **DONE** |
| Wall blocking behavior strict for `STRAIGHT` and beam line of sight | **DONE** |

## 7. Implementation tasks

| # | Task | Status |
|---|---|---|
| 7.1 | Per profile spawn fixtures in test data for both frontends | **DONE** |
| 7.2 | Integration test per projectile type — JavaFX side (3 types × multiple cases) | **DONE** |
| 7.3 | Rendering-focused assertion per projectile type — libGDX pipeline tests | **DONE** |
| 7.4 | Edge case tests: wall blocking, multi-target splash, cooldown boundary, range boundary, zero-dt | **DONE** |
| 7.5 | Manual playtest script for F10 and F20 in `manual-test-plan-missing-features.md` | **PARTIAL** (smoke pass exists; BEAM Phase 2 script pending) |

### JavaFX edge cases added (2026-06-23)

- `straightProjectile_wallBetween_doesNotDamagePlayer` — STRAIGHT wall block verified via damage assertion (not just no-throw)
- `lobProjectile_splashDamagesAllSubscribersWithinRadius` — multi-target splash
- `straightProjectile_respectsCooldown` — cooldown contract for STRAIGHT type
- `beamProjectile_targetAtExactlyRange_doesNotFire` — boundary: target at exactly `attackRange` must not fire (strict `>` check)
- `beamProjectile_targetJustInsideRange_fires` — boundary: target 1px inside range fires correctly
- `updateProjectiles_withZeroDt_doesNotAdvanceProjectile` — zero dt must not resolve projectile

### libGDX edge cases added (2026-06-23)

- `beamBlockedByWall_beamVisualShowsBlockedTrue` — `BeamVisual.blocked()` is true when wall intercepts
- `beamBlockedByWall_dealsNoDamage` — BEAM blocked by wall returns 0 damage
- `straightProjectile_hitsPlayerWithNoWall` — STRAIGHT deals damage when path is clear
- `lobProjectile_withZeroArcHeight_stillLandsAndDamages` — flat LOB still lands and splashes
- `shotCooldown_preventsSecondShotWithinPeriod` — second BEAM within cooldown deals no damage
- `impactVisual_hasPositiveRadiusAndAlpha_immediatelyAfterImpact` — impact visual structure validated
- `lobProjectile_firesAgainAfterCooldown` — LOB re-fires after cooldown elapses

## 8. Telemetry and debugging support

| Item | Status |
|---|---|
| Debug event logging for each ranged attack (enemy id, projectile type) | **NOT DONE** |
| Optional HUD debug counter for projectile hits and misses | **NOT DONE** |
| Beam blocked reason and wall blocked reason logging when debug mode enabled | **NOT DONE** |

> **Next action:** Wire `Logger` (or `System.err` behind a debug flag) into `tryShootAt()` and `updateRangedAttacks()`.

## 9. Release sequence

| Phase | Scope | Status |
|---|---|---|
| Phase 1 | Ship PumpkinBomber with `STRAIGHT` and `LOB` only | **COMPLETE** |
| Phase 2 | Enable `BEAM` for `pb_elite_beam` profile after balancing | **READY** (profile defined, set `enabled=true` to ship) |
| Phase 3 | Reuse shared projectile strategy for other ranged enemy classes | **FUTURE** |

## 10. Acceptance criteria

| Criterion | Status |
|---|---|
| Full Maven root `test` succeeds under Java 21 | Verified per PR pipeline |
| Manual parity checks pass in both frontends for F10 and F20 | Smoke pass defined |
| No severe readability regressions in combat encounters | No regressions observed |
| No wall interaction regressions for non-projectile melee contact damage | Confirmed by existing tests |

## 11. Fast visual validation

1. Run the rapid smoke pass in `docs/plans/manual-test-plan-missing-features.md` section `7.1` before each balancing session.
2. Capture one short clip per mode from JavaFX and libGDX when changing visuals or timing values.
3. Only proceed with broader balancing if all three projectile modes pass parity in the rapid smoke pass.
