package main.game.maze.common.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class PatrolMovementServiceTest {

    @Test
    void shortestPathMovesAroundBarrierInsteadOfPushingIntoIt() {
        PatrolMovementService service = new PatrolMovementService(
                List.of(new PatrolMovementService.Waypoint(160d, 80d)));
        WorldView world = new GapBarrierWorld(200d, 200d, 80d, 150d, false);
        EnemyState enemy = new EnemyState("patrol", 40d, 80d, 1, 0, 8d, 8d);

        boolean sawVerticalStep = false;
        for (int i = 0; i < 20; i++) {
            MovementResult next = service.tick(enemy, world, 0.1d);
            if (next.directionY() != 0) {
                sawVerticalStep = true;
            }
            enemy = new EnemyState(enemy.id(), next.x(), next.y(), next.directionX(), next.directionY(), enemy.size(), enemy.speed());
        }

        assertTrue(sawVerticalStep,
                "with the direct lane blocked, patrol should eventually follow a shortest path that changes vertical axis");
        assertEquals(PatrolMovementService.PatrolMovementMode.PATH_FOLLOW, service.modeForEnemy("patrol"));
        assertTrue(!service.currentPathForEnemy("patrol", enemy.x(), enemy.y()).isEmpty(),
                "path-follow patrol should expose the live path it is currently using");
    }

    @Test
    void noPathTriggersFiveSecondWanderThenRetriesWhenPathOpens() {
        PatrolMovementService service = new PatrolMovementService(
                List.of(new PatrolMovementService.Waypoint(160d, 80d)));
        ToggleGapBarrierWorld world = new ToggleGapBarrierWorld(200d, 200d, 100d);
        EnemyState enemy = new EnemyState("retry", 40d, 80d, 1, 0, 8d, 8d);

        boolean sawWanderRecovery = false;
        boolean sawPathFollowAfterOpen = false;

        for (int i = 0; i < 120; i++) {
            if (i == 60) {
                world.barrierOpen = true;
            }
            MovementResult next = service.tick(enemy, world, 0.1d);
            enemy = new EnemyState(enemy.id(), next.x(), next.y(), next.directionX(), next.directionY(), enemy.size(), enemy.speed());

            if (service.modeForEnemy("retry") == PatrolMovementService.PatrolMovementMode.WANDER_RECOVERY) {
                sawWanderRecovery = true;
            }
            if (world.barrierOpen && service.modeForEnemy("retry") == PatrolMovementService.PatrolMovementMode.PATH_FOLLOW) {
                sawPathFollowAfterOpen = true;
            }
        }

        assertTrue(sawWanderRecovery, "patrol must wander for 5 seconds when no shortest path exists");
        assertTrue(sawPathFollowAfterOpen, "after the barrier opens patrol must retry and re-enter path follow mode");
    }

    @Test
    void currentTargetWaypointForReturnsNullBeforeFirstTick() {
        PatrolMovementService service = new PatrolMovementService(
                List.of(new PatrolMovementService.Waypoint(160d, 80d)));
        assertNull(service.currentTargetWaypointFor("new-enemy"),
                "before any tick the enemy has no target waypoint yet");
    }

    @Test
    void currentTargetWaypointForReturnsActiveWaypointAfterTick() {
        PatrolMovementService service = new PatrolMovementService(
                List.of(new PatrolMovementService.Waypoint(160d, 80d)));
        WorldView world = new GapBarrierWorld(200d, 200d, 80d, 150d, false);
        EnemyState enemy = new EnemyState("wp-enemy", 40d, 80d, 1, 0, 8d, 8d);
        service.tick(enemy, world, 0.1d);

        PatrolMovementService.Waypoint wp = service.currentTargetWaypointFor("wp-enemy");
        assertNotNull(wp, "after a tick the service should expose the active target waypoint");
        assertEquals(160d, wp.x(), 1e-9, "target waypoint X must match the configured waypoint");
        assertEquals(80d, wp.y(), 1e-9, "target waypoint Y must match the configured waypoint");
    }

    private record GapBarrierWorld(double maxX, double maxY, double wallX, double gapStartsAtY, boolean barrierOpen)
            implements WorldView {
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
            double half = size * 0.5d;
            if (centerX - half < 0d || centerY - half < 0d) {
                return true;
            }
            if (centerX + half > maxX || centerY + half > maxY) {
                return true;
            }
            if (barrierOpen) {
                return false;
            }
            double left = centerX - half;
            double right = centerX + half;
            return left <= wallX && right >= wallX && centerY < gapStartsAtY;
        }
    }

    private static final class ToggleGapBarrierWorld implements WorldView {
        private final double maxX;
        private final double maxY;
        private final double wallX;
        private boolean barrierOpen;

        private ToggleGapBarrierWorld(double maxX, double maxY, double wallX) {
            this.maxX = maxX;
            this.maxY = maxY;
            this.wallX = wallX;
        }

        @Override
        public double playerX() {
            return 0;
        }

        @Override
        public double playerY() {
            return 0;
        }

        @Override
        public double minX() {
            return 0d;
        }

        @Override
        public double minY() {
            return 0d;
        }

        @Override
        public double maxX() {
            return maxX;
        }

        @Override
        public double maxY() {
            return maxY;
        }

        @Override
        public boolean wouldCollide(double centerX, double centerY, double size) {
            double half = size * 0.5d;
            if (centerX - half < 0d || centerY - half < 0d) {
                return true;
            }
            if (centerX + half > maxX || centerY + half > maxY) {
                return true;
            }
            if (barrierOpen) {
                return false;
            }
            double left = centerX - half;
            double right = centerX + half;
            return left <= wallX && right >= wallX;
        }
    }
}
