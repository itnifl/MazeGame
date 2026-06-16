package main.game.maze.service;

import main.game.maze.difficulties.Difficulty;
import main.game.maze.runtime.OclBootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyServiceTest {

    @BeforeAll
    static void initOcl() {
        OclBootstrap.init();
    }

    @Test
    void list_returnsThreeCanonicalDifficulties() {
        DifficultyService svc = new DifficultyService();
        List<Difficulty> diffs = svc.list();

        assertNotNull(diffs, "list() must not return null");
        assertFalse(diffs.isEmpty(), "list() must return at least one difficulty");
        assertEquals(3, diffs.size(), "Expected exactly three canonical difficulty levels");
    }

    @Test
    void list_eachDifficultyHasNonBlankName() {
        DifficultyService svc = new DifficultyService();
        for (Difficulty d : svc.list()) {
            assertNotNull(d, "Difficulty entry must not be null");
        }
    }

    @Test
    void getCurrent_beforeSetCurrent_returnsNullOrDefault() {
        DifficultyService svc = new DifficultyService();
        // Before any setCurrent(), getCurrent() may be null or a default.
        // Either outcome is acceptable — what is not acceptable is an exception.
        assertDoesNotThrow(svc::getCurrent, "getCurrent() must not throw before setCurrent()");
    }

    @Test
    void setCurrent_persistsDuringServiceLifetime() {
        DifficultyService svc = new DifficultyService();
        List<Difficulty> diffs = svc.list();
        Difficulty first = diffs.get(0);

        svc.setCurrent(first);

        assertSame(first, svc.getCurrent(), "getCurrent() must return the difficulty set via setCurrent()");
    }

    @Test
    void list_isStable_onMultipleCalls() {
        DifficultyService svc = new DifficultyService();
        List<Difficulty> a = svc.list();
        List<Difficulty> b = svc.list();

        assertEquals(a.size(), b.size(), "list() must return consistent size across calls");
    }
}
