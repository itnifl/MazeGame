package main.game.maze.javafx.render;

import java.util.HashMap;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import main.game.maze.common.graphics.fire.BombImpact;

/** Shakes a node (the game screen) with the shared {@link BombImpact} offsets and puts it back afterwards. */
public final class FxScreenShake {

    private static final Map<Node, ActiveShake> ACTIVE_SHAKES = new HashMap<>();

    private FxScreenShake() {
    }

    static final class ActiveShake extends AnimationTimer {
        private final Node target;
        private final double baseX;
        private final double baseY;
        private long startNanos = -1;
        private double seconds = BombImpact.SHAKE_SECONDS;
        private double scale = 1.0;

        ActiveShake(Node target, double baseX, double baseY) {
            this.target = target;
            this.baseX = baseX;
            this.baseY = baseY;
        }

        void restart(double seconds, double scale) {
            this.seconds = Math.max(this.seconds, seconds);
            this.scale = Math.max(this.scale, scale);
            startNanos = -1;
        }

        double baseX() {
            return baseX;
        }

        double baseY() {
            return baseY;
        }

        @Override
        public void handle(long now) {
            if (startNanos < 0) {
                startNanos = now;
            }
            double elapsed = (now - startNanos) / 1e9;
            if (elapsed >= seconds) {
                stop();
                target.setTranslateX(baseX);
                target.setTranslateY(baseY);
                ACTIVE_SHAKES.remove(target);
                return;
            }
            // the shared offsets, stretched over the length of this shake and scaled to its strength
            double[] offset = BombImpact.shakeOffset(elapsed * BombImpact.SHAKE_SECONDS / seconds);
            target.setTranslateX(baseX + offset[0] * scale);
            target.setTranslateY(baseY + offset[1] * scale);
        }
    }

    /** Shakes {@code target} for {@link BombImpact#SHAKE_SECONDS}; must be called on the JavaFX thread. */
    public static void play(Node target) {
        play(target, BombImpact.SHAKE_SECONDS, BombImpact.SHAKE_STRENGTH);
    }

    /** Shakes {@code target} for {@code seconds} with offsets of up to about {@code strength} px (the nuclear blast). */
    public static void play(Node target, double seconds, double strength) {
        if (target == null) {
            return;
        }
        ActiveShake active = ACTIVE_SHAKES.get(target);
        if (active != null) {
            active.restart(seconds, strength / BombImpact.SHAKE_STRENGTH);
            return;
        }
        ActiveShake shake = new ActiveShake(target, target.getTranslateX(), target.getTranslateY());
        shake.restart(seconds, strength / BombImpact.SHAKE_STRENGTH);
        ACTIVE_SHAKES.put(target, shake);
        shake.start();
    }

    static ActiveShake activeShake(Node target) {
        return ACTIVE_SHAKES.get(target);
    }
}
