package main.game.maze.libgdx.input;

import com.badlogic.gdx.Input;
import java.util.Set;
import main.game.maze.common.input.MovementKeys;

/**
 * libGDX movement keys: arrows, numpad, A left, W up, D right, S down. Used for both the key
 * bindings and reading the movement; the JavaFX frontend uses the same layout.
 */
public final class GdxMovementKeys {

    public static final MovementKeys<Integer> KEYS = new MovementKeys<>(
            Set.of(Input.Keys.LEFT, Input.Keys.NUMPAD_4, Input.Keys.A),
            Set.of(Input.Keys.RIGHT, Input.Keys.NUMPAD_6, Input.Keys.D),
            Set.of(Input.Keys.UP, Input.Keys.NUMPAD_8, Input.Keys.W),
            Set.of(Input.Keys.DOWN, Input.Keys.NUMPAD_5, Input.Keys.S));

    private GdxMovementKeys() {
    }
}
