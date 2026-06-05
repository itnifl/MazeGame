package main.game.maze.common.graphics;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AnimationEngineTest {

    @AfterEach
    void restoreDefault() {
        AnimationEngine.reset();
    }

    @Test
    @DisplayName("set(null) is rejected")
    void setNullRejected() {
        assertThrows(IllegalArgumentException.class, () -> AnimationEngine.set(null));
    }

    @Test
    @DisplayName("default engine executes scheduleOnce immediately")
    void defaultNoopRunsImmediately() {
        AtomicBoolean invoked = new AtomicBoolean(false);
        AnimationEngine.get().scheduleOnce(10.0, () -> invoked.set(true));
        assertTrue(invoked.get());
    }

    @Test
    @DisplayName("reset restores noop default")
    void resetRestoresNoop() {
        AnimationEngine.set(new IAnimationEngine() {
            @Override
            public IAnimationHandle scheduleOnce(double delaySeconds, Runnable action) {
                return new IAnimationHandle() {
                    @Override public void stop() {}
                    @Override public boolean isRunning() { return true; }
                };
            }

            @Override
            public IAnimationHandle animateValues(double[] keyTimesSeconds, double[] values,
                                                  java.util.function.Consumer<Double> onValue,
                                                  Runnable onFinished) {
                return new IAnimationHandle() {
                    @Override public void stop() {}
                    @Override public boolean isRunning() { return true; }
                };
            }

            @Override
            public void dispose() {}
        });

        AnimationEngine.reset();

        assertTrue(AnimationEngine.get() instanceof NoopAnimationEngine);
        assertDoesNotThrow(() -> {
            IAnimationHandle handle = AnimationEngine.get().scheduleOnce(0.1, () -> {});
            assertFalse(handle.isRunning());
        });
    }

    @Test
    @DisplayName("set(other) ignores dispose failures from the previous engine")
    void setIgnoresDisposeFailures() {
        IAnimationEngine replacement = new NoopAnimationEngine();
        AnimationEngine.set(new IAnimationEngine() {
            @Override
            public IAnimationHandle scheduleOnce(double delaySeconds, Runnable action) {
                return new IAnimationHandle() {
                    @Override public void stop() {}
                    @Override public boolean isRunning() { return false; }
                };
            }

            @Override
            public IAnimationHandle animateValues(double[] keyTimesSeconds, double[] values,
                                                  java.util.function.Consumer<Double> onValue,
                                                  Runnable onFinished) {
                return new IAnimationHandle() {
                    @Override public void stop() {}
                    @Override public boolean isRunning() { return false; }
                };
            }

            @Override
            public void dispose() {
                throw new IllegalStateException("boom");
            }
        });

        assertDoesNotThrow(() -> AnimationEngine.set(replacement));
        assertSame(replacement, AnimationEngine.get());
    }
}


