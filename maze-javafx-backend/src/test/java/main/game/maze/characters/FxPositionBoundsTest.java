package main.game.maze.characters;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link FxPositionBounds}.
 * BoundingBox is pure geometry (javafx.base) and requires no FX toolkit.
 */
class FxPositionBoundsTest {

    private static FxPositionBounds box(double minX, double minY, double maxX, double maxY) {
        return new FxPositionBounds(new BoundingBox(minX, minY, maxX - minX, maxY - minY));
    }

    @Test
    void constructorThrowsOnNullBounds() {
        assertThrows(IllegalArgumentException.class, () -> new FxPositionBounds(null));
    }

    @Test
    void gettersDelegate() {
        Bounds b = new BoundingBox(10, 20, 30, 40); // minX=10, minY=20, w=30, h=40
        FxPositionBounds pb = new FxPositionBounds(b);
        assertEquals(10.0, pb.getMinX(), 1e-9);
        assertEquals(20.0, pb.getMinY(), 1e-9);
        assertEquals(40.0, pb.getMaxX(), 1e-9); // minX + width = 10+30
        assertEquals(60.0, pb.getMaxY(), 1e-9); // minY + height = 20+40
    }

    @Test
    void toJfxBoundsReturnsSameInstance() {
        Bounds b = new BoundingBox(0, 0, 5, 5);
        FxPositionBounds pb = new FxPositionBounds(b);
        assertSame(b, pb.toJfxBounds());
    }

    @Test
    void intersectsNullReturnsFalse() {
        assertFalse(box(0, 0, 10, 10).intersects(null));
    }

    @Test
    void intersectsOverlappingReturnsTrue() {
        FxPositionBounds a = box(0, 0, 10, 10);
        FxPositionBounds b = box(5, 5, 15, 15);
        assertTrue(a.intersects(b));
        assertTrue(b.intersects(a));
    }

    @Test
    void intersectsTouchingEdgeReturnsTrue() {
        FxPositionBounds a = box(0, 0, 10, 10);
        FxPositionBounds b = box(10, 0, 20, 10); // right edge of a == left edge of b
        assertTrue(a.intersects(b));
    }

    @Test
    void intersectsToTheRightReturnsFalse() {
        // other.getMaxX() < getMinX() → false (this is to the right of other)
        FxPositionBounds a = box(20, 0, 30, 10);
        FxPositionBounds b = box(0,  0, 10, 10);
        assertFalse(a.intersects(b)); // b is entirely left of a
    }

    @Test
    void intersectsToTheLeftReturnsFalse() {
        // other.getMinX() > getMaxX()
        FxPositionBounds a = box(0,  0, 10, 10);
        FxPositionBounds b = box(20, 0, 30, 10);
        assertFalse(a.intersects(b)); // b is entirely right of a
    }

    @Test
    void intersectsAboveReturnsFalse() {
        // other.getMaxY() < getMinY()
        FxPositionBounds a = box(0, 20, 10, 30);
        FxPositionBounds b = box(0,  0, 10, 10);
        assertFalse(a.intersects(b)); // b is entirely above a
    }

    @Test
    void intersectsBelowReturnsFalse() {
        // other.getMinY() > getMaxY()
        FxPositionBounds a = box(0,  0, 10, 10);
        FxPositionBounds b = box(0, 20, 10, 30);
        assertFalse(a.intersects(b)); // b is entirely below a
    }

    @Test
    void intersectsSelfIsTrue() {
        FxPositionBounds a = box(5, 5, 15, 15);
        assertTrue(a.intersects(a));
    }
}
