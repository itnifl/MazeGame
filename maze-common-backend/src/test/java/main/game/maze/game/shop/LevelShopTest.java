package main.game.maze.game.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import main.game.maze.game.items.PlayerArsenal;
import main.game.maze.game.items.Weapon;
import main.game.maze.game.level.FileSavePointStore;
import main.game.maze.game.level.LevelBoosts;
import main.game.maze.game.level.LevelCatalogLoader;
import main.game.maze.game.level.LevelProgress;
import main.game.maze.game.level.SavePoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Shop between levels (#164): pay with the score carried into the next level")
class LevelShopTest {

    private static final ShopCatalog CATALOG = ShopCatalogLoader.loadDefault();

    @TempDir
    Path dir;

    private LevelProgress progress;
    private LevelShop shop;

    @BeforeEach
    void setUp() {
        progress = new LevelProgress(LevelCatalogLoader.loadDefault(), new FileSavePointStore(dir.resolve("save.properties")));
        shop = new LevelShop(CATALOG, progress);
    }

    private void winLevelOneWith(int score) {
        PlayerArsenal arsenal = new PlayerArsenal(2);
        arsenal.add(Weapon.LASER_RIFLE, 4);
        arsenal.cycle();   // laser in hand
        progress.completeLevel(arsenal, score, "Hard");
    }

    @Test
    @DisplayName("the shop is closed while a level is played and open between levels")
    void openOnlyBetweenLevels() {
        assertFalse(shop.isOpen());
        assertEquals(0, shop.credits());
        assertEquals(LevelShop.Result.CLOSED, shop.buy(CATALOG.item(ShopItemKind.GRENADE)));
        winLevelOneWith(20_000);
        assertTrue(shop.isOpen());
        assertEquals(20_000, shop.credits());
        progress.advance(PlayerArsenal.restore(progress.pendingSavePoint().orElseThrow().arsenal()));
        assertFalse(shop.isOpen(), "closed again once the next level is entered");
    }

    @Test
    @DisplayName("a weapon costs its price and is carried into the next level; the weapon in hand stays")
    void buyingAWeapon() {
        winLevelOneWith(20_000);
        ShopItem grenades = CATALOG.item(ShopItemKind.GRENADE);
        assertEquals(LevelShop.Result.BOUGHT, shop.buy(grenades));
        assertEquals(20_000 - grenades.price(), shop.credits());
        assertEquals(grenades.amount(), shop.stock(grenades));
        assertEquals(LevelShop.Result.BOUGHT, shop.buy(grenades));
        assertEquals(2 * grenades.amount(), shop.stock(grenades), "weapons stack");

        progress.advance(new PlayerArsenal(0));
        PlayerArsenal next = progress.inheritedArsenal();
        assertEquals(2 * grenades.amount(), next.count(Weapon.GRENADE));
        assertEquals(4, next.count(Weapon.LASER_RIFLE), "what the player had is still there");
        assertEquals(Weapon.LASER_RIFLE, next.selected(), "buying does not change the weapon in hand");
        assertEquals(20_000 - 2 * grenades.price(), progress.carriedScore(), "the price came out of the carried score");
    }

    @Test
    @DisplayName("the extra life gives three refills of the health bar")
    void extraLifeIsThreeRefills() {
        winLevelOneWith(20_000);
        ShopItem life = CATALOG.item(ShopItemKind.EXTRA_LIFE);
        shop.buy(life);
        progress.advance(new PlayerArsenal(0));
        assertEquals(3, progress.inheritedArsenal().count(Weapon.LIFE_REFILL));
    }

    @Test
    @DisplayName("not enough score: nothing changes")
    void notEnoughScore() {
        ShopItem infantry = CATALOG.item(ShopItemKind.INFANTRY_STRIKE);
        winLevelOneWith(infantry.price() - 1);
        assertFalse(shop.canAfford(infantry));
        assertEquals(LevelShop.Result.NOT_ENOUGH_SCORE, shop.buy(infantry));
        assertEquals(infantry.price() - 1, shop.credits());
        assertEquals(0, shop.stock(infantry));
    }

    @Test
    @DisplayName("boosts last for the next level, and buying one twice is refused instead of wasting score")
    void boosts() {
        winLevelOneWith(20_000);
        ShopItem shield = CATALOG.item(ShopItemKind.SHIELD_BOOST);
        ShopItem flames = CATALOG.item(ShopItemKind.FLAME_BOOST);
        assertEquals(LevelShop.Result.BOUGHT, shop.buy(shield));
        assertTrue(shop.isActive(shield));
        assertEquals(LevelShop.Result.ALREADY_ACTIVE, shop.buy(shield));
        assertEquals(20_000 - shield.price(), shop.credits(), "the refused purchase cost nothing");
        assertEquals(LevelShop.Result.BOUGHT, shop.buy(flames));

        progress.advance(new PlayerArsenal(0));
        LevelBoosts boosts = progress.boosts();
        assertEquals(shield.factor(), boosts.shieldDurationFactor(), 1e-9);
        assertEquals(flames.factor(), boosts.bombPowerFactor(), 1e-9);

        // the level after that has no boosts unless they are bought again
        progress.completeLevel(new PlayerArsenal(0), 1000, "Hard");
        progress.advance(new PlayerArsenal(0));
        assertEquals(LevelBoosts.NONE, progress.boosts());
    }

    @Test
    @DisplayName("what was bought is in the savepoint: loading it later, or restarting the level, keeps it")
    void keptInTheSavepoint() {
        winLevelOneWith(20_000);
        shop.buy(CATALOG.item(ShopItemKind.BUNKER_BUSTER));
        shop.buy(CATALOG.item(ShopItemKind.FLAME_BOOST));

        LevelProgress later = new LevelProgress(LevelCatalogLoader.loadDefault(),
                new FileSavePointStore(dir.resolve("save.properties")));
        assertTrue(later.resumeFromSavePoint());
        assertEquals(3, later.inheritedArsenal().count(Weapon.BUNKER_BUSTER));
        assertTrue(later.boosts().hasBombBoost());
        int paid = CATALOG.item(ShopItemKind.BUNKER_BUSTER).price() + CATALOG.item(ShopItemKind.FLAME_BOOST).price();
        assertEquals(20_000 - paid, later.carriedScore());
        later.restartLevel();
        assertTrue(later.boosts().hasBombBoost(), "the level restarts as it was entered");
    }

    @Test
    @DisplayName("the pending savepoint can only be replaced by one for the same level")
    void updateGuard() {
        assertThrows(IllegalStateException.class, () -> progress.updatePendingSavePoint(new SavePoint(2, null)));
        winLevelOneWith(100);
        assertThrows(IllegalStateException.class, () -> progress.updatePendingSavePoint(new SavePoint(3, null)));
    }
}
