package main.game.maze.libgdx.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.Input;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelProgress;
import main.game.maze.game.shop.LevelShop;
import main.game.maze.game.shop.ShopCatalogLoader;
import main.game.maze.game.shop.ShopMenu;
import main.game.maze.libgdx.view.GdxShopOverlayView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("libGDX shop overlay (#164): keys and clicks drive the shared shop menu")
class GdxShopOverlayControllerTest {

    private static LevelProgress betweenLevels(int score) {
        LevelProgress progress = new LevelProgress(LevelCatalogLoader.loadDefault());
        for (int level = 1; level < 3; level++) {   // medium items (plasma grenades) are sold after level 3
            progress.completeLevel(new PlayerArsenal(0), 0, "Hard");
            progress.advance(new PlayerArsenal(0));
        }
        progress.completeLevel(new PlayerArsenal(1), score, "Hard");
        return progress;
    }

    private static GdxShopOverlayView.ShopLayout layout(int rows) {
        List<GdxShopOverlayView.Rect> rects = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            rects.add(new GdxShopOverlayView.Rect(10, 500 - i * 40, 400, 38));
        }
        return new GdxShopOverlayView.ShopLayout(rects, new GdxShopOverlayView.Rect(100, 10, 100, 30),
                new GdxShopOverlayView.Rect(220, 10, 100, 30));
    }

    @Test
    @DisplayName("Down moves, Enter buys; the purchase goes into the next level")
    void keys() {
        LevelProgress progress = betweenLevels(10_000);
        ShopMenu menu = new ShopMenu(new LevelShop(ShopCatalogLoader.loadDefault(), progress));
        var shop = new GdxShopOverlayController(menu);
        shop.open();
        assertTrue(shop.isOpen());
        shop.handleKeys(Set.of(Input.Keys.DOWN)::contains);
        shop.handleKeys(Set.of(Input.Keys.S)::contains);
        assertEquals(2, menu.selectedIndex());
        shop.handleKeys(Set.of(Input.Keys.ENTER)::contains);
        assertEquals(9_400, menu.credits(), "grenades cost 600");
        progress.advance(new PlayerArsenal(0));
        assertEquals(5, progress.inheritedArsenal().count(Weapon.GRENADE));
    }

    @Test
    @DisplayName("a click selects a row, a click on the selected row buys it, Done closes")
    void clicks() {
        ShopMenu menu = new ShopMenu(new LevelShop(ShopCatalogLoader.loadDefault(), betweenLevels(10_000)));
        var shop = new GdxShopOverlayController(menu);
        shop.open();
        shop.onRendered(layout(menu.rows().size()));
        shop.handleClick(50, 500 - 3 * 40 + 5);   // row 3: plasma grenades
        assertEquals(3, menu.selectedIndex());
        assertEquals(10_000, menu.credits(), "selecting is not buying");
        shop.handleClick(50, 500 - 3 * 40 + 5);
        assertEquals(8_000, menu.credits(), "plasma grenades cost 2000");
        shop.handleClick(150, 20);                 // Buy
        assertEquals(6_000, menu.credits());
        shop.handleClick(250, 20);                 // Done
        assertFalse(shop.isOpen());
    }

    @Test
    @DisplayName("Left and Right (or A and D) move across the tier shelves")
    void acrossTheShelves() {
        ShopMenu menu = new ShopMenu(new LevelShop(ShopCatalogLoader.loadDefault(), betweenLevels(10_000)));
        var shop = new GdxShopOverlayController(menu);
        shop.open();
        shop.handleKeys(Set.of(Input.Keys.RIGHT)::contains);
        assertEquals(3, menu.selectedIndex(), "the first medium item");
        shop.handleKeys(Set.of(Input.Keys.D)::contains);
        assertEquals(7, menu.selectedIndex(), "the first expensive item");
        shop.handleKeys(Set.of(Input.Keys.A)::contains);
        shop.handleKeys(Set.of(Input.Keys.LEFT)::contains);
        assertEquals(0, menu.selectedIndex(), "back on the cheap shelf");
    }
}
