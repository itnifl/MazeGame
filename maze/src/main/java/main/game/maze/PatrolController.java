package main.game.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.game.maze.behaviour.BehaviourFactory;
import main.game.maze.behaviour.DijkstraPathCalculator;
import main.game.maze.behaviour.PatrolBehavior;
import main.game.maze.behaviour.PatrolPathBehavior;
import main.game.maze.behaviour.PatrolPoint;
import main.game.maze.behaviour.Position;
import main.game.maze.characters.ComputerCharacter;
import main.game.maze.characters.interfaces.IMovingComputerCharacter;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.service.MazeNavigationGraph;

public class PatrolController {

    private static final Logger LOGGER = Logger.getLogger(PatrolController.class.getName());
    private static final int WANDER_FALLBACK_TICKS = 60;

    // Cache to hold the stateful Behavior for each character, as we are manually instantiating them.
    // WeakHashMap ensures cleanup when characters are removed/garbage collected.
    private static final Map<IMovingComputerCharacter, PatrolBehavior> behaviorCache = new WeakHashMap<>();
    
    // Track remaining wander ticks when patrol fails
    private static final Map<IMovingComputerCharacter, Integer> wanderTicksRemaining = new WeakHashMap<>();

    /**
     * Checks if the character is currently in wander fallback mode.
     * If so, decrements the counter and returns true.
     */
    public static boolean isInWanderFallback(IMovingComputerCharacter character) {
        Integer remaining = wanderTicksRemaining.get(character);
        if (remaining != null && remaining > 0) {
            wanderTicksRemaining.put(character, remaining - 1);
            return true;
        }
        return false;
    }

    /**
     * Triggers wander fallback mode for the specified number of ticks.
     */
    public static void triggerWanderFallback(IMovingComputerCharacter character) {
        wanderTicksRemaining.put(character, WANDER_FALLBACK_TICKS);
        LOGGER.fine("Character entered wander fallback for " + WANDER_FALLBACK_TICKS + " ticks");
    }

    /**
     * Calculates the direction (Unit Vector) the character should move to reach the next step.
     * Returns null if no valid direction could be calculated (caller should use wander).
     */
    public static Point2D getDirectionToNextPatrolPoint(IMovingComputerCharacter computerCharacter) {
        if (!(computerCharacter instanceof ComputerCharacter cc)) {
            return null;
        }

        try {
            // 1. Get existing or Instantiate NEW PatrolBehavior (Stateful)
            PatrolBehavior patrol = behaviorCache.computeIfAbsent(cc, k -> {
                PatrolBehavior pb = BehaviourFactory.eINSTANCE.createPatrolBehavior();
                initializePatrolRoute(pb);
                return pb;
            });

            // 2. Sync EMF Position with Character's current visual position
            // (The behavior needs to know where the character actually is to calculate the next step)
            if (patrol.getPosition() == null) {
                patrol.setPosition(BehaviourFactory.eINSTANCE.createPosition());
            }
            patrol.getPosition().setPosX(cc.getCharacterPosition().getX());
            patrol.getPosition().setPosY(cc.getCharacterPosition().getY());

            // 3. Run EMF Logic (Calculate Path / Advance Index / Update NextPositions)
            patrol.move();

            // 4. Determine Direction Vector to the next immediate waypoint
            if (!patrol.getNextPositions().isEmpty()) {
                Position nextStep = patrol.getNextPositions().get(0);
                double currentX = cc.getCharacterPosition().getX();
                double currentY = cc.getCharacterPosition().getY();

                double deltaX = nextStep.getPosX() - currentX;
                double deltaY = nextStep.getPosY() - currentY;

                // Normalize direction (-1, 0, 1)
                int dirX = 0;
                int dirY = 0;
                
                // Use small threshold to prevent jitter when aligned
                if (Math.abs(deltaX) > 1.0) dirX = (int) Math.signum(deltaX);
                if (Math.abs(deltaY) > 1.0) dirY = (int) Math.signum(deltaY);

                // Simple 4-way movement priority to align with grid
                if (dirX != 0 && dirY != 0) {
                    if (Math.abs(deltaX) > Math.abs(deltaY)) dirY = 0;
                    else dirX = 0;
                }

                return new Point2D(dirX, dirY);
            }

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error calculating patrol direction", e);
        }

        return null;  // No valid direction - caller should use wander fallback
    }

    private static void initializePatrolRoute(PatrolBehavior patrol) {
        try {
            GameMazeWorld maze = GameMazeWorld.GetWorld();
            if (maze == null) return;
            
            MazeNavigationGraph graph = maze.getNavigationGraph();
            if (graph == null) return;

            // Find valid points (Corners/Area centers) based on grid size
            List<Position> validPositions = new ArrayList<>();
            int cols = graph.getCols();
            int rows = graph.getRows();
            int[][] samplePoints = {
                {cols / 4, rows / 4}, {3 * cols / 4, rows / 4},
                {3 * cols / 4, 3 * rows / 4}, {cols / 4, 3 * rows / 4}
            };

            for (int[] p : samplePoints) {
                // Bounds check
                int c = Math.max(0, Math.min(p[0], cols-1));
                int r = Math.max(0, Math.min(p[1], rows-1));
                
                MazeNavigationGraph.Node node = graph.getGrid()[c][r];
                if (node != null) {
                    Position pos = BehaviourFactory.eINSTANCE.createPosition();
                    pos.setPosX(node.getX());
                    pos.setPosY(node.getY());
                    validPositions.add(pos);
                }
            }

            // Apply to Behavior
            for (Position pos : validPositions) {
                PatrolPoint pp = BehaviourFactory.eINSTANCE.createPatrolPoint();
                pp.setPoint(pos);
                patrol.getPath().add(pp);
            }

            patrol.setCurrentIndex(0);
            patrol.setBehavior(PatrolPathBehavior.LOOP);
            
            // Set calculator
            DijkstraPathCalculator dijkstra = BehaviourFactory.eINSTANCE.createDijkstraPathCalculator();
            dijkstra.setMaxPathLength(500);
            patrol.setPathcalculator(dijkstra);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to init patrol route", e);
        }
    }
}