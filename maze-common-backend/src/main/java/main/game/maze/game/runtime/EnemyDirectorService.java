package main.game.maze.game.runtime;

import java.util.List;
import main.game.maze.common.movement.AdaptiveAggressiveMovementService;
import main.game.maze.common.movement.AntiLoopWanderMovementService;
import main.game.maze.common.movement.GhostPhasingMovementService;
import main.game.maze.common.movement.PatrolMovementService;
import main.game.maze.common.movement.WorldView;

/**
 * Orchestrates enemy movement services for one frame.
 */
public final class EnemyDirectorService {

    private final AntiLoopWanderMovementService wanderService;
    private final AdaptiveAggressiveMovementService adaptiveService;
    private final PatrolMovementService patrolService;
    private final GhostPhasingMovementService phasingService;

    public EnemyDirectorService() {
        this(new AntiLoopWanderMovementService(),
                new AdaptiveAggressiveMovementService(),
                new PatrolMovementService(),
                new GhostPhasingMovementService());
    }

    EnemyDirectorService(
            AntiLoopWanderMovementService wanderService,
            AdaptiveAggressiveMovementService adaptiveService,
            PatrolMovementService patrolService,
            GhostPhasingMovementService phasingService) {
        this.wanderService = wanderService;
        this.adaptiveService = adaptiveService;
        this.patrolService = patrolService;
        this.phasingService = phasingService;
    }

    public void advanceAll(List<? extends EnemyRuntime> enemies, WorldView world, float dt) {
        if (enemies == null || enemies.isEmpty() || world == null) {
            return;
        }
        for (EnemyRuntime enemy : enemies) {
            enemy.advance(world, wanderService, patrolService, adaptiveService, phasingService, dt);
        }
    }

    public void reset() {
        wanderService.reset();
        adaptiveService.reset();
        patrolService.reset();
        phasingService.reset();
    }

    public AdaptiveAggressiveMovementService adaptiveService() {
        return adaptiveService;
    }

    public PatrolMovementService patrolService() {
        return patrolService;
    }
}
