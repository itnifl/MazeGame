package main.game.maze.javafx.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javafx.application.Platform;
import javafx.scene.control.Button;
import main.game.maze.game.shop.ShopTier;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.game.theme.MenuThemeLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JavaFX menu theme: the stylesheet of the screens between levels, rendered from the theme model")
class FxMenuThemeTest {

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

    @Test
    @DisplayName("the template turns every colour, texture, tier and button of the theme into CSS")
    void stylesheetFromTheModel() {
        MenuTheme theme = MenuThemeLoader.loadDefault();
        String css = FxMenuTheme.css(theme);
        assertTrue(css.contains("-fx-font-family: \"" + theme.font() + "\""), css);
        assertTrue(css.contains(FxMenuTheme.rgba(theme.colour(MenuTheme.Swatch.TITLE))));
        for (MenuTheme.Art art : new MenuTheme.Art[] {MenuTheme.Art.PANEL, MenuTheme.Art.CARD_SELECTED,
                MenuTheme.Art.HAZARD_STRIP, MenuTheme.Art.VIGNETTE}) {
            String file = theme.image(art).path().substring(theme.image(art).path().lastIndexOf('/') + 1);
            assertTrue(css.contains(file + "\")"), art + " is used: " + file);
        }
        assertTrue(css.contains("-fx-border-image-slice: 30 fill;"), "the panel's 9-slice inset");
        for (MenuButton button : MenuButton.values()) {
            assertTrue(css.contains(".menu-button." + FxMenuTheme.styleClass(button) + " {"), button.name());
        }
        for (ShopTier tier : ShopTier.values()) {
            assertTrue(css.contains("." + FxMenuTheme.tierStyleClass(tier) + " {"), tier.name());
        }
        assertFalse(css.contains("${"), "every value was filled in");
        assertTrue(FxMenuTheme.stylesheet().startsWith("data:text/css;base64,"));
    }

    @Test
    @DisplayName("colours become CSS rgba(), names become style classes")
    void conversions() {
        assertEquals("rgba(255,0,0,1.000)", FxMenuTheme.rgba("#ff0000"));
        assertEquals("rgba(0,0,0,0.753)", FxMenuTheme.rgba("#000000c0"));
        assertEquals("next-level", FxMenuTheme.styleClass(MenuButton.NEXT_LEVEL));
        assertEquals("tier-expensive", FxMenuTheme.tierStyleClass(ShopTier.EXPENSIVE));
    }

    @Test
    @DisplayName("a themed button shows its label on its metal plate")
    void themedButton() throws Exception {
        onFx(() -> {
            Button button = new Button("x");
            FxMenuTheme.style(button, MenuButton.BACK_TO_MENU);
            assertEquals("Back to Menu", button.getText());
            assertTrue(button.getStyleClass().contains(FxMenuTheme.BUTTON_CLASS));
            assertTrue(button.getStyleClass().contains("back-to-menu"));
            assertFalse(button.getStyleClass().contains("button"), "no default JavaFX button look under the plate");
        });
    }
}
