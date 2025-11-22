package main.game.maze.runtime.generators;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import javafx.geometry.Point2D;
import main.game.maze.Vector2D;

/**
 * Depth-first search backtracking maze generator.
 *
 * Builds a grid of cells and starts with all internal walls present.
 * DFS carves passages by removing walls between cells.
 * Finally we convert the remaining walls to Vector2D segments.
 */
public class DfsMazeGenerator implements IMazeGenerator {

    private static final class Cell {
        boolean visited;
        boolean north = true;
        boolean south = true;
        boolean east = true;
        boolean west = true;
    }

    private static final class CellPos {
        final int r;
        final int c;
        CellPos(int r, int c) { this.r = r; this.c = c; }
    }

    private static final int MIN_Y = 22;

    private final MazeGeneratorConfig cfg;
    private final int cellSize;
    private final int cols;
    private final int rows;
    private final int marginX;
    private final int marginY;
    private final Random random = new Random();

    public DfsMazeGenerator(MazeGeneratorConfig config) {
        this.cfg = config;

        int step = cfg.getWallSegmentLengthPx();
        this.cellSize = cfg.getMinHallwayWidthPx();

        if (cfg.getDoorwayWidthPx() != cellSize) {
            throw new IllegalArgumentException("For now doorwayWidthPx must equal minHallwayWidthPx");
        }
        if (cellSize % step != 0) {
            throw new IllegalArgumentException("cellSize must be a multiple of wallSegmentLengthPx");
        }

        this.cols = Math.max(1, cfg.getWidthPx() / cellSize);
        this.rows = Math.max(1, cfg.getHeightPx() / cellSize);

        int usedWidth = cols * cellSize;
        int usedHeight = rows * cellSize;

        this.marginX = (cfg.getWidthPx() - usedWidth) / 2;
        this.marginY = (cfg.getHeightPx() - usedHeight) / 2;
    }

    @Override
    public List<Vector2D> generateMaze() {
        Cell[][] grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell();
            }
        }

        runDfs(grid);
        return buildWallVectors(grid);
    }

    private void runDfs(Cell[][] grid) {
        Deque<CellPos> stack = new ArrayDeque<>();
        grid[0][0].visited = true;
        stack.push(new CellPos(0, 0));

        while (!stack.isEmpty()) {
            CellPos current = stack.peek();
            int r = current.r;
            int c = current.c;

            List<int[]> neighbors = new ArrayList<>();
            neighbors.add(new int[] { r - 1, c, 0 }); // north
            neighbors.add(new int[] { r + 1, c, 1 }); // south
            neighbors.add(new int[] { r, c + 1, 2 }); // east
            neighbors.add(new int[] { r, c - 1, 3 }); // west

            Collections.shuffle(neighbors, random);

            CellPos nextPos = null;
            int dir = -1;

            for (int[] n : neighbors) {
                int nr = n[0];
                int nc = n[1];
                int d = n[2];

                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                    continue;
                }
                if (grid[nr][nc].visited) {
                    continue;
                }

                nextPos = new CellPos(nr, nc);
                dir = d;
                break;
            }

            if (nextPos == null) {
                stack.pop();
                continue;
            }

            if (dir == 0) {
                grid[r][c].north = false;
                grid[nextPos.r][nextPos.c].south = false;
            } else if (dir == 1) {
                grid[r][c].south = false;
                grid[nextPos.r][nextPos.c].north = false;
            } else if (dir == 2) {
                grid[r][c].east = false;
                grid[nextPos.r][nextPos.c].west = false;
            } else if (dir == 3) {
                grid[r][c].west = false;
                grid[nextPos.r][nextPos.c].east = false;
            }

            grid[nextPos.r][nextPos.c].visited = true;
            stack.push(nextPos);
        }
    }

    private List<Vector2D> buildWallVectors(Cell[][] grid) {
        List<Vector2D> walls = new ArrayList<>();
        int step = cfg.getWallSegmentLengthPx();

        int usedWidth = cols * cellSize;
        int usedHeight = rows * cellSize;
        int x0 = marginX;
        int y0 = marginY;
        int x1 = x0 + usedWidth;
        int y1 = y0 + usedHeight;

        // no explicit border walls here

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = grid[r][c];
                int cellX = x0 + c * cellSize;
                int cellY = y0 + r * cellSize;

                if (cell.north && r > 0) {
                    int wallY = cellY;
                    addHorizontalWall(walls, cellX, cellX + cellSize, wallY, step);
                }

                if (cell.west && c > 0) {
                    int wallX = cellX;
                    addVerticalWall(walls, cellY, cellY + cellSize, wallX, step);
                }

                if (r == rows - 1 && cell.south) {
                    int wallY = cellY + cellSize;
                    addHorizontalWall(walls, cellX, cellX + cellSize, wallY, step);
                }
                if (c == cols - 1 && cell.east) {
                    int wallX = cellX + cellSize;
                    addVerticalWall(walls, cellY, cellY + cellSize, wallX, step);
                }
            }
        }

        // new: ensure corridor width against outer edges
        enforceEdgeCorridorWidths(walls);

        return walls;
    }

    private void addHorizontalWall(List<Vector2D> walls, int xStart, int xEnd, int y, int step) {
        if (y < MIN_Y) {
            return;
        }
        int length = xEnd - xStart;
        if (length <= 0) return;
        if (length % step != 0) {
            throw new IllegalStateException("Horizontal wall length not multiple of step");
        }
        for (int x = xStart; x < xEnd; x += step) {
            walls.add(new Vector2D(
                    new Point2D(x, y),
                    new Point2D(x + step, y)));
        }
    }

    private void addVerticalWall(List<Vector2D> walls, int yStart, int yEnd, int x, int step) {
        if (yStart < MIN_Y || yEnd <= MIN_Y) {
            return;
        }
        int length = yEnd - yStart;
        if (length <= 0) return;
        if (length % step != 0) {
            throw new IllegalStateException("Vertical wall length not multiple of step");
        }
        for (int y = yStart; y < yEnd; y += step) {
            if (y < MIN_Y || y + step <= MIN_Y) {
                continue;
            }
            walls.add(new Vector2D(
                    new Point2D(x, y),
                    new Point2D(x, y + step)));
        }
    }

    /**
     * Post-process walls so that any wall segment parallel to an outer edge
     * does not create a corridor narrower than minHallwayWidthPx against that edge.
     */
    private void enforceEdgeCorridorWidths(List<Vector2D> walls) {
        final int width = cfg.getWidthPx();
        final int height = cfg.getHeightPx();
        final int minHall = cfg.getMinHallwayWidthPx();

        walls.removeIf(v -> {
            Point2D s = v.getStart();
            Point2D e = v.getEnd();
            double x1 = s.getX();
            double y1 = s.getY();
            double x2 = e.getX();
            double y2 = e.getY();

            boolean horizontal = Math.abs(y1 - y2) < 0.0001;
            boolean vertical = Math.abs(x1 - x2) < 0.0001;

            if (horizontal) {
                double y = y1;
                if (y < MIN_Y) return true;                   // safety with top margin
                if (y < minHall) return true;                 // too close to top edge
                if (height - y < minHall) return true;        // too close to bottom edge
            } else if (vertical) {
                double x = x1;
                if (x < minHall) return true;                 // too close to left edge
                if (width - x < minHall) return true;         // too close to right edge
            }
            return false;
        });
    }
}