package main.game.maze;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;
import main.game.maze.constants.ScreenNameFXMLConstants;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelProgress;
import main.game.maze.game.shop.LevelShop;
import main.game.maze.game.shop.ShopCatalogLoader;
import main.game.maze.game.shop.ShopMenu;
import main.game.maze.javafx.menu.FxMenuTheme;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The win screen and the shop with the menu theme: the cards, plates and labels get the theme's style classes, and the
 * screens render with it. The rendered screens are written to {@code target/menu-snapshots} for looking at the design.
 */
@DisplayName("JavaFX screens between levels: themed like a supply depot")
class BetweenLevelScreensTest {

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
        assertTrue(done.await(20, TimeUnit.SECONDS), "FX action timed out");
        if (error.get() != null) {
            throw new AssertionError(error.get());
        }
    }

    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    private static LevelProgress afterLevelOne(int score) {
        LevelProgress progress = new LevelProgress(LevelCatalogLoader.loadDefault());   // in memory: no savepoint file
        progress.completeLevel(new PlayerArsenal(1), score, "Normal");
        return progress;
    }

    @Test
    @DisplayName("the shop shows crate cards on a shelf per tier: the chosen one lit, the ones not sold yet locked")
    void shopCards() throws Exception {
        LevelProgress progress = afterLevelOne(3000);
        onFx(() -> {
            FXMLLoader loader = new FXMLLoader(ShopController.class.getResource(ScreenNameFXMLConstants.ShopScreen));
            AnchorPane root = loader.load();
            ShopController controller = loader.getController();
            controller.setMenu(new ShopMenu(new LevelShop(ShopCatalogLoader.loadDefault(), progress)));

            assertTrue(root.getStylesheets().contains(FxMenuTheme.stylesheet()));
            assertEquals(ShopMenu.TITLE, ((Label) root.lookup("#titleLabel")).getText());
            List<Node> cards = root.lookupAll("." + ShopController.CARD_CLASS).stream().toList();
            assertEquals(10, cards.size());
            assertEquals(3, root.lookupAll("." + ShopController.SHELF_CLASS).size(), "one shelf per tier");
            assertTrue(cards.get(0).getStyleClass().containsAll(List.of(ShopController.CARD_CLASS, ShopController.SELECTED_CLASS)));
            assertTrue(cards.get(9).getStyleClass().contains(ShopController.LOCKED_CLASS), "expensive items wait for level 6");
            assertTrue(!cards.get(1).getStyleClass().contains(ShopController.LOCKED_CLASS), "cheap items are sold now");
            assertEquals("Buy", ((Button) root.lookup("#buyButton")).getText());
            assertTrue(root.lookup("#doneButton").getStyleClass().contains("done"));

            controller.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));
            Label status = (Label) root.lookup("#statusLabel");
            assertTrue(status.getStyleClass().contains("menu-good"), "bought: " + status.getText());
            assertTrue(!((Label) root.lookup("#briefingLabel")).getText().isBlank(), "what the chosen item does");
        });
    }

    @Test
    @DisplayName("the win screen and the shop render with the theme")
    void screensRender() throws Exception {
        LevelProgress progress = afterLevelOne(3000);
        Path out = Path.of("target", "menu-snapshots");
        Files.createDirectories(out);
        onFx(() -> {
            FXMLLoader shopLoader = new FXMLLoader(ShopController.class.getResource(ScreenNameFXMLConstants.ShopScreen));
            AnchorPane shop = shopLoader.load();
            ShopController controller = shopLoader.getController();
            controller.setMenu(new ShopMenu(new LevelShop(ShopCatalogLoader.loadDefault(), progress)));
            assertDarkPlate(snapshot(shop, out.resolve("shop.png")));

            FXMLLoader winLoader = new FXMLLoader(ShopController.class.getResource(ScreenNameFXMLConstants.WinGameScreen));
            AnchorPane win = winLoader.load();
            WinGameController winController = winLoader.getController();
            winController.setScoreLabel(3000);
            assertEquals("Next Level", ((Button) win.lookup("#nextLevelButton")).getText());
            assertDarkPlate(snapshot(win, out.resolve("win.png")));
        });
    }

    @Test
    @DisplayName("the main menu is themed too: the title plate, the riveted panel and metal-plate buttons")
    void mainMenuRenders() throws Exception {
        Path out = Path.of("target", "menu-snapshots");
        Files.createDirectories(out);
        onFx(() -> {
            FXMLLoader loader = new FXMLLoader(StartController.class.getResource(ScreenNameFXMLConstants.StartScreen));
            AnchorPane menu = loader.load();
            assertTrue(menu.getStylesheets().contains(FxMenuTheme.stylesheet()));
            assertEquals(main.game.maze.game.theme.StartMenuText.TITLE, ((Label) menu.lookup("#titleLabel")).getText());
            Button start = (Button) menu.lookup("#startButton");
            assertEquals("Start Mission", start.getText());
            assertTrue(start.getStyleClass().contains("start-mission"));
            assertTrue(menu.lookup("#difficultyCombo").getStyleClass().contains("menu-combo"));
            assertDarkPlate(snapshot(menu, out.resolve("start.png")));
        });
    }

    @Test
    @DisplayName("Help & Controls shows every key as a key, with what it does, and Back or Esc closes it")
    void helpScreen() throws Exception {
        Path out = Path.of("target", "menu-snapshots");
        Files.createDirectories(out);
        java.util.concurrent.atomic.AtomicInteger closed = new java.util.concurrent.atomic.AtomicInteger();
        onFx(() -> {
            FXMLLoader loader = new FXMLLoader(HelpController.class.getResource(ScreenNameFXMLConstants.HelpScreen));
            AnchorPane help = loader.load();
            HelpController controller = loader.getController();
            controller.setOnClose(closed::incrementAndGet);
            assertEquals(main.game.maze.game.theme.ControlsGuide.entries().size(),
                    help.lookupAll("." + HelpController.ROW_CLASS).size(), "a row per key");
            assertTrue(help.lookupAll("." + HelpController.KEY_CLASS).size() >= 6, "keycaps: SPACE, TAB, P, O, H, ESC");
            assertEquals("Back", ((Button) help.lookup("#backButton")).getText());
            assertDarkPlate(snapshot(help, out.resolve("help.png")));
            controller.handleKeyPressed(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false));
            assertEquals(1, closed.get());

            FXMLLoader menuLoader = new FXMLLoader(StartController.class.getResource(ScreenNameFXMLConstants.StartScreen));
            AnchorPane menu = menuLoader.load();
            assertEquals("Help & Controls", ((Button) menu.lookup("#helpButton")).getText());
        });
    }

    private static void assertDarkPlate(BufferedImage image) {
        int rgb = image.getRGB(image.getWidth() / 2, image.getHeight() / 2 + 40);
        int brightness = ((rgb >> 16) & 0xff) + ((rgb >> 8) & 0xff) + (rgb & 0xff);
        assertTrue(brightness < 3 * 110, "the middle of the screen is the panel, not the backdrop: #" + Integer.toHexString(rgb));
    }

    private static BufferedImage snapshot(Parent root, Path file) throws Exception {
        new Scene(root, 800, 630);   // the game window
        root.applyCss();
        root.layout();
        WritableImage image = root.snapshot(null, null);
        PixelReader pixels = image.getPixelReader();
        BufferedImage copy = new BufferedImage((int) image.getWidth(), (int) image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < copy.getHeight(); y++) {
            for (int x = 0; x < copy.getWidth(); x++) {
                copy.setRGB(x, y, pixels.getArgb(x, y));
            }
        }
        ImageIO.write(copy, "png", file.toFile());
        return copy;
    }
}
