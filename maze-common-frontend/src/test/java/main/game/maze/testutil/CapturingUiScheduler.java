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
        if (inline) {
            action.run();
        } else {
            runLater(action);
        }
    }

    @Override
    public boolean isUiThread() {
        return inline;
    }

    /** Runs all captured actions that have not yet been executed (deferred mode only). */
    public void flush() {
        if (!inline) {
            new ArrayList<>(captured).forEach(Runnable::run);
        }
    }

    /** Returns a snapshot of every action ever submitted (run or not). */
    public List<Runnable> capturedActions() {
        return List.copyOf(captured);
    }

    /** Total number of {@code runOnUiThread} calls received. */
    public int callCount() {
        return captured.size();
    }
}
