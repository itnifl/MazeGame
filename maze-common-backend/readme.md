# maze-common-backend

Renderer-neutral backend services, interfaces, constants, and DTOs shared by
both the JavaFX and libGDX frontends. No rendering or input code lives here.

## What lives here

| Package | Contents |
|---|---|
| `characters` | `CollisionDamage` — damage constants and instant-kill threshold |
| `characters.interfaces` | `ICanDie`, `ICanKill`, `ICanLetYouWin`, `ICanSubscribeAndNotifyPosition`, `ICharacterAction`, `ICharacterAnimations`, `IHaveModel`, `IMovingComputerCharacter`, `INonTangientMazeGameCharacter`, `ISubscribeOnDirection`, `PositionBounds` |
| `config.model` | `PlayerConfig` — player defaults (health, speed, enabled flag, display name, image paths) |
| `config.service` | `CompositionResolver` (interface), `CompositionResolverImpl` — enemy composition from difficulty profile with per-type cap enforcement; `ProfileRules`, `XmiRulesLoader` |
| `constants` | `AudioChannelConstants`, `ColorHueConstants`, `DataFileConstants`, `DifficultyResourceConstants`, `ImageResourceConstants`, `OpponentConstants`, `PlayerConstants` |
| `dto` | `HighScoreFile`, `Score` |
| `game.audio` | `GameAudioDirector` — channel-routed audio transitions (menu, in-game, win, game-over) |
| `game.runtime` | `EnemyDirectorService`, `EnemyRuntime` |
| `game.score` | `FileHighScoreRepository`, `HighScoreRepository`, `PathHintBudget`, `ScoringEngine` |
| `game.session` | `GameMode` enum, `GameSession` |
| `game.status` | `StatusMessageBus` — publish/subscribe for transient HUD status messages |
| `interfaces` | `IDeathSubscriber`, `INotifyMovement` |
| `runtime` | `OclBootstrap` — OCL/EMF delegate factory initialisation |
| `service` | `DifficultyService` (XMI-backed difficulty list), `DifficultyPresentationSupport` |

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
- [DifficultyServiceTest](src/test/java/main/game/maze/service/DifficultyServiceTest.java) — returns 3 difficulties, each non-null, getCurrent before set no-throw, setCurrent persists, list stable.
- [PlayerConfigTest](src/test/java/main/game/maze/PlayerConfigTest.java) — health > 0, speed > 0, enabled = true, matches PlayerConstants, non-blank id/displayName, non-null image paths, toString/equality/hashCode.
- [GameAudioDirectorTest](src/test/java/main/game/maze/game/audio/GameAudioDirectorTest.java)
- [EnemyDirectorServiceTest](src/test/java/main/game/maze/game/runtime/EnemyDirectorServiceTest.java)
- [FileHighScoreRepositoryTest](src/test/java/main/game/maze/game/score/FileHighScoreRepositoryTest.java)
- [PathHintBudgetTest](src/test/java/main/game/maze/game/score/PathHintBudgetTest.java)
- [ScoringEngineTest](src/test/java/main/game/maze/game/score/ScoringEngineTest.java)
- [StatusMessageBusTest](src/test/java/main/game/maze/game/status/StatusMessageBusTest.java)
- [DifficultyPresentationSupportTest](src/test/java/main/game/maze/service/DifficultyPresentationSupportTest.java)
- [HighScoreFileTest](src/test/java/main/game/maze/dto/HighScoreFileTest.java)
- [OclBootstrapTest](src/test/java/main/game/maze/runtime/OclBootstrapTest.java)
- [Point2DOperationsTest](src/test/java/main/game/maze/Point2DOperationsTest.java), [ScoreTest](src/test/java/main/game/maze/ScoreTest.java), [Vector2D\*Tests](src/test/java/main/game/maze/) — geometry and DTO invariants.
- [WallCollisionUtilWallBetweenTest](src/test/java/main/game/maze/WallCollisionUtilWallBetweenTest.java)
- [XmiRulesLoaderPlayerConfigTest](src/test/java/main/game/maze/XmiRulesLoaderPlayerConfigTest.java)
