package main.game.maze.common.input;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EdgeKeyTrackerTest {

    @Test
    void firesOnlyOnRisingEdge() {
        EdgeKeyTracker<Integer> tracker = new EdgeKeyTracker<>();

        assertFalse(tracker.consumeEdge(10, false));
        assertTrue(tracker.consumeEdge(10, true));
        assertFalse(tracker.consumeEdge(10, true));
        assertFalse(tracker.consumeEdge(10, false));
        assertTrue(tracker.consumeEdge(10, true));
    }

    @Test
    void resetClearsLatches() {
        EdgeKeyTracker<Integer> tracker = new EdgeKeyTracker<>();
        assertTrue(tracker.consumeEdge(42, true));
        assertFalse(tracker.consumeEdge(42, true));

        tracker.reset();

        assertTrue(tracker.consumeEdge(42, true));
    }

    @Test
    void tracksDistinctKeysIndependently() {
        EdgeKeyTracker<String> tracker = new EdgeKeyTracker<>();

        assertTrue(tracker.consumeEdge("A", true));
        assertTrue(tracker.consumeEdge("B", true));
        assertFalse(tracker.consumeEdge("A", true));
        assertFalse(tracker.consumeEdge("B", true));
    }
}
