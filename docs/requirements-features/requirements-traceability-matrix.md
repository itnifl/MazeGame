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

## Quality Attributes

1. Maintainability
Code centralizes movement decisions in shared services and exposes a minimal
overlay DTO (`ActivePathPoint`) to keep frontend drawing code thin.

2. Testability
Movement services are pure enough to execute against `WorldView` stubs and
assert deterministic behavior windows.

3. Parity and correctness
JavaFX and libGDX consume the same movement runtime state for enemy path
overlay rendering.