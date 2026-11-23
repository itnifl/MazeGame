package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.game.maze.constants.ScreenNameFXMLConstants;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelProgress;
import main.game.maze.game.shop.LevelShop;
import main.game.maze.game.shop.ShopCatalogLoader;
import main.game.maze.game.shop.ShopMenu;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JavaFX shop screen (#164): the view of the shared shop menu")
class ShopControllerTest {

    @BeforeAll
    static void initFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        assertTrue(latch.await(2, TimeUnit.SECONDS), "JavaFX startup timed out");
    }

    private static void onFx(ThrowingRunnable action) throws Exception {
        CountDownLatch done = new CountDownLatch(1);
        AtomicReference<Throwable> error = new AtomicReference<>();
        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                error.set(t);
            } finally {
                done.countDown();
            }
        });
        assertTrue(done.await(10, TimeUnit.SECONDS), "FX action timed out");
        if (error.get() != null) {
            throw new AssertionError(error.get());
        }
    }

    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    private static KeyEvent key(KeyCode code) {
        return new KeyEvent(KeyEvent.KEY_PRESSED, "", "", code, false, false, false, false);
    }

    @Test
    @DisplayName("shows every item, buys with Enter, moves with Down and closes with Esc")
    void shopScreen() throws Exception {
        LevelProgress progress = new LevelProgress(LevelCatalogLoader.loadDefault());   // in memory: no savepoint file
        progress.completeLevel(new PlayerArsenal(1), 5000, "Normal");
        AtomicInteger closed = new AtomicInteger();
        onFx(() -> {
            FXMLLoader loader = new FXMLLoader(ShopController.class.getResource(ScreenNameFXMLConstants.ShopScreen));
            AnchorPane root = loader.load();
            ShopController controller = loader.getController();
            controller.setMenu(new ShopMenu(new LevelShop(ShopCatalogLoader.loadDefault(), progress)));
            controller.setOnClose(closed::incrementAndGet);

            javafx.scene.layout.HBox shelves = (javafx.scene.layout.HBox) root.lookup("#shelvesBox");
            Label credits = (Label) root.lookup("#creditsLabel");
            assertNotNull(shelves);
            assertEquals(java.util.List.of(4, 5, 4), shelves.getChildren().stream()
                    .map(shelf -> ((VBox) shelf).getChildren().size()).toList(),
                    "a header and three cheap, four medium, three expensive cards");
            assertEquals("Score to spend: 5000", credits.getText());

            // the first row is the shield boost (800); Down moves to the flame boost (1000), Down again to grenades (600)
            controller.handleKeyPressed(key(KeyCode.DOWN));
            controller.handleKeyPressed(key(KeyCode.DOWN));
            controller.handleKeyPressed(key(KeyCode.ENTER));
            assertEquals("Score to spend: 4400", credits.getText());
            assertTrue(((Label) root.lookup("#statusLabel")).getText().startsWith("Bought Grenades"));

            controller.handleKeyPressed(key(KeyCode.ESCAPE));
            assertEquals(1, closed.get());
        });
        progress.advance(new PlayerArsenal(0));
        assertEquals(5, progress.inheritedArsenal().count(Weapon.GRENADE), "what was bought goes into the next level");
    }
}
