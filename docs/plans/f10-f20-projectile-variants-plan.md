# Implementation Plan — F20 & F10. Projectile Variants (Straight, Lob, Beam)

**Status:** IMPLEMENTED
**ID:** `F10`, `F20`
**Source:** `opponents.ecore` / `MazeDsl.xtext` — `ProjectileType {STRAIGHT, LOB, BEAM}`
**Backend:** both
**Target:** Game physics/combat engine
**Last updated:** 2026-06-19

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

## 2. Implementation outcome

Completed in both backends.

1. **Model propagation**
   - `EnemySpawn` in libGDX now carries projectile configuration needed at runtime:
     `projectileType`, `splashRadius`, `arcHeight`, `attackRange`, `attackCooldownMs`, `projectileSpeed`.
   - `RuntimeVisualModelLoader` now propagates ranged values from `PumpkinBomber` model entries into each runtime spawn.

2. **Physics and combat**
   - `STRAIGHT`: projectile advances linearly, checks wall crossing per frame, and stops on first blocking wall or first target hit.
   - `LOB`: projectile follows an arc profile, ignores wall blocking while airborne, and applies splash damage at impact.
   - `BEAM`: beam resolves instantly, applies immediate damage when line of fire is clear, and records a short lived beam visual.

3. **Rendering**
   - JavaFX: projectile nodes are attached to the active scene; lob arcs and beam flashes are rendered in scene space.
   - libGDX: world renderer now draws active projectile markers (including lob shadow) and beam lines from runtime visual snapshots.

4. **Verification tests**
   - Added `GdxEnemyRuntimeProjectileTest` covering:
     - straight projectile blocked by wall,
     - lob projectile ignoring intermediate wall and applying splash,
     - beam immediate damage plus beam visual emission.