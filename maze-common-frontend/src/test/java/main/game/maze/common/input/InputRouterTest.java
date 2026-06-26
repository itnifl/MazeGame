package main.game.maze.common.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import main.game.maze.common.input.command.GameCommand;
import main.game.maze.common.input.command.GameCommandContext;
import org.junit.jupiter.api.Test;

class InputRouterTest {

    @Test
    void executesCommandsInRegistryOrder() {
        AtomicInteger calls = new AtomicInteger();

        GameCommand<Integer> first = (ctx, frame) -> calls.incrementAndGet();
        GameCommand<Integer> second = (ctx, frame) -> calls.addAndGet(10);

        KeyBindingRegistry<Integer> registry = new KeyBindingRegistry<Integer>()
                .bind(GameAction.TOGGLE_TERMINAL, 1, KeyBindingRegistry.BindingKind.EDGE)
                .command(GameAction.TOGGLE_TERMINAL, first)
                .bind(GameAction.OPEN_HIGH_SCORES, 2, KeyBindingRegistry.BindingKind.EDGE)
                .command(GameAction.OPEN_HIGH_SCORES, second);

        InputRouter<Integer> router = new InputRouter<>(registry);
        InputFrame<Integer> frame = new InputFrame<>(Set.of(), Set.of(1, 2), 0, 0, false);
        router.route(frame, new FakeContext());

        assertEquals(11, calls.get());
    }

    @Test
    void stopsRoutingWhenContextRequestsStop() {
        AtomicInteger calls = new AtomicInteger();

        GameCommand<Integer> first = (ctx, frame) -> {
            calls.incrementAndGet();
            ctx.requestStop();
        };
        GameCommand<Integer> second = (ctx, frame) -> calls.addAndGet(10);

        KeyBindingRegistry<Integer> registry = new KeyBindingRegistry<Integer>()
                .bind(GameAction.TOGGLE_TERMINAL, 1, KeyBindingRegistry.BindingKind.EDGE)
                .command(GameAction.TOGGLE_TERMINAL, first)
                .bind(GameAction.OPEN_HIGH_SCORES, 2, KeyBindingRegistry.BindingKind.EDGE)
                .command(GameAction.OPEN_HIGH_SCORES, second);

        InputRouter<Integer> router = new InputRouter<>(registry);
        InputFrame<Integer> frame = new InputFrame<>(Set.of(), Set.of(1, 2), 0, 0, false);
        router.route(frame, new FakeContext());

        assertEquals(1, calls.get());
    }

    private static final class FakeContext implements GameCommandContext {
        private boolean stop;

        @Override
        public boolean terminalActive() {
            return false;
        }

        @Override
        public void requestReturnToMenu() {
        }

        @Override
        public void openTerminalPrompt() {
        }

        @Override
        public void openHighScores() {
        }

        @Override
        public void toggleSpanningTree() {
        }

        @Override
        public void applyPathHintHeld(boolean held) {
        }

        @Override
        public void triggerPlayerFlameAttack() {
        }

        @Override
        public void applyMovementFromFrame() {
        }

        @Override
        public void requestStop() {
            stop = true;
        }

        @Override
        public boolean stopRequested() {
            return stop;
        }
    }
}
