package main.game.maze.javafx;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;
import main.game.maze.common.graphics.IAnimationEngine;
import main.game.maze.common.graphics.IAnimationHandle;
import main.game.maze.common.graphics.UiScheduler;

/** JavaFX-backed implementation of {@link IAnimationEngine}. */
public final class JavaFxAnimationEngine implements IAnimationEngine {
    private final Set<Timeline> active = ConcurrentHashMap.newKeySet();

    @Override
    public IAnimationHandle scheduleOnce(double delaySeconds, Runnable action) {
        double safeDelay = Math.max(0.0, delaySeconds);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(safeDelay), e -> {
            if (action != null) {
                action.run();
            }
        }));
        track(timeline);
        return handleOf(timeline);
    }

    @Override
    public IAnimationHandle animateValues(
        double[] keyTimesSeconds,
        double[] values,
        Consumer<Double> onValue,
        Runnable onFinished) {
        if (keyTimesSeconds == null || values == null || onValue == null) {
            throw new IllegalArgumentException("keyTimesSeconds, values and onValue are required");
        }
        if (keyTimesSeconds.length == 0 || keyTimesSeconds.length != values.length) {
            throw new IllegalArgumentException("keyTimesSeconds and values must be non-empty and equal length");
        }

        SimpleDoubleProperty animated = new SimpleDoubleProperty(values[0]);
        animated.addListener((obs, oldVal, newVal) -> onValue.accept(newVal.doubleValue()));

        Timeline timeline = new Timeline();
        for (int i = 0; i < keyTimesSeconds.length; i++) {
            double t = Math.max(0.0, keyTimesSeconds[i]);
            timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(t), new KeyValue(animated, values[i])));
        }
        timeline.setOnFinished(e -> {
            if (onFinished != null) {
                onFinished.run();
            }
        });

        track(timeline);
        return handleOf(timeline);
    }

    @Override
    public void dispose() {
        for (Timeline timeline : active) {
            stopInternal(timeline);
        }
        active.clear();
    }

    private void track(Timeline timeline) {
        active.add(timeline);
        var previous = timeline.getOnFinished();
        timeline.setOnFinished(e -> {
            if (previous != null) {
                previous.handle(e);
            }
            active.remove(timeline);
        });
        UiScheduler.get().runOnUiThread(timeline::play);
    }

    private IAnimationHandle handleOf(Timeline timeline) {
        return new IAnimationHandle() {
            @Override
            public void stop() {
                stopInternal(timeline);
            }

            @Override
            public boolean isRunning() {
                return timeline.getStatus() == Timeline.Status.RUNNING;
            }
        };
    }

    private void stopInternal(Timeline timeline) {
        UiScheduler.get().runOnUiThread(() -> {
            timeline.stop();
            active.remove(timeline);
        });
    }
}

