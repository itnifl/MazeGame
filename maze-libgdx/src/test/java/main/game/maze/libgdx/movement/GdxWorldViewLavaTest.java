package main.game.maze.libgdx.movement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.game.maze.game.hazard.MazeHazards;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.libgdx.game.PlayerCombatStateService;
import main.game.maze.mazeworld.LavaField;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.generators.MazeArena;
import main.game.maze.mazeworld.generators.PlayerState;
import main.game.maze.mazeworld.generators.RealMaze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX lava: enemies keep out of it, the player dies in it")
class GdxWorldViewLavaTest {

    private static final int W = 780;
    private static final int H = 660;

    private static RealMaze mazeWithLava() {
        RealMaze maze = RealMaze.fresh(W, H);
        LevelCatalogLoader.loadDefault().level(6).applyLava(maze.sourceWorld(), W, H);
        return maze;
    }

    @Test
    @DisplayName("an enemy cannot move into deadly lava (screen y is flipped), but can cross a bridge and walk on dry ground")
    void enemiesAvoidLava() {
        RealMaze maze = mazeWithLava();
        LavaField lava = maze.sourceWorld().getLava();
        GdxWorldView view = new GdxWorldView(maze, new PlayerState(100, 100, 24));
        boolean sawDeadly = false;
        for (LavaField.Stream stream : lava.streams()) {
            for (Point2D p : stream.path()) {
                if (p.getX() < 40 || p.getX() > W - 40 || p.getY() < 40 || p.getY() > H - 40) {
                    continue;
                }
                double screenY = H - p.getY();
                if (lava.isDeadly(p.getX(), p.getY())) {
                    sawDeadly = true;
                    assertTrue(view.wouldCollide(p.getX(), screenY, 24), "deadly lava blocks an enemy");
                }
            }
        }
        assertTrue(sawDeadly, "the streams have deadly stretches");
        for (LavaField.Bridge bridge : lava.streams().get(0).bridges()) {
            assertFalse(lava.isDeadly(bridge.x(), bridge.y()), "a bridge is not deadly (walls may still block there)");
        }
        for (Point2D dry : MazeHazards.freePoints(maze.sourceWorld())) {
            assertFalse(lava.isLava(dry.getX(), dry.getY()));
            assertFalse(view.wouldCollide(dry.getX(), H - dry.getY(), 20), "free dry ground is open");
            break;
        }
    }

    @Test
    @DisplayName("the player who steps into lava is dead at once, even behind the power shield")
    void lavaKillsThroughTheShield() {
        PlayerCombatStateService combat = new PlayerCombatStateService();
        combat.reset(100);
        combat.setShield(() -> true);
        combat.applyDirectDamage(50);
        org.junit.jupiter.api.Assertions.assertEquals(100, combat.currentHitPointsAsInt(), "the shield blocks ordinary damage");
        combat.killOutright();
        assertTrue(combat.isDead());
        org.junit.jupiter.api.Assertions.assertEquals(0, combat.currentHitPointsAsInt());
    }

    @Test
    @DisplayName("a level without lava puts none in the world")
    void noLava() {
        RealMaze maze = RealMaze.fresh(W, H);
        LevelCatalogLoader.loadDefault().level(5).applyLava(maze.sourceWorld(), W, H);
        org.junit.jupiter.api.Assertions.assertNull(maze.sourceWorld().getLava());
    }
}
