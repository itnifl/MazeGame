package main.game.maze.common.graphics;

/**
 * Process-wide animation facade accessor mirroring UiScheduler/AudioEngine.
 */
public final class AnimationEngine {
    private static volatile IAnimationEngine instance = new NoopAnimationEngine();

    private AnimationEngine() {}

    public static IAnimationEngine get() {
        return instance;
    }

    public static void set(IAnimationEngine engine) {
        if (engine == null) {
            throw new IllegalArgumentException("AnimationEngine cannot be null");
        }
        IAnimationEngine previous = instance;
        instance = engine;
        if (previous != engine) {
            previous.dispose();
        }
    }

    public static void reset() {
        set(new NoopAnimationEngine());
    }
}