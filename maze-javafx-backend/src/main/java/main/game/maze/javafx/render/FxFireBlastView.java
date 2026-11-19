package main.game.maze.javafx.render;

import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.game.maze.common.graphics.fire.FireBlast;
import main.game.maze.common.graphics.fire.FireBlastModel;
import main.game.maze.common.graphics.fire.FirePuff;

/**
 * JavaFX view of a bomb fire blast: a transparent {@link Canvas} covering the blast, redrawn
 * every frame from the shared {@link FireBlastModel} and removed when the blast ends.
 * Smoke is painted with normal blending, flame layers with {@link BlendMode#ADD} so
 * overlapping puffs glow.
 *
 * <p>JavaFX is y-down, so a blast's {@code armPlusY} reaches down the screen.
 */
public final class FxFireBlastView {

    /** Room around the arms for the shockwave ring, embers and puff radii, in corridor widths. */
    static final double PADDING_WIDTHS = 3.2;
    private static final double NANOS_PER_SECOND = 1_000_000_000.0;

    private final FireBlast blast;
    private final Canvas canvas;

    public FxFireBlastView(FireBlast blast) {
        this.blast = blast;
        double pad = blast.corridorWidth() * PADDING_WIDTHS;
        this.canvas = new Canvas(
                blast.armMinusX() + blast.armPlusX() + 2 * pad,
                blast.armMinusY() + blast.armPlusY() + 2 * pad);
        canvas.setLayoutX(blast.centerX() - blast.armMinusX() - pad);
        canvas.setLayoutY(blast.centerY() - blast.armMinusY() - pad);
        canvas.setMouseTransparent(true);
    }

    public Canvas canvas() {
        return canvas;
    }

    /**
     * Adds the blast to {@code parent}, animates it for {@code lifetimeSeconds}, then removes it.
     * Must be called on the JavaFX Application Thread.
     */
    public static FxFireBlastView play(Pane parent, FireBlast blast, double lifetimeSeconds, double viewOrder) {
        FxFireBlastView view = new FxFireBlastView(blast);
        view.canvas.setViewOrder(viewOrder);
        parent.getChildren().add(view.canvas);
        new AnimationTimer() {
            private long startNanos = -1;

            @Override
            public void handle(long now) {
                if (startNanos < 0) {
                    startNanos = now;
                }
                double elapsed = (now - startNanos) / NANOS_PER_SECOND;
                double progress = progressAt(elapsed, lifetimeSeconds);
                if (progress >= 1.0) {
                    stop();
                    parent.getChildren().remove(view.canvas);
                    return;
                }
                view.renderFrame(progress, elapsed);
            }
        }.start();
        return view;
    }

    /** Life progress for an elapsed time; 1 means finished. */
    static double progressAt(double elapsedSeconds, double lifetimeSeconds) {
        if (lifetimeSeconds <= 0) {
            return 1.0;
        }
        return Math.max(0.0, Math.min(1.0, elapsedSeconds / lifetimeSeconds));
    }

    /** Clears the canvas and draws one frame. */
    public void renderFrame(double progress, double clockSeconds) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        paint(gc, FireBlastModel.puffs(blast, progress, clockSeconds), canvas.getLayoutX(), canvas.getLayoutY());
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
    }

    private static void paint(GraphicsContext gc, List<FirePuff> puffs, double originX, double originY) {
        for (FirePuff puff : puffs) {
            gc.setGlobalBlendMode(puff.layer().additive() ? BlendMode.ADD : BlendMode.SRC_OVER);
            gc.setFill(Color.color(puff.red(), puff.green(), puff.blue(), puff.alpha()));
            double r = puff.radius();
            gc.fillOval(puff.x() - originX - r, puff.y() - originY - r, 2 * r, 2 * r);
        }
    }
}
