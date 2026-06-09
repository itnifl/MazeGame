package main.game.maze.libgdx.input;

import java.util.Set;

/**
 * Immutable per-frame input snapshot.
 */
public record InputFrame(Set<Integer> heldKeys, Set<Integer> edgeKeys, int mouseX, int mouseY, boolean leftMouseClicked) {

    public boolean isHeld(int keyCode) {
        return heldKeys.contains(keyCode);
    }

    public boolean isEdge(int keyCode) {
        return edgeKeys.contains(keyCode);
    }
}
