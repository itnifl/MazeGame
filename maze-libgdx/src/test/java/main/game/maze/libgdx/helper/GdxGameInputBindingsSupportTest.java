package main.game.maze.libgdx.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.InputProcessor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

/**
 * Verifies the terminal keyboard {@link InputProcessor} routing extracted from
 * the gameplay screen controller: typed characters go to the win-name field
 * while it is capturing, otherwise to the terminal, and the handled flag is
 * propagated back from the chosen consumer.
 */
class GdxGameInputBindingsSupportTest {

    @Test
    void routesTypedCharToWinNameWhileCapturing() {
        AtomicInteger winChar = new AtomicInteger(-1);
        AtomicInteger terminalChar = new AtomicInteger(-1);

        InputProcessor processor = GdxGameInputBindingsSupport.createTerminalKeyboardProcessor(
                () -> true,
                c -> {
                    winChar.set(c);
                    return true;
                },
                c -> {
                    terminalChar.set(c);
                    return true;
                });

        boolean handled = processor.keyTyped('A');

        assertTrue(handled, "win-name consumer reported handled");
        assertEquals('A', winChar.get(), "char routed to win-name consumer");
        assertEquals(-1, terminalChar.get(), "terminal consumer not invoked while capturing");
    }

    @Test
    void routesTypedCharToTerminalWhenNotCapturing() {
        AtomicInteger winChar = new AtomicInteger(-1);
        AtomicInteger terminalChar = new AtomicInteger(-1);

        InputProcessor processor = GdxGameInputBindingsSupport.createTerminalKeyboardProcessor(
                () -> false,
                c -> {
                    winChar.set(c);
                    return true;
                },
                c -> {
                    terminalChar.set(c);
                    return false;
                });

        boolean handled = processor.keyTyped('z');

        assertFalse(handled, "terminal consumer reported not handled");
        assertEquals('z', terminalChar.get(), "char routed to terminal consumer");
        assertEquals(-1, winChar.get(), "win-name consumer not invoked when not capturing");
    }

    @Test
    void capturePredicateIsReevaluatedPerKeystroke() {
        AtomicBoolean capturing = new AtomicBoolean(true);
        AtomicInteger winCount = new AtomicInteger();
        AtomicInteger terminalCount = new AtomicInteger();

        InputProcessor processor = GdxGameInputBindingsSupport.createTerminalKeyboardProcessor(
                capturing::get,
                c -> {
                    winCount.incrementAndGet();
                    return true;
                },
                c -> {
                    terminalCount.incrementAndGet();
                    return true;
                });

        processor.keyTyped('a');
        capturing.set(false);
        processor.keyTyped('b');

        assertEquals(1, winCount.get(), "first keystroke captured by win-name field");
        assertEquals(1, terminalCount.get(), "second keystroke routed to terminal after capture ended");
    }
}
