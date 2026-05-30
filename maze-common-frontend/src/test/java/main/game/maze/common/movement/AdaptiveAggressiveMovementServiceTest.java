package main.game.maze.common.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class AdaptiveAggressiveMovementServiceTest {

    @Test
    void doesNotTriggerPathBeforeFourSecondsStuck() {
        AdaptiveAggressiveMovementService service = new AdaptiveAggressiveMovementService();
        WorldView world = new FlatWorldView(180d, 120d,
            List.of(
                new SimpleWall(64d, 112d, 88d, 112d, true),
                new SimpleWall(64d, 136d, 88d, 136d, true),
                new SimpleWall(64d, 112d, 64d, 136d, false),
                new SimpleWall(88d, 112d, 88d, 136d, false)),
                240d, 240d);

        EnemyState enemy = new EnemyState("z", 72d, 120d, 1, 0, 16d, 4d);

        for (int i = 0; i < 20; i++) {
            MovementResult result = service.tick(enemy, world, 0.1d);
            assertFalse(result.moved(), "enemy should remain blocked prior to fallback window");
            enemy = advance(enemy, result);
        }
    }

    @Test
    void afterFourSecondsStuckEnemyUsesShortestPathThenRecoversProgress() {
        AdaptiveAggressiveMovementService service = new AdaptiveAggressiveMovementService();
        WorldView world = new FlatWorldView(180d, 120d,
                List.of(new SimpleWall(80d, 40d, 80d, 200d, false)),
                240d, 240d);

        EnemyState enemy = new EnemyState("z", 72d, 120d, 1, 0, 16d, 4d);
        double startDistance = distance(enemy.x(), enemy.y(), world.playerX(), world.playerY());

        boolean movedAfterTrigger = false;
        for (int i = 0; i < 120; i++) {
            MovementResult result = service.tick(enemy, world, 0.1d);
            if (i >= 40 && result.moved()) {
                movedAfterTrigger = true;
            }
            enemy = advance(enemy, result);
        }

        double endDistance = distance(enemy.x(), enemy.y(), world.playerX(), world.playerY());
        assertTrue(movedAfterTrigger,
                "stuck enemy should switch to path-follow movement after the 4 second threshold");
        assertTrue(endDistance < startDistance,
                "path fallback should help enemy make net progress toward player");
    }

    @Test
    void pathFollowModeRemainsActiveForTwentySecondsThenReturnsToDirectional() {
        AdaptiveAggressiveMovementService service = new AdaptiveAggressiveMovementService();
        WorldView world = new FlatWorldView(180d, 120d,
                List.of(new SimpleWall(80d, 40d, 80d, 200d, false)),
                240d, 240d);

        EnemyState enemy = new EnemyState("z", 72d, 120d, 1, 0, 16d, 4d);

        for (int i = 0; i < 41; i++) {
            MovementResult result = service.tick(enemy, world, 0.1d);
            enemy = advance(enemy, result);
        }

        assertEquals(AdaptiveAggressiveMovementService.AggressiveMovementMode.PATH_FOLLOW,
                service.modeForEnemy("z"),
            "enemy should be in path-follow mode shortly after the 4 second stuck trigger");
        assertTrue(!service.currentPathForEnemy("z", enemy.x(), enemy.y()).isEmpty(),
            "path-follow mode should expose the live path the enemy is currently following");

        for (int i = 0; i < 210; i++) {
            MovementResult result = service.tick(enemy, world, 0.1d);
            enemy = advance(enemy, result);
        }

        assertEquals(AdaptiveAggressiveMovementService.AggressiveMovementMode.DIRECTIONAL,
                service.modeForEnemy("z"),
                "enemy should return to directional chase after 20 seconds of path-follow mode");
    }

    @Test
    void noPathFallsBackToWanderAndRetriesUntilPathWindowCanComplete() {
        AdaptiveAggressiveMovementService service = new AdaptiveAggressiveMovementService();
        ToggleBarrierWorld world = new ToggleBarrierWorld(220d, 120d, 240d, 240d);

        EnemyState enemy = new EnemyState("z", 32d, 120d, 1, 0, 16d, 4d);
        boolean sawWanderRecovery = false;
        boolean sawPathFollowAfterOpen = false;
        boolean sawDirectionalAfterSuccessfulPath = false;

        for (int i = 0; i < 520; i++) {
            if (i == 170) {
                world.barrierOpen = true;
            }
            MovementResult result = service.tick(enemy, world, 0.1d);
            enemy = advance(enemy, result);

            var mode = service.modeForEnemy("z");
            if (mode == AdaptiveAggressiveMovementService.AggressiveMovementMode.WANDER_RECOVERY) {
                sawWanderRecovery = true;
            }
            if (world.barrierOpen
                    && mode == AdaptiveAggressiveMovementService.AggressiveMovementMode.PATH_FOLLOW) {
                sawPathFollowAfterOpen = true;
            }
            if (sawPathFollowAfterOpen
                    && mode == AdaptiveAggressiveMovementService.AggressiveMovementMode.DIRECTIONAL) {
                sawDirectionalAfterSuccessfulPath = true;
            }
        }

        assertTrue(sawWanderRecovery,
                "when no shortest path is available, aggressive enemy must enter wander recovery");
        assertTrue(sawPathFollowAfterOpen,
                "after path becomes available, enemy must retry and re-enter path-follow mode");
        assertTrue(sawDirectionalAfterSuccessfulPath,
            "after a full 20 second path-follow window completes, mode must return to directional chase");
    }

    @Test
    void circularNoProgressForTenSecondsTriggersRecoveryAsStuck() {
        AdaptiveAggressiveMovementService service = new AdaptiveAggressiveMovementService();
        CircularArenaWorld world = new CircularArenaWorld(120d, 120d, 240d, 240d);

        EnemyState enemy = new EnemyState("loop", 40d, 120d, 0, -1, 16d, 4d);
        boolean enteredRecovery = false;

        for (int i = 0; i < 180; i++) {
            MovementResult result = service.tick(enemy, world, 0.1d);
            enemy = advance(enemy, result);
            var mode = service.modeForEnemy("loop");
            if (mode == AdaptiveAggressiveMovementService.AggressiveMovementMode.PATH_FOLLOW
                    || mode == AdaptiveAggressiveMovementService.AggressiveMovementMode.WANDER_RECOVERY) {
                enteredRecovery = true;
                break;
            }
        }

        assertTrue(enteredRecovery,
                "repeating circles without player progress for 10 seconds should be treated as stuck");
    }

    @Test
    void wanderRecoveryDoesNotRevisitHotCellsMoreThanThreeTimesInSlidingWindow() {
        AdaptiveAggressiveMovementService service = new AdaptiveAggressiveMovementService();
        ToggleBarrierWorld world = new ToggleBarrierWorld(220d, 120d, 240d, 240d);

        EnemyState enemy = new EnemyState("anti-circle", 32d, 120d, 1, 0, 16d, 4d);
        List<Long> recent = new java.util.ArrayList<>();

        for (int i = 0; i < 220; i++) {
            MovementResult result = service.tick(enemy, world, 0.1d);
            enemy = advance(enemy, result);
            if (service.modeForEnemy("anti-circle") != AdaptiveAggressiveMovementService.AggressiveMovementMode.WANDER_RECOVERY) {
                continue;
            }
            recent.add(cell(enemy.x(), enemy.y()));
            if (recent.size() > 24) {
                recent.remove(0);
            }
            long latest = recent.get(recent.size() - 1);
            long visits = recent.stream().filter(v -> v == latest).count();
            assertTrue(visits <= 3,
                    "wander recovery should not let a hot cell appear more than 3 times in the last 24 steps");
        }
    }

    private static EnemyState advance(EnemyState s, MovementResult r) {
        return new EnemyState(s.id(), r.x(), r.y(), r.directionX(), r.directionY(), s.size(), s.speed());
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.hypot(dx, dy);
    }

    private static long cell(double x, double y) {
        long qx = Math.round(x / 4d);
        long qy = Math.round(y / 4d);
        return (qx << 32) ^ (qy & 0xffffffffL);
    }

    private record FlatWorldView(double playerX, double playerY,
                                 List<SimpleWall> walls,
                                 double maxX, double maxY) implements WorldView {
        @Override
        public boolean wouldCollide(double centerX, double centerY, double size) {
            double half = size * 0.5d;
            if (centerX - half < 0d || centerY - half < 0d) {
                return true;
            }
            if (centerX + half > maxX || centerY + half > maxY) {
                return true;
            }
            double left = centerX - half;
            double right = centerX + half;
            double bottom = centerY - half;
            double top = centerY + half;
            for (SimpleWall w : walls) {
                if (w.horizontal()) {
                    double wx1 = Math.min(w.x1(), w.x2());
                    double wx2 = Math.max(w.x1(), w.x2());
                    if (right < wx1 || left > wx2) {
                        continue;
                    }
                    if (bottom <= w.y1() && top >= w.y1()) {
                        return true;
                    }
                } else {
                    double wy1 = Math.min(w.y1(), w.y2());
                    double wy2 = Math.max(w.y1(), w.y2());
                    if (top < wy1 || bottom > wy2) {
                        continue;
                    }
                    if (left <= w.x1() && right >= w.x1()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private record SimpleWall(double x1, double y1, double x2, double y2, boolean horizontal) {
    }

    private static final class ToggleBarrierWorld implements WorldView {
        private final double playerX;
        private final double playerY;
        private final double maxX;
        private final double maxY;
        private boolean barrierOpen;

        private ToggleBarrierWorld(double playerX, double playerY, double maxX, double maxY) {
            this.playerX = playerX;
            this.playerY = playerY;
            this.maxX = maxX;
            this.maxY = maxY;
            this.barrierOpen = false;
        }

        @Override
        public double playerX() {
            return playerX;
        }

        @Override
        public double playerY() {
            return playerY;
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
            double bottom = centerY - half;
            double top = centerY + half;
            double wallX = 120d;
            return left <= wallX && right >= wallX && bottom <= maxY && top >= 0d;
        }
    }

    private static final class CircularArenaWorld implements WorldView {
        private final double playerX;
        private final double playerY;
        private final double maxX;
        private final double maxY;

        private CircularArenaWorld(double playerX, double playerY, double maxX, double maxY) {
            this.playerX = playerX;
            this.playerY = playerY;
            this.maxX = maxX;
            this.maxY = maxY;
        }

        @Override
        public double playerX() {
            return playerX;
        }

        @Override
        public double playerY() {
            return playerY;
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

            // Ring corridor: center is blocked, outer area blocked.
            // Enemy can move but mostly loops around the ring.
            double dx = centerX - 120d;
            double dy = centerY - 120d;
            double r = Math.hypot(dx, dy);
            return r < 54d || r > 78d;
        }
    }
}
