# Implementation Plan — F24. Zombie Resurrection Time

**Status:** PLANNED
**ID:** `F24`
**Source:** `opponents.ecore` — `Zombie.resurrectionTime`; `MazeDsl.xtext` — `ZombieSpecifics.resurrectionTime`
**Backend:** both (JavaFX and libGDX)
**Target:** Shared backend, JavaFX, libGDX
**Last updated:** 2026-06-16

---

## 1. Why this plan exists

Plan for **F24. Zombie resurrection time**. Currently, when a zombie dies, it is permanently removed from the active enemy list. The metamodel dictates that zombies can have a `resurrectionTime` (>0). If set, the zombie should "respawn" after the configured time delay.

### Goals
- Read `resurrectionTime` from the `Zombie` model.
- Keep track of "dead" zombies that have a resurrection timer.
- Respawn them at their original coordinates (or a valid spawn point) after the delay.

### Detailed Approach
1. **Model Propagation**: Update `EnemySpawn` to include `resurrectionTimeMs`.
2. **Game State Management**:
   - Create a `List<DeadEnemy>` in `GameWorldModel` (libGDX) and `FxGameWorldModel` (JavaFX).
   - `DeadEnemy` tracks the `EnemySpawn` configuration and `timeUntilResurrection`.
3. **Combat/Death Logic (`updateCombat`)**:
   - Instead of instantly discarding a dead zombie, check `resurrectionTime`. If > 0, move it to the dead list with the timer initialized.
4. **Respawn Tick**:
   - In the main update loops (`advanceEnemies` or `updateCombat`), iterate over the dead list. Decrement timers by `dt`.
   - If timer <= 0, remove from dead list and re-add to `animatedEnemies` / `allComputerCharacters` at the original spawn coordinates. Add brief invulnerability or a respawn visual effect to prevent immediate player death.
5. **Testing**: Write headless game loop tests where a zombie is killed, time is advanced manually, and the zombie is verified to reappear.