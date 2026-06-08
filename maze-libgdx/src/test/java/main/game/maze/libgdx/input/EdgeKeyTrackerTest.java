package main.game.maze.libgdx.input;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EdgeKeyTrackerTest {

    @Test
    void firesOnlyOnRisingEdge() {
        EdgeKeyTracker tracker = new EdgeKeyTracker();

        assertFalse(tracker.consumeEdge(10, false));
        assertTrue(tracker.consumeEdge(10, true));
        assertFalse(tracker.consumeEdge(10, true));
        assertFalse(tracker.consumeEdge(10, false));
        assertTrue(tracker.consumeEdge(10, true));
    }

    @Test
    void resetClearsLatches() {
        EdgeKeyTracker tracker = new EdgeKeyTracker();
        assertTrue(tracker.consumeEdge(42, true));
        assertFalse(tracker.consumeEdge(42, true));

        tracker.reset();

        assertTrue(tracker.consumeEdge(42, true));
    }
}
