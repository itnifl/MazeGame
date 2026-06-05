package main.game.maze.common.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AntiLoopWanderMovementServiceTest {

    @Test
    void continuesStraightUntilCollisionThenTurns() {
        AntiLoopWanderMovementService service = new AntiLoopWanderMovementService();
        WorldView world = new CorridorWorld(200d, 200d, 140d);

        EnemyState current = new EnemyState("corridor", 40d, 100d, 1, 0, 16d, 8d);

        for (int i = 0; i < 11; i++) {
            MovementResult next = service.tick(current, world);
            assertTrue(next.moved(), "enemy should keep moving straight before the wall");
            assertTrue(next.directionX() == 1 && next.directionY() == 0,
                    "wander should keep the same heading until it collides");
            current = new EnemyState(current.id(), next.x(), next.y(), next.directionX(), next.directionY(),
                    current.size(), current.speed());
        }

        MovementResult turn = service.tick(current, world);
        assertTrue(turn.moved(), "after collision wander should choose another open cardinal");
        assertTrue(!(turn.directionX() == 1 && turn.directionY() == 0),
            "after collision wander should pick a fresh random direction instead of keep pushing into the wall");
    }

    @Test
    void wanderBreaksOutOfTinyLoopArea() {
        AntiLoopWanderMovementService service = new AntiLoopWanderMovementService();
        WorldView world = new OpenWorld(1000d, 1000d);

        EnemyState current = new EnemyState("loop", 100d, 100d, 1, 0, 16d, 4d);
        Set<Long> visited = new HashSet<>();
        visited.add(cell(current.x(), current.y()));

        for (int i = 0; i < 80; i++) {
            MovementResult next = service.tick(current, world);
            current = new EnemyState(current.id(), next.x(), next.y(), next.directionX(), next.directionY(),
                    current.size(), current.speed());
            visited.add(cell(current.x(), current.y()));
        }

        assertTrue(visited.size() >= 10,
                "wander should not stay trapped in a tiny repeated loop, visited=" + visited.size());
    }

    @Test
    void allowsReverseWhenThatIsTheOnlyOpenDirection() {
        AntiLoopWanderMovementService service = new AntiLoopWanderMovementService();
        WorldView world = new DeadEndWorld(200d, 200d, 112d, 100d);

        EnemyState current = new EnemyState("ping", 100d, 100d, 1, 0, 16d, 4d);
        MovementResult next = service.tick(current, world);

        assertEquals(-1, next.directionX(), "reverse should be allowed when it is the only passable random choice");
        assertEquals(0, next.directionY(), "reverse should stay on the corridor axis");
        assertTrue(next.moved(), "enemy should move backwards out of the dead end");
    }

    private static long cell(double x, double y) {
        long qx = Math.round(x / 4d);
        long qy = Math.round(y / 4d);
        return (qx << 32) ^ (qy & 0xffffffffL);
    }

    private record OpenWorld(double maxX, double maxY) implements WorldView {
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
            return centerX + half > maxX || centerY + half > maxY;
        }
    }

    private record CorridorWorld(double maxX, double maxY, double wallX) implements WorldView {
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
            return centerX + half >= wallX;
        }
    }

    private record DeadEndWorld(double maxX, double maxY, double frontWallX, double laneY) implements WorldView {
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
            if (Math.abs(centerY - laneY) > 0.001d) {
                return true;
            }
            return centerX + half >= frontWallX;
        }
    }
}


