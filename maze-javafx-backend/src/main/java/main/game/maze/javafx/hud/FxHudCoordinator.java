package main.game.maze.javafx.hud;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.function.Supplier;

/**
 * Manages the transient HUD message shown in the mouse-coordinates label.
 * Messages can be permanent (until replaced) or auto-clearing after a duration.
 */
public final class FxHudCoordinator {

    private final Supplier<Label> messageLabelSupplier;
    private PauseTransition clearTimer;

    public FxHudCoordinator(Supplier<Label> messageLabelSupplier) {
        this.messageLabelSupplier = messageLabelSupplier;
    }

    public void setMessage(String text) {
        if (clearTimer != null) { clearTimer.stop(); clearTimer = null; }
        Label l = messageLabelSupplier.get();
        if (l != null) l.setText(text);
    }

    public void setMessage(String text, Duration visibleFor) {
        setMessage(text);
        clearTimer = new PauseTransition(visibleFor);
        clearTimer.setOnFinished(e -> setMessage(""));
        clearTimer.play();
    }

    public void dispose() {
        if (clearTimer != null) { clearTimer.stop(); clearTimer = null; }
    }
}
