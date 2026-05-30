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
