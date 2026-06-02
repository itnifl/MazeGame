package main.game.maze.game.runtime;

import main.game.maze.common.movement.AdaptiveAggressiveMovementService;
import main.game.maze.common.movement.AntiLoopWanderMovementService;
import main.game.maze.common.movement.GhostPhasingMovementService;
import main.game.maze.common.movement.PatrolMovementService;
import main.game.maze.common.movement.WorldView;

/**
 * Backend runtime contract for enemy movement ticks.
 */
public interface EnemyRuntime {

    void advance(
            WorldView world,
            AntiLoopWanderMovementService wanderService,
            PatrolMovementService patrolService,
            AdaptiveAggressiveMovementService adaptiveService,
            GhostPhasingMovementService phasingService,
            float dt);
}
