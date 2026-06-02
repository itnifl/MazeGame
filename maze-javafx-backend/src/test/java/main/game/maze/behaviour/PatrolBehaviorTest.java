package main.game.maze.behaviour;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.game.maze.behaviour.BehaviourFactory;
import main.game.maze.behaviour.PatrolBehavior;
import main.game.maze.behaviour.PatrolPathBehavior;
import main.game.maze.behaviour.PatrolPoint;
import main.game.maze.behaviour.Position;
import main.game.maze.behaviour.impl.PathCalculatorImpl;
import main.game.maze.mazeworld.service.MazeNavigationGraph;
import main.game.maze.opponents.CharacterType;
import main.game.maze.opponents.OpponentsFactory;

/**
 * Unit Test for PatrolBehavior.
 * Uses a StubPathCalculator to ensure tests are deterministic and independent of Maze generation.
 */
public class PatrolBehaviorTest {

    private PatrolBehavior patrol;
    private StubPathCalculator stubCalculator;

    @BeforeEach
    void setUp() throws Exception {
        // 1. Initialize Behavior
        patrol = BehaviourFactory.eINSTANCE.createPatrolBehavior();
        
        // 2. Use our Stub Calculator (Guarantees paths always exist)
        stubCalculator = new StubPathCalculator();
        patrol.setPathcalculator(stubCalculator);

        // 3. Setup Character Type (Speed)
        CharacterType charType = OpponentsFactory.eINSTANCE.createZombie();
        charType.setSpeed(2.0); // 2.0 units per tick
        patrol.setCharactertype(charType);
        
        patrol.setBehavior(PatrolPathBehavior.LOOP);
    }

    @Test
    @DisplayName("Should populate NextPositions when moving to target")
    void testMoveGeneratesPath() {
        // Arrange
        Position start = createPosition(100, 100);
        Position target = createPosition(200, 100);

        patrol.setPosition(start);
        addPatrolPoint(target);
        patrol.setCurrentIndex(0);

        // Act
        patrol.move();

        // Assert
        assertNotNull(patrol.getNextPositions(), "NextPositions should not be null");
        assertFalse(patrol.getNextPositions().isEmpty(), "NextPositions should not be empty");
        
        // Verify the stub was called
        assertTrue(stubCalculator.wasCalled, "PathCalculator should have been invoked");
        
        // Verify we are moving towards target
        Position nextStep = patrol.getNextPositions().get(0);
        assertTrue(nextStep.getPosX() > 100, "Should move right towards 200");
    }

    @Test
    @DisplayName("Should advance index upon reaching target")
    void testPatrolCycle() {
        // Arrange: P1 -> P2
        Position p1 = createPosition(100, 100);
        Position p2 = createPosition(120, 100); // Distance 20. Speed 2. ~10 ticks.

        addPatrolPoint(p1);
        addPatrolPoint(p2);
        
        // Start AT P1
        patrol.setPosition(createPosition(100, 100));
        patrol.setCurrentIndex(0); 

        // Act 1: Move. logic should detect we are AT target P1.
        patrol.move();
        
        // Depending on wait logic, it might take 1 or 2 ticks to switch.
        // If it waits, force clear wait.
        if (patrol.getCurrentIndex() == 0) {
            patrol.move(); 
        }

        // Assert 1: Should have switched to Index 1 (P2)
        assertEquals(1, patrol.getCurrentIndex(), "Should have advanced to next patrol point (Index 1)");

        // Act 2: Move towards P2
        patrol.move();

        // Assert 2: Should have path to P2
        assertFalse(patrol.getNextPositions().isEmpty(), "Should generate path to P2");
        Position next = patrol.getNextPositions().get(0);
        assertEquals(120, next.getPosX(), 0.1, "Stub returns direct target as next step");
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

    /**
     * Internal Stub class to mock pathfinding.
     * Simply returns the target position as the path.
     */
    class StubPathCalculator extends PathCalculatorImpl {
        boolean wasCalled = false;

        @Override
        public EList<MazeNavigationGraph.Node> compute(MazeNavigationGraph.Node start, MazeNavigationGraph.Node end) {
            // Not used by the bridge override below, but required by abstract class
            return new BasicEList<>();
        }
        
        @Override
        public EList<Position> calculatePath(Position start, Position end) {
            wasCalled = true;
            EList<Position> path = new BasicEList<>();
            
            // Return a simple path: [Target]
            // This simulates a straight line with no obstacles
            Position step = createPosition(end.getPosX(), end.getPosY());
            path.add(step);
            
            return path;
        }
    }
}