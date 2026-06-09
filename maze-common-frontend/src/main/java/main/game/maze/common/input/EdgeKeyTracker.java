package main.game.maze.common.input;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks rising-edge transitions for key states, parameterized on the key type {@code K}.
 *
 * @param <K> the physical key type
 */
public final class EdgeKeyTracker<K> {

    private final Map<K, Boolean> previousPressed = new HashMap<>();

    public boolean consumeEdge(K key, boolean pressedNow) {
        boolean wasPressed = previousPressed.getOrDefault(key, false);
        previousPressed.put(key, pressedNow);
        return pressedNow && !wasPressed;
    }

    public void reset() {
        previousPressed.clear();
    }
}
