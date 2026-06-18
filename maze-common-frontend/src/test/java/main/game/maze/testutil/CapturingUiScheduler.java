package main.game.maze.testutil;

import main.game.maze.common.graphics.IUiScheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link IUiScheduler} test double that records every {@code runOnUiThread} call.
 * Two operating modes:
 * <ul>
 *   <li><b>inline</b> (default) — actions run immediately on the calling thread,
 *       simulating a single-threaded or already-on-UI-thread environment.</li>
 *   <li><b>deferred</b> — actions are captured but not run; call {@link #flush()}
 *       to drain the queue, simulating a UI event loop.</li>
 * </ul>
 * Always restore the singleton with {@code UiScheduler.reset()} in {@code @AfterEach}.
 */
public final class CapturingUiScheduler implements IUiScheduler {

    private final List<Runnable> captured = new ArrayList<>();
    private final boolean inline;
    /** Points past the last action already executed by {@link #flush()}. */
    private int flushedUpTo = 0;

    /** Creates a capturing scheduler that also runs actions inline (synchronous mode). */
    public CapturingUiScheduler() {
        this(true);
    }

    /**
     * @param inline {@code true} to run actions immediately; {@code false} to
     *               only capture them until {@link #flush()} is called.
     */
    public CapturingUiScheduler(boolean inline) {
        this.inline = inline;
    }

    @Override
    public void runLater(Runnable action) {
        captured.add(action);
        if (inline) {
            action.run();
        }
    }

    @Override
    public void runOnUiThread(Runnable action) {
        runLater(action);
    }

    @Override
    public boolean isUiThread() {
        return inline;
    }

    /**
     * Runs all as-yet-unflushed actions (deferred mode only).
     * Safe to call multiple times — each call runs only actions added since the last flush.
     */
    public void flush() {
        if (!inline) {
            int end = captured.size();
            for (; flushedUpTo < end; flushedUpTo++) {
                captured.get(flushedUpTo).run();
            }
        }
    }

    /** Returns a snapshot of every action ever submitted (run or not). */
    public List<Runnable> capturedActions() {
        return List.copyOf(captured);
    }

    /** Total actions submitted via {@code runLater} or {@code runOnUiThread}. */
    public int callCount() {
        return captured.size();
    }
}
