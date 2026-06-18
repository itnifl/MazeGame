package main.game.maze.characters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the null-guard paths in {@link ProgressBarStatePresenter}.
 * Tests that target a null ProgressBar do not touch UiScheduler/FX toolkit.
 */
class ProgressBarStatePresenterTest {

    @Test
    void showHealthRatio_withNullBar_doesNotThrow() {
        ProgressBarStatePresenter presenter = new ProgressBarStatePresenter(null);
        assertDoesNotThrow(() -> presenter.showHealthRatio(0.75));
    }

    @Test
    void showHealthRatio_withNullBar_belowZero_doesNotThrow() {
        ProgressBarStatePresenter presenter = new ProgressBarStatePresenter(null);
        assertDoesNotThrow(() -> presenter.showHealthRatio(-1.0));
    }

    @Test
    void showHealthRatio_withNullBar_aboveOne_doesNotThrow() {
        ProgressBarStatePresenter presenter = new ProgressBarStatePresenter(null);
        assertDoesNotThrow(() -> presenter.showHealthRatio(2.0));
    }

    @Test
    void dispose_withNullBar_doesNotThrow() {
        ProgressBarStatePresenter presenter = new ProgressBarStatePresenter(null);
        assertDoesNotThrow(presenter::dispose);
    }

    @Test
    void dispose_calledTwice_doesNotThrow() {
        ProgressBarStatePresenter presenter = new ProgressBarStatePresenter(null);
        presenter.dispose();
        // second call: hpBar is already null → must not throw
        assertDoesNotThrow(presenter::dispose);
    }

    @Test
    void showHealthRatio_afterDispose_doesNotThrow() {
        ProgressBarStatePresenter presenter = new ProgressBarStatePresenter(null);
        presenter.dispose();
        assertDoesNotThrow(() -> presenter.showHealthRatio(0.5));
    }
}
