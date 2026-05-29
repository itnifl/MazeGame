# Missing features

This file tracks game-design and game-logic features that are **defined in the
metamodels** (ecore or the Xtext DSL) but **not yet wired into the running
JavaFX game client**. The goal is to give every modeller a clear shopping list
of work that closes the gap between what the model declares and what the game
actually does.

Each feature has:

- **ID** — `F<number>` for cross-referencing in commits, issues and tests.
- **Source** — which model file declares it.
- **Status** — Missing, Partial, or Done.
- **Owner / target backend** — JavaFX, libGDX or both.
- **Acceptance** — a one-line definition-of-done.

Where a feature applies to both backends, both should reach Done before the
overall feature is marked Done.

---

## Gaps from the ecore metamodels

### F1. Animated character sprites

- **Source**: [opponents.ecore](main.game.maze.opponents/src/main/resources/opponents.ecore) — `CharacterType.animationFrameCount` (default 1), `CharacterType.spriteScale` (default 1.0).
- **Status**: Missing.
- **Backend**: both.
- **What the model says**: every character type can declare an animation frame
  count and a sprite scale.
- **What the game does today**: the sprite is drawn as a single static
  `ImageView`. No grep hit shows runtime use of `animationFrameCount` or
  `spriteScale` outside of code generation.
- **Acceptance**: characters with `animationFrameCount > 1` cycle through their
  frames at runtime, and `spriteScale` controls the rendered size.

### F2. Loot drops on enemy death

- **Source**: [opponents.ecore](main.game.maze.opponents/src/main/resources/opponents.ecore) — `Zombie.zombieLootTable`, `LootTable.weightCapacity`, `LootItem (name, type, value, weight, graphicBase)`, `LootItemType {FOOD, BOMB, TRAP, WEAPON}`.
- **Status**: Missing.
- **Backend**: both.
- **What the model says**: zombies (and any type that gains a `LootTable`) can
  drop items capped by a weight budget. Items have four flavours.
- **What the game does today**: no loot drop, no inventory, no pickup. Killed
  enemies simply disappear.
- **Acceptance**: killing a zombie that owns a loot table spawns the rolled
  items on the floor; the player can walk over them; FOOD restores hit points,
  BOMB / TRAP / WEAPON affect gameplay according to their `value`.

### F3. Patrol behaviour with explicit waypoints

- **Source**: [movements.ecore](main.game.maze.behaviour/src/main/resources/movements/movements.ecore) — `PatrolBehavior (currentIndex, behavior: PatrolPathBehavior)`, `PatrolPoint (point, events)`, `PatrolZone (topLeft, width, height)`, `PatrolPathBehavior {LOOP, BACKWARD, RANDOM}`.
- **Status**: Partial.
- **Backend**: both.
- **What the model says**: a patrol is an ordered list of waypoints, optionally
  scoped to a rectangular zone, with three traversal strategies.
- **What the game does today**: `GameController.doCharacterPatrolMove()` exists
  and a `BehaviorType.PATROL` is handled, but waypoints are currently inferred
  from local geometry rather than read from a configured `PatrolBehavior` with
  a `path` and a `patrolZone`. The `LOOP / BACKWARD / RANDOM` traversal modes
  are not implemented.
- **Acceptance**: a `PatrolBehavior` loaded from XMI (or from the DSL) drives
  the enemy through its declared waypoints using the chosen traversal mode.

### F4. CharacterEvents along a patrol path

- **Source**: [movements.ecore](main.game.maze.behaviour/src/main/resources/movements/movements.ecore) — `PatrolPoint.events: CharacterEvent[0..*]`, with subclasses `HealthEvent, SpeedEvent, TimeEvent, AttackEvent, VisionEvent` and a `probability` 0..100.
- **Status**: Missing.
- **Backend**: both.
- **What the model says**: arriving at a patrol point may trigger a typed
  event (heal, speed-up, wait, attack, scan) with a probability roll.
- **What the game does today**: no event dispatch on patrol arrival.
- **Acceptance**: reaching a `PatrolPoint` rolls each attached `CharacterEvent`
  against its `probability` and applies the corresponding effect to the
  patrolling character.

### F5. Pathfinding algorithm selection

- **Source**: [movements.ecore](main.game.maze.behaviour/src/main/resources/movements/movements.ecore) — `PathCalculator` with `DijkstraPathCalculator`, `AstarPathCalculator (heuristicMethod)`, `LocalPathCalculator`, and `DistanceMethod {MANHATTAN, EUCLIDEAN}`.
- **Status**: Partial.
- **Backend**: both.
- **What the model says**: each behaviour can pick its path-finding algorithm
  and (for A*) its heuristic.
- **What the game does today**: the maze module ships A* / Dijkstra debug
  overlays, but `MovementBehavior.pathcalculator` is not consulted at runtime;
  enemy movement uses a hard-coded local strategy.
- **Acceptance**: a `ChaseBehavior` configured with `AstarPathCalculator
  (heuristicMethod=EUCLIDEAN)` actually chases via A* with that heuristic.

### F6. ChaseBehavior

- **Source**: [movements.ecore](main.game.maze.behaviour/src/main/resources/movements/movements.ecore) — `ChaseBehavior (relativePositionTarget, pathcalculator)`.
- **Status**: Missing.
- **Backend**: both.
- **What the model says**: a chase behaviour pursues a target offset from a
  reference position.
- **What the game does today**: `GameController` routes only on `WANDER` and
  `PATROL` behavior types; chase is unimplemented.
- **Acceptance**: enemies created with `ChaseBehavior` actively pursue the
  player (or another target) along the configured path calculator.

### F7. Difficulty multipliers applied to opponents

- **Source**: [difficulty-module.ecore](main.game.maze.difficulties/src/main/resources/difficulty-module.ecore) — `Difficulty.monstersMovementSpeedMultiplier`, `Difficulty.monstersDamageMultiplier`.
- **Status**: Partial.
- **Backend**: both.
- **What the model says**: enemy speed and damage should scale per difficulty.
- **What the game does today**: difficulty only controls board size and base
  score. Grep finds no runtime use of `monstersMovementSpeedMultiplier` or
  `monstersDamageMultiplier`.
- **Acceptance**: changing the selected `Difficulty` measurably changes enemy
  movement speed and damage at runtime.

### F8. EnemyMaxCount caps enforced at runtime

- **Source**: [difficulty-module.ecore](main.game.maze.difficulties/src/main/resources/difficulty-module.ecore) — `EnemyMaxCount (type: EnemyTypes, maxCount: EInt)` on each `Difficulty`.
- **Status**: Partial.
- **Backend**: both.
- **What the model says**: each difficulty declares per-type spawn caps.
- **What the game does today**: caps are defined in `difficulties.xmi` but no
  runtime check enforces them. `OpponentRuntimeFactory` instantiates everything
  it finds in the opponents XMI.
- **Acceptance**: trying to spawn more than `EnemyMaxCount.maxCount` of a
  given `EnemyTypes` for the current difficulty is rejected, with a clear log
  message.

### F9. validateMaxThreat enforced on the runtime opponent set

- **Source**: [opponents.ecore](main.game.maze.opponents/src/main/resources/opponents.ecore) — `OpponentModel.validateMaxThreat`.
- **Status**: Partial.
- **Backend**: both.
- **What the model says**: the sum of `characterTypes.threatLevel` must be
  &le; `selectedDifficulty.maxThreat`.
- **What the game does today**: the OCL invariant is declared on the metamodel
  but the runtime factory does not refuse to spawn an opponent set that
  exceeds the cap; the game just runs.
- **Acceptance**: an opponent XMI that violates the cap fails fast on load
  with an explicit, model-level error message.

### F10. Ranged enemy projectile variants

- **Source**: [opponents.ecore](main.game.maze.opponents/src/main/resources/opponents.ecore) — `RangedEnemy.projectileType: ProjectileType {STRAIGHT, LOB, BEAM}`, `splashRadius`, `arcHeight`, `attackCooldownMs`, `attackRange`, `explosionImage`, `explosionSound`, `throwSound`.
- **Status**: Partial.
- **Backend**: both.
- **What the model says**: ranged enemies (`PumpkinBomber` is one) can throw
  STRAIGHT, LOB or BEAM projectiles with splash and arc parameters, complete
  with throw / explosion sound and image.
- **What the game does today**: `PumpkinBomberCharacter` throws a single
  hard-coded projectile shape, and the runtime does not switch behaviour on
  `projectileType` or honour `splashRadius` / `arcHeight`.
- **Acceptance**: changing `projectileType` in the XMI visibly changes how the
  projectile flies and how it damages on hit.

### F11. Breakable walls and hit points

- **Source**: [walls.ecore](main.game.maze.walls/model/walls.ecore) — `WallMaterial.breakable`, `WallMaterial.hitPoints`, OCL `ValidHitPoints`.
- **Status**: Missing.
- **Backend**: both.
- **What the model says**: a wall can be marked breakable with a positive
  `hitPoints` budget.
- **What the game does today**: walls are static; nothing depletes hit points
  and nothing removes a wall during play.
- **Acceptance**: a wall declared with `breakable=true, hitPoints=N` can be
  damaged N times by an appropriate action (player weapon, bomb loot, ranged
  enemy projectile) and then disappears from the maze.

### F12. Full wall material catalogue surfaced

- **Source**: [walls.ecore](main.game.maze.walls/model/walls.ecore) — `WallMaterialBaseType {GLASS, DIRT, WOOD, STONE, STEEL}`.
- **Status**: Partial.
- **Backend**: both.
- **What the model says**: five base materials are available.
- **What the game does today**: only three difficulty-locked wall types are
  used (DIRT_BASIC, WOOD_BASIC, STEEL_SOLID). GLASS and STONE are defined but
  never drawn.
- **Acceptance**: every `WallMaterialBaseType` is exercised by at least one
  level configuration and rendered correctly.

### F13. Player death sprite

- **Source**: [Player.ecore](maze/src/main/resources/xmi/player/Player.ecore) — `PlayerType.ImageDeath` (default `/main/game/maze/you2-dead.png`).
- **Status**: Partial.
- **Backend**: both.
- **What the model says**: the player has an explicit death image distinct
  from the base / turn images.
- **What the game does today**: `PlayerCharacter` swaps to the death image in
  certain code paths, but the swap is gated on JavaFX-only animation hooks.
  The libGDX backend has no death-state rendering at all yet.
- **Acceptance**: when hit points reach 0, both backends render the
  `ImageDeath` sprite and freeze player input until restart.

### F14. Ghost non-tangibility energy as a UI gauge

- **Source**: [opponents.ecore](main.game.maze.opponents/src/main/resources/opponents.ecore) — `Ghost.nonTangibilityEnergy (default 100)`. [movements.ecore](main.game.maze.behaviour/src/main/resources/movements/movements.ecore) — derived `ignoreWalls = nonTangibilityEnergy > 0`.
- **Status**: Partial.
- **Backend**: both.
- **What the model says**: ghosts ignore walls while their energy is
  positive; the value is bounded 0..100.
- **What the game does today**: the energy is depleted in
  `doNonTangientEnergyCalculation` but no visible feedback shows the player
  how close a ghost is to becoming tangible again.
- **Acceptance**: each ghost shows a tiny energy indicator (bar or tint) that
  reflects its current `nonTangibilityEnergy`.

### F15. instantKillOnCollision for high-threat enemies

- **Source**: [movements.ecore](main.game.maze.behaviour/src/main/resources/movements/movements.ecore) — derived `instantKillOnCollision = threatLevel > 100`.
- **Status**: Missing.
- **Backend**: both.
- **What the model says**: any enemy with threat level above 100 should
  instantly kill the player on contact, irrespective of player HP.
- **What the game does today**: damage on collision is computed from
  `attackDamage` only; no path queries `instantKillOnCollision`.
- **Acceptance**: an enemy authored with `threatLevel > 100` reduces the
  player to 0 HP on first contact.

---

## Gaps from the Xtext DSL (`MazeDsl.xtext`)

### F16. DSL is grammar-only; no loader feeds the game

- **Source**: [MazeDsl.xtext](main.game.maze.dsl/src/main/java/main/game/maze/dsl/MazeDsl.xtext) and the empty `dsl/` examples folder.
- **Status**: Missing.
- **Backend**: both.
- **What the grammar says**: a single `.mazedsl` file can declare an entire
  game configuration (difficulty + opponents + patrols + loot tables).
- **What the game does today**: `GameController` loads opponents, difficulty,
  walls and player from XMI directly. No code path parses a `.mazedsl` file or
  bridges its AST into the EMF runtime models.
- **Acceptance**: pointing the game at a `.mazedsl` file (CLI flag or
  drop-in resource) produces the same gameplay configuration as the
  equivalent XMI set.

### F17. DSL `import` directive

- **Source**: [MazeDsl.xtext](main.game.maze.dsl/src/main/java/main/game/maze/dsl/MazeDsl.xtext) — `Import: 'import' importURI=STRING`.
- **Status**: Missing.
- **Backend**: both.
- **What the grammar says**: a DSL file can pull in additional resources
  (XMI, other DSL files) via `import`.
- **What the game does today**: nothing consumes the import list.
- **Acceptance**: imports declared in a DSL file are resolved and merged into
  the runtime model graph, with duplicate-id detection.

### F18. DSL `pumpkinbomber` opponent variant

- **Source**: [MazeDsl.xtext](main.game.maze.dsl/src/main/java/main/game/maze/dsl/MazeDsl.xtext) — `CharacterTypeEnum.pumpkinbomber`; corresponding `RangedSpecifics`. [difficulty-module.ecore](main.game.maze.difficulties/src/main/resources/difficulty-module.ecore) — `EnemyTypes.PUMPKINBOMBER`.
- **Status**: Partial.
- **Backend**: both.
- **What the grammar/model says**: pumpkin bomber is a first-class opponent
  type with per-type max-count and full ranged stats.
- **What the game does today**: the runtime class `PumpkinBomberCharacter`
  exists, but all shipped difficulties cap it at 0 (`PumpkinBomber max=0` in
  Easy / Normal / Hard XMI). The DSL has no example using it.
- **Acceptance**: at least one difficulty actively spawns pumpkin bombers and
  there is a DSL example that configures them.

### F19. DSL `passive` and `aggressive` behaviors

- **Source**: [MazeDsl.xtext](main.game.maze.dsl/src/main/java/main/game/maze/dsl/MazeDsl.xtext) — `BehaviorTypeEnum {passive, wander, aggressive, patrol}`.
- **Status**: Partial.
- **Backend**: both.
- **What the grammar says**: four behaviour modes are selectable.
- **What the game does today**: `GameController.doCharacter*Move()` switches
  on `WANDER` and `PATROL` only; `PASSIVE` (do nothing) and `AGGRESSIVE`
  (always chase) are not implemented.
- **Acceptance**: an opponent declared as `behavior passive` stands still and
  one declared as `behavior aggressive` actively chases the player.

### F20. DSL `straight` / `lob` / `beam` projectile dispatch

- **Source**: [MazeDsl.xtext](main.game.maze.dsl/src/main/java/main/game/maze/dsl/MazeDsl.xtext) — `ProjectileTypeEnum {straight, lob, beam}` in `RangedSpecifics`.
- **Status**: Missing.
- **Backend**: both.
- **What the grammar says**: ranged enemies can declare three projectile
  shapes.
- **What the game does today**: only one projectile motion exists. See also
  [F10](#f10-ranged-enemy-projectile-variants).
- **Acceptance**: setting `projectileType beam` produces a beam-style attack
  distinct from `straight` and `lob`.

### F21. DSL loot configuration

- **Source**: [MazeDsl.xtext](main.game.maze.dsl/src/main/java/main/game/maze/dsl/MazeDsl.xtext) — `LootTableConfig`, `LootItemConfig`, `LootItemTypeEnum {food, bomb, trap, weapon}`, opponent `loot` reference.
- **Status**: Missing.
- **Backend**: both.
- **What the grammar says**: loot tables are first-class DSL constructs.
- **What the game does today**: see [F2](#f2-loot-drops-on-enemy-death). The
  grammar can declare them; the game does not consume them.
- **Acceptance**: a DSL file declaring a `loot_table` is linked to an opponent
  whose `loot` reference points to it, and items drop accordingly.

### F22. DSL `waypoint (x, y) : N ms` wait times

- **Source**: [MazeDsl.xtext](main.game.maze.dsl/src/main/java/main/game/maze/dsl/MazeDsl.xtext) — `Waypoint: '(' x ',' y ')' (':' waitTime 'ms')?`.
- **Status**: Missing.
- **Backend**: both.
- **What the grammar says**: a waypoint can carry an optional wait duration.
- **What the game does today**: nothing reads `waitTimeMs` even when patrol
  behaviour is later wired up.
- **Acceptance**: an enemy on a patrol with `waitTime` pauses at each
  waypoint for the configured number of milliseconds.

### F23. DSL `PatrolZoneConfig` bounding rectangle

- **Source**: [MazeDsl.xtext](main.game.maze.dsl/src/main/java/main/game/maze/dsl/MazeDsl.xtext) — `PatrolZoneConfig: topLeft (x, y), width, height`.
- **Status**: Missing.
- **Backend**: both.
- **What the grammar says**: a patrol can be constrained to a rectangular
  zone.
- **What the game does today**: not enforced; patrolling enemies move
  through any reachable tile.
- **Acceptance**: an enemy whose `patrol` includes a `zone` never leaves
  that rectangle.

---

## Roll-up by area

| Area | Open IDs |
|------|----------|
| Opponents + sprites | F1, F10, F14, F15, F18 |
| Loot | F2, F21 |
| Patrol + AI | F3, F4, F5, F6, F19, F22, F23 |
| Difficulty | F7, F8, F9 |
| Walls | F11, F12 |
| Player | F13 |
| DSL plumbing | F16, F17, F20 |

When picking up a feature, link the commit and PR back to its `F<number>`
identifier so the gap analysis stays in sync with the implementation.
