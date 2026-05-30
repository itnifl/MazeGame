package main.game.maze;

import javafx.scene.layout.Pane;
import main.game.maze.mazeworld.generators.SpriteWallNudger;
import main.game.maze.mazeworld.generators.WallSegment;
import main.game.maze.service.CharacterIntersectionFixerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies the JavaFX adapter and the shared {@link SpriteWallNudger}
 * geometry now used by both frontends. Private helpers are exercised via
 * reflection so the math contract stays locked.
 */
public class CharacterIntersectionFixerServiceTest {

    @Test
    @DisplayName("fixInitialSpriteMazeIntersections handles null maze gracefully")
    void fixIntersectionsHandlesNullMaze() {
        CharacterIntersectionFixerService service =
            new CharacterIntersectionFixerService(new Pane(), null);
        assertDoesNotThrow(service::fixInitialSpriteMazeIntersections);
    }

    @Test
    @DisplayName("pointInRect returns true for point inside rectangle")
    void pointInRectReturnsTrueForInsidePoint() throws Exception {
        assertTrue(invokePointInRect(5f, 5f, 0f, 0f, 10f, 10f));
    }

    @Test
    @DisplayName("pointInRect returns false for point outside rectangle")
    void pointInRectReturnsFalseForOutsidePoint() throws Exception {
        assertFalse(invokePointInRect(15f, 15f, 0f, 0f, 10f, 10f));
    }

    @Test
    @DisplayName("pointInRect returns true for point on boundary")
    void pointInRectReturnsTrueForBoundaryPoint() throws Exception {
        assertTrue(invokePointInRect(10f, 5f, 0f, 0f, 10f, 10f));
    }

    @Test
    @DisplayName("segmentsIntersect detects crossing segments")
    void segmentsIntersectDetectsCrossing() throws Exception {
        assertTrue(invokeSegmentsIntersect(0, 5, 10, 5, 5, 0, 5, 10));
    }

    @Test
    @DisplayName("segmentsIntersect rejects parallel non-overlapping segments")
    void segmentsIntersectRejectsParallel() throws Exception {
        assertFalse(invokeSegmentsIntersect(0, 0, 10, 0, 0, 5, 10, 5));
    }

    @Test
    @DisplayName("direction returns negative for point above CCW triangle base")
    void directionReturnsPositiveForCCW() throws Exception {
        float result = invokeDirection(0, 0, 10, 0, 5, 5);
        assertTrue(result < 0, "Point above line should give negative cross product");
    }

    @Test
    @DisplayName("onSegment returns true for point on segment")
    void onSegmentReturnsTrueForPointOnSegment() throws Exception {
        assertTrue(invokeOnSegment(0, 0, 10, 0, 5, 0));
    }

    @Test
    @DisplayName("onSegment returns false for point off segment")
    void onSegmentReturnsFalseForPointOffSegment() throws Exception {
        assertFalse(invokeOnSegment(0, 0, 10, 0, 15, 0));
    }

    @Test
    @DisplayName("nudgeOffWalls pushes an AABB off a horizontal wall")
    void nudgeOffWallsMovesRectOffHorizontalWall() {
        float[] pos = { 50f, 50f }; // rect center
        final float half = 5f;
        SpriteWallNudger.MovableAabb rect = new SpriteWallNudger.MovableAabb() {
            @Override public float minX() { return pos[0] - half; }
            @Override public float minY() { return pos[1] - half; }
            @Override public float maxX() { return pos[0] + half; }
            @Override public float maxY() { return pos[1] + half; }
            @Override public void offset(float dx, float dy) { pos[0] += dx; pos[1] += dy; }
        };
        WallSegment horizontalThroughRect = new WallSegment(0, 50, 100, 50);
        SpriteWallNudger.nudgeOffWalls(rect, List.of(horizontalThroughRect), 50);

        assertFalse(SpriteWallNudger.intersects(rect, horizontalThroughRect),
            "Rect should be clear of the horizontal wall after nudging");
    }

    // --- reflection helpers targeting the shared geometry module ---

    private static boolean invokePointInRect(float x, float y, float rx1, float ry1, float rx2, float ry2) throws Exception {
        Method m = SpriteWallNudger.class.getDeclaredMethod("pointInRect",
            float.class, float.class, float.class, float.class, float.class, float.class);
        m.setAccessible(true);
        return (boolean) m.invoke(null, x, y, rx1, ry1, rx2, ry2);
    }

    private static boolean invokeSegmentsIntersect(float x1, float y1, float x2, float y2,
                                                   float x3, float y3, float x4, float y4) throws Exception {
        Method m = SpriteWallNudger.class.getDeclaredMethod("segmentsIntersect",
            float.class, float.class, float.class, float.class,
            float.class, float.class, float.class, float.class);
        m.setAccessible(true);
        return (boolean) m.invoke(null, x1, y1, x2, y2, x3, y3, x4, y4);
    }

    private static float invokeDirection(float xi, float yi, float xj, float yj, float xk, float yk) throws Exception {
        Method m = SpriteWallNudger.class.getDeclaredMethod("direction",
            float.class, float.class, float.class, float.class, float.class, float.class);
        m.setAccessible(true);
        return (float) m.invoke(null, xi, yi, xj, yj, xk, yk);
    }

    private static boolean invokeOnSegment(float xi, float yi, float xj, float yj, float xk, float yk) throws Exception {
        Method m = SpriteWallNudger.class.getDeclaredMethod("onSegment",
            float.class, float.class, float.class, float.class, float.class, float.class);
        m.setAccessible(true);
        return (boolean) m.invoke(null, xi, yi, xj, yj, xk, yk);
    }
}
