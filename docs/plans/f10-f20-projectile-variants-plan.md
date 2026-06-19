# Implementation Plan — F20 & F10. Projectile Variants (Straight, Lob, Beam)

**Status:** PLANNED
**ID:** `F10`, `F20`
**Source:** `opponents.ecore` / `MazeDsl.xtext` — `ProjectileType {STRAIGHT, LOB, BEAM}`
**Backend:** both
**Target:** Game physics/combat engine
**Last updated:** 2026-06-16

---

## 1. Why this plan exists

Plan for **F10/F20. Ranged enemy projectile variants**. The model defines multiple projectile flight patterns and effects (`STRAIGHT`, `LOB`, `BEAM`) with parameters like `splashRadius` and `arcHeight`. Currently, only one hardcoded straight-line projectile logic exists.

### Goals
- Implement physics and rendering logic for three distinct projectile types.

### Detailed Approach
1. **Model Propagation**: Update `EnemySpawn` to include `projectileType`, `splashRadius`, and `arcHeight`.
2. **Physics Engine**:
   - `STRAIGHT`: Move directly along the vector. Hits first target (player or wall).
   - `LOB`: Calculate a parabolic arc using `arcHeight`. Ignore wall collisions mid-flight. Explode at target coordinates applying `splashRadius` AoE damage.
   - `BEAM`: Instantaneous raycast. Draw a line from enemy to player/wall. Apply damage instantly.
3. **Rendering**:
   - Update JavaFX/libGDX render pipelines to draw shadows/arcs for `LOB`, and laser lines for `BEAM`.
4. **Testing**: Add mock test environments to verify that `LOB` projectiles bypass intermediate walls and calculate correct splash damage, while `STRAIGHT` impacts walls.