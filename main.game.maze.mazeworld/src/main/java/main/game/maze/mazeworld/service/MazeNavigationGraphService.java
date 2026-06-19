// maze/src/main/java/main/game/maze/service/MazeNavigationGraphService.java
package main.game.maze.mazeworld.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.mazeworld.service.MazeNavigationGraph.Node;

/**
 * Service that builds a MazeNavigationGraph based on maze vectors.
 */
public final class MazeNavigationGraphService {

    private MazeNavigationGraphService() {
        // Utility class
    }

    public static MazeNavigationGraph buildFrom(List<Vector2D> walls, double stepSize) {
        if (walls == null || walls.isEmpty()) {
            throw new IllegalArgumentException("walls must not be null or empty");
        }

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (Vector2D w : walls) {
            Point2D s = w.getStart();
            Point2D e = w.getEnd();
            minX = Math.min(minX, Math.min(s.getX(), e.getX()));
            minY = Math.min(minY, Math.min(s.getY(), e.getY()));
            maxX = Math.max(maxX, Math.max(s.getX(), e.getX()));
            maxY = Math.max(maxY, Math.max(s.getY(), e.getY()));
        }

        double padding = stepSize;
        minX -= padding;
        minY -= padding;
        maxX += padding;
        maxY += padding;

        int cols = (int) Math.ceil((maxX - minX) / stepSize) + 1;
        int rows = (int) Math.ceil((maxY - minY) / stepSize) + 1;

        MazeNavigationGraph.Node[][] grid = new MazeNavigationGraph.Node[cols][rows];

        for (int c = 0; c < cols; c++) {
            double x = minX + c * stepSize;
            for (int r = 0; r < rows; r++) {
                double y = minY + r * stepSize;
                grid[c][r] = new MazeNavigationGraph.Node(c, r, x, y);
            }
        }

        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                Node n = grid[c][r];

                // Right neighbor
                if (c + 1 < cols) {
                    Node right = grid[c + 1][r];
                    if (segmentIsFree(n.toPoint2D(), right.toPoint2D(), walls, stepSize * 0)) { //Delete the stepSize away from the walls here
                        n.addNeighbor(right);
                        right.addNeighbor(n);
                    }
                }

                // Down neighbor
                if (r + 1 < rows) {
                    Node down = grid[c][r + 1];
                    if (segmentIsFree(n.toPoint2D(), down.toPoint2D(), walls, stepSize * 0)) { //Delete the stepSize away from the walls here
                        n.addNeighbor(down);
                        down.addNeighbor(n);
                    }
                }
            }
        }

        MazeNavigationGraph graph = new MazeNavigationGraph(grid, cols, rows, stepSize, minX, minY);

        // Du kan gjerne la denne stå, men den blir uansett overskrevet når vi
        // bygger tree fra spillerposisjon:
        buildSpanningTree(graph, graph.getNode(0, 0));

        return graph;
    }

    private static boolean segmentIsFree(Point2D a,
                                         Point2D b,
                                         List<Vector2D> walls,
                                         double wallOffset) {
        Vector2D seg = new Vector2D(a, b);
        for (Vector2D wall : walls) {
            if (seg.doIntersect(wall, (int) Math.ceil(wallOffset))) {
                return false;
            }
        }
        return true;
    }

    private static void buildSpanningTree(MazeNavigationGraph graph, Node root) {
        if (graph == null || root == null) {
            return;
        }

        // Rens tidligere treeParent før vi bygger et nytt tre
        Node[][] grid = graph.getGrid();
        int cols = graph.getCols();
        int rows = graph.getRows();
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                Node n = grid[c][r];
                if (n != null) {
                    n.setTreeParent(null);
                }
            }
        }

        ArrayDeque<Node> queue = new ArrayDeque<>();
        root.setTreeParent(null);
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (Node neighbor : new ArrayList<>(current.getNeighbors())) {
                if (neighbor.getTreeParent() == null && neighbor != root) {
                    neighbor.setTreeParent(current);
                    queue.add(neighbor);
                }
            }
        }
    }

    /**
     * Restores navigation edges that were blocked solely by the removed wall.
     *
     * <p>After a breakable wall is destroyed, {@code remainingWalls} no longer
     * contains that wall. This method scans every pair of adjacent grid nodes and
     * adds edges back wherever the corridor between them is now wall-free.
     * Edges that already exist are left untouched (no duplicates).
     *
     * <p>Should be called on the UI thread after removing the wall from the live
     * wall list so that {@code remainingWalls} reflects the post-destruction state.
     */
    public static void rewireAfterWallRemoval(MazeNavigationGraph graph,
                                              List<Vector2D> remainingWalls) {
        if (graph == null || remainingWalls == null) return;
        Node[][] grid = graph.getGrid();
        int cols = graph.getCols();
        int rows = graph.getRows();

        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                Node n = grid[c][r];
                if (n == null) continue;

                if (c + 1 < cols) {
                    Node right = grid[c + 1][r];
                    if (right != null && !n.getNeighbors().contains(right)
                            && segmentIsFree(n.toPoint2D(), right.toPoint2D(), remainingWalls, 0)) {
                        n.addNeighbor(right);
                        right.addNeighbor(n);
                    }
                }

                if (r + 1 < rows) {
                    Node down = grid[c][r + 1];
                    if (down != null && !n.getNeighbors().contains(down)
                            && segmentIsFree(n.toPoint2D(), down.toPoint2D(), remainingWalls, 0)) {
                        n.addNeighbor(down);
                        down.addNeighbor(n);
                    }
                }
            }
        }
    }

    /**
     * Bygger spanning-tree med root på nærmeste node til gitt verdensposisjon.
     * Brukes for å starte treet fra spillerens posisjon.
     */
    public static void rebuildSpanningTreeFrom(MazeNavigationGraph graph, Point2D worldPos) {
        if (graph == null || worldPos == null) {
            return;
        }
        Node root = graph.snapToNode(worldPos);
        buildSpanningTree(graph, root);
    }

    public static List<Point2D> findPath(MazeNavigationGraph graph,
                                         Point2D start,
                                         Point2D goal) {
        if (graph == null || start == null || goal == null) {
            return List.of();
        }

        MazeNavigationGraph.Node startNode = graph.snapToNode(start);
        MazeNavigationGraph.Node goalNode  = graph.snapToNode(goal);
        if (startNode == null || goalNode == null) {
            return List.of();
        }

        ArrayDeque<Node> queue = new ArrayDeque<>();
        Map<Node, Node> parent = new HashMap<>();
        Set<Node> visited = new HashSet<>();

        queue.add(startNode);
        visited.add(startNode);
        parent.put(startNode, null);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current == goalNode) {
                break;
            }
            for (Node nb : current.getNeighbors()) {
                if (!visited.contains(nb)) {
                    visited.add(nb);
                    parent.put(nb, current);
                    queue.add(nb);
                }
            }
        }

        if (!parent.containsKey(goalNode)) {
            return List.of();  // ingen sti
        }

        List<Point2D> path = new ArrayList<>();
        Node cur = goalNode;
        while (cur != null) {
            path.add(cur.toPoint2D());
            cur = parent.get(cur);
        }
        Collections.reverse(path);
        return path;
    }
}
