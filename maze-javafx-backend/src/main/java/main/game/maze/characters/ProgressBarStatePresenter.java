package main.game.maze.characters;

import javafx.scene.control.ProgressBar;
import main.game.maze.common.graphics.ICharacterStatePresenter;
import main.game.maze.common.graphics.UiScheduler;

/** JavaFX presenter that projects player health ratio into a ProgressBar. */
public final class ProgressBarStatePresenter implements ICharacterStatePresenter {
    private volatile ProgressBar hpBar;

    public ProgressBarStatePresenter(ProgressBar hpBar) {
        this.hpBar = hpBar;
    }

    @Override
    public void showHealthRatio(double ratio) {
        ProgressBar bar = hpBar;
        if (bar == null) return;
        double clamped = Math.max(0.0, Math.min(1.0, ratio));
        UiScheduler.get().runOnUiThread(() -> bar.setProgress(clamped));
    }

    @Override
    public void dispose() {
        ProgressBar bar = hpBar;
        hpBar = null;
        if (bar == null) return;
        UiScheduler.get().runOnUiThread(() -> {
            if (bar.progressProperty().isBound()) {
                bar.progressProperty().unbind();
            }
        });
    }
}
