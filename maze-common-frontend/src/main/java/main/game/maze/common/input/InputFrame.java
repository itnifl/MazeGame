package main.game.maze.common.input;

import java.util.Set;

/**
 * Immutable per-frame input snapshot, parameterized on the frontend key type {@code K}.
 *
 * <p>Each frontend supplies its own physical key type: {@code Integer} for libGDX
 * ({@code Input.Keys.*}) and {@code KeyCode} for JavaFX. Mouse coordinates are kept as
 * {@code double} so both the libGDX integer pixel coordinates and the JavaFX double
 * scene coordinates fit without loss.</p>
 *
 * @param <K> the physical key type
 */
public final class InputFrame<K> {

    private final Set<K> heldKeys;
    private final Set<K> edgeKeys;
    private final double mouseX;
    private final double mouseY;
    private final boolean leftMouseClicked;
    private final boolean secondaryMouseClicked;

    public InputFrame(Set<K> heldKeys, Set<K> edgeKeys, double mouseX, double mouseY, boolean leftMouseClicked) {
        this(heldKeys, edgeKeys, mouseX, mouseY, leftMouseClicked, false);
    }

    /** @param secondaryMouseClicked the secondary (usually right) button was clicked this frame */
    public InputFrame(Set<K> heldKeys, Set<K> edgeKeys, double mouseX, double mouseY, boolean leftMouseClicked,
                      boolean secondaryMouseClicked) {
        this.secondaryMouseClicked = secondaryMouseClicked;
        this.heldKeys = Set.copyOf(heldKeys);
        this.edgeKeys = Set.copyOf(edgeKeys);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.leftMouseClicked = leftMouseClicked;
    }

    public Set<K> heldKeys() {
        return heldKeys;
    }

    public Set<K> edgeKeys() {
        return edgeKeys;
    }

    public double mouseX() {
        return mouseX;
    }

    public double mouseY() {
        return mouseY;
    }

    public boolean leftMouseClicked() {
        return leftMouseClicked;
    }

    public boolean secondaryMouseClicked() {
        return secondaryMouseClicked;
    }

    /** Whether this mouse button was clicked this frame. */
    public boolean isMouseClicked(MouseButton button) {
        return button == MouseButton.PRIMARY ? leftMouseClicked : secondaryMouseClicked;
    }

    public boolean isHeld(K key) {
        return heldKeys.contains(key);
    }

    public boolean isEdge(K key) {
        return edgeKeys.contains(key);
    }
}
