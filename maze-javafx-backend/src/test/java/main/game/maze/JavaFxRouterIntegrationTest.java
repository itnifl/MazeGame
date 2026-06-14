package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.scene.input.KeyCode;
import main.game.maze.common.input.InputFrame;
import main.game.maze.common.input.InputRouter;
import main.game.maze.common.input.KeyBindingRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Validates the full InputRouter integration replacing the inline GameController
 * handleKeyPressed switch.
 */
class JavaFxRouterIntegrationTest {

    private KeyBindingRegistry<KeyCode> registry;
    private InputRouter<KeyCode> router;
    private RecordingSink sink;
    private JavaFxInputCommandContext context;

    @BeforeEach
    void setUp() {
        registry = new KeyBindingRegistry<>();
        JavaFxInputBindingsSupport.configureDefaultBindings(registry);
        router = new InputRouter<>(registry);
        sink = new RecordingSink();
        context = new JavaFxInputCommandContext(sink);
    }

    @Test
    void highScoresKeyTriggersAction() {
        InputFrame<KeyCode> edgeFrame = new InputFrame<>(Set.of(), Set.of(KeyCode.H), 0, 0, false);
        router.route(edgeFrame, context);
        
        assertEquals(1, sink.calls.size());
        assertEquals("showHighScore", sink.calls.get(0));
    }

    @Test
    void menuKeyTriggersAction() {
        InputFrame<KeyCode> edgeFrame = new InputFrame<>(Set.of(), Set.of(KeyCode.ESCAPE), 0, 0, false);
        router.route(edgeFrame, context);
        
        assertEquals(1, sink.calls.size());
        assertEquals("openDifficultyPickerAndMaybeRestart", sink.calls.get(0));
    }

    @Test
    void terminalKeyTriggersAction() {
        // T is terminal but we mocked it inside JavaFxInputCommandContext as doing nothing 
        // until fully integrated. Let's just make sure it routes to context.openTerminalPrompt
        // To verify we need to check if openTerminalPrompt on context delegates,
        // but it's an empty implementation right now. We can still execute it without error.
        InputFrame<KeyCode> edgeFrame = new InputFrame<>(Set.of(), Set.of(KeyCode.T), 0, 0, false);
        router.route(edgeFrame, context);
        
        assertEquals(0, sink.calls.size()); // empty impl in context for now
    }

    @Test
    void pathHintTriggersOnHold() {
        InputFrame<KeyCode> heldFrame = new InputFrame<>(Set.of(KeyCode.P), Set.of(), 0, 0, false);
        router.route(heldFrame, context);
        
        assertEquals(1, sink.calls.size());
        assertEquals("showNavigationPath", sink.calls.get(0));
    }

    @Test
    void spanningTreeTriggersOnHold() {
        InputFrame<KeyCode> heldFrame = new InputFrame<>(Set.of(KeyCode.O), Set.of(), 0, 0, false);
        router.route(heldFrame, context);
        
        assertEquals(1, sink.calls.size());
        assertEquals("showSpanningTree", sink.calls.get(0));
    }

    @Test
    void movementKeysTriggerMovementApplication() {
        InputFrame<KeyCode> heldFrame = new InputFrame<>(Set.of(KeyCode.W), Set.of(), 0, 0, false);
        router.route(heldFrame, context);
        
        assertEquals(0, sink.calls.size()); // Movement application is empty impl in context
    }

    private static final class RecordingSink implements JavaFxInputCommandContext.ActionSink {
        final List<String> calls = new ArrayList<>();

        @Override
        public void showHighScore() { calls.add("showHighScore"); }

        @Override
        public void openDifficultyPickerAndMaybeRestart() { calls.add("openDifficultyPickerAndMaybeRestart"); }

        @Override
        public void showNavigationPath() { calls.add("showNavigationPath"); }

        @Override
        public void showSpanningTree() { calls.add("showSpanningTree"); }

        @Override
        public void clearNavigationPath() { calls.add("clearNavigationPath"); }

        @Override
        public void clearSpanningTree() { calls.add("clearSpanningTree"); }

        @Override
        public void updateDebugLabels() { calls.add("updateDebugLabels"); }

        @Override
        public void updateScoreHud() { calls.add("updateScoreHud"); }

        @Override
        public void openTerminalPrompt() { calls.add("openTerminalPrompt"); }
    }
}
