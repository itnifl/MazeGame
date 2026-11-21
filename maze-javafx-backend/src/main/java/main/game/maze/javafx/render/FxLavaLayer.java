package main.game.maze.javafx.render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import main.game.maze.App;
import main.game.maze.common.graphics.fire.LavaEffect;

/**
 * The lava of a level, drawn on one canvas at the very bottom of the board: under the maze canvas, so the walls
 * lie over the streams, and under the characters. The picture is the shared {@link LavaEffect}, redrawn every frame so
 * the lava flows. Call from the JavaFX thread.
 */
public final class FxLavaLayer {

    private final Pane board;
    private Canvas canvas;
    private LavaEffect effect;

    public FxLavaLayer(Pane board) {
        this.board = board;
    }

    /** Puts the canvas at the bottom of the board (index 0: below the maze); null removes the lava. */
    public void attach(LavaEffect lava) {
        detach();
        this.effect = lava;
        if (board == null || lava == null) {
            return;
        }
        canvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        canvas.setMouseTransparent(true);
        board.getChildren().add(0, canvas);
    }

    public void detach() {
        if (canvas != null && board != null) {
            board.getChildren().remove(canvas);
        }
        canvas = null;
        effect = null;
    }

    /** Redraws the lava for this moment of the running clock. */
    public void render(double clockSeconds) {
        if (canvas == null || effect == null) {
            return;
        }
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        FxExplosionView.paint(gc, effect.puffs(0, clockSeconds), 0, 0);
    }
}
