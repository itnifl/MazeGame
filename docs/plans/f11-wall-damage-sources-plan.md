# Implementation Plan — F11 (Extension). Wall Damage Sources

**Status:** SR-99 IMPLEMENTED — remaining items PLANNED
**ID:** `F11-EXT`
**Parent:** `F11` (Breakable Walls — HP tracking, damage, removal)
**Related:** `F10`, `F20` (Projectile Variants), `SR-99` (libGDX parity), `SR-100` (visual damage cues), `SR-101` (domain events), `SR-113` (Bomberman flame attack)
**Backend:** both (JavaFX + libGDX)
**Target:** Combat engine, characters, game loop
**Last updated:** 2026-06-24

---

## 1. Context

F11 introduced the full breakable-wall pipeline:

- `WallMaterialSpec` records (Glass 5 HP, Dirt 10 HP, Wood 20 HP, Stone 40 HP)
- `BreakableWall.applyDamage(int)` → floor-at-zero HP tracking
- `GameMazeWorld.applyWallDamage(bw, damage)` → removal + nav-graph rewire
- `GameController.applyProjectileDamageToWall(wall, damage)` → UI-thread-safe entry point

**Currently the only damage source** is `PumpkinBomberCharacter.updateProjectiles()`, which calls
`applyProjectileDamageToWall` when a straight projectile's AABB intersects a wall segment.

This plan defines all additional damage sources to implement in a follow-on PR (`feature/wallDamageSources`
or similar).

---

## 2. Damage Sources (Prioritised)

### DS-0 — Bomberman flame attack (highest priority)

| Attribute | Detail |
|-----------|--------|
| Who fires | `PlayerCharacter` when the player presses the space bar |
| Charges | Player starts with **3 bombs** |
| Damage | **100 HP** per bomb |
| Mechanic | The bomb resolves as a chained flame hit, then any leftover damage spills to the next enemy in target order. For example, if the first enemy has 70 HP, it dies and the next enemy takes the remaining 30 HP |
| Both frontends | JavaFX: space bar action in `GameController` / `PlayerCharacter`; libGDX: matching space bar command in `GdxGameScreenController` |
| New req | **SR-113** |

### DS-1 — Player weapon projectile

| Attribute | Detail |
|-----------|--------|
| Who fires | `PlayerCharacter` when a shoot action is triggered (key / button TBD) |
| Damage | Configurable per weapon; suggested default **3 HP** per shot |
| Mechanic | Same as PumpkinBomber: straight projectile, AABB vs wall, `applyProjectileDamageToWall` |
| Material impact | Glass (5 HP) shatters in 2 shots; Dirt (10 HP) in 4; Wood (20 HP) in 7; Stone (40 HP) in 14 |
| Both frontends | JavaFX: new `Projectile` in `PlayerCharacter`; libGDX: equivalent in `GdxGameScreenController` input handler |
| New req | **SR-105** |

### DS-2 — Explosion splash from PumpkinBomber (medium priority)

| Attribute | Detail |
|-----------|--------|
| Who fires | `PumpkinBomberCharacter` on projectile arrival / detonation |
| Damage | `p.damage` (same as the current direct-hit damage) |
| Mechanic | On detonation, check all `mazeVectors` within `splashRadius`; call `applyProjectileDamageToWall` for each hit wall |
| Why not done in F11 | Current splash only damages the player; walls inside the blast radius are ignored |
| Both frontends | Requires libGDX parity (SR-99) |
| New req | **SR-106** |

### DS-3 — Zombie melee wall bash (low priority)

| Attribute | Detail |
|-----------|--------|
| Who fires | `ZombieCharacter` when movement is blocked by a breakable wall for N consecutive ticks |
| Damage | `zombie.getDamage() / 4` per tick blocked (rounds up to ≥ 1) |
| Mechanic | In `ZombieCharacter.doPositionEvaluation`: if next-step vector is a breakable wall, call `applyWallDamage` instead of stopping |
| Game feel | Zombies slowly pound through Dirt / Glass; cannot break Stone or Wood without significant time |
| Both frontends | JavaFX: in `ZombieCharacter`; libGDX: equivalent in its zombie runtime |
| New req | **SR-107** |

### DS-4 — libGDX projectile parity (SR-99) ✅ IMPLEMENTED (2026-06-20)

| Attribute | Detail |
|-----------|--------|
| Who fires | Any libGDX enemy that fires a projectile (PumpkinBombers via `GdxEnemyRuntime.tryShootAt`) |
| Damage | `GdxWallDamageSupport.updateProjectiles` calls `GameMazeWorld.applyWallDamage` directly on the render thread |
| Key files | `GdxProjectileRuntime`, `GdxEnemyRuntime`, `GdxWallDamageSupport`, `RangedEnemySpawnProps`, `PlayingModeBridge.updateProjectiles`, `LibgdxPlayingBridge`, `GdxGamePlayingBridgeFactory`, `GdxGameScreenAssembler` |
| Tests | `GdxWallDamageSupportTest` (9 tests), `PlayingModeControllerTest` FakeBridge |
| CI | Passed on branch `feature/breakableWaLLS` (run 27874753789) |

---

## 3. Shared Architecture Changes Required

### 3a. `ICanDamageWalls` interface (DIP, ISP)

Create in `maze-common-frontend` (or `main.game.maze.mazeworld`):

```java
public interface ICanDamageWalls {
    /** Returns the HP damage this source deals per hit to a wall. */
    int getWallDamage();
}
```

`PumpkinBomberCharacter`, `PlayerCharacter` (when armed), and `ZombieCharacter` implement it.
`GameController.applyProjectileDamageToWall(Vector2D, int)` already accepts raw `int` damage, so
no change needed there — the interface just formalises the contract.

### 3b. `WallCollisionUtil` extensions

`WallCollisionUtil.findFirstHitWall(Vector2D projectile, List<Vector2D> walls)` is already shared.
Add:

```java
/** Returns all walls whose AABB intersects the circle centred at (cx, cy) with given radius. */
public static List<Vector2D> findWallsInRadius(double cx, double cy, double radius,
                                               List<Vector2D> walls)
```

Needed by DS-2 (explosion splash). Must be in `main.game.maze.mazeworld` so both frontends share it.

### 3c. UI-thread dispatch in libGDX

JavaFX uses `Platform.runLater`; libGDX must use `Gdx.app.postRunnable`. The
`applyWallDamage` pipeline is thread-safe (`CopyOnWriteArrayList`) but nav-graph rewire
should be posted to the render thread in libGDX to avoid concurrent iteration.

### 3d. Ecore model mapping for DS-0

The Bomberman flame attack is a player feature, so its authoritative model home is `Player.ecore`, not `opponents.ecore`.

| Ecore model | Role in DS-0 | Relevant features |
|---|---|---|
| `maze-common-backend/src/main/resources/xmi/player/Player.ecore` | Player side authority for the new flame attack | `PlayerModel.playerCharacter`, `PlayerType.id`, `displayName`, `health`, `speed`, `ImageBase`, `ImageTurnLeft`, `ImageTurnRight`, `ImageTurnUp`, `ImageTurnDown`, `ImageDeath` |
| `main.game.maze.opponents/src/main/resources/opponents.ecore` | Enemy side authority for the targets that receive chained damage | `CharacterType.health`, `Zombie.attackDamage`, `Ghost.attackDamage`, `RangedEnemy.attackDamage`, `resurrectionTime`, `projectileType`, `projectileSpeed`, `splashRadius`, `arcHeight` |

The plan should treat the flame attack as runtime player combat logic backed by player tuning data. If the model is extended, the new values should live on `PlayerType`, for example `bombCount` with default `3` and `bombDamage` with default `100`. The chain transfer rule, where leftover damage carries from one enemy to the next, remains runtime logic and consumes enemy HP from `CharacterType.health` in the opponent model.

No new field is required in `opponents.ecore` for the flame attack itself. That model already supplies the target HP values and enemy metadata used by the runtime when resolving the chained hit order.

---

## 4. New Requirements

| ID | Summary | Source |
|----|---------|--------|
| **SR-113** | Space bar bomberman flame attack with 3 bombs and 100 HP chained damage | DS-0 above |
| **SR-99** | libGDX parity — projectile wall damage | ✅ IMPLEMENTED 2026-06-20 |
| **SR-100** | Visual crack/tint cue on partial damage | existing, proposed |
| **SR-101** | `WallDestroyedEvent` domain event bus | existing, proposed |
| **SR-104** | `WallMaterialSpec` as DIP boundary | ratified in F11 Phase 2 |
| **SR-105** | Player weapon can damage walls | DS-1 above |
| **SR-106** | PumpkinBomber explosion splash damages nearby walls | DS-2 above |
| **SR-107** | Zombie melee bash slowly destroys breakable walls | DS-3 above |

---

## 5. Suggested Implementation Order

1. **DS-0 (Bomberman flame attack)** — highest gameplay value; defines the new space bar bomb flow
2. **DS-1 (Player weapon)** — direct wall damage source with no new architecture needed
3. **DS-4 (libGDX parity)** — unblocks both frontends for all future sources
4. **3a (ICanDamageWalls interface) + 3b (findWallsInRadius)** — shared plumbing
5. **DS-2 (Explosion splash)** — depends on `findWallsInRadius`
6. **DS-3 (Zombie bash)** — lowest complexity; purely in character movement logic

Each source should be a separate commit on a dedicated branch with:
- TDD tests written first (mock `GameController` / `applyWallDamage`)
- RTM row updated
- Both frontends covered (CRR-5)

---

## 6. Out of Scope (separate plans)

- SR-100 (visual crack overlay) — render concern, separate PR
- SR-101 (domain events / `WallDestroyedEvent`) — event bus architecture, separate PR
- F10/F20 (LOB and BEAM projectile variants) — see `f10-f20-projectile-variants-plan.md`
