package main.game.maze.game.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.game.maze.mazeworld.LavaField;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;

/**
 * The maze as the generator made it: a grid of square cells, one corridor wide, whose edges are closed by wall
 * pieces of one piece length (three to a corridor width). Built from the live wall list (world space, y-down), so it
 * always shows what is closed now. Shared by both frontends.
 */
public final class CellGrid {

    /** A corridor is this wide, and so is a cell. */
    public static final int CELL = 60;
    /** One piece of wall. */
    public static final int PIECE = 20;
    public static final int PIECES_PER_EDGE = CELL / PIECE;

    /** A cell, by row and column. */
    public record Cell(int row, int col) {
    }

    /** The edge between two neighbouring cells: where a wall can stand across the corridor. */
    public record Edge(Cell a, Cell b) {

        /** Whether this is the same edge as {@code other}, whichever way round. */
        public boolean sameAs(Edge other) {
            return (a.equals(other.a) && b.equals(other.b)) || (a.equals(other.b) && b.equals(other.a));
        }
    }

    private final int cols;
    private final int rows;
    private final Set<Long> vertical = new HashSet<>();     // wall pieces along x = const, key (x, top y)
    private final Set<Long> horizontal = new HashSet<>();   // wall pieces along y = const, key (y, left x)

    private final LavaField lava;

    public CellGrid(List<Vector2D> walls, int widthPx, int heightPx) {
        this(walls, widthPx, heightPx, null);
    }

    /** @param lava the lava under the maze, or null: a builder will not walk where it is deadly */
    public CellGrid(List<Vector2D> walls, int widthPx, int heightPx, LavaField lava) {
        this.lava = lava;
        this.cols = Math.max(1, widthPx / CELL);
        this.rows = Math.max(1, heightPx / CELL);
        for (Vector2D wall : walls) {
            Point2D s = wall.getStart();
            Point2D e = wall.getEnd();
            int x1 = (int) Math.round(s.getX());
            int y1 = (int) Math.round(s.getY());
            int x2 = (int) Math.round(e.getX());
            int y2 = (int) Math.round(e.getY());
            if (x1 == x2) {
                vertical.add(key(x1, Math.min(y1, y2)));
            } else if (y1 == y2) {
                horizontal.add(key(y1, Math.min(x1, x2)));
            }
        }
    }

    private static long key(int line, int start) {
        return ((long) line << 32) ^ (start & 0xffffffffL);
    }

    public int cols() {
        return cols;
    }

    public int rows() {
        return rows;
    }

    public boolean contains(Cell cell) {
        return cell.row() >= 0 && cell.row() < rows && cell.col() >= 0 && cell.col() < cols;
    }

    /** The cell a point lies in, clamped to the grid. */
    public Cell cellOf(double x, double y) {
        int col = Math.max(0, Math.min(cols - 1, (int) Math.floor(x / CELL)));
        int row = Math.max(0, Math.min(rows - 1, (int) Math.floor(y / CELL)));
        return new Cell(row, col);
    }

    /** The middle of a cell: the middle of the corridor. */
    public Point2D centerOf(Cell cell) {
        return new Point2D(cell.col() * CELL + CELL / 2.0, cell.row() * CELL + CELL / 2.0);
    }

    /** The four neighbours of a cell that are in the grid. */
    public List<Cell> neighbours(Cell cell) {
        List<Cell> out = new ArrayList<>(4);
        for (int[] d : new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
            Cell next = new Cell(cell.row() + d[0], cell.col() + d[1]);
            if (contains(next)) {
                out.add(next);
            }
        }
        return out;
    }

    /** The pieces of wall that close the edge between two neighbouring cells, in order (each as its two end points). */
    public List<Vector2D> pieces(Edge edge) {
        List<Vector2D> pieces = new ArrayList<>(PIECES_PER_EDGE);
        Cell a = edge.a();
        Cell b = edge.b();
        if (a.row() == b.row()) {   // side by side: a vertical wall between them
            int x = Math.max(a.col(), b.col()) * CELL;
            int y0 = a.row() * CELL;
            for (int k = 0; k < PIECES_PER_EDGE; k++) {
                pieces.add(new Vector2D(new Point2D(x, y0 + k * PIECE), new Point2D(x, y0 + (k + 1) * PIECE)));
            }
        } else {                    // one above the other: a horizontal wall between them
            int y = Math.max(a.row(), b.row()) * CELL;
            int x0 = a.col() * CELL;
            for (int k = 0; k < PIECES_PER_EDGE; k++) {
                pieces.add(new Vector2D(new Point2D(x0 + k * PIECE, y), new Point2D(x0 + (k + 1) * PIECE, y)));
            }
        }
        return pieces;
    }

    /** Whether this piece of wall (given by its two end points) stands now. */
    public boolean stands(Vector2D piece) {
        int x1 = (int) Math.round(piece.getStart().getX());
        int y1 = (int) Math.round(piece.getStart().getY());
        int x2 = (int) Math.round(piece.getEnd().getX());
        int y2 = (int) Math.round(piece.getEnd().getY());
        if (x1 == x2) {
            return vertical.contains(key(x1, Math.min(y1, y2)));
        }
        return horizontal.contains(key(y1, Math.min(x1, x2)));
    }

    /** How many of the pieces across an edge stand: 0 = open corridor, 3 = closed. */
    public int piecesStanding(Edge edge) {
        int count = 0;
        for (Vector2D piece : pieces(edge)) {
            if (stands(piece)) {
                count++;
            }
        }
        return count;
    }

    /** Whether a builder can walk from one cell to its neighbour: the corridor is open and no deadly lava in the way. */
    public boolean canWalk(Edge edge) {
        if (!isOpen(edge)) {
            return false;
        }
        if (lava == null) {
            return true;
        }
        Point2D a = centerOf(edge.a());
        Point2D b = centerOf(edge.b());
        return !lava.crossesDeadly(a.getX(), a.getY(), b.getX(), b.getY());
    }

    /** Whether the straight line between two points runs through deadly lava (false without lava). */
    public boolean lavaBetween(double x1, double y1, double x2, double y2) {
        return lava != null && lava.crossesDeadly(x1, y1, x2, y2);
    }

    public boolean isOpen(Edge edge) {
        return piecesStanding(edge) == 0;
    }

    public boolean isClosed(Edge edge) {
        return piecesStanding(edge) == PIECES_PER_EDGE;
    }

    /** The middle of an edge. */
    public Point2D midpointOf(Edge edge) {
        Point2D a = centerOf(edge.a());
        Point2D b = centerOf(edge.b());
        return new Point2D((a.getX() + b.getX()) / 2.0, (a.getY() + b.getY()) / 2.0);
    }
}
