package main.game.maze.common.graphics;

import java.util.function.Consumer;

/** Safe default animation engine for non-UI and tests. */
public final class NoopAnimationEngine implements IAnimationEngine {

    private static final IAnimationHandle STOPPED = new IAnimationHandle() {
        @Override public void stop() {}
        @Override public boolean isRunning() { return false; }
    };

    @Override
    public IAnimationHandle scheduleOnce(double delaySeconds, Runnable action) {
        if (action != null) {
            action.run();
        }
        return STOPPED;
    }

    @Override
    public IAnimationHandle animateValues(
        double[] keyTimesSeconds,
        double[] values,
        Consumer<Double> onValue,
        Runnable onFinished) {
        if (values != null && values.length > 0 && onValue != null) {
            onValue.accept(values[values.length - 1]);
        }
        if (onFinished != null) {
            onFinished.run();
        }
        return STOPPED;
    }

    @Override
    public void dispose() {
        // nothing to release
    }
}