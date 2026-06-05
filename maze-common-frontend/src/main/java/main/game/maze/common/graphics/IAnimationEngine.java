package main.game.maze.common.graphics;

import java.util.function.Consumer;

/**
 * Backend-neutral animation facade used by gameplay code that needs timed
 * effects without importing a renderer-specific animation type.
 */
public interface IAnimationEngine {

    /**
     * Schedules a single callback after the given delay (in seconds).
     */
    IAnimationHandle scheduleOnce(double delaySeconds, Runnable action);

    /**
     * Animates a scalar value through keyframes.
     */
    IAnimationHandle animateValues(
        double[] keyTimesSeconds,
        double[] values,
        Consumer<Double> onValue,
        Runnable onFinished);

    /** Releases backend resources held by this engine. */
    void dispose();
}

