package main.game.maze.javafx.hud;

import java.util.function.Supplier;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * A big word in the middle of the game screen (for example "Teleported") that fades in, holds and fades out.
 * Use on the JavaFX Application Thread.
 */
public final class FxBannerView {

    private static final double FADE_SECONDS = 0.15;
    /** Height of the text on the screen as a share of the window height. */
    private static final double HEIGHT_SHARE = 0.34;

    private final Supplier<AnchorPane> rootSupplier;
    private Label label;
    private SequentialTransition running;

    public FxBannerView(Supplier<AnchorPane> rootSupplier) {
        this.rootSupplier = rootSupplier;
    }

    /** The label currently on the screen, or null (for tests). */
    Label label() {
        return label;
    }

    /** Shows {@code text} for {@code seconds}, including the fades. */
    public void show(String text, double seconds) {
        AnchorPane root = rootSupplier.get();
        if (root == null || text == null || text.isBlank() || seconds <= 0) {
            return;
        }
        hide();
        label = new Label(text);
        label.setStyle("-fx-font-size: 72px; -fx-font-weight: bold; -fx-font-family: 'Consolas'; -fx-text-fill: #b8ebff;");
        label.setEffect(new DropShadow(10, Color.BLACK));
        label.setMouseTransparent(true);
        label.setOpacity(0);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 0.0);
        label.layoutYProperty().bind(root.heightProperty().multiply(HEIGHT_SHARE));
        root.getChildren().add(label);

        double hold = Math.max(0, seconds - 2 * FADE_SECONDS);
        FadeTransition in = new FadeTransition(Duration.seconds(FADE_SECONDS), label);
        in.setToValue(1);
        PauseTransition pause = new PauseTransition(Duration.seconds(hold));
        FadeTransition out = new FadeTransition(Duration.seconds(FADE_SECONDS), label);
        out.setToValue(0);
        Label shown = label;
        running = new SequentialTransition(in, pause, out);
        running.setOnFinished(event -> remove(shown));
        running.play();
    }

    /** Takes the banner off the screen at once. */
    public void hide() {
        if (running != null) {
            running.stop();
            running = null;
        }
        if (label != null) {
            remove(label);
        }
    }

    private void remove(Label shown) {
        shown.layoutYProperty().unbind();
        AnchorPane root = rootSupplier.get();
        if (root != null) {
            root.getChildren().remove(shown);
        }
        if (label == shown) {
            label = null;
        }
    }
}
