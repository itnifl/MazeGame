package main.game.maze.javafx.lifecycle;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Pending enemy resurrections of the current level (JavaFX). Owned by the game controller, which
 * cancels them all when the level is torn down, so an enemy that died in one level can never be
 * resurrected into the next. Use on the JavaFX Application Thread.
 */
public final class FxResurrectionScheduler {

    private final List<PauseTransition> pending = new ArrayList<>();

    /** Runs {@code resurrection} after {@code seconds}, unless cancelled first. */
    public void schedule(double seconds, Runnable resurrection) {
        PauseTransition timer = new PauseTransition(Duration.seconds(Math.max(0, seconds)));
        timer.setOnFinished(event -> {
            pending.remove(timer);
            resurrection.run();
        });
        pending.add(timer);
        timer.play();
    }

    /** Cancels every pending resurrection (level restart or shutdown). */
    public void cancelAll() {
        for (PauseTransition timer : pending) {
            timer.stop();
        }
        pending.clear();
    }

    int pendingCount() {
        return pending.size();
    }
}
