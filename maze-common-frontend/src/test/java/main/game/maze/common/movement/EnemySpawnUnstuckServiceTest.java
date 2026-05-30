package main.game.maze.common.movement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EnemySpawnUnstuckServiceTest {

    @Test
    void keepsSpawnWhenAlreadyFree() {
        WorldView world = new BoxObstacleWorld();
        var result = EnemySpawnUnstuckService.nudgeIfColliding(world, 15d, 15d, 8d);
        assertFalse(result.moved(), "free spawn should remain unchanged");
        assertTrue(!world.wouldCollide(result.x(), result.y(), 8d),
                "result should stay collision free");
    }

    @Test
    void nudgesSpawnOutOfObstacleWhenColliding() {
        WorldView world = new BoxObstacleWorld();
        var result = EnemySpawnUnstuckService.nudgeIfColliding(world, 50d, 50d, 12d);
        assertTrue(result.moved(), "colliding spawn should be nudged");
        assertTrue(!world.wouldCollide(result.x(), result.y(), 12d),
                "nudged spawn should be collision free");
    }

    private static final class BoxObstacleWorld implements WorldView {
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
            return 0;
        }

        @Override
        public double minY() {
            return 0;
        }

        @Override
        public double maxX() {
            return 100;
        }

        @Override
        public double maxY() {
            return 100;
        }

        @Override
        public boolean wouldCollide(double centerX, double centerY, double size) {
            double half = size * 0.5d;
            double left = centerX - half;
            double right = centerX + half;
            double top = centerY - half;
            double bottom = centerY + half;
            return intersects(left, right, 40d, 60d) && intersects(top, bottom, 40d, 60d);
        }

        private static boolean intersects(double aMin, double aMax, double bMin, double bMax) {
            return aMax >= bMin && aMin <= bMax;
        }
    }
}