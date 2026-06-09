package main.game.maze;

import java.util.LinkedHashSet;
import java.util.Set;
import javafx.scene.input.KeyCode;

/**
 * Builds immutable JavaFX input snapshots from the controller key state.
 */
final class JavaFxInputSnapshotReader {

    JavaFxInputFrame read(Set<KeyCode> heldKeys, Set<KeyCode> edgeKeys, double mouseX, double mouseY, boolean leftMouseClicked) {
        JavaFxInputFrame frame = new JavaFxInputFrame(
                Set.copyOf(new LinkedHashSet<>(heldKeys)),
                Set.copyOf(new LinkedHashSet<>(edgeKeys)),
                mouseX,
                mouseY,
                leftMouseClicked);
        edgeKeys.clear();
        return frame;
    }
}
