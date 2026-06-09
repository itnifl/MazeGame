package main.game.maze.libgdx.controller.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class GameModeRouterTest {

    @Test
    void stopsAtFirstHandlerThatConsumes() {
        GameModeRouter router = new GameModeRouter();
        AtomicInteger calls = new AtomicInteger();

        router.register(() -> {
            calls.incrementAndGet();
            return false;
        });
        router.register(() -> {
            calls.incrementAndGet();
            return true;
        });
        router.register(() -> {
            calls.incrementAndGet();
            return false;
        });

        assertTrue(router.update());
        assertEquals(2, calls.get());
    }
}
