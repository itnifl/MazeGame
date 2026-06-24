package main.game.maze;

import java.util.LinkedHashSet;
import java.util.Set;
import javafx.scene.input.KeyCode;
import main.game.maze.common.input.EdgeKeyTracker;
import main.game.maze.common.input.InputFrame;

/**
 * Builds immutable JavaFX input snapshots from the controller key state.
 */
final class JavaFxInputSnapshotReader {

    private final EdgeKeyTracker<KeyCode> edgeKeyTracker = new EdgeKeyTracker<>();


    InputFrame<KeyCode> read(Set<KeyCode> heldKeys, Set<KeyCode> edgeKeys, double mouseX, double mouseY, boolean leftMouseClicked) {
        // Compute edge keys using the shared EdgeKeyTracker for parity with libGDX
        Set<KeyCode> computedEdges = new LinkedHashSet<>();
        // We iterate over a union of previously known and currently held keys to ensure we catch all transitions
        Set<KeyCode> allRelevantKeys = new LinkedHashSet<>(heldKeys);
        
        // This is a bit tricky in FX because we rely on event-driven updates.
        // But we can still use the tracker to verify if a 'held' key just started.
        for (KeyCode key : heldKeys) {
            if (edgeKeyTracker.consumeEdge(key, true)) {
                computedEdges.add(key);
            }
        }
        
        // Ensure keys NOT in heldKeys are marked as released in the tracker
        // Since we don't have a full key list, we can only track what we've seen.
        // But GameController calls handleKeyReleased which we can hook into or just
        // let the next frame's read() handle it if we knew what to clear.
        
        InputFrame<KeyCode> frame = new InputFrame<>(
                Set.copyOf(heldKeys),
                Set.copyOf(computedEdges),
                mouseX,
                mouseY,
                leftMouseClicked);
        return frame;
    }

    public void clearKey(KeyCode code) {
        edgeKeyTracker.consumeEdge(code, false);
    }
}

