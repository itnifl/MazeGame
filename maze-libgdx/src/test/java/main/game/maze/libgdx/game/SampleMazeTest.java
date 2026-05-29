package main.game.maze.libgdx.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.game.maze.mazeworld.generators.SampleMaze;
import main.game.maze.mazeworld.generators.WallSegment;

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

    /**
     * Regression for the vertical-wall carve fix: every cell must be
     * reachable from (0,0) by walking through gaps between adjacent cells.
     * Before the fix, cases 0 and 2 cleared border edges instead of the
     * shared interior wall, leaving cells fenced off from their neighbours.
     */
    @Test
    void generatedMazeIsFullyConnectedForMultipleSeedsAndShapes() {
        int[][] shapes = {{4, 4}, {8, 6}, {10, 10}, {1, 5}, {5, 1}, {12, 3}};
        long[] seeds = {0L, 1L, 42L, 99L, 12345L};
        for (int[] shape : shapes) {
            for (long seed : seeds) {
                SampleMaze maze = new SampleMaze(shape[0], shape[1], 16f, seed);
                int reachable = countReachableCellsFromOrigin(maze);
                int expected = shape[0] * shape[1];
                assertEquals(expected, reachable,
                        "maze " + shape[0] + "x" + shape[1] + " seed " + seed
                                + " left " + (expected - reachable) + " unreachable cells");
            }
        }
    }

    private static int countReachableCellsFromOrigin(SampleMaze maze) {
        int cols = maze.cols();
        int rows = maze.rows();
        float cs = maze.cellSize();
        java.util.Set<Long> blockedBetween = new java.util.HashSet<>();
        for (WallSegment w : maze.walls()) {
            // Horizontal segment of length cellSize blocks cells sharing the same y line.
            if (Math.abs(w.y1 - w.y2) < 1e-4f) {
                int cx = Math.round(Math.min(w.x1, w.x2) / cs);
                int cyBelow = Math.round(w.y1 / cs) - 1; // cell directly below the wall
                int cyAbove = cyBelow + 1;
                if (cyBelow >= 0 && cyAbove < rows && cx >= 0 && cx < cols) {
                    blockedBetween.add(edgeKey(cx, cyBelow, cx, cyAbove));
                }
            } else if (Math.abs(w.x1 - w.x2) < 1e-4f) {
                int cxLeft = Math.round(w.x1 / cs) - 1;
                int cxRight = cxLeft + 1;
                int cy = Math.round(Math.min(w.y1, w.y2) / cs);
                if (cxLeft >= 0 && cxRight < cols && cy >= 0 && cy < rows) {
                    blockedBetween.add(edgeKey(cxLeft, cy, cxRight, cy));
                }
            }
        }
        boolean[][] seen = new boolean[cols][rows];
        java.util.ArrayDeque<int[]> q = new java.util.ArrayDeque<>();
        q.add(new int[]{0, 0});
        seen[0][0] = true;
        int count = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!q.isEmpty()) {
            int[] c = q.poll();
            count++;
            for (int[] dir : dirs) {
                int nx = c[0] + dir[0];
                int ny = c[1] + dir[1];
                if (nx < 0 || ny < 0 || nx >= cols || ny >= rows) continue;
                if (seen[nx][ny]) continue;
                if (blockedBetween.contains(edgeKey(c[0], c[1], nx, ny))) continue;
                seen[nx][ny] = true;
                q.add(new int[]{nx, ny});
            }
        }
        return count;
    }

    private static long edgeKey(int ax, int ay, int bx, int by) {
        long aHash = ((long) ax << 16) | (ay & 0xFFFFL);
        long bHash = ((long) bx << 16) | (by & 0xFFFFL);
        long lo = Math.min(aHash, bHash);
        long hi = Math.max(aHash, bHash);
        return (lo << 32) | (hi & 0xFFFFFFFFL);
    }
}
