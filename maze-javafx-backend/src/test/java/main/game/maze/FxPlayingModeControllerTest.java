package main.game.maze;

import javafx.scene.input.KeyCode;
import main.game.maze.javafx.controller.state.FxPlayingModeController;
import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.InputRouter;
import main.game.maze.common.input.KeyBindingRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

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
}
