# Game Rules (GR)

This document lists the gameplay rules that MUST behave identically across
the JavaFX (`maze`) and libGDX (`maze-libgdx`) frontends. Every requirement
is numbered GR-N. Drift between backends is a defect; new gameplay features
MUST be added to the shared core, not to a single frontend.

## Authoring source of truth

- **GR-1**: All game rule data (opponents, difficulties, damage,
  multipliers, infection, behaviors, player attributes) is authored as
  EMF/XMI models and loaded through `XmiRulesLoader`. Frontends MUST NOT
  hard-code gameplay values that exist in the model.
- **GR-2**: OCL constraints on the models (under
  `main.game.maze.opponents`, `main.game.maze.difficulties`, etc.) are
  validated via `OpponentsValidator` and equivalent validators. Both
  frontends MUST validate the loaded model and refuse to spawn from an
  invalid model.

## Difficulty resolution

- **GR-3**: The active `Difficulty` for a play session is resolved via
  `DifficultyService.getCurrent()`. Frontends MUST NOT cache or duplicate
  this resolution; they MUST consult the service per session.
- **GR-4**: Per-enemy-type caps are taken from
  `Difficulty.getEnemyMaxCount()` mapped by `EnemyTypes` to integer caps.
  Both frontends MUST use this exact mapping with no per-frontend
  adjustments.
- **GR-5**: When ratios are required (JavaFX `ProfileRules`), they MUST be
  derived from the same caps map so that JavaFX and libGDX always pick from
  the same population. Future divergence MUST be removed by extracting a
  shared `EnemySpawnPlanner`.

## Enemy spawning

- **GR-6**: The number of spawned enemies of a given type MUST be the
  minimum of the difficulty cap and the available `CharacterType`
  instances for that enemy type in the loaded `OpponentModel`.
- **GR-7**: An enemy instance MUST be placed at a position that does not
  collide with any wall. The shared `PlayerState.spawnAwayFromWalls`
  helper (or an equivalent service) is the canonical wall-avoidance
  routine. Frontends MUST NOT roll their own wall-avoidance loop with
  different semantics.
- **GR-8**: Spawn margins from the level edges MUST be consistent across
  frontends. The libGDX `RuntimeVisualModelLoader.SPAWN_MARGIN` and the
  JavaFX `OpponentRuntimeFactory.SPAWN_MARGIN` MUST stay numerically
  equal; the long-term fix is a shared constant.

## Damage and combat

- **GR-9**: Player health is initialized from `PlayerConfig.health()`
  loaded via `XmiRulesLoader`. Both frontends MUST initialize HP from the
  same call.
- **GR-10**: Per-character collision damage is sourced from
  `CollisionDamage` on the model. Damage applied per contact tick MUST be
  numerically identical between frontends for the same opponent and
  difficulty (same multiplier path).
- **GR-11**: Death sets the player's render texture to the death texture
  declared in `PlayerConfig`. Both frontends MUST swap to it on death.

## Infection

- **GR-12**: An enemy is "infectious" iff its `EnemySpawn.infectionLevel()
  > 0`. The boolean predicate `isInfectious` MUST agree across both
  frontends.
- **GR-13**: When the player is infected, a visible "Infected!" warning
  MUST be drawn on the HUD. JavaFX uses a sign Node; libGDX uses the HUD
  triangle + label. Both MUST be driven by the same combat-frame infected
  flag.
- **GR-14**: The infection mist visual MUST be drawn BEHIND the enemy
  sprite. It is a transient overlay and MUST NOT occlude the sprite or
  the player.

## HUD and overlays

- **GR-15**: HUD layout is overlay-based. The HP bar pins to the top edge
  of the window; the commands bar pins to the bottom edge; the maze
  renders behind both. This matches JavaFX FXML anchoring.
- **GR-16**: Game state overlays (WIN / GAME OVER / HIGH SCORES) are
  centered on the full window in both frontends and respond to ESC.

## Scoring and progression

- **GR-17**: Base score per difficulty is the canonical
  `EASY/NORMAL/HARD_BASE_SCORE` integers. Both frontends MUST use the same
  values for the same difficulty.
- **GR-18**: Score adjustments (route hint penalty, etc.) MUST be applied
  identically. New score rules go in a shared scorer service; per-frontend
  scorers are forbidden.
- **GR-36**: When the player dies (HP reaches 0), a **death penalty of 5 000
  points** is subtracted from the end-screen score. This penalty is applied
  via `StageConstants.ScoreDeathPenalty` and must be identical in both
  frontends. Both frontends MUST call `ScoringEngine.characterScreenScore()`
  (not `gameplayScore()`) on the GAME_OVER and WON overlays.
- **GR-37**: On the GAME_OVER and WON end screens, the total damage the player
  received is subtracted from the score as **damage × 10**
  (`hpReduction × ScoreSubtractFactor` where `ScoreSubtractFactor = 10`).
  This penalty is in addition to the move penalty and the death penalty.
  Both frontends MUST compute this via `ScoringEngine.characterScreenScore()`;
  a per-frontend inline formula is forbidden.

## Win / lose

- **GR-19**: Win is reached when the player enters the goal radius. Both
  frontends MUST use the same goal position derivation (centered on the
  maze) and the same proximity check.
- **GR-20**: Loss is reached when HP reaches 0. Both frontends MUST
  consult the same `CombatState` frame to detect death.

## Death flow and input lock

- **GR-21**: After player death is detected, the dead player visual MUST
  remain visible for 3 seconds before the GAME OVER screen is shown.
  JavaFX and libGDX MUST use the same delay.
- **GR-22**: Once the player is dead, player movement input MUST be
  blocked immediately. Keyboard and mouse movement are forbidden until the
  game leaves the active play state.
- **GR-23**: During the death-display delay window, the game MUST NOT
  transition to WIN state even if the player position overlaps the goal.

## Audio

- **GR-24**: Music tracks for menu, in-game, win, and game-over MUST be
  resolved via the same `MazeVisualStyleConfig` keys and the shared
  `AudioEngine`. Per-frontend audio routing is forbidden.

## Ghost phasing (non-tangibility)

- **GR-26**: A ghost with `nonTangibilityEnergy > 0` is considered
  "phasing". A phasing ghost bypasses all wall-collision checks during
  movement and when evaluating contact damage; it CAN still deal contact
  damage to the player when its bounding box overlaps the player.
- **GR-27**: The phasing state, energy drain rate, and visual opacity
  calculation for ghosts are defined in the shared service
  `GhostNonTangibilityService` (in `maze-common-frontend`). Both JavaFX
  and libGDX MUST use this service exclusively; magic-number inline copies
  are forbidden.
  - Energy drain rate: `ENERGY_DECREASE_PER_SEC = 0.14 * (1000 / 60)` units/sec.
  - Opacity: `clamp(1 − energy/MAX_ENERGY + MIN_OPACITY, MIN_OPACITY, 1.0)` where `MIN_OPACITY = 0.1` and `MAX_ENERGY = 100`.
- **GR-28**: The wall-ignoring movement path for phasing ghosts is
  implemented in `GhostPhasingMovementService` (in `maze-common-frontend`).
  The service uses a random cardinal direction, bounces at board boundaries,
  and is reset when a new game session starts. Both frontends MUST delegate
  to this service; per-frontend movement logic for phasing ghosts is
  forbidden.
- **GR-29**: `GhostPhasingMovementService` state (per-enemy direction map)
  MUST be reset on every new game session start so that state does not leak
  across sessions.
- **GR-30**: Automated parity tests (`GhostTangibilityParityTest`,
  `GhostNonTangibilityServiceTest`, `GhostPhasingMovementServiceTest`,
  `GhostTangibilityTest`) MUST assert that phasing ghosts deal contact
  damage and that opacity/energy calculations match between both frontends.

## Locked-in tests (regression contracts)

- **GR-25**: A parity test MUST verify caps derived from `Difficulty` are
  equal for both consumers (JavaFX `OpponentRuntimeFactory` path and
  libGDX `RuntimeVisualModelLoader` path).
- **GR-26**: A parity test MUST verify that loaded `OpponentModel` passes
  OCL validation; both frontends use the same validator.
- **GR-27**: A parity test MUST verify that `EnemySpawn.infectionLevel`
  drives `isInfectious` consistently and that the libGDX mist is drawn
  before the enemy sprite (GR-14).
- **GR-28**: A parity test MUST verify that `PlayerConfig.health()` is
  what initializes player HP in both frontends.
- **GR-29**: A parity test MUST verify identical base score per
  difficulty in both frontends (GR-17).
- **GR-30**: A parity test MUST verify the death-display delay is 3
  seconds in libGDX and matches the JavaFX death-delay rule (GR-21).

## Aggressive chase fallback

- **GR-31**: If an aggressive enemy stays blocked for more than 4 seconds,
  it MUST switch to shortest-path follow mode and keep following that path
  for up to 20 seconds before returning to directional chase.
- **GR-32**: The blocked detection timer and the 20-second path-follow
  window MUST be shared across JavaFX and libGDX through
  `AdaptiveAggressiveMovementService`; frontend-specific duplicates are
  forbidden.

## Loop avoidance

- **GR-33**: Wander and patrol movement MUST avoid repeating short circular
  loops when alternatives are available. JavaFX and libGDX MUST use the same
  shared anti-loop wander logic so non-aggressive movement remains parity-safe.

## Enemy path overlay and wander collision semantics

- **GR-34**: `/showenemypath` and `/sep` MUST visualize the active runtime
  path buffer each enemy is currently following (aggressive path-follow or
  patrol path mode). Frontends MUST NOT substitute a newly computed
  shortest-path to the player for this overlay.
- **GR-35**: WANDER collision behavior MUST keep moving in the current
  direction until blocked, then choose a random passable cardinal direction.
  Immediate reverse is allowed when selected by random choice or when it is
  the only passable option.

- **GR-36**: When a breakable wall is destroyed by any damage source (bomb
  flame, projectile, etc.) every other breakable wall that shares an endpoint
  with the destroyed wall MUST also collapse immediately (cascade collapse).
  The cascade propagates transitively: if a newly collapsed wall also shares an
  endpoint with another breakable wall, that wall collapses too, and so on until
  no further adjacent breakable walls remain. Indestructible walls adjacent to a
  destroyed wall are never affected by the cascade.

- **GR-37**: The bomb flame corridor MUST detect and damage walls whose
  perpendicular span overlaps the flame corridor, not only walls whose span
  contains the exact blast origin coordinate. A wall is in the flame corridor
  if any part of its perpendicular span lies within `corridorHalfWidth` pixels of
  the blast origin on the perpendicular axis. Both the east/west and north/south
  directions must apply this rule symmetrically.

## Weapons, pickups and kill credit

- **GR-38**: While playing, an item spawns every 3 seconds until the cap is
  reached (bomb, laser rifle, sledgehammer, shield cube or small heart) at a
  corridor point away from the player and never in or over a wall (its centre
  keeps half an item plus half a wall from every wall), drawn as large as the
  player, up to 5 items on easy maps, 10 on medium and 15 on hard / large maps.
  Picking an item up frees a slot, so spawning continues.
- **GR-39**: The player starts with the difficulty's bombs only. A bomb pickup
  adds 1 bomb, a laser rifle 5 shots, a sledgehammer 5 swings. Tab switches
  between weapons in stock; Space uses the weapon in hand. An empty weapon
  switches to the next one in stock, and when nothing is left in hand a
  picked-up weapon goes straight into the hands.
- **GR-40**: The laser hits the nearest enemy in line with the player's facing
  and damages breakable walls in its way, but never steel walls. The
  sledgehammer strikes every enemy within reach that no wall separates from the
  player and never damages walls.
- **GR-41**: A shield cube blocks all damage to the player for 5 seconds; a
  heart heals 20 % of the maximum health (never above the maximum), flashes the
  player three times, shows a green arrow rising over the player's head for one
  second and plays the "extra life" jingle.
- **GR-42**: Each kill adds points in proportion to the enemy's danger (its
  effective threat x 100, at least 10) to the player who dealt the killing blow,
  including kills by walls that player brought down. Kills by nobody (enemy
  projectiles, terminal commands) score nothing.
- **GR-43**: A killed enemy that can come back (zombies) respawns 15 seconds
  after its death, in both frontends; enemies that cannot come back (ghosts,
  pumpkin bombers) stay dead.
- **GR-44**: The player starts with the difficulty's lives (easy 6, normal 5,
  hard 3). Dying with lives left costs one life: the dead player is shown for
  3 seconds, then respawns at the level start with full health and 2 seconds
  of protection; weapons, items, kills and score carry over. Losing the last
  life ends the game as before.
- **GR-45**: The extra-life item spawns with the other items (spawn weight 1
  against 3 for each other item) and gives one more life.
- **GR-46**: The weapons HUD (top-left) lists the weapons in stock and the one
  in hand, highlighted, plus the lives left.
- **GR-47**: Movement keys: arrows, numpad, Q/A left, W up, E right, S/D down.
  Space or a right click uses the weapon in hand; Tab switches weapons.
- **GR-48**: Winning level 1 leads on to level 2. Level data (zones, threat
  capacity, sergeants) comes from the levels model through `LevelCatalogLoader`
  and level progress from the shared `LevelProgress`; neither frontend keeps
  its own level state.
- **GR-49**: Level 2 uses the same generated maze as level 1; only the wall
  materials differ: concentric zones around the heart (four on level 2), the
  outer zone the strongest and the inner zone the weakest
  (`GameMazeWorld.assignZonedWalls`). A level with `showWallMaterials` draws each
  wall with the sprite of its material in both frontends (`WallLook`).
- **GR-50**: Level 2's threat capacity is the difficulty's maximum threat x the
  level's `threatCapacityFactor` (`LevelSpec.maxThreat`).
- **GR-51**: Zombie sergeants never move. A sergeant that sees the player
  (`SergeantGunner.canSee`: in sight range, no wall between) fires bursts of
  rounds with a rest between bursts; losing sight ends the burst. Six on hard,
  four on normal, one on easy, standing near the heart.
- **GR-52**: A killed sergeant drops its gatling gun where it fell
  (`PickupKind.GATLING_GUN`, never spawned randomly); picking it up adds
  bursts to the player's arsenal.
- **GR-53**: The player's arsenal is inherited by the next level
  (`LevelProgress.advance` / `inheritedArsenal`).
- **GR-54**: The player's bomb makes a deep explosion sound and shakes the
  screen (`BombImpact`: strength 14 px, 0.7 s, decaying); both frontends.
- **GR-55**: A level can be built on an earlier one (`baseLevel`): what it does
  not set itself is taken from that level. Level 3 is level 2 plus mines and
  portal ghosts.
- **GR-56**: Level 3 puts three anti-personnel mines round every sergeant
  (`MineField`), only on free ground. A mine that the player touches goes off:
  the player takes 20 damage, walls and enemies in the small blast (150 px) take
  15, and a hole is left in the ground that stays.
- **GR-57**: A player whose centre is over a hole falls and comes out on free
  ground at the opposite edge of the map (`EdgeTeleport.oppositeEdge`).
- **GR-58**: A portal ghost does no damage. Touching the player teleports them
  to a random free spot in the outer band of the map
  (`EdgeTeleport.randomOuterEdge`). Portal ghosts: 3 easy, 6 normal, 10 hard.
- **GR-59**: After a teleport the player cannot be teleported again for the
  ghost's cooldown (`TeleportGate`).
- **GR-60**: A teleported player flashes in at the new place; the screen says
  the level's banner word ("Teleported") briefly and a deep voice says it. The
  word, its time, the cooldown and the sounds come from the level's
  `TeleportRules`.
- **GR-61**: Level 4 is level 3 plus a second circle of mines round every sergeant
  (the same number of mines as the first circle, staggered, further out: twice
  the mines) and guard towers.
- **GR-62**: Guard towers: 4 on hard, 3 on normal, 2 on easy, on free ground in
  the outer band of the map, apart from each other and from the player's start.
  They never move, have 1500 hit points and look more damaged as they lose them.
  A destroyed tower explodes (damage 60 to its surroundings, deep boom, screen
  shake) and leaves no corpse.
- **GR-63**: A tower shoots when the player is within 650 px and no more than 3
  walls lie between them (it shoots over walls, not over a fourth).
- **GR-64**: A tower's weapons run on a steady 3 second cycle: an rpg at the
  start of the cycle, a burst of six gatling rounds (3 damage each) halfway
  through. A rocket flies 300 px/s to where the player was and explodes there
  (blast 60 px: 25 damage to the player, 40 to walls and enemies).
- **GR-65**: Level 5 is level 4 plus wall builders: 10 on hard, 5 on normal, 2
  on easy, standing within 170 px of the heart when the level starts.
- **GR-66**: A wall builder has 80 hit points, does no damage to the player and
  does not resurrect. It walks 55 px/s through the corridor middles to an open
  corridor into the block of cells round the heart (ring by ring, outermost
  first) and builds a wooden wall across it, one 20 px piece per second (a 60 px corridor width per 3 s).
- **GR-67**: The savepoint of a level is made when the level before it is
  completed (the level number and the weapons carried in). Restarting a level
  restores it; restarting level 1 is a new game. A new game removes the
  savepoint.
- **GR-68**: The player starts on the free corridor point nearest to the
  original start that the HUD does not cover (the top bar, the weapon panel
  under it, the score panel and the bottom row).
- **GR-69**: The wall builders start at least 60 px (one corridor) apart,
  wander outwards until they are 4 cells from the heart, build there, and when
  that ring is closed move in one cell at a time (4, 3, 2, 1), standing on the
  heart side of each corridor they wall up.
- **GR-70**: Level 6 is level 5 plus two random snaking streams of lava under
  the walls, 46 px wide, with two bridges (56 px long) over each.
- **GR-71**: Standing in lava off a bridge kills the player at once, shield or
  not. A stream can only be crossed on its bridges.
- **GR-72**: Enemies, builders and everything that is placed (spawns, pickups,
  mines, exits) keep out of lava; the way from the start to the heart always
  crosses the streams on bridges.
- **GR-73**: The last level starts with the title FINAL LEVEL (about 2.8 s,
  slam, shake, fade) and a dark voice. Winning it shows GAME WON and a dark
  voice says "Ultimate victory".
- **GR-74**: From level 6 on, starting the last life gives the pulse beam cannon
  (6 shots) in the hands. Fire charges for 2 s, then one beam of 6000 damage
  over 1200 px that blows up a tower in one shot; another weapon cancels it.
- **GR-75**: The starting score of a level is the difficulty's starting score (5 000 easy, 10 000 normal, 30 000 hard) plus the score the previous level ended with.
  Restarting a level restores the score it was entered with; level 1 and a new game carry nothing.
