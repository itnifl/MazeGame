package main.game.maze.game.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelProgress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Shop menu: the model both shop screens draw")
class ShopMenuTest {

    private LevelProgress progress;
    private ShopMenu menu;

    @BeforeEach
    void setUp() {
        progress = new LevelProgress(LevelCatalogLoader.loadDefault());
        menu = new ShopMenu(new LevelShop(ShopCatalogLoader.loadDefault(), progress));
        progress.completeLevel(new PlayerArsenal(1), 1500, "Easy");
    }

    @Test
    @DisplayName("one row per item, the first selected; Up and Down wrap round")
    void selection() {
        List<ShopMenu.Row> rows = menu.rows();
        assertEquals(10, rows.size());
        assertTrue(rows.get(0).selected());
        menu.moveUp();
        assertEquals(9, menu.selectedIndex());
        menu.moveDown();
        menu.moveDown();
        assertEquals(1, menu.selectedIndex());
        menu.select(42);
        assertEquals(1, menu.selectedIndex(), "a click outside the list changes nothing");
        menu.select(4);
        assertTrue(menu.rows().get(4).selected());
    }

    @Test
    @DisplayName("buying shows what happened and updates the row and the score")
    void buying() {
        int grenades = indexOf(ShopItemKind.GRENADE);
        menu.select(grenades);
        assertEquals(LevelShop.Result.BOUGHT, menu.buySelected());
        assertTrue(menu.status().startsWith("Bought Grenades"));
        assertEquals("have 5", menu.rows().get(grenades).ownedText());
        assertEquals(900, menu.credits());
        assertEquals("Score to spend: 900", menu.creditsText());

        int nuke = indexOf(ShopItemKind.NUKE_GRENADE);
        assertFalse(menu.rows().get(nuke).affordable());
        menu.select(nuke);
        assertEquals(LevelShop.Result.NOT_ENOUGH_SCORE, menu.buySelected());
        assertTrue(menu.status().startsWith("Not enough score"));

        menu.select(indexOf(ShopItemKind.SHIELD_BOOST));
        menu.buySelected();
        assertEquals("ACTIVE", menu.rows().get(indexOf(ShopItemKind.SHIELD_BOOST)).ownedText());
        assertEquals("Extra long shield", menu.rows().get(indexOf(ShopItemKind.SHIELD_BOOST)).title());
        assertEquals("Grenades x5", menu.rows().get(grenades).title());
        menu.reset();
        assertEquals("", menu.status());
        assertEquals(0, menu.selectedIndex());
    }

    private int indexOf(ShopItemKind kind) {
        List<ShopMenu.Row> rows = menu.rows();
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).item().kind() == kind) {
                return i;
            }
        }
        throw new AssertionError(kind);
    }
}
