package main.game.maze.game.strike;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.common.movement.WorldView;
import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Enemies near a soldier go for the soldier first")
class DecoyTargetingTest {

    private static final WorldView PLAYER_AT_500 = new WorldView() {
        @Override public double playerX() { return 500; }
        @Override public double playerY() { return 500; }
        @Override public boolean wouldCollide(double x, double y, double size) { return x < 0; }
        @Override public double maxX() { return 900; }
    };

    @Test
    @DisplayName("the nearest soldier within the lure radius, even when the player is nearer")
    void nearestSoldierWins() {
        List<Point2D> soldiers = List.of(new Point2D(100, 0), new Point2D(60, 0), new Point2D(1000, 0));
        Point2D target = DecoyTargeting.nearestWithin(soldiers, 0, 0, 240);
        assertEquals(60, target.getX(), 1e-9);
        assertNull(DecoyTargeting.nearestWithin(soldiers, 0, 600, 240), "no soldier near: the player as before");
        assertNull(DecoyTargeting.nearestWithin(null, 0, 0, 240));
    }

    @Test
    @DisplayName("the enemy is given a view whose player is the soldier; walls, lava and bounds are unchanged")
    void chasingView() {
        WorldView view = DecoyTargeting.viewFor(PLAYER_AT_500, List.of(new Point2D(30, 40)), 0, 0, 240);
        assertEquals(30, view.playerX(), 1e-9);
        assertEquals(40, view.playerY(), 1e-9);
        assertTrue(view.wouldCollide(-1, 0, 10));
        assertEquals(900, view.maxX(), 1e-9);
        assertSame(PLAYER_AT_500, DecoyTargeting.viewFor(PLAYER_AT_500, List.of(), 0, 0, 240));
    }
}
