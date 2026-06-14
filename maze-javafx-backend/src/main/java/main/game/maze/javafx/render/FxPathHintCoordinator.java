package main.game.maze.javafx.render;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.game.maze.FxGameWorldModel;
import main.game.maze.characters.PlayerCharacter;
import main.game.maze.difficulties.EasyDifficulty;
import main.game.maze.difficulties.HardDifficulty;
import main.game.maze.mazeworld.GameMazeWorld;
import main.game.maze.mazeworld.Point2D;
import main.game.maze.mazeworld.service.MazeNavigationGraphService;

import java.util.function.Supplier;

/**
 * Manages the path-hint energy budget, countdown label, and player navigation
 * path drawing. Extracted from GameController to isolate path-hint lifecycle.
 *
 * The label supplier is resolved lazily so this coordinator can be constructed
 * before FXML initialisation populates the @FXML label field.
 */
public final class FxPathHintCoordinator {

    private static final long PATH_HINT_BUDGET_EASY_NANOS   = 45L * 1_000_000_000L;
    private static final long PATH_HINT_BUDGET_NORMAL_NANOS = 25L * 1_000_000_000L;
    private static final long PATH_HINT_BUDGET_HARD_NANOS   = 15L * 1_000_000_000L;

    private final Supplier<Label> labelSupplier;
    private final FxGameWorldModel model;
    private final Supplier<?> difficultySupplier;
    private final Supplier<GameMazeWorld> mazeSupplier;
    private final Supplier<PlayerCharacter> playerSupplier;
    private final Supplier<Node> heartSupplier;
    private final Runnable onRefreshRequired;

    private Timeline pathHintCountdownTicker;
    private PauseTransition pathHintExhaustedClearTimer;

    public FxPathHintCoordinator(
            Supplier<Label> labelSupplier,
            FxGameWorldModel model,
            Supplier<?> difficultySupplier,
            Supplier<GameMazeWorld> mazeSupplier,
            Supplier<PlayerCharacter> playerSupplier,
            Supplier<Node> heartSupplier,
            Runnable onRefreshRequired) {
        this.labelSupplier      = labelSupplier;
        this.model              = model;
        this.difficultySupplier = difficultySupplier;
        this.mazeSupplier       = mazeSupplier;
        this.playerSupplier     = playerSupplier;
        this.heartSupplier      = heartSupplier;
        this.onRefreshRequired  = onRefreshRequired;
    }

    private Label lbl() {
        return labelSupplier != null ? labelSupplier.get() : null;
    }

    public long budgetNanos() {
        Object difficulty = difficultySupplier.get();
        if (difficulty instanceof HardDifficulty) return PATH_HINT_BUDGET_HARD_NANOS;
        if (difficulty instanceof EasyDifficulty) return PATH_HINT_BUDGET_EASY_NANOS;
        return PATH_HINT_BUDGET_NORMAL_NANOS;
    }

    public void startCountdown() {
        stopCountdown();
        pathHintCountdownTicker = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            long usedSoFar      = model.pathHintTotalUsedNanos() + (System.nanoTime() - model.pathHintPressStartNanos());
            long budget         = budgetNanos();
            long remainingNanos = budget - usedSoFar;
            if (remainingNanos <= 0) {
                model.setPathHintTotalUsedNanos(budget);
                model.setPathHintKeyDown(false);
                model.setRouteHintVisible(false);
                stopCountdown();
                if (onRefreshRequired != null) onRefreshRequired.run();
                showExhaustedMessage();
                return;
            }
            updateLbl(remainingNanos);
        }));
        pathHintCountdownTicker.setCycleCount(Animation.INDEFINITE);
        pathHintCountdownTicker.play();
        updateLbl(budgetNanos() - model.pathHintTotalUsedNanos());
    }

    public void stopCountdown() {
        if (pathHintCountdownTicker != null) {
            pathHintCountdownTicker.stop();
            pathHintCountdownTicker = null;
        }
    }

    public void clearTimerLabel() {
        Label l = lbl();
        if (l == null) return;
        l.setText("");
        l.setVisible(false);
    }

    public void showExhaustedMessage() {
        Label l = lbl();
        if (l == null) return;
        if (pathHintExhaustedClearTimer != null) pathHintExhaustedClearTimer.stop();
        l.setText("Path hint energy already spent!");
        l.setVisible(true);
        pathHintExhaustedClearTimer = new PauseTransition(Duration.seconds(3));
        pathHintExhaustedClearTimer.setOnFinished(ev -> clearTimerLabel());
        pathHintExhaustedClearTimer.play();
    }

    public void drawPlayerNavigationPath(GraphicsContext gc) {
        GameMazeWorld maze     = mazeSupplier.get();
        PlayerCharacter player = playerSupplier.get();
        Node heart             = heartSupplier.get();

        if (maze == null || heart == null || player == null) {
            model.setRouteHintVisible(false);
            return;
        }

        var navGraph = maze.getNavigationGraph();
        if (navGraph == null) {
            model.setRouteHintVisible(false);
            return;
        }

        Point2D start = new Point2D(
                player.getCharacterPosition().getX(),
                player.getCharacterPosition().getY());
        double heartW = heart.getBoundsInLocal().getWidth();
        double heartH = heart.getBoundsInLocal().getHeight();
        Point2D goal = new Point2D(
                heart.getLayoutX() + heartW / 2.0,
                heart.getLayoutY() + heartH / 2.0);

        var path = MazeNavigationGraphService.findPath(navGraph, start, goal);
        if (path == null || path.size() < 2) {
            model.setRouteHintVisible(false);
            return;
        }

        gc.setLineWidth(8.0);
        gc.setStroke(Color.DODGERBLUE);
        gc.setGlobalAlpha(0.6);
        Point2D prev = path.get(0);
        for (int i = 1; i < path.size(); i++) {
            Point2D p = path.get(i);
            gc.strokeLine(prev.getX(), prev.getY(), p.getX(), p.getY());
            prev = p;
        }
    }

    public void dispose() {
        stopCountdown();
        if (pathHintExhaustedClearTimer != null) {
            pathHintExhaustedClearTimer.stop();
            pathHintExhaustedClearTimer = null;
        }
    }

    private void updateLbl(long remainingNanos) {
        Label l = lbl();
        if (l == null) return;
        double secs = remainingNanos / 1_000_000_000.0;
        l.setText(String.format("Path hint: %.1fs left", secs));
        l.setVisible(true);
    }
}
