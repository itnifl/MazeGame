package main.game.maze.libgdx.controller;

/**
 * Translates movement key state into normalized movement intent.
 */
public final class GdxPlayerInputController {

    public MovementIntent resolveMovement(boolean left, boolean right, boolean down, boolean up) {
        float dx = 0f;
        float dy = 0f;

        if (left) {
            dx -= 1f;
        }
        if (right) {
            dx += 1f;
        }
        if (down) {
            dy -= 1f;
        }
        if (up) {
            dy += 1f;
        }

        if (dx != 0f && dy != 0f) {
            float inv = (float) (1.0 / Math.sqrt(2.0));
            dx *= inv;
            dy *= inv;
        }
        return new MovementIntent(dx, dy);
    }

    public record MovementIntent(float dx, float dy) {
        public boolean hasMovement() {
            return dx != 0f || dy != 0f;
        }
    }
}


