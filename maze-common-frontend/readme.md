# maze-common-frontend

Backend-agnostic facades for UI threading, audio playback, and character views.
This module is the seam that lets the game code stay independent of whether the
runtime renderer is JavaFX, libGDX, or something else.

## What lives here

- `IUiScheduler` and `UiScheduler` (singleton holder): post work onto the UI
  thread, query whether the current thread is the UI thread.
- `IAudioEngine` and `AudioEngine` (singleton holder): play sound resources by
  classpath path, with optional per-sound cooldown.
- `ICharacterView`: a renderer-neutral view facade exposing position, scale,
  opacity, visibility, view order, effect clearing, and parent detachment.
- `DamageState` and `DamageStateResolver`: visual damage-state enum based on
  remaining HP ratio (`HEALTHY` > 75 %, `DAMAGED` 51–75 %, `BLEEDING` 26–50 %,
  `TIRED` &le; 25 %) and convention-based sprite path derivation (e.g.
  `zombie1-damaged-right.png`, `pumpkinbomber-damaged.png`) with silent classpath fallback.
- `DamagePathResolver` / `CachingDamagePathResolver`: the damage-sprite lookup both frontends
  use. `DamagePathResolver.classpath(anchor)` checks each (path, state) on the classpath once and
  caches the answer, so renderers can call it every frame.
- `FireBlast`, `FirePuff` and `FireBlastModel` (`common.graphics.fire`): frontend-neutral
  bomb fire blast. `FireBlastModel.puffs(blast, progress, clock)` returns layered circles
  (smoke, red rim, orange body, yellow, white-hot core, shockwave ring, embers) for one
  frame; flame fronts shoot out along each corridor arm, billow, flicker, cool and turn
  to smoke. Both frontends only draw the circles (smoke and debris normal-blended, the rest additive).
- `PumpkinBurst` and `PumpkinBurstModel` (`common.graphics.fire`): pumpkin bomber projectile
  explosion. A white-hot flash, a lumpy fireball that swells and burns down to smoke, a shockwave
  ring, orange rind chunks, green stem bits and seeds that fly out (`FirePuff.Layer.DEBRIS`,
  painted solid on top of the glow), and embers. Size follows the splash radius
  (`PumpkinBurst.lobRadius`, `STRAIGHT_SHOT_RADIUS`); bigger bursts last longer
  (`lifetimeFor`, 0.45–1.0 s). Shares its flame drawing with `FireBlastModel` via `PuffFrame`.
- `remains` package: `DeathCause` (FIRE, SHOT, CRUSHED, OTHER; used by the domain `ICanDie`),
  `RemainsSpriteResolver` (coal pile for fire, the enemy's own shot / crushed remains or a generic
  blood fallback), `RemainsLedger` (one remains per enemy, identity-keyed, capped, cleared on respawn)
  and `CrushZone` (which enemies a collapsing wall segment crushes).
- `WallRubble`, `WallRubbleModel`, `RubbleMaterial` (`common.graphics.fire`): a collapsing wall as
  layered puffs: blocks and gravel tumble out to both sides under a dust cloud and settle; colours
  follow the walls-model material id (`STONE_BASIC` -> stone); steel throws sparks.
- `PuffEffect` (`common.graphics.fire`): a timed effect drawn as puffs, so both frontends draw every
  combat effect with the same code. Implemented by `LaserBeamEffect` (beam flash), `SledgeSwingEffect`
  (a giant swing arc with a dust burst), `HitReactionEffect` (SHOT: a jolt back along the beam with
  sparks; BLOW: a harder knock-back with dust; both scaled by damage / max HP, and a `spriteOffset`
  the frontends apply to the struck enemy), `PickupSparkleEffect`, `HealFlashEffect` (the player flashing after a heart); `ShieldBubble` draws the power shield.
  `HealFeedback` times the heal flash and the green arrow (`pickups/heal-arrow.png`) rising over the player's head.
- `input.command.SwitchWeaponCommand` / `GameAction.SWITCH_WEAPON`: Tab switches weapons in both frontends.
- `input.MovementKeys<K>`: which keys move the player in which direction; each frontend builds one (Q/A left,
  W up, E right, S/D down, arrows, numpad) and uses it for both the bindings and the movement.
- `input.MouseButton` + `KeyBindingRegistry.bindMouse` + `InputFrame.secondaryMouseClicked()`: mouse buttons
  bound to actions; a right click triggers `FLAME_ATTACK` (use the weapon in hand) in both frontends.
- Resources: pickup sprites in `main/game/maze/pickups/` and weapon / pickup sounds in
  `main/game/maze/sounds/` (generated, see `tools/sprite-gen` and `tools/sound-gen`).
- `MazeVisualStyleConfig` plus `XmiMazeVisualStyleLoader` and
  `PropertiesMazeVisualStyleLoader`: shared model driven style and
  asset mapping for both backends, including
  difficulty background images, wall type ids, menu icon, and common
  menu or gameplay audio resource paths. The hard difficulty uses the volcanic
  wasteland image `main/game/maze/gameBackGround3.png`, selected by
  `hardBackgroundImagePath` in `src/main/resources/xmi/maze-visual-style.xmi`. `XmiMazeVisualStyleLoader` configures
  its `DocumentBuilderFactory` with OWASP XXE hardening (secure-processing,
  disallow-doctype-decl, external general/parameter entities and external DTD
  loading disabled, XInclude off, entity expansion off) so loading from
  arbitrary file paths is safe.
- Shared gameplay cadence:
  `GameplayTickRate` is the single source of truth for the enemy movement
  rate (`ENEMY_MOVEMENT_TICKS_PER_SECOND = 30`, `SECONDS_PER_TICK = 1/30`,
  `intervalMillis() = 33`, `MAX_TICKS_PER_FRAME = 4`). `MovementTickAccumulator`
  is the shared fixed-timestep catch-up timer both frontends use to run the
  correct number of movement ticks regardless of how long each loop iteration
  takes (capped at `MAX_TICKS_PER_FRAME`). This keeps JavaFX and libGDX enemy
  speed identical even when per-frame AI work (e.g. hard-difficulty A*) varies.
- Shared enemy movement helpers:
  `AdaptiveAggressiveMovementService`, `AntiLoopWanderMovementService`,
  `EnemySpawnUnstuckService`, `GhostNonTangibilityService`, and
  `GhostPhasingMovementService`.
  Aggressive enemies switch to shortest-path follow mode after 4 seconds of
  blocked movement and keep it for up to 20 seconds before returning to
  directional chase. Wander and patrol use anti-loop scoring to avoid short
  repeating circles when alternate cardinal moves exist.
  Ghost phasing (non-tangibility) is governed by two shared services:
  - `GhostNonTangibilityService`: stateless utility that determines whether a
    ghost is phasing (`energy > 0`), computes the per-tick energy drain, and
    calculates the semi-transparent rendering opacity. Both frontends MUST use
    this service exclusively.
    - Single-arg overload `calculateOpacity(double energy)` — legacy path; assumes
      `visibilityLevel = 100` (fully visible cap).
    - Two-arg overload `calculateOpacity(double energy, int visibilityLevel)` (F25) —
      clips the opacity ceiling to `visibilityLevel / 100.0` so a ghost configured
      with e.g. `visibilityLevel = 50` never renders above 50 % opacity even when
      fully materialised. Input is clamped to `[0, 100]`. When energy = 0 the
      returned opacity equals exactly `visibilityLevel / 100.0` (the solid-state
      floor). The gameplay floor of 0.1 applies only while the ghost is phasing.
  - `GhostPhasingMovementService`: stateful per-ghost wall-ignoring movement.
    A phasing ghost picks a random cardinal direction and bounces at board
    boundaries, bypassing all wall collision checks. Call `reset()` when a new
    game session starts so per-enemy direction state does not leak across
    sessions. Both frontends MUST delegate phasing movement to this service.
- Shared live path snapshot support for enemy overlays:
  `ActivePathPoint`,
  `AdaptiveAggressiveMovementService.currentPathForEnemy(...)`, and
  `PatrolMovementService.currentPathForEnemy(...)`.
  Frontends use these APIs for `/showenemypath` and `/sep` so overlays show
  the active runtime path being followed, rather than a newly computed path.

- Shared generic input core (`main.game.maze.common.input` and
  `main.game.maze.common.input.command`), backend-neutral and parameterized on the
  physical key type `K` (libGDX uses `Integer`, JavaFX uses `javafx.scene.input.KeyCode`):
  - `GameAction`: logical action enum (return to menu, toggle terminal, open high scores,
    toggle spanning tree, apply path hint, move player).
  - `InputFrame<K>`: immutable per-frame snapshot (held keys, edge keys, mouse x/y as
    `double`, left-click) with `isHeld(K)` / `isEdge(K)`.
  - `EdgeKeyTracker<K>`: rising-edge latch for poll-based frontends.
  - `KeyBindingRegistry<K>` (with `BindingKind`, `KeyBinding<K>`): maps actions to keys and
    commands; `InputRouter<K>` resolves triggered actions and runs their commands.
  - `command.GameCommand<K>` and `command.GameCommandContext`: the command contract and a
    neutral side-effect facade. Each frontend supplies its own physical snapshot reader and
    command-context adapter.


`RocketEffect` (package `common.graphics.fire`) is the rpg rocket in flight (body, flame, smoke trail), drawn each frame by playing a fresh one that lives a moment, so both frontends need no layer of their own. Sprites and sounds of level 4 (`guard-tower*.png`, `rpg-launch.wav`, `rpg-explosion.wav`, `tower-hit.wav`, `tower-destroyed.wav`) come from `tools/sprite-gen/GuardTowerSpriteGenerator.java` and `tools/sound-gen/SoundEffectGenerator.java`.

`LavaEffect` (package `common.graphics.fire`) is the lava of level 6: dark banks, a red-orange river that glows, bright blobs that flow along each stream and the wooden bridges on top, as a chain of circles; both frontends draw it before the maze so the walls lie over it. The sound `lava-death.wav` comes from `tools/sound-gen/SoundEffectGenerator.java`.

`PulseChargeEffect` (the ball of light, rings and sparks while the pulse beam cannon charges, one short-lived frame at a time) and `PulseBeamEffect` (the huge white-hot and blue beam) are the shared visuals of the pulse beam cannon.

## Default behaviour with no backend installed

The defaults are deliberately inert so tests and library consumers do not
require a windowing system:

- `UiScheduler.get()` returns a `SynchronousUiScheduler` (runs work inline,
  reports itself as the UI thread).
- `AudioEngine.get()` returns a `NoopAudioEngine` (records the resources it
  was asked to play, never touches the audio device).

`reset()` on either singleton restores the inert default. `AudioEngine.set(...)`
disposes the previous engine before installing a new one.

## Installing a real backend

Backend modules expose an `install()` method that swaps the singletons:

- `main.game.maze.javafx.JavaFxBackend.install()` (see [maze-javafx](../maze-javafx/readme.md))
- `main.game.maze.libgdx.GdxBackend.install()` (see [maze-libgdx](../maze-libgdx/readme.md))

Production code (for example [App.java](../maze-javafx-backend/src/main/java/main/game/maze/App.java))
calls the appropriate `install()` before any gameplay code touches the
singletons.

## Tests

- [UiSchedulerTest](src/test/java/main/game/maze/common/graphics/UiSchedulerTest.java)
- [AudioEngineTest](src/test/java/main/game/maze/common/graphics/AudioEngineTest.java)
- [DamageStateTest](src/test/java/main/game/maze/common/graphics/DamageStateTest.java) — HP ratio threshold boundaries (0, 1, 25, 26, 50, 51, 75, 76, 100), scaled HP, and zero/negative maxHp handling
- [DamageStateResolverTest](src/test/java/main/game/maze/common/graphics/DamageStateResolverTest.java) — direction-suffixed and non-suffixed sprite path derivation, HEALTHY passthrough, and silent classpath fallback
- [WallRubbleModelTest](src/test/java/main/game/maze/common/graphics/fire/WallRubbleModelTest.java) — chunks start on the wall and fall to both sides, dust rises and settles, extent bounds, more chunks for longer walls, steel sparks, material from model id.
- [RemainsSpriteResolverTest](src/test/java/main/game/maze/common/remains/RemainsSpriteResolverTest.java), [RemainsLedgerTest](src/test/java/main/game/maze/common/remains/RemainsLedgerTest.java), [CrushZoneTest](src/test/java/main/game/maze/common/remains/CrushZoneTest.java) — remains sprite choice and fallback (and that all shipped remains exist), per-owner ledger with cap, crush geometry.
- [PumpkinBurstModelTest](src/test/java/main/game/maze/common/graphics/fire/PumpkinBurstModelTest.java) — puffs stay within `maxExtent()`, white-hot flash at the start, rind and stem debris fly outward, debris is not additive, ends in smoke, layer order, determinism per seed, lifetime and radius rules.
- [FireBlastModelTest](src/test/java/main/game/maze/common/graphics/fire/FireBlastModelTest.java) — puffs stay inside the corridors and arm reach, blocked arms stay empty, flame fronts travel outwards, white-hot core gives way to smoke, layer ordering, value ranges, determinism with clock flicker
- [CachingDamagePathResolverTest](src/test/java/main/game/maze/common/graphics/CachingDamagePathResolverTest.java) — fallback, one lookup per (path, state), pass-through for HEALTHY/blank, classpath factory.
- [DamageStateSpriteAssetsTest](src/test/java/main/game/maze/common/graphics/DamageStateSpriteAssetsTest.java) — every generated damage-state sprite (see [tools/sprite-gen](../tools/sprite-gen/readme.md)) resolves via `DamageStateResolver` and matches its healthy sprite's dimensions
- [PropertiesMazeVisualStyleLoaderTest](src/test/java/main/game/maze/common/graphics/config/PropertiesMazeVisualStyleLoaderTest.java)
- [XmiMazeVisualStyleLoaderTest](src/test/java/main/game/maze/common/graphics/config/XmiMazeVisualStyleLoaderTest.java)
- [EdgeKeyTrackerTest](src/test/java/main/game/maze/common/input/EdgeKeyTrackerTest.java)
- [KeyBindingRegistryTest](src/test/java/main/game/maze/common/input/KeyBindingRegistryTest.java)
- [InputRouterTest](src/test/java/main/game/maze/common/input/InputRouterTest.java)
- [GhostNonTangibilityServiceTest](src/test/java/main/game/maze/common/movement/GhostNonTangibilityServiceTest.java): single-arg backward-compat, two-arg overload (F25) — solid at full/partial visibility, phasing capped at `baseOpacity`, floor=0.1 while phasing, zero-visibility floor, clamp for negative and >100 input.
- [TerminalCommandParserTest](src/test/java/main/game/maze/common/input/TerminalCommandParserTest.java) — full alias/case/null coverage for `TerminalCommandParser`
- [ChasePlayerMovementServiceTest](src/test/java/main/game/maze/common/movement/ChasePlayerMovementServiceTest.java) — 6 tests for horizontal/vertical chase, blocked-axis fallback, and stability

### Test utilities (`src/test/java/main/game/maze/testutil`)

- `CapturingAudioEngine` — `IAudioEngine` test double that records all `play`, `playLoop`, `stopChannel`, and `playRateLimited` calls; use `AudioEngine.set(...)` / `AudioEngine.reset()` in `@AfterEach`.
- `FakeWorldView` — configurable `WorldView` stub for movement service tests; builder methods `playerAt(x,y)`, `bounds(w,h)`, `allBlocked()`, `blockAt(dir)`.
- `CapturingUiScheduler` — `IUiScheduler` test double with two modes:
  - **inline** (default, `new CapturingUiScheduler()`) — actions run immediately on the calling thread; both `runLater` and `runOnUiThread` execute and are tracked by `callCount()`.
  - **deferred** (`new CapturingUiScheduler(false)`) — actions are captured but not run until `flush()` is called; `flush()` is idempotent (each call drains only newly-added actions). Inspect all submitted actions via `capturedActions()` and total count via `callCount()`.

`RocketFlightEffect` (package `common.graphics.fire`) is the bunker buster rocket of the shop (#164) flying from the muzzle to where it stops: the `RocketEffect` drawn where the rocket is at each moment. The shop weapon icons, the soldier sprite (`pickups/grenade.png`, `plasma-grenade.png`, `nuke-grenade.png`, `bunker-buster.png`, `fat-bomb.png`, `airstrike.png`, `infantry-strike.png`, `life-refill.png`, `soldier.png`) and the sounds `shop-buy.wav`, `grenade-throw.wav`, `airstrike-siren.wav`, `infantry-whistle.wav` come from `tools/sprite-gen/PickupSpriteGenerator.java` and `tools/sound-gen/SoundEffectGenerator.java`.

`MushroomCloudEffect` (package `common.graphics.fire`) is the nuclear blast of the small nuclear grenade: a white flash, a shockwave ring, a skirt of dust and fire, a stem rising into a rolling cap that cools into smoke, and embers; the caller says which way is up on its screen (-1 JavaFX, +1 libGDX). Sound `nuke-explosion.wav`; the soldier sprites in `soldier/` come from `tools/sprite-gen/SoldierSpriteGenerator.java`, `soldier-down.wav` from the sound generator.

The textures of the screens between levels are in `ui/` (`panel.png`, `banner.png`, `hazard-strip.png`, `card*.png`, `slot.png`, `button-*.png`, `tier-*.png`, `token.png`, `lock.png`, `vignette.png`), drawn by `tools/sprite-gen/MenuThemeSpriteGenerator.java`. Which one is used where, and their 9-slice insets, is the menu theme model (`xmi/theme/menutheme.xmi` in maze-common-backend).
