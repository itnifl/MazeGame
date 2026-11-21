package main.game.maze.game.spawn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.game.hazard.MazeHazards;
import main.game.maze.game.items.PickupField;
import main.game.maze.game.spawn.HudZones.Rect;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.constants.StageConstants;
import main.game.maze.mazeworld.generators.DfsMazeGenerator;
import main.game.maze.mazeworld.generators.MazeGeneratorConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Player spawn: near the original start, on free ground, outside the HUD")
class PlayerSpawnTest {

    private static final int W = 780;
    private static final int H = 660;
    private static final double PLAYER = 30;

    private static GameMazeWorld maze() {
        return new GameMazeWorld(new DfsMazeGenerator(new MazeGeneratorConfig(W, H,
                StageConstants.WallSegmentLengthPx, StageConstants.HallwayWidthPx, StageConstants.HallwayWidthPx, 40)));
    }

    @Test
    @DisplayName("the old start under the weapon panel is moved out from under the HUD, close by, onto free ground")
    void movedOutOfTheHud() {
        double originalX = 15;
        double originalY = 67;   // the player started at (0, 52), 30 px square: under the weapon panel
        List<Rect> hud = HudZones.covered(W, H);
        assertTrue(hud.stream().anyMatch(r -> r.containsWithMargin(originalX, originalY, PLAYER / 2)), "it was under the HUD");
        for (int attempt = 0; attempt < 10; attempt++) {
            GameMazeWorld world = maze();
            Point2D spawn = PlayerSpawn.find(world, originalX, originalY, PLAYER, W, H);
            for (Rect zone : hud) {
                assertFalse(zone.containsWithMargin(spawn.getX(), spawn.getY(), PLAYER / 2), "outside the HUD");
            }
            assertTrue(Math.hypot(spawn.getX() - originalX, spawn.getY() - originalY) <= 130, "close to the original start");
            assertTrue(MazeHazards.freePoints(world).contains(spawn), "on free ground: not in a wall");
            for (Vector2D wall : world.getMazeVectors()) {
                assertTrue(PickupField.distanceToSegment(spawn.getX(), spawn.getY(), wall) > PLAYER / 2, "clear of every wall");
            }
        }
    }

    @Test
    @DisplayName("the nearest free point is chosen")
    void nearestFreePoint() {
        List<Point2D> free = List.of(new Point2D(30, 30), new Point2D(390, 330), new Point2D(450, 330));
        Point2D chosen = PlayerSpawn.nearestClear(free, 440, 335, 15, List.of());
        assertEquals(450, chosen.getX(), 1e-9);
    }

    @Test
    @DisplayName("points under a zone are skipped even when nearest; without any free point the start is kept")
    void skipsCoveredPoints() {
        List<Point2D> free = List.of(new Point2D(30, 30), new Point2D(30, 200));
        Point2D chosen = PlayerSpawn.nearestClear(free, 30, 40, 15, List.of(new Rect(0, 0, 100, 60)));
        assertEquals(200, chosen.getY(), 1e-9);
        Point2D kept = PlayerSpawn.nearestClear(List.of(), 12, 34, 15, List.of());
        assertEquals(12, kept.getX(), 1e-9);
        assertEquals(34, kept.getY(), 1e-9);
    }

    @Test
    @DisplayName("the HUD zones: top bar, weapon panel, score panel and bottom row")
    void zones() {
        List<Rect> zones = HudZones.covered(800, 665);
        assertEquals(4, zones.size());
        assertEquals(800, zones.get(0).width(), 1e-9);
        assertEquals(665 - HudZones.BOTTOM_ROW_HEIGHT, zones.get(3).y(), 1e-9);
        assertTrue(zones.get(2).x() + zones.get(2).width() <= 800, "the score panel is inside the board");
    }
}
