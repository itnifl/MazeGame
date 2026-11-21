package main.game.maze.javafx.render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import main.game.maze.App;
import main.game.maze.common.graphics.fire.HoleEffect;
import main.game.maze.common.graphics.fire.MinePulseEffect;
import main.game.maze.game.hazard.MineField;

/**
 * The mines and holes of a level, drawn on one canvas right above the maze (below the characters). The shapes
 * are the shared {@link MinePulseEffect} and {@link HoleEffect}; the pulse and the swirl follow the field's clock.
 * Call from the JavaFX thread.
 */
public final class FxHazardLayer {

    private final Pane board;
    private Canvas canvas;

    public FxHazardLayer(Pane board) {
        this.board = board;
    }

    /** Puts the canvas on the board, just above the maze canvas (index 0). */
    public void attach() {
        detach();
        if (board == null) {
            return;
        }
        canvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        canvas.setMouseTransparent(true);
        board.getChildren().add(Math.min(1, board.getChildren().size()), canvas);
    }

    public void detach() {
        if (canvas != null && board != null) {
            board.getChildren().remove(canvas);
        }
        canvas = null;
    }

    /** Redraws every hole and mine of the field (world space, y-down, same as the board). */
    public void render(MineField field) {
        if (canvas == null) {
            return;
        }
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (field == null) {
            return;
        }
        double clock = field.clock();
        for (MineField.Hole hole : field.holes()) {
            FxExplosionView.paint(gc, new HoleEffect(hole.x(), hole.y(), hole.radius()).puffs(0, clock), 0, 0);
        }
        double pulse = field.spec().pulseSeconds();
        for (MineField.Mine mine : field.mines()) {
            FxExplosionView.paint(gc, new MinePulseEffect(mine.x(), mine.y(), mine.phase(), pulse).puffs(0, clock), 0, 0);
        }
    }
}
