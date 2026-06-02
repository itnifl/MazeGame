package main.game.maze.game.status;

/**
 * Stores a transient status message with a countdown timer.
 */
public final class StatusMessageBus {

    private static final float DEFAULT_DURATION_SECONDS = 2.4f;

    private String message = "";
    private float secondsRemaining;

    public void publish(String text) {
        publish(text, DEFAULT_DURATION_SECONDS);
    }

    public void publish(String text, float durationSeconds) {
        message = text == null ? "" : text;
        secondsRemaining = Math.max(0f, durationSeconds);
        if (message.isBlank() || secondsRemaining <= 0f) {
            clear();
        }
    }

    public void tick(float dt) {
        if (secondsRemaining <= 0f) {
            return;
        }
        secondsRemaining = Math.max(0f, secondsRemaining - Math.max(0f, dt));
        if (secondsRemaining == 0f) {
            clear();
        }
    }

    public String currentMessage() {
        return message;
    }

    public boolean hasMessage() {
        return !message.isBlank();
    }

    public float secondsRemaining() {
        return secondsRemaining;
    }

    public void clear() {
        message = "";
        secondsRemaining = 0f;
    }
}
