package main.game.maze;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

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
 * JUnit 5 Test for PatrolBehavior.
 * 
 * Note: Since GameMazeWorld depends on JavaFX, these tests assume 
 * the environment allows head-less JavaFX execution or is mocked.
 */
public class PatrolBehaviorTest {

    private static final int MAZE_WIDTH = 800;
    private static final int MAZE_HEIGHT = 600;
    private static final double MOVEMENT_SPEED = 2.0;

    private GameMazeWorld mazeWorld;
    private MazeNavigationGraph graph;
    private PatrolBehavior patrol;

    @BeforeEach
    void setUp() throws Exception {
        // 1. Initialize World (Ideally, you should mock MazeNavigationGraph to avoid loading the GUI)
        // We wrap this in try-catch to fail the test gracefully if JavaFX isn't init
        try {
            mazeWorld = GameMazeWorld.GetWorld(MAZE_WIDTH, MAZE_HEIGHT);
            graph = mazeWorld.getNavigationGraph();
        } catch (Exception e) {
            fail("Could not initialize GameMazeWorld. Ensure JavaFX is setup or use a Mock Graph. Error: " + e.getMessage());
        }

        assertNotNull(graph, "Navigation Graph must not be null");

        // 2. Initialize Behavior
        patrol = BehaviourFactory.eINSTANCE.createPatrolBehavior();
        
        // Setup Calculator
        DijkstraPathCalculator dijkstra = BehaviourFactory.eINSTANCE.createDijkstraPathCalculator();
        dijkstra.setMaxPathLength(500); // Increased length to ensure paths are found
        patrol.setPathcalculator(dijkstra);

        // Setup Character Type (Speed)
        CharacterType charType = OpponentsFactory.eINSTANCE.createZombie();
        charType.setSpeed(MOVEMENT_SPEED);
        patrol.setCharactertype(charType);
        
        patrol.setBehavior(PatrolPathBehavior.LOOP);
    }

    @Test
    @DisplayName("Should calculate NextPositions when moving to first target")
    void testMoveGeneratesPath() {
        // --- ARRANGE ---
        // Find two valid nodes on the graph
        List<Position> validNodes = getValidGraphNodes(graph, 2);
        assertTrue(validNodes.size() >= 2, "Need at least 2 valid graph nodes for this test");

        Position startPos = validNodes.get(0);
        Position targetPos = validNodes.get(1);

        // Set Start Position
        patrol.setPosition(createPosition(startPos.getPosX(), startPos.getPosY()));

        // Add Target to Patrol Path
        PatrolPoint pp = BehaviourFactory.eINSTANCE.createPatrolPoint();
        pp.setPoint(targetPos);
        patrol.getPath().add(pp);
        patrol.setCurrentIndex(0);

        // Ensure we aren't already there
        assertNotEquals(startPos, targetPos, "Start and Target should be different");

        // --- ACT ---
        // Trigger the move logic (which should invoke the path calculator)
        System.out.println("Attempting to calculate path from " + printPos(startPos) + " to " + printPos(targetPos));
        patrol.move();

        // --- ASSERT ---
        
        // 1. The Critical Fix Check: NextPositions should NOT be empty
        assertNotNull(patrol.getNextPositions(), "NextPositions list is null");
        
        // If this assertion fails, it means Dijkstra failed to find a path
        assertFalse(patrol.getNextPositions().isEmpty(), 
            "NextPositions is empty! Path Calculator failed to find path between " 
            + printPos(startPos) + " and " + printPos(targetPos));

        // 2. Check that we didn't just skip the index
        assertEquals(0, patrol.getCurrentIndex(), "Should remain at index 0 while moving towards it");
    }

    @Test
    @DisplayName("Should advance index only when reaching target")
    void testPatrolCycle() {
        // --- ARRANGE ---
        List<Position> validNodes = getValidGraphNodes(graph, 2);
        Position p1 = validNodes.get(0);
        Position p2 = validNodes.get(1);

        // Setup path: P1 -> P2
        addPatrolPoint(p1);
        addPatrolPoint(p2);
        
        // Start exactly at P1
        patrol.setPosition(createPosition(p1.getPosX(), p1.getPosY()));
        patrol.setCurrentIndex(0); 

        // --- ACT & ASSERT ---
        
        // 1. We are AT P1 (Index 0). Logic should detect we are there and increment to Index 1 (P2)
        patrol.move();
        
        // Depending on your implementation, it might increment immediately or after one tick.
        // Assuming it detects "At Target" -> "Increment Index" -> "Calculate path to new Index"
        
        int idx = patrol.getCurrentIndex();
        boolean movedIndex = (idx == 1);
        
        if(movedIndex) {
            // It switched to next target, check if it calculated path
            assertFalse(patrol.getNextPositions().isEmpty(), "Should have path to P2");
        } else {
            // It might take one more tick to update
            patrol.move();
            assertEquals(1, patrol.getCurrentIndex(), "Should have advanced to next patrol point");
        }
    }

    // ================= HELPER METHODS =================

    private void addPatrolPoint(Position p) {
        PatrolPoint pp = BehaviourFactory.eINSTANCE.createPatrolPoint();
        pp.setPoint(p);
        patrol.getPath().add(pp);
    }

    private Position createPosition(double x, double y) {
        Position p = BehaviourFactory.eINSTANCE.createPosition();
        p.setPosX(x);
        p.setPosY(y);
        return p;
    }
    
    private String printPos(Position p) {
        return String.format("[%.1f, %.1f]", p.getPosX(), p.getPosY());
    }

    /**
     * Simplification of your spiral search to find confirmed valid graph nodes.
     */
    private List<Position> getValidGraphNodes(MazeNavigationGraph graph, int count) {
        List<Position> result = new ArrayList<>();
        MazeNavigationGraph.Node[][] grid = graph.getGrid();
        
        // Scan the middle of the map to avoid edges
        for (int x = 10; x < graph.getCols() - 10; x++) {
            for (int y = 10; y < graph.getRows() - 10; y++) {
                MazeNavigationGraph.Node node = grid[x][y];
                if (node != null) { 
                    // Found a walkable node
                    Position p = createPosition(node.getX(), node.getY());
                    result.add(p);
                    if (result.size() >= count) return result;
                    
                    // Skip a few to ensure distance
                    y += 5; 
                }
            }
            if (!result.isEmpty()) x += 5; 
        }
        return result;
    }
}