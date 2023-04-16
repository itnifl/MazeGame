package main.game.maze;

import javafx.geometry.Point2D;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Vector2DTest {

    private Vector2D vector;

    @BeforeEach
    public void setUp() {
        vector = new Vector2D(new Point2D(0, 0), new Point2D(3, 4));
    }

    @Test
    public void TwoVectorsIntersect_true() {
        Vector2D vector1 = new Vector2D(0, 0, 100, 100);
        Vector2D vector2 = new Vector2D(100, 0, 0, 100);

        var intersect = vector1.doIntersect(vector2, 1);

        Assertions.assertEquals(intersect, true);
    }

    @Test
    public void TwoVectorsIntersect_false() {
        Vector2D vector1 = new Vector2D(0, 150, 0, 160);
        Vector2D vector2 = new Vector2D(5, 155, 5, 165);

        var intersect = vector1.doIntersect(vector2, 1);

        Assertions.assertEquals(intersect, false);
    }

    @Test
    public void testGetStart() {
        Assertions.assertEquals(new Point2D(0, 0), vector.getStart());
    }

    @Test
    public void testGetEnd() {
        Assertions.assertEquals(new Point2D(3, 4), vector.getEnd());
    }

    @Test
    public void testMagnitude() {
        Assertions.assertEquals(5.0, vector.magnitude(), 0.00001);
    }

    @Test
    public void testNormalize() {
        Vector2D normalized = vector.normalize(1);
        Assertions.assertEquals(1.0, normalized.magnitude(), 0.00001);
        Assertions.assertEquals(new Point2D(0, 0), normalized.getStart());
    }

    @Test
    public void testAdd() {
        Vector2D other = new Vector2D(new Point2D(2, 2), new Point2D(5, 6));
        Vector2D sum = vector.add(other);
        Assertions.assertEquals(new Point2D(2, 2), other.getStart());
        Assertions.assertEquals(new Point2D(5, 6), other.getEnd());
        Assertions.assertEquals(new Point2D(2, 2), sum.getStart());
        Assertions.assertEquals(new Point2D(8, 10), sum.getEnd());
    }

    @Test
    public void testSubtract() {
        Vector2D other = new Vector2D(new Point2D(2, 2), new Point2D(5, 6));
        Vector2D difference = vector.subtract(other);
        Assertions.assertEquals(new Point2D(2, 2), other.getStart());
        Assertions.assertEquals(new Point2D(5, 6), other.getEnd());
        Assertions.assertEquals(new Point2D(-2, -2), difference.getStart());
        Assertions.assertEquals(new Point2D(-2, -2), difference.getEnd());
    }

    @Test
    public void testDotProduct() {
        Vector2D other = new Vector2D(new Point2D(2, 2), new Point2D(5, 6));
        Assertions.assertEquals(25.0, vector.dotProduct(other), 0.00001);
    }

}
