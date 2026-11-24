package main.game.maze.libgdx.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.graphics.Color;
import java.util.List;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelProgress;
import main.game.maze.game.shop.LevelShop;
import main.game.maze.game.shop.ShopCatalogLoader;
import main.game.maze.game.shop.ShopMenu;
import main.game.maze.game.shop.ShopTier;
import main.game.maze.game.theme.MenuButton;
import main.game.maze.game.theme.MenuTheme;
import main.game.maze.game.theme.MenuThemeLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** The parts of the libGDX screens between levels that need no GL context: layout and the theme's colours. */
@DisplayName("libGDX screens between levels: the menu theme and the layout")
class GdxMenuViewsTest {

    @Test
    @DisplayName("the win screen's buttons: Shop and Next Level only while a next level follows")
    void winButtons() {
        assertEquals(List.of(MenuButton.SAVE_SCORE, MenuButton.SHOP, MenuButton.NEXT_LEVEL, MenuButton.RESTART_LEVEL,
                MenuButton.BACK_TO_MENU), GdxWinOverlayView.buttons(true));
        assertEquals(List.of(MenuButton.SAVE_SCORE, MenuButton.RESTART_LEVEL, MenuButton.BACK_TO_MENU),
                GdxWinOverlayView.buttons(false));
    }

    @Test
    @DisplayName("the shop with its three shelves fits the game window")
    void shopFitsTheWindow() {
        LevelProgress progress = new LevelProgress(LevelCatalogLoader.loadDefault());
        progress.completeLevel(new PlayerArsenal(1), 3000, "Normal");
        ShopMenu menu = new ShopMenu(new LevelShop(ShopCatalogLoader.loadDefault(), progress));
        float height = GdxShopOverlayView.panelHeight(menu);
        assertTrue(height <= 620f, "the window is 665 px with its title bar: " + height);
        assertTrue(height >= 4 * GdxShopOverlayView.CARD_H, "room for the medium shelf's four cards");
    }

    @Test
    @DisplayName("the skin takes its colours from the theme model, and draws nothing it cannot load")
    void skinColours() {
        MenuTheme theme = MenuThemeLoader.loadDefault();
        GdxMenuSkin skin = new GdxMenuSkin(theme, path -> null);
        float[] title = MenuTheme.rgba(theme.colour(MenuTheme.Swatch.TITLE));
        assertEquals(new Color(title[0], title[1], title[2], title[3]), skin.colour(MenuTheme.Swatch.TITLE));
        assertEquals(GdxMenuSkin.color(theme.badge(ShopTier.EXPENSIVE).colour()), skin.badgeColour(ShopTier.EXPENSIVE));
        assertEquals(0.753f, GdxMenuSkin.color("#000000c0").a, 0.001f);
        assertNull(skin.texture(MenuTheme.Art.PANEL), "no texture without a GL context");
        skin.plate(null, MenuTheme.Art.PANEL, 0, 0, 10, 10);   // nothing to draw: no batch needed
        skin.hazard(null, 0, 0, 10);
        assertEquals(12f, skin.hazardHeight());
    }

    @Test
    @DisplayName("the help screen fits the game window; the main menu's Help & Controls button is hit-tested")
    void helpScreen() {
        assertTrue(GdxHelpView.panelHeight() <= 622f, "the window is 665 px with its title bar: " + GdxHelpView.panelHeight());
        var layout = new main.game.maze.libgdx.view.layout.MenuLayout(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                400, 100, 180, 40);
        assertTrue(layout.onHelp(450, 120));
        assertTrue(!layout.onHelp(350, 120));
        assertTrue(!main.game.maze.libgdx.view.layout.MenuLayout.zero().onHelp(10, 10));
    }
}
