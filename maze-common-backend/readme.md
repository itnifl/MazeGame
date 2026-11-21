# maze-common-backend

Renderer-neutral backend services, interfaces, constants, and DTOs shared by
both the JavaFX and libGDX frontends. No rendering or input code lives here.

## What lives here

| Package | Contents |
|---|---|
| `characters` | `CollisionDamage` — damage constants and instant-kill threshold |
| `characters.interfaces` | `ICanDie` (the hit-point / death contract for both frontends: JavaFX characters and libGDX `GdxEnemyRuntime`; damage with a `DeathCause`, `isAlive()`, `deathCause()`, death subscribers), `ICanKill`, `ICanLetYouWin`, `ICanSubscribeAndNotifyPosition`, `ICharacterAction`, `ICharacterAnimations`, `IHaveModel`, `IMovingComputerCharacter`, `INonTangientMazeGameCharacter`, `ISubscribeOnDirection`, `PositionBounds` |
| `config.model` | `PlayerConfig` — player defaults (health, speed, enabled flag, display name, image paths) |
| `config.service` | `CompositionResolver` (interface), `CompositionResolverImpl` — enemy composition from difficulty profile with per-type cap enforcement; `ProfileRules`, `XmiRulesLoader` |
| `constants` | `AudioChannelConstants`, `ColorHueConstants`, `DataFileConstants`, `DifficultyResourceConstants`, `ImageResourceConstants`, `OpponentConstants`, `PlayerConstants` |
| `dto` | `HighScoreFile`, `Score` |
| `game.audio` | `GameAudioDirector` — channel-routed audio transitions (menu, in-game, win, game-over) |
| `game.runtime` | `EnemyDirectorService`, `EnemyRuntime`, `RetiredShooters` (dead ranged enemies kept only until their shots in the air land; both frontends) |
| `game.level` | Levels, shared by both frontends: `LevelCatalogLoader` (reads `xmi/levels/levels.ecore` / `levels.xmi` with dynamic EMF and fails fast on broken levels), `LevelSpec` (zones, threat capacity, sergeants; `applyWalls` zones the walls of a generated maze), `SergeantSpec`, `SergeantGunner` (sight + burst/cooldown), `SergeantPosts` (where sergeants stand), `LevelProgress` (current level and the weapons carried over). To make a level see [docs/level-authoring.md](../docs/level-authoring.md) |
| `game.hazard` | The level 3 hazards, shared by both frontends: `MineField` (a circle of pulsating mines round each sergeant, the holes they leave, touch and fall detection), `MazeHazards` (free-ground placement), `EdgeTeleport` (the opposite edge for a hole, a random outer edge for a portal ghost, always a free point), `TeleportGate` (cooldown), `WallCrossing` (walls between two points); level 4 adds the second circle of mines |
| `game.builder` | The level 5 wall builders, shared by both frontends: `CellGrid` (the maze as cells one corridor wide with edges closed by three wall pieces), `WallBuilderSession` (finds the open corridors into the block round the heart, walks the builders there and builds wood walls a corridor width per 3 seconds) |
| `game.items` | Weapons and pickups shared by both frontends: `PickupCatalogLoader` (reads the `xmi/items/items.ecore` metamodel and `items.xmi` with dynamic EMF), `PickupCatalog` / `PickupSpec` / `PickupKind` / `Weapon`, `PickupField` (spawns an item every interval up to the cap for the map size, only on corridor points clear of every wall (`spawnPointsOf`), collects items the player walks over), `PlayerArsenal` (bombs, laser shots, sledgehammer swings, selected weapon), `PowerShield`, `PickupEffects` (also the extra life), `ArsenalHud`, `WeaponHud` (the top-left weapons HUD model: icons, the one in hand, lives) |
| `game.score` | `FileHighScoreRepository`, `HighScoreRepository`, `PathHintBudget`, `ScoringEngine` (includes the kill bonus), `KillLedger` (who killed what, per player), `KillScoring` (points by the enemy's danger), `PlayerIds` |
| `game.session` | `GameMode` enum, `GameSession`, `PlayerLives` (lives per difficulty, extra lives, respawn or game over, respawn delay and protection) |
| `game.status` | `StatusMessageBus` — publish/subscribe for transient HUD status messages |
| `interfaces` | `IDeathSubscriber`, `INotifyMovement` |
| `runtime` | `OclBootstrap` — OCL/EMF delegate factory initialisation |
| `service` | `DifficultyService` (XMI-backed difficulty list), `DifficultyPresentationSupport`, `MortalityService` (kill-all, crushing and sledgehammer strikes through `ICanDie`, all with an attacker id), `EnemyVitals` (the single HP / death / respawn-protection implementation behind `ICanDie` for libGDX and JavaFX enemies), `MortalityRules` (per-type max HP, resurrection delay, respawn protection from the opponent model) |

## Dependency chain

```text
maze-common-backend
  ├── maze-common-frontend    (IUiScheduler, IAudioEngine, ICharacterView)
  ├── main.game.maze.mazeworld
  ├── main.game.maze.difficulties
  └── main.game.maze.opponents
```

System-scope OCL runtime JARs are bundled in `lib/` and declared as `system`
scope in `pom.xml` (these are not available from Maven Central):

- `org.eclipse.ocl-3.4.0-v20140524-1358.jar`
- `org.eclipse.ocl.ecore-3.3.0-v20130520-1222.jar`
- `org.eclipse.ocl.common-1.2.0-v20140528-1458.jar`
- `java-2.0.17-v201004271640.jar` (LPG runtime)

## CI coverage gate

The `coverage` Maven profile (activated automatically in CI via `env.CI`) binds
JaCoCo 0.8.12 with a **60 % LINE** minimum. The profile is inherited from root
`pluginManagement`; no configuration override is needed in this module.

## Tests

- [CompositionResolverImplTest](src/test/java/main/game/maze/config/service/CompositionResolverImplTest.java) — 8 tests: single ratio, override precedence, total=enemyCount invariant, idempotent, unknown-profile IAE, null/empty-profiles NPE/ISE, caps respected, requested-exceeds-cap-sum stops at cap total.
- [EnemyVitalsTest](src/test/java/main/game/maze/service/EnemyVitalsTest.java) — damage and the killing blow (cause, one notification), capped healing that never revives, respawn with invulnerability window.
- [MortalityRulesTest](src/test/java/main/game/maze/service/MortalityRulesTest.java) — max HP from model health, zombie resurrection (model time or default), other types stay dead.
- [RetiredShootersTest](src/test/java/main/game/maze/game/runtime/RetiredShootersTest.java) — dead shooters kept only while shots are in flight, no duplicates, cleared per level.
- [MortalityServiceTest](src/test/java/main/game/maze/service/MortalityServiceTest.java) — `killAll` kills only living `ICanDie`s with the cause, `crushAlong` crushes only those touching the wall, no body means no crush, `ICanDie` defaults.
- [DifficultyServiceTest](src/test/java/main/game/maze/service/DifficultyServiceTest.java) — returns 3 difficulties, each non-null, getCurrent before set no-throw, setCurrent persists, list stable.
- [PlayerConfigTest](src/test/java/main/game/maze/PlayerConfigTest.java) — health > 0, speed > 0, enabled = true, matches PlayerConstants, non-blank id/displayName, non-null image paths, toString/equality/hashCode.
- [GameAudioDirectorTest](src/test/java/main/game/maze/game/audio/GameAudioDirectorTest.java)
- [EnemyDirectorServiceTest](src/test/java/main/game/maze/game/runtime/EnemyDirectorServiceTest.java)
- [FileHighScoreRepositoryTest](src/test/java/main/game/maze/game/score/FileHighScoreRepositoryTest.java)
- [PathHintBudgetTest](src/test/java/main/game/maze/game/score/PathHintBudgetTest.java)
- [ScoringEngineTest](src/test/java/main/game/maze/game/score/ScoringEngineTest.java) — 15 tests covering `gameplayScore`, `characterScreenScore` (GR-36 death penalty 5 000, GR-37 damage × 10), breakdown parity, win bonus, and edge cases
- [StatusMessageBusTest](src/test/java/main/game/maze/game/status/StatusMessageBusTest.java)
- [DifficultyPresentationSupportTest](src/test/java/main/game/maze/service/DifficultyPresentationSupportTest.java)
- [HighScoreFileTest](src/test/java/main/game/maze/dto/HighScoreFileTest.java)
- [OclBootstrapTest](src/test/java/main/game/maze/runtime/OclBootstrapTest.java)
- [Point2DOperationsTest](src/test/java/main/game/maze/Point2DOperationsTest.java), [ScoreTest](src/test/java/main/game/maze/ScoreTest.java), [Vector2D\*Tests](src/test/java/main/game/maze/) — geometry and DTO invariants.
- [WallCollisionUtilWallBetweenTest](src/test/java/main/game/maze/WallCollisionUtilWallBetweenTest.java)
- [XmiRulesLoaderPlayerConfigTest](src/test/java/main/game/maze/XmiRulesLoaderPlayerConfigTest.java)

`game.level` also holds the savepoint (`SavePoint`, `SavePointStore` with `FileSavePointStore` / `InMemorySavePointStore`, used by `LevelProgress`): made when a level is completed, kept until a new game, restored when a level is restarted.
