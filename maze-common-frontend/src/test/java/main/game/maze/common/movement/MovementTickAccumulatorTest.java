package main.game.maze.common.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Contract for {@link MovementTickAccumulator}, the shared catch-up timer that
 * keeps enemy movement at {@link GameplayTickRate#ENEMY_MOVEMENT_TICKS_PER_SECOND}
 * regardless of how long a frame / background-loop iteration actually takes.
 *
 * <p>The {@code heavyIterations…} test is the regression guard for the residual
 * JavaFX slowdown: a loop that runs exactly one movement tick per iteration falls
 * below the target rate once per-iteration AI work (heavier on hard difficulty)
 * stretches each iteration past the nominal interval; the accumulator must instead
 * run extra catch-up ticks so the long-run rate matches libGDX.</p>
 */
class MovementTickAccumulatorTest {

    private static final double TICKS_PER_SECOND = GameplayTickRate.ENEMY_MOVEMENT_TICKS_PER_SECOND;
    private static final int MAX_TICKS = GameplayTickRate.MAX_TICKS_PER_FRAME;

    @Test
    void nominalIntervalsProduceOneTickEachAndAverageTheTargetRate() {
        MovementTickAccumulator accumulator = new MovementTickAccumulator(TICKS_PER_SECOND, MAX_TICKS);
        double secondsPerTick = GameplayTickRate.SECONDS_PER_TICK; // 1/30

        int total = 0;
        for (int i = 0; i < (int) TICKS_PER_SECOND; i++) {
            total += accumulator.accumulate(secondsPerTick);
        }

        assertEquals((int) TICKS_PER_SECOND, total,
                "One nominal interval per iteration must yield exactly the target ticks over a second");
    }

    @Test
    void heavyIterationsStillAverageTheTargetRate_notOneTickPerIteration() {
        // Simulate hard-difficulty load: each loop iteration takes 33 ms of sleep
        // PLUS 20 ms of AI work = 53 ms of wall time. A naive one-tick-per-iteration
        // loop would manage only ~19 ticks per real second; the accumulator must
        // catch up to the full 30.
        MovementTickAccumulator accumulator = new MovementTickAccumulator(TICKS_PER_SECOND, MAX_TICKS);
        double iterationSeconds = 0.053d;

        int total = 0;
        double wallClock = 0d;
        while (wallClock < 1.0d) {
            total += accumulator.accumulate(iterationSeconds);
            wallClock += iterationSeconds;
        }

        // Over ~1.007 s of wall time we expect ~30 ticks (target rate), never the
        // ~19 a single-step loop would produce.
        assertTrue(total >= 29 && total <= 31,
                "Expected ~30 catch-up ticks for ~1 s of heavy iterations, got " + total);
    }

    @Test
    void longPauseIsCappedAndExcessTicksAreDropped() {
        MovementTickAccumulator accumulator = new MovementTickAccumulator(TICKS_PER_SECOND, MAX_TICKS);

        // 10 s elapsed would be 300 ticks; the cap must bound the burst.
        int burst = accumulator.accumulate(10d);
        assertEquals(MAX_TICKS, burst, "A long pause must be capped at MAX_TICKS_PER_FRAME");

        // Excess whole ticks are dropped (not carried), so a follow-up zero-time
        // call yields nothing.
        assertEquals(0, accumulator.accumulate(0d), "Dropped ticks must not be carried over");
    }

    @Test
    void fractionalRemainderIsCarriedToTheNextCall() {
        MovementTickAccumulator accumulator = new MovementTickAccumulator(TICKS_PER_SECOND, MAX_TICKS);
        double halfTick = GameplayTickRate.SECONDS_PER_TICK / 2d; // 0.5 ticks each

        assertEquals(0, accumulator.accumulate(halfTick), "Half a tick is not yet a whole tick");
        assertEquals(1, accumulator.accumulate(halfTick), "Two halves must accumulate into one tick");
    }

    @Test
    void nonPositiveElapsedReturnsZero() {
        MovementTickAccumulator accumulator = new MovementTickAccumulator(TICKS_PER_SECOND, MAX_TICKS);
        assertEquals(0, accumulator.accumulate(0d));
        assertEquals(0, accumulator.accumulate(-1d));
    }

    @Test
    void resetDiscardsCarriedRemainder() {
        MovementTickAccumulator accumulator = new MovementTickAccumulator(TICKS_PER_SECOND, MAX_TICKS);
        accumulator.accumulate(GameplayTickRate.SECONDS_PER_TICK * 0.9d); // 0.9 ticks carried
        accumulator.reset();
        assertEquals(0, accumulator.accumulate(GameplayTickRate.SECONDS_PER_TICK * 0.5d),
                "After reset the carried 0.9 must be gone, so 0.5 is not a whole tick");
    }

    @Test
    void invalidConstructionIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new MovementTickAccumulator(0d, MAX_TICKS));
        assertThrows(IllegalArgumentException.class, () -> new MovementTickAccumulator(-30d, MAX_TICKS));
        assertThrows(IllegalArgumentException.class, () -> new MovementTickAccumulator(TICKS_PER_SECOND, 0));
    }
}
