package main.game.maze.javafx.hud;

import java.util.function.Supplier;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.game.maze.game.status.BannerFade;

/**
 * A big word in the middle of the game screen (for example "Teleported") that fades in, holds and fades out, with
 * the same fade as the libGDX banner ({@link BannerFade}). Use on the JavaFX Application Thread.
 */
public final class FxBannerView {

    /** Height of the text on the screen as a share of the window height. */
    private static final double HEIGHT_SHARE = 0.34;

    private final Supplier<AnchorPane> rootSupplier;
    private Label label;
    private AnimationTimer timer;

    public FxBannerView(Supplier<AnchorPane> rootSupplier) {
        this.rootSupplier = rootSupplier;
    }

    /** Shows {@code text} for {@code seconds}, including the fades. */
    public void show(String text, double seconds) {
        AnchorPane root = rootSupplier.get();
        if (root == null || text == null || text.isBlank() || seconds <= 0) {
            return;
        }
        hide();
        Label shown = new Label(text);
        shown.setStyle("-fx-font-size: 72px; -fx-font-weight: bold; -fx-font-family: 'Consolas'; -fx-text-fill: #b8ebff;");
        shown.setEffect(new DropShadow(10, Color.BLACK));
        shown.setMouseTransparent(true);
        shown.setOpacity(0);
        shown.setAlignment(javafx.geometry.Pos.CENTER);
        AnchorPane.setLeftAnchor(shown, 0.0);
        AnchorPane.setRightAnchor(shown, 0.0);
        shown.layoutYProperty().bind(root.heightProperty().multiply(HEIGHT_SHARE));
        root.getChildren().add(shown);
        label = shown;

        float total = (float) seconds;
        timer = new AnimationTimer() {
            private long startNanos = -1;

            @Override
            public void handle(long now) {
                if (startNanos < 0) {
                    startNanos = now;
                }
                float elapsed = (now - startNanos) / 1e9f;
                if (elapsed >= total) {
                    hide();
                    return;
                }
                shown.setOpacity(BannerFade.alpha(elapsed, total));
            }
        };
        timer.start();
    }

    /** Takes the banner off the screen at once. */
    public void hide() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        if (label != null) {
            label.layoutYProperty().unbind();
            AnchorPane root = rootSupplier.get();
            if (root != null) {
                root.getChildren().remove(label);
            }
            label = null;
        }
    }
}
