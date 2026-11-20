package main.game.maze;

import java.util.Set;
import javafx.scene.input.KeyCode;
import main.game.maze.common.input.MovementKeys;

/**
 * JavaFX movement keys: arrows, numpad, A left, W up, D right, S down. Used for both the key
 * bindings and reading the movement; the libGDX frontend uses the same layout.
 */
public final class JavaFxMovementKeys {

    public static final MovementKeys<KeyCode> KEYS = new MovementKeys<>(
            Set.of(KeyCode.LEFT, KeyCode.NUMPAD4, KeyCode.A),
            Set.of(KeyCode.RIGHT, KeyCode.NUMPAD6, KeyCode.D),
            Set.of(KeyCode.UP, KeyCode.NUMPAD8, KeyCode.W),
            Set.of(KeyCode.DOWN, KeyCode.NUMPAD5, KeyCode.S));

    private JavaFxMovementKeys() {
    }
}
