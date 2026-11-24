# maze-javafx-backend

JavaFX frontend module for MazeGame. Provides the primary desktop UI built on JavaFX.

## Key responsibilities

- **App** — entry point; sizes the window to `min(boardSize, screenResolution)` (windowed only, never fullscreen)
- **FxGameRenderCoordinator** — renders the maze canvas and handles player-follow camera translation
- **FxGameLayoutSupport / FxDifficultyPickerSupport** — scene layout and difficulty selection UI
- **Router** — view-to-view navigation (main menu → difficulty picker → game → end screen)

## Camera follow (F27)

`FxGameRenderCoordinator.computeCameraTranslation(viewW, viewH, worldW, worldH, playerX, playerY)`
returns `[translateX, translateY]` for the canvas group transform:

- Both axes fit: returns `[0, 0]`
- World wider than viewport: `translateX = viewW/2 − playerX`, clamped to `[viewW − worldW, 0]`
- World taller than viewport: `translateY = viewH/2 − playerY`, clamped to `[viewH − worldH, 0]`

`App.clampBoardToScreen(boardW, boardH, screenW, screenH)` ensures the stage is never larger than the primary screen's visual bounds.

## Weapons, pickups and kill credit

Same shared rules as libGDX (`maze-common-backend` `game.items` / `game.score`):

- `FxWeaponController` (the counterpart of libGDX `GdxWeaponSupport`) owns the per-game `PickupField`, `PlayerArsenal`,
  `PowerShield` and `KillLedger`: `useWeapon`, `switchWeapon`, `tick` (spawn and collect items every frame) and
  `recordKill`. It reaches the game screen only through its small `Host` interface, so `FxWeaponControllerTest`
  runs without FXML. `FxPickupLayer` draws the items, the weapon in the player's hands (mirrored to the facing),
  the shield bubble and, after a heart, the flash and the arrow rising over the player (`showHeal`).
- `FxEnemyCoordinator.fireLaser` / `swingSledgehammer` apply laser and sledgehammer damage through `ICanDie`
  with the player's id; struck enemies play their `HitReactionEffect` (`ComputerCharacter.react`).
- `PlayerCharacter.setShield` / `heal` / `getFacing`; Tab switches weapons (`SwitchWeaponCommand`).
- `FxWeaponHudView` shows the weapons HUD (top-left) from the shared `WeaponHud` model.
- `FxPlayerLifeController` receives the player's death: with lives left it respawns the player at the start
  (`PlayerCharacter.revive`) after 3 s with 2 s of protection, otherwise it passes the death to the game-over action.
- Movement keys come from `JavaFxMovementKeys` (arrows, numpad, Q/A, W, E, S/D); a right click uses the weapon.
- A dying enemy reports itself to `GameController.recordKill` (`KillLedger`); the score includes the kill bonus and
  the live score label refreshes at once.

## Building & running

```bash
mvn package -pl maze-javafx-backend -am
java -jar target/maze-javafx-backend-*.jar
```

Requires **Java 21**. See [root README](../readme.md) for auto-detection details.

## Tests

```bash
mvn test -pl maze-javafx-backend
```

Pure-math helpers (`computeCameraTranslation`, `clampBoardToScreen`) are tested headlessly — no JavaFX toolkit required.

`FxExplosionViewTest` covers the explosion view (`javafx.render.FxExplosionView`, drawn from the
shared `FireBlastModel` / `PumpkinBurstModel` on a transparent canvas). It is used for the player's
bomb fire blast and for pumpkin bomber projectile bursts (`PumpkinBomberCharacter`). The tests check
that the canvas covers every puff for the whole life of both kinds, progress timing, that a fire
blast frame paints fire at the centre, and that a pumpkin burst frame paints solid pumpkin debris.

`FxMortalityParityTest` checks JavaFX enemies follow the same life rules as libGDX (shared `EnemyVitals` / `MortalityRules`): ghosts are mortal and reached by bomb fire, crushing walls and `/kill`; a resurrected zombie is whole and briefly invulnerable; the zombie resurrection default applies.

`FxGuardTowerControllerTest` and `GuardTowerSpawnerTest` cover the level 4 guard towers (`FxGuardTowerController`, `GuardTowerSpawner`, `GuardTowerCharacter`): the shared `GuardTowerSession` decides the rpg every 3 seconds and the gatling in between, the walls a tower may shoot over (3) and what hits the player; the controller draws the rockets, plays the sounds (launch, explosion, gatling, hit, destroyed) and starts the blasts. A tower is a zombie-model enemy that never moves, shows its damage and leaves no corpse.

`FxWallBuilderControllerTest` and `WallBuilderSpawnerTest` cover the level 5 wall builders (`FxWallBuilderController`, `WallBuilderSpawner`, `WallBuilderCharacter`): the shared `WallBuilderSession` decides where a builder goes and what it builds; the controller moves the builder, adds the wooden wall to the world and redraws the maze. A builder is a zombie-model enemy with no touch damage and no resurrection.

`FxLavaLayer` draws the lava of level 6 (the shared `LavaEffect`) on a canvas at the very bottom of the board, below the maze canvas, redrawn every frame so it flows; `GameController.tickLava` kills the player who is in deadly lava (`PlayerCharacter.killOutright`, the shield does not help) and `FxEnemyCoordinator` keeps enemies out of it. `PlayerLavaDeathTest` covers the death.

`FxRemainsLayerTest` covers enemy death remains (`javafx.render.FxRemainsLayer`): remains lie just
above the maze canvas at the spot where the enemy died, one per enemy (a second death replaces it), cleared
when the enemy resurrects or the level restarts, capped (oldest removed from the board), nothing for
unknown causes. `FxEnemyCoordinatorTest` checks bomb damage is tagged `FIRE`, `killAll(cause)`, and that
`crushAlong` crushes only enemies touching a collapsed wall. Collapsing walls also play
`FxExplosionView.of(WallRubble)`.

## Clip ownership

`FxGameRenderCoordinator` is the **sole owner** of the `gameBoard` clip rectangle. `GameController` must never call `gameBoard.setClip()` — JavaFX's last-call-wins semantics would override the coordinator's correctly-offset clip and reintroduce the white-scroll-area bug.

`GameController.installCameraFollowListeners()` now only installs resize listeners; all clip management is in `FxGameRenderCoordinator.updateCameraFollow()`.

Viewport dimensions are read from `gameBoard.getScene().getWidth()/getHeight()` (the actual visible scene area), not from `root.getBoundsInLocal()` (which returns the board's own size after `FxGameSessionBootstrapper` pins it to `boardMaxX × boardMaxY`).

## Background rendering

`FxMazeCanvasRenderer.drawCanvas()` fills the full-map canvas with the difficulty-specific background image (tiled via `ImagePattern`) **before** painting walls. This ensures all areas revealed by the camera scroll have a background, because the `gameBoard` Pane's own background image only tiles within the Pane's window-sized layout bounds.

## Test suite documentation

- `ZombieCharacterTest` — construction, `getDamage`, partial HP subtraction, audio-on-overlap, death-subscriber management, and `getModel`.
- `ComputerCharacterDamageStateTest` — F80 damage-state sprites: HP changes swap to damaged/bleeding/tired sprites per direction and walk frame (only when `damageStatesEnabled`), missing sprites fall back silently, and the pumpkin bomber uses `pumpkinbomber-left/up.png` (or runtime mirror/tint when all its paths are the same file) including the matching damage sprites.
- `PumpkinBomberCharacterTest` — construction, `getDamage`, partial HP subtraction, `setHitPoints`/`addHitPoints`, `doPositionEvaluation` no-throw, `getModel`, and F26 physics: `lobProjectile_arrivesAfterDistanceDividedBySpeed_seconds` (LOB arrives in exactly `distance/speed` seconds) and `lobProjectile_stillInFlightBeforeDistanceDividedBySpeed_seconds` (LOB still active before arrival).

### Wall renderer tests (wall registry classpath regression)

- `FxMazeCanvasRendererTest` — 6 tests verifying `WallRegistry` initialises without `ExceptionInInitializerError/NoClassDefFoundError` (classpath regression guard for `main.game.maze.walls` explicit dep), registry has at least one material, `DIRT_BASIC` is resolvable, and `drawCanvas` completes without throwing for empty vectors, unknown difficulty, and null difficulty supplier.

### Spawn factory tests (single-attempt spawn regression)

- `OpponentRuntimeFactorySpawnTest` — 6 tests verifying `spawnByTarget` fills all requested slots when all candidates fit, always finds a fitting candidate in a mixed pool (regression for single-attempt pick bug), returns 0 spawns when no candidate fits the budget, enforces per-type caps, fills both ghost and zombie slots independently, and that `instantiateFromModel` schedules at least one enemy with the default model.

### Enemy coordinator tests (require `Platform.startup()`)

- `FxEnemyCoordinatorTest` — 6 headless lifecycle tests using null-returning suppliers (no real scene graph): `stepAll` with no enemies, `reset`, `showEnemyDebugLabels` with null board (early-return guard), `drawEnemyNavigationPaths` with null maze (early-return guard), `dispose`, and `dispose` called twice (idempotent guard).

### Test doubles (`src/test/java/main/game/maze`)

- `SpyActionSink` — records all `ActionSink` method calls for verifying command dispatch without real side-effects. Must stay in package `main.game.maze` to access the package-private `ActionSink` interface.


**Shop between levels (#164).** The win screen has a Shop button while a next level follows (`WinGameController.openShop`). `ShopAction` swaps in `shopScreen.fxml` (view) with `ShopController` (controller) over the shared `ShopMenu` (model): Up/Down choose, Enter or Space buy, Esc or Done close, a click selects and a double click buys. Closing puts the win screen back with the score that is left. The shop weapons go through `FxWeaponController`: grenades, the fat bomb, the airstrike and the infantry strike are planned by the shared `StrikeWeapons` and set off through `Host.blast` (`GameController.blastAt`), the bunker buster is a piercing shot (`FxEnemyCoordinator.fireLaser`) with a blast at the end, the extra life heals the player. `FxStrikeLayer` draws the soldiers and the fuse markers; `FxEnemyCoordinator.livingTargets` is what the soldiers shoot at. `ShopControllerTest` loads the real FXML and drives it with keys.

Review follow-up: `FxStrikeLayer` draws the animated soldiers and the grenades lying on the floor (green ring round the nuke); `GameController.nuclearBlastAt` sets off the nuclear blast (`NuclearBlastEngine`, `MushroomCloudEffect`, `FxScreenShake.play(node, seconds, strength)`); `FxEnemyCoordinator.setSoldiers` makes enemies near a soldier chase it (a `ThreadLocal` chase target read by the coordinator's world view on the AI thread). Locked shop tiers are shown greyed.

Ranged enemies shoot the soldiers first (SR-400): `FxWeaponController.soldierTargets()` is the squad as a `SoldierTargets` port whose hits are passed to the FX thread; `FxEnemyCoordinator.setSoldiers(SoldierTargets, lureRadius)` keeps it as centres (the sergeants' `Range.soldiers`) and as top-left corners (the chase of SR-396 and the pumpkin bombers, `PumpkinBomberCharacter.setSoldiers` / `canHit`); `FxGuardTowerController.Host.soldiers` gives it to the towers.

The win screen and the shop are themed (SR-403/404): `javafx.menu.FxMenuTheme` renders the stylesheet of those screens from the shared menu theme with the FreeMarker template `main/game/maze/javafx/menu/menu-theme.css.ftl` (a `data:` stylesheet, built once) and styles the buttons (`style(Button, MenuButton)`); `winScreen.fxml` and `shopScreen.fxml` only name style classes (`menu-panel`, `menu-banner`, `menu-hazard`, `menu-card`, ...). `ShopController` builds a shelf per tier (`shelf`) of crate cards (`card`); Left/Right or A/D move across the shelves. `BetweenLevelScreensTest` renders both screens to `target/test-classes/target/menu-snapshots/` for looking at them. FreeMarker 2.3.32 is a runtime dependency of this module.

The bomb lies as the bomb (SR-413): `FxWeaponController.plantBomb` plants it in the shared strike schedule, so `FxStrikeLayer.showFuses` draws it like the grenades; `FxStrikeLayer.showFalling` draws the airstrike bombs falling onto their shadows. The main menu (`startScreen.fxml`, `StartController.applyTheme`) has the menu theme too (SR-414): title plate, panel, a crate-card difficulty picker (`menu-combo`) and metal-plate buttons.

Dropped weapons lie armed (SR-418): `FxStrikeLayer.showFuses` asks `StrikeVisuals.lyingSprite(blast, items, clock)` for the armed frame. Help & Controls (SR-420): `StartController.onHelp` opens `helpScreen.fxml` (`HelpController`, `actions.HelpAction`) over the menu: the shared `ControlsGuide` as keycaps (`menu-key`) and arrow keys, with icons.
