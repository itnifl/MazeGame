package main.game.maze.libgdx.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class SampleMazeTest {

    @Test
    void exposesGridDimensionsAndPixelSizes() {
        SampleMaze maze = new SampleMaze(4, 3, 32f, 42L);
        assertEquals(4, maze.cols());
        assertEquals(3, maze.rows());
        assertEquals(32f, maze.cellSize());
        assertEquals(128f, maze.widthPx());
        assertEquals(96f, maze.heightPx());
    }

    @Test
    void startSitsInsideFirstCellAndGoalInsideLastCell() {
        SampleMaze maze = new SampleMaze(5, 4, 20f, 7L);
        assertEquals(10f, maze.startX());
        assertEquals(10f, maze.startY());
        assertEquals(90f, maze.goalX());
        assertEquals(70f, maze.goalY());
    }

    @Test
    void generatesDeterministicWallSetForGivenSeed() {
        SampleMaze a = new SampleMaze(8, 6, 48f, 99L);
        SampleMaze b = new SampleMaze(8, 6, 48f, 99L);
        List<WallSegment> wa = a.walls();
        List<WallSegment> wb = b.walls();
        assertEquals(wa.size(), wb.size());
        for (int i = 0; i < wa.size(); i++) {
            assertEquals(wa.get(i).x1, wb.get(i).x1);
            assertEquals(wa.get(i).y1, wb.get(i).y1);
            assertEquals(wa.get(i).x2, wb.get(i).x2);
            assertEquals(wa.get(i).y2, wb.get(i).y2);
        }
    }

    @Test
    void differentSeedsProduceDifferentWallLayouts() {
        SampleMaze a = new SampleMaze(10, 8, 40f, 1L);
        SampleMaze b = new SampleMaze(10, 8, 40f, 2L);
        // Same outer perimeter count, but interior carving differs; equality
        // would be a flag that the seed isn't actually driving randomness.
        boolean identical = true;
        if (a.walls().size() != b.walls().size()) {
            identical = false;
        } else {
            for (int i = 0; i < a.walls().size(); i++) {
                WallSegment wa = a.walls().get(i);
                WallSegment wb = b.walls().get(i);
                if (wa.x1 != wb.x1 || wa.y1 != wb.y1 || wa.x2 != wb.x2 || wa.y2 != wb.y2) {
                    identical = false;
                    break;
                }
            }
        }
        assertFalse(identical, "expected different seeds to produce different wall layouts");
    }

    @Test
    void wallsListIsImmutable() {
        SampleMaze maze = new SampleMaze(3, 3, 16f, 0L);
        assertNotNull(maze.walls());
        assertThrows(UnsupportedOperationException.class,
                () -> maze.walls().add(new WallSegment(0, 0, 1, 1)));
    }

    @Test
    void allWallsAreAxisAligned() {
        SampleMaze maze = new SampleMaze(6, 6, 24f, 5L);
        for (WallSegment w : maze.walls()) {
            boolean horizontal = Math.abs(w.y1 - w.y2) < 1e-4f && Math.abs(w.x1 - w.x2) > 0f;
            boolean vertical = Math.abs(w.x1 - w.x2) < 1e-4f && Math.abs(w.y1 - w.y2) > 0f;
            assertTrue(horizontal || vertical, "wall is not axis-aligned");
        }
    }

    @Test
    void rejectsNonPositiveDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new SampleMaze(0, 3, 16f, 0L));
        assertThrows(IllegalArgumentException.class, () -> new SampleMaze(3, 0, 16f, 0L));
        assertThrows(IllegalArgumentException.class, () -> new SampleMaze(3, 3, 0f, 0L));
    }
}
