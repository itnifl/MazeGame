package main.game.maze.javafx.render;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.game.maze.common.remains.DeathCause;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FxRemainsLayer")
class FxRemainsLayerTest {

    private static final String ZOMBIE = "/main/game/maze/zombie1-down.png";

    @BeforeAll
    static void initFx() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    private static void onFx(Runnable action) throws Exception {
        AtomicReference<Throwable> failure = new AtomicReference<>();
        CountDownLatch done = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                failure.set(t);
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(5, TimeUnit.SECONDS));
        if (failure.get() instanceof AssertionError ae) {
            throw ae;
        }
        if (failure.get() != null) {
            throw new RuntimeException(failure.get());
        }
    }

    /** Board like the game's: maze canvas at index 0, then a character on top. */
    private static Pane board() {
        Pane pane = new Pane();
        pane.getChildren().add(new Canvas(400, 400));
        pane.getChildren().add(new Rectangle(10, 10));
        return pane;
    }

    @Test
    @DisplayName("remains lie on the floor: just above the maze canvas, under the characters, at the enemy's spot")
    void placedOnTheFloor() throws Exception {
        onFx(() -> {
            Pane pane = board();
            FxRemainsLayer layer = new FxRemainsLayer(() -> pane);

            ImageView view = layer.show("zombie", DeathCause.FIRE, ZOMBIE, 30, 40, 32);

            assertNotNull(view);
            assertSame(view, pane.getChildren().get(FxRemainsLayer.FLOOR_INDEX));
            assertTrue(pane.getChildren().get(0) instanceof Canvas, "maze stays at the bottom");
            assertEquals(30, view.getLayoutX());
            assertEquals(40, view.getLayoutY());
            assertEquals(32, view.getFitWidth());
            assertTrue(view.getImage().getWidth() > 1, "the coal sprite is on the classpath");
        });
    }

    @Test
    @DisplayName("each owner keeps one remains; clear removes it (respawn); clearAll empties the board (new level)")
    void ownersAndClearing() throws Exception {
        onFx(() -> {
            Pane pane = board();
            FxRemainsLayer layer = new FxRemainsLayer(() -> pane);
            Object zombie = new Object();
            Object bomber = new Object();

            ImageView first = layer.show(zombie, DeathCause.FIRE, ZOMBIE, 0, 0, 32);
            ImageView second = layer.show(zombie, DeathCause.CRUSHED, ZOMBIE, 5, 5, 32);
            layer.show(bomber, DeathCause.SHOT, "/main/game/maze/pumpkinbomber.png", 50, 50, 32);

            assertFalse(pane.getChildren().contains(first), "a second death replaces the old remains");
            assertTrue(pane.getChildren().contains(second));
            assertEquals(2, layer.views().size());

            layer.clear(zombie);
            assertFalse(pane.getChildren().contains(second));
            assertEquals(1, layer.views().size());

            layer.clearAll();
            assertTrue(layer.views().isEmpty());
            assertEquals(2, pane.getChildren().size(), "only the canvas and the character are left");
        });
    }

    @Test
    @DisplayName("deaths of unknown cause, or without a board, leave nothing")
    void nothingForOtherOrNoBoard() throws Exception {
        onFx(() -> {
            Pane pane = board();
            assertNull(new FxRemainsLayer(() -> pane).show("z", DeathCause.OTHER, ZOMBIE, 0, 0, 32));
            assertEquals(2, pane.getChildren().size());
            assertNull(new FxRemainsLayer(() -> null).show("z", DeathCause.FIRE, ZOMBIE, 0, 0, 32));
        });
    }

    @Test
    @DisplayName("over the cap the oldest remains leave the board")
    void capRemovesOldestFromBoard() throws Exception {
        onFx(() -> {
            Pane pane = board();
            FxRemainsLayer layer = new FxRemainsLayer(() -> pane);
            ImageView oldest = layer.show(new Object(), DeathCause.FIRE, ZOMBIE, 0, 0, 8);
            for (int i = 0; i < main.game.maze.common.remains.RemainsLedger.DEFAULT_CAPACITY; i++) {
                layer.show(new Object(), DeathCause.FIRE, ZOMBIE, i, 0, 8);
            }
            assertFalse(pane.getChildren().contains(oldest));
            assertEquals(main.game.maze.common.remains.RemainsLedger.DEFAULT_CAPACITY, layer.views().size());
        });
    }
}
