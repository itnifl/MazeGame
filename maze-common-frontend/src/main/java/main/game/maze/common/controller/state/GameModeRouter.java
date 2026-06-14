package main.game.maze.common.controller.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Dispatches per-frame mode handlers in registration order.
 */
public final class GameModeRouter {

    @FunctionalInterface
    public interface ModeHandler {
        boolean update();
    }

    private final List<ModeHandler> handlers = new ArrayList<>();

    public GameModeRouter register(ModeHandler handler) {
        handlers.add(handler);
        return this;
    }

    public boolean update() {
        for (ModeHandler handler : handlers) {
            if (handler.update()) {
                return true;
            }
        }
        return false;
    }
}
