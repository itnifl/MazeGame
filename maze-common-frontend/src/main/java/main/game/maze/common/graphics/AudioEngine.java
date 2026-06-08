package main.game.maze.common.graphics;

/**
 * Global accessor for the active {@link IAudioEngine}. Defaults to the
 * {@link NoopAudioEngine} so the holder remains usable in unit tests and
 * headless tools. Production code is expected to call {@code install()} on
 * whichever backend module it uses (e.g. {@code JavaFxBackend} or
 * {@code GdxBackend}) during startup.
 */
public final class AudioEngine {
    private static volatile IAudioEngine instance = new NoopAudioEngine();

    private AudioEngine() {}

    public static IAudioEngine get() {
        return instance;
    }

    public static void set(IAudioEngine engine) {
        if (engine == null) {
            throw new IllegalArgumentException("engine must not be null");
        }
        var previous = instance;
        instance = engine;
        if (previous != null && previous != engine) {
            try {
                previous.dispose();
            } catch (Exception ignored) {
                // best-effort cleanup
            }
        }
    }

    /** Restore the safe no-op default and dispose the previous engine. */
    public static void reset() {
        set(new NoopAudioEngine());
    }
}
