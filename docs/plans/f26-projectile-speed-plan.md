# Implementation Plan — F26. Ranged Projectile Speed

**Status:** PLANNED
**ID:** `F26`
**Source:** `opponents.ecore` — `RangedEnemy.projectileSpeed`
**Backend:** both
**Target:** Shared backend, JavaFX, libGDX
**Last updated:** 2026-06-16

---

## 1. Why this plan exists

Plan for **F26. Ranged projectile speed**. `RangedEnemy` types (like PumpkinBomber) define a `projectileSpeed` attribute. Currently, projectile flight speed is hard-coded in the combat services.

### Goals
- Plumb `projectileSpeed` from the model through to the projectile physics controllers.

### Detailed Approach
1. **Model Propagation**: Update `EnemySpawn` to extract `projectileSpeed` from the `PumpkinBomber` EMF instance.
2. **Physics Adjustment**: In the logic handling projectile updates (either `PumpkinBomberCharacter` in FX or `GdxGameCombatAndEnemyFlowSupport` / projectile entities in libGDX), replace the hardcoded movement delta with `dt * projectileSpeed`.
3. **Testing**: Write unit tests simulating 1 second of projectile flight and assert that the distance traveled matches exactly the configured `projectileSpeed`.