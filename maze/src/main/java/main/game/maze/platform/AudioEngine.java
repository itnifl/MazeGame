package main.game.maze.platform;

/**
 * Global accessor for the active {@link IAudioEngine}. Defaults to the
 * JavaFX-backed implementation; tests may swap in {@link NoopAudioEngine}
 * via {@link #set}.
 */
public final class AudioEngine {
    private static volatile IAudioEngine instance = new JavaFxAudioEngine();

    private AudioEngine() {}

    public static IAudioEngine get() {
        return instance;
    }

    public static void set(IAudioEngine engine) {
        if (engine == null) {
            throw new IllegalArgumentException("engine must not be null");
        }
        instance = engine;
    }

    public static void reset() {
        var previous = instance;
        instance = new JavaFxAudioEngine();
        try {
            previous.dispose();
        } catch (Exception ignored) {
            // best-effort cleanup
        }
    }
}
