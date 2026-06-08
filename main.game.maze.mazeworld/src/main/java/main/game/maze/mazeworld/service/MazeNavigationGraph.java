package main.game.maze.mazeworld.service;

import java.util.ArrayList;
import java.util.List;
import main.game.maze.mazeworld.Point2D;

/**
 * Runtime navigation graph built from maze vectors.
 * 
 * Nodes are placed on a grid (stepSize), and edges exist only where
 * the line between two grid points does not cross a wall.
 */
public final class MazeNavigationGraph {

    public static final class Node {
        private final int col;
        private final int row;
        private final double x;
        private final double y;
        private final List<Node> neighbors = new ArrayList<>();

        // Valgfritt: lagring av spanning-tree-parent
        private Node treeParent;

        Node(int col, int row, double x, double y) {
            this.col = col;
            this.row = row;
            this.x = x;
            this.y = y;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public Point2D toPoint2D() {
            return new Point2D(x, y);
        }

        public List<Node> getNeighbors() {
            return neighbors;
        }

        void addNeighbor(Node n) {
            neighbors.add(n);
        }

        public Node getTreeParent() {
            return treeParent;
        }

        void setTreeParent(Node parent) {
            this.treeParent = parent;
        }
    }

    private final Node[][] grid;
    private final int cols;
    private final int rows;
    private final double stepSize;
    private final double minX;
    private final double minY;

    public MazeNavigationGraph(Node[][] grid,
                               int cols,
                               int rows,
                               double stepSize,
                               double minX,
                               double minY) {
        this.grid = grid;
        this.cols = cols;
        this.rows = rows;
        this.stepSize = stepSize;
        this.minX = minX;
        this.minY = minY;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public double getStepSize() {
        return stepSize;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public Node getNode(int col, int row) {
        if (col < 0 || col >= cols || row < 0 || row >= rows) {
            return null;
        }
        return grid[col][row];
    }

    public Node[][] getGrid() {
        return grid;
    }

    /**
     * Snaps an arbitrary position to the nearest node in the grid.
     */
    public Node snapToNode(Point2D p) {
        int c = (int) Math.round((p.getX() - minX) / stepSize);
        int r = (int) Math.round((p.getY() - minY) / stepSize);
        c = clamp(c, 0, cols - 1);
        r = clamp(r, 0, rows - 1);
        return grid[c][r];
    }

    public Point2D nodeToPoint2D(Node node) {
        return new Point2D(node.getX(), node.getY());
    }

    private int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }
}
