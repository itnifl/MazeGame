package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Platform;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for infection-warning visuals now owned by {@link FxEnemyCoordinator}.
 * Accesses internals via reflection so we can verify JavaFX-thread visual state
 * without a full FXML scene.
 */
class GameControllerInfectionWarningTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        latch.await(2, TimeUnit.SECONDS);
    }

    private static FxEnemyCoordinator buildCoordinator(AnchorPane root) {
        return new FxEnemyCoordinator(null, root, new FxGameWorldModel(),
                () -> null, () -> null, () -> {});
    }

    @Test
    void infectionWarningShowsFullCaptionText() throws Exception {
        AnchorPane root       = new AnchorPane();
        FxEnemyCoordinator ec = buildCoordinator(root);

        Method ensure = FxEnemyCoordinator.class.getDeclaredMethod("ensureInfectionWarningSign");
        ensure.setAccessible(true);

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        Platform.runLater(() -> {
            try {
                ensure.invoke(ec);
            } catch (Exception ex) {
                failure.set(ex);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(2, TimeUnit.SECONDS), "FX thread should ensure the infection sign");
        if (failure.get() != null) throw new RuntimeException(failure.get());

        Field signField = FxEnemyCoordinator.class.getDeclaredField("infectionWarningSign");
        signField.setAccessible(true);
        VBox sign = (VBox) signField.get(ec);

        assertEquals(1, root.getChildren().size(), "only the infection sign should be added to the root");
        assertTrue(root.getChildren().get(0) == sign, "the sign should be attached to the root");
        assertTrue(sign.getChildren().stream().anyMatch(Text.class::isInstance), "the caption should render as text");

        Text caption = (Text) sign.getChildren().stream()
                .filter(Text.class::isInstance)
                .findFirst()
                .orElseThrow();
        assertEquals("Infected!", caption.getText(), "the caption must show the full infected label");
    }

    @Test
    void infectingZombieGetsPulsatingMistEffect() throws Exception {
        FxEnemyCoordinator ec = buildCoordinator(new AnchorPane());
        ImageView zombieNode  = new ImageView();

        Method applyMist = FxEnemyCoordinator.class
                .getDeclaredMethod("applyInfectiousMist", javafx.scene.Node.class, int.class);
        applyMist.setAccessible(true);

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        Platform.runLater(() -> {
            try {
                applyMist.invoke(ec, zombieNode, 50);
            } catch (Exception ex) {
                failure.set(ex);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(2, TimeUnit.SECONDS), "FX thread should apply the mist effect");
        if (failure.get() != null) throw new RuntimeException(failure.get());

        assertTrue(zombieNode.getEffect() instanceof DropShadow,
                "infectious characters should carry a neon mist effect");
    }
}
