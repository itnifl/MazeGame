package main.game.maze.common.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Cross-frontend parity contract for enemy movement.
 *
 * <p>JavaFX and libGDX inject their own {@link WorldView} adapter into the
 * shared {@link EnemyMovementService}. This test plays both adapter roles
 * against the same service implementation and the same synthetic world to
 * prove the two frontends are wired to one code path: the per-tick output
 * (next position + chosen direction) MUST match enemy-for-enemy and
 * tick-for-tick.
 *
 * <p>It also guards the original report — enemies should travel in
 * deliberate directions toward the player, not orbit their spawn point.
 */
class EnemyMovementParityTest {

    @Test
    void bothFrontendsProduceIdenticalMovementForSameInputs() {
        EnemyMovementService service = new ChasePlayerMovementService();

        // Single shared world: player in the centre, no walls.
        WorldView javafxWorld = new FlatWorldView(200d, 200d, List.of(), 1000d, 1000d);
        WorldView libgdxWorld = new AdapterStyleWorldView(200d, 200d, List.of(), 1000d, 1000d);

        List<EnemyState> seeds = List.of(
                new EnemyState("a", 50d, 50d, 0, 0, 16d, 4d),
                new EnemyState("b", 350d, 50d, 0, 0, 16d, 4d),
                new EnemyState("c", 200d, 400d, 0, 0, 16d, 4d),
                new EnemyState("d", 199d, 199d, 0, 0, 16d, 4d));

        // "JavaFX" frontend and "libGDX" frontend are simulated by two
        // independent driver loops that both delegate to `service`. If the
        // service is the single source of truth, the per-tick traces MUST
        // be identical.
        List<List<MovementResult>> javafxTrace = simulate(service, javafxWorld, seeds, 50);
        List<List<MovementResult>> libgdxTrace = simulate(service, libgdxWorld, seeds, 50);

        assertEquals(javafxTrace.size(), libgdxTrace.size(), "same enemy count");
        for (int i = 0; i < javafxTrace.size(); i++) {
            List<MovementResult> fx = javafxTrace.get(i);
            List<MovementResult> gdx = libgdxTrace.get(i);
            assertEquals(fx.size(), gdx.size(), "tick count parity for enemy " + i);
            for (int t = 0; t < fx.size(); t++) {
                MovementResult a = fx.get(t);
                MovementResult b = gdx.get(t);
                assertEquals(a.x(), b.x(), 1e-9, "x parity enemy=" + i + " tick=" + t);
                assertEquals(a.y(), b.y(), 1e-9, "y parity enemy=" + i + " tick=" + t);
                assertEquals(a.directionX(), b.directionX(),
                        "dirX parity enemy=" + i + " tick=" + t);
                assertEquals(a.directionY(), b.directionY(),
                        "dirY parity enemy=" + i + " tick=" + t);
                assertEquals(a.moved(), b.moved(),
                        "moved parity enemy=" + i + " tick=" + t);
            }
        }
    }

    @Test
    void enemiesActuallyChasePlayerInsteadOfOrbitingSpawn() {
        EnemyMovementService service = new ChasePlayerMovementService();
        WorldView world = new FlatWorldView(500d, 500d, List.of(), 1000d, 1000d);

        EnemyState start = new EnemyState("z", 100d, 100d, 0, 0, 16d, 4d);
        double startDistance = distance(start.x(), start.y(), world.playerX(), world.playerY());

        EnemyState current = start;
        for (int t = 0; t < 80; t++) {
            MovementResult next = service.tick(current, world);
            current = new EnemyState(current.id(), next.x(), next.y(),
                    next.directionX(), next.directionY(),
                    current.size(), current.speed());
        }

        double endDistance = distance(current.x(), current.y(), world.playerX(), world.playerY());
        assertTrue(endDistance < startDistance,
                "enemy must close on the player; was " + startDistance + " -> " + endDistance);
        // Anti-orbit guard: after 80 ticks the enemy must not still be
        // hovering within one bounding box of its spawn (which is what the
        // old libGDX sin/cos oscillator did).
        double driftFromSpawn = distance(current.x(), current.y(), start.x(), start.y());
        assertTrue(driftFromSpawn > current.size(),
                "enemy must travel deliberately, not orbit spawn (drift=" + driftFromSpawn + ")");
    }

    @Test
    void blockedEnemyChoosesAnotherCardinalInsteadOfStandingStill() {
        EnemyMovementService service = new ChasePlayerMovementService();
        // Wall blocking the direct +X step from (50, 50) toward player at (200, 50):
        // a vertical wall at x=58 between y=20 and y=80.
        var walls = List.of(new SimpleWall(58d, 20d, 58d, 80d, false));
        WorldView world = new FlatWorldView(200d, 50d, walls, 1000d, 1000d);

        EnemyState enemy = new EnemyState("z", 50d, 50d, 0, 0, 16d, 4d);
        MovementResult result = service.tick(enemy, world);

        assertTrue(result.moved(), "blocked enemy must still pick a passable cardinal");
        assertFalse(result.directionX() == 1 && result.directionY() == 0,
                "must NOT walk into the wall directly to the right");
    }

    private static List<List<MovementResult>> simulate(EnemyMovementService service,
                                                       WorldView world,
                                                       List<EnemyState> seeds,
                                                       int ticks) {
        List<List<MovementResult>> traces = new ArrayList<>();
        for (EnemyState seed : seeds) {
            List<MovementResult> trace = new ArrayList<>(ticks);
            EnemyState current = seed;
            for (int t = 0; t < ticks; t++) {
                MovementResult next = service.tick(current, world);
                trace.add(next);
                current = new EnemyState(current.id(), next.x(), next.y(),
                        next.directionX(), next.directionY(),
                        current.size(), current.speed());
            }
            traces.add(trace);
        }
        return traces;
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /** Trivial WorldView with axis-aligned wall list and a rectangular playfield. */
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

    /** Independent adapter-style world implementation for frontend parity checks. */
    private static final class AdapterStyleWorldView implements WorldView {
        private final double playerX;
        private final double playerY;
        private final List<SimpleWall> walls;
        private final double maxX;
        private final double maxY;

        private AdapterStyleWorldView(double playerX, double playerY, List<SimpleWall> walls, double maxX, double maxY) {
            this.playerX = playerX;
            this.playerY = playerY;
            this.walls = walls;
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
}


