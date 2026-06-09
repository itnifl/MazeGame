package main.game.maze.libgdx.input;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks rising-edge transitions for key states.
 */
public final class EdgeKeyTracker {

    private final Map<Integer, Boolean> previousPressed = new HashMap<>();

    public boolean consumeEdge(int keyCode, boolean pressedNow) {
        boolean wasPressed = previousPressed.getOrDefault(keyCode, false);
        previousPressed.put(keyCode, pressedNow);
        return pressedNow && !wasPressed;
    }

    public void reset() {
        previousPressed.clear();
    }
}
