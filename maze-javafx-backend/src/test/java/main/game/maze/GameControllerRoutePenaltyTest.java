package main.game.maze;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.mazeworld.GameMazeWorld;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerRoutePenaltyTest {

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

    private static Object invoke(Object target, String method, Class<?>[] sig, Object... args) throws Exception {
        Method m = target.getClass().getDeclaredMethod(method, sig);
        m.setAccessible(true);
        return m.invoke(target, args);
    }

    @Test
    void showNavigationPathWithMissingNavigationGraphDoesNotEnablePenalty() throws Exception {
        GameController gc = new GameController();

        GameMazeWorld maze = new GameMazeWorld();
        Field nav = GameMazeWorld.class.getDeclaredField("navigationGraph");
        nav.setAccessible(true);
        nav.set(maze, null);

        set(gc, "maze", maze);
        set(gc, "pathCanvas", new Canvas(800, 600));
        set(gc, "heart", new ImageView());
        set(gc, "playerCharacter", new PlayerCharacter(null, 10, 10, null));

        invoke(gc, "showNavigationPath", new Class<?>[]{});

        assertEquals(false, get(gc, "isRouteHintVisible"), "Hint penalty must stay disabled when no path can be shown");
    }

    @Test
    void routePenaltyAccruesOnlyWhenHintIsVisible() throws Exception {
        GameController gc = new GameController();

        long now = System.nanoTime();

        set(gc, "isRouteHintVisible", false);
        set(gc, "lastRouteHintPenaltyNanos", now - 1_000_000_000L);
        invoke(gc, "applyRouteHintPenalty", new Class<?>[]{long.class}, now);
        assertEquals(0, gc.getDynamicScorePenalty(), "Penalty should not accrue while hint is hidden");

        set(gc, "isRouteHintVisible", true);
        set(gc, "lastRouteHintPenaltyNanos", now - 1_000_000_000L);
        invoke(gc, "applyRouteHintPenalty", new Class<?>[]{long.class}, now);

        assertTrue(gc.getDynamicScorePenalty() > 0, "Penalty should accrue while hint is visible");
    }
}


