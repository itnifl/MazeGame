package main.game.maze.javafx.render;

import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import main.game.maze.App;
import main.game.maze.common.graphics.fire.RocketEffect;

/**
 * The rockets in the air, drawn on one canvas over the board and redrawn every frame (one canvas for all of them,
 * instead of a canvas and a timer per rocket per frame). The shape is the shared {@link RocketEffect}. Call from the
 * JavaFX thread.
 */
public final class FxRocketLayer {

    /** Drawn above the maze and the characters, like the other combat effects. */
    private static final int VIEW_ORDER = -9;

    private final Pane board;
    private Canvas canvas;

    public FxRocketLayer(Pane board) {
        this.board = board;
    }

    public void attach() {
        detach();
        if (board == null) {
            return;
        }
        canvas = new Canvas(App.getBoardMaxX(), App.getBoardMaxY());
        canvas.setMouseTransparent(true);
        canvas.setViewOrder(VIEW_ORDER);
        board.getChildren().add(canvas);
    }

    public void detach() {
        if (canvas != null && board != null) {
            board.getChildren().remove(canvas);
        }
        canvas = null;
    }

    /** Redraws the rockets (world space, y-down, as the board is); an empty list clears the layer. */
    public void render(List<RocketEffect> rockets, double clockSeconds) {
        if (canvas == null) {
            return;
        }
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (RocketEffect rocket : rockets) {
            FxExplosionView.paint(gc, rocket.puffs(0, clockSeconds), 0, 0);
        }
    }
}
