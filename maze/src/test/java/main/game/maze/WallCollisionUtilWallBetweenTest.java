package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.WallCollisionUtil;
import main.game.maze.mazeworld.generators.WallSegment;

/**
 * Tests for {@link WallCollisionUtil#wallBetween} and
 * {@link WallCollisionUtil#wallBetweenVectors}.
 *
 * Grid reference: cell size = 20px, walls at cell edges.
 * Cell 1 centre = (10, 10); cell 2 centre = (30, 10).
 * A vertical wall at x=20 separates them.
 */
class WallCollisionUtilWallBetweenTest {

    // -----------------------------------------------------------------------
    // wallBetween (WallSegment list, libGDX path)
    // -----------------------------------------------------------------------

    @Test
    void verticalWall_separatesPointsOnOpposideSides() {
        // Vertical wall at x=20 from y=0 to y=20.
        WallSegment wall = new WallSegment(20, 0, 20, 20);
        // Enemy at (10, 10); player at (30, 10) — one on each side.
        assertTrue(WallCollisionUtil.wallBetween(10, 10, 30, 10, List.of(wall)),
                "Vertical wall at x=20 must block the segment from x=10 to x=30");
    }

    @Test
    void verticalWall_noBlockWhenBothOnSameSide() {
        WallSegment wall = new WallSegment(20, 0, 20, 20);
        // Both at x<20 — same side.
        assertFalse(WallCollisionUtil.wallBetween(5, 10, 15, 10, List.of(wall)),
                "Both points on same side of wall must not be blocked");
    }

    @Test
    void horizontalWall_separatesPointsOnOpposideSides() {
        // Horizontal wall at y=20 from x=0 to x=20.
        WallSegment wall = new WallSegment(0, 20, 20, 20);
        // Enemy at (10, 10); player at (10, 30) — one above, one below.
        assertTrue(WallCollisionUtil.wallBetween(10, 10, 10, 30, List.of(wall)),
                "Horizontal wall at y=20 must block the segment from y=10 to y=30");
    }

    @Test
    void horizontalWall_noBlockWhenBothOnSameSide() {
        WallSegment wall = new WallSegment(0, 20, 20, 20);
        assertFalse(WallCollisionUtil.wallBetween(5, 5, 15, 5, List.of(wall)),
                "Both points above the horizontal wall must not be blocked");
    }

    @Test
    void verticalWall_crossingOutsideWallExtent_noBlock() {
        // Wall segment from y=0 to y=20, the segment crosses y-band but at x=20 at y=25 (outside segment).
        WallSegment wall = new WallSegment(20, 0, 20, 20);
        // Enemy at (10, 25); player at (30, 25) — crossing x=20 at y=25, but wall ends at y=20.
        assertFalse(WallCollisionUtil.wallBetween(10, 25, 30, 25, List.of(wall)),
                "Segment crossing x=20 at y=25 is outside the wall extent (y=0..20) — no block");
    }

    @Test
    void emptyWallList_neverBlocks() {
        assertFalse(WallCollisionUtil.wallBetween(0, 0, 100, 100, List.of()),
                "Empty wall list must never block");
    }

    @Test
    void diagonalSegment_crossingVerticalWall_blocks() {
        // Diagonal from (10, 10) to (30, 30) crosses vertical wall at x=20 at y=20.
        WallSegment wall = new WallSegment(20, 0, 20, 40);
        assertTrue(WallCollisionUtil.wallBetween(10, 10, 30, 30, List.of(wall)),
                "Diagonal segment crossing x=20 within wall extent must be blocked");
    }

    // -----------------------------------------------------------------------
    // wallBetweenVectors (Vector2D list, JavaFX path)
    // -----------------------------------------------------------------------

    @Test
    void vectors_verticalWall_separatesOpposideSides() {
        // Vertical wall at x=20 from y=0 to y=20 expressed as Vector2D.
        Vector2D wall = new Vector2D(new Point2D(20, 0), new Point2D(20, 20));
        assertTrue(WallCollisionUtil.wallBetweenVectors(10, 10, 30, 10, List.of(wall)),
                "Vector2D vertical wall at x=20 must block segment from x=10 to x=30");
    }

    @Test
    void vectors_horizontalWall_separatesOpposideSides() {
        Vector2D wall = new Vector2D(new Point2D(0, 20), new Point2D(20, 20));
        assertTrue(WallCollisionUtil.wallBetweenVectors(10, 10, 10, 30, List.of(wall)),
                "Vector2D horizontal wall at y=20 must block segment from y=10 to y=30");
    }

    @Test
    void vectors_noBlockWhenBothOnSameSide() {
        Vector2D wall = new Vector2D(new Point2D(20, 0), new Point2D(20, 20));
        assertFalse(WallCollisionUtil.wallBetweenVectors(5, 10, 15, 10, List.of(wall)),
                "Both points on same side of wall must not be blocked");
    }

    @Test
    void vectors_emptyList_neverBlocks() {
        assertFalse(WallCollisionUtil.wallBetweenVectors(0, 0, 100, 100, List.of()),
                "Empty Vector2D wall list must never block");
    }
}
