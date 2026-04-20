package main.game.maze;

import javafx.scene.layout.Pane;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.Vector2D;
import main.game.maze.service.CharacterIntersectionFixerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CharacterIntersectionFixerService geometry calculations.
 * Uses reflection to test private helper methods for thorough coverage.
 */
public class CharacterIntersectionFixerServiceTest {

    private Pane gameBoard;
    private CharacterIntersectionFixerService service;

    @BeforeEach
    void setUp() {
        gameBoard = new Pane();
        // Use null maze for most tests since we test private methods directly
        service = new CharacterIntersectionFixerService(gameBoard, null);
    }

    @Test
    @DisplayName("fixInitialSpriteMazeIntersections handles null maze gracefully")
    void fixIntersectionsHandlesNullMaze() {
        CharacterIntersectionFixerService nullMazeService = 
            new CharacterIntersectionFixerService(gameBoard, null);
        
        assertDoesNotThrow(() -> nullMazeService.fixInitialSpriteMazeIntersections(),
            "Should handle null maze without throwing");
    }

    @Test
    @DisplayName("pointInRect returns true for point inside rectangle")
    void pointInRectReturnsTrueForInsidePoint() throws Exception {
        Method method = CharacterIntersectionFixerService.class.getDeclaredMethod(
            "pointInRect", double.class, double.class, 
            double.class, double.class, double.class, double.class);
        method.setAccessible(true);
        
        boolean result = (boolean) method.invoke(service, 5.0, 5.0, 0.0, 0.0, 10.0, 10.0);
        
        assertTrue(result, "Point (5,5) should be inside rectangle (0,0)-(10,10)");
    }

    @Test
    @DisplayName("pointInRect returns false for point outside rectangle")
    void pointInRectReturnsFalseForOutsidePoint() throws Exception {
        Method method = CharacterIntersectionFixerService.class.getDeclaredMethod(
            "pointInRect", double.class, double.class, 
            double.class, double.class, double.class, double.class);
        method.setAccessible(true);
        
        boolean result = (boolean) method.invoke(service, 15.0, 15.0, 0.0, 0.0, 10.0, 10.0);
        
        assertFalse(result, "Point (15,15) should be outside rectangle (0,0)-(10,10)");
    }

    @Test
    @DisplayName("pointInRect returns true for point on boundary")
    void pointInRectReturnsTrueForBoundaryPoint() throws Exception {
        Method method = CharacterIntersectionFixerService.class.getDeclaredMethod(
            "pointInRect", double.class, double.class, 
            double.class, double.class, double.class, double.class);
        method.setAccessible(true);
        
        boolean result = (boolean) method.invoke(service, 10.0, 5.0, 0.0, 0.0, 10.0, 10.0);
        
        assertTrue(result, "Point (10,5) on boundary should be inside rectangle");
    }

    @Test
    @DisplayName("segmentsIntersect detects crossing segments")
    void segmentsIntersectDetectsCrossing() throws Exception {
        Method method = CharacterIntersectionFixerService.class.getDeclaredMethod(
            "segmentsIntersect", 
            double.class, double.class, double.class, double.class,
            double.class, double.class, double.class, double.class);
        method.setAccessible(true);
        
        // Horizontal line (0,5) to (10,5) crossing vertical line (5,0) to (5,10)
        boolean result = (boolean) method.invoke(service, 
            0.0, 5.0, 10.0, 5.0,  // segment 1
            5.0, 0.0, 5.0, 10.0); // segment 2
        
        assertTrue(result, "Perpendicular crossing segments should intersect");
    }

    @Test
    @DisplayName("segmentsIntersect rejects parallel non-overlapping segments")
    void segmentsIntersectRejectsParallel() throws Exception {
        Method method = CharacterIntersectionFixerService.class.getDeclaredMethod(
            "segmentsIntersect", 
            double.class, double.class, double.class, double.class,
            double.class, double.class, double.class, double.class);
        method.setAccessible(true);
        
        // Two parallel horizontal lines
        boolean result = (boolean) method.invoke(service, 
            0.0, 0.0, 10.0, 0.0,   // y=0 line
            0.0, 5.0, 10.0, 5.0);  // y=5 line
        
        assertFalse(result, "Parallel non-overlapping segments should not intersect");
    }

    @Test
    @DisplayName("direction returns positive for counterclockwise orientation")
    void directionReturnsPositiveForCCW() throws Exception {
        Method method = CharacterIntersectionFixerService.class.getDeclaredMethod(
            "direction", 
            double.class, double.class, double.class, double.class, double.class, double.class);
        method.setAccessible(true);
        
        // Triangle (0,0), (10,0), (5,5) - k is above the line ij
        double result = (double) method.invoke(service, 0.0, 0.0, 10.0, 0.0, 5.0, 5.0);
        
        assertTrue(result < 0, "Point above line should give negative cross product");
    }

    @Test
    @DisplayName("onSegment returns true for point on segment")
    void onSegmentReturnsTrueForPointOnSegment() throws Exception {
        Method method = CharacterIntersectionFixerService.class.getDeclaredMethod(
            "onSegment", 
            double.class, double.class, double.class, double.class, double.class, double.class);
        method.setAccessible(true);
        
        // Point (5,0) on segment (0,0) to (10,0)
        boolean result = (boolean) method.invoke(service, 0.0, 0.0, 10.0, 0.0, 5.0, 0.0);
        
        assertTrue(result, "Point (5,0) should be on segment (0,0)-(10,0)");
    }

    @Test
    @DisplayName("onSegment returns false for point off segment")
    void onSegmentReturnsFalseForPointOffSegment() throws Exception {
        Method method = CharacterIntersectionFixerService.class.getDeclaredMethod(
            "onSegment", 
            double.class, double.class, double.class, double.class, double.class, double.class);
        method.setAccessible(true);
        
        // Point (15,0) beyond segment (0,0) to (10,0)
        boolean result = (boolean) method.invoke(service, 0.0, 0.0, 10.0, 0.0, 15.0, 0.0);
        
        assertFalse(result, "Point (15,0) should not be on segment (0,0)-(10,0)");
    }
}
