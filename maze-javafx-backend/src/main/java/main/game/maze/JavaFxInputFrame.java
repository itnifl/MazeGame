package main.game.maze;

import java.util.Set;
import javafx.scene.input.KeyCode;

/**
 * Immutable per tick input snapshot for JavaFX gameplay updates.
 */
record JavaFxInputFrame(Set<KeyCode> heldKeys, Set<KeyCode> edgeKeys, double mouseX, double mouseY, boolean leftMouseClicked) {

    static JavaFxInputFrame empty() {
        return new JavaFxInputFrame(Set.of(), Set.of(), 0d, 0d, false);
    }

    boolean isHeld(KeyCode keyCode) {
        return heldKeys.contains(keyCode);
    }

    boolean isEdge(KeyCode keyCode) {
        return edgeKeys.contains(keyCode);
    }
}
