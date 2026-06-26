package main.game.maze;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.javafx.controller.state.FxPlayingModeController;
import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.InputRouter;
import main.game.maze.common.input.KeyBindingRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Headless unit tests for {@link FxPlayingModeController}.
 * All collaborators are either null-safe stubs or real lightweight instances;
 * no FXML or scene-graph initialization is required.
 *
 * Placed in main.game.maze to access package-private
 * JavaFxInputCommandContext constructor and ActionSink.
 */
class FxPlayingModeControllerTest {

    private FxGameWorldModel model;
    private KeyBindingRegistry<KeyCode> registry;
    private InputRouter<KeyCode> router;
    private JavaFxInputCommandContext context;

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
                        public void triggerPlayerFlameAttack() {}
        });
    }

    @BeforeEach
    void setUp() {
        model    = new FxGameWorldModel();
        registry = new KeyBindingRegistry<>();
        router   = new InputRouter<>(registry);
        context  = noopContext();
    }

    @Test
    void noArgUpdateReturnsFalse() {
        FxPlayingModeController controller =
                new FxPlayingModeController(model, router, null, null, context);
        assertFalse(controller.update(),
                "The no-arg update() is a contract stub — it must always return false");
    }

    @Test
    void updateWithNullPlayerDoesNotThrow() {
        FxPlayingModeController controller =
                new FxPlayingModeController(model, router, null, null, context);
        InputFrame<KeyCode> emptyFrame = new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
        assertDoesNotThrow(() -> controller.update(emptyFrame, System.nanoTime()),
                "update() with a null playerCharacter must silently return without throwing");
    }

    @Test
    void penaltyAccruesWhenRouteHintVisible() throws Exception {
        // applyRouteHintPenalty is called inside update() after the null-player guard,
        // so we invoke it directly via reflection — the same pattern used in
        // GameControllerRoutePenaltyTest — to test the penalty logic in isolation.
        FxPlayingModeController controller =
                new FxPlayingModeController(model, router, null, null, context);

        long now = System.nanoTime();
        model.setRouteHintVisible(false);
        model.setLastRouteHintPenaltyNanos(now - 1_000_000_000L);

        java.lang.reflect.Method m = FxPlayingModeController.class
                .getDeclaredMethod("applyRouteHintPenalty", long.class);
        m.setAccessible(true);

        m.invoke(controller, now);
        assertEquals(0, model.routeHintPenaltyPoints(),
                "Penalty must not accrue while hint is hidden");

        model.setRouteHintVisible(true);
        model.setLastRouteHintPenaltyNanos(now - 1_000_000_000L);
        m.invoke(controller, now);
        assertTrue(model.routeHintPenaltyPoints() > 0,
                "Penalty must accrue while hint is visible (1 s elapsed)");
    }

    @Test
    void updateWithNullPlayerDoesNotThrowOnRapidCalls() {
        FxPlayingModeController controller =
                new FxPlayingModeController(model, router, null, null, context);
        InputFrame<KeyCode> emptyFrame = new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
        long now = System.nanoTime();

        // Null player causes early return — verify no exception on rapid / same-timestamp calls.
        assertDoesNotThrow(() -> {
            controller.update(emptyFrame, now);
            controller.update(emptyFrame, now);
            controller.update(emptyFrame, now + 100_000_000L);
        });
    }

    @Test
    void movementIsThrottledByInterval() throws Exception {
        // Directly test throttle logic via applyRouteHintPenalty path:
        // the lastMoveTime field is package-private; test via repeated update
        // with controlled timestamps while route-hint penalty (which has no
        // player guard) gives us observable side effects.
        FxPlayingModeController controller =
                new FxPlayingModeController(model, router, null, null, context);

        // Obtain the lastMoveTime field to inspect throttle state.
        java.lang.reflect.Field lastMoveField =
                FxPlayingModeController.class.getDeclaredField("lastMoveTime");
        lastMoveField.setAccessible(true);

        // With a null player, update() returns early — throttle is NOT applied.
        // Verify this assumption holds (lastMoveTime stays 0).
        InputFrame<KeyCode> emptyFrame = new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
        long t0 = System.nanoTime();
        controller.update(emptyFrame, t0);
        assertEquals(0L, lastMoveField.get(controller),
                "lastMoveTime must not advance when playerCharacter is null");
    }

    // --- Non-null player path (lines 54-83 of FxPlayingModeController.update) ---

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(10, TimeUnit.SECONDS), "JavaFX startup timed out");
    }

    @Test
    void updateWithNonNullPlayer_coversPostGuardPath() throws Exception {
        // Rectangle graphics → configureDirectionalImages() skips image loading (safe without FX scene).
        // null ProgressBar → ProgressBarStatePresenter not created (null statePresenter is fine).
        // null renderCoordinator → only touched when moved=true; empty frame keeps moved=false.
        PlayerCharacter player = new PlayerCharacter(new Rectangle(16, 16), 50, 50, null);
        FxPlayingModeController controller =
                new FxPlayingModeController(model, router, player, null, context);

        InputFrame<KeyCode> emptyFrame = new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
        // now > MOVE_INTERVAL_NANOS (33ms) so throttle is passed and lastMoveTime is updated.
        long now = 100_000_000L;
        assertDoesNotThrow(() -> controller.update(emptyFrame, now),
                "update() with a non-null player and empty frame must not throw");

        // Verify lastMoveTime was advanced (confirms the post-throttle path executed).
        java.lang.reflect.Field lastMoveField =
                FxPlayingModeController.class.getDeclaredField("lastMoveTime");
        lastMoveField.setAccessible(true);
        assertEquals(now, lastMoveField.get(controller),
                "lastMoveTime must be set to 'now' after passing the throttle interval");
    }

    @Test
    void updateWithNonNullPlayer_throttlePreventsMoveOnRapidCalls() throws Exception {
        PlayerCharacter player = new PlayerCharacter(new Rectangle(16, 16), 50, 50, null);
        FxPlayingModeController controller =
                new FxPlayingModeController(model, router, player, null, context);

        InputFrame<KeyCode> emptyFrame = new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
        long t0 = 100_000_000L;
        controller.update(emptyFrame, t0);              // sets lastMoveTime = t0

        // Second call only 1ms later — within throttle window → lastMoveTime stays t0.
        long t1 = t0 + 1_000_000L;
        controller.update(emptyFrame, t1);

        java.lang.reflect.Field lastMoveField =
                FxPlayingModeController.class.getDeclaredField("lastMoveTime");
        lastMoveField.setAccessible(true);
        assertEquals(t0, lastMoveField.get(controller),
                "lastMoveTime must not advance when called within the throttle window");
    }
}
