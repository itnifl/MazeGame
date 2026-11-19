package main.game.maze.javafx.render;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.game.maze.common.graphics.fire.FireBlast;
import main.game.maze.common.remains.DeathCause;
import main.game.maze.common.remains.RemainsLedger;
import main.game.maze.common.remains.RemainsSpriteResolver;

/**
 * Remains of killed enemies on the JavaFX board (coal for fire, a bloody body when shot, a splat
 * when crushed). Each remains is an {@link ImageView} placed just above the maze floor, under the
 * characters, and kept until its owner respawns ({@link #clear}) or the level restarts
 * ({@link #clearAll}). Must be used on the JavaFX Application Thread.
 */
public final class FxRemainsLayer {

    /** Child index just above the maze canvas (index 0), below characters and overlays. */
    static final int FLOOR_INDEX = 1;

    private final Supplier<Pane> board;
    private final RemainsSpriteResolver sprites;
    private final Function<String, Image> loader;
    private final RemainsLedger<Object, ImageView> ledger = new RemainsLedger<>(RemainsLedger.DEFAULT_CAPACITY);
    private final Map<String, Image> images = new HashMap<>();

    /** @param board the game board, looked up when remains are shown (FXML injects it late) */
    public FxRemainsLayer(Supplier<Pane> board) {
        this(board, RemainsSpriteResolver.classpath(FxRemainsLayer.class), FxRemainsLayer::loadImage);
    }

    FxRemainsLayer(Supplier<Pane> board, RemainsSpriteResolver sprites, Function<String, Image> loader) {
        this.board = board;
        this.sprites = sprites;
        this.loader = loader;
    }

    /**
     * Leaves remains for {@code owner} at the given board bounds, replacing any it already had.
     *
     * @param bodySprite the dead character's sprite path (picks its own shot / crushed remains)
     * @return the remains view, or null when the death leaves nothing or there is no board
     */
    public ImageView show(Object owner, DeathCause cause, String bodySprite, double x, double y, double size) {
        Pane pane = board.get();
        String path = sprites.spriteFor(cause, bodySprite, FireBlast.seedFor(x, y));
        if (pane == null || path == null) {
            return null;
        }
        Image image = images.computeIfAbsent(path, loader);
        if (image == null) {
            return null;
        }
        clear(owner);
        ImageView view = new ImageView(image);
        view.setFitWidth(size);
        view.setFitHeight(size);
        view.setPreserveRatio(true);
        view.setLayoutX(x);
        view.setLayoutY(y);
        view.setMouseTransparent(true);
        pane.getChildren().add(Math.min(FLOOR_INDEX, pane.getChildren().size()), view);
        ImageView dropped = ledger.put(owner, view);
        if (dropped != null) {
            pane.getChildren().remove(dropped);
        }
        return view;
    }

    /** Removes the remains of {@code owner} (e.g. a zombie that resurrected). */
    public void clear(Object owner) {
        ImageView view = ledger.remove(owner);
        Pane pane = board.get();
        if (view != null && pane != null) {
            pane.getChildren().remove(view);
        }
    }

    /** Removes all remains (new level). */
    public void clearAll() {
        Pane pane = board.get();
        if (pane != null) {
            pane.getChildren().removeAll(ledger.values());
        }
        ledger.clear();
    }

    List<ImageView> views() {
        return ledger.values();
    }

    private static Image loadImage(String path) {
        URL url = FxRemainsLayer.class.getResource(path);
        return url != null ? new Image(url.toExternalForm()) : null;
    }
}
