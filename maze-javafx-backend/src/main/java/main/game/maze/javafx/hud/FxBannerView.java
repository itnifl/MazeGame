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
        AnchorPane.setTopAnchor(shown, 0.0);
        shown.translateYProperty().bind(root.heightProperty().multiply(HEIGHT_SHARE));
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

    /**
     * A fighting-game title card: heavy italic letters with a gold gradient, a thick dark red outline and a black
     * shadow, slammed on to the screen at the scale and place the shared {@link main.game.maze.game.status.TitleCard}
     * gives. Shown for {@code seconds}.
     */
    public void showTitleCard(String text, double seconds) {
        AnchorPane root = rootSupplier.get();
        if (root == null || text == null || text.isBlank() || seconds <= 0) {
            return;
        }
        hide();
        javafx.scene.text.Text shown = new javafx.scene.text.Text(text);
        shown.setFont(javafx.scene.text.Font.font("Impact", javafx.scene.text.FontWeight.BLACK, javafx.scene.text.FontPosture.ITALIC, 130));
        shown.setFill(new javafx.scene.paint.LinearGradient(0, 0, 0, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0.0, Color.web("#fffbd0")), new javafx.scene.paint.Stop(0.35, Color.web("#ffd21e")),
                new javafx.scene.paint.Stop(0.7, Color.web("#ff8a00")), new javafx.scene.paint.Stop(1.0, Color.web("#d83a00"))));
        shown.setStroke(Color.web("#6e0505"));
        shown.setStrokeWidth(9);
        shown.setStrokeLineJoin(javafx.scene.shape.StrokeLineJoin.ROUND);
        shown.setEffect(new DropShadow(18, 10, 10, Color.BLACK));
        shown.setMouseTransparent(true);
        shown.setOpacity(0);
        javafx.scene.layout.StackPane holder = new javafx.scene.layout.StackPane(shown);
        holder.setMouseTransparent(true);
        holder.setAlignment(javafx.geometry.Pos.CENTER);
        holder.prefWidthProperty().bind(root.widthProperty());
        holder.prefHeightProperty().bind(root.heightProperty());
        holder.setViewOrder(-30);
        root.getChildren().add(holder);
        titleHolder = holder;

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
                main.game.maze.game.status.TitleCard.Frame frame = main.game.maze.game.status.TitleCard.frame(elapsed, total);
                shown.setOpacity(frame.alpha());
                shown.setScaleX(frame.scale());
                shown.setScaleY(frame.scale());
                shown.setTranslateX(frame.shakeX() * root.getHeight());
                shown.setTranslateY(-root.getHeight() * 0.12 + frame.shakeY() * root.getHeight());
            }
        };
        timer.start();
    }

    private javafx.scene.layout.StackPane titleHolder;

    /** Takes the banner off the screen at once. */
    public void hide() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        if (titleHolder != null) {
            AnchorPane root = rootSupplier.get();
            if (root != null) {
                root.getChildren().remove(titleHolder);
            }
            titleHolder = null;
        }
        if (label != null) {
            label.translateYProperty().unbind();
            AnchorPane root = rootSupplier.get();
            if (root != null) {
                root.getChildren().remove(label);
            }
            label = null;
        }
    }
}
