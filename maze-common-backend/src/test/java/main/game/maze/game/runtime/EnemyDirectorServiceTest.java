package main.game.maze.game.runtime;

import java.util.List;
import main.game.maze.common.movement.AdaptiveAggressiveMovementService;
import main.game.maze.common.movement.AntiLoopWanderMovementService;
import main.game.maze.common.movement.GhostPhasingMovementService;
import main.game.maze.common.movement.PatrolMovementService;
import main.game.maze.common.movement.WorldView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnemyDirectorServiceTest {

    @Test
    void advanceAllCallsEachEnemyOnce() {
        EnemyDirectorService director = new EnemyDirectorService();
        CountingEnemyRuntime first = new CountingEnemyRuntime();
        CountingEnemyRuntime second = new CountingEnemyRuntime();

        director.advanceAll(List.of(first, second), new EmptyWorld(), 0.16f);

        assertEquals(1, first.calls);
        assertEquals(1, second.calls);
    }

    private static final class CountingEnemyRuntime implements EnemyRuntime {
        int calls;

        @Override
        public void advance(
                WorldView world,
                AntiLoopWanderMovementService wanderService,
                PatrolMovementService patrolService,
                AdaptiveAggressiveMovementService adaptiveService,
                GhostPhasingMovementService phasingService,
                float dt) {
            calls++;
        }
    }

    private static final class EmptyWorld implements WorldView {
        @Override
        public double playerX() {
            return 0;
        }

        @Override
        public double playerY() {
            return 0;
        }

        @Override
        public boolean wouldCollide(double centerX, double centerY, double size) {
            return false;
        }
    }
}


