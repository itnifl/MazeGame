package main.game.maze;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.difficulties.DifficultiesFactory;
import main.game.maze.mazeworld.GameMazeWorld;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the path-hint energy budget introduced per difficulty:
 *   Hard = 15s, Normal = 25s, Easy = 45s.
 */
class GameControllerPathHintBudgetTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        latch.await(2, TimeUnit.SECONDS);
    }

    private static void set(Object target, String field, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(field);
        f.setAccessible(true);
        f.set(target, value);
    }

    private static Object get(Object target, String field) throws Exception {
        Field f = target.getClass().getDeclaredField(field);
        f.setAccessible(true);
        return f.get(target);
    }

    private static Object invoke(Object target, String method) throws Exception {
        Method m = target.getClass().getDeclaredMethod(method);
        m.setAccessible(true);
        return m.invoke(target);
    }

    private static long getBudgetNanos(GameController gc) throws Exception {
        Method m = gc.getClass().getDeclaredMethod("pathHintBudgetNanos");
        m.setAccessible(true);
        return (long) m.invoke(gc);
    }

    // -----------------------------------------------------------------------
    // Budget constants
    // -----------------------------------------------------------------------

    @Test
    void budgetIsHardForHardDifficulty() throws Exception {
        GameController gc = new GameController();
        set(gc, "startDifficulty", DifficultiesFactory.eINSTANCE.createHardDifficulty());
        long expectedNanos = 15L * 1_000_000_000L;
        assertEquals(expectedNanos, getBudgetNanos(gc), "Hard difficulty budget must be 15s");
    }

    @Test
    void budgetIsNormalForNormalDifficulty() throws Exception {
        GameController gc = new GameController();
        set(gc, "startDifficulty", DifficultiesFactory.eINSTANCE.createNormalDifficulty());
        long expectedNanos = 25L * 1_000_000_000L;
        assertEquals(expectedNanos, getBudgetNanos(gc), "Normal difficulty budget must be 25s");
    }

    @Test
    void budgetIsEasyForEasyDifficulty() throws Exception {
        GameController gc = new GameController();
        set(gc, "startDifficulty", DifficultiesFactory.eINSTANCE.createEasyDifficulty());
        long expectedNanos = 45L * 1_000_000_000L;
        assertEquals(expectedNanos, getBudgetNanos(gc), "Easy difficulty budget must be 45s");
    }

    @Test
    void budgetFallsBackToNormalWhenNoDifficultySet() throws Exception {
        GameController gc = new GameController();
        // startDifficulty is null by default
        long expectedNanos = 25L * 1_000_000_000L;
        assertEquals(expectedNanos, getBudgetNanos(gc), "Null difficulty should use Normal budget");
    }

    // -----------------------------------------------------------------------
    // Budget exhaustion blocks showing path
    // -----------------------------------------------------------------------

    @Test
    void showNavigationPathBlockedWhenBudgetExhausted() throws Exception {
        GameController gc = new GameController();
        set(gc, "startDifficulty", DifficultiesFactory.eINSTANCE.createNormalDifficulty());

        // Use up the entire budget.
        long fullBudget = 25L * 1_000_000_000L;
        set(gc, "pathHintTotalUsedNanos", fullBudget);
        set(gc, "pathCanvas", new Canvas(800, 600));
        set(gc, "heart", new ImageView());
        set(gc, "playerCharacter", new PlayerCharacter(null, 10, 10, null));

        invoke(gc, "showNavigationPath");

        assertFalse((boolean) get(gc, "isRouteHintVisible"),
                "Path should not be shown when budget is exhausted");
        assertFalse((boolean) get(gc, "pathHintKeyDown"),
                "Key-down flag must not be set when budget is exhausted");
    }

    @Test
    void showNavigationPathAllowedWhenBudgetHasRemaining() throws Exception {
        GameController gc = new GameController();
        set(gc, "startDifficulty", DifficultiesFactory.eINSTANCE.createNormalDifficulty());

        // Only half the budget used up.
        long halfBudget = 12L * 1_000_000_000L;
        set(gc, "pathHintTotalUsedNanos", halfBudget);

        GameMazeWorld maze = new GameMazeWorld();
        Field nav = GameMazeWorld.class.getDeclaredField("navigationGraph");
        nav.setAccessible(true);
        nav.set(maze, null); // null nav graph makes the drawing abort, but keyDown is still set

        set(gc, "maze", maze);
        set(gc, "pathCanvas", new Canvas(800, 600));
        set(gc, "heart", new ImageView());
        set(gc, "playerCharacter", new PlayerCharacter(null, 10, 10, null));

        invoke(gc, "showNavigationPath");

        // pathHintKeyDown is set to true when budget has remaining (even if the path
        // drawing aborts because the nav graph is null - the timer still runs).
        assertTrue((boolean) get(gc, "pathHintKeyDown"),
                "Key-down flag must be set while budget has remaining time");
    }

    // -----------------------------------------------------------------------
    // clearNavigationPath accumulates used time
    // -----------------------------------------------------------------------

    @Test
    void clearNavigationPathAccumulatesHeldTime() throws Exception {
        GameController gc = new GameController();
        set(gc, "startDifficulty", DifficultiesFactory.eINSTANCE.createNormalDifficulty());
        set(gc, "pathCanvas", new Canvas(800, 600));

        long startNanos = System.nanoTime() - 2_000_000_000L; // simulate 2s held
        set(gc, "pathHintKeyDown", true);
        set(gc, "pathHintPressStartNanos", startNanos);
        set(gc, "pathHintTotalUsedNanos", 0L);

        invoke(gc, "clearNavigationPath");

        long used = (long) get(gc, "pathHintTotalUsedNanos");
        assertTrue(used >= 2_000_000_000L, "At least 2s should be accumulated after clearing a 2s hold");
        assertFalse((boolean) get(gc, "pathHintKeyDown"), "Key-down flag must be cleared");
    }

    @Test
    void clearNavigationPathDoesNotDoubleCountWhenCalledWithKeyDownFalse() throws Exception {
        GameController gc = new GameController();
        set(gc, "pathCanvas", new Canvas(800, 600));
        set(gc, "pathHintKeyDown", false);
        set(gc, "pathHintTotalUsedNanos", 5_000_000_000L);

        invoke(gc, "clearNavigationPath");

        long used = (long) get(gc, "pathHintTotalUsedNanos");
        assertEquals(5_000_000_000L, used, "No additional time should be accumulated when key is not flagged down");
    }
}
