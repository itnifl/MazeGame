package main.game.maze.libgdx.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.common.movement.ChasePlayerMovementService;
import main.game.maze.common.movement.EnemyMovementService;
import main.game.maze.common.movement.EnemyState;
import main.game.maze.common.movement.MovementResult;
import main.game.maze.common.movement.WorldView;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.WallSegment;
import org.junit.jupiter.api.Test;

/**
 * Validates that the libGDX {@link GdxWorldView} adapter produces the same
 * per-tick movement result as a stand-in JavaFX-style adapter when both
 * are driven by the shared {@link ChasePlayerMovementService} against the
 * same maze geometry and player position. Guards the user-reported bug
 * (libGDX enemies orbiting their spawn instead of chasing).
 */
class GdxMovementParityTest {

    @Test
    void gdxAdapterMatchesAlternateAdapterUnderSameService() {
        EnemyMovementService service = new ChasePlayerMovementService();

        StubArena maze = new StubArena(400f, 300f, List.of(
                new WallSegment(80f, 60f, 80f, 240f),  // vertical wall
                new WallSegment(160f, 150f, 320f, 150f) // horizontal wall
        ));
        double playerX = 380d;
        double playerY = 280d;

        WorldView gdx = new GdxWorldView(maze, new PlayerState((float) playerX, (float) playerY, 16f));
        WorldView reference = new ReferenceAdapter(maze, playerX, playerY);

        List<EnemyState> seeds = List.of(
                new EnemyState("a", 20d, 20d, 0, 0, 16d, 5d),
                new EnemyState("b", 100d, 100d, 0, 0, 16d, 5d),
                new EnemyState("c", 250d, 60d, 0, 0, 16d, 5d),
                new EnemyState("d", 40d, 280d, 0, 0, 16d, 5d));

        for (EnemyState seed : seeds) {
            EnemyState gdxState = seed;
            EnemyState refState = seed;
            for (int t = 0; t < 60; t++) {
                MovementResult g = service.tick(gdxState, gdx);
                MovementResult r = service.tick(refState, reference);
                assertEquals(r.x(), g.x(), 1e-6, "x parity seed=" + seed.id() + " tick=" + t);
                assertEquals(r.y(), g.y(), 1e-6, "y parity seed=" + seed.id() + " tick=" + t);
                assertEquals(r.directionX(), g.directionX(),
                        "dirX parity seed=" + seed.id() + " tick=" + t);
                assertEquals(r.directionY(), g.directionY(),
                        "dirY parity seed=" + seed.id() + " tick=" + t);
                assertEquals(r.moved(), g.moved(),
                        "moved parity seed=" + seed.id() + " tick=" + t);
                gdxState = advance(gdxState, g);
                refState = advance(refState, r);
            }
            double finalDist = Math.hypot(playerX - gdxState.x(), playerY - gdxState.y());
            double startDist = Math.hypot(playerX - seed.x(), playerY - seed.y());
            assertTrue(finalDist < startDist,
                    "libGDX enemy must close on the player; seed=" + seed.id()
                            + " start=" + startDist + " final=" + finalDist);
        }
    }

    private static EnemyState advance(EnemyState s, MovementResult r) {
        return new EnemyState(s.id(), r.x(), r.y(), r.directionX(), r.directionY(), s.size(), s.speed());
    }

    private record StubArena(float widthPx, float heightPx, List<WallSegment> walls) implements MazeArena {
        @Override public float startX() { return 0f; }
        @Override public float startY() { return 0f; }
        @Override public float goalX() { return widthPx; }
        @Override public float goalY() { return heightPx; }
    }

    /**
     * Mirrors {@link GdxWorldView#wouldCollide} exactly. Stands in for a
     * "JavaFX-style" frontend adapter: a different class, same contract.
     * If the two adapters drift, the parity assertion above breaks.
     */
    private record ReferenceAdapter(MazeArena maze, double playerX, double playerY) implements WorldView {
        @Override
        public boolean wouldCollide(double centerX, double centerY, double size) {
            double half = size * 0.5d;
            if (centerX - half < 0d || centerY - half < 0d) {
                return true;
            }
            if (centerX + half > maze.widthPx() || centerY + half > maze.heightPx()) {
                return true;
            }
            double left = centerX - half;
            double right = centerX + half;
            double bottom = centerY - half;
            double top = centerY + half;
            for (WallSegment w : maze.walls()) {
                if (w.isHorizontal()) {
                    double wx1 = Math.min(w.x1, w.x2);
                    double wx2 = Math.max(w.x1, w.x2);
                    if (right < wx1 || left > wx2) {
                        continue;
                    }
                    if (bottom <= w.y1 && top >= w.y1) {
                        return true;
                    }
                } else {
                    double wy1 = Math.min(w.y1, w.y2);
                    double wy2 = Math.max(w.y1, w.y2);
                    if (top < wy1 || bottom > wy2) {
                        continue;
                    }
                    if (left <= w.x1 && right >= w.x1) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
