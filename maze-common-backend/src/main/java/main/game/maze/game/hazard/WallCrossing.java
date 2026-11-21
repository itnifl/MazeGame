package main.game.maze.game.hazard;

import java.util.List;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.generators.WallSegment;

/** Counts the walls a straight line crosses: how many walls lie between a guard tower and the player. */
public final class WallCrossing {

    private WallCrossing() {
    }

    /** Walls (JavaFX world: {@link Vector2D}) crossed by the line from (x1, y1) to (x2, y2). */
    public static int countVectors(double x1, double y1, double x2, double y2, List<Vector2D> walls) {
        int count = 0;
        for (Vector2D wall : walls) {
            if (WallCollisionUtil.wallBetweenVectors(x1, y1, x2, y2, List.of(wall))) {
                count++;
            }
        }
        return count;
    }

    /** Walls (libGDX world: {@link WallSegment}) crossed by the line from (x1, y1) to (x2, y2). */
    public static int countSegments(double x1, double y1, double x2, double y2, List<WallSegment> walls) {
        int count = 0;
        for (WallSegment wall : walls) {
            if (WallCollisionUtil.wallBetween(x1, y1, x2, y2, List.of(wall))) {
                count++;
            }
        }
        return count;
    }
}
