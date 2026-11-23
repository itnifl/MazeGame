package main.game.maze.game.theme;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumMap;
import java.util.Map;
import main.game.maze.game.shop.ShopTier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Menu theme: the look of the screens between levels, from the theme model")
class MenuThemeTest {

    private final MenuTheme theme = MenuThemeLoader.loadDefault();

    @Test
    @DisplayName("the shipped theme has a colour, a texture and a tier badge for everything, and every texture exists")
    void completeAndOnTheClasspath() {
        assertEquals("Consolas", theme.font());
        for (MenuTheme.Swatch swatch : MenuTheme.Swatch.values()) {
            assertNotNull(theme.colour(swatch));
        }
        for (MenuTheme.Art art : MenuTheme.Art.values()) {
            assertNotNull(MenuTheme.class.getResource(theme.image(art).path()), art + ": " + theme.image(art).path());
        }
        for (ShopTier tier : ShopTier.values()) {
            assertNotNull(MenuTheme.class.getResource(theme.badge(tier).image()), tier.name());
        }
        assertEquals(30, theme.image(MenuTheme.Art.PANEL).slice());
        assertEquals(0, theme.image(MenuTheme.Art.TOKEN).slice(), "drawn as it is");
    }

    @Test
    @DisplayName("colours are #rrggbb or #rrggbbaa")
    void colours() {
        assertArrayEquals(new float[] {1f, 0f, 0f, 1f}, MenuTheme.rgba("#ff0000"), 1e-6f);
        assertArrayEquals(new float[] {0f, 0f, 0f, 192 / 255f}, MenuTheme.rgba("#000000c0"), 1e-6f);
        assertThrows(IllegalArgumentException.class, () -> MenuTheme.rgba("red"));
        assertThrows(IllegalArgumentException.class, () -> MenuTheme.rgba("#fff"));
    }

    @Test
    @DisplayName("a theme missing a colour, a texture or a tier badge is refused, and it cannot be changed afterwards")
    void incompleteIsRefused() {
        Map<MenuTheme.Swatch, String> swatches = new EnumMap<>(theme.swatches());
        swatches.remove(MenuTheme.Swatch.SHADOW);
        IllegalArgumentException missing = assertThrows(IllegalArgumentException.class,
                () -> new MenuTheme("F", swatches, theme.images(), theme.badges()));
        assertTrue(missing.getMessage().contains("SHADOW"), missing.getMessage());
        assertThrows(IllegalArgumentException.class, () -> new MenuTheme(" ", theme.swatches(), theme.images(), theme.badges()));
        assertThrows(UnsupportedOperationException.class, () -> theme.swatches().put(MenuTheme.Swatch.TEXT, "#ffffff"));
        assertThrows(IllegalArgumentException.class, () -> new MenuTheme.Image("/x.png", -1));
    }

    @Test
    @DisplayName("every button of the screens between levels has a label and a metal plate of the theme")
    void buttons() {
        for (MenuButton button : MenuButton.values()) {
            assertTrue(button.art().name().startsWith("BUTTON_"), button.name());
            assertTrue(!button.label().isBlank());
        }
        assertEquals(MenuTheme.Art.BUTTON_GO, MenuButton.NEXT_LEVEL.art());
        assertEquals(MenuTheme.Art.BUTTON_DANGER, MenuButton.BACK_TO_MENU.art());
    }
}
