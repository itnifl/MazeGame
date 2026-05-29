package main.game.maze.libgdx.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Self-contained grid maze for the libGDX gameplay subset.
 *
 * Generated with a deterministic depth-first recursive backtracker so that
 * tests can assert exact wall counts and the rendering pipeline gets the
 * same layout every run. This is a stand-in until the Tycho-packaged
 * {@code main.game.maze.mazeworld} bundle is repackaged as a plain Maven
 * jar that the libGDX module can consume directly.
 */
public final class SampleMaze implements MazeArena {

    private final int cols;
    private final int rows;
    private final float cellSize;
    private final List<WallSegment> walls;

    public SampleMaze(int cols, int rows, float cellSize, long seed) {
        if (cols <= 0 || rows <= 0) {
            throw new IllegalArgumentException("cols and rows must be positive");
        }
        if (cellSize <= 0f) {
            throw new IllegalArgumentException("cellSize must be positive");
        }
        this.cols = cols;
        this.rows = rows;
        this.cellSize = cellSize;
        this.walls = Collections.unmodifiableList(generate(cols, rows, cellSize, seed));
    }

    public int cols() { return cols; }
    public int rows() { return rows; }
    public float cellSize() { return cellSize; }
    public float widthPx() { return cols * cellSize; }
    public float heightPx() { return rows * cellSize; }

    public List<WallSegment> walls() { return walls; }

    public float startX() { return cellSize * 0.5f; }
    public float startY() { return cellSize * 0.5f; }
    public float goalX() { return (cols - 0.5f) * cellSize; }
    public float goalY() { return (rows - 0.5f) * cellSize; }

    private static List<WallSegment> generate(int cols, int rows, float cellSize, long seed) {
        // Each cell tracks four walls: top, right, bottom, left.
        boolean[][] top = new boolean[cols][rows];
        boolean[][] right = new boolean[cols][rows];
        boolean[][] bottom = new boolean[cols][rows];
        boolean[][] left = new boolean[cols][rows];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                top[x][y] = right[x][y] = bottom[x][y] = left[x][y] = true;
            }
        }

        boolean[][] visited = new boolean[cols][rows];
        Random rnd = new Random(seed);
        // Iterative DFS to avoid stack-overflow on big mazes.
        int[][] stack = new int[cols * rows][2];
        int sp = 0;
        stack[sp][0] = 0; stack[sp][1] = 0; sp++;
        visited[0][0] = true;
        while (sp > 0) {
            int[] cell = stack[sp - 1];
            int cx = cell[0], cy = cell[1];
            int[] dirs = {0, 1, 2, 3};
            shuffle(dirs, rnd);
            boolean carved = false;
            for (int d : dirs) {
                int nx = cx + (d == 1 ? 1 : d == 3 ? -1 : 0);
                int ny = cy + (d == 0 ? -1 : d == 2 ? 1 : 0);
                if (nx < 0 || ny < 0 || nx >= cols || ny >= rows) continue;
                if (visited[nx][ny]) continue;
                // Carve wall between (cx,cy) and (nx,ny).
                switch (d) {
                    case 0: top[cx][cy] = false; bottom[nx][ny] = false; break;
                    case 1: right[cx][cy] = false; left[nx][ny] = false; break;
                    case 2: bottom[cx][cy] = false; top[nx][ny] = false; break;
                    case 3: left[cx][cy] = false; right[nx][ny] = false; break;
                    default: break;
                }
                visited[nx][ny] = true;
                stack[sp][0] = nx; stack[sp][1] = ny; sp++;
                carved = true;
                break;
            }
            if (!carved) sp--;
        }

        List<WallSegment> out = new ArrayList<>();
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                // libGDX origin is bottom-left; row 0 sits at the bottom.
                float x0 = x * cellSize;
                float y0 = y * cellSize;
                float x1 = (x + 1) * cellSize;
                float y1 = (y + 1) * cellSize;
                // Only emit each shared wall once: bottom + left per cell, plus
                // the outer top/right border on the boundary cells.
                if (bottom[x][y]) out.add(new WallSegment(x0, y0, x1, y0));
                if (left[x][y])   out.add(new WallSegment(x0, y0, x0, y1));
                if (y == rows - 1 && top[x][y]) out.add(new WallSegment(x0, y1, x1, y1));
                if (x == cols - 1 && right[x][y]) out.add(new WallSegment(x1, y0, x1, y1));
            }
        }
        return out;
    }

    private static void shuffle(int[] arr, Random rnd) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
        }
    }
}
