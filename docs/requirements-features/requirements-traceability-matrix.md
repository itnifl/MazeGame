# Requirements Traceability Matrix

## Scope

This matrix tracks movement and enemy-path overlay rules implemented in the
shared movement services and consumed by both JavaFX and libGDX frontends.

## Traceability

| Requirement | Design Pattern / Principle | Implementation | Verification Tests |
|---|---|---|---|
| GR-31, GR-32 | Shared service abstraction, single responsibility | `AdaptiveAggressiveMovementService.tick(...)` and `modeForEnemy(...)` | `AdaptiveAggressiveMovementServiceTest.afterFourSecondsStuckEnemyUsesShortestPathThenRecoversProgress`, `AdaptiveAggressiveMovementServiceTest.pathFollowModeRemainsActiveForTwentySecondsThenReturnsToDirectional` |
| GR-33 | Shared policy service, DRY parity across frontends | `AntiLoopWanderMovementService.tick(...)`, `PatrolMovementService.tick(...)` | `AntiLoopWanderMovementServiceTest.wanderBreaksOutOfTinyLoopArea`, `PatrolMovementServiceTest.noPathTriggersFiveSecondWanderThenRetriesWhenPathOpens` |
| GR-34 | Runtime state projection, backend-neutral DTO | `ActivePathPoint`, `AdaptiveAggressiveMovementService.currentPathForEnemy(...)`, `PatrolMovementService.currentPathForEnemy(...)`, JavaFX `GameController.drawEnemyNavigationPaths(...)`, libGDX `GdxGameScreen.drawEnemyPathOverlay(...)` | `AdaptiveAggressiveMovementServiceTest.pathFollowModeRemainsActiveForTwentySecondsThenReturnsToDirectional` (live path non-empty assertion), `PatrolMovementServiceTest.shortestPathMovesAroundBarrierInsteadOfPushingIntoIt` (live path non-empty assertion) |
| GR-35 | Deterministic core flow with constrained random turn choice | `AntiLoopWanderMovementService.pickRandomCardinal(...)` | `AntiLoopWanderMovementServiceTest.continuesStraightUntilCollisionThenTurns`, `AntiLoopWanderMovementServiceTest.allowsReverseWhenThatIsTheOnlyOpenDirection` |
| Terminal overlay discoverability | Command contract parity across frontends | libGDX `GdxGameScreen.parseTerminalCommand(...)` and `terminalHelpText()`, JavaFX terminal command parser/help text | `GdxGameScreenTerminalCommandTest.parsesShowEnemyPathCommand`, `GdxGameScreenTerminalCommandTest.helpTextMentionsEnemyPathDuration` |

| GR-26, GR-27 | Shared service (SRP, DRY), Strategy pattern | `GhostNonTangibilityService.isPhasing(...)`, `drainEnergy(...)`, `calculateOpacity(...)` in `maze-common-frontend` | `GhostNonTangibilityServiceTest` (11 tests), `GhostTangibilityParityTest.opacityCalculation_libgdxMatchesJavaFx` |
| GR-26, GR-28, GR-29 | Shared service, Strategy, Reset on session start | `GhostPhasingMovementService.tick(...)`, `reset()` in `maze-common-frontend`; integrated in `GameController` and `GdxGameScreen` | `GhostPhasingMovementServiceTest` (8 tests), `GhostTangibilityParityTest.phasingGhostDealsDamage_libgdxMatchesJavaFx`, `GhostTangibilityTest.phasingGhostDealsNoDamageThroughWall` |
| GR-26 (damage semantics) | OCP: wall-bypass ≠ damage-bypass | `GhostCharacter.doPositionEvaluation(...)` (JavaFX) skips wall check when phasing; `PlayerCombatStateService` (libGDX) skips wall check when phasing; both still apply contact damage | `GhostTangibilityTest.phasingGhostDealsNoDamageThroughWall`, `PlayerCombatStateServiceTest.phasingGhostDoesDamage_evenWithoutWall`, `PlayerCombatStateServiceTest.phasingGhostBypassesWallCheck_dealsDamage` |

## Quality Attributes

1. Maintainability
Code centralizes movement decisions in shared services and exposes a minimal
overlay DTO (`ActivePathPoint`) to keep frontend drawing code thin. Ghost
phasing logic is fully encapsulated in `GhostNonTangibilityService` and
`GhostPhasingMovementService`; both frontends delegate to them.

2. Testability
Movement services are pure enough to execute against `WorldView` stubs and
assert deterministic behavior windows. Ghost phasing services are tested via
unit tests with controlled inputs.

3. Parity and correctness
JavaFX and libGDX consume the same movement runtime state for enemy path
overlay rendering. Ghost phasing opacity and energy drain are guaranteed
identical via a shared service.