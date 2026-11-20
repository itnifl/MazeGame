package main.game.maze.common.input;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Which keys move the player in which direction. Each frontend builds one with its own key type
 * (JavaFX {@code KeyCode}, libGDX {@code Input.Keys}) and uses it both for the key bindings and
 * for reading the movement, so the two can never disagree. Directions are screen y-down: up is -1.
 */
public final class MovementKeys<K> {

    private final Set<K> left;
    private final Set<K> right;
    private final Set<K> up;
    private final Set<K> down;

    public MovementKeys(Set<K> left, Set<K> right, Set<K> up, Set<K> down) {
        this.left = Set.copyOf(left);
        this.right = Set.copyOf(right);
        this.up = Set.copyOf(up);
        this.down = Set.copyOf(down);
        Set<K> seen = new HashSet<>();
        for (Set<K> direction : java.util.List.of(this.left, this.right, this.up, this.down)) {
            for (K key : direction) {
                if (!seen.add(key)) {
                    throw new IllegalArgumentException("key bound to two directions: " + key);
                }
            }
        }
    }

    public boolean isLeft(InputFrame<K> frame) {
        return anyHeld(left, frame);
    }

    public boolean isRight(InputFrame<K> frame) {
        return anyHeld(right, frame);
    }

    public boolean isUp(InputFrame<K> frame) {
        return anyHeld(up, frame);
    }

    public boolean isDown(InputFrame<K> frame) {
        return anyHeld(down, frame);
    }

    /** -1 left, 1 right, 0 none or both. */
    public int dx(InputFrame<K> frame) {
        return (isRight(frame) ? 1 : 0) - (isLeft(frame) ? 1 : 0);
    }

    /** -1 up, 1 down (screen y-down), 0 none or both. */
    public int dy(InputFrame<K> frame) {
        return (isDown(frame) ? 1 : 0) - (isUp(frame) ? 1 : 0);
    }

    /** Every movement key, for binding them all to {@link GameAction#MOVE_PLAYER}. */
    public Set<K> all() {
        Set<K> all = new LinkedHashSet<>(left);
        all.addAll(right);
        all.addAll(up);
        all.addAll(down);
        return Set.copyOf(all);
    }

    private static <K> boolean anyHeld(Set<K> keys, InputFrame<K> frame) {
        for (K key : keys) {
            if (frame.isHeld(key)) {
                return true;
            }
        }
        return false;
    }
}
