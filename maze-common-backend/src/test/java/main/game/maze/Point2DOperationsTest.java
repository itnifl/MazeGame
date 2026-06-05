package main.game.maze;

import main.game.maze.mazeworld.Point2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Point2D mathematical operations and object behavior.
 */
public class Point2DOperationsTest {

    @Test
    @DisplayName("add with doubles creates new point with sum coordinates")
    void addDoublesCreatesNewPoint() {
        Point2D point = new Point2D(10, 20);
        
        Point2D result = point.add(5, 3);
        
        assertEquals(15, result.getX(), 0.01);
        assertEquals(23, result.getY(), 0.01);
        // Original unchanged
        assertEquals(10, point.getX(), 0.01);
    }

    @Test
    @DisplayName("add with Point2D creates new point with sum coordinates")
    void addPointCreatesNewPoint() {
        Point2D p1 = new Point2D(10, 20);
        Point2D p2 = new Point2D(5, 3);
        
        Point2D result = p1.add(p2);
        
        assertEquals(15, result.getX(), 0.01);
        assertEquals(23, result.getY(), 0.01);
    }

    @Test
    @DisplayName("subtract creates point with difference coordinates")
    void subtractCreatesNewPoint() {
        Point2D p1 = new Point2D(10, 20);
        Point2D p2 = new Point2D(3, 5);
        
        Point2D result = p1.subtract(p2);
        
        assertEquals(7, result.getX(), 0.01);
        assertEquals(15, result.getY(), 0.01);
    }

    @Test
    @DisplayName("multiply scales both coordinates")
    void multiplyScalesCoordinates() {
        Point2D point = new Point2D(3, 4);
        
        Point2D result = point.multiply(2);
        
        assertEquals(6, result.getX(), 0.01);
        assertEquals(8, result.getY(), 0.01);
    }

    @Test
    @DisplayName("distance calculates Euclidean distance")
    void distanceCalculatesEuclidean() {
        Point2D p1 = new Point2D(0, 0);
        Point2D p2 = new Point2D(3, 4);
        
        assertEquals(5, p1.distance(p2), 0.01);
    }

    @Test
    @DisplayName("distance to self is zero")
    void distanceToSelfIsZero() {
        Point2D point = new Point2D(5, 5);
        
        assertEquals(0, point.distance(point), 0.01);
    }

    @Test
    @DisplayName("magnitude returns length from origin")
    void magnitudeReturnsLength() {
        Point2D point = new Point2D(3, 4);
        
        assertEquals(5, point.magnitude(), 0.01);
    }

    @Test
    @DisplayName("normalize creates unit vector")
    void normalizeCreatesUnitVector() {
        Point2D point = new Point2D(3, 4);
        
        Point2D normalized = point.normalize();
        
        assertEquals(0.6, normalized.getX(), 0.01);
        assertEquals(0.8, normalized.getY(), 0.01);
        assertEquals(1.0, normalized.magnitude(), 0.01);
    }

    @Test
    @DisplayName("normalize of zero vector returns zero")
    void normalizeZeroReturnsZero() {
        Point2D zero = new Point2D(0, 0);
        
        Point2D normalized = zero.normalize();
        
        assertEquals(0, normalized.getX(), 0.01);
        assertEquals(0, normalized.getY(), 0.01);
    }

    @Test
    @DisplayName("equals returns true for same coordinates")
    void equalsReturnsTrueForSameCoords() {
        Point2D p1 = new Point2D(5, 10);
        Point2D p2 = new Point2D(5, 10);
        
        assertEquals(p1, p2);
    }

    @Test
    @DisplayName("equals returns false for different coordinates")
    void equalsReturnsFalseForDifferentCoords() {
        Point2D p1 = new Point2D(5, 10);
        Point2D p2 = new Point2D(5, 11);
        
        assertNotEquals(p1, p2);
    }

    @Test
    @DisplayName("hashCode is consistent for equal points")
    void hashCodeConsistentForEqual() {
        Point2D p1 = new Point2D(5, 10);
        Point2D p2 = new Point2D(5, 10);
        
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    @DisplayName("ZERO constant is at origin")
    void zeroConstantAtOrigin() {
        assertEquals(0, Point2D.ZERO.getX(), 0.01);
        assertEquals(0, Point2D.ZERO.getY(), 0.01);
    }

    @Test
    @DisplayName("midpoint returns center between two points")
    void midpointReturnsCenter() {
        Point2D p1 = new Point2D(0, 0);
        Point2D p2 = new Point2D(10, 10);
        
        Point2D mid = p1.midpoint(p2);
        
        assertEquals(5, mid.getX(), 0.01);
        assertEquals(5, mid.getY(), 0.01);
    }

    @Test
    @DisplayName("dotProduct calculates correctly")
    void dotProductCalculates() {
        Point2D p1 = new Point2D(2, 3);
        Point2D p2 = new Point2D(4, 5);
        
        // 2*4 + 3*5 = 8 + 15 = 23
        assertEquals(23, p1.dotProduct(p2), 0.01);
    }

    @Test
    @DisplayName("interpolate at 0 returns this point")
    void interpolateAtZeroReturnsThis() {
        Point2D p1 = new Point2D(0, 0);
        Point2D p2 = new Point2D(10, 10);
        
        Point2D result = p1.interpolate(p2, 0);
        
        assertSame(p1, result);
    }

    @Test
    @DisplayName("interpolate at 1 returns target point")
    void interpolateAtOneReturnsTarget() {
        Point2D p1 = new Point2D(0, 0);
        Point2D p2 = new Point2D(10, 10);
        
        Point2D result = p1.interpolate(p2, 1);
        
        assertSame(p2, result);
    }

    @Test
    @DisplayName("interpolate at 0.5 returns midpoint")
    void interpolateAtHalfReturnsMidpoint() {
        Point2D p1 = new Point2D(0, 0);
        Point2D p2 = new Point2D(10, 10);
        
        Point2D result = p1.interpolate(p2, 0.5);
        
        assertEquals(5, result.getX(), 0.01);
        assertEquals(5, result.getY(), 0.01);
    }

    @Test
    @DisplayName("toString formats correctly")
    void toStringFormatsCorrectly() {
        Point2D point = new Point2D(3.5, 4.5);
        
        String str = point.toString();
        
        assertTrue(str.contains("3.5"), "Should contain x coordinate");
        assertTrue(str.contains("4.5"), "Should contain y coordinate");
    }
}


