package main.game.maze.javafx.menu;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.difficulties.DifficultiesFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link FxDifficultyPickerSupport#resolvePickResult}, the injectable
 * core routing logic that has no JavaFX dialog dependency.
 */
class FxDifficultyPickerSupportTest {

    @BeforeAll
    static void initEmf() {
        main.game.maze.difficulties.DifficultiesPackage.eINSTANCE.eClass();
    }

    private static Difficulty fakeDifficulty() {
        return DifficultiesFactory.eINSTANCE.createEasyDifficulty();
    }

    // No-restart path: onDifficultySet + onResume called once each; onHardRestart NOT called.
    @Test
    void resolvePickResult_noRestart_callsSetAndResume() {
        AtomicInteger resumeCalls = new AtomicInteger();
        AtomicInteger hardRestartCalls = new AtomicInteger();
        AtomicReference<Difficulty> setCalled = new AtomicReference<>();
        Difficulty chosen = fakeDifficulty();

        FxDifficultyPickerSupport.resolvePickResult(
                chosen,
                () -> false,
                resumeCalls::incrementAndGet,
                setCalled::set,
                hardRestartCalls::incrementAndGet);

        assertEquals(1, resumeCalls.get(), "onResume must be called exactly once when not restarting");
        assertEquals(0, hardRestartCalls.get(), "onHardRestart must NOT be called when cancel is chosen");
        assertSame(chosen, setCalled.get(), "onDifficultySet must receive the chosen difficulty");
    }

    // Hard-restart path: onHardRestart called once; onResume and onDifficultySet NOT called.
    @Test
    void resolvePickResult_confirmRestart_callsHardRestartOnly() {
        AtomicInteger resumeCalls = new AtomicInteger();
        AtomicInteger hardRestartCalls = new AtomicInteger();
        AtomicReference<Difficulty> setCalled = new AtomicReference<>();
        Difficulty chosen = fakeDifficulty();

        FxDifficultyPickerSupport.resolvePickResult(
                chosen,
                () -> true,
                resumeCalls::incrementAndGet,
                setCalled::set,
                hardRestartCalls::incrementAndGet);

        assertEquals(1, hardRestartCalls.get(), "onHardRestart must be called exactly once on OK");
        assertEquals(0, resumeCalls.get(), "onResume must NOT be called when restarting");
        assertNull(setCalled.get(), "onDifficultySet must NOT be called when restarting");
    }

    // Guard: onResume is never called more than once on the no-restart path.
    @Test
    void resolvePickResult_noRestartPath_resumeCalledExactlyOnce() {
        AtomicInteger resumeCalls = new AtomicInteger();

        FxDifficultyPickerSupport.resolvePickResult(
                fakeDifficulty(), () -> false, resumeCalls::incrementAndGet, d -> {}, () -> {});

        assertEquals(1, resumeCalls.get());
    }

    // Guard: onHardRestart is never called more than once on the restart path.
    @Test
    void resolvePickResult_restartPath_hardRestartCalledExactlyOnce() {
        AtomicInteger hardRestartCalls = new AtomicInteger();

        FxDifficultyPickerSupport.resolvePickResult(
                fakeDifficulty(), () -> true, () -> {}, d -> {}, hardRestartCalls::incrementAndGet);

        assertEquals(1, hardRestartCalls.get());
    }
}
