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

    @Test
    void infectionWarningShowsFullCaptionText() throws Exception {
        GameController controller = new GameController();
        AnchorPane root = new AnchorPane();

        Field rootField = GameController.class.getDeclaredField("root");
        rootField.setAccessible(true);
        rootField.set(controller, root);

        Method ensure = GameController.class.getDeclaredMethod("ensureInfectionWarningSign");
        ensure.setAccessible(true);
        ensure.invoke(controller);

        Field signField = GameController.class.getDeclaredField("infectionWarningSign");
        signField.setAccessible(true);
        VBox sign = (VBox) signField.get(controller);

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
        GameController controller = new GameController();
        ImageView zombieNode = new ImageView();

        Method applyMist = GameController.class.getDeclaredMethod("applyInfectiousMist", javafx.scene.Node.class, int.class);
        applyMist.setAccessible(true);

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        Platform.runLater(() -> {
            try {
                applyMist.invoke(controller, zombieNode, 50);
            } catch (Exception ex) {
                failure.set(ex);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(2, TimeUnit.SECONDS), "FX thread should apply the mist effect");
        if (failure.get() != null) {
            throw new RuntimeException(failure.get());
        }

        assertTrue(zombieNode.getEffect() instanceof DropShadow,
                "infectious characters should carry a neon mist effect");
    }
}