package main.game.maze.javafx.render;

import main.game.maze.common.graphics.fire.PuffEffect;
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
import main.game.maze.common.graphics.fire.PumpkinBurst;
import main.game.maze.common.graphics.fire.PumpkinBurstModel;
import main.game.maze.common.graphics.fire.WallRubble;
import main.game.maze.common.graphics.fire.WallRubbleModel;

/**
 * JavaFX view of one explosion (player bomb fire blast, pumpkin bomber burst or collapsing wall): a transparent
 * {@link Canvas} covering the explosion, redrawn every frame from the shared puff models and
 * removed when it ends. Smoke and debris are painted with normal blending, flame layers with
 * {@link BlendMode#ADD} so overlapping puffs glow.
 *
 * <p>JavaFX is y-down, so a fire blast's {@code armPlusY} reaches down the screen.
 */
public final class FxExplosionView {

    /** Frame source: puffs for a life progress (0..1) and a running clock. */
    @FunctionalInterface
    interface PuffSource {
        List<FirePuff> puffs(double progress, double clockSeconds);
    }

    /** Room around fire blast arms for the shockwave ring, embers and puff radii, in corridor widths. */
    static final double PADDING_WIDTHS = 3.2;
    private static final double NANOS_PER_SECOND = 1_000_000_000.0;

    private final PuffSource source;
    private final Canvas canvas;

    private FxExplosionView(PuffSource source, double minX, double minY, double maxX, double maxY) {
        this.source = source;
        this.canvas = new Canvas(maxX - minX, maxY - minY);
        canvas.setLayoutX(minX);
        canvas.setLayoutY(minY);
        canvas.setMouseTransparent(true);
    }

    /** View for a player bomb fire blast. */
    public static FxExplosionView of(FireBlast blast) {
        double pad = blast.corridorWidth() * PADDING_WIDTHS;
        return new FxExplosionView((p, clock) -> FireBlastModel.puffs(blast, p, clock),
                blast.centerX() - blast.armMinusX() - pad,
                blast.centerY() - blast.armMinusY() - pad,
                blast.centerX() + blast.armPlusX() + pad,
                blast.centerY() + blast.armPlusY() + pad);
    }

    /** View for a pumpkin bomber projectile burst. */
    public static FxExplosionView of(PumpkinBurst burst) {
        double extent = burst.maxExtent();
        return new FxExplosionView((p, clock) -> PumpkinBurstModel.puffs(burst, p, clock),
                burst.centerX() - extent, burst.centerY() - extent,
                burst.centerX() + extent, burst.centerY() + extent);
    }

    /** View for any shared combat effect (laser beam, sledgehammer swing, hit reaction, pickup sparkle). */
    public static FxExplosionView of(PuffEffect effect) {
        double[] b = effect.bounds();
        return new FxExplosionView(effect::puffs, b[0], b[1], b[2], b[3]);
    }

    /** View for a breakable wall collapsing into rubble. */
    public static FxExplosionView of(WallRubble rubble) {
        double extent = rubble.maxExtent();
        return new FxExplosionView((p, clock) -> WallRubbleModel.puffs(rubble, p, clock),
                Math.min(rubble.ax(), rubble.bx()) - extent, Math.min(rubble.ay(), rubble.by()) - extent,
                Math.max(rubble.ax(), rubble.bx()) + extent, Math.max(rubble.ay(), rubble.by()) + extent);
    }

    public Canvas canvas() {
        return canvas;
    }

    /**
     * Adds this view to {@code parent}, animates it for {@code lifetimeSeconds}, then removes it.
     * Must be called on the JavaFX Application Thread.
     */
    public FxExplosionView play(Pane parent, double lifetimeSeconds, double viewOrder) {
        canvas.setViewOrder(viewOrder);
        parent.getChildren().add(canvas);
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
                    parent.getChildren().remove(canvas);
                    return;
                }
                renderFrame(progress, elapsed);
            }
        }.start();
        return this;
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
        paint(gc, source.puffs(progress, clockSeconds), canvas.getLayoutX(), canvas.getLayoutY());
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
    }

    /** Paints puffs onto a canvas whose top-left is at board position (originX, originY). */
    public static void paint(GraphicsContext gc, List<FirePuff> puffs, double originX, double originY) {
        for (FirePuff puff : puffs) {
            gc.setGlobalBlendMode(puff.layer().additive() ? BlendMode.ADD : BlendMode.SRC_OVER);
            gc.setFill(Color.color(puff.red(), puff.green(), puff.blue(), puff.alpha()));
            double r = puff.radius();
            gc.fillOval(puff.x() - originX - r, puff.y() - originY - r, 2 * r, 2 * r);
        }
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
    }
}
