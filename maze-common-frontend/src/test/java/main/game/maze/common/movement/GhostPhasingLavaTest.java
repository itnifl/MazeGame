package main.game.maze.common.movement;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("A phasing ghost slips through walls but never into deadly lava")
class GhostPhasingLavaTest {

    /** A board 400 wide; everything right of x = 200 is lava. */
    private static final WorldView WORLD = new WorldView() {
        @Override public double playerX() { return 0; }
        @Override public double playerY() { return 0; }
        @Override public boolean wouldCollide(double cx, double cy, double size) { return false; }
        @Override public boolean inDeadlyLava(double cx, double cy) { return cx > 200; }
        @Override public double maxX() { return 400; }
        @Override public double maxY() { return 400; }
    };

    @Test
    @DisplayName("over many steps the ghost never enters the lava")
    void neverEntersLava() {
        GhostPhasingMovementService service = new GhostPhasingMovementService();
        double x = 150;
        double y = 200;
        double dx = 1;
        double dy = 0;
        for (int i = 0; i < 5000; i++) {
            MovementResult next = service.tick(new EnemyState("ghost", x, y, (int) dx, (int) dy, 40, 4), WORLD);
            x = next.x();
            y = next.y();
            assertFalse(x > 200, "the ghost went into the lava at step " + i + ": x = " + x);
        }
    }
}
