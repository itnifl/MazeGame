package main.game.maze;

import main.game.maze.behaviour.BehaviourFactory;
import main.game.maze.behaviour.DijkstraPathCalculator;
import main.game.maze.behaviour.PatrolBehavior;
import main.game.maze.behaviour.PatrolPathBehavior;
import main.game.maze.behaviour.PatrolPoint;
import main.game.maze.behaviour.Position;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.service.MazeNavigationGraph;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.OpponentsFactory;

/**
 * Test harness for PatrolBehavior.move() logic.
 * 
 * This version uses the real PathCalculator (Dijkstra) and requires
 * a MazeNavigationGraph from GameMazeWorld.
 * 
 * Run this class directly (has a main method).
 */
public class TestPatrolBehaviorMoveManual {

    // ========== Configuration ==========
    private static final int TOTAL_TICKS = 50;
    private static final long TICK_DELAY_MS = 100; // delay between ticks for readability
    private static final double MOVEMENT_SPEED = 2.0; // matches CharacterType default

    // Maze dimensions (must match your game)
    private static final int MAZE_WIDTH = 800;
    private static final int MAZE_HEIGHT = 600;

    public static void main(String[] args) {
        System.out.println("=== PatrolBehavior Move Test ===\n");

        // Initialize the game world and navigation graph
        System.out.println("Initializing GameMazeWorld...");
        GameMazeWorld maze;
        try {
            maze = GameMazeWorld.GetWorld(MAZE_WIDTH, MAZE_HEIGHT);
        } catch (Exception e) {
            System.err.println("Failed to initialize GameMazeWorld: " + e.getMessage());
            System.err.println("Make sure JavaFX is available or run in headless mode.");
            return;
        }

        MazeNavigationGraph graph = maze.getNavigationGraph();
        if (graph == null) {
            System.err.println("Navigation graph is null. Cannot proceed.");
            return;
        }
        System.out.println("Navigation graph initialized: " + graph.getCols() + "x" + graph.getRows() + " nodes\n");

        // Find valid spawn points on the graph (nodes that exist)
        // We'll pick corners or known walkable areas
        Position[] patrolPositions = findValidPatrolPositions(graph);
        if (patrolPositions == null || patrolPositions.length < 2) {
            System.err.println("Could not find enough valid patrol positions on the graph.");
            return;
        }

        System.out.println("Patrol Points (snapped to graph nodes):");
        for (int i = 0; i < patrolPositions.length; i++) {
            System.out.printf("  [%d] (%.1f, %.1f)%n", i, 
                patrolPositions[i].getPosX(), patrolPositions[i].getPosY());
        }

        // Create PatrolBehavior
        PatrolBehavior patrol = BehaviourFactory.eINSTANCE.createPatrolBehavior();
        
        // Add patrol points
        for (Position pos : patrolPositions) {
            PatrolPoint pp = BehaviourFactory.eINSTANCE.createPatrolPoint();
            pp.setPoint(pos);
            patrol.getPath().add(pp);
        }
        
        patrol.setCurrentIndex(0);
        patrol.setBehavior(PatrolPathBehavior.LOOP);

        // Set initial position (start at first patrol point)
        Position startPos = patrolPositions[0];
        Position currentPos = createPosition(startPos.getPosX(), startPos.getPosY());
        patrol.setPosition(currentPos);

        // Create and set Dijkstra PathCalculator
        DijkstraPathCalculator dijkstra = BehaviourFactory.eINSTANCE.createDijkstraPathCalculator();
        dijkstra.setMaxPathLength(200); // Allow long paths
        patrol.setPathcalculator(dijkstra);

        // Create a mock CharacterType for speed
        CharacterType mockCharType = createMockCharacterType(MOVEMENT_SPEED);
        patrol.setCharactertype(mockCharType);

        System.out.println("\nPatrol Mode: " + patrol.getBehavior());
        System.out.println("Movement Speed: " + MOVEMENT_SPEED);
        System.out.println("\nStarting simulation...\n");
        System.out.println("Tick | CurrentIndex | Position (x, y)      | NextPositions | Status");
        System.out.println("-----|--------------|----------------------|---------------|--------");

        // Run simulation
        for (int tick = 1; tick <= TOTAL_TICKS; tick++) {
            // Call the actual move() method
            try {
            	System.out.println("DEBUG: patrol class = " + patrol.getClass().getName());
            	System.out.println("DEBUG: calling move()...");
            	patrol.move();
            	System.out.println("DEBUG: move() returned");
            } catch (Exception e) {
                System.err.printf("%4d | ERROR: %s%n", tick, e.getMessage());
                e.printStackTrace();
                break;
            }

            // Get state after move
            Position pos = patrol.getPosition();
            int idx = patrol.getCurrentIndex();
            int nextPosCount = patrol.getNextPositions() != null ? patrol.getNextPositions().size() : 0;

            // Determine status based on position relative to current target
            String status = determineStatus(patrol, pos);

            System.out.printf("%4d | %12d | (%7.2f, %7.2f)   | %13d | %s%n",
                    tick, idx, pos.getPosX(), pos.getPosY(), nextPosCount, status);

            // Small delay for readability
            try {
                Thread.sleep(TICK_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("\n=== Simulation Complete ===");
    }

    // ========== Helper Methods ==========

    private static Position createPosition(double x, double y) {
        Position p = BehaviourFactory.eINSTANCE.createPosition();
        p.setPosX(x);
        p.setPosY(y);
        return p;
    }

    /**
     * Find valid patrol positions by sampling the navigation graph.
     * Returns positions that correspond to actual graph nodes.
     */
    private static Position[] findValidPatrolPositions(MazeNavigationGraph graph) {
        java.util.List<Position> validPositions = new java.util.ArrayList<>();
        
        MazeNavigationGraph.Node[][] grid = graph.getGrid();
        int cols = graph.getCols();
        int rows = graph.getRows();

        // Sample some nodes from different areas of the graph
        int[][] samplePoints = {
            {cols / 4, rows / 4},
            {3 * cols / 4, rows / 4},
            {3 * cols / 4, 3 * rows / 4},
            {cols / 4, 3 * rows / 4},
            {cols / 2, rows / 2}
        };

        for (int[] point : samplePoints) {
            int c = Math.min(point[0], cols - 1);
            int r = Math.min(point[1], rows - 1);
            
            // Search nearby for a valid node
            MazeNavigationGraph.Node node = findNearestValidNode(grid, c, r, cols, rows);
            if (node != null) {
                validPositions.add(createPosition(node.getX(), node.getY()));
            }
        }

        // Remove duplicates (if nodes overlap)
        java.util.List<Position> unique = new java.util.ArrayList<>();
        for (Position p : validPositions) {
            boolean isDupe = false;
            for (Position existing : unique) {
                if (Math.abs(p.getPosX() - existing.getPosX()) < 1 &&
                    Math.abs(p.getPosY() - existing.getPosY()) < 1) {
                    isDupe = true;
                    break;
                }
            }
            if (!isDupe) {
                unique.add(p);
            }
        }

        return unique.toArray(new Position[0]);
    }

    private static MazeNavigationGraph.Node findNearestValidNode(
            MazeNavigationGraph.Node[][] grid, int startC, int startR, int cols, int rows) {
        // Spiral search outward from start point
        for (int radius = 0; radius < Math.max(cols, rows); radius++) {
            for (int dc = -radius; dc <= radius; dc++) {
                for (int dr = -radius; dr <= radius; dr++) {
                    int c = startC + dc;
                    int r = startR + dr;
                    if (c >= 0 && c < cols && r >= 0 && r < rows) {
                        MazeNavigationGraph.Node node = grid[c][r];
                        if (node != null) {
                            return node;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Create a mock CharacterType with the given speed.
     */
    private static CharacterType createMockCharacterType(double speed) {
        // Try to create via factory if available
        try {
            CharacterType ct = OpponentsFactory.eINSTANCE.createZombie(); // or appropriate type
            ct.setSpeed(speed);
            return ct;
        } catch (Exception e) {
            // If factory not available, return null and let move() use fallback speed
            System.out.println("Note: Could not create CharacterType via factory. Using fallback speed.");
            return null;
        }
    }

    /**
     * Determine a status string based on current state.
     */
    private static String determineStatus(PatrolBehavior patrol, Position currentPos) {
        if (patrol.getPath() == null || patrol.getPath().isEmpty()) {
            return "NO_PATH";
        }

        int idx = patrol.getCurrentIndex();
        if (idx < 0 || idx >= patrol.getPath().size()) {
            return "INVALID_INDEX";
        }

        PatrolPoint target = patrol.getPath().get(idx);
        if (target == null || target.getPoint() == null) {
            return "NULL_TARGET";
        }

        Position targetPos = target.getPoint();
        double dist = distance(currentPos, targetPos);

        if (dist < 1.0) {
            return "AT_TARGET";
        } else if (patrol.getNextPositions() != null && !patrol.getNextPositions().isEmpty()) {
            return "MOVING (dist=" + String.format("%.1f", dist) + ")";
        } else {
            return "COMPUTING_PATH";
        }
    }

    private static double distance(Position a, Position b) {
        if (a == null || b == null) return Double.MAX_VALUE;
        double dx = a.getPosX() - b.getPosX();
        double dy = a.getPosY() - b.getPosY();
        return Math.hypot(dx, dy);
    }
}