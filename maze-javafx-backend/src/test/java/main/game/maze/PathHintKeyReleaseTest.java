package main.game.maze;

import javafx.scene.input.KeyCode;
import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.InputRouter;
import main.game.maze.common.input.KeyBindingRegistry;
import main.game.maze.javafx.controller.state.FxPlayingModeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Regression tests for the P-key path-hint release bug.
 *
 * <p>Root cause: the APPLY_PATH_HINT action is bound as HELD, so the router only fires
 * {@link ApplyPathHintCommand} when P <em>is</em> in {@code pressedKeys}.  Nothing called
 * {@code context.applyPathHintHeld(false)} when P was released, so the hint overlay
 * stayed visible indefinitely.</p>
 *
 * <p>Fix: {@link FxPlayingModeController#update} checks whether P was released while
 * the model still records the hint as active, and explicitly calls
 * {@code context.applyPathHintHeld(false)}.</p>
 */
class PathHintKeyReleaseTest {

    private FxGameWorldModel model;
    private List<String> sinkCalls;
    private JavaFxInputCommandContext context;
    private InputRouter<KeyCode> router;

    @BeforeEach
    void setUp() {
        model = new FxGameWorldModel();
        sinkCalls = new ArrayList<>();

        context = new JavaFxInputCommandContext(new JavaFxInputCommandContext.ActionSink() {
            @Override public void showHighScore() {}
            @Override public void openDifficultyPickerAndMaybeRestart() {}
            @Override public void showSpanningTree() {}
            @Override public void clearSpanningTree() {}
            @Override public void updateDebugLabels() {}
            @Override public void updateScoreHud() {}
            @Override public void openTerminalPrompt() {}

            @Override public void showNavigationPath()  { sinkCalls.add("show");  }
            @Override public void clearNavigationPath() { sinkCalls.add("clear"); }
        });

        KeyBindingRegistry<KeyCode> registry = new KeyBindingRegistry<>();
        JavaFxInputBindingsSupport.configureDefaultBindings(registry);
        router = new InputRouter<>(registry);
    }

    /** P held → router fires show; P released after hint active → controller fires clear. */
    @Test
    void pathHintClearsWhenPIsReleasedAfterBeingHeld() {
        FxPlayingModeController controller =
                new FxPlayingModeController(model, router, null, null, context);

        // Simulate the model state after P was held (beginPathHint sets pathHintKeyDown = true).
        model.beginPathHint();
        assertTrue(model.pathHintKeyDown(), "Precondition: hint is recorded as active in model");

        // Next frame — P is no longer in pressedKeys (key was released).
        InputFrame<KeyCode> released = new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
        controller.update(released, System.nanoTime());

        assertTrue(sinkCalls.contains("clear"),
                "clearNavigationPath must be called when P is released while hint is active");
        assertFalse(sinkCalls.contains("show"),
                "showNavigationPath must NOT be called when P is not held");
    }

    /** P never pressed → release check must be a no-op (avoid spurious clear calls). */
    @Test
    void pathHintNotClearedWhenHintWasNeverActive() {
        FxPlayingModeController controller =
                new FxPlayingModeController(model, router, null, null, context);

        // model.pathHintKeyDown() == false by default — hint was never active.
        assertFalse(model.pathHintKeyDown(), "Precondition: hint is NOT active");

        InputFrame<KeyCode> empty = new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
        controller.update(empty, System.nanoTime());

        assertFalse(sinkCalls.contains("clear"),
                "clearNavigationPath must NOT be called when the hint was never active");
    }

    /**
     * Full lifecycle: hint active (seeded directly into the model, mirroring what
     * GameController.showNavigationPath() does) → P released → clear fires and model
     * resets.
     *
     * <p>The "show" half (router fires ApplyPathHintCommand on P held) is already
     * covered by {@link JavaFxRouterIntegrationTest#pathHintTriggersOnHold()}.
     * This test focuses on the release transition.</p>
     */
    @Test
    void pathHintShowThenClearFullLifecycle() {
        FxGameWorldModel liveModel = new FxGameWorldModel();
        List<String> calls = new ArrayList<>();

        JavaFxInputCommandContext liveContext = new JavaFxInputCommandContext(
                new JavaFxInputCommandContext.ActionSink() {
            @Override public void showHighScore() {}
            @Override public void openDifficultyPickerAndMaybeRestart() {}
            @Override public void showSpanningTree() {}
            @Override public void clearSpanningTree() {}
            @Override public void updateDebugLabels() {}
            @Override public void updateScoreHud() {}
            @Override public void openTerminalPrompt() {}
            @Override public void showNavigationPath()  { calls.add("show"); }
            @Override public void clearNavigationPath() {
                calls.add("clear");
                liveModel.endPathHint(Long.MAX_VALUE);  // mirrors GameController.clearNavigationPath
            }
        });

        FxPlayingModeController controller =
                new FxPlayingModeController(liveModel, router, null, null, liveContext);

        // Seed model as if P was held in the previous frame and showNavigationPath ran.
        liveModel.beginPathHint();
        calls.add("show");
        assertTrue(liveModel.pathHintKeyDown(), "precondition: hint is active in model");

        // Frame where P is released.
        InputFrame<KeyCode> released = new InputFrame<>(Set.of(), Set.of(), 0, 0, false);
        controller.update(released, System.nanoTime());

        assertEquals(List.of("show", "clear"), calls,
                "full lifecycle must produce show then clear");
        assertFalse(liveModel.pathHintKeyDown(),
                "model must record hint as inactive after clear");
    }
}
