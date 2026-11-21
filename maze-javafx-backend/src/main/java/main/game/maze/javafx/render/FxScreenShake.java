package main.game.maze.javafx.render;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import main.game.maze.common.graphics.fire.BombImpact;

/** Shakes a node (the game screen) with the shared {@link BombImpact} offsets and puts it back afterwards. */
public final class FxScreenShake {

    private FxScreenShake() {
    }

    /** Shakes {@code target} for {@link BombImpact#SHAKE_SECONDS}; must be called on the JavaFX thread. */
    public static void play(Node target) {
        if (target == null) {
            return;
        }
        double baseX = target.getTranslateX();
        double baseY = target.getTranslateY();
        new AnimationTimer() {
            private long startNanos = -1;

            @Override
            public void handle(long now) {
                if (startNanos < 0) {
                    startNanos = now;
                }
                double elapsed = (now - startNanos) / 1e9;
                if (elapsed >= BombImpact.SHAKE_SECONDS) {
                    target.setTranslateX(baseX);
                    target.setTranslateY(baseY);
                    stop();
                    return;
                }
                double[] offset = BombImpact.shakeOffset(elapsed);
                target.setTranslateX(baseX + offset[0]);
                target.setTranslateY(baseY + offset[1]);
            }
        }.start();
    }
}
