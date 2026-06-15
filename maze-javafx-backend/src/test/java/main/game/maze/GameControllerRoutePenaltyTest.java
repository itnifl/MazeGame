package main.game.maze;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.javafx.controller.state.FxPlayingModeController;
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
        Object[] ownerAndField = resolveField(target, field);
        Field f = (Field) ownerAndField[1];
        f.setAccessible(true);
        f.set(ownerAndField[0], value);
    }

    private static Object get(Object target, String field) throws Exception {
        Object[] ownerAndField = resolveField(target, field);
        Field f = (Field) ownerAndField[1];
        f.setAccessible(true);
        return f.get(ownerAndField[0]);
    }

    /**
     * Resolves a field by name, first on {@code target}, then (for state that was
     * extracted into {@link FxGameWorldModel}) on the controller's {@code model}
     * sub-object. Returns the owning instance and the {@link Field} to access.
     */
    private static Object[] resolveField(Object target, String field) throws Exception {
        try {
            return new Object[]{target, target.getClass().getDeclaredField(field)};
        } catch (NoSuchFieldException notOnController) {
            Field modelField = target.getClass().getDeclaredField("model");
            modelField.setAccessible(true);
            Object model = modelField.get(target);
            return new Object[]{model, model.getClass().getDeclaredField(field)};
        }
    }

    /**
     * Invokes {@code method(sig)} on {@code target}; if not found there, falls back to
     * the {@code pathHintCoordinator} sub-object (methods were moved there during refactoring).
     */
    private static Object invoke(Object target, String method, Class<?>[] sig, Object... args) throws Exception {
        try {
            Method m = target.getClass().getDeclaredMethod(method, sig);
            m.setAccessible(true);
            return m.invoke(target, args);
        } catch (NoSuchMethodException original) {
            try {
                Field cf = target.getClass().getDeclaredField("pathHintCoordinator");
                cf.setAccessible(true);
                Object coordinator = cf.get(target);
                Method m = coordinator.getClass().getDeclaredMethod(method, sig);
                m.setAccessible(true);
                return m.invoke(coordinator, args);
            } catch (NoSuchFieldException | NoSuchMethodException e) {
                throw original;
            }
        }
    }

    /** Extracts the {@link FxGameWorldModel} from a {@link GameController} via reflection. */
    private static FxGameWorldModel extractModel(GameController gc) throws Exception {
        Field f = GameController.class.getDeclaredField("model");
        f.setAccessible(true);
        return (FxGameWorldModel) f.get(gc);
    }

    /** No-op sink used to satisfy {@link JavaFxInputCommandContext} in unit tests. */
    private static JavaFxInputCommandContext noopContext() {
        return new JavaFxInputCommandContext(new JavaFxInputCommandContext.ActionSink() {
            public void showHighScore() {}
            public void openDifficultyPickerAndMaybeRestart() {}
            public void showNavigationPath() {}
            public void showSpanningTree() {}
            public void clearNavigationPath() {}
            public void clearSpanningTree() {}
            public void updateDebugLabels() {}
            public void updateScoreHud() {}
            public void openTerminalPrompt() {}
        });
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
        FxGameWorldModel model = extractModel(gc);
        FxPlayingModeController playing = new FxPlayingModeController(model, null, null, null, noopContext());

        long now = System.nanoTime();

        // Hint hidden — penalty must NOT accrue.
        set(gc, "isRouteHintVisible", false);
        set(gc, "lastRouteHintPenaltyNanos", now - 1_000_000_000L);
        invoke(playing, "applyRouteHintPenalty", new Class<?>[]{long.class}, now);
        assertEquals(0, gc.getDynamicScorePenalty(), "Penalty should not accrue while hint is hidden");

        // Hint visible — penalty MUST accrue (1 s elapsed @ 0.05 pts/ms = 50 pts).
        set(gc, "isRouteHintVisible", true);
        set(gc, "lastRouteHintPenaltyNanos", now - 1_000_000_000L);
        invoke(playing, "applyRouteHintPenalty", new Class<?>[]{long.class}, now);
        assertTrue(gc.getDynamicScorePenalty() > 0, "Penalty should accrue while hint is visible");
    }
}
